/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhpm.main;

import java.util.Map;
import thanhpm.crawler.AKCategoryCrawler;
import thanhpm.crawler.AKProductByCategoryCrawler;
import thanhpm.utils.StringConstant;

/**
 *
 * @author minht
 */
public class CrawlerAKService {

    private Map<String, String> categoryAKMap;

    public void crawlAK() {
        crawlCategoryAK();
    }

    public void crawlCategoryAK() {
        AKCategoryCrawler categoryAK = new AKCategoryCrawler(null);
        categoryAKMap = categoryAK.getCategory(StringConstant.ANKHANG_URL);
        for (Map.Entry<String, String> entry : categoryAKMap.entrySet()) {
            System.out.println(entry.getKey() + "   "  +entry.getValue());
            AKProductByCategoryCrawler productCrawler = new AKProductByCategoryCrawler(null, entry.getKey(), entry.getValue());
            productCrawler.crawlProductByPage();
        }
    }
}
