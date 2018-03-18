/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck;

import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Color;
import net.proteanit.sql.DbUtils;
import java.sql.*;
import javax.swing.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
//import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
//import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
//import java.util.List;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import static javax.swing.ScrollPaneConstants.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import static pck.CustomerAcc.costtxt;
import static pck.SignupAs3position.st;
import static pck.SignupAs3position.url;


/**
 *
 * @author intel
 */
public class Admin extends javax.swing.JFrame {
Connection Conn = null;
static Connection connection;
static Statement stt;
static Statement st2;
ResultSet rs = null;
PreparedStatement pst = null;
PreparedStatement pst3 = null;
PreparedStatement st = null;
private ImageIcon format = null;
String filename = null;
int s =0;
byte[] ima = null;
byte[] ima1 = null;
String id = "";
String m1,m2;
String d,d1;
//ImageIcon img;
public static class javaconnect{
    Connection Conn = null;
    public static Connection ConnecrDB(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pos","root","");
            return Conn;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "error");
        }
        return null;
    }
}

public int search(String id, String name) throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        url = "jdbc:mysql://localhost:3306/pos";
        connection = DriverManager.getConnection(url, "root", "");
        stt = connection.createStatement();
        st2 = connection.createStatement();
        rs = st.executeQuery("Select * From admin Where adminID= '" + id + "'  " + "and fname = '" + name + "'");
        if (rs != null)
            return 1;
        else
            return 0;
    }



public void populateJTable(){
        MyQuery mq = new MyQuery();
        ArrayList<Product2> list = mq.BindTable();
        String[] columnName = {"ProductID", "ProductName", "ProductDescription",
            "ProductCategory", "ProductQuantity", "ProductPrice", "ProductDate"};
        Object[][] rows = new Object[list.size()][7];
        for(int i = 0; i < list.size(); i++){
            rows[i][0] = list.get(i).getProductId();
            rows[i][1] = list.get(i).getName();
            rows[i][2] = list.get(i).getDescription();
            rows[i][3] = list.get(i).getCategory();
            rows[i][4] = list.get(i).getQuantity();
            rows[i][5] = list.get(i).getPrice();
            rows[i][6] = list.get(i).getdate();
        }
        
        TheModel model = new TheModel(rows, columnName);
        tb.setModel(model);
        alignLeft(tb,0);
        alignLeft(tb,1);
        alignLeft(tb,2);
        alignLeft(tb,3);       
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        JTableHeader header = tb.getTableHeader();
        leftRenderer.setForeground(tb.getForeground());
        leftRenderer.setBackground(header.getBackground());
        leftRenderer.setFont(tb.getFont());
        leftRenderer.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        header.getColumnModel().getColumn(0).setHeaderRenderer(leftRenderer);
        header.getColumnModel().getColumn(1).setHeaderRenderer(leftRenderer);
        header.getColumnModel().getColumn(2).setHeaderRenderer(leftRenderer);
        header.getColumnModel().getColumn(3).setHeaderRenderer(leftRenderer);
        header.getColumnModel().getColumn(4).setHeaderRenderer(leftRenderer);
        header.getColumnModel().getColumn(5).setHeaderRenderer(leftRenderer);
        header.getColumnModel().getColumn(6).setHeaderRenderer(leftRenderer);
        setForeground(tb.getTableHeader().getForeground());
        setBackground(tb.getTableHeader().getBackground());
        //tb.setDefaultRenderer(Object.class, new MultiLineCellRenderer());
}

