
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
public class POS extends javax.swing.JFrame {
    static Connection connection;
    static String url;
    static Statement st1;
    static ResultSet rs22;
    int count = 0;
    Connection Conn = null;
    ResultSet rs = null;
    ResultSet rs1 = null;
    ResultSet rs2 = null;
    ResultSet rs4 = null;
    PreparedStatement pst5 = null;
    PreparedStatement pst4 = null;
    PreparedStatement pst3 = null;
    PreparedStatement pst2 = null;
    PreparedStatement pst1 = null;
    PreparedStatement pst = null;
    PreparedStatement st = null;
    private ImageIcon format = null;
    String id = "";
    
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
    
    public int tableClick2(){
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
    }
    
    public String tableClick(){
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
    }
    
    public void getOrder(){
        try{
            String sql = "INSERT INTO `sales2`(`Customer`, `ID`, `Product_Name`, `Qty`, `Price`, `Date`) VALUES(?,?,?,?,?,?)";
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
                pst.setString(2,tbcart.getModel().getValueAt(i, 0).toString());
                pst.setString(3,tbcart.getModel().getValueAt(i, 1).toString());
                pst.setString(6,tbcart.getModel().getValueAt(i, 5).toString());            
                pst.setString(4,tbcart.getModel().getValueAt(i, 3).toString());
                //pst.setString(3,tbcart.getModel().getValueAt(i, 5).toString());
                pst.setString(5,tbcart.getModel().getValueAt(i, 2).toString());
                pst.setString(1,namelbl.getText());
                pst.execute();
                pst3.executeUpdate();
            }
            JOptionPane.showMessageDialog(null, "Purchase successful!");
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void pickUp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd", Locale.getDefault());
        String d = sdf.format(pickupDateChooser.getDate());
        JOptionPane.showMessageDialog(null, d);
        try{
            double pay = Double.parseDouble(paymenttxt.getText())-Double.parseDouble(costtxt.getText());
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
                }
            }
 
            String sql = "INSERT INTO `pickup`(`Id`, `Total`, `Date1`, `Date2`) VALUES(?,?,?,?,?,?)";
            pst = Conn.prepareStatement(sql);
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
                JOptionPane.showMessageDialog(null, id2);
                pst3.executeUpdate();
                
