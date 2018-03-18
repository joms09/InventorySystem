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
import javax.swing.table.JTableHeader;


/**
 *
 * @author intel
 */
public class UpdatePickupTrial extends javax.swing.JFrame {
Connection Conn = null;
ResultSet rs = null;
PreparedStatement pst = null;
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
            Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","");
            return Conn;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "error");
        }
        return null;
    }
}

public void populateJTable(){
        MyQueryforPickup mq = new MyQueryforPickup();
        ArrayList<ProductforPickup> list = mq.BindTable();
        String[] columnName = {"Id","Total","Status", "Date",""};
        Object[][] rows = new Object[list.size()][5];
        for(int i = 0; i < list.size(); i++){
            rows[i][0] = list.get(i).getID();
            rows[i][1] = list.get(i).getTotal();
            rows[i][3] = list.get(i).getDate();
            rows[i][2] = list.get(i).getStatus();
            
            //if(list.get(i).getMyImage() != null){
                
            // ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getMyImage()).getImage()
            // .getScaledInstance(150, 120, Image.SCALE_SMOOTH) );   
                
            //rows[i][4] = image;
            //}
            //else{
            //    rows[i][4] = null;
            //}
            // rows[i][2] = list.get(i).getDef();
        }
        
        TheModel model = new TheModel(rows, columnName);
        tb.setModel(model);
        /*tb.getColumnModel().getColumn(0).setCellRenderer(new MultiLineCellRenderer());
        tb.getColumnModel().getColumn(1).setCellRenderer(new MultiLineCellRenderer());
        tb.getColumnModel().getColumn(2).setCellRenderer(new MultiLineCellRenderer());
        tb.getColumnModel().getColumn(3).setCellRenderer(new MultiLineCellRenderer());
        tb.getColumnModel().getColumn(4).setCellRenderer(new MultiLineCellRenderer());*/
        tb.setRowHeight(120);
        tb.getColumnModel().getColumn(4).setPreferredWidth(150);
        tb.getColumnModel().getColumn(0).setPreferredWidth(20);
        tb.getColumnModel().getColumn(3).setPreferredWidth(20);
        tb.getColumnModel().getColumn(2).setPreferredWidth(30);
        tb.getColumnModel().getColumn(1).setPreferredWidth(200);
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

        setForeground(tb.getTableHeader().getForeground());
        setBackground(tb.getTableHeader().getBackground());
        //tb.setDefaultRenderer(Object.class, new MultiLineCellRenderer());
}

private void alignLeft(JTable table, int column){
    DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
    leftRenderer.setHorizontalAlignment(JLabel.LEFT);
    table.getColumnModel().getColumn(column).setCellRenderer(leftRenderer);
}
   
    

/*private void byproduct(){
    try{
        // int roww = salestbl.getRowCount();
        //qtytxt.setText(Integer.toString(roww));
        String sql = "SELECT `ID`, `Product_Name` as \"Product Name\" , sum(Qty) as Quantity, sum(Price) as Price FROM `sales2` group by ID";
        pst = Conn.prepareStatement(sql);
        rs= pst.executeQuery();
        //salestbl.setModel(DbUtils.resultSetToTableModel(rs));
        //String id2 = salestbl.getModel().getValueAt(i, 0).toString();
        //String s = Double.toString(getSum());
        //txtsales.setText(s);
    }
    catch(Exception e){}
}*/
    

private void jtab(){
    try{
        ImageIcon img = new ImageIcon(getClass().getResource("/images/inven.png"));      
        //tabpane1.setIconAt(0, img);
        ImageIcon img1 = new ImageIcon(getClass().getResource("/images/sales.png"));      
        //tabpane1.setIconAt(3, img1);
        ImageIcon img2 = new ImageIcon(getClass().getResource("/images/member.png"));      
        //tabpane1.setIconAt(2, img2);
        ImageIcon img3 = new ImageIcon(getClass().getResource("/images/add.png"));      
        //tabpane1.setIconAt(1, img3);
        ImageIcon img4 = new ImageIcon(getClass().getResource("/images/member.png"));      
        //tabpane1.setIconAt(4, img4);
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
        ResultSet rs11 = state.executeQuery("SELECT ID FROM store");
        if ( rs11.next() ){
            identif.add(rs11.getString("ID"));
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
            //txtid.setText(identification);
        }
        else{
            int identification = Integer.parseInt(sum)+1;
            //txtid.setText(Integer.toString(identification));
        }
    }
    catch(Exception e){}
}

