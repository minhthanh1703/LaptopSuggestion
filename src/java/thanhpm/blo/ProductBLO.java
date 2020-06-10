/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhpm.blo;

import java.util.ArrayList;
import java.util.Collections;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import thanhpm.dto.ProductTestDTO;
import thanhpm.entities.Product;
import thanhpm.utils.Helper;

/**
 *
 * @author minht
 */
public class ProductBLO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("final_v2PU");

    public void persist(Object object) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public boolean checkProductExist(String productName) {
        EntityManager em = emf.createEntityManager();
        String jpql = "Select r From Product r "
                + "Where r.name = :productName";
        Query query = em.createQuery(jpql);
        query.setParameter("productName", productName);
        try {
            query.getSingleResult();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Product getProductByName(String productName) {
        EntityManager em = emf.createEntityManager();
        String jpql = "Select r From Product r "
                + "Where r.name = :productName";
        Query query = em.createQuery(jpql);
        query.setParameter("productName", productName);
        try {
            List<Product> result = query.getResultList();
            if (result != null && !result.isEmpty()) {
                return result.get(0);
            }
        } catch (Exception e) {
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }

    public void updateProduct(Product product) {
        EntityManager em = emf.createEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.merge(product);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void insertProduct(Product product) {
        EntityManager em = emf.createEntityManager();
        Product findProduct = getProductByName(product.getName());
        try {
            if (findProduct == null) {
                em.getTransaction().begin();
                em.persist(product);
                em.getTransaction().commit();
            } else {
                findProduct.setImg(product.getImg());
                findProduct.setCpu(product.getCpu());
                findProduct.setRam(product.getRam());
                findProduct.setVga(product.getVga());
                findProduct.setDisplay(product.getDisplay());
                findProduct.setMemory(product.getMemory());
                findProduct.setPrice(product.getPrice());
                updateProduct(findProduct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public ProductTestDTO getDetailsProduct(String productId) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT p.id, p.name, b.brand_name, p.img, p.cpu, p.ram, p.vga, p.display, p.memory, c.category_name, p.price "
                    + "FROM ((Product p INNER JOIN Brand b ON p.brand_id = b.id) INNER JOIN Category c ON p.category_id = c.id ) "
                    + "WHERE p.id = '" + productId + "'";

            Object[] result = (Object[]) em.createNativeQuery(jpql).getSingleResult();

            ProductTestDTO dto = new ProductTestDTO();
            dto.setId(Integer.toString((int) result[0]));
            dto.setName((String) result[1]);
            dto.setBrand((String) result[2]);
            dto.setImg((String) result[3]);
            dto.setCpu((String) result[4]);
            dto.setRam((String) result[5]);
            dto.setVga((String) result[6]);
            dto.setDisplay((String) result[7]);
            dto.setHardDisk((String) result[8]);
            dto.setCategory((String) result[9]);
            dto.setPrice(Double.toString((double) result[10]));

            dto.setPrice(Helper.formatPrice(dto.getPrice()));
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }

    public List<ProductTestDTO> searchProductByBrand(String brandId) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT p.id, p.name, b.brand_name, p.img, p.cpu, p.ram, p.vga, p.display, p.memory, c.category_name, p.price "
                    + "FROM ((Product p INNER JOIN Brand b ON p.brand_id = b.id) INNER JOIN Category c ON p.category_id = c.id ) "
                    + "WHERE b.id = '" + brandId + "' ORDER BY p.price  ";

            List<Object[]> result = em.createNativeQuery(jpql)
                    .getResultList();
            List<ProductTestDTO> listProduct = new ArrayList<>();
            for (Object[] rs : result) {
                ProductTestDTO dto = new ProductTestDTO();
                dto.setId(Integer.toString((int) rs[0]));
                dto.setName((String) rs[1]);
                dto.setBrand((String) rs[2]);
                dto.setImg((String) rs[3]);
                dto.setCpu((String) rs[4]);
                dto.setRam((String) rs[5]);
                dto.setVga((String) rs[6]);
                dto.setDisplay((String) rs[7]);
                dto.setHardDisk((String) rs[8]);
                dto.setCategory((String) rs[9]);
                dto.setPrice(Double.toString((double) rs[10]));
                listProduct.add(dto);
            }

            return changeNameAndPrice(listProduct);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }
    
    public List<ProductTestDTO> searchProductByCategory(String categoryId) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT p.id, p.name, b.brand_name, p.img, p.cpu, p.ram, p.vga, p.display, p.memory, c.category_name, p.price "
                    + "FROM ((Product p INNER JOIN Brand b ON p.brand_id = b.id) INNER JOIN Category c ON p.category_id = c.id ) "
                    + "WHERE c.id = '" + categoryId + "' ORDER BY p.price  ";

            List<Object[]> result = em.createNativeQuery(jpql)
                    .getResultList();
            List<ProductTestDTO> listProduct = new ArrayList<>();
            for (Object[] rs : result) {
                ProductTestDTO dto = new ProductTestDTO();
                dto.setId(Integer.toString((int) rs[0]));
                dto.setName((String) rs[1]);
                dto.setBrand((String) rs[2]);
                dto.setImg((String) rs[3]);
                dto.setCpu((String) rs[4]);
                dto.setRam((String) rs[5]);
                dto.setVga((String) rs[6]);
                dto.setDisplay((String) rs[7]);
                dto.setHardDisk((String) rs[8]);
                dto.setCategory((String) rs[9]);
                dto.setPrice(Double.toString((double) rs[10]));
                listProduct.add(dto);
            }

            return changeNameAndPrice(listProduct);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }

    public List<ProductTestDTO> searchProduct(String question1, String question2, String question3,
            String question4, String question5, String question6,
            String question7) {

        EntityManager em = emf.createEntityManager();
        try {

            String jpql = "SELECT p.id, p.name, b.brand_name, p.img, p.cpu, p.ram, p.vga, p.display, p.memory, c.category_name, p.price "
                    + "FROM ((Product p INNER JOIN Brand b ON p.brand_id = b.id) INNER JOIN Category c ON p.category_id = c.id ) "
                    + "WHERE ";

            String queryCategory = "";
            String requestCategory = "";
            if (question1.contains("Học tập") || question1.equals("study") ) {
                queryCategory = " c.category_name Like N'%PH%' ";
                requestCategory = "VĂN PHÒNG";
            } else if (question1.contains("game") || question1.equals("game")) {
                queryCategory = " c.category_name Like N'%GAMING%' ";
                requestCategory = "GAMING";
            } else if (question1.contains("doanh") || question1.equals("business")) {
                queryCategory = " c.category_name Like N'%CAO%' ";
                requestCategory = "CAO CẤP";
            } else if (question1.contains("chuyên nghiệp") || question1.equals("professional")) {
                queryCategory = " c.category_name Like N'%Đ%'  ";
                requestCategory = "ĐỒ HỌA";
            }
            jpql = jpql + queryCategory;

            String queryBrand = "";
            String brandRequest = "";
            if (question2 != null) {
                queryBrand = "OR b.brand_name = '" + question2 + "' ";
                brandRequest = question2;
            }
            jpql = jpql + queryBrand;

            String queryDisplay = "";
            String displayRequest = "";
            if (question3 != null) {
                queryDisplay = "OR p.display LIKE '%" + question3 + "%' ";
                displayRequest = question3;
            }
            jpql = jpql + queryDisplay;

            String queryRam = "";
            String ramRequest = "";
            if (question4 != null) {
                queryRam = "OR p.ram LIKE '%" + question4 + "%' ";
                ramRequest = question4;
            }
            jpql = jpql + queryRam;

            String queryCpu = "";
            String cpuRequest = "";
            if (question5 != null) {
                queryCpu = "OR p.cpu LIKE '%" + question5 + "%' ";
                cpuRequest = question5;
            }
            jpql = jpql + queryCpu;

            String queryMemory = "";
            String memoryRequest = "";
            if (question6 != null) {
                if (question6.equals("Cơ bản đơn thuần") || question6.equals("basic")) {
                    queryMemory = "OR p.memory LIKE '%256%' ";
                    memoryRequest = "256";
                } else if (question6.equals("Trung bình") || question6.equals("medium")) {
                    queryMemory = "OR p.memory LIKE '%512%' ";
                    memoryRequest = "512";
                } else if (question6.equals("Cao") || question6.equals("high")) {
                    queryMemory = "OR p.memory LIKE '%1tb%' ";
                    memoryRequest = "1tb";
                }
            }
            jpql = jpql + queryMemory;

            String queryVga = "";
            String vgaRequest = "";
            if (question7 != null) {
                if (question7.equals("AMD")) {
                    queryVga = "OR ( p.vga LIKE '%AMD%' OR p.vga LIKE '%Radeon%' ) ";
                    vgaRequest = "AMD - Radeon";
                } else {
                    queryVga = "OR p.vga LIKE '%" + question7 + "%' ";
                    vgaRequest = question7;
                }
            }
            jpql = jpql + queryVga;

            jpql = jpql + " ORDER BY p.price ";
            List<Object[]> result = em.createNativeQuery(jpql)
                    .getResultList();
            List<ProductTestDTO> listProduct = new ArrayList<>();
            for (Object[] rs : result) {
                ProductTestDTO dto = new ProductTestDTO();
                dto.setId(Integer.toString((int) rs[0]));
                dto.setName((String) rs[1]);
                dto.setBrand((String) rs[2]);
                dto.setImg((String) rs[3]);
                dto.setCpu((String) rs[4]);
                dto.setRam((String) rs[5]);
                dto.setVga((String) rs[6]);
                dto.setDisplay((String) rs[7]);
                dto.setHardDisk((String) rs[8]);
                dto.setCategory((String) rs[9]);
                dto.setPrice(Double.toString((double) rs[10]));
                listProduct.add(dto);
            }

            List<ProductTestDTO> dtoList = sortProductByRequesting(listProduct, requestCategory, brandRequest, displayRequest,
                    ramRequest, cpuRequest, memoryRequest, vgaRequest);
            if (dtoList.size() > 100) {
                List<ProductTestDTO> resultList = new ArrayList<>();
                for (int i = 0; i < 100; i++) {
                    resultList.add(dtoList.get(i));
                }
                return resultList;
            } else {
                return dtoList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return null;
    }

    public List<ProductTestDTO> changeNameAndPrice(List<ProductTestDTO> listProduct) {
        if (listProduct != null) {
            for (int i = 0; i < listProduct.size(); i++) {
                ProductTestDTO dto = listProduct.get(i);
                dto.changeName();
                dto.setPrice(Helper.formatPrice(dto.getPrice()));
            }
        }
        return listProduct;

    }

    public List<ProductTestDTO> sortProductByRequesting(List<ProductTestDTO> listProduct, String requestCategory,
            String brandRequest, String displayRequest, String ramRequest, String cpuRequest,
            String memoryRequest, String vgaRequest
    ) {

        for (int i = 0; i < listProduct.size(); i++) {
            ProductTestDTO dto = listProduct.get(i);
            int matching = 0;
            //request category
            if (requestCategory != null) {
                if (dto.getCategory().contains(requestCategory)) {
                    matching++;
                }
            }
            //request brand
            if (brandRequest != null) {
                if (brandRequest.equals(dto.getBrand())) {
                    matching++;
                }
            }
            //request display
            if (displayRequest != null) {
                if (dto.getDisplay().contains(displayRequest)) {
                    matching++;
                }
            }
            //requets ram
            if (ramRequest != null) {
                if (dto.getRam().contains(ramRequest)) {
                    matching++;
                }
            }
            //request cpu
            if (cpuRequest != null) {
                if (dto.getCpu().contains(cpuRequest)) {
                    matching++;
                }
            }
            //request memory
            if (memoryRequest != null) {
                if (dto.getHardDisk().contains(memoryRequest)) {
                    matching++;
                }
            }
            //request vga
            if (vgaRequest != null) {
                String[] requestVgaList = vgaRequest.split(vgaRequest);
                for (String vga : requestVgaList) {
                    if (dto.getVga().contains(vga)) {
                        matching++;
                        break;
                    }
                }
            }

            dto.setMatching(matching);
            dto.changeName();
            dto.setPrice(Helper.formatPrice(dto.getPrice()));
        }
        Collections.sort(listProduct);
        return listProduct;
    }

}
