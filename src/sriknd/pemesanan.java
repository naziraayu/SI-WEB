/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sriknd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lenovo
 */
public class pemesanan extends javax.swing.JFrame {
    DefaultTableModel model=new DefaultTableModel();
    koneksi k=new koneksi();
    public void autonumberNopes() {
        try {
            String sql = "select * from pemesanan order by no_pemesanan desc";
            Connection conn=k.getcon();
            PreparedStatement pst=conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            if(rs.next()) {
                String no_Faktur = rs.getString("no_pemesanan").substring(2);
                String TR = "" + (Integer.parseInt(no_Faktur) + 1);
                String No1 = "";
                
                if(TR.length()== 1)
                {No1 = "000";}
                else if(TR.length()== 2)
                {No1 = "00";}
                else if(TR.length()== 3)
                {No1 = "0";}
                else if(TR.length()== 4)
                {No1 = "";}
                nopes.setText("66" + No1 + TR);
            } else {
                nopes.setText("660001");
            }
            nopes.disable();
            rs.close();
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.out.println("AutoNumber Error");
        }
    }
    public void autonumberkons() {
        try {
            String sql = "select * from pemesanan order by id_konsumen desc";
            Connection conn=k.getcon();
            PreparedStatement pst=conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            if(rs.next()) {
                String no_Faktur = rs.getString("id_konsumen").substring(2);
                String TR = "" + (Integer.parseInt(no_Faktur) + 1);
                String No1 = "";
                
                if(TR.length()== 1)
                {No1 = "000";}
                else if(TR.length()== 2)
                {No1 = "00";}
                else if(TR.length()== 3)
                {No1 = "0";}
                else if(TR.length()== 4)
                {No1 = "";}
                idkons.setText("98" + No1 + TR );
            } else {
                idkons.setText("980001");
            }
            idkons.disable();
            rs.close();
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.out.println("AutoNumber ini Error");
        }
    }
    public void loadData() {
        DefaultTableModel model = (DefaultTableModel) detailpemesanan.getModel();
        model.addRow(new Object[]{
            nopes.getText(),
            combonama.getSelectedItem(),
            hargabrg.getText(),
            jmlh.getText(),
            subtotal.getText()
        });
    }
    public void kosong() {
        DefaultTableModel model = (DefaultTableModel) detailpemesanan.getModel();
        
        while(model.getRowCount() > 0) {
            model.removeRow(0);
        }
    }
    public void  utama() {
        
        hargabrg.setText("");
        jmlh.setText(""); 
        
        autonumberNopes();
        autonumberkons();
    }
    public void clear() {
        idkons.setText("");
        namkons.setText("");
        nopes.setText("");
        alamat.setText("");
        notelp.setText("");
        subtotal.setText("0");
    }
    public void clear2() {
        hargabrg.setText("");
        jmlh.setText("");
    }
    public void tambahTransaksi() {
        int jumlah, harga, total;
        
        jumlah = Integer.valueOf(jmlh.getText());
        harga = Integer.valueOf(hargabrg.getText());
        total = jumlah * harga;
        
        subtotal.setText(String.valueOf(total));
        
        loadData();
        //totalbiaya();
        clear2();
        nopes.requestFocus();
        idkons.requestFocus();
    }
    public void tampilcombo (){
        try {
            String sql="Select * from produk";
            Connection conn=k.getcon();
            PreparedStatement pst=conn.prepareStatement(sql);
            ResultSet rs=pst.executeQuery();
            while (rs.next()) {                
                combonama.addItem(rs.getString("kode_produk"));
            }
            rs.last();
            int jumlahdata=rs.getRow();
            rs.first();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    /**
     * Creates new form pemesanan
     */
    public pemesanan() {
        initComponents();
        k.connect();
        tampilcombo();
        
        model = new DefaultTableModel();
        
        detailpemesanan.setModel(model);
        
        model.addColumn("No.Pemesanan");
        model.addColumn("Kode Produk");
        model.addColumn("Harga Satuan");
        model.addColumn("Jumlah");
        model.addColumn("Sub total");
        
        
        utama();
        Date date = new Date();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        
        tglpes.setText(s.format(date));
        tglpes.disable();
        
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
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        nopes = new javax.swing.JTextField();
        idkons = new javax.swing.JTextField();
        alamat = new javax.swing.JTextField();
        jmlh = new javax.swing.JTextField();
        tglpes = new javax.swing.JTextField();
        namkons = new javax.swing.JTextField();
        notelp = new javax.swing.JTextField();
        hargabrg = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        detailpemesanan = new javax.swing.JTable();
        subtotal = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        simpan = new javax.swing.JButton();
        hapus = new javax.swing.JButton();
        bayar = new javax.swing.JButton();
        combonama = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("No. Pemesanan");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        jLabel2.setText("ID Konsumen");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        jLabel3.setText("Alamat ");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        jLabel4.setText("Nama Barang");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        jLabel5.setText("Jumlah");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, -1, -1));

        jLabel6.setText("Tanggal Pemesanan");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 60, -1, -1));

        jLabel7.setText("Nama Konsumen");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 100, -1, -1));

        jLabel8.setText("No.Telepon");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 140, -1, -1));

        jLabel9.setText("Harga Barang");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 180, -1, -1));
        getContentPane().add(nopes, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, 103, -1));
        getContentPane().add(idkons, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 103, -1));
        getContentPane().add(alamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 103, -1));

        jmlh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmlhActionPerformed(evt);
            }
        });
        getContentPane().add(jmlh, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 230, 103, -1));
        getContentPane().add(tglpes, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 60, 100, -1));
        getContentPane().add(namkons, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 100, 100, -1));
        getContentPane().add(notelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 140, 100, -1));

        hargabrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hargabrgActionPerformed(evt);
            }
        });
        getContentPane().add(hargabrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 180, 100, -1));

        detailpemesanan.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(detailpemesanan);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 620, 390));
        getContentPane().add(subtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 220, 100, 30));

        jLabel10.setText("Sub Total");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 220, -1, -1));

        simpan.setText("Simpan");
        simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanActionPerformed(evt);
            }
        });
        getContentPane().add(simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 300, 90, -1));

        hapus.setText("Hapus");
        hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusActionPerformed(evt);
            }
        });
        getContentPane().add(hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 300, -1, -1));

        bayar.setText("Bayar");
        bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bayarActionPerformed(evt);
            }
        });
        getContentPane().add(bayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 300, -1, -1));

        combonama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combonamaActionPerformed(evt);
            }
        });
        getContentPane().add(combonama, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, 100, 30));

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setIcon(new javax.swing.ImageIcon("D:\\back\\Keuntungan.png")); // NOI18N
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -60, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusActionPerformed
        DefaultTableModel model = (DefaultTableModel) detailpemesanan.getModel();
        int row = detailpemesanan.getSelectedRow();
        model.removeRow(row);
        //totalbiaya();
    }//GEN-LAST:event_hapusActionPerformed

    private void simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanActionPerformed
        tambahTransaksi();
        
        //namabrg.setText("");
        hargabrg.setText("");
        jmlh.setText("");
        subtotal.setText("");
        
    }//GEN-LAST:event_simpanActionPerformed

    private void jmlhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmlhActionPerformed
        int jumlah, harga, total;
        
        jumlah = Integer.valueOf(jmlh.getText());
        harga = Integer.valueOf(hargabrg.getText());
        total = jumlah * harga;
        
        subtotal.setText(String.valueOf(total));
    }//GEN-LAST:event_jmlhActionPerformed

    private void combonamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combonamaActionPerformed
        try {
            String sql="Select * from produk where kode_produk='"+combonama.getSelectedItem()+"'";
            Connection con=k.getcon();
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                hargabrg.setText(rs.getString("harga_jual"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_combonamaActionPerformed

    private void hargabrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hargabrgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hargabrgActionPerformed

    private void bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bayarActionPerformed
        DefaultTableModel model=(DefaultTableModel) detailpemesanan.getModel();
       try {
            int jumlahbaris=detailpemesanan.getRowCount();
            for (int i=0; i<jumlahbaris; i++){
            String sql="insert into detail_pemesanan  values ('"
                    +detailpemesanan.getValueAt(i,0)+"','"+detailpemesanan.getValueAt(i, 1)+"','"+detailpemesanan.getValueAt(i, 3)+"','"+detailpemesanan.getValueAt(i, 4)+"')";
            Connection con=k.getcon();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.execute();
            ps.close();
            //JOptionPane.showMessageDialog(null, "berhasil");
            detailpemesanan.setModel(model);
            
            }} catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());     
        }
        try {
            String sql="insert into pemesanan (no_pemesanan, tgl_pemesanan,total_item,total_harga,bayar_awal,kurang,id_user,id_konsumen) values ('"
                    +nopes.getText()+"',now(),'0','0','0','0','','"+idkons.getText()+"')";
            //String sql="insert into transaksi values (?, ?, ?, ?, ?, ?, ?, ?)";
            Connection con=k.getcon();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.execute();
            ps.close();
            JOptionPane.showMessageDialog(null, "BERHASIL!!!!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        try {
            String sql="insert into konsumen values ('"+idkons.getText()+"','"+namkons.getText()+"','"+alamat.getText()+"','"+notelp.getText()+"')";
            Connection con=k.getcon();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.execute();
            ps.close();
            JOptionPane.showMessageDialog(null, "Berhasil");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_bayarActionPerformed

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
            java.util.logging.Logger.getLogger(pemesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pemesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pemesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pemesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pemesanan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField alamat;
    private javax.swing.JButton bayar;
    private javax.swing.JComboBox<String> combonama;
    private javax.swing.JTable detailpemesanan;
    private javax.swing.JButton hapus;
    private javax.swing.JTextField hargabrg;
    private javax.swing.JTextField idkons;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jmlh;
    private javax.swing.JTextField namkons;
    private javax.swing.JTextField nopes;
    private javax.swing.JTextField notelp;
    private javax.swing.JButton simpan;
    private javax.swing.JTextField subtotal;
    private javax.swing.JTextField tglpes;
    // End of variables declaration//GEN-END:variables
}