private void getId(){
    String sum;
    try{
        String sql = "SELECT max(ID) FROM store";
        pst = Conn.prepareStatement(sql);
        //pst.setString(1, searchtxt.getText());
        rs= pst.executeQuery();
        while(rs.next()){
            //rs.beforeFirst();
            sum = rs.getString("max(ID)");
            int identification = Integer.parseInt(sum)+1;
            //txtid.setText(Integer.toString(identification));
        }
    }
    catch (Exception e){
        //int identification = Integer.parseInt(txtid.getText())+1;
        //txtid.setText(Integer.toString(identification));
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
        String sql =  ("Select * From pickup Where Id = '" + tbClick + "'  ");
        pst = Conn.prepareStatement(sql);
        rs = pst.executeQuery();
        while(rs.next()){
            id = rs.getString("Id");             
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
        String sql = "select id as \"Id\", username as \"Username\", password as \"Password\" from login_tbl";
        //String sql = ("Select * From Category Where id = '" + lb + "'  ");
        pst = Conn.prepareStatement(sql);
        rs = pst.executeQuery();
        while(rs.next()){
            //byte[]imagedata = rs.getBytes("image");
            //format = new ImageIcon(imagedata);
            //bglbl.setIcon(format);
            //memberTable.setModel(DbUtils.resultSetToTableModel(rs));
        }
    }
    catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
    }
}

private void Pickup(){
    try{
        String sql = "select Id, Total as \"Total Price\", Date1 as \"Pick up date\" , Status from pickup";
        //String sql ="SELECT `Id`, `Total` as \"Total Price\", Date1 as Pick-up date FROM `pickup` WHERE category = '" + products + "'";
        pst = Conn.prepareStatement(sql);
        rs = pst.executeQuery();
        //pickupTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
    }
}

