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
public class MyQuery1 {
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
    
    public ArrayList<Product3> BindTable(){
        ArrayList<Product3> lists = new ArrayList<Product3>();
        Connection con = getConnection();
        Statement st;
        ResultSet rs;
        
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM `products`");
            Product3 p;
            while(rs.next()){
                p = new Product3(
                        rs.getString("ProductName"),
                        rs.getString("ProductDescription"),
                        rs.getString("ProductCategory"),
                        rs.getDouble("ProductPrice")
                );
                lists.add(p);
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(MyQuery1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lists;
    }
}
