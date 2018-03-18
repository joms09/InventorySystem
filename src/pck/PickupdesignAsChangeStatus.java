
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck;
import net.proteanit.sql.DbUtils;
import java.sql.*;
import javax.swing.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author intel
 */
public class PickupdesignAsChangeStatus extends javax.swing.JFrame {
    static Connection connection;
    static String url;
    static Statement st;
    static ResultSet rs;
    String dbtbl;
    //static Connection connection;
    //static String url;
    static Statement st1;
    static ResultSet rs22;
    int count = 0;
    Connection Conn = null;
    //ResultSet rs = null;
    ResultSet rs1 = null;
    ResultSet rs2 = null;
    ResultSet rs4 = null;
    PreparedStatement pst5 = null;
    PreparedStatement pst4 = null;
    PreparedStatement pst3 = null;
    PreparedStatement pst2 = null;
    PreparedStatement pst1 = null;
    PreparedStatement pst = null;
    //PreparedStatement st = null;
    private ImageIcon format = null;
    String id = "";
    
    public int search(String id, String name) throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        url = "jdbc:mysql://localhost:3306/project";
        connection = DriverManager.getConnection(url, "root", "");
        st = connection.createStatement();
        rs = st.executeQuery("Select * From login_tbl Where Id= '" + id + "'  " + "and username = '" + name + "'");
        if (rs != null)
            return 1;
        else
            return 0;
    }
    
    public static class javaconnect{
        Connection Conn = null;
        
        public static Connection ConnecrDB(){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","");
                return Conn;
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, "error");
            }
            return null;
        }
    }
    
    /*public int tableClick2(){
        try{
            int count1 =0;
            int row = tbcart.getSelectedRow();
            int tbClick = Integer.parseInt(tbcart.getModel().getValueAt(row, 0).toString());
            String sql =  ("Select * From sales Where count = '" + tbClick + "'  ");
            pst = Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                count1 = rs.getInt("count");
            }
            return count1;
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        } 
        return 0;
    }*/
    
    /*public String tableClick(){
        try{
            int row = tb.getSelectedRow();
            String tbClick = tb.getModel().getValueAt(row, 0).toString();
            String sql =  ("Select * From store Where ID = '" + tbClick + "'  ");
            pst = Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                id = rs.getString("ID");
            }
            return id;
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        } 
        return null;
    }*/
    
    /*public void getOrder(){
        try{
            String sql = "INSERT INTO `sales2`(`ID`, `Product_Name`, `Qty`, `Price`, `Date`) VALUES(?,?,?,?,?)";
            //String sql2 = "INSERT INTO `sales2`(`ID`, `Product_Name`, `Price`, `Qty`, `Date`) VALUES (?,?,?,?,?)";
            pst = Conn.prepareStatement(sql);
            int roww = tbcart.getRowCount();
            //qtytxt.setText(Integer.toString(roww));
            for(int i = 0;i<roww;i++){
                // int check = 0;
                String id2 = tbcart.getModel().getValueAt(i, 0).toString();
                String sql3 = "UPDATE `store` SET `Qty` = ? where ID = '" + id2 + "'";
                pst3 = Conn.prepareStatement(sql3);
                pst3.setString(1, tbcart.getModel().getValueAt(i, 4).toString());
                //pst.setString(8,tbcart.getModel().getValueAt(i, 0).toString());
                
                pst.setString(1,tbcart.getModel().getValueAt(i, 0).toString());
                pst.setString(1,transactionId.getText());
                
                pst.setString(2,tbcart.getModel().getValueAt(i, 1).toString());
                pst.setString(5,tbcart.getModel().getValueAt(i, 5).toString());            
                pst.setString(3,tbcart.getModel().getValueAt(i, 3).toString());
                //pst.setString(3,tbcart.getModel().getValueAt(i, 5).toString());
                pst.setString(4,tbcart.getModel().getValueAt(i, 2).toString());
                //pst.setString(1,namelbl.getText());
                pst.execute();
                pst3.executeUpdate();
            }
            JOptionPane.showMessageDialog(null, "Purchase successful!");
            PickUpPickUp page = new PickUpPickUp();
            page.setVisible(true);
            PickUpPickUp.transactionId.setText(transactionId.getText());
            PickUpPickUp.cost.setText(costtxt.getText());
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }*/
    
    /*public void pickUp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd", Locale.getDefault());
        //String d = sdf.format(pickupDateChooser.getDate());
        //JOptionPane.showMessageDialog(null, d);
        try{
            double pay = Double.parseDouble(paymenttxt.getText())-Double.parseDouble(costtxt.getText());
            double cost = Double.parseDouble(costtxt.getText());
            int rowc = tbcart.getRowCount();
            if(rowc==0){
                JOptionPane.showMessageDialog(null, "Cart is empty");
            }
            else if(paymenttxt.getText().isEmpty()){
                JOptionPane.showMessageDialog(null,"Please enter your payment!");
            }
            else{
                if(pay<0){
                    JOptionPane.showMessageDialog(null,"Sorry your payment is not enough!");
                }
                else{
                    changetxt.setText(Double.toString(pay));
                    JOptionPane.showMessageDialog(null, pay);
                    JOptionPane.showMessageDialog(null, cost);
                }
            }
 
            //String sql = "INSERT INTO `pickup`(`Id`, `Total`, `Date1`, `Date2`) VALUES(?,?,?,?,?,?)";
            //pst = Conn.prepareStatement(sql);
            int roww = tbcart.getRowCount();
            for(int i = 0;i<roww;i++){
                String id2 = tbcart.getModel().getValueAt(i, 0).toString();
                String sql3 = "UPDATE `store` SET `Qty` = ? where ID = '" + id2 + "'";
                pst3 = Conn.prepareStatement(sql3);
                pst3.setString(1, tbcart.getModel().getValueAt(i, 4).toString());
                pst.setString(2,tbcart.getModel().getValueAt(i, 0).toString());
                pst.setString(3,tbcart.getModel().getValueAt(i, 1).toString());
                pst.setString(6,tbcart.getModel().getValueAt(i, 5).toString());            
                pst.setString(4,tbcart.getModel().getValueAt(i, 3).toString());
                pst.setString(5,tbcart.getModel().getValueAt(i, 2).toString());
                pst.setString(1,namelbl.getText());
                //JOptionPane.showMessageDialog(null, id2);
                pst3.executeUpdate();
                
                //st.executeUpdate("INSERT INTO pickup(" + "Id,Total,Date1," + "Date2" + ") VALUES ('" + id2 + "','" + pay + "','" + d + "','" + d + "')");  
            }
            //st.executeUpdate("Insert into pickup(" + "Id,Total," + "Date1" + ") VALUES ('" + paymenttxt.getText() + "','" + costtxt.getText() + "','" + sdf.format(pickupDateChooser.getDate()) + "')");
            JOptionPane.showMessageDialog(null, "Record Created", "System Message", JOptionPane.INFORMATION_MESSAGE);
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }*/
    /*public int search(String id) throws SQLException, ClassNotFoundException
    {
        Class.forName("com.mysql.jdbc.Driver");
        url = "jdbc:mysql://localhost:3306/project";
        connection = DriverManager.getConnection(url, "root", "");
        st1 = connection.createStatement();
        rs22 = st1.executeQuery("Select * From sales2 Where ID= '" + id + "'");
        if (rs22 != null)
            return 1;
        else
            return 0;
    }*/
   
    /*public double getSum(){
        int rowsCount = tbcart.getRowCount();
        double sum = 0;
        for(int i = 0; i < rowsCount; i++){
            sum = sum+Double.parseDouble(tbcart.getValueAt(i, 2).toString());
        }
        return sum;
    }*/
   
    /**
     * Creates new form CustomerAcc
     */
    public PickupdesignAsChangeStatus(){
        initComponents();
        Conn = PickupdesignAsChangeStatus.javaconnect.ConnecrDB();
        Update_table();
        //order_tbl();
        //costtxt.setEditable(false);
        //purchasebtn.setEnabled(false);
        //remlbl.setEnabled(false);
        //clearbtn.setEnabled(false);
        //changetxt.setEnabled(false);
        //costtxt.setEnabled(false);
        //paymenttxt.setEnabled(false);
        //pluss.setEnabled(false);
        //minuss.setEnabled(false);   
        getId();
        //tblmousemove();
    }
    
    private static java.sql.Date getCurrentDate(){
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
    }
    
    private void getId(){
    String sum;
    try{
        String sql = "SELECT max(ID) FROM login_tbl";
        pst = Conn.prepareStatement(sql);
        //pst.setString(1, searchtxt.getText());
        rs= pst.executeQuery();
        while(rs.next()){
            //rs.beforeFirst();
            sum = rs.getString("max(ID)");
            int identification = Integer.parseInt(sum)+1;
            transactionId.setText(Integer.toString(identification));
        }
    }
    catch (Exception e){
        int identification = Integer.parseInt(transactionId.getText())+1;
        transactionId.setText(Integer.toString(identification));
    }   
}
    
    public void getorders(){
        try{
            //int row = tb.getSelectedRow();
            //String tbClick = tb.getModel().getValueAt(row, 0).toString();
            //String sql =  ("Select * From store Where ID = '" + tbClick + "'  ");
            //pst = Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                id = rs.getString("ID");
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void Update_table(){
        try{
            String sql = "select ID, Product_Name, Price from store";
            pst = Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            //tb.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch  (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void NewArr(){
        try{
            String sql = "select ID, Product_Name, Price from new_arrivals";
            //String sql = ("Select * From Category Where id = '" + lb + "'  ");
            pst = Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            //byte[]imagedata = rs.getBytes("image");
            //format = new ImageIcon(imagedata);
            //bglbl.setIcon(format);
            //tb.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private Image fix_it(Image imeg,int w,int h){
        BufferedImage resizedImage=new    
        BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2=resizedImage.createGraphics();
       
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        
        g2.drawImage(imeg,0, 0, w,h,null);
        g2.dispose();
        return resizedImage;
    }
    
    /*public void qq(int multiple,double price, double pricetb){
        double pricemultiple = (double)multiple * price;
        double adjust = pricemultiple - pricetb;
        
        double tot = Double.parseDouble(costtxt.getText());
        double tott = tot + adjust;
        costtxt.setText(Double.toString(tott));
    }*/
    
    /*public void  quantity(int qty1){
        int row = tbcart.getSelectedRow();
        int qty;
        double price = Double.parseDouble(tbcart.getModel().getValueAt(row, 2).toString());
        qty = Integer.parseInt(tbcart.getModel().getValueAt(row, 3).toString());
        
        int add2 = qty + Integer.parseInt(qtytxt.getText())*qty1;
        double add = price + (price/qty)* (Double.parseDouble(qtytxt.getText())*qty1); 
        double tota = (price/qty)* (Double.parseDouble(qtytxt.getText())*qty1);
        double totalcost = tota + Double.parseDouble(costtxt.getText());
        tbcart.getModel().setValueAt(add, row, 2);  
        tbcart.getModel().setValueAt(add2, row, 3);  
        costtxt.setText(Double.toString(totalcost));
        int sto = Integer.parseInt(stocktxt.getText());
        int totalstock = sto-Integer.parseInt(qtytxt.getText())*qty1;
        tbcart.getModel().setValueAt(totalstock, row, 4);
        stocktxt.setText(Integer.toString(totalstock));
    }*/
    
    /*public void  quantity2(int qty1){
        int row = tbcart.getSelectedRow();
        
        double price = Double.parseDouble(tbcart.getModel().getValueAt(row, 2).toString());
        int qty = Integer.parseInt(tbcart.getModel().getValueAt(row, 3).toString());
        double deduct = price - (price/qty)* (Double.parseDouble(qtytxt.getText()))*qty1;
        int deduct2 = qty - (Integer.parseInt(qtytxt.getText()))*qty1;
   
        if(deduct<=0){
            double diff = getSum()- Double.parseDouble(tbcart.getModel().getValueAt(row, 2).toString());
            costtxt.setText(Double.toString(diff));
            ((DefaultTableModel)tbcart.getModel()).removeRow(row);
            stocktxt.setText("");
        }
        else{
            double tota = (price/qty)* (Double.parseDouble(qtytxt.getText()))*qty1;
            double totalcost = Double.parseDouble(costtxt.getText()) - tota; 
            tbcart.getModel().setValueAt(deduct, row, 2);
            tbcart.getModel().setValueAt(deduct2, row, 3);
            int sto = Integer.parseInt(stocktxt.getText());
            int totalstock = sto+Integer.parseInt(qtytxt.getText())*qty1;
            tbcart.getModel().setValueAt(totalstock, row, 4);
            stocktxt.setText(Integer.toString(totalstock));
            costtxt.setText(Double.toString(totalcost));
        }
    }*/

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        transactionId = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<String>();
        jButton3 = new javax.swing.JButton();
        userName = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jDesktopPane1.setBackground(new java.awt.Color(0, 102, 255));

        jLabel5.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Pick Up Date");
        jDesktopPane1.add(jLabel5);
        jLabel5.setBounds(20, 10, 220, 30);

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setLayout(null);

        jPanel5.setBackground(new java.awt.Color(153, 204, 255));
        jPanel5.setFont(new java.awt.Font("Calibri", 1, 48)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 912, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 52, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel5);
        jPanel5.setBounds(40, 20, 912, 52);

        jLabel13.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel13.setText("Id:");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(70, 70, 20, 23);

        transactionId.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        transactionId.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        transactionId.setEnabled(false);
        jPanel1.add(transactionId);
        transactionId.setBounds(240, 70, 170, 23);

        jLabel14.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel14.setText("Status");
        jPanel1.add(jLabel14);
        jLabel14.setBounds(70, 100, 60, 23);

        jButton2.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jButton2.setText("Save");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(270, 160, 170, 25);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pending", "Received" }));
        jPanel1.add(jComboBox1);
        jComboBox1.setBounds(240, 100, 170, 22);

        jButton3.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jButton3.setText("Back");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(60, 160, 170, 25);

        jDesktopPane1.add(jPanel1);
        jPanel1.setBounds(20, 50, 480, 210);

        getContentPane().add(jDesktopPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, 400));

        userName.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        userName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userNameActionPerformed(evt);
            }
        });
        getContentPane().add(userName, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        setSize(new java.awt.Dimension(519, 285));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
      
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            url = "jdbc:mysql://localhost:3306/project";
            connection = DriverManager.getConnection(url, "root", "");
            String prod_id = transactionId.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            //String d = sdf.format(pickupDateChooser.getDate());

            String sql = "update pickup set Status = '" +jComboBox1.getSelectedItem()+ "'  where ID =  '" +prod_id+ "' " ;
            //pst.executeUpdate("Insert into store(" + "ID,Product_Name,description,Price,Qty,Unit," + "Date1" + ") VALUES ('" + productname.getText() + "','" + description.getText() + "','" + price.getText() + "','" + quantity.getText() + "','" + unitCombo.getSelectedItem() + "','" + sdf.format(pickupDateChooser.getDate()) + "')");

            pst = connection.prepareStatement(sql);
            int qty;

            //double pricey = Double.parseDouble(price.getText());

            pst.execute();
            JOptionPane.showMessageDialog(null, "Saved");
        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void userNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userNameActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        AdminTryAsPickUp pik = new AdminTryAsPickUp();
        pik.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PickupdesignAsChangeStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PickupdesignAsChangeStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PickupdesignAsChangeStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PickupdesignAsChangeStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PickupdesignAsChangeStatus().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    public static javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    public static javax.swing.JTextField transactionId;
    private javax.swing.JTextField userName;
    // End of variables declaration//GEN-END:variables
}
