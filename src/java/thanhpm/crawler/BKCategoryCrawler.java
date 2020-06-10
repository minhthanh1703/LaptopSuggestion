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
public class BKCategoryCrawler extends BaseCrawler {

    public BKCategoryCrawler(ServletContext context) {
        super(context);
    }

    private static String beginSyntax = "<div class=\"laptopmanu\">";
    private static String endSyntax = "<ul class=\"filter\">";

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
                            if (line.contains("<!--wrap-->")) {
                                document = document + "</div>";
                            } else {
                                document = document + line.trim();
                            }
                        }
                    }
                    document = document + "</document>";
                    XmlSyntaxChecker checker = new XmlSyntaxChecker();
                    document = checker.check(document);
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

        if (doc != null) {
            XPath xPath = XMLUtils.creatXPath();
            String exp = "//a";

            NodeList tagList = (NodeList) xPath.evaluate(exp, doc, XPathConstants.NODESET);
            if (tagList != null) {
                for (int i = 0; i < tagList.getLength(); i++) {
                    Node tmp = tagList.item(i);

                    String categoryString = tmp.getTextContent().trim();
                    String urlString = tmp.getAttributes().getNamedItem("href").getNodeValue();
                    if (!categoryString.equals("") && !urlString.equals("")) {
                        categoryMap.put(categoryString.toUpperCase(), StringConstant.BACHKHOA_URL_DOMAIN_NAME + urlString);
                    }

                }
            }
        }
        return categoryMap;
    }
}
