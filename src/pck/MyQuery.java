/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.Query;


/**
 *
 * @author intel
 */
public class MyQuery {
    public Connection getConnection(){
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/pos", "root","");
        } 
        catch (SQLException ex){
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
    
    public ArrayList<Product2> BindTable(){
        ArrayList<Product2> list = new ArrayList<Product2>();
        Connection con = getConnection();
        Statement st;
        ResultSet rs;
        
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM `products`");
            Product2 p;
            while(rs.next()){
                p = new Product2(
                        rs.getString("ProductName"),
                        rs.getString("ProductDescription"),
                        rs.getString("ProductCategory"),
                        rs.getInt("ProductQuantity"),
                        rs.getDouble("ProductPrice"),
                        rs.getString("ProductDate")
                );
                list.add(p);
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(MyQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
