/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck;



/**
 *
 * @author intel
 */
public class Product3 {
    private String productName;
    private String productDescription;
    private String productCategory;
    private double productPrice;
    // private String def;
    
    public Product3(){}

    public Product3(String productName, String productDescription, String productCategory, double productPrice) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productCategory = productCategory;
        this.productPrice = productPrice;

    }
    
    public String getName(){
        return productName;
    }
    
    public void setName(String Name){
        this.productName = Name;
    }
    
    public String getDescription(){
      return productDescription;
    }
    
    public void setDescription(String desc){
        this.productDescription = desc;
    }
    
    public String getCategory(){
      return productCategory;
    }
    
    public void setCategory(String category){
        this.productCategory = category;
    }
    
    public double getPrice(){
        return productPrice;
    }
    
    public void setPrice(double price){
        this.productPrice = price;
    }  
}