                st.executeUpdate("INSERT INTO pickup(" + "Id,Total,Date1," + "Date2" + ") VALUES ('" + id2 + "','" + pay + "','" + d + "','" + d + "')");
                    JOptionPane.showMessageDialog(null, "Record Created", "System Message", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
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
   
    public double getSum(){
        int rowsCount = tbcart.getRowCount();
        double sum = 0;
        for(int i = 0; i < rowsCount; i++){
            sum = sum+Double.parseDouble(tbcart.getValueAt(i, 2).toString());
        }
        return sum;
    }
   
    /**
     * Creates new form CustomerAcc
     */
    public POS(){
        initComponents();
        Conn = POS.javaconnect.ConnecrDB();
        Update_table();
        //order_tbl();
        costtxt.setEditable(false);
        purchasebtn.setEnabled(false);
        remlbl.setEnabled(false);
        clearbtn.setEnabled(false);
        changetxt.setEnabled(false);
        costtxt.setEnabled(false);
        paymenttxt.setEnabled(false);
        pluss.setEnabled(false);
        minuss.setEnabled(false);            
        //tblmousemove();
        getId();
    }
    
    private void getId(){
    String sum;
    try{
        String sql = "SELECT max(ID) FROM sales2";
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
    
    private static java.sql.Date getCurrentDate(){
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
    }
    
    public void getorders(){
        try{
            int row = tb.getSelectedRow();
            String tbClick = tb.getModel().getValueAt(row, 0).toString();
            String sql =  ("Select * From store Where ID = '" + tbClick + "'  ");
            pst = Conn.prepareStatement(sql);
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
            tb.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch  (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void criticalProduct(){
    try{
        String sql = ("SELECT `ID`, `Product_Name` as \"Product Name\", `Price`,Qty as Quantity, image FROM store WHERE Qty <= '10'" );
        pst = Conn.prepareStatement(sql);
        rs = pst.executeQuery();
        //while(rs.next()){
        //rs.first();
        //byte[]imagedata = rs.getBytes("image");
        //format = new ImageIcon(imagedata);
        //bglbl.setIcon(format);
        tb.setModel(DbUtils.resultSetToTableModel(rs));
    } 
    catch (Exception e){
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
            tb.setModel(DbUtils.resultSetToTableModel(rs));
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
    
    public void qq(int multiple,double price, double pricetb){
        double pricemultiple = (double)multiple * price;
        double adjust = pricemultiple - pricetb;
        
        double tot = Double.parseDouble(costtxt.getText());
        double tott = tot + adjust;
        costtxt.setText(Double.toString(tott));
    }
    
    public void  quantity(int qty1){
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
    }
    
    public void  quantity2(int qty1){
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
        namelbl = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        out = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        searchtxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        catcbx = new javax.swing.JComboBox();
        jButton6 = new javax.swing.JButton();
        cbxsearch = new javax.swing.JComboBox();
        sp1 = new javax.swing.JScrollPane();
        tb = new javax.swing.JTable();
        sp2 = new javax.swing.JScrollPane();
        tbcart = new javax.swing.JTable();
        addbtn = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        cbxunit = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        qtytxt = new javax.swing.JTextField();
        clearbtn = new javax.swing.JButton();
        purchasebtn = new javax.swing.JButton();
        remlbl = new javax.swing.JLabel();
        paymenttxt = new javax.swing.JTextField();
        costtxt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        pluss = new javax.swing.JButton();
        minuss = new javax.swing.JButton();
        stocktxt = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        changetxt = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        pickupDateChooser = new com.toedter.calendar.JDateChooser();
        transactionId = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jDesktopPane1.setBackground(new java.awt.Color(0, 102, 255));

        namelbl.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        namelbl.setForeground(new java.awt.Color(255, 255, 255));
        namelbl.setText("Admin");
        jDesktopPane1.add(namelbl);
        namelbl.setBounds(170, 10, 220, 30);

        jLabel5.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Welcome ");
        jDesktopPane1.add(jLabel5);
        jLabel5.setBounds(20, 10, 160, 30);

        out.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/out1.png"))); // NOI18N
        out.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                outMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                outMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                outMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                outMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                outMouseReleased(evt);
            }
        });
        jDesktopPane1.add(out);
        out.setBounds(840, 10, 150, 40);

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setLayout(null);

        jPanel5.setBackground(new java.awt.Color(153, 204, 255));
        jPanel5.setFont(new java.awt.Font("Calibri", 1, 48)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel1.setText("Search by:");

        searchtxt.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        searchtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchtxtActionPerformed(evt);
            }
        });
        searchtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchtxtKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel2.setText("Filter:");

        catcbx.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        catcbx.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All Products", "Critical Products" }));
        catcbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                catcbxActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jButton6.setText("View Selected Item");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        cbxsearch.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        cbxsearch.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Product Name", "ID", "Price" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbxsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(catcbx, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(104, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(catcbx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6)
                    .addComponent(jLabel1)
                    .addComponent(cbxsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel5);
        jPanel5.setBounds(40, 12, 912, 60);

        sp1.setBackground(new java.awt.Color(204, 204, 255));
        sp1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tb.setBackground(new java.awt.Color(153, 255, 255));
        tb.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        tb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        tb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMouseClicked(evt);
            }
        });
        sp1.setViewportView(tb);

        jPanel1.add(sp1);
        sp1.setBounds(40, 106, 904, 190);

        sp2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        sp2.setToolTipText("Click Cart to add/remove Item");
        sp2.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N

        tbcart.setBackground(new java.awt.Color(153, 255, 255));
        tbcart.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        tbcart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Product Name", "Price", "Qty", "In stock", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbcart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbcartMouseClicked(evt);
            }
        });
        sp2.setViewportView(tbcart);
        if (tbcart.getColumnModel().getColumnCount() > 0) {
            tbcart.getColumnModel().getColumn(0).setResizable(false);
            tbcart.getColumnModel().getColumn(1).setResizable(false);
            tbcart.getColumnModel().getColumn(2).setResizable(false);
            tbcart.getColumnModel().getColumn(3).setResizable(false);
            tbcart.getColumnModel().getColumn(4).setResizable(false);
            tbcart.getColumnModel().getColumn(5).setResizable(false);
        }

        jPanel1.add(sp2);
        sp2.setBounds(40, 496, 904, 180);

        addbtn.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        addbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cart.png"))); // NOI18N
        addbtn.setToolTipText("Add to Cart");
        addbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addbtnActionPerformed(evt);
            }
        });
        jPanel1.add(addbtn);
        addbtn.setBounds(460, 310, 70, 57);

        jLabel6.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel6.setText("Unit:");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(70, 370, 37, 23);

        cbxunit.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        cbxunit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PCS", "Box", "Bundle" }));
        jPanel1.add(cbxunit);
        cbxunit.setBounds(210, 370, 100, 23);

        jLabel7.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel7.setText("Quantity:");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(70, 400, 71, 23);

        qtytxt.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        qtytxt.setText("1");
        jPanel1.add(qtytxt);
        qtytxt.setBounds(210, 400, 100, 23);

        clearbtn.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        clearbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cartclear.png"))); // NOI18N
        clearbtn.setToolTipText("Clear Cart");
        clearbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbtnActionPerformed(evt);
            }
        });
        jPanel1.add(clearbtn);
        clearbtn.setBounds(540, 310, 70, 59);

        purchasebtn.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        purchasebtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pay.png"))); // NOI18N
        purchasebtn.setText("Purchase");
        purchasebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                purchasebtnActionPerformed(evt);
            }
        });
        jPanel1.add(purchasebtn);
        purchasebtn.setBounds(650, 300, 150, 50);

        remlbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Button-Delete-icon.png"))); // NOI18N
        remlbl.setToolTipText("Remove Selected Item");
        remlbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                remlblMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                remlblMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                remlblMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                remlblMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                remlblMouseReleased(evt);
            }
        });
        jPanel1.add(remlbl);
        remlbl.setBounds(470, 390, 32, 32);

        paymenttxt.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        paymenttxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(paymenttxt);
        paymenttxt.setBounds(650, 390, 150, 23);

        costtxt.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        costtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        costtxt.setEnabled(false);
        jPanel1.add(costtxt);
        costtxt.setBounds(650, 360, 150, 23);

        jLabel8.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel8.setText("Payment");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(810, 390, 68, 23);

        jLabel9.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel9.setText("Total");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(810, 360, 38, 23);

        jLabel10.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel10.setText("Pick Up");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(810, 450, 57, 23);

        pluss.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        pluss.setToolTipText("Add quantity");
        pluss.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plussActionPerformed(evt);
            }
        });
        jPanel1.add(pluss);
        pluss.setBounds(360, 330, 36, 39);

        minuss.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/red.png"))); // NOI18N
        minuss.setToolTipText("Reduce Quantity");
        minuss.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minussActionPerformed(evt);
            }
        });
        jPanel1.add(minuss);
        minuss.setBounds(360, 390, 36, 41);

        stocktxt.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        stocktxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        stocktxt.setEnabled(false);
        jPanel1.add(stocktxt);
        stocktxt.setBounds(210, 337, 100, 23);

        jLabel11.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel11.setText("Stock Available:");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(70, 340, 120, 23);

        changetxt.setEditable(false);
        changetxt.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        changetxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        changetxt.setEnabled(false);
        jPanel1.add(changetxt);
        changetxt.setBounds(650, 420, 150, 23);

        jLabel12.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel12.setText("Change");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(810, 420, 57, 23);
        jPanel1.add(pickupDateChooser);
        pickupDateChooser.setBounds(650, 450, 150, 22);

        transactionId.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        transactionId.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        transactionId.setEnabled(false);
        jPanel1.add(transactionId);
        transactionId.setBounds(210, 310, 100, 23);

        jLabel13.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel13.setText("Transaction Id:");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(70, 310, 112, 23);

        jDesktopPane1.add(jPanel1);
        jPanel1.setBounds(20, 50, 980, 690);

        getContentPane().add(jDesktopPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1020, 770));

        setSize(new java.awt.Dimension(1022, 768));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addbtnActionPerformed
        int multiple;
        if(tb.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(null, "No item selected!");
        }
        else if(tbcart.getRowCount()==0){
            changetxt.setEnabled(true);
            costtxt.setEnabled(true);
            paymenttxt.setEnabled(true);
            purchasebtn.setEnabled(true);
            clearbtn.setEnabled(true);
            remlbl.setEnabled(true);
            
            try{
                int row = tb.getSelectedRow();
                String tbclk = (tb.getModel().getValueAt(row, 0).toString());
                //String tc = (tbcart.getModel().getValueAt(row, 0).toString());t
                tbcart.getModel();
                String sql =  ("select ID, Product_Name, Price, Qty from store Where ID = '" + tbclk + "'  ");
                //pst1 = Conn.prepareStatement(sql2);            
                pst = Conn.prepareStatement(sql);
                rs = pst.executeQuery();
                int qty;
                //String s = cbxunit.getSelectedItem().toString();               
                //rs1 = pst1.executeQuery();
                //tbcart.setModel(DbUtils.resultSetToTableModel(rs1));
                while(rs.next()){
                    int avtxt = Integer.parseInt(stocktxt.getText());
                    if(cbxunit.getSelectedIndex()==1){
                        if(avtxt<15){
                            JOptionPane.showMessageDialog(null, "Sorry this item is limited!");
                        }
                        else{
                            qty  =Integer.parseInt(qtytxt.getText());
                            multiple = qty * 15;
                            double pricemultiple = (double)multiple * Double.parseDouble(rs.getString("Price"));
                            int sto = avtxt-multiple;
                            stocktxt.setText(Integer.toString(sto));
                            DefaultTableModel dtm = (DefaultTableModel)tbcart.getModel();
                            dtm.addRow(new Object[] { rs.getString("ID"), rs.getString("Product_Name"),pricemultiple, multiple,sto,getCurrentDate() });
                        }
                    }
                    else if(cbxunit.getSelectedIndex()==2){
                        if(avtxt<10){
                            JOptionPane.showMessageDialog(null, "Sorry this item is limited!");
                        }
                        else{  
                            qty  =Integer.parseInt(qtytxt.getText());
                            multiple = qty * 10;
                            double pricemultiple = (double)multiple * Double.parseDouble(rs.getString("Price"));
                            int sto = avtxt-multiple;
                            stocktxt.setText(Integer.toString(sto));
                            DefaultTableModel dtm = (DefaultTableModel)tbcart.getModel();
                            dtm.addRow(new Object[] { rs.getString("ID"), rs.getString("Product_Name"),pricemultiple, multiple,sto,getCurrentDate() });
                        }
                    }
                    else if(cbxunit.getSelectedIndex()==0){
                        if(avtxt<=0){
                            JOptionPane.showMessageDialog(null, "Sorry this item is out of stock!");
                        }
                        else{ 
                            multiple  =Integer.parseInt(qtytxt.getText());
                            double pricemultiple = (double)multiple * Double.parseDouble(rs.getString("Price"));
                            int sto = avtxt-multiple;
                            stocktxt.setText(Integer.toString(sto));
                            DefaultTableModel dtm = (DefaultTableModel)tbcart.getModel();
                            dtm.addRow(new Object[] { rs.getString("ID"), rs.getString("Product_Name"),pricemultiple, multiple,sto,getCurrentDate() });
                        }
                    }
                }
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }   
        }
        
        else if(tbcart.getRowCount()!=0){
            int roww = tbcart.getRowCount();
            Object[] content = new Object[roww];
            for (int i = 0; i < roww; i++) {
                content[i] = Integer.parseInt(tbcart.getValueAt(i, 0).toString());
            }
            int value_to_find= Integer.parseInt(tb.getModel().getValueAt(tb.getSelectedRow(), 0).toString()) ;
            boolean exist = Arrays.asList(content).contains(value_to_find);
            if (exist){
                JOptionPane.showMessageDialog(null, "Item already in cart!");
            }
            else{
                changetxt.setEnabled(true);
                costtxt.setEnabled(true);
                paymenttxt.setEnabled(true);
                purchasebtn.setEnabled(true);
                clearbtn.setEnabled(true);
                remlbl.setEnabled(true);
                
                try{
                    int row = tb.getSelectedRow();
                    String tbclk = (tb.getModel().getValueAt(row, 0).toString());
                    //String tc = (tbcart.getModel().getValueAt(row, 0).toString());t
                    tbcart.getModel();
                    String sql =  ("select ID, Product_Name, Price, Qty from store Where ID = '" + tbclk + "'  ");
                    //pst1 = Conn.prepareStatement(sql2);
                    pst = Conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                    int qty;
                    //String s = cbxunit.getSelectedItem().toString();
                    //rs1 = pst1.executeQuery();
                    //tbcart.setModel(DbUtils.resultSetToTableModel(rs1));
                    while(rs.next()){
                        int avtxt = Integer.parseInt(stocktxt.getText());
                        if(cbxunit.getSelectedIndex()==1){
                            if(avtxt<15){
                                JOptionPane.showMessageDialog(null, "Sorry this item is limited!");
                            }
                            else{
                                qty  =Integer.parseInt(qtytxt.getText());
                                multiple = qty * 15;
                                double pricemultiple = (double)multiple * Double.parseDouble(rs.getString("Price"));
                                int sto = avtxt-multiple;
                                stocktxt.setText(Integer.toString(sto));
                                DefaultTableModel dtm = (DefaultTableModel)tbcart.getModel();
                                dtm.addRow(new Object[] { rs.getString("ID"), rs.getString("Product_Name"),pricemultiple, multiple,sto,getCurrentDate() });
                            }
                        }
                        else if(cbxunit.getSelectedIndex()==2){
                            if(avtxt<10){
                                JOptionPane.showMessageDialog(null, "Sorry this item is limited!");
                            }
                            else{
                                qty  =Integer.parseInt(qtytxt.getText());
                                multiple = qty * 10;
                                double pricemultiple = (double)multiple * Double.parseDouble(rs.getString("Price"));
                                int sto = avtxt-multiple;
                                stocktxt.setText(Integer.toString(sto));
                                DefaultTableModel dtm = (DefaultTableModel)tbcart.getModel();
                                dtm.addRow(new Object[] { rs.getString("ID"), rs.getString("Product_Name"),pricemultiple, multiple,sto,getCurrentDate() });
                            }
                        }
                        else if(cbxunit.getSelectedIndex()==0){
                            if(avtxt<=0){
                                JOptionPane.showMessageDialog(null, "Sorry this item is out of stock!");
                            }
                            else{ 
                                multiple  =Integer.parseInt(qtytxt.getText());
                                double pricemultiple = (double)multiple * Double.parseDouble(rs.getString("Price"));
                                int sto = avtxt-multiple;
                                stocktxt.setText(Integer.toString(sto));
                                DefaultTableModel dtm = (DefaultTableModel)tbcart.getModel();
                                dtm.addRow(new Object[] { rs.getString("ID"), rs.getString("Product_Name"),pricemultiple, multiple,sto,getCurrentDate() });
                            }
                        }
                    }
                }
                catch (Exception e){
                    JOptionPane.showMessageDialog(null, e);
                } 
            }
        }
        String ss = Double.toString(getSum());
        costtxt.setText(ss);
        //getOrder();     
    }//GEN-LAST:event_addbtnActionPerformed
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if(tb.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(null, "Please choose an item to view!");
        }
        else{  
            try{
                ViewProduct vp = new ViewProduct();
                vp.setVisible(true);
                String id = tableClick();
                String sql = ("Select * From store Where ID = '" + id + "'  ");
                pst = Conn.prepareStatement(sql);
                rs = pst.executeQuery();
                while(rs.next()){
                    ViewProduct.txta1.setText(rs.getString("description"));
                    ImageIcon icon=new ImageIcon(rs.getString("imageurl"));
                    Image icoo = icon.getImage();
                    format=new ImageIcon(fix_it(icoo,ViewProduct.lbl1.getWidth(),ViewProduct.lbl1.getHeight()));
                    ViewProduct.lbl1.setIcon(format);
                }
            }
            catch  (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void clearbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbtnActionPerformed
        costtxt.setText("");
        costtxt.setEnabled(false);
        paymenttxt.setEnabled(false);
        remlbl.setEnabled(false);
        purchasebtn.setEnabled(false);
        changetxt.setEnabled(false);
        ((DefaultTableModel)tbcart.getModel()).setRowCount(0);
    }//GEN-LAST:event_clearbtnActionPerformed

    private void catcbxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_catcbxActionPerformed
        String products = (String)catcbx.getSelectedItem();
        try{
            if(products.equals("All Products")){
                Update_table();
            }
            else if(products.equals("Critical Products")){
                criticalProduct();
            }
            else{
                String sql ="SELECT `ID`, `Product_Name`, `Price` FROM `store` WHERE category = '" + products + "'";
                pst = Conn.prepareStatement(sql);
                rs = pst.executeQuery();
                tb.setModel(DbUtils.resultSetToTableModel(rs));
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_catcbxActionPerformed
      
    private void searchtxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchtxtKeyReleased
        if(cbxsearch.getSelectedIndex()==0){
            try{
                String sql = "Select `ID`, `Product_Name`, `Price` FROM `store` Where Product_Name = ? ";
                pst = Conn.prepareStatement(sql);
                pst.setString(1, searchtxt.getText());
                rs = pst.executeQuery();
                tb.setModel(DbUtils.resultSetToTableModel(rs)); 
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        else if(cbxsearch.getSelectedIndex()==1){
            try{
                String sql = "Select `ID`, `Product_Name`, `Price` FROM `store` Where ID = ? ";
                pst = Conn.prepareStatement(sql);
                pst.setString(1, searchtxt.getText());
                rs = pst.executeQuery();
                tb.setModel(DbUtils.resultSetToTableModel(rs)); 
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        else if(cbxsearch.getSelectedIndex()==2){
            try{
                String sql = "Select `ID`, `Product_Name`, `Price` FROM `store` Where Price = ? ";
                pst = Conn.prepareStatement(sql);
                pst.setString(1, searchtxt.getText());
                rs = pst.executeQuery();
                tb.setModel(DbUtils.resultSetToTableModel(rs)); 
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
       }
    }//GEN-LAST:event_searchtxtKeyReleased

    private void searchtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchtxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchtxtActionPerformed

    private void purchasebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_purchasebtnActionPerformed
        int rowc = tbcart.getRowCount();
        if(rowc==0){
            JOptionPane.showMessageDialog(null, "Cart is empty");
        }
        else if(paymenttxt.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Please enter your payment!");
        }
        else{
            double pay = Double.parseDouble(paymenttxt.getText())-Double.parseDouble(costtxt.getText());
            if(pay<0){
                JOptionPane.showMessageDialog(null,"Sorry your payment is not enough!");
            }
            else{
                changetxt.setText(Double.toString(pay));
                getOrder();
                pickUp();
                stocktxt.setText("");
                costtxt.setText("");
                paymenttxt.setText("");
                changetxt.setText("");
                costtxt.setText("");
                costtxt.setEnabled(false);
                paymenttxt.setEnabled(false);
                remlbl.setEnabled(false);
                purchasebtn.setEnabled(false);
                changetxt.setEnabled(false);
                pickupDateChooser.setEnabled(false);
                ((DefaultTableModel)tbcart.getModel()).setRowCount(0);
            }
        }       
    }//GEN-LAST:event_purchasebtnActionPerformed

    private void remlblMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_remlblMouseEntered
        ImageIcon img = new ImageIcon(getClass().getResource("/images/delhover.png"));
        remlbl.setIcon(img);
    }//GEN-LAST:event_remlblMouseEntered

    private void remlblMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_remlblMouseExited
        ImageIcon img = new ImageIcon(getClass().getResource("/images/Button-Delete-icon.png"));
        remlbl.setIcon(img);
    }//GEN-LAST:event_remlblMouseExited

    private void remlblMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_remlblMousePressed
        ImageIcon img = new ImageIcon(getClass().getResource("/images/delext.png"));
        remlbl.setIcon(img);
    }//GEN-LAST:event_remlblMousePressed

    private void remlblMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_remlblMouseReleased
        ImageIcon img = new ImageIcon(getClass().getResource("/images/Button-Delete-icon.png"));
        remlbl.setIcon(img);
    }//GEN-LAST:event_remlblMouseReleased

    private void remlblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_remlblMouseClicked
        int rowc = tbcart.getRowCount();
        if(rowc==0){
            JOptionPane.showMessageDialog(null, "Cart is empty");
        }
        else if(tbcart.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(null, "Please choose an item to remove!");
        }
        else{
            stocktxt.setText("");
            int row = tbcart.getSelectedRow();
            double re = Double.parseDouble(costtxt.getText()) - Double.parseDouble(tbcart.getModel().getValueAt(row, 3).toString());
            costtxt.setText(Double.toString(re));
            ((DefaultTableModel)tbcart.getModel()).removeRow(tbcart.getSelectedRow());
        }        
    }//GEN-LAST:event_remlblMouseClicked

    private void outMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outMouseClicked
        LoginLogin logg = new LoginLogin();
        logg.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_outMouseClicked

    private void outMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outMouseEntered
        ImageIcon img = new ImageIcon(getClass().getResource("/images/out2.png"));
        out.setIcon(img);
    }//GEN-LAST:event_outMouseEntered

    private void outMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outMouseExited
        ImageIcon img = new ImageIcon(getClass().getResource("/images/out1.png"));
        out.setIcon(img);
    }//GEN-LAST:event_outMouseExited

    private void outMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outMousePressed
        ImageIcon img = new ImageIcon(getClass().getResource("/images/out3.png"));
        out.setIcon(img);
    }//GEN-LAST:event_outMousePressed

    private void outMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outMouseReleased
        ImageIcon img = new ImageIcon(getClass().getResource("/images/out1.png"));
        out.setIcon(img);
    }//GEN-LAST:event_outMouseReleased

    private void plussActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plussActionPerformed
        int row = tbcart.getSelectedRow();
        if(tbcart.getRowCount()==0){
            JOptionPane.showMessageDialog(null, "Cart is empty!");
        }
        else if(tbcart.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(null, "No item selected!" + "\n" + "Please choose from cart.");
        }
        else{
            int avtxt = Integer.parseInt(stocktxt.getText());
            if(cbxunit.getSelectedIndex()==1){
                if(avtxt<15){
                    JOptionPane.showMessageDialog(null, "Sorry this item is limited!" + "\n"+ "Try to add item per bundle or per piece");
                }
                else{
                    quantity(15);}
            }
        
            else if(cbxunit.getSelectedIndex()==2){
                if(avtxt<10){
                    JOptionPane.showMessageDialog(null, "Sorry this item is limited!"+ "\n"+ "Try to add item per piece");
                }
                else{
                    quantity(10);
                }
            }
        
            else if(cbxunit.getSelectedIndex()==0){
                if(avtxt<=0){
                    JOptionPane.showMessageDialog(null, "Sorry this item is out of stock!");
                }
                else{ 
                    quantity(1);
                }
            }
        } 
    }//GEN-LAST:event_plussActionPerformed

    private void minussActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minussActionPerformed
        int row = tbcart.getSelectedRow();
        if(tbcart.getRowCount()==0){
            JOptionPane.showMessageDialog(null, "Cart is empty!");
        }
        else if(tbcart.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(null, "No item selected!" + "\n" + "Please choose from cart.");
        }
        else{
            if(cbxunit.getSelectedIndex()==1){
                quantity2(15);}
            else if(cbxunit.getSelectedIndex()==2){
                quantity2(10);
            }
            else if(cbxunit.getSelectedIndex()==0){
                quantity2(1);
            }
        }         
    }//GEN-LAST:event_minussActionPerformed

    private void tbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMouseClicked
        pluss.setEnabled(false);
        minuss.setEnabled(false);
        addbtn.setEnabled(true);
        int row = tb.getSelectedRow();
        if(tbcart.getRowCount()==0){
            try{
                String tbclk = (tb.getModel().getValueAt(row, 0).toString());
                String sql =  ("select Qty from store Where ID = '" + tbclk + "'  ");
                //pst1 = Conn.prepareStatement(sql2);
                pst = Conn.prepareStatement(sql);
                rs = pst.executeQuery();
                while(rs.next())
                    stocktxt.setText(rs.getString("Qty"));
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null,e);   
            }
        }
        else{
            int roww = tbcart.getRowCount();
            Object[] content = new Object[roww];
            for (int i = 0; i < roww; i++){
                content[i] = Integer.parseInt(tbcart.getValueAt(i, 0).toString());
            }
            int value_to_find= Integer.parseInt(tb.getModel().getValueAt(tb.getSelectedRow(), 0).toString()) ;
            boolean exist = Arrays.asList(content).contains(value_to_find);
            if (exist){
                JOptionPane.showMessageDialog(null, "Item already in cart!");
                addbtn.setEnabled(false);
            } 
            else{
                try{
                    String tbclk = (tb.getModel().getValueAt(row, 0).toString());
                    String sql =  ("select Qty from store Where ID = '" + tbclk + "'  ");
                    //pst1 = Conn.prepareStatement(sql2);
                    pst = Conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                    while(rs.next())
                        stocktxt.setText(rs.getString("Qty"));
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null,e);
                }
            }
        }   
    }//GEN-LAST:event_tbMouseClicked

    private void tbcartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbcartMouseClicked
        pluss.setEnabled(true);
        minuss.setEnabled(true); 
        addbtn.setEnabled(false);
        int row = tbcart.getSelectedRow();
        stocktxt.setText(tbcart.getModel().getValueAt(row, 4).toString());
    }//GEN-LAST:event_tbcartMouseClicked

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
            java.util.logging.Logger.getLogger(POS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(POS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(POS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(POS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new POS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addbtn;
    private javax.swing.JComboBox catcbx;
    private javax.swing.JComboBox cbxsearch;
    private javax.swing.JComboBox cbxunit;
    private javax.swing.JTextField changetxt;
    private javax.swing.JButton clearbtn;
    private javax.swing.JTextField costtxt;
    private javax.swing.JButton jButton6;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JButton minuss;
    public static javax.swing.JLabel namelbl;
    private javax.swing.JLabel out;
    private javax.swing.JTextField paymenttxt;
    private com.toedter.calendar.JDateChooser pickupDateChooser;
    private javax.swing.JButton pluss;
    private javax.swing.JButton purchasebtn;
    private javax.swing.JTextField qtytxt;
    private javax.swing.JLabel remlbl;
    private javax.swing.JTextField searchtxt;
    private javax.swing.JScrollPane sp1;
    private javax.swing.JScrollPane sp2;
    private javax.swing.JTextField stocktxt;
    private javax.swing.JTable tb;
    private javax.swing.JTable tbcart;
    public javax.swing.JTextField transactionId;
    // End of variables declaration//GEN-END:variables
}
