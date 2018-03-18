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
public class ProductforPickup {
    private String id;
    private int total;
    private String date;
    private String status;
    // private String def;
    //private byte[] Image;
    
    public ProductforPickup(){}
    
    public ProductforPickup(String Id, int Total, String Date, String Status){
        this.id = Id;
        this.total = Total;
        //this.def = Defi;
        this.date = Date;
        this.status = Status;
    }
    
    
    
    public String getID(){
      return id;
    }
    
    public void setID(String Id){
        this.id = Id;
    }
    
    
    
    
    
    public int getTotal(){
        return total;
    }
    
    public void setTotal(int Total){
        this.total = Total;
    }
   
    
    
    
    
     public String getDate(){
        return date;
    }
    
    public void setDate(String Date){
        this.date = Date;
    }
    
    
    
    
    public String getStatus(){
        return status;
    }
    
    public void setStatus(String Status){
        this.status = Status;
    }
}
