
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
import static pck.PickupdesignNewProduct.url;

/**
 *
 * @author intel
 */
public class PickupdesignViewProduct extends javax.swing.JFrame {

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

    public int search(String id, String name) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        url = "jdbc:mysql://localhost:3306/pos";
        connection = DriverManager.getConnection(url, "root", "");
        st = connection.createStatement();
        rs = st.executeQuery("Select * From admin Where adminID = '" + id + "'  " + "and fname = '" + name + "'");
        if (rs != null) {
            return 1;
        } else {
            return 0;
        }
    }

    public static class javaconnect {

        Connection Conn = null;

        public static Connection ConnecrDB() {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pos", "root", "");
                return Conn;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "error");
            }
            return null;
        }
    }
    
    public PickupdesignViewProduct() {
        //PickupdesignViewProduct(null);
        //getId();
        initComponents();
        Conn = PickupdesignViewProduct.javaconnect.ConnecrDB();
        Update_table();
    }
    
    public PickupdesignViewProduct(String id) {
        initComponents();
        Conn = PickupdesignViewProduct.javaconnect.ConnecrDB();
        Update_table();
        
        this.id = id;
    }

    private void getId() {
        String sum;
        try {
            String sql = "SELECT max(adminID) FROM admin";
            pst = Conn.prepareStatement(sql);
            //pst.setString(1, searchtxt.getText());
            rs = pst.executeQuery();
            while (rs.next()) {
                //rs.beforeFirst();
                sum = rs.getString("max(adminID)");
//                int identification = Integer.parseInt(sum) + 1;
//                productid.setText(Integer.toString(identification));
            }
        } catch (Exception e) {
//            int identification = Integer.parseInt(productid.getText()) + 1;
//            productid.setText(Integer.toString(identification));
        }
    }

    /*public void getorders() {
        try {
            //int row = tb.getSelectedRow();
            //String tbClick = tb.getModel().getValueAt(row, 0).toString();
            //String sql =  ("Select * From store Where ID = '" + tbClick + "'  ");
            //pst = Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                id = rs.getString("adminID");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }*/
    

    private void Update_table() {
        try {
            String sql = "select ProductName = '" +productname.getText()+ "', ProductDescription = '" +description.getText()+ "', "
                    + "ProductPrice = '" +price.getText()+ "', ProductQuantity = '" +quantity.getText()+ "', ProductCategory = '"+category.getText()+"', "
                    + "ProductUnit= '" +unitCombo.getSelectedItem()+"', ProductDate= '" +pickupDateChooser.getDate()+ "' from products" ;
            pst = Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            //tb.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
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
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        productname = new javax.swing.JTextField();
        description = new javax.swing.JTextField();
        price = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        quantity = new javax.swing.JTextField();
        Quantity = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        pickupDateChooser = new com.toedter.calendar.JDateChooser();
        unitCombo = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        Quantity1 = new javax.swing.JLabel();
        category = new javax.swing.JTextField();
        userName = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jDesktopPane1.setBackground(new java.awt.Color(0, 102, 255));

        jLabel5.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("View Product");
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
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel5);
        jPanel5.setBounds(40, 20, 912, 10);

        jLabel14.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel14.setText("Product Name:");
        jPanel1.add(jLabel14);
        jLabel14.setBounds(20, 40, 200, 23);

        jLabel15.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel15.setText("Description:");
        jPanel1.add(jLabel15);
        jLabel15.setBounds(20, 70, 200, 23);

        jButton2.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jButton2.setText("Save");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(270, 270, 170, 25);

        productname.setText(" ");
        productname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productnameActionPerformed(evt);
            }
        });
        jPanel1.add(productname);
        productname.setBounds(240, 30, 230, 30);

        description.setText(" ");
        jPanel1.add(description);
        description.setBounds(240, 60, 230, 30);

        price.setText(" ");
        price.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                priceKeyTyped(evt);
            }
        });
        jPanel1.add(price);
        price.setBounds(240, 90, 230, 30);

        jLabel16.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel16.setText("Price:");
        jPanel1.add(jLabel16);
        jLabel16.setBounds(20, 100, 200, 23);

        quantity.setText(" ");
        quantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                quantityKeyTyped(evt);
            }
        });
        jPanel1.add(quantity);
        quantity.setBounds(240, 120, 230, 30);

        Quantity.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        Quantity.setText("Quantity:");
        jPanel1.add(Quantity);
        Quantity.setBounds(20, 130, 200, 23);

        jLabel17.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel17.setText("Date:");
        jPanel1.add(jLabel17);
        jLabel17.setBounds(20, 220, 130, 23);

        pickupDateChooser.setDateFormatString("yyyy-MM-dd");
        jPanel1.add(pickupDateChooser);
        pickupDateChooser.setBounds(240, 210, 230, 30);

        unitCombo.setBackground(new java.awt.Color(204, 255, 204));
        unitCombo.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        unitCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PCS", "Box", "Bundle" }));
        unitCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unitComboActionPerformed(evt);
            }
        });
        jPanel1.add(unitCombo);
        unitCombo.setBounds(240, 183, 230, 30);

        jLabel18.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel18.setText("Unit:");
        jPanel1.add(jLabel18);
        jLabel18.setBounds(20, 190, 37, 23);

        jButton3.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jButton3.setText("Back");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(50, 270, 170, 25);

        Quantity1.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        Quantity1.setText("Category");
        jPanel1.add(Quantity1);
        Quantity1.setBounds(20, 160, 200, 23);

        category.setText(" ");
        jPanel1.add(category);
        category.setBounds(240, 150, 230, 30);

        jDesktopPane1.add(jPanel1);
        jPanel1.setBounds(20, 50, 480, 320);

        getContentPane().add(jDesktopPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, 400));

        userName.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        userName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userNameActionPerformed(evt);
            }
        });
        getContentPane().add(userName, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        setSize(new java.awt.Dimension(519, 401));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (productname.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Product Name Required!");
        } 
        else if (description.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please add description");
        }
        else if (price.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please add price");
        } 
        else if (quantity.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please add Quantitty");
        }
        else if (category.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please add Category");
        }
        else {
            try {
                url = "jdbc:mysql://localhost:3306/pos";
                connection = DriverManager.getConnection(url, "root", "");
                //String prod_id = productid.getText();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String d = sdf.format(pickupDateChooser.getDate());

                String sql = "UPDATE products set ProductName = '" +productname.getText()+ "', ProductDescription = '" +description.getText()+ "', ProductPrice = '" +price.getText()+ "', ProductQuantity = '" +quantity.getText()+ "', ProductCategory= '"+category.getText()+"', ProductUnit= '" +unitCombo.getSelectedItem()+"', ProductDate= '" +d+ "' WHERE ProductID = " +id  ;
                //pst.executeUpdate("Insert into store(" + "ID,Product_Name,description,Price,Qty,Unit," + "Date1" + ") VALUES ('" + productname.getText() + "','" + description.getText() + "','" + price.getText() + "','" + quantity.getText() + "','" + unitCombo.getSelectedItem() + "','" + sdf.format(pickupDateChooser.getDate()) + "')");

                pst = connection.prepareStatement(sql);

                double pricey = Double.parseDouble(price.getText());

                pst.execute(sql);
                JOptionPane.showMessageDialog(null, "Record Updated");
                this.dispose();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void userNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userNameActionPerformed

    private void unitComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unitComboActionPerformed

    }//GEN-LAST:event_unitComboActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.dispose();
        Admin bal = new Admin();
        bal.setVisible(true);
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void productnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_productnameActionPerformed

    private void priceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_priceKeyTyped
        char character = evt.getKeyChar();
        if (((character < '.') || (character > '9')) && (character != '\b')) {
                    evt.consume();
        }
    }//GEN-LAST:event_priceKeyTyped

    private void quantityKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_quantityKeyTyped
        char character = evt.getKeyChar();
        if (((character < '0') || (character > '9'))&& (character != '\b')) {
                    evt.consume();
        }
    }//GEN-LAST:event_quantityKeyTyped

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
            java.util.logging.Logger.getLogger(PickupdesignViewProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PickupdesignViewProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PickupdesignViewProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PickupdesignViewProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PickupdesignViewProduct().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Quantity;
    private javax.swing.JLabel Quantity1;
    public static javax.swing.JTextField category;
    public static javax.swing.JTextField description;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    public static com.toedter.calendar.JDateChooser pickupDateChooser;
    public static javax.swing.JTextField price;
    public static javax.swing.JTextField productname;
    public static javax.swing.JTextField quantity;
    public static javax.swing.JComboBox unitCombo;
    private javax.swing.JTextField userName;
    // End of variables declaration//GEN-END:variables
}
