/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhpm.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import thanhpm.blo.BrandBLO;
import thanhpm.blo.CategoryBLO;
import thanhpm.blo.ProductBLO;
import thanhpm.crawler.BKBrandCrawler;
import thanhpm.crawler.BKCategoryCrawler;
import thanhpm.crawler.BKProductDetailsCrawler;
import thanhpm.crawler.BKProductLinkByCategoryCrawler;
import thanhpm.dto.ProductTestDTO;
import thanhpm.entities.Brand;
import thanhpm.entities.Category;
import thanhpm.entities.Product;
import thanhpm.utils.Helper;
import thanhpm.utils.JAXBUtils;
import thanhpm.utils.StringConstant;

/**
 *
 * @author minht
 */
public class CrawlerBKService {

    private List<String> brandBK;
    private Map<String, String> categoryBKMap;
    private List<ProductTestDTO> listProductTempBK;
    private List<ProductTestDTO> listProductByCategoryBK;

    private List<Category> categorysDB;
    private List<Brand> brandsDB;

    public void crawlBK() {
        crawlBrandBK();
        crawlCategoryBK();

//        System.out.println("Số sản phẩm của bách khoa: " + listProductByCategoryBK.size());
//        for (int i = 0; i < listProductByCategoryBK.size(); i++) {
//            System.out.println("name: " + listProductByCategoryBK.get(i).getName());
//            System.out.println("brand: " + listProductByCategoryBK.get(i).getBrand());
//            System.out.println("img: " + listProductByCategoryBK.get(i).getImg());
//            System.out.println("cpu: " + listProductByCategoryBK.get(i).getCpu());
//            System.out.println("ram: " + listProductByCategoryBK.get(i).getRam());
//            System.out.println("vga: " + listProductByCategoryBK.get(i).getVga());
//            System.out.println("display: " + listProductByCategoryBK.get(i).getDisplay());
//            System.out.println("hardDisk: " + listProductByCategoryBK.get(i).getHardDisk());
//            System.out.println("category: " + listProductByCategoryBK.get(i).getCategory());
//            System.out.println("price: " + listProductByCategoryBK.get(i).getPrice());
//            System.out.println("");
//        }
    }

    public void crawlBrandBK() {
        BrandBLO brandBLO = new BrandBLO();
        BKBrandCrawler brandCrawler = new BKBrandCrawler(null);
        brandBK = brandCrawler.getBrand(StringConstant.BACHKHOA_URL);
        System.out.println("Hãng: ");
        for (int i = 0; i < brandBK.size(); i++) {
            System.out.print(brandBK.get(i) + ", ");
            brandBLO.insertBrand(brandBK.get(i).trim());
        }
        brandsDB = brandBLO.getAllBrand();
    }

    public void crawlCategoryBK() {
        CategoryBLO categoryBLO = new CategoryBLO();
        BKCategoryCrawler categoryBK = new BKCategoryCrawler(null);
        categoryBKMap = categoryBK.getCategory(StringConstant.BACHKHOA_URL);
        System.out.println("-----Category và link của BACH KHOA-----");
        if (categoryBKMap != null) {
            for (Map.Entry<String, String> entry : categoryBKMap.entrySet()) {
                categoryBLO.insertCategory(entry.getKey().trim().toUpperCase());
            }
            categorysDB = categoryBLO.getAllCategory();
            for (Map.Entry<String, String> entry : categoryBKMap.entrySet()) {

                System.out.println(entry.getKey() + "   " + entry.getValue());
                crawlProductByCategory(entry.getKey(), entry.getValue());

            }
        }

    }

    public void crawlProductByCategory(String category, String urlCategory) {
        BKProductLinkByCategoryCrawler crawlerLinkProductBK = new BKProductLinkByCategoryCrawler(null, category, urlCategory);
        List<String> linkProductListBK = crawlerLinkProductBK.getProductLink();
        listProductTempBK = new ArrayList<>();
        if (linkProductListBK != null) {
            System.out.println(category + linkProductListBK.size() + "sản phẩm");
            for (int i = 0; i < linkProductListBK.size(); i++) {
                final int num = i;
                Thread thread = new Thread() {
                    public void run() {
                        crawlProductDetailsByLink(category, linkProductListBK.get(num));

                    }
                };
                thread.start();

            }
        }
        if (listProductByCategoryBK == null) {
            listProductByCategoryBK = new ArrayList<>();
        }
        Helper.addListToList(listProductTempBK, listProductByCategoryBK);
    }

    public void crawlProductDetailsByLink(String category, String urlProduct) {
        BKProductDetailsCrawler productDetailsCrawler = new BKProductDetailsCrawler(null, category, urlProduct);
        ProductTestDTO dto = productDetailsCrawler.getProductDetails();

        if (dto != null) { 
            Product product = new Product();
            product.setName(dto.getName());
            for(int i = 0; i < brandsDB.size(); i++){
                if(dto.getName().toUpperCase().contains(brandsDB.get(i).getBrandName())){
                    product.setBrandId(brandsDB.get(i));
                    break;
                }
            }
            if(dto.getImg() != null){
                product.setImg(dto.getImg());
            }
            
            if(dto.getCpu() != null){
                product.setCpu(dto.getCpu());
            }
            
            if(dto.getRam() != null){
                product.setRam(dto.getRam());
            }
            
            if(dto.getVga() != null){
                product.setVga(dto.getVga());
            }
            
            if(dto.getDisplay() != null){
                product.setDisplay(dto.getDisplay());
            }
            
            if(dto.getHardDisk() != null){
                product.setMemory(dto.getHardDisk());
            }
            
            if(dto.getPrice() != null){
                try {
                    Double price = Double.parseDouble(dto.getPrice());
                    product.setPrice(price);
                } catch (Exception e) {
                }
            }
            
            for(int i = 0; i < categorysDB.size(); i++){
                if(dto.getCategory().equals(categorysDB.get(i).getCategoryName())){
                    product.setCategoryId(categorysDB.get(i));
                    break;
                }
            }
            boolean validate = JAXBUtils.validateXml(StringConstant.FILE_PATH_PRODUCT_XSD, product);
            if(validate){
                ProductBLO productBLO = new ProductBLO();
                productBLO.insertProduct(product);
            }
            
        }

    }

    public List<String> getBrandBK() {
        return brandBK;
    }

    public void setBrandBK(List<String> brandBK) {
        this.brandBK = brandBK;
    }

    public Map<String, String> getCategoryBKMap() {
        return categoryBKMap;
    }

    public void setCategoryBKMap(Map<String, String> categoryBKMap) {
        this.categoryBKMap = categoryBKMap;
    }

    public List<ProductTestDTO> getListProductTempBK() {
        return listProductTempBK;
    }

    public void setListProductTempBK(List<ProductTestDTO> listProductTempBK) {
        this.listProductTempBK = listProductTempBK;
    }

    public List<ProductTestDTO> getListProductByCategoryBK() {
        return listProductByCategoryBK;
    }

    public void setListProductByCategoryBK(List<ProductTestDTO> listProductByCategoryBK) {
        this.listProductByCategoryBK = listProductByCategoryBK;
    }

}
