/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhpm.dto;

/**
 *
 * @author minht
 */
public class ProductTestDTO implements Comparable<ProductTestDTO>{
    private String id;
    private String name;
    private String brand;
    private String img;
    private String cpu;
    private String ram;
    private String vga;
    private String display;
    private String hardDisk;
    private String category;
    private String price;
    
    private int matching;

    public ProductTestDTO(String id, String name, String brand, String img, String cpu, String ram, String vga, String display, String hardDisk, String category, String price) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.img = img;
        this.cpu = cpu;
        this.ram = ram;
        this.vga = vga;
        this.display = display;
        this.hardDisk = hardDisk;
        this.category = category;
        this.price = price;
    }

    public ProductTestDTO() {
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getMatching() {
        return matching;
    }

    public void setMatching(int matching) {
        this.matching = matching;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getVga() {
        return vga;
    }

    public void setVga(String vga) {
        this.vga = vga;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getHardDisk() {
        return hardDisk;
    }

    public void setHardDisk(String hardDisk) {
        this.hardDisk = hardDisk;
    }

    @Override
    public int compareTo(ProductTestDTO dto) {
        if(matching == dto.matching){
            return 0;
        }else if(matching < dto.matching){
            return 1;
        }else
            return -1;
    }
    
    
    public void changeName(){
        if(name.length()>23){
            this.name = name.substring(0, 20) + "...";
        }
    }
    
}
