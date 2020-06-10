/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhpm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import thanhpm.blo.BrandBLO;
import thanhpm.blo.CategoryBLO;
import thanhpm.blo.ProductBLO;
import thanhpm.dto.ProductTestDTO;
import thanhpm.entities.Brand;
import thanhpm.entities.Category;

/**
 *
 * @author minht
 */
public class SearchServlet extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SEARCHPAGE = "search.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String question1 = request.getParameter("question1");
            String question2 = request.getParameter("question2");
            String question3 = request.getParameter("question3");
            String question4 = request.getParameter("question4");
            String question5 = request.getParameter("question5");
            String question6 = request.getParameter("question6");
            String question7 = request.getParameter("question7");

            String brandId = request.getParameter("brandId");
            String categoryId = request.getParameter("categoryId");

            //lay ra url trang page 1
            HttpSession session = request.getSession();
            if (!request.getQueryString().contains("pageNumber")) {
                String queryString = "DispatcherServlet?" + request.getQueryString();
                session.setAttribute("QUERYSTRING", queryString);
            }

            //lay brand va category tu db
            BrandBLO brandBLO = new BrandBLO();
            CategoryBLO categoryBLO = new CategoryBLO();
            List<Brand> brands = brandBLO.getAllBrand();
            List<Category> categorys = categoryBLO.getAllCategory();
            request.setAttribute("BRANDS", brands);
            request.setAttribute("CATEGORYS", categorys);

            //logic phan trang
            String pageNum = request.getParameter("pageNumber");
            int pageNumber = 1;
            if (pageNum != null) {
                pageNumber = Integer.parseInt(pageNum);
            }

            ProductBLO blo = new ProductBLO();
            List<ProductTestDTO> resultDB = new ArrayList<>();
            if (question1 != null && question2 != null && question3 != null
                    && question4 != null && question5 != null
                    && question6 != null && question7 != null ) {
                resultDB = blo.searchProduct(question1, question2, question3, question4, question5, question6, question7);
                url = SEARCHPAGE;
            }else if(brandId != null){
                resultDB = blo.searchProductByBrand(brandId);
                url = SEARCHPAGE;
            }else if(categoryId != null){
                resultDB = blo.searchProductByCategory(categoryId);
                url = SEARCHPAGE;
            }
            
            int maxItem = resultDB.size();
            int maxPage = maxItem / 20;
            if (maxItem % 20 != 0) {
                maxPage = maxPage + 1;
            }
            List<ProductTestDTO> result = new ArrayList<>();
            if (resultDB != null) {
                int toNum = (pageNumber * 20) - 20;
                int endNum = toNum + 20;
                if (endNum < resultDB.size()) {
                    for (int i = toNum; i < endNum; i++) {
                        result.add(resultDB.get(i));
                    }
                } else {
                    for (int i = toNum; i < resultDB.size(); i++) {
                        result.add(resultDB.get(i));
                    }
                }
            }

            request.setAttribute("PAGENUMER", pageNumber);
            request.setAttribute("MAXPAGE", maxPage);
            request.setAttribute("RESULTLIST", result);
            

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