private void Update_table(){
    try{
        //String prod_id = allproductsComboBox.getSelectedItem();
        String sql = "select Id, Total as \"Total\", Date1, Status as Status from pickup where Status =  '" +allproductsComboBox.getSelectedItem()+ "'";
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

private void Update_table_all(){
    try{
        //String prod_id = allproductsComboBox.getSelectedItem();
        String sql = "select Id, Total as \"Total\", Date1, Status as Status  from pickup";
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

//public double getSum(){
    //int rowsCount = salestbl.getRowCount();
    //double sum = 0;
    //for(int i = 0; i < rowsCount; i++){
        //sum = sum+Double.parseDouble(salestbl.getValueAt(i, 3).toString());
    //}
    //return sum;
//}

private void getVal(){
    try{
        String sql = ("SELECT  `ID`, `Product_Name` as 'Product Name', `Qty`, `Price`, `Date` FROM `sales`" );
        pst = Conn.prepareStatement(sql);
        rs = pst.executeQuery();
        //while(rs.next()){
        //rs.first();
        //byte[]imagedata = rs.getBytes("image");
        //format = new ImageIcon(imagedata);
        //bglbl.setIcon(format);
        //salestbl.setModel(DbUtils.resultSetToTableModel(rs));
        //String s = Double.toString(getSum());
        //txtsales.setText(s);
    }
    catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
    }
}

private void criticalProduct(){
    try{
        String sql = ("SELECT `Id`, `Total` as \"Total\", `Date1`,Status as Status FROM pickup where Status =  '" +allproductsComboBox.getSelectedItem()+ "'");
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

private static java.sql.Date getCurrentDate() {
    java.util.Date today = new java.util.Date();
    return new java.sql.Date(today.getTime());
}

    /**
     * Creates new form Admin
     */

public UpdatePickupTrial(){
    initComponents();
    populateJTable();
    Conn = UpdatePickupTrial.javaconnect.ConnecrDB();
    //tom2.getJCalendar().setMaxSelectableDate(getCurrentDate());
    //((JTextFieldDateEditor)tom2.getDateEditor()).setEditable(false);
    //fromm1.getJCalendar().setMaxSelectableDate(getCurrentDate());
    //((JTextFieldDateEditor)tom2.getDateEditor()).setEditable(false);
    //byproduct();
    jtab();
    //Update_table();
    Member();
    Pickup();
    getId();
    //((JTextField)datetxt.getDateEditor().getUiComponent()).setText(getCurrentDate().toString());
    //tabpane1.setBackgroundAt(0,Color.BLUE);
    //tabpane1.get
    //jScrollPane7.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);
    //jScrollPane7.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
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
        jDesktopPane1 = new javax.swing.JDesktopPane();
        out = new javax.swing.JLabel();
        inventoryPanel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        searchtxt = new javax.swing.JTextField();
        allproductsComboBox = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        cbxsearch = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        delete = new javax.swing.JLabel();
        urltxt = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tb = new javax.swing.JTable();
        ViewSelectedItem = new javax.swing.JButton();
        back = new javax.swing.JButton();

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

        inventoryPanel.setBackground(new java.awt.Color(153, 204, 255));
        inventoryPanel.setLayout(null);

        jPanel5.setBackground(new java.awt.Color(153, 204, 255));

        searchtxt.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        searchtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchtxtKeyReleased(evt);
            }
        });

        allproductsComboBox.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        allproductsComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All", "Pending", "Completed" }));
        allproductsComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allproductsComboBoxActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel2.setText("Filter:");

        cbxsearch.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        cbxsearch.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Product Name", "ID", "Price" }));
        cbxsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxsearchActionPerformed(evt);
            }
        });

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
                .addComponent(searchtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbxsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(allproductsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addComponent(delete)
                .addContainerGap())
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
                            .addComponent(cbxsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

            },
            new String [] {

            }
        ));
        tb.setToolTipText("");
        tb.setRowMargin(2);
        tb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(tb);

        inventoryPanel.add(jScrollPane11);
        jScrollPane11.setBounds(40, 110, 820, 260);

        ViewSelectedItem.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        ViewSelectedItem.setText("Edit Status");
        ViewSelectedItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewSelectedItemActionPerformed(evt);
            }
        });
        inventoryPanel.add(ViewSelectedItem);
        ViewSelectedItem.setBounds(20, 20, 150, 40);

        jDesktopPane1.add(inventoryPanel);
        inventoryPanel.setBounds(60, 60, 930, 590);

        back.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        back.setText("Back to Main Menu");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });
        jDesktopPane1.add(back);
        back.setBounds(60, 20, 150, 30);

        getContentPane().add(jDesktopPane1);
        jDesktopPane1.setBounds(0, 0, 1050, 710);

        setSize(new java.awt.Dimension(1050, 700));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    private void allproductsComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allproductsComboBoxActionPerformed
        String products = (String)allproductsComboBox.getSelectedItem();
        try{
            if(products.equals("All")){
                Update_table_all();
            }
            else if(products.equals("Pending")){
                Update_table();
            }
            else if(products.equals("Completed")){
                criticalProduct();
            }
            else{
                String sql ="SELECT `ID`, `Product_Name` as \"Product Name\", `Price`,Qty as Quantity, image FROM `store` WHERE category = '" + products + "'";
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
        if(cbxsearch.getSelectedIndex()==1){
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
        
        else if(cbxsearch.getSelectedIndex()==0){
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
            String sql = "delete from pickup where ID = '" + tbClick + "'";
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
    }//GEN-LAST:event_deleteMouseClicked

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

    private void outMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outMouseClicked
        LoginLogin logg = new LoginLogin();
        logg.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_outMouseClicked

    private void cbxsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxsearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxsearchActionPerformed

    private void tom2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tom2KeyReleased
      
    
    }//GEN-LAST:event_tom2KeyReleased

    private void tom2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tom2KeyPressed
       
    
    }//GEN-LAST:event_tom2KeyPressed

    private void tbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMouseClicked
        int row = tb.getSelectedRow();
        try{
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd", Locale.getDefault());
            //String d = sdf.format(pickupDateChooser.getDate());
        
        
            String tbClick = tb.getModel().getValueAt(row, 0).toString();
            String sql = ("Select username, password From pickup Where ID = '" + tbClick + "'  ");
            pst = Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                //pname.setText(tb.getModel().getValueAt(row, 1).toString());
                //des.setText(rs.getString("description"));
                //priceTextField.setText(tb.getModel().getValueAt(row, 2).toString());
                //addstockTextField.setText(tb.getModel().getValueAt(row, 3).toString());
                //urltxt.setText(rs.getString("imageurl"));
            }
            
//            PickupdesignViewProduct.productid.setText(rs.getString("ID"));
            PickupdesignViewProduct.productname.setText(rs.getString("Product_Name"));
            PickupdesignViewProduct.description.setText(rs.getString("description"));
            PickupdesignViewProduct.price.setText(rs.getString("Price"));
            PickupdesignViewProduct.quantity.setText(rs.getString("Qty"));
            
            //PickupdesignViewProduct.unitCombo.setSelectedItem(unitCombo.getSelectedItem().toString());
            //PickupdesignViewProduct.productname.setText(costtxt.getText());
        }
        catch(Exception e){}
    }//GEN-LAST:event_tbMouseClicked

    private void ViewSelectedItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViewSelectedItemActionPerformed
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
                String sql = ("Select * From pickup Where Id = '" + id + "'  ");
                pst = Conn.prepareStatement(sql);
                rs = pst.executeQuery();
                while(rs.next()){
                    //ViewProduct.txta1.setText(rs.getString("description"));
                    //ImageIcon icon=new ImageIcon(rs.getString("imageurl"));
                    //Image icoo = icon.getImage();
                    //format=new ImageIcon(fix_it(icoo,ViewProduct.lbl1.getWidth(),ViewProduct.lbl1.getHeight()));
                    //ViewProduct.lbl1.setIcon(format);
                    PickupdesignAsChangeStatus.transactionId.setText(rs.getString("Id"));
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
        
        
    }//GEN-LAST:event_ViewSelectedItemActionPerformed

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        SelectionSelection balik = new SelectionSelection();
        balik.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_backActionPerformed
    
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
            java.util.logging.Logger.getLogger(UpdatePickupTrial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdatePickupTrial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdatePickupTrial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdatePickupTrial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UpdatePickupTrial().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ViewSelectedItem;
    private javax.swing.JComboBox allproductsComboBox;
    private javax.swing.JButton back;
    private javax.swing.JComboBox cbxsearch;
    private javax.swing.JLabel delete;
    private javax.swing.JDesktopPane dpane;
    private javax.swing.JPanel inventoryPanel;
    private javax.swing.JButton jButton3;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JLabel out;
    private javax.swing.JTextField searchtxt;
    private javax.swing.JTable tb;
    private javax.swing.JLabel urltxt;
    // End of variables declaration//GEN-END:variables
}

