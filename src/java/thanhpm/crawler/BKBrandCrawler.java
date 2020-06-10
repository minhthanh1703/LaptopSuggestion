/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhpm.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import thanhpm.utils.XMLUtils;
import thanhpm.utils.XmlSyntaxChecker;

/**
 *
 * @author minht
 */
public class BKBrandCrawler extends BaseCrawler{
    
    public BKBrandCrawler(ServletContext context) {
        super(context);
    }
    
    private static String beginSyntax = "<a href=\"laptop.html\">";
    private static String endSyntax = "<a href=\"dtdd.html\">";
    
    
    public List<String> getBrand(String url) {
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
                        if (isStart && line.contains(endSyntax)) {
                            break;
                        }
                        if (isStart) {
                            document = document + line.trim();
                        }
                        if (line.contains(beginSyntax)) {
                            isStart = true;
                        }
                    }

                    document = document + "</document>";
                    XmlSyntaxChecker checker = new XmlSyntaxChecker();
                    document = checker.check(document);
//                    System.out.println(document);
                    return domParserForBrand(document);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return null;
    }
    
    public List<String> domParserForBrand(String document) throws UnsupportedEncodingException, XMLStreamException, XPathExpressionException {
        document = document.trim();
        Document doc = XMLUtils.convertStringToXMLDocument(document);
        
        List<String> brandList = new ArrayList<>();

        if (doc != null) {
            XPath xPath = XMLUtils.creatXPath();
            String exp = "//ul/li";
            NodeList tagList = (NodeList) xPath.evaluate(exp, doc, XPathConstants.NODESET);
            if (tagList != null) {
                for (int i = 0; i < tagList.getLength(); i++) {
                    Node node = tagList.item(i);
                    String tmp = node.getTextContent().trim();
                    brandList.add(tmp.toUpperCase());
                }
            }

        }
        return brandList;
    }
    
}
