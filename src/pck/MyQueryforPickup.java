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
public class MyQueryforPickup {
    public Connection getConnection(){
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/project", "root","");
        } 
        catch (SQLException ex){
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
    
    public ArrayList<ProductforPickup> BindTable(){
        ArrayList<ProductforPickup> list = new ArrayList<ProductforPickup>();
        Connection con = getConnection();
        Statement st;
        ResultSet rs;
        
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT `Id`, `Total`, `Date1`, `Status` FROM `pickup`");
            ProductforPickup p;
            while(rs.next()){
                p = new ProductforPickup(
                        rs.getString("Id"),
                        rs.getInt("Total"),
                        rs.getString("Date1"),
                        rs.getString("Status")
                        //rs.getString("description")
                );
                list.add(p);
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(MyQueryforPickup.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}