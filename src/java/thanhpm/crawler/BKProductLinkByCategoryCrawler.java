/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhpm.crawler;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import thanhpm.utils.StringConstant;
import thanhpm.utils.XMLUtils;
import thanhpm.utils.XmlSyntaxChecker;

/**
 *
 * @author minht
 */
public class BKProductLinkByCategoryCrawler extends BaseCrawler {
    
    private String url;
    private String category;
    private static String beginSyntax = "<ul class=\"cate normal\">";
    private static String endSyntax = "tracking?ad_id=179";
    
    private List<String> productLinkList;
    
    public BKProductLinkByCategoryCrawler(ServletContext context, String category, String url) {
        super(context);
        this.url = url;
        this.category = category;
    }
    
    public List<String> getProductLink() {
        BufferedReader reader = null;
        try {
            if (url != null) {
                reader = getBufferedReaderForURL(url);
                String line = "";
                String document = "<document>";
                boolean isStart = false;
                
                if (reader != null) {
                    while ((line = reader.readLine()) != null) {
                        if (line.contains(beginSyntax)) {
                            isStart = true;
                        }
                        if (isStart) {
                            document = document + line.trim();
                        }
                        if (isStart && line.contains(endSyntax)) {
                            break;
                        }
                    }
                    document = document + "</document>";
                    XmlSyntaxChecker checker = new XmlSyntaxChecker();
                    document = checker.check(document);
                    
                    int lastPage = getLastPage(document);
                    //crawl page 1
                    domParserProductLink(document);
                    //crawl page other
                    for (int i = 2; i <= lastPage; i++) {
                        String urlOther = url + "&page=" + i;
                        domParserProductLinkOtherPage(urlOther);
                    }
                   
                    return productLinkList;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public int getLastPage(String docString) throws XPathExpressionException {
        docString = docString.trim();
        Document doc = XMLUtils.convertStringToXMLDocument(docString);
        int lastPage = 0;
        int maxProduct = 0;
        int tmp = 0;
        if (doc != null) {
            XPath xPath = XMLUtils.creatXPath();
            String exp = "//a[@class='next caret_down']";
            String maxPageString = (String) xPath.evaluate(exp, doc, XPathConstants.STRING);
            maxPageString = maxPageString.replace("Xem thêm", "").replace("Laptop", "").trim();
            try {
                maxProduct = Integer.parseInt(maxPageString) + 25;
                lastPage = maxProduct / 25;
                tmp = maxProduct % 25;
                if (tmp != 0) {
                    lastPage = lastPage + 1;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return lastPage;
    }
    
    public void domParserProductLink(String docString) throws XPathExpressionException {
        docString = docString.trim();
        Document doc = XMLUtils.convertStringToXMLDocument(docString);
        if (productLinkList == null) {
            productLinkList = new ArrayList<>();
        }
        if (doc != null) {
            XPath xPath = XMLUtils.creatXPath();
            String exp = "//ul/li/a";
            NodeList listLink = (NodeList) xPath.evaluate(exp, doc, XPathConstants.NODESET);
            
            if (listLink != null) {
                for (int i = 0; i < listLink.getLength(); i++) {
                    String link = listLink.item(i).getAttributes().getNamedItem("href").getNodeValue();
                    if (link != null) {
                        productLinkList.add(StringConstant.BACHKHOA_URL_DOMAIN_NAME + link);
                    }
                }
            }
        }
    }
    
    public void domParserProductLinkOtherPage(String url){
        BufferedReader reader = null;
        try {
            if (url != null) {
                reader = getBufferedReaderForURL(url);
                String line = "";
                String document = "<document>";
                boolean isStart = false;

                if (reader != null) {
                    while ((line = reader.readLine()) != null) {
                        if (line.contains(beginSyntax)) {
                            isStart = true;
                        }
                        if (isStart) {
                            document = document + line.trim();
                        }
                        if (isStart && line.contains(endSyntax)) {
                            break;
                        }
                    }
                    document = document + "</document>";
                    XmlSyntaxChecker checker = new XmlSyntaxChecker();
                    document = checker.check(document);

                    domParserProductLink(document);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
