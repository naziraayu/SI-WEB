
package sriknd;

import custom.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;


public class table_custom extends JTable{
    
    public table_custom() {
        getTableHeader().setDefaultRenderer(new table_customHeader());
        getTableHeader().setPreferredSize(new Dimension(0, 35));
        setDefaultRenderer(Object.class, new table_customCell());
        setRowHeight(30);
    }
    
    private class table_customHeader extends DefaultTableCellRenderer{
        
        @Override
        public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
            Component com= super.getTableCellRendererComponent(jtable, o, bln1, bln1, i, i1);
            com.setBackground(new Color(44,140,136));
            com.setForeground(new Color(255,255,255));
            com.setFont(com.getFont().deriveFont(Font.BOLD, 18));
            return com;
        }
    }
    private class table_customCell extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int row, int coloum) {
            Component com= super.getTableCellRendererComponent(jtable, o, bln, bln1, row, coloum); 
            if(isCellSelected(row, coloum)) {
                if (row%2==0) {
                    com.setBackground(new Color(240,180,107));
                }else{
                    com.setBackground(new Color(240,180,107));
                }
            }else{
                if (row%2==0) {
                    com.setBackground(new Color(255,255,255));
                }else{
                    com.setBackground(new Color(255,255,255));
                }
            }
            com.setForeground(new Color(50,50,50));
            setBorder(new EmptyBorder(0,5,0,5));
            return com;
        }
         
    }
}