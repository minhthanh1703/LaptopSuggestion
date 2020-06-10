package thanhpm.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import thanhpm.entities.Brand;
import thanhpm.entities.Category;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-25T09:47:25")
@StaticMetamodel(Product.class)
public class Product_ { 

    public static volatile SingularAttribute<Product, String> vga;
    public static volatile SingularAttribute<Product, String> img;
    public static volatile SingularAttribute<Product, String> memory;
    public static volatile SingularAttribute<Product, Double> price;
    public static volatile SingularAttribute<Product, String> display;
    public static volatile SingularAttribute<Product, Brand> brandId;
    public static volatile SingularAttribute<Product, String> name;
    public static volatile SingularAttribute<Product, String> cpu;
    public static volatile SingularAttribute<Product, Integer> id;
    public static volatile SingularAttribute<Product, Category> categoryId;
    public static volatile SingularAttribute<Product, String> ram;

}