private void alignLeft(JTable table, int column){
    DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
    leftRenderer.setHorizontalAlignment(JLabel.LEFT);
    table.getColumnModel().getColumn(column).setCellRenderer(leftRenderer);
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
   
    

private void byproduct(){
    try{
        // int roww = salestbl.getRowCount();
        //qtytxt.setText(Integer.toString(roww));
        String sql = "SELECT `SalesID`, `ProductID` , sum(`SalesQuantity`) as 'Total Quantity', sum(`SalesPrice`) as 'Total Price', SalesDate  FROM sales  where SalesDate ='" + getCurrentDate()+ "' group by SalesID";
        pst = Conn.prepareStatement(sql);
        rs= pst.executeQuery();
        salestbl.setModel(DbUtils.resultSetToTableModel(rs));
        //String id2 = salestbl.getModel().getValueAt(i, 0).toString();
        String s = Double.toString(getSum());
        txtsales.setText(s);
    }
    catch(Exception e){}
}
    
public void getOrder(){
        try{
            String sql = "INSERT INTO `sales`(`SalesID`, `ProductID`, `SalesQuantity`, `SalesPrice`, `SalesDate`) VALUES(?,?,?,?,?)";
            //String sql2 = "INSERT INTO `sales2`(`ID`, `Product_Name`, `Price`, `Qty`, `Date`) VALUES (?,?,?,?,?)";
            pst = Conn.prepareStatement(sql);
            int roww = tbcart.getRowCount();
            //qtytxt.setText(Integer.toString(roww));
            for(int i = 0;i<roww;i++){
                // int check = 0;
                String id2 = tbcart.getModel().getValueAt(i, 0).toString();
                String sql3 = "UPDATE `products` SET `ProductQuantity` = ? from products where ProductID = '" + id2 + "'";
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
            //PickUpPickUp page = new PickUpPickUp();
            //page.setVisible(true);
            //PickUpPickUp.transactionId.setText(transactionId.getText());
            //PickUpPickUp.cost.setText(costtxt.getText());
            
            PickupdesignAsChangeStatus page = new PickupdesignAsChangeStatus();
            page.setVisible(true);
            PickupdesignAsChangeStatus.transactionId.setText(transactionId.getText());
            PickupdesignAsChangeStatus.jComboBox1.setSelectedItem(costtxt.getText());
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    } 

private void jtab(){
    try{
        ImageIcon img = new ImageIcon(getClass().getResource("/images/inven.png"));      
        tabpane1.setIconAt(0, img);
        ImageIcon img1 = new ImageIcon(getClass().getResource("/images/sales.png"));      
        tabpane1.setIconAt(3, img1);
        ImageIcon img2 = new ImageIcon(getClass().getResource("/images/member.png"));      
        tabpane1.setIconAt(2, img2);
        ImageIcon img3 = new ImageIcon(getClass().getResource("/images/add.png"));      
        tabpane1.setIconAt(1, img3);
        ImageIcon img4 = new ImageIcon(getClass().getResource("/images/member.png"));      
        tabpane1.setIconAt(4, img4);
    }
    catch (Exception e){
        JOptionPane.showMessageDialog(null, e);
    }
} 

public void uniqueId(String sum){
    ArrayList<String> identif = new ArrayList<>(); 
    List list = new ArrayList();
    try{
        //pst.setString(1, searchtxt.getText());
        Statement state = Conn.createStatement();
        ResultSet rs11 = state.executeQuery("SELECT ProductID FROM products");
        if ( rs11.next() ){
            identif.add(rs11.getString("ProductID"));
            list.add(rs11.getString(1));
        }
        Object[] content = new Object[list.size()];
        identif.toArray();
        for(int i =0;i<identif.size();i++){
            content[i] = identif.toArray();
            JOptionPane.showMessageDialog(null, Integer.toString(list.size()));
        }
        boolean exist = Arrays.asList(content).contains(sum);
        if (exist){
            String identification = sum+"A";
            txtid.setText(identification);
        }
        else{
            int identification = Integer.parseInt(sum)+1;
            txtid.setText(Integer.toString(identification));
        }
    }
    catch(Exception e){}
}

private void getId(){
    String sum;
    try{
        String sql = "SELECT max(ProductID) FROM products";
        pst = Conn.prepareStatement(sql);
        //pst.setString(1, searchtxt.getText());
        rs= pst.executeQuery();
        while(rs.next()){
            //rs.beforeFirst();
            sum = rs.getString("max(ProductID)");
            int identification = Integer.parseInt(sum)+1;
            txtid.setText(Integer.toString(identification));
        }
    }
    catch (Exception e){
       int identification = Integer.parseInt(txtid.getText())+1;
       txtid.setText(Integer.toString(identification));
    }   
}

/*private void getId2(){
    try{
        String sql = "SELECT max(ID) FROM store ";
        pst = Conn.prepareStatement(sql);
                //pst.setString(1, searchtxt.getText());
               rs= pst.executeQuery();
while(rs.next()){
    //rs.beforeFirst();
     String sum = rs.getString("max(ID)");
     
     int identification = Integer.parseInt(sum)+1;
    ll.setText(Integer.toString(identification));
    
    
}
    }catch(Exception e){
        
    }
}*/

public String tableClick(){
    try{
        int row = tb.getSelectedRow();
        String tbClick = tb.getModel().getValueAt(row, 0).toString();
        String sql =  "Select `ProductID`, `ProductName`, `ProductDescription`, `ProductPrice`, `ProductQuantity`, `ProductCategory`, `ProductUnit`, `ProductDate` FROM `products` WHERE ProductID = '"+ tbClick +"'";
        pst = Conn.prepareStatement(sql);
        rs = pst.executeQuery();
        while(rs.next()){
            id = rs.getString("ProductID");             
        }
        return id;
    }
    catch (Exception e){
        JOptionPane.showMessageDialog(null, e);
    } 
    return null;
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

private void Member(){
    try{
        String sql = "select AdminID as \"AdminID\", fname as \"Username\", Password as \"Password\" from admin";
        //String sql = ("Select * From Category Where id = '" + lb + "'  ");
        pst = Conn.prepareStatement(sql);
        rs = pst.executeQuery();
        while(rs.next()){
            //byte[]imagedata = rs.getBytes("image");
            //format = new ImageIcon(imagedata);
            //bglbl.setIcon(format);
            memberTable.setModel(DbUtils.resultSetToTableModel(rs));
        }
    }
    catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
    }
}

private void Pickup(){
    try{
        String sql = "select SalesID, ProductID as \"ProductID\", SalesCategory as \"SalesCategory\", SalesPrice as \"Total Price\", SalesQuantity as \"Quantity\", PickUpDate as \"Pick up date\" from sales";
        //String sql ="SELECT `Id`, `Total` as \"Total Price\", Date1 as Pick-up date FROM `pickup` WHERE category = '" + products + "'";
        pst = Conn.prepareStatement(sql);
        rs = pst.executeQuery();
        pickupTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
    }
}

private void Update_table(){
    try{
        String sql = "SELECT `ProductID` as \"Product ID\", `ProductName` as \"Product Name\", `ProductDescription`, "
                        + "ProductCategory as ProductCategory, ProductQuantity, ProductPrice, ProductDate "
                        + "FROM `products`";
        //String sql = ("Select * From Category Where id = '" + lb + "'  ");
        pst = Conn.prepareStatement(sql);
        rs = pst.executeQuery();
        //byte[]imagedata = rs.getBytes("image");
        //format = new ImageIcon(imagedata);
        //bglbl.setIcon(format);
        tb.setModel(DbUtils.resultSetToTableModel(rs));
    }
    catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
    }
}

private void Update_table_members(){
    try{
        String sql = "select AdminID, fname, Password from admin";
        //String sql = ("Select * From Category Where id = '" + lb + "'  ");
        pst = Conn.prepareStatement(sql);
        rs = pst.executeQuery();
        //byte[]imagedata = rs.getBytes("image");
        //format = new ImageIcon(imagedata);
        //bglbl.setIcon(format);
        memberTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
    }
}

public double getSum(){
    int rowsCount = salestbl.getRowCount();
    double sum = 0;
    for(int i = 0; i < rowsCount; i++){
        sum = sum+Double.parseDouble(salestbl.getValueAt(i, 3).toString());
    }
    return sum;
}

private void getVal(){
    try{
        String sql = ("SELECT `ProductName` as \"Product Name\", `AdminID`, `SupplierID`, `ProductDescription`, "
                        + "ProductCategory as ProductCategory, ProductQuantity, ProductPrice, ProductDate "
                        + "FROM `products`" );
        pst = Conn.prepareStatement(sql);
        rs = pst.executeQuery();
        //while(rs.next()){
        //rs.first();
        //byte[]imagedata = rs.getBytes("image");
        //format = new ImageIcon(imagedata);
        //bglbl.setIcon(format);
        salestbl.setModel(DbUtils.resultSetToTableModel(rs));
        String s = Double.toString(getSum());
        txtsales.setText(s);
    }
    catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
    }
}

private void criticalProduct(){
    try{
        String sql = ("SELECT `ProductID` as \"Product ID\", `ProductName` as \"Product Name\", `ProductDescription`, "
                        + "ProductCategory as ProductCategory, ProductQuantity, ProductPrice, ProductDate "
                        + "FROM `products` WHERE ProductQuantity <= '10'" );
        pst = Conn.prepareStatement(sql);
        rs = pst.executeQuery();
        tb.setModel(DbUtils.resultSetToTableModel(rs));
    } 
    catch (Exception e){
        JOptionPane.showMessageDialog(null, e);
    }
}

private static java.sql.Date getCurrentDate() {
    java.util.Date today = new java.util.Date();
    return new java.sql.Date(today.getTime());
}

    /**
     * Creates new form Admin
     */

public Admin(){
    initComponents();
    populateJTable();
    Conn = Admin.javaconnect.ConnecrDB();
    tom2.getJCalendar().setMaxSelectableDate(getCurrentDate());
    ((JTextFieldDateEditor)tom2.getDateEditor()).setEditable(false);
    fromm1.getJCalendar().setMaxSelectableDate(getCurrentDate());
    ((JTextFieldDateEditor)tom2.getDateEditor()).setEditable(false);
    byproduct();
    jtab();
    Update_table();
    Member();
    Pickup();
//    getId();
//    ((JTextField)datetxt.getDateEditor().getUiComponent()).setText(getCurrentDate().toString());
    tabpane1.setBackgroundAt(0,Color.BLUE);
    //tabpane1.get
    jScrollPane7.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);
    jScrollPane7.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
}
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton3 = new javax.swing.JButton();
        dpane = new javax.swing.JDesktopPane();
        jLabel4 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        out = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabpane1 = new javax.swing.JTabbedPane();
        inventoryPanel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        searchtxt = new javax.swing.JTextField();
        allproductsComboBox = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        delete = new javax.swing.JLabel();
        urltxt = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tb = new javax.swing.JTable();
        AddProduct = new javax.swing.JButton();
        ViewSelectedItem = new javax.swing.JButton();
        customersPanel1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        pickupTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        txtsales = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cbxall = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        salem2m = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        salestbl = new javax.swing.JTable();
        salesm2m = new javax.swing.JButton();
        tom2 = new com.toedter.calendar.JDateChooser();
        fromm1 = new com.toedter.calendar.JDateChooser();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        searchtxt1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        catcbx = new javax.swing.JComboBox();
        cbxsearch1 = new javax.swing.JComboBox();
        sp1 = new javax.swing.JScrollPane();
        tb1 = new javax.swing.JTable();
        sp2 = new javax.swing.JScrollPane();
        tbcart = new javax.swing.JTable();
        addbtn = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        cbxunit = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        qtytxt = new javax.swing.JTextField();
        clearbtn = new javax.swing.JButton();
        purchasebtn = new javax.swing.JButton();
        remlbl = new javax.swing.JLabel();
        paymenttxt = new javax.swing.JTextField();
        costtxt = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        pluss = new javax.swing.JButton();
        minuss = new javax.swing.JButton();
        stocktxt = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        changetxt = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        transactionId = new javax.swing.JTextField();
        inventoryPanel1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        searchtxt2 = new javax.swing.JTextField();
        allproductsComboBox1 = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        cbxsearch2 = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        delete1 = new javax.swing.JLabel();
        urltxt1 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tb2 = new javax.swing.JTable();
        ViewSelectedItem1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        userId = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        userName = new javax.swing.JTextField();
        userPassword2 = new javax.swing.JPasswordField();
        userPassword = new javax.swing.JPasswordField();
        comboPosition = new javax.swing.JComboBox();
        jLabel24 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        customersPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        memberTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        jButton3.setText("jButton3");

        dpane.setBackground(new java.awt.Color(153, 255, 255));
        dpane.setPreferredSize(new java.awt.Dimension(1020, 700));

        javax.swing.GroupLayout dpaneLayout = new javax.swing.GroupLayout(dpane);
        dpane.setLayout(dpaneLayout);
        dpaneLayout.setHorizontalGroup(
            dpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1210, Short.MAX_VALUE)
        );
        dpaneLayout.setVerticalGroup(
            dpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );

        jLabel4.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel4.setText("Product ID");

        txtid.setEditable(false);
        txtid.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 204, 255));
        setMinimumSize(new java.awt.Dimension(1050, 700));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(null);

        jDesktopPane1.setBackground(new java.awt.Color(51, 51, 255));

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
        out.setBounds(880, 10, 150, 40);

        jScrollPane7.setBackground(new java.awt.Color(153, 204, 255));
        jScrollPane7.setEnabled(false);
        jScrollPane7.setWheelScrollingEnabled(false);

        tabpane1.setBackground(new java.awt.Color(153, 255, 255));
        tabpane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tabpane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        tabpane1.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        tabpane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tabpane1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tabpane1MouseExited(evt);
            }
        });

        inventoryPanel.setBackground(new java.awt.Color(153, 204, 255));
        inventoryPanel.setLayout(null);

        jPanel5.setBackground(new java.awt.Color(153, 204, 255));

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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                searchtxtKeyTyped(evt);
            }
        });

        allproductsComboBox.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        allproductsComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All Products", "Critical Products" }));
        allproductsComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allproductsComboBoxActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel2.setText("Filter:");

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel1.setText("Search:");

        delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/del.png"))); // NOI18N
        delete.setToolTipText("Remove");
        delete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                deleteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                deleteMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                deleteMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                deleteMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(allproductsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                .addComponent(delete)
                .addGap(23, 23, 23))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(searchtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(allproductsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)))
                    .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        inventoryPanel.add(jPanel5);
        jPanel5.setBounds(20, 60, 800, 40);
        inventoryPanel.add(urltxt);
        urltxt.setBounds(540, 530, 220, 20);

        tb.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tb.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        tb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Product Name", "Price", "Qty", "Unit", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb.setToolTipText("");
        tb.setRowMargin(2);
        tb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tbMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tbMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbMouseReleased(evt);
            }
        });
        jScrollPane11.setViewportView(tb);

        inventoryPanel.add(jScrollPane11);
        jScrollPane11.setBounds(10, 110, 820, 440);

        AddProduct.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        AddProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        AddProduct.setText("Add Product");
        AddProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddProductActionPerformed(evt);
            }
        });
        inventoryPanel.add(AddProduct);
        AddProduct.setBounds(20, 20, 150, 40);

        ViewSelectedItem.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        ViewSelectedItem.setText("View Selected Item");
        ViewSelectedItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewSelectedItemActionPerformed(evt);
            }
        });
        inventoryPanel.add(ViewSelectedItem);
        ViewSelectedItem.setBounds(180, 20, 150, 40);

        tabpane1.addTab("Inventory       ", inventoryPanel);

        customersPanel1.setBackground(new java.awt.Color(153, 204, 255));

        jScrollPane5.setBackground(new java.awt.Color(153, 255, 255));
        jScrollPane5.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        pickupTable.setBackground(new java.awt.Color(153, 255, 255));
        pickupTable.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        pickupTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(pickupTable);

        javax.swing.GroupLayout customersPanel1Layout = new javax.swing.GroupLayout(customersPanel1);
        customersPanel1.setLayout(customersPanel1Layout);
        customersPanel1Layout.setHorizontalGroup(
            customersPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customersPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 761, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1348, Short.MAX_VALUE))
        );
        customersPanel1Layout.setVerticalGroup(
            customersPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customersPanel1Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(392, Short.MAX_VALUE))
        );

        tabpane1.addTab("PickUp", customersPanel1);

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));
        jPanel3.setLayout(null);

        txtsales.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        txtsales.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtsales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsalesActionPerformed(evt);
            }
        });
        jPanel3.add(txtsales);
        txtsales.setBounds(90, 30, 149, 30);

        jLabel13.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI18N
        jLabel13.setText("Total Sales");
        jPanel3.add(jLabel13);
        jLabel13.setBounds(10, 35, 81, 20);

        cbxall.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI18N
        cbxall.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All", "Today" }));
        cbxall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxallActionPerformed(evt);
            }
        });
        jPanel3.add(cbxall);
        cbxall.setBounds(250, 30, 70, 26);

        jLabel14.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI18N
        jLabel14.setText("From:");
        jPanel3.add(jLabel14);
        jLabel14.setBounds(340, 30, 39, 30);

        jLabel15.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI18N
        jLabel15.setText("To:");
        jPanel3.add(jLabel15);
        jLabel15.setBounds(570, 30, 21, 30);

        salem2m.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        salem2m.setText("Ok");
        salem2m.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salem2mActionPerformed(evt);
            }
        });
        jPanel3.add(salem2m);
        salem2m.setBounds(1893, 31, 53, 25);

        jScrollPane1.setBackground(new java.awt.Color(153, 204, 255));

        salestbl.setBackground(new java.awt.Color(153, 255, 255));
        salestbl.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        salestbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(salestbl);

        jPanel3.add(jScrollPane1);
        jScrollPane1.setBounds(10, 82, 790, 510);

        salesm2m.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI18N
        salesm2m.setText("Go");
        salesm2m.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salesm2mActionPerformed(evt);
            }
        });
        jPanel3.add(salesm2m);
        salesm2m.setBounds(780, 30, 51, 29);
        jPanel3.add(tom2);
        tom2.setBounds(600, 30, 170, 30);
        jPanel3.add(fromm1);
        fromm1.setBounds(390, 30, 170, 30);

        tabpane1.addTab("Sales               ", jPanel3);

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setLayout(null);

        jPanel6.setBackground(new java.awt.Color(153, 204, 255));
        jPanel6.setFont(new java.awt.Font("Calibri", 1, 48)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel6.setText("Search by:");

        searchtxt1.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        searchtxt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchtxt1ActionPerformed(evt);
            }
        });
        searchtxt1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchtxt1KeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel7.setText("Filter:");

        catcbx.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        catcbx.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All Products", "Critical Products" }));
        catcbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                catcbxActionPerformed(evt);
            }
        });

        cbxsearch1.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        cbxsearch1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Product Name", "ID", "Price" }));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbxsearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchtxt1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(catcbx, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 282, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchtxt1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(catcbx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(cbxsearch1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1.add(jPanel6);
        jPanel6.setBounds(40, 20, 904, 50);

        sp1.setBackground(new java.awt.Color(204, 204, 255));
        sp1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tb1.setBackground(new java.awt.Color(153, 255, 255));
        tb1.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        tb1.setModel(new javax.swing.table.DefaultTableModel(
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
        tb1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        tb1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb1MouseClicked(evt);
            }
        });
        sp1.setViewportView(tb1);

        jPanel1.add(sp1);
        sp1.setBounds(40, 80, 760, 216);

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

        jPanel1.add(sp2);
        sp2.setBounds(40, 450, 760, 140);

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

        jLabel8.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel8.setText("Unit:");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(70, 370, 37, 23);

        cbxunit.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        cbxunit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PCS", "Box", "Bundle" }));
        jPanel1.add(cbxunit);
        cbxunit.setBounds(210, 370, 100, 23);

        jLabel9.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel9.setText("Quantity:");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(70, 400, 71, 23);

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
        paymenttxt.setBounds(650, 390, 90, 23);

        costtxt.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        costtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        costtxt.setEnabled(false);
        jPanel1.add(costtxt);
        costtxt.setBounds(650, 360, 90, 23);

        jLabel10.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel10.setText("Payment");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(750, 390, 68, 23);

        jLabel11.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel11.setText("Total");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(750, 360, 38, 23);

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

        jLabel12.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel12.setText("Stock Available:");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(70, 340, 120, 23);

        changetxt.setEditable(false);
        changetxt.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        changetxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        changetxt.setEnabled(false);
        jPanel1.add(changetxt);
        changetxt.setBounds(650, 420, 90, 23);

        jLabel16.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel16.setText("Change");
        jPanel1.add(jLabel16);
        jLabel16.setBounds(750, 420, 57, 23);

        jLabel17.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel17.setText("Transaction Id:");
        jPanel1.add(jLabel17);
        jLabel17.setBounds(70, 310, 112, 23);

        transactionId.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        transactionId.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        transactionId.setEnabled(false);
        jPanel1.add(transactionId);
        transactionId.setBounds(210, 310, 100, 23);

        tabpane1.addTab("Point of Sales", jPanel1);

        inventoryPanel1.setBackground(new java.awt.Color(153, 204, 255));
        inventoryPanel1.setLayout(null);

        jPanel7.setBackground(new java.awt.Color(153, 204, 255));

        searchtxt2.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        searchtxt2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchtxt2KeyReleased(evt);
            }
        });

        allproductsComboBox1.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        allproductsComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All Products", "Critical Products" }));
        allproductsComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allproductsComboBox1ActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel18.setText("Filter:");

        cbxsearch2.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        cbxsearch2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Product Name", "ID", "Price" }));
        cbxsearch2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxsearch2ActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel19.setText("Search:");

        delete1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/del.png"))); // NOI18N
        delete1.setToolTipText("Remove");
        delete1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                delete1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                delete1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                delete1MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                delete1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                delete1MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchtxt2, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbxsearch2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(allproductsComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                .addComponent(delete1)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(searchtxt2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)
                            .addComponent(allproductsComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxsearch2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)))
                    .addComponent(delete1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        inventoryPanel1.add(jPanel7);
        jPanel7.setBounds(20, 60, 800, 40);
        inventoryPanel1.add(urltxt1);
        urltxt1.setBounds(540, 530, 220, 20);

        tb2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tb2.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        tb2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tb2.setToolTipText("");
        tb2.setRowMargin(2);
        tb2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb2MouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(tb2);

        inventoryPanel1.add(jScrollPane12);
        jScrollPane12.setBounds(40, 110, 820, 470);

        ViewSelectedItem1.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        ViewSelectedItem1.setText("Edit Status");
        ViewSelectedItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewSelectedItem1ActionPerformed(evt);
            }
        });
        inventoryPanel1.add(ViewSelectedItem1);
        ViewSelectedItem1.setBounds(20, 20, 150, 40);

        tabpane1.addTab("PickUps", inventoryPanel1);

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));
        jPanel4.setLayout(null);

        jLabel20.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel20.setText("Id:");
        jPanel4.add(jLabel20);
        jLabel20.setBounds(210, 100, 20, 23);

        userId.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        userId.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        userId.setEnabled(false);
        jPanel4.add(userId);
        userId.setBounds(380, 100, 170, 23);

        jLabel21.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel21.setText("Username:");
        jPanel4.add(jLabel21);
        jLabel21.setBounds(210, 130, 82, 23);

        jLabel22.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel22.setText("Password:");
        jPanel4.add(jLabel22);
        jLabel22.setBounds(210, 160, 77, 23);

        jLabel23.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel23.setText("Re-enter Password:");
        jPanel4.add(jLabel23);
        jLabel23.setBounds(210, 190, 146, 23);

        userName.setText(" ");
        jPanel4.add(userName);
        userName.setBounds(380, 130, 170, 20);
        jPanel4.add(userPassword2);
        userPassword2.setBounds(380, 190, 170, 20);
        jPanel4.add(userPassword);
        userPassword.setBounds(380, 160, 170, 20);

        comboPosition.setBackground(new java.awt.Color(204, 255, 204));
        comboPosition.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        comboPosition.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Admin", "SalesPerson", "WarehouseManager" }));
        comboPosition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPositionActionPerformed(evt);
            }
        });
        jPanel4.add(comboPosition);
        comboPosition.setBounds(380, 220, 170, 23);

        jLabel24.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel24.setText("Position");
        jPanel4.add(jLabel24);
        jLabel24.setBounds(210, 220, 140, 20);

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton5.setText("Register");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton5);
        jButton5.setBounds(320, 270, 110, 25);

        tabpane1.addTab("Register", jPanel4);

        customersPanel.setBackground(new java.awt.Color(153, 204, 255));

        jScrollPane2.setBackground(new java.awt.Color(153, 255, 255));
        jScrollPane2.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        memberTable.setBackground(new java.awt.Color(153, 255, 255));
        memberTable.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        memberTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(memberTable);

        jButton1.setFont(new java.awt.Font("Calibri Light", 1, 14)); // NOI18N
        jButton1.setText("Delete");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout customersPanelLayout = new javax.swing.GroupLayout(customersPanel);
        customersPanel.setLayout(customersPanelLayout);
        customersPanelLayout.setHorizontalGroup(
            customersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customersPanelLayout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(customersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 735, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(1348, Short.MAX_VALUE))
        );
        customersPanelLayout.setVerticalGroup(
            customersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customersPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(490, Short.MAX_VALUE))
        );

        tabpane1.addTab("Accounts", customersPanel);

        jScrollPane7.setViewportView(tabpane1);

        jDesktopPane1.add(jScrollPane7);
        jScrollPane7.setBounds(20, 50, 1000, 630);

        getContentPane().add(jDesktopPane1);
        jDesktopPane1.setBounds(0, 0, 1050, 710);

        setSize(new java.awt.Dimension(1050, 700));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    private void allproductsComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allproductsComboBoxActionPerformed
        String products = (String)allproductsComboBox.getSelectedItem();
        try{
            if(products.equals("All Products")){
                Update_table();
            }
            else if(products.equals("Critical Products")){
                criticalProduct();
            }
            else{
                String sql ="SELECT `ProductID` as \"Product ID\" `ProductName` as \"Product Name\", `AdminID`, `SupplierID`, `ProductDescription`, "
                        + "ProductCategory as ProductCategory, ProductQuantity, ProductPrice, ProductDate "
                        + "FROM `products` WHERE ProductCategory = '" + products + "'";
                pst = Conn.prepareStatement(sql);
                rs = pst.executeQuery();
                tb.setModel(DbUtils.resultSetToTableModel(rs));
            }
        }

        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_allproductsComboBoxActionPerformed

    private void searchtxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchtxtKeyReleased
        try{
            String sql = "SELECT ProductID, `ProductName`, `ProductDescription`, ProductCategory, ProductQuantity, ProductPrice, ProductDate FROM `products` Where ProductName LIKE '%" + searchtxt.getText() + "%' OR ProductID LIKE '%" + searchtxt.getText() + "%' OR ProductPrice LIKE '%" + searchtxt.getText() + "%' OR ProductDescription LIKE '%" + searchtxt.getText() + "%' OR ProductQuantity LIKE '%" + searchtxt.getText() + "%' OR ProductCategory LIKE '%" + searchtxt.getText() + "%'";
            pst = Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            tb.setModel(DbUtils.resultSetToTableModel(rs));
            }
            catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_searchtxtKeyReleased

    private void salem2mActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salem2mActionPerformed
        
    }//GEN-LAST:event_salem2mActionPerformed

    private void cbxallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxallActionPerformed
        try{
            int pro = cbxall.getSelectedIndex();
            if(pro==0){
                byproduct();
            }
            else if(pro==1){
                String sql = "SELECT `SalesID`, `ProductID` , sum(`SalesQuantity`) as 'Total Quantity', sum(`SalesPrice`) as 'Total Price', SalesDate  FROM sales where SalesDate ='" + getCurrentDate()+ "' group by SalesID";
                pst = Conn.prepareStatement(sql);
                rs = pst.executeQuery();
                //while(rs.next()){
                //rs.first();
                //byte[]imagedata = rs.getBytes("image");
                //format = new ImageIcon(imagedata);
                //bglbl.setIcon(format);
                salestbl.setModel(DbUtils.resultSetToTableModel(rs));
                double sumtbl = getSum();
                String su = Double.toString(sumtbl);
                txtsales.setText(su);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_cbxallActionPerformed

    private void deleteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteMouseReleased
        ImageIcon img = new ImageIcon(getClass().getResource("/images/del.png"));
        delete.setIcon(img);
    }//GEN-LAST:event_deleteMouseReleased

    private void deleteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteMousePressed
        ImageIcon img = new ImageIcon(getClass().getResource("/images/del3.png"));
        delete.setIcon(img);
    }//GEN-LAST:event_deleteMousePressed

    private void deleteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteMouseExited
        ImageIcon img = new ImageIcon(getClass().getResource("/images/del.png"));
        delete.setIcon(img);
    }//GEN-LAST:event_deleteMouseExited

    private void deleteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteMouseEntered
        ImageIcon img = new ImageIcon(getClass().getResource("/images/del2.png"));
        delete.setIcon(img);
    }//GEN-LAST:event_deleteMouseEntered

    private void deleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteMouseClicked
        int row = tb.getSelectedRow();
        String tbClick = tb.getModel().getValueAt(row, 0).toString();

        try{
            String sql = "delete from products where ProductID = '" + tbClick + "'";
            int delete = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this item?","Delete", JOptionPane.YES_NO_OPTION);
            if(delete == JOptionPane.YES_OPTION){
            pst = Conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Deleted");}
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        Update_table();
//        getId();
    }//GEN-LAST:event_deleteMouseClicked

    private void tabpane1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabpane1MouseEntered




    }//GEN-LAST:event_tabpane1MouseEntered

    private void tabpane1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabpane1MouseExited
        ImageIcon img = new ImageIcon(getClass().getResource("/images/inven.png"));
        tabpane1.setIconAt(0, img);
        ImageIcon img1 = new ImageIcon(getClass().getResource("/images/sales.png"));
        tabpane1.setIconAt(1, img1);
    }//GEN-LAST:event_tabpane1MouseExited

    private void salesm2mActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salesm2mActionPerformed
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd", Locale.getDefault());
        String d = sdf.format(fromm1.getDate());
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-M-dd", Locale.getDefault());
        String d1 = sdf1.format(tom2.getDate());
        SimpleDateFormat mon = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat mon2 = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat day = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat day2 = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat yr = new SimpleDateFormat("yyyy", Locale.getDefault());
        SimpleDateFormat yr2 = new SimpleDateFormat("yyyy", Locale.getDefault());
        int m = Integer.parseInt(mon.format(fromm1.getDate()));
        int m2 =Integer.parseInt(mon2.format(tom2.getDate()));
        int dayy = Integer.parseInt(day.format(fromm1.getDate()));
        int dayy2 =Integer.parseInt(day2.format(tom2.getDate()));
        int y = Integer.parseInt(yr.format(fromm1.getDate()));
        int y2 =Integer.parseInt(yr2.format(tom2.getDate()));
        String temp ="";
     
     
        //t1.setText(Integer.toString^(d));
        //t2.setText(Integer.toString(d1));
        /* i
        else
        datee();
        }*/
        try{
        //"SELECT `Customer`, `ID`, `Product_Name`, `Qty`, `Unit`, `Price`, `Date` FROM `sales`"
            if(y==y2){
                if(m2<m){
                    temp = d;
                    d = d1;
                    d1 = temp;
                }
                else if(m==m2){
                    if(dayy2<dayy){
                        temp = d;
                        temp = d;
                        d = d1;
                        d1 = temp;
                    }
                }
            }
           
            String sql = ("SELECT  `SalesID`, `ProductID`, sum(`SalesQuantity`) as Total Quantity, "
                    + "sum(`SalesPrice`) as Total Price, `SalesDate` FROM sales where SalesDate between "
                    + "'" + d+ "' and'" + d1+ "' group by SalesID");
            pst = Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            salestbl.setModel(DbUtils.resultSetToTableModel(rs));
            int rowsCount = salestbl.getRowCount();
            double sum = 0;
            for(int i = 0; i < rowsCount; i++){
                sum = sum+Double.parseDouble(salestbl.getValueAt(i, 3).toString());
            }
            txtsales.setText(Double.toString(sum));
        }
        catch  (Exception e){
                JOptionPane.showMessageDialog(null, e);
        }  
    }//GEN-LAST:event_salesm2mActionPerformed

    private void tom2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tom2KeyReleased
      
    
    }//GEN-LAST:event_tom2KeyReleased

    private void tom2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tom2KeyPressed
       
    
    }//GEN-LAST:event_tom2KeyPressed

    private void tbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMouseClicked
        int row = tb.getSelectedRow();
        try{
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd", Locale.getDefault());
            //String d = sdf.format(pickupDateChooser.getDate());
        
        
        /*    String tbClick = tb.getModel().getValueAt(row, 0).toString();
            String sql = ("Select description,imageurl From store Where ID = '" + tbClick + "'  ");
            pst = Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                urltxt.setText(rs.getString("imageurl"));
            }*/
            
//            PickupdesignViewProduct.productid.setText(rs.getString("ProductID"));
            PickupdesignViewProduct.productname.setText(rs.getString("ProductName"));
            PickupdesignViewProduct.description.setText(rs.getString("ProductDescription"));
            PickupdesignViewProduct.price.setText(rs.getString("ProductPrice"));
            PickupdesignViewProduct.quantity.setText(rs.getString("ProductQuantity"));
            
            //PickupdesignViewProduct.unitCombo.setSelectedItem(unitCombo.getSelectedItem().toString());
            //PickupdesignViewProduct.productname.setText(costtxt.getText());
        }
        catch(Exception e){}
    }//GEN-LAST:event_tbMouseClicked

    private void AddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddProductActionPerformed
        PickupdesignNewProduct page = new PickupdesignNewProduct();
        page.setVisible(true);   
        //this.setVisible(false);
    }//GEN-LAST:event_AddProductActionPerformed

    private void ViewSelectedItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViewSelectedItemActionPerformed
        if(tb.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(null, "Please choose an item to view!");
        }
        else{
            try{
                //ViewProduct vp = new ViewProduct();
                //vp.setVisible(true);
                String productid = tableClick();
                PickupdesignViewProduct page = new PickupdesignViewProduct(productid);
                page.setVisible(true);
                String sql = "SELECT `ProductName`, `ProductDescription`, `ProductPrice`, `ProductQuantity`, `ProductCategory`, `ProductUnit`, `ProductDate` FROM `products` WHERE ProductID = '"+ productid +"'";
                pst = Conn.prepareStatement(sql);
               // pst.setString(0, id);
                rs = pst.executeQuery();
                while(rs.next()){
                    //ViewProduct.txta1.setText(rs.getString("description"));
                    //ImageIcon icon=new ImageIcon(rs.getString("imageurl"));
                    //Image icoo = icon.getImage();
                    //format=new ImageIcon(fix_it(icoo,ViewProduct.lbl1.getWidth(),ViewProduct.lbl1.getHeight()));
                    //ViewProduct.lbl1.setIcon(format);
                    PickupdesignViewProduct.productname.setText(rs.getString("ProductName"));
                    PickupdesignViewProduct.description.setText(rs.getString("ProductDescription"));
                    PickupdesignViewProduct.price.setText(rs.getString("ProductPrice"));
                    PickupdesignViewProduct.quantity.setText(rs.getString("ProductQuantity"));                  
                    PickupdesignViewProduct.category.setText(rs.getString("ProductCategory"));
                    PickupdesignViewProduct.unitCombo.setSelectedItem(rs.getString("ProductUnit"));
                }
                
            }
            catch  (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        
        
        /*int row = tb.getSelectedRow();        PickupdesignViewProduct
        try{
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd", Locale.getDefault());
            //String d = sdf.format(pickupDateChooser.getDate());
        
        
            String tbClick = tb.getModel().getValueAt(row, 0).toString();
            String sql = ("Select description,imageurl From store Where ID = '" + tbClick + "'  ");
            pst = Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                pname.setText(tb.getModel().getValueAt(row, 1).toString());
                des.setText(rs.getString("description"));
                priceTextField.setText(tb.getModel().getValueAt(row, 2).toString());
                addstockTextField.setText(tb.getModel().getValueAt(row, 3).toString());
                urltxt.setText(rs.getString("imageurl"));
            }
            
            PickupdesignViewProduct.productid.setText(rs.getString("ID"));
            PickupdesignViewProduct.productname.setText(rs.getString("Product_Name"));
            PickupdesignViewProduct.description.setText(rs.getString("description"));
            PickupdesignViewProduct.price.setText(rs.getString("Price"));
            PickupdesignViewProduct.quantity.setText(rs.getString("Qty"));
            
            PickupdesignViewProduct page = new PickupdesignViewProduct();
            page.setVisible(true);
            this.setVisible(false);
            
            //PickupdesignViewProduct.unitCombo.setSelectedItem(unitCombo.getSelectedItem().toString());
            //PickupdesignViewProduct.productname.setText(costtxt.getText());
        }
        catch(Exception e){}*/
        
        
    }//GEN-LAST:event_ViewSelectedItemActionPerformed

    private void outMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outMouseReleased
        ImageIcon img = new ImageIcon(getClass().getResource("/images/out1.png"));
        out.setIcon(img);
    }//GEN-LAST:event_outMouseReleased

    private void outMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outMousePressed
        ImageIcon img = new ImageIcon(getClass().getResource("/images/out3.png"));
        out.setIcon(img);
    }//GEN-LAST:event_outMousePressed

    private void outMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outMouseExited
        ImageIcon img = new ImageIcon(getClass().getResource("/images/out1.png"));
        out.setIcon(img);
    }//GEN-LAST:event_outMouseExited

    private void outMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outMouseEntered
        ImageIcon img = new ImageIcon(getClass().getResource("/images/out2.png"));
        out.setIcon(img);
    }//GEN-LAST:event_outMouseEntered

    private void outMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outMouseClicked
        LoginLogin logg = new LoginLogin();
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to sign out?");
        if(choice == 0){
            logg.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_outMouseClicked

        
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        int row = memberTable.getSelectedRow();
        String tbClick = memberTable.getModel().getValueAt(row, 0).toString();

        try{
            String sql = "delete from admin where adminID = '" + tbClick + "'";
            int delete = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this account?","Delete", JOptionPane.YES_NO_OPTION);
            if(delete == JOptionPane.YES_OPTION){
            pst = Conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Account Deleted");}
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        Update_table_members();
    }//GEN-LAST:event_jButton1MouseClicked

    private void txtsalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsalesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsalesActionPerformed

    private void searchtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchtxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchtxtActionPerformed

    private void searchtxt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchtxt1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchtxt1ActionPerformed

    private void searchtxt1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchtxt1KeyReleased
        
    }//GEN-LAST:event_searchtxt1KeyReleased

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
                String sql ="SELECT `ProductID`, `ProductName` as \"Product Name\", `ProductPrice`, ProductQuantity as "
                        + "Product Quantity FROM `products` WHERE ProductCategory = '" + products + "'";
                pst = Conn.prepareStatement(sql);
                rs = pst.executeQuery();
                tb.setModel(DbUtils.resultSetToTableModel(rs));
            }
        }

        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_catcbxActionPerformed

    private void tb1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb1MouseClicked
        pluss.setEnabled(false);
        minuss.setEnabled(false);
        addbtn.setEnabled(true);
        int row = tb.getSelectedRow();
        if(tbcart.getRowCount()==0){
            try{
                String tbclk = (tb.getModel().getValueAt(row, 0).toString());
                String sql =  ("select * from products Where ProductID = '" + tbclk + "'  ");
                //pst1 = Conn.prepareStatement(sql2);
                pst = Conn.prepareStatement(sql);
                rs = pst.executeQuery();
                while(rs.next())
                stocktxt.setText(rs.getString("ProductQuantity"));
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
                    String sql =  ("select * from products Where ProductID = '" + tbclk + "'  ");
                    //pst1 = Conn.prepareStatement(sql2);
                    pst = Conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                    while(rs.next())
                    stocktxt.setText(rs.getString("ProductQuantity"));
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null,e);
                }
            }
        }
    }//GEN-LAST:event_tb1MouseClicked

    private void tbcartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbcartMouseClicked
        pluss.setEnabled(true);
        minuss.setEnabled(true);
        addbtn.setEnabled(false);
        int row = tbcart.getSelectedRow();
        stocktxt.setText(tbcart.getModel().getValueAt(row, 4).toString());
    }//GEN-LAST:event_tbcartMouseClicked

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
                String sql =  ("select ProductID, ProductName, ProductPrice, ProductQuantity from products Where ProductID = '" + tbclk + "'  ");
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
                            double pricemultiple = (double)multiple * Double.parseDouble(rs.getString("ProductPrice"));
                            int sto = avtxt-multiple;
                            stocktxt.setText(Integer.toString(sto));
                            DefaultTableModel dtm = (DefaultTableModel)tbcart.getModel();
                            dtm.addRow(new Object[] { rs.getString("ProductID"), rs.getString("ProductName"),pricemultiple, multiple,sto,getCurrentDate() });
                        }
                    }
                    else if(cbxunit.getSelectedIndex()==2){
                        if(avtxt<10){
                            JOptionPane.showMessageDialog(null, "Sorry this item is limited!");
                        }
                        else{
                            qty  =Integer.parseInt(qtytxt.getText());
                            multiple = qty * 10;
                            double pricemultiple = (double)multiple * Double.parseDouble(rs.getString("ProductPrice"));
                            int sto = avtxt-multiple;
                            stocktxt.setText(Integer.toString(sto));
                            DefaultTableModel dtm = (DefaultTableModel)tbcart.getModel();
                            dtm.addRow(new Object[] { rs.getString("ProductID"), rs.getString("ProductName"),pricemultiple, multiple,sto,getCurrentDate() });
                        }
                    }
                    else if(cbxunit.getSelectedIndex()==0){
                        if(avtxt<=0){
                            JOptionPane.showMessageDialog(null, "Sorry this item is out of stock!");
                        }
                        else{
                            multiple  =Integer.parseInt(qtytxt.getText());
                            double pricemultiple = (double)multiple * Double.parseDouble(rs.getString("ProductPrice"));
                            int sto = avtxt-multiple;
                            stocktxt.setText(Integer.toString(sto));
                            DefaultTableModel dtm = (DefaultTableModel)tbcart.getModel();
                            dtm.addRow(new Object[] { rs.getString("ProductID"), rs.getString("ProductName"),pricemultiple, multiple,sto,getCurrentDate() });
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
                    String sql =  ("select ProductID, ProductName, ProductPrice, ProductQuantity from products Where ProductID = '" + tbclk + "'  ");
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
                                double pricemultiple = (double)multiple * Double.parseDouble(rs.getString("ProductPrice"));
                                int sto = avtxt-multiple;
                                stocktxt.setText(Integer.toString(sto));
                                DefaultTableModel dtm = (DefaultTableModel)tbcart.getModel();
                                dtm.addRow(new Object[] { rs.getString("ProductID"), rs.getString("ProductName"),pricemultiple, multiple,sto,getCurrentDate() });
                            }
                        }
                        else if(cbxunit.getSelectedIndex()==2){
                            if(avtxt<10){
                                JOptionPane.showMessageDialog(null, "Sorry this item is limited!");
                            }
                            else{
                                qty  =Integer.parseInt(qtytxt.getText());
                                multiple = qty * 10;
                                double pricemultiple = (double)multiple * Double.parseDouble(rs.getString("ProductPrice"));
                                int sto = avtxt-multiple;
                                stocktxt.setText(Integer.toString(sto));
                                DefaultTableModel dtm = (DefaultTableModel)tbcart.getModel();
                                dtm.addRow(new Object[] { rs.getString("ProductID"), rs.getString("ProductName"),pricemultiple, multiple,sto,getCurrentDate() });
                            }
                        }
                        else if(cbxunit.getSelectedIndex()==0){
                            if(avtxt<=0){
                                JOptionPane.showMessageDialog(null, "Sorry this item is out of stock!");
                            }
                            else{
                                multiple  =Integer.parseInt(qtytxt.getText());
                                double pricemultiple = (double)multiple * Double.parseDouble(rs.getString("ProductPrice"));
                                int sto = avtxt-multiple;
                                stocktxt.setText(Integer.toString(sto));
                                DefaultTableModel dtm = (DefaultTableModel)tbcart.getModel();
                                dtm.addRow(new Object[] { rs.getString("ProductID"), rs.getString("ProductName"),pricemultiple, multiple,sto,getCurrentDate() });
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

    private void clearbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbtnActionPerformed
        costtxt.setText("");
        costtxt.setEnabled(false);
        paymenttxt.setEnabled(false);
        remlbl.setEnabled(false);
        purchasebtn.setEnabled(false);
        changetxt.setEnabled(false);
        ((DefaultTableModel)tbcart.getModel()).setRowCount(0);
    }//GEN-LAST:event_clearbtnActionPerformed

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
                //pickUp();
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
                //pickupDateChooser.setEnabled(false);
                ((DefaultTableModel)tbcart.getModel()).setRowCount(0);
                //PickUpPickUp page = new PickUpPickUp();
                //page.setVisible(true);
                //PickUpPickUp.userId.setText(transactionId.getText());
                //PickUpPickUp.userPassword.setText(costtxt.getText());
            }
        }
    }//GEN-LAST:event_purchasebtnActionPerformed

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

    private void searchtxt2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchtxt2KeyReleased
       
    }//GEN-LAST:event_searchtxt2KeyReleased

    private void allproductsComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allproductsComboBox1ActionPerformed
        String products = (String)allproductsComboBox.getSelectedItem();
        try{
            if(products.equals("All Products")){
                Update_table();
            }
            else if(products.equals("Critical Products")){
                criticalProduct();
            }
            else{
                String sql ="SELECT `ProductID`, `ProductName` as \"Product Name\", `ProductPrice`, ProductQuantity as "
                        + "Product Quantity FROM `products` WHERE ProductCategory = '" + products + "'";
                pst = Conn.prepareStatement(sql);
                rs = pst.executeQuery();
                tb.setModel(DbUtils.resultSetToTableModel(rs));
            }
        }

        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_allproductsComboBox1ActionPerformed

    private void cbxsearch2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxsearch2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxsearch2ActionPerformed

    private void delete1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete1MouseClicked
        int row = tb.getSelectedRow();
        String tbClick = tb.getModel().getValueAt(row, 0).toString();

        try{
            String sql = "delete from sales where SalesID = '" + tbClick + "'";
            int delete = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?","Delete", JOptionPane.YES_NO_OPTION);
            if(delete == JOptionPane.YES_OPTION){
                pst = Conn.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Deleted");}
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        Update_table();
        getId();
    }//GEN-LAST:event_delete1MouseClicked

    private void delete1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete1MouseEntered
        ImageIcon img = new ImageIcon(getClass().getResource("/images/del2.png"));
        delete.setIcon(img);
    }//GEN-LAST:event_delete1MouseEntered

    private void delete1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete1MouseExited
        ImageIcon img = new ImageIcon(getClass().getResource("/images/del.png"));
        delete.setIcon(img);
    }//GEN-LAST:event_delete1MouseExited

    private void delete1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete1MousePressed
        ImageIcon img = new ImageIcon(getClass().getResource("/images/del3.png"));
        delete.setIcon(img);
    }//GEN-LAST:event_delete1MousePressed

    private void delete1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete1MouseReleased
        ImageIcon img = new ImageIcon(getClass().getResource("/images/del.png"));
        delete.setIcon(img);
    }//GEN-LAST:event_delete1MouseReleased

    private void tb2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb2MouseClicked
        int row = tb.getSelectedRow();
        try{
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd", Locale.getDefault());
            //String d = sdf.format(pickupDateChooser.getDate());

            String tbClick = tb.getModel().getValueAt(row, 0).toString();
            String sql = ("Select fname, Password From sales Where SalesID = '" + tbClick + "'  ");
            pst = Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                //pname.setText(tb.getModel().getValueAt(row, 1).toString());
                //des.setText(rs.getString("description"));
                //priceTextField.setText(tb.getModel().getValueAt(row, 2).toString());
                //addstockTextField.setText(tb.getModel().getValueAt(row, 3).toString());
                //urltxt.setText(rs.getString("imageurl"));
            }

