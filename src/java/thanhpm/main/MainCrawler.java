package thanhpm.main;

import java.util.List;
import thanhpm.blo.ProductBLO;
import thanhpm.crawler.AKProductByPageCrawler;
import thanhpm.crawler.BKBrandCrawler;
import thanhpm.dto.ProductTestDTO;
import thanhpm.entities.Product;
import thanhpm.utils.StringConstant;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author minht
 */
public class MainCrawler {

    public static void main(String[] args) {
        CrawlerBKService bkCrawler = new CrawlerBKService();
        bkCrawler.crawlBK();
        
        CrawlerAKService akService = new CrawlerAKService();
        akService.crawlAK();
        
        

    }
}
