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
public class Product2 {
    private int productId;
    private String productName;
    private String productDescription;
    private String productCategory;
    private int productQuantity;
    private double productPrice;
    private String date;
    // private String def;
    
    public Product2(){}

    public Product2(int productId, String productName, String productDescription, String productCategory, int productQuantity, double productPrice, String date) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productCategory = productCategory;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.date = date;
    }
    
    
    
    public Product2(String name, String desc, String category, int quantity, double productPrice, String date){
        this.productName = name;
        this.productDescription = desc;
        this.productCategory = category;
        this.productQuantity = quantity;
        this.productPrice = productPrice;
        this.date = date;
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
    
    public int getQuantity(){
        return productQuantity;
    }
    
    public void setQuantity(int quantity){
        this.productQuantity = quantity;
    }
    
    public double getPrice(){
        return productPrice;
    }
    
    public void setPrice(double price){
        this.productPrice = price;
    }
    
    public String getdate(){
        return date;
    }
    
    public void setdate(String date){
        this.date = date;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
    
}