//            PickupdesignViewProduct.productid.setText(rs.getString("ProductID"));
            PickupdesignViewProduct.productname.setText(rs.getString("ProductName"));
            PickupdesignViewProduct.description.setText(rs.getString("ProductDescription"));
            PickupdesignViewProduct.price.setText(rs.getString("ProductPrice"));
            PickupdesignViewProduct.quantity.setText(rs.getString("ProductQuantity"));

            //PickupdesignViewProduct.unitCombo.setSelectedItem(unitCombo.getSelectedItem().toString());
            //PickupdesignViewProduct.productname.setText(costtxt.getText());
        }
        catch(Exception e){}
    }//GEN-LAST:event_tb2MouseClicked

    private void ViewSelectedItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViewSelectedItem1ActionPerformed
        if(tb.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(null, "Please choose an item to view!");
        }
        else{
            try{
                PickupdesignAsChangeStatus page = new PickupdesignAsChangeStatus();
                page.setVisible(true);
                //ViewProduct vp = new ViewProduct();
                //vp.setVisible(true);
                String id = tableClick();
                String sql = ("Select * From sales Where SalesID = '" + id + "'  ");
                pst = Conn.prepareStatement(sql);
                rs = pst.executeQuery();
                while(rs.next()){
                    //ViewProduct.txta1.setText(rs.getString("description"));
                    //ImageIcon icon=new ImageIcon(rs.getString("imageurl"));
                    //Image icoo = icon.getImage();
                    //format=new ImageIcon(fix_it(icoo,ViewProduct.lbl1.getWidth(),ViewProduct.lbl1.getHeight()));
                    //ViewProduct.lbl1.setIcon(format);
                    PickupdesignAsChangeStatus.transactionId.setText(rs.getString("SalesID"));
                    //PickupdesignViewProduct.productname.setText(rs.getString("Product_Name"));
                    ///PickupdesignViewProduct.description.setText(rs.getString("description"));
                    //PickupdesignViewProduct.price.setText(rs.getString("Price"));
                    //PickupdesignViewProduct.quantity.setText(rs.getString("Qty"));

                    //PickupdesignViewProduct.unitCombo.setSelectedItem(rs.getString("Unit"));
                }

            }
            catch  (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }

        /*int row = tb.getSelectedRow();        PickupdesignViewProduct
        try{
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd", Locale.getDefault());
            //String d = sdf.format(pickupDateChooser.getDate());

            String tbClick = tb.getModel().getValueAt(row, 0).toString();
            String sql = ("Select description,imageurl From store Where ID = '" + tbClick + "'  ");
            pst = Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                pname.setText(tb.getModel().getValueAt(row, 1).toString());
                des.setText(rs.getString("description"));
                priceTextField.setText(tb.getModel().getValueAt(row, 2).toString());
                addstockTextField.setText(tb.getModel().getValueAt(row, 3).toString());
                urltxt.setText(rs.getString("imageurl"));
            }

            PickupdesignViewProduct.productid.setText(rs.getString("ID"));
            PickupdesignViewProduct.productname.setText(rs.getString("Product_Name"));
            PickupdesignViewProduct.description.setText(rs.getString("description"));
            PickupdesignViewProduct.price.setText(rs.getString("Price"));
            PickupdesignViewProduct.quantity.setText(rs.getString("Qty"));

            PickupdesignViewProduct page = new PickupdesignViewProduct();
            page.setVisible(true);
            this.setVisible(false);

            //PickupdesignViewProduct.unitCombo.setSelectedItem(unitCombo.getSelectedItem().toString());
            //PickupdesignViewProduct.productname.setText(costtxt.getText());
        }
        catch(Exception e){}*/

    }//GEN-LAST:event_ViewSelectedItem1ActionPerformed

    private void comboPositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPositionActionPerformed

    }//GEN-LAST:event_comboPositionActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if(comboPosition.getSelectedItem().equals("Admin")){
            int check = 0;
            try{
                check = search(userName.getText(), userName.getText());
            }
            catch (SQLException ex) {/*Logger.getLogger(trial.class.getName()).log(Level.SEVERE, null, ex);*/}
            catch (ClassNotFoundException ex) {/*Logger.getLogger(trial.class.getName()).log(Level.SEVERE, null, ex);*/}

            if (check == 1){
                try{
                    if (userPassword.getText().equals("")){
                        JOptionPane.showMessageDialog(null, "Password must contain value", "System Message", JOptionPane.INFORMATION_MESSAGE);
                        userName.setText(null);
                        //userAge.setText(null);
                        userPassword.setText(null);
                    }
                    if ((userName.getText().equals("")))
                    JOptionPane.showMessageDialog(null, "Username is Required", "System Message", JOptionPane.ERROR_MESSAGE);

                    else if(!(userPassword.getText().equals(userPassword2.getText()))){
                        JOptionPane.showMessageDialog(null, "Password not matched", "Systemwatch Message", JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        st.executeUpdate("Insert into admin(" + "adminID, fname," + "Password" + "userPosition" + ") VALUES ('" + userId.getText() + "','" + userName.getText() + "','" + userPassword.getText() + "','"+comboPosition.getSelectedItem()+"')");
                        //st2.executeUpdate("Insert into history(" + "Id,username," + "date" + ") VALUES ('" + userId.getText() + "','" + userName.getText() + "','" + userName.getText() + "')");
                        JOptionPane.showMessageDialog(null, "Account Created", "System Message", JOptionPane.INFORMATION_MESSAGE);
                        CustomerAcc.namelbl.setText(userName.getText());
                        //st.executeUpdate("Insert into history(" + "Id,username," + "date" + ") VALUES ('" + userId.getText() + "','" + userName.getText() + "','" + userName.getText() + "')");
                    }
                }
                catch (SQLException ex){
                    //Logger.getLogger(trial.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Record Already Exist", "System Message", JOptionPane.ERROR_MESSAGE);
                    userName.setText(null);
                    //userAge.setText(null);
                    userPassword.setText(null);
                }
            }
        }

        else if(comboPosition.getSelectedItem().equals("SalesPerson"))
        {
            int check = 0;
            try{
                check = search(userName.getText(), userName.getText());
            }
            catch (SQLException ex) {/*Logger.getLogger(trial.class.getName()).log(Level.SEVERE, null, ex);*/}
            catch (ClassNotFoundException ex) {/*Logger.getLogger(trial.class.getName()).log(Level.SEVERE, null, ex);*/}

            if (check == 1){
                try{
                    if (userPassword.getText().equals("")){
                        JOptionPane.showMessageDialog(null, "Password must contain value", "System Message", JOptionPane.INFORMATION_MESSAGE);
                        userName.setText(null);
                        //userAge.setText(null);
                        userPassword.setText(null);
                    }
                    if ((userName.getText().equals("")))
                    JOptionPane.showMessageDialog(null, "Username is Required", "System Message", JOptionPane.ERROR_MESSAGE);

                    else if(!(userPassword.getText().equals(userPassword2.getText()))){
                        JOptionPane.showMessageDialog(null, "Password not matched", "Systemwatch Message", JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        st.executeUpdate("Insert into admin(" + "adminID, fname," + "Password" + "userPosition" + ") VALUES ('" + userId.getText() + "','" + userName.getText() + "','" + userPassword.getText() + "','"+comboPosition.getSelectedItem()+"')");
                        //st2.executeUpdate("Insert into history(" + "Id,username," + "date" + ") VALUES ('" + userId.getText() + "','" + userName.getText() + "','" + userName.getText() + "')");
                        JOptionPane.showMessageDialog(null, "Account Created", "System Message", JOptionPane.INFORMATION_MESSAGE);
                        CustomerAcc.namelbl.setText(userName.getText());
                        //st.executeUpdate("Insert into history(" + "Id,username," + "date" + ") VALUES ('" + userId.getText() + "','" + userName.getText() + "','" + userName.getText() + "')");
                    }
                }
                catch (SQLException ex){
                    //Logger.getLogger(trial.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Record Already Exist", "System Message", JOptionPane.ERROR_MESSAGE);
                    userId.setText(null);
                    userName.setText(null);
                    //userAge.setText(null);
                    userPassword.setText(null);
                }
            }
        }

        else if(comboPosition.getSelectedItem().equals("WarehouseManager"))
        {
            int check = 0;
            try{
                check = search(userName.getText(), userName.getText());
            }
            catch (SQLException ex) {/*Logger.getLogger(trial.class.getName()).log(Level.SEVERE, null, ex);*/}
            catch (ClassNotFoundException ex) {/*Logger.getLogger(trial.class.getName()).log(Level.SEVERE, null, ex);*/}

            if (check == 1){
                try{
                    if (userPassword.getText().equals("")){
                        JOptionPane.showMessageDialog(null, "Password must contain value", "System Message", JOptionPane.INFORMATION_MESSAGE);
                        userName.setText(null);
                        //userAge.setText(null);
                        userPassword.setText(null);
                    }
                    if ((userName.getText().equals("")))
                    JOptionPane.showMessageDialog(null, "Username is Required", "System Message", JOptionPane.ERROR_MESSAGE);

                    else if(!(userPassword.getText().equals(userPassword2.getText()))){
                        JOptionPane.showMessageDialog(null, "Password not matched", "Systemwatch Message", JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        st.executeUpdate("Insert into admin(" + "adminID, fname," + "Password" + "userPosition" + ") VALUES ('" + userId.getText() + "','" + userName.getText() + "','" + userPassword.getText() + "','"+comboPosition.getSelectedItem()+"')");
                        //st2.executeUpdate("Insert into history(" + "Id,username," + "date" + ") VALUES ('" + userId.getText() + "','" + userName.getText() + "','" + userName.getText() + "')");
                        JOptionPane.showMessageDialog(null, "Account Created", "System Message", JOptionPane.INFORMATION_MESSAGE);
                        CustomerAcc.namelbl.setText(userName.getText());
                        //st.executeUpdate("Insert into history(" + "Id,username," + "date" + ") VALUES ('" + userId.getText() + "','" + userName.getText() + "','" + userName.getText() + "')");
                    }
                }
                catch (SQLException ex){
                    //Logger.getLogger(trial.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Record Already Exist", "System Message", JOptionPane.ERROR_MESSAGE);
                    userId.setText(null);
                    userName.setText(null);
                    //userAge.setText(null);
                    userPassword.setText(null);
                }
            }
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void tbMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMouseEntered

    }//GEN-LAST:event_tbMouseEntered

    private void tbMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMouseExited

    }//GEN-LAST:event_tbMouseExited

    private void tbMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMousePressed

    }//GEN-LAST:event_tbMousePressed

    private void tbMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMouseReleased

    }//GEN-LAST:event_tbMouseReleased

    private void searchtxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchtxtKeyTyped
        
    }//GEN-LAST:event_searchtxtKeyTyped
    
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
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddProduct;
    private javax.swing.JButton ViewSelectedItem;
    private javax.swing.JButton ViewSelectedItem1;
    private javax.swing.JButton addbtn;
    private javax.swing.JComboBox allproductsComboBox;
    private javax.swing.JComboBox allproductsComboBox1;
    private javax.swing.JComboBox catcbx;
    private javax.swing.JComboBox cbxall;
    private javax.swing.JComboBox cbxsearch1;
    private javax.swing.JComboBox cbxsearch2;
    private javax.swing.JComboBox cbxunit;
    private javax.swing.JTextField changetxt;
    private javax.swing.JButton clearbtn;
    private javax.swing.JComboBox comboPosition;
    public static javax.swing.JTextField costtxt;
    private javax.swing.JPanel customersPanel;
    private javax.swing.JPanel customersPanel1;
    private javax.swing.JLabel delete;
    private javax.swing.JLabel delete1;
    private javax.swing.JDesktopPane dpane;
    private com.toedter.calendar.JDateChooser fromm1;
    private javax.swing.JPanel inventoryPanel;
    private javax.swing.JPanel inventoryPanel1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTable memberTable;
    private javax.swing.JButton minuss;
    private javax.swing.JLabel out;
    private javax.swing.JTextField paymenttxt;
    private javax.swing.JTable pickupTable;
    private javax.swing.JButton pluss;
    private javax.swing.JButton purchasebtn;
    private javax.swing.JTextField qtytxt;
    private javax.swing.JLabel remlbl;
    private javax.swing.JButton salem2m;
    private javax.swing.JButton salesm2m;
    private javax.swing.JTable salestbl;
    private javax.swing.JTextField searchtxt;
    private javax.swing.JTextField searchtxt1;
    private javax.swing.JTextField searchtxt2;
    private javax.swing.JScrollPane sp1;
    private javax.swing.JScrollPane sp2;
    private javax.swing.JTextField stocktxt;
    private javax.swing.JTabbedPane tabpane1;
    private javax.swing.JTable tb;
    private javax.swing.JTable tb1;
    private javax.swing.JTable tb2;
    private javax.swing.JTable tbcart;
    private com.toedter.calendar.JDateChooser tom2;
    public javax.swing.JTextField transactionId;
    public static javax.swing.JTextField txtid;
    private javax.swing.JTextField txtsales;
    private javax.swing.JLabel urltxt;
    private javax.swing.JLabel urltxt1;
    public javax.swing.JTextField userId;
    private javax.swing.JTextField userName;
    private javax.swing.JPasswordField userPassword;
    private javax.swing.JPasswordField userPassword2;
    // End of variables declaration//GEN-END:variables
}

