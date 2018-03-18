/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck;
import javax.swing.*; 
import javax.swing.table.TableCellRenderer; 
import javax.swing.border.*; 
import java.awt.Component; 
import java.awt.Color; 
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
/**
 *
 * @author intel
 */
public class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {
    private Border focusBorder, noFocusBorder;
    public MultiLineCellRenderer(){
        setLineWrap(true);
        setWrapStyleWord(true);
        //int lineCount = ((JTextArea)getTableCell).getLineCount();
        focusBorder = UIManager.getBorder("Table.focusCellHighlightBorder");
        noFocusBorder = focusBorder == null ? null : newEmptyBorder(focusBorder.getBorderInsets(this));
    }
    private static Border newEmptyBorder(Insets n){
        return BorderFactory.createEmptyBorder(n.top, n.left, n.bottom, n.right);
    }
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        setText(value.toString());
        //or something in value, like value.getNote()...
        setBorder(table.getBorder());
        setFont(table.getFont());
        setEnabled(table.isEnabled());
        setComponentOrientation(table.getComponentOrientation());

        if (isSelected){
            setBorder(table.getBorder());
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        }
        else{
            setBorder(table.getBorder());
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }

        
        if (hasFocus){
            //setBorder(table.getBorder());
            setBorder(focusBorder);

            if (table.isCellEditable(row, column)){
                setForeground(UIManager.getColor("Table.focusCellForeground"));
                setBackground(UIManager.getColor("Table.focusCellBackground"));
            }
        }
        else{
            // setBorder(table.getBorder());
            setBorder(noFocusBorder);
        }
        if (table.getRowHeight(row) != getPreferredSize().height) {
               table.setRowHeight(row, getPreferredSize().height);
        }
        return this;
    }
    public static void updateRowHeights(int column, int width, JTable table){
        for (int row = 0; row < table.getRowCount(); row++){
            int rowHeight = table.getRowHeight();
            Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
            Dimension d = comp.getPreferredSize();
            comp.setSize(new Dimension(width, d.height));
            d = comp.getPreferredSize();
            rowHeight = Math.max(rowHeight, d.height);
            table.setRowHeight(row, rowHeight);
        }
    }
}