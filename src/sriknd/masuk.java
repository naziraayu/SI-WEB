/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sriknd;

import custom.notif;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author lenovo
 */
public class masuk extends javax.swing.JFrame {
koneksi k=new koneksi();
private void tablepsnmsk(){
    DefaultTableModel m=new DefaultTableModel();
    m.addColumn("No. Transaksi");
    m.addColumn("Kode Produk");
    m.addColumn("Jumlah");
    m.addColumn("Tanggal Ambil");
    m.addColumn("Keterangan");
    try {
        int no=1;
        String sql="select no_pemesanan, kode_produk, jumlah, tanggal_ambil, keterangan from detail_pemesanan where keterangan='Pesanan masuk' order by no_pemesanan asc";
        Connection c=k.getcon();
        PreparedStatement p=c.prepareStatement(sql);
        ResultSet r=p.executeQuery();
        while (r.next()) {
            m.addRow(new Object[]{r.getString(1),r.getString(2),r.getString(3),r.getString(4),r.getString(5)});
            table_custom1.setModel(m);
        }
    } catch (Exception e) {
    }
}
    /**{
     * 
     * Creates new form masuk
     */
    public masuk() {
        initComponents();
        k.connect();
        tablepsnmsk();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table_custom1 = new custom.table_custom();
        comboBoxSuggestion1 = new custom.ComboBoxSuggestion();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        table_custom1.setModel(new javax.swing.table.DefaultTableModel(
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
        table_custom1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jScrollPane1.setViewportView(table_custom1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 770, 690));

        comboBoxSuggestion1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pesanan masuk", "Sedang diproses", "Pesanan selesai" }));
        comboBoxSuggestion1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        comboBoxSuggestion1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxSuggestion1ActionPerformed(evt);
            }
        });
        getContentPane().add(comboBoxSuggestion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 110, 370, 60));

        jLabel3.setIcon(new javax.swing.ImageIcon("D:\\back\\PESANAN MASUK.png")); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, -1, 60));

        jLabel2.setIcon(new javax.swing.ImageIcon("D:\\back\\previous 1.png")); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 8, -1, 60));

        jLabel1.setIcon(new javax.swing.ImageIcon("D:\\back\\Keuntungan1.png")); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void comboBoxSuggestion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxSuggestion1ActionPerformed
        int i=table_custom1.getSelectedRow();
    TableModel m=table_custom1.getModel();
    String notrans=m.getValueAt(i, 0).toString();
    String kodeproduk=m.getValueAt(i, 1).toString();
    String jml=m.getValueAt(i, 2).toString();
    String tgl=m.getValueAt(i, 3).toString();
    //String tgl=m.getValueAt(i, 4).toString();
        try {
            String sql="update detail_pemesanan set keterangan='"+comboBoxSuggestion1.getSelectedItem()
                    +"' where no_pemesanan='"+notrans+"' and kode_produk='"+kodeproduk+"' and jumlah='"
                    +jml+"' and tanggal_ambil='"+tgl+"'";
            Connection c=k.getcon();
            PreparedStatement p=c.prepareStatement(sql);
            p.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Berahsil mengubah keterangan");
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Berhasil Mengubah Keterangan");
            if (obj.getMessageType() == notif.MessageType.OK) {
                System.out.println("User click ya");
            } else {
                System.out.println("User click batal");
            }
            tablepsnmsk();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_comboBoxSuggestion1ActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_jLabel2MouseClicked

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
            java.util.logging.Logger.getLogger(masuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(masuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(masuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(masuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new masuk().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private custom.ComboBoxSuggestion comboBoxSuggestion1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private custom.table_custom table_custom1;
    // End of variables declaration//GEN-END:variables
}
