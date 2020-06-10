/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhpm.crawler;

import java.io.BufferedReader;
import javax.servlet.ServletContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import thanhpm.utils.XMLUtils;
import thanhpm.utils.XmlSyntaxChecker;

/**
 *
 * @author minht
 */
public class AKProductByCategoryCrawler extends BaseCrawler {

    private String url;
    private String category;
    private static String beginSyntax = "<div class=\"paging\">";
    private static String endSyntax = "!--category-pro-list-->";

    public AKProductByCategoryCrawler(ServletContext context, String category, String url) {
        super(context);
        this.url = url;
        this.category = category;
    }

    public void crawlProductByPage() {
        BufferedReader reader = null;
        try {
            if (url != null) {
                reader = getBufferedReaderForURL(url + "?page=1000");
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
//                    System.out.println(document);         
                    int maxPage = getMaxPage(document);
                    System.out.println("max page: " +maxPage);
                    for (int i = 1; i <= maxPage; i++) {
                        final String urlString = url + "?page=" + i;
                        Thread thread = new Thread() {
                            public void run() {
                                AKProductByPageCrawler akProductByPageCrawler = new AKProductByPageCrawler(null, category, urlString);
                                akProductByPageCrawler.crawlProductDetails();
                                
                            }
                        };
                        thread.start();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getMaxPage(String docString) throws XPathExpressionException {
        docString = docString.trim();
        Document doc = XMLUtils.convertStringToXMLDocument(docString);

        int max = 1;

        if (doc != null) {
            String exp = "//div[@class='paging']";
            XPath xPath = XMLUtils.creatXPath();
            Node pagingNode = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
            if (pagingNode != null) {
                Node maxPageNode = pagingNode.getLastChild();
                if (maxPageNode != null) {
                    String maxPageString = maxPageNode.getTextContent();
                    try {
                        max = Integer.parseInt(maxPageString);

                    } catch (Exception e) {
                    }
                }
            }
        }
        return max;
    }

}
