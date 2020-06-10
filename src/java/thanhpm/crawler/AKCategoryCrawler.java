/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhpm.crawler;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import thanhpm.utils.StringConstant;
import thanhpm.utils.XMLUtils;
import thanhpm.utils.XmlSyntaxChecker;

/**
 *
 * @author minht
 */
public class AKCategoryCrawler extends BaseCrawler {

    public AKCategoryCrawler(ServletContext context) {
        super(context);
    }

    private static String beginSyntax = "<div class=\"item-filter category-filter\"";
    private static String endSyntax = "<div class=\"item-filter\">";

    public Map<String, String> getCategory(String url) {
        BufferedReader reader = null;
        try {
            if (url != null) {
                reader = getBufferedReaderForURL(url);
                String line = "";
                String document = "<document>";
                boolean isStart = false;

                if (reader != null) {
                    //get html fragment
                    while ((line = reader.readLine()) != null) {
                        if (line.contains(beginSyntax)) {
                            isStart = true;
                        }
                        if (isStart && line.contains(endSyntax)) {
                            break;
                        }
                        if (isStart) {
                            document = document + line.trim();
                        }
                    }
                    document = document + "</document>";
                    XmlSyntaxChecker checker = new XmlSyntaxChecker();
                    document = checker.check(document);
//                    System.out.println(document);
                    return domParserForCategory(document);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> domParserForCategory(String document) throws XPathExpressionException {
        document = document.trim();
        Document doc = XMLUtils.convertStringToXMLDocument(document);

        Map<String, String> categoryMap = new HashMap<>();
        
        if(doc != null){
            XPath xPath = XMLUtils.creatXPath();
            String exp = "//div/ul/li/a";
            
            NodeList tagList = (NodeList) xPath.evaluate(exp, doc, XPathConstants.NODESET);
            if(tagList != null){
                for (int i = 1; i < 4; i++){
                    Node tmp = tagList.item(i);
                    String categoryString = tmp.getTextContent().trim();
                    if(categoryString.equals("Laptop Gaming")){
                        categoryString = "LAPTOP GAMING";
                    }else if(categoryString.equals("Laptop Cao Cấp")){
                        categoryString = "CAO CẤP - SANG TRỌNG";
                    }else if(categoryString.equals("Laptop Văn Phòng")){
                        categoryString = "HỌC TẬP VĂN PHÒNG";
                    }
                    String urlString = tmp.getAttributes().getNamedItem("href").getNodeValue();
                    if(!categoryString.equals("") && !urlString.equals("")){
                        categoryMap.put(categoryString, StringConstant.ANKHANG_URL_DOMAIN_NAME + urlString);
                    }
                }
            }
        }

        return categoryMap;
    }
}
