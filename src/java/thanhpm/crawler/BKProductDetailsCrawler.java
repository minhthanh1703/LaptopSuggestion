/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhpm.crawler;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import thanhpm.dto.ProductTestDTO;
import thanhpm.utils.XMLUtils;
import thanhpm.utils.XmlSyntaxChecker;

/**
 *
 * @author minht
 */
public class BKProductDetailsCrawler extends BaseCrawler {

    private String url;
    private String category;
    private static String beginSyntax = "<section>";
    private static String endSyntax = "<footer>";

    public BKProductDetailsCrawler(ServletContext context, String category, String url) {
        super(context);
        this.url = url;
        this.category = category;
    }

    public ProductTestDTO getProductDetails() {
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
                    

                    return domParserProductDetails(document);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ProductTestDTO domParserProductDetails(String docString) throws XPathExpressionException {
        docString = docString.trim();
        Document doc = XMLUtils.convertStringToXMLDocument(docString);

        ProductTestDTO dto;

        if (doc != null) {
            dto = new ProductTestDTO();
            XPath xPath = XMLUtils.creatXPath();
            String exp = "//h1";
            String name = (String) xPath.evaluate(exp, doc, XPathConstants.STRING);
            
            exp = "//p[@class='price promo_price']/strong";
            String price = (String) xPath.evaluate(exp, doc, XPathConstants.STRING);
            if(price.equals("")){
                exp = "//p[@class='price']/strong";
                price = (String) xPath.evaluate(exp, doc, XPathConstants.STRING);
            }
            price = price.replace(".", "").replace("₫", "");
            
            exp = "//div[@id='showimb']/a";
            Node node = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
            String img = "";
            if(node != null){
                img = node.getAttributes().getNamedItem("href").getNodeValue();
            }
            if(img.equals("")){
                exp = "//div[@class='extra_details']/img";
                node = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
                if(node!=  null){
                    img = node.getAttributes().getNamedItem("src").getNodeValue();
                }
            }
            
            exp = "//ul[@class='parameter']/li";
            NodeList nodeListParameter = (NodeList) xPath.evaluate(exp, doc, XPathConstants.NODESET);
            String cpu = "";
            String ram = "";
            String vga = "";
            String display = "";
            String hardDisk = ""; 
            
            dto.setName(name);
            dto.setCategory(category);
            dto.setPrice(price);
            dto.setImg(img);
           
            
            if(nodeListParameter != null){
                for(int i = 0; i < nodeListParameter.getLength(); i++){
                    Node tmp = nodeListParameter.item(i);
                    if(tmp.getTextContent().contains("Chip:") || tmp.getTextContent().contains("CPU:")){
                        cpu = tmp.getLastChild().getTextContent();
                    }else if(tmp.getTextContent().contains("RAM:")){
                        ram = tmp.getLastChild().getTextContent();
                    }else if(tmp.getTextContent().contains("Ổ cứng:") || tmp.getTextContent().contains("Đĩa cứng:") || tmp.getTextContent().contains("Bộ nhớ trong:")){
                        hardDisk = tmp.getLastChild().getTextContent();
                    }else if(tmp.getTextContent().contains("Card màn hình:") || tmp.getTextContent().contains("đồ họa:") 
                            || tmp.getTextContent().contains("Đồ họa:") || tmp.getTextContent().contains("VGA")){
                        vga = tmp.getLastChild().getTextContent();
                    }else if(tmp.getTextContent().contains("Màn hình:") || tmp.getTextContent().contains("Màn hình rộng:")){
                        display = tmp.getTextContent();
                    }
                    
                }
                dto.setCpu(cpu);
                dto.setRam(ram);
                dto.setHardDisk(hardDisk);
                dto.setVga(vga);
                dto.setDisplay(display);
            }
            return dto;
        }

        return null;

    }

}
