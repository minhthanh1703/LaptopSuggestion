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
import thanhpm.blo.BrandBLO;
import thanhpm.blo.CategoryBLO;
import thanhpm.blo.ProductBLO;
import thanhpm.dto.ProductTestDTO;
import thanhpm.entities.Brand;
import thanhpm.entities.Category;
import thanhpm.entities.Product;
import thanhpm.utils.JAXBUtils;
import thanhpm.utils.StringConstant;
import thanhpm.utils.XMLUtils;
import thanhpm.utils.XmlSyntaxChecker;

/**
 *
 * @author minht
 */
public class AKProductByPageCrawler extends BaseCrawler {

    private String url;
    private String category;

    private static String beginSyntax = "<ul class=\"ul product-list\">";
    private static String endSyntax = "<div class=\"paging\">";

    private List<ProductTestDTO> listDTO;

    public AKProductByPageCrawler(ServletContext context, String category, String url) {
        super(context);
        this.url = url;
        this.category = category;
    }

    public void crawlProductDetails() {
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
                    domParserProduct(document);

                    BrandBLO brandBLO = new BrandBLO();
                    List<Brand> brandsDB = brandBLO.getAllBrand();

                    CategoryBLO categoryBLO = new CategoryBLO();
                    List<Category> categorysDB = categoryBLO.getAllCategory();

                    for (int i = 0; i < listDTO.size(); i++) {
                        ProductTestDTO dto = listDTO.get(i);
                        Product product = new Product();
                        product.setName(dto.getName());
                        for (int j = 0; j < brandsDB.size(); j++) {
                            if (dto.getName().toUpperCase().contains(brandsDB.get(j).getBrandName())) {
                                product.setBrandId(brandsDB.get(j));
                                break;
                            }
                        }

                        if (dto.getImg() != null) {
                            product.setImg(dto.getImg().replace(":", "").trim());
                        }

                        if (dto.getCpu() != null) {
                            product.setCpu(dto.getCpu().replace(":", "").trim());
                        }

                        if (dto.getRam() != null) {
                            product.setRam(dto.getRam().replace(":", "").trim());
                        }

                        if (dto.getVga() != null) {
                            product.setVga(dto.getVga().replace(":", "").trim());
                        }

                        if (dto.getDisplay() != null) {
                            product.setDisplay(dto.getDisplay().replace(":", "").trim());
                        }

                        if (dto.getHardDisk() != null) {
                            product.setMemory(dto.getHardDisk().replace(":", "").trim());
                        }

                        if (dto.getPrice() != null) {
                            try {
                                Double price = Double.parseDouble(dto.getPrice().replace(":", "").trim());
                                product.setPrice(price);
                            } catch (Exception e) {
                            }
                        }

                        for (int j = 0; j < categorysDB.size(); j++) {
                            if (dto.getCategory().equals(categorysDB.get(j).getCategoryName())) {
                                product.setCategoryId(categorysDB.get(j));
                                break;
                            }
                        }
                        boolean validate = JAXBUtils.validateXml(StringConstant.FILE_PATH_PRODUCT_XSD, product);
                        if (validate) {
                            ProductBLO productBLO = new ProductBLO();
                            productBLO.insertProduct(product);
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void domParserProduct(String docString) throws XPathExpressionException {
        docString = docString.trim();

        Document doc = XMLUtils.convertStringToXMLDocument(docString);
        if (listDTO == null) {
            listDTO = new ArrayList<>();
        }
        if (doc != null) {
            XPath xPath = XMLUtils.creatXPath();
            String exp = "//h2/a";
            NodeList nodeName = (NodeList) xPath.evaluate(exp, doc, XPathConstants.NODESET);

            exp = "//img[@class='lazy']";
            NodeList nodeImg = (NodeList) xPath.evaluate(exp, doc, XPathConstants.NODESET);

            exp = "//p-container--/div/p/span[@class='p-price']";
            NodeList nodePrice = (NodeList) xPath.evaluate(exp, doc, XPathConstants.NODESET);

            exp = "//li/p-container--/p-hover--/div[@class='hover_content_pro tooltip']/div[3]";
            NodeList nodeParam = (NodeList) xPath.evaluate(exp, doc, XPathConstants.NODESET);

            if (nodeName != null) {
                for (int i = 0; i < nodeName.getLength(); i++) {
                    String name = nodeName.item(i).getTextContent().trim();
                    String img = "//www.ankhang.vn" + nodeImg.item(i).getAttributes().getNamedItem("data-original").getNodeValue().trim();
                    String price = nodePrice.item(i).getTextContent().replace(".", "").replace("VNĐ", "").replace("VNĐ", "").trim();
                    String parameterString = nodeParam.item(i).getTextContent().replace("+", "--");
                    String[] parameterArray = parameterString.split("--");
                    String cpu = "";
                    String vga = "";
                    String ram = "";
                    String display = "";
                    String hardDisk = "";
                    for (String w : parameterArray) {
                        if (w.contains("CPU")) {
                            cpu = w.substring(4).trim();
                        } else if (w.contains("RAM")) {
                            ram = w.substring(4).trim();
                        } else if (w.contains("VGA")) {
                            vga = w.substring(4).trim();
                        } else if (w.contains("Display") || w.contains("DISPLAY")) {
                            display = w.substring(9).trim();
                        } else if (w.contains("SSD") || w.contains("ssd") || w.contains("HDD") || w.contains("hdd")) {
                            hardDisk = hardDisk + w;
                        }
                    }
                    ProductTestDTO dto = new ProductTestDTO(null, name, null, img, cpu, ram, vga, display, hardDisk, category, price);
                    listDTO.add(dto);
                }
            }
        }
    }

}
