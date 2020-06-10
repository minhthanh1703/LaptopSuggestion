/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhpm.utils;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import thanhpm.dto.ProductTestDTO;

/**
 *
 * @author minht
 */
public class Helper {
    public static int computerMatchingDensity(String a, String b){
        int n = a.length();
        int m = b.length();
        
        int dp[][] = new int[n+1][m+1];
        
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(a.charAt(i) == b.charAt(j)){
                    dp[i + 1][j + 1] = dp[i][j] + 1; 
                }
                else
                    dp[i + 1][j + 1] = Math.max(dp[i + 1][j], dp[i][j + 1]);
            }
        }
        return (dp[n][m]*100) / Math.min(n, m);
    }
    
    public static void addListToList(List<ProductTestDTO> listFrom, List<ProductTestDTO> listTo){
        if(listFrom != null && listTo != null){
            for(int i = 0; i < listFrom.size(); i++){
                listTo.add(listFrom.get(i));
            }
        }
    }
    
    public static String formatPrice(String price){
        String result = "";
        try {
            float floatPrice = Float.parseFloat(price);
            
            Locale localeVN = new Locale("vi", "VN");
            NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
            result = currencyVN.format(floatPrice);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }
}
