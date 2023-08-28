/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sriknd;

import com.sun.org.apache.bcel.internal.util.SecuritySupport;
import custom.notif;
import java.awt.Color;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author lenovo
 */
public class kasir extends javax.swing.JFrame {
koneksi k=new koneksi();
    String Tanggal;
    private DefaultTableModel tabel;
    private PreparedStatement statement;
    private ResultSet result;
    private Statement state;
    DefaultTableModel model=new DefaultTableModel();
    public long angka;
    public String ganti;
    public StringTokenizer token;
    
    //koneksi k=new koneksi();
    private PreparedStatement st;
    private void tampilNoPesanan(){
    String no=noPes.getText();
    noPesanan.setText(no);
}
    private void totalubahpesan(){
    //panel ubahtransaksi
    int jumlahBaris=tableubahpsn.getRowCount();
        int totalHarga=0;
        int hargaBarang;
        for(int i=0; i<jumlahBaris; i++) {
            hargaBarang = Integer.parseInt(tableubahpsn.getValueAt(i, 4).toString());
            totalHarga = totalHarga + hargaBarang;}
        TotalHarga.setText(String.valueOf(totalHarga));
}
    private void tampilpsnmsk(){
        //panel dashkasir
        try {
            String sql="Select sum(jumlah) as jml from detail_pemesanan where keterangan='Pesanan masuk'";
            Connection c=k.getcon();
            PreparedStatement p=c.prepareStatement(sql);
            ResultSet rs=p.executeQuery();
            if (rs.next()) {
                textFieldSuggestion1.setText(rs.getString("jml"));
            }
                    
        } catch (Exception e) {
        }
    }
    private void tampilpsnpss(){
        //panel dashkasir
        try {
            String sql="Select sum(jumlah) as jml from detail_pemesanan where keterangan='Sedang diproses'";
            Connection c=k.getcon();
            PreparedStatement p=c.prepareStatement(sql);
            ResultSet rs=p.executeQuery();
            if (rs.next()) {
                textFieldSuggestion2.setText(rs.getString("jml"));
            }
                    
        } catch (Exception e) {
        }
    }
    private void tampilpsnsls(){
        //panel dashkasir
        try {
            String sql="Select sum(jumlah) as jml from detail_pemesanan where keterangan='Pesanan selesai'";
            Connection c=k.getcon();
            PreparedStatement p=c.prepareStatement(sql);
            ResultSet rs=p.executeQuery();
            if (rs.next()) {
                textFieldSuggestion3.setText(rs.getString("jml"));
            }
                    
        } catch (Exception e) {
        }
    }
    private void tablpsnbsk(){
        //panel dashkasir
        DefaultTableModel d=new DefaultTableModel();
        d.addColumn("No. Pemesanan");
        d.addColumn("Kode Produk");
        d.addColumn("Jumlah");
        d.addColumn("Tanggal Ambil");
        d.addColumn("Keterangan");
        try {
            int no=1;
            String sql="Select no_pemesanan, kode_produk, jumlah, tanggal_ambil, keterangan from detail_pemesanan where"
                    + " tanggal_ambil=curdate() + interval 1 day";
            Connection c=k.getcon();
            PreparedStatement p=c.prepareStatement(sql);
            ResultSet rs=p.executeQuery();
            while (rs.next()) {
            d.addRow(new Object[]{
            rs.getString(1),
            rs.getString(2),
            rs.getString(3),
            rs.getString(4),
            rs.getString(5)});
            }
            table_custom1.setModel(d);
        } catch (Exception e) {
        }
    }
    private void tablpsnmgg(){
        //panel dashkasir
        DefaultTableModel d=new DefaultTableModel();
        d.addColumn("No. Pemesanan");
        d.addColumn("Kode Produk");
        d.addColumn("Jumlah");
        d.addColumn("Tanggal Ambil");
        d.addColumn("Keterangan");
        try {
            int no=1;
            String sql="Select no_pemesanan, kode_produk, jumlah, tanggal_ambil, keterangan from detail_pemesanan where tanggal_ambil<=curdate() + interval 6 day order by tanggal_ambil asc";
            Connection c=k.getcon();
            PreparedStatement p=c.prepareStatement(sql);
            ResultSet rs=p.executeQuery();
            while (rs.next()) {
            d.addRow(new Object[]{
            rs.getString(1),
            rs.getString(2),
            rs.getString(3),
            rs.getString(4),
            rs.getString(5)});
            }
            table_custom2.setModel(d);
        } catch (Exception e) {
        }
    }
private void tampilTabl(){
    //panel ubahtransaksi
    DefaultTableModel model=new DefaultTableModel();
        
        
        model.addColumn(("No. Pemesanan"));
        model.addColumn(("Nama Produk"));
        model.addColumn(("Harga"));
        model.addColumn(("Jumlah"));
        model.addColumn("Total Harga");
        model.addColumn("Tanggal Ambil");
        model.addColumn("Keterangan");
        try {
            int no=1;
            PreparedStatement pst=k.getcon().prepareStatement("Select no_pemesanan, kode_produk, harga, jumlah, "
                    + "total_harga, tanggal_ambil, keterangan From detail_pemesanan where no_pemesanan='"
                    +noPesanan.getText()+"'");
            ResultSet rst=pst.executeQuery();
            
            while (rst.next()) {                
                model.addRow(new Object[] {rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)});
                
                tableubahpsn.setModel(model);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
}
public void tampilTablee(){
    DefaultTableModel model=new DefaultTableModel();
        model.addColumn(("No. Pemesanan"));
        model.addColumn(("Total Bayar"));
        model.addColumn(("Kurang"));
        model.addColumn(("Status"));
        model.addColumn("Bayar Awal");
        
        try {
            int no=1;
            PreparedStatement pst=k.getcon().prepareStatement("Select no_pemesanan,total_pembayaran, "
                    + "kurang,status,bayar_awal From "
                    + "pemesanan where status = 'Belum lunas'");
            ResultSet rst=pst.executeQuery();
            
            while (rst.next()) {                
                model.addRow(new Object[] {
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)});
                
                tlunas.setModel(model);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
}
    private void totalitemubah(){
        //panel ubahtransaksi
        int jumlahBaris=tableubahpsn.getRowCount();
        int jumlahitem=0;
        int jumlahBarang;
        for(int i=0; i<jumlahBaris; i++) {
            jumlahBarang = Integer.parseInt(tableubahpsn.getValueAt(i, 3).toString());
            jumlahitem = jumlahitem + jumlahBarang;}
        TotalItem.setText(String.valueOf(jumlahitem));
    }
    public void bayarawalubah(){
        //panel ubahtransaksi
        try {
            String sql="Select bayar_awal From pemesanan where no_pemesanan='"+noPesanan.getText()+"'";
            Connection kon=k.getcon();
            PreparedStatement p=kon.prepareStatement(sql);
            ResultSet rs=p.executeQuery();
            while (rs.next()) {                
                setergeter1.setAwalbayar(Integer.parseInt(rs.getString("bayar_awal")));
                int byr=setergeter1.getAwalbayar();
                bayarAwL.setText(String.valueOf(byr));
            } 
        } catch (Exception e) {
        }
    }
    
    
    private void totalHarga(){
        //panel pemesanan
        int jumlahBaris=jTable1.getRowCount();
        int totalHarga=0;
        int jumlahBarang,hargaBarang;
        for(int i=0; i<jumlahBaris; i++) {
            hargaBarang = Integer.parseInt(jTable1.getValueAt(i, 4).toString());
            totalHarga = totalHarga + hargaBarang;
            setergeter1.setTotalHarga(totalHarga);
        }
    }
    public void tampilcomboubah(){
        try {
            String sql="Select * From produk";
            Connection kon=k.getcon();
            PreparedStatement pst=kon.prepareStatement(sql);
            ResultSet rs=pst.executeQuery();
            while (rs.next()) {                
                combokode.addItem(rs.getString("nama_produk"));
            }
            rs.last();
            int jumlahdata=rs.getRow();
            rs.first();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public void loadDataubahtransaksi(){
        try {
            String sql="select kode_produk from produk where nama_produk='"+combokode.getSelectedItem()+"'";
            Connection c=k.getcon();
            PreparedStatement p=c.prepareStatement(sql);
            ResultSet r=p.executeQuery();
            while (r.next()) {
        String kode=r.getString("kode_produk");
        String getDate = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(getDate);
        String tanggal = String.valueOf(fm.format(dtanggal.getDate()));
        tabel= (DefaultTableModel) tableTransaksi.getModel();
        tabel.addRow(new Object[]{
        noPes.getText(),
        String.valueOf(kode),
        tharga.getText(),
        tjumlah.getText(),
        ttotal.getText(),
        tanggal
        });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
private void tampiltabletransaksi(){
        //panel transaksi
        DefaultTableModel model=new DefaultTableModel();
        model.addColumn(("No. Pemesanan"));
        model.addColumn(("Tgl Pemesanan"));
        model.addColumn(("Total Item"));
        model.addColumn("Bayar Awal");
        model.addColumn("Kurang");
        model.addColumn(("Total Bayar"));
        model.addColumn("Diskon");
        
        try {
            int no=1;
            PreparedStatement pst=k.getcon().prepareStatement("Select no_pemesanan,tgl_pemesanan, "
                    + "total_item, bayar_awal, kurang, total_pembayaran, diskon From "
                    + "pemesanan where status = 'Belum lunas'");
            ResultSet rst=pst.executeQuery();
            
            while (rst.next()) {                
                model.addRow(new Object[] {
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)});
                
                tableTransaksi.setModel(model);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
}
    private void tampilIdUser(){
        //panel transaksi
        tiduser.setText(setergeter1.getIdUser());
    }
    public void tampilNopes(){
        //panel transaksi
        String no=no_transaksi.getText();
        nopes.setText(no);
    }
    
public void tampilTable() throws SQLException{
    DefaultTableModel model=new DefaultTableModel();
        
        
        model.addColumn(("No. Pemesanan"));
        model.addColumn(("Kode Produk"));
        model.addColumn(("Harga"));
        model.addColumn(("Jumlah"));
        model.addColumn("Total Harga");
        model.addColumn("Tanggal Ambil");
        model.addColumn("Keterangan");
        try {
            int no=1;
            PreparedStatement pst=k.getcon().prepareStatement("Select * From detail_pemesanan where no_pemesanan='"
                    +nopes.getText()+"'");
            ResultSet rst=pst.executeQuery();
            
            while (rst.next()) {                
                model.addRow(new Object[] {rst.getString(1),
                    rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6),
                    rst.getString(7)});
                
                tableDetailPemesanan.setModel(model);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
}

    public void totalHarga1(){
        //panel transaksi
        int jumlahBaris=tableDetailPemesanan.getRowCount();
        int totalHarga=0;
        int hargaBarang;
        for(int i=0; i<jumlahBaris; i++) {
            hargaBarang = Integer.parseInt(tableDetailPemesanan.getValueAt(i, 4).toString());
            totalHarga = totalHarga + hargaBarang;
    }
        ttlpembayaran.setText(String.valueOf(totalHarga));
    }
    public void jumlahItem(){
        //panel transaksi
        int jumlahBaris=tableDetailPemesanan.getRowCount();
        int jumlahitem=0;
        int jumlahBarang;
        for(int i=0; i<jumlahBaris; i++) {
            jumlahBarang = Integer.parseInt(tableDetailPemesanan.getValueAt(i, 3).toString());
            jumlahitem = jumlahitem + jumlahBarang;
    }
        totalItem.setText(String.valueOf(jumlahitem));
    }
    public void ngitungdis(){
        int total, dis, hasil, totall;
        total=Integer.valueOf(ttlpembayaran.getText());
        dis=Integer.valueOf(tdis.getText());
        hasil=total*dis/100;
        totall=total-hasil;
        tkurang.setText(String.valueOf(totall));
    }
    private void ngitungdisubah(){
        int total, dis, hasil, totall;
        total=Integer.valueOf(TotalHarga.getText());
        dis=Integer.valueOf(tdiskon.getText());
        hasil=total*dis/100;
        totall=total-hasil;
        txtKurang.setText(String.valueOf(totall));
    }
    public void tungdis(){
        int total, bagi, hasil;
        total=Integer.valueOf(ttlpembayaran.getText());
        bagi=20000000;
        hasil=total/bagi;
        tdis.setText(String.valueOf(hasil));
    }
    private void tungdisubah(){
        int total, bagi, hasil;
        total=Integer.valueOf(TotalHarga.getText());
        bagi=20000000;
        hasil=total/bagi;
        tdiskon.setText(String.valueOf(hasil));
    }
    public void tampilidUserr(){
        //panel transaksi
        try {
            String sql="Select * From karyawan";
            Connection kon=k.getcon();
            PreparedStatement pst=kon.prepareStatement(sql);
            ResultSet rs=pst.executeQuery();
            while (rs.next()) {                
            }
            rs.last();
            int jumlahdata=rs.getRow();
            rs.first();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
private void autoNumber(){
    //panel pemesanan
    try {
      Connection c=k.getcon();
      Statement s=c.createStatement();
      String sql= "select * from pemesanan order by no_pemesanan desc";
      result=s.executeQuery(sql);
        if (result.next()) {
            String NoTrans= result.getString("no_pemesanan").substring(2);
            String TR="" + (Integer.parseInt(NoTrans)+1);
            String Nol="";
            
            if (TR.length()==1) {
                Nol= "000";
            } else if (TR.length()==2){
                Nol= "00";
            } else if (TR.length()==3){
                Nol= "0";
            } else if (TR.length()==4){
                Nol="";}
                no_transaksi.setText("TR" + Nol + TR);
                String noTrans=no_transaksi.getText();
            } else {
                no_transaksi.setText("TR0001");
                String noTrans=no_transaksi.getText();
            }
            result.close();
        } catch (Exception e) {
            System.out.println("autonumber error");
    }
}
private void autonumberNOkons (){
    try {
        Connection c=k.getcon();
      Statement s=c.createStatement();
      String sql= "select * from konsumen order by id_konsumen desc";
      result=s.executeQuery(sql);
        if (result.next()) {
            String NoTrans= result.getString("id_konsumen").substring(2);
            String TR="" + (Integer.parseInt(NoTrans)+1);
            String Nol="";
            
            if (TR.length()==1) {
                Nol= "000";
            } else if (TR.length()==2){
                Nol= "00";
            } else if (TR.length()==3){
                Nol= "0";
            } else if (TR.length()==4){
                Nol="";}
                tid_kons.setText("KS" + Nol + TR);
                String noTrans=tid_kons.getText();
            } else {
                tid_kons.setText("KS0001");
                String noTrans=tid_kons.getText();
            }
            result.close();
    } catch (Exception e) {
    }
}

    private void loadData(){
        //panel pemesanan
        try {
            String sql="select kode_produk from produk where nama_produk='"+combonama.getSelectedItem()+"'";
            Connection c=k.getcon();
            PreparedStatement p=c.prepareStatement(sql);
            ResultSet r=p.executeQuery();
            while (r.next()) {
        String kode=r.getString("kode_produk");
        String getDate = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(getDate);
        String tanggal = String.valueOf(fm.format(tanggalAmbil.getDate()));
        tabel= (DefaultTableModel) jTable1.getModel();
        tabel.addRow(new Object[]{
        no_transaksi.getText(),
        String.valueOf(kode),
        harga.getText(),
        jumlah.getText(),
        total_harga.getText(),
        tanggal,
        keterangan.getText()
        });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    private void kosong(){
        //panel pemesanan
        this.tabel= (DefaultTableModel) jTable1.getModel();
        while (tabel.getRowCount()>0) {            
            tabel.removeRow(0);
        }
    }
    private void utama(){
        //panel pemesanan
        no_transaksi.setText("");
        harga.setText("");
        jumlah.setText("");
        total_harga.setText("");
    }
    
    private void clear1(){
        //panel pemesanan
        no_transaksi.setText("");
        tid_kons.setText("");
        tiduser.setText("");
        autoNumber();
        autonumberNOkons();
        tampilIdUser();
    }
    
    private void tambahTransaksi(){
        //panel pemesanan
        int jmlh, hrg, total;
        jmlh = Integer.valueOf(jumlah.getText());
        hrg = Integer.valueOf(harga.getText());
        total = jmlh*hrg;
        
        total_harga.setText(String.valueOf(total));
        
        loadData();//ini untuk menampilkan di table interface
        totalHarga();
    }
    private void tampilcombo (){
        //panel pemesanan
        try {
            String sql="Select * From produk";
            Connection kon=k.getcon();
            PreparedStatement pst=kon.prepareStatement(sql);
            ResultSet rs=pst.executeQuery();
            while (rs.next()) {                
                combonama.addItem(rs.getString("nama_produk"));
            }
            rs.last();
            int jumlahdata=rs.getRow();
            rs.first();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public kasir() throws SQLException {
        initComponents();
        k.connect();
        tampilcombo();
        tabel=new DefaultTableModel();
        
        jTable1.setModel(tabel);
        info.setVisible(false);
        tabel.addColumn("No.Pemesanan");
        tabel.addColumn("Kode Produk");
        tabel.addColumn("Harga Barang");
        tabel.addColumn("Jumlah Item");
        tabel.addColumn("Total Harga");
        tabel.addColumn("Tanggal Ambil");
        tabel.addColumn("Keterangan");
        utama();
        clear1();
        tampilpsnmsk();
        tampilpsnpss();
        tampilpsnsls();
        tablpsnbsk();
        tablpsnmgg();
        Date date=new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        tanggal.setText(s.format(date));
        ttanggal.setText(s.format(date));
        tanggal1.setText(s.format(date));
        no_transaksi.requestFocus();
        tid_kons.requestFocus();
        tanggal.requestFocus();
        ttanggal.requestFocus();
        tampilTablee();
        tampiltabletransaksi();
        tampilcomboubah();
        tampilIdUser();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        menu = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        kasir = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        pemesanan = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        ubahPemesan = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        pelunasan = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        logout = new javax.swing.JLabel();
        utama = new javax.swing.JPanel();
        dashKasir = new javax.swing.JPanel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_custom1 = new custom.table_custom();
        jLabel72 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        table_custom2 = new custom.table_custom();
        jLabel110 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        textFieldSuggestion1 = new custom.TextFieldSuggestion();
        textFieldSuggestion2 = new custom.TextFieldSuggestion();
        textFieldSuggestion3 = new custom.TextFieldSuggestion();
        card1 = new custom.card();
        card2 = new custom.card();
        card3 = new custom.card();
        Pemesanan = new javax.swing.JPanel();
        notrans = new javax.swing.JLabel();
        tgl = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        tanggalAmbil = new com.toedter.calendar.JDateChooser();
        nama = new javax.swing.JLabel();
        hrg = new javax.swing.JLabel();
        jml = new javax.swing.JLabel();
        totalharga = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1 = new custom.table_custom();
        hapus = new rojerusan.RSMaterialButtonRectangle();
        bayar = new rojerusan.RSMaterialButtonRectangle();
        tambah = new rojerusan.RSMaterialButtonRectangle();
        jLabel25 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        tid_kons = new custom.TextFieldSuggestion();
        tiduser = new custom.TextFieldSuggestion();
        tanggal = new custom.TextFieldSuggestion();
        no_transaksi = new custom.TextFieldSuggestion();
        harga = new custom.TextFieldSuggestion();
        jumlah = new custom.TextFieldSuggestion();
        total_harga = new custom.TextFieldSuggestion();
        keterangan = new custom.TextFieldSuggestion();
        combonama = new custom.ComboBoxSuggestion();
        jLabel52 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        keterangan1 = new custom.TextFieldSuggestion();
        total_harga1 = new custom.TextFieldSuggestion();
        hrg1 = new javax.swing.JLabel();
        hrg2 = new javax.swing.JLabel();
        Transaksi = new javax.swing.JPanel();
        hrg8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        ubah = new rojerusan.RSMaterialButtonRectangle();
        bayar1 = new rojerusan.RSMaterialButtonRectangle();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableDetailPemesanan = new custom.table_custom();
        jLabel49 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        nopes = new custom.TextFieldSuggestion();
        tnamakons = new custom.TextFieldSuggestion();
        tanggal1 = new custom.TextFieldSuggestion();
        totalItem = new custom.TextFieldSuggestion();
        ttlpembayaran = new custom.TextFieldSuggestion();
        tkurang = new custom.TextFieldSuggestion();
        tstatus = new custom.TextFieldSuggestion();
        bayarAwalLL = new custom.TextFieldSuggestion();
        tdis = new custom.TextFieldSuggestion();
        talamat = new custom.TextFieldSuggestion();
        tnotelp = new custom.TextFieldSuggestion();
        jLabel65 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        info = new javax.swing.JLabel();
        ubahPemesanan = new javax.swing.JPanel();
        dtanggal = new com.toedter.calendar.JDateChooser();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        bubah = new rojerusan.RSMaterialButtonRectangle();
        btambah = new rojerusan.RSMaterialButtonRectangle();
        bhapus = new rojerusan.RSMaterialButtonRectangle();
        bbayar = new rojerusan.RSMaterialButtonRectangle();
        hrg3 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableTransaksi = new custom.table_custom();
        jLabel58 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        noPes = new custom.TextFieldSuggestion();
        ttanggal = new custom.TextFieldSuggestion();
        combokode = new custom.ComboBoxSuggestion();
        tharga = new custom.TextFieldSuggestion();
        tjumlah = new custom.TextFieldSuggestion();
        ttotal = new custom.TextFieldSuggestion();
        jLabel66 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        hrg4 = new javax.swing.JLabel();
        ubahTransaksi = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        hrg5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableubahpsn = new custom.table_custom();
        jButton1 = new rojerusan.RSMaterialButtonRectangle();
        jLabel62 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        noPesanan = new custom.TextFieldSuggestion();
        TotalItem = new custom.TextFieldSuggestion();
        TotalHarga = new custom.TextFieldSuggestion();
        bayarAwL = new custom.TextFieldSuggestion();
        txtKurang = new custom.TextFieldSuggestion();
        jLabel69 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        tdiskon = new custom.TextFieldSuggestion();
        jLabel112 = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        jLabel116 = new javax.swing.JLabel();
        jLabel117 = new javax.swing.JLabel();
        hrg6 = new javax.swing.JLabel();
        hrg7 = new javax.swing.JLabel();
        Pelunasan = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        print = new javax.swing.JButton();
        jLabel46 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tlunas = new custom.table_custom();
        bbyr = new rojerusan.RSMaterialButtonRectangle();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        tnotrans = new custom.TextFieldSuggestion();
        tkurrang = new custom.TextFieldSuggestion();
        tbaayar = new custom.TextFieldSuggestion();
        ttotalpmbyrn = new custom.TextFieldSuggestion();
        tstts = new custom.TextFieldSuggestion();
        tkembali = new custom.TextFieldSuggestion();
        jLabel70 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menu.setBackground(new java.awt.Color(44, 140, 136));
        menu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon("D:\\back\\Team spirit-bro (1) 2.png")); // NOI18N
        menu.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon("D:\\back\\Logo.png")); // NOI18N
        menu.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, -1, -1));

        kasir.setBackground(new java.awt.Color(44, 140, 136));
        kasir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kasirMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                kasirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                kasirMouseExited(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon("D:\\back\\Dashboard (1).png")); // NOI18N

        jLabel55.setIcon(new javax.swing.ImageIcon("D:\\back\\dashboard (1) 1.png")); // NOI18N

        javax.swing.GroupLayout kasirLayout = new javax.swing.GroupLayout(kasir);
        kasir.setLayout(kasirLayout);
        kasirLayout.setHorizontalGroup(
            kasirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kasirLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel55)
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE))
        );
        kasirLayout.setVerticalGroup(
            kasirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
            .addComponent(jLabel55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        menu.add(kasir, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 420, 490, 60));

        pemesanan.setBackground(new java.awt.Color(44, 140, 136));
        pemesanan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pemesananMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pemesananMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pemesananMouseExited(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon("D:\\back\\Pemesanan.png")); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon("D:\\back\\booking 1.png")); // NOI18N

        javax.swing.GroupLayout pemesananLayout = new javax.swing.GroupLayout(pemesanan);
        pemesanan.setLayout(pemesananLayout);
        pemesananLayout.setHorizontalGroup(
            pemesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pemesananLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addContainerGap(202, Short.MAX_VALUE))
        );
        pemesananLayout.setVerticalGroup(
            pemesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        menu.add(pemesanan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 500, 490, 60));

        ubahPemesan.setBackground(new java.awt.Color(44, 140, 136));
        ubahPemesan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ubahPemesanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ubahPemesanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ubahPemesanMouseExited(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon("D:\\back\\Ubah Pemesanan1.png")); // NOI18N

        jLabel8.setIcon(new javax.swing.ImageIcon("D:\\back\\money-transfer (2) 1.png")); // NOI18N

        javax.swing.GroupLayout ubahPemesanLayout = new javax.swing.GroupLayout(ubahPemesan);
        ubahPemesan.setLayout(ubahPemesanLayout);
        ubahPemesanLayout.setHorizontalGroup(
            ubahPemesanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ubahPemesanLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addContainerGap(96, Short.MAX_VALUE))
        );
        ubahPemesanLayout.setVerticalGroup(
            ubahPemesanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        menu.add(ubahPemesan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 580, 490, 60));

        pelunasan.setBackground(new java.awt.Color(44, 140, 136));
        pelunasan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pelunasanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pelunasanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pelunasanMouseExited(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon("D:\\back\\Pelunasan.png")); // NOI18N

        jLabel9.setIcon(new javax.swing.ImageIcon("D:\\back\\payment-method (1) 2.png")); // NOI18N

        javax.swing.GroupLayout pelunasanLayout = new javax.swing.GroupLayout(pelunasan);
        pelunasan.setLayout(pelunasanLayout);
        pelunasanLayout.setHorizontalGroup(
            pelunasanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pelunasanLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addContainerGap(228, Short.MAX_VALUE))
        );
        pelunasanLayout.setVerticalGroup(
            pelunasanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        menu.add(pelunasan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 660, 490, 60));

        logout.setBackground(new java.awt.Color(255, 255, 255));
        logout.setFont(new java.awt.Font("Tahoma", 1, 32)); // NOI18N
        logout.setForeground(new java.awt.Color(255, 255, 255));
        logout.setIcon(new javax.swing.ImageIcon("D:\\back\\arrow (1) 1.png")); // NOI18N
        logout.setText("Log Out");
        logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutMouseClicked(evt);
            }
        });
        menu.add(logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 890, -1, -1));

        jPanel1.add(menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 490, 1000));

        utama.setBackground(new java.awt.Color(255, 255, 255));
        utama.setLayout(new java.awt.CardLayout());

        dashKasir.setBackground(new java.awt.Color(255, 255, 255));
        dashKasir.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel79.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel79.setText("Lihat Detail>");
        jLabel79.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel79MouseClicked(evt);
            }
        });
        dashKasir.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 270, -1, -1));

        jLabel80.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel80.setText("Lihat Detail>");
        jLabel80.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel80MouseClicked(evt);
            }
        });
        dashKasir.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 270, -1, -1));

        jLabel109.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel109.setText("Lihat Detail>");
        jLabel109.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel109MouseClicked(evt);
            }
        });
        dashKasir.add(jLabel109, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 270, -1, -1));

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
        jScrollPane3.setViewportView(table_custom1);

        dashKasir.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 390, 1330, 240));

        jLabel72.setIcon(new javax.swing.ImageIcon("D:\\back\\DASHBOARD (2).png")); // NOI18N
        dashKasir.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 0, -1, 60));
        dashKasir.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, 270, -1, -1));

        jLabel42.setIcon(new javax.swing.ImageIcon("D:\\back\\Rectangle 11.png")); // NOI18N
        dashKasir.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1430, 60));

        table_custom2.setModel(new javax.swing.table.DefaultTableModel(
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
        table_custom2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jScrollPane6.setViewportView(table_custom2);

        dashKasir.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 700, 1330, 240));

        jLabel110.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel110.setText("Pesanan Minggu Ini");
        dashKasir.add(jLabel110, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 660, -1, -1));

        jLabel111.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel111.setText("Pesanan Besok");
        dashKasir.add(jLabel111, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, -1, -1));

        jLabel75.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel75.setIcon(new javax.swing.ImageIcon("D:\\back\\Pesanan Masuk.1.png")); // NOI18N
        jLabel75.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel75MouseClicked(evt);
            }
        });
        dashKasir.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, -1, -1));

        jLabel73.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel73.setIcon(new javax.swing.ImageIcon("D:\\back\\Proses Pemesanan.1.png")); // NOI18N
        jLabel73.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel73MouseClicked(evt);
            }
        });
        dashKasir.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 120, -1, -1));

        jLabel74.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel74.setIcon(new javax.swing.ImageIcon("D:\\back\\Pemesanan Selesai.1.png")); // NOI18N
        jLabel74.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel74MouseClicked(evt);
            }
        });
        dashKasir.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 120, -1, -1));

        textFieldSuggestion1.setForeground(new java.awt.Color(255, 255, 255));
        textFieldSuggestion1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textFieldSuggestion1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        textFieldSuggestion1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textFieldSuggestion1MouseClicked(evt);
            }
        });
        textFieldSuggestion1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldSuggestion1ActionPerformed(evt);
            }
        });
        dashKasir.add(textFieldSuggestion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 420, 210));

        textFieldSuggestion2.setForeground(new java.awt.Color(255, 255, 255));
        textFieldSuggestion2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textFieldSuggestion2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        textFieldSuggestion2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldSuggestion2ActionPerformed(evt);
            }
        });
        dashKasir.add(textFieldSuggestion2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, 420, 210));

        textFieldSuggestion3.setForeground(new java.awt.Color(255, 255, 255));
        textFieldSuggestion3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textFieldSuggestion3.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        textFieldSuggestion3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldSuggestion3ActionPerformed(evt);
            }
        });
        dashKasir.add(textFieldSuggestion3, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 100, 420, 210));

        card1.setColor1(new java.awt.Color(255, 136, 36));
        card1.setColor2(new java.awt.Color(255, 250, 229));
        dashKasir.add(card1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 420, 210));

        card2.setColor1(new java.awt.Color(255, 102, 102));
        card2.setColor2(new java.awt.Color(255, 230, 230));
        dashKasir.add(card2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, 420, 210));

        card3.setColor1(new java.awt.Color(255, 0, 0));
        card3.setColor2(new java.awt.Color(255, 233, 232));
        dashKasir.add(card3, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 100, 420, 210));

        utama.add(dashKasir, "card2");

        Pemesanan.setBackground(new java.awt.Color(255, 255, 255));
        Pemesanan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        notrans.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        notrans.setText("No.Pemesanan");
        Pemesanan.add(notrans, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 650, -1, 45));

        tgl.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        tgl.setText("Tanggal Pemesanan");
        Pemesanan.add(tgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 110, -1, 45));

        jLabel10.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel10.setText("Tanggal Pengambilan");
        Pemesanan.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 180, -1, 45));

        tanggalAmbil.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Pemesanan.add(tanggalAmbil, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 180, 240, 40));

        nama.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        nama.setText("Nama Barang");
        Pemesanan.add(nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 720, -1, 45));

        hrg.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        hrg.setText("Harga");
        Pemesanan.add(hrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 650, -1, 45));

        jml.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jml.setText("Jumlah Item");
        Pemesanan.add(jml, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 720, -1, 45));

        totalharga.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        totalharga.setText("Total Harga");
        Pemesanan.add(totalharga, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 650, -1, 45));

        jLabel11.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel11.setText("Keterangan");
        Pemesanan.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 720, -1, 45));

        jLabel21.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel21.setText("Id Konsumen");
        Pemesanan.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, 45));

        jLabel22.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel22.setText("Id User");
        Pemesanan.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, -1, 45));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "No.Transaksi", "Nama Produk", "Harga Barang", "Jumlah Item", "Total Harga", "Tanggal Ambil", "Keterangan"
            }
        ));
        jTable1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jScrollPane4.setViewportView(jTable1);

        Pemesanan.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 1340, 340));

        hapus.setBackground(new java.awt.Color(255, 81, 81));
        hapus.setText("HAPUS");
        hapus.setFont(new java.awt.Font("Roboto Medium", 1, 20)); // NOI18N
        hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusActionPerformed(evt);
            }
        });
        Pemesanan.add(hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 840, 150, 60));

        bayar.setBackground(new java.awt.Color(65, 165, 222));
        bayar.setText("BAYAR");
        bayar.setFont(new java.awt.Font("Roboto Medium", 1, 20)); // NOI18N
        bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bayarActionPerformed(evt);
            }
        });
        Pemesanan.add(bayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 830, 150, 60));

        tambah.setBackground(new java.awt.Color(65, 222, 112));
        tambah.setText("TAMBAH");
        tambah.setFont(new java.awt.Font("Roboto Medium", 1, 20)); // NOI18N
        tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahActionPerformed(evt);
            }
        });
        Pemesanan.add(tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 830, 150, 60));
        Pemesanan.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 650, -1, -1));

        jLabel60.setIcon(new javax.swing.ImageIcon("D:\\back\\10603249_42589-removebg-preview 2.png")); // NOI18N
        Pemesanan.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 900, -1, -1));

        jLabel61.setIcon(new javax.swing.ImageIcon("D:\\back\\10603249_42589-removebg-preview 1.png")); // NOI18N
        Pemesanan.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 770, 650, 230));

        tid_kons.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tid_kons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tid_konsActionPerformed(evt);
            }
        });
        Pemesanan.add(tid_kons, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 110, 240, 45));

        tiduser.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Pemesanan.add(tiduser, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, 240, 45));

        tanggal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Pemesanan.add(tanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 110, 240, 45));

        no_transaksi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Pemesanan.add(no_transaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 650, 240, 45));

        harga.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Pemesanan.add(harga, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 650, 240, 45));

        jumlah.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jumlahActionPerformed(evt);
            }
        });
        Pemesanan.add(jumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 720, 240, 45));

        total_harga.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Pemesanan.add(total_harga, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 650, 240, 45));

        keterangan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        keterangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keteranganActionPerformed(evt);
            }
        });
        Pemesanan.add(keterangan, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 720, 240, 45));

        combonama.setBackground(new java.awt.Color(214, 214, 214));
        combonama.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        combonama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combonamaActionPerformed(evt);
            }
        });
        Pemesanan.add(combonama, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 720, 240, 40));

        jLabel52.setIcon(new javax.swing.ImageIcon("D:\\back\\previous 1.1.png")); // NOI18N
        jLabel52.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel52MouseClicked(evt);
            }
        });
        Pemesanan.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(1370, 0, -1, 60));

        jLabel48.setIcon(new javax.swing.ImageIcon("D:\\back\\PEMESANAN1.png")); // NOI18N
        Pemesanan.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 0, -1, 60));

        jLabel43.setIcon(new javax.swing.ImageIcon("D:\\back\\Rectangle 11.png")); // NOI18N
        Pemesanan.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1430, -1));

        jLabel76.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        Pemesanan.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 110, -1, -1));

        jLabel77.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        Pemesanan.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 110, -1, -1));

        jLabel78.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        Pemesanan.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, -1, -1));

        jLabel81.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        Pemesanan.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 650, -1, -1));

        jLabel82.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        Pemesanan.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 720, -1, -1));

        jLabel83.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        Pemesanan.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 650, -1, -1));

        jLabel84.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        Pemesanan.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 650, -1, -1));

        jLabel85.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        Pemesanan.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 720, -1, -1));

        keterangan1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        keterangan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keterangan1ActionPerformed(evt);
            }
        });
        Pemesanan.add(keterangan1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 720, 240, 40));

        total_harga1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Pemesanan.add(total_harga1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 650, 240, 40));

        hrg1.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        hrg1.setText("Rp.");
        Pemesanan.add(hrg1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 650, -1, 40));

        hrg2.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        hrg2.setText("Rp.");
        Pemesanan.add(hrg2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 650, -1, 40));

        utama.add(Pemesanan, "card3");

        Transaksi.setBackground(new java.awt.Color(255, 255, 255));
        Transaksi.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        hrg8.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        hrg8.setText("Rp.");
        Transaksi.add(hrg8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 300, -1, 40));

        jLabel12.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel12.setText("No. Pemesanan");
        Transaksi.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, 45));

        jLabel13.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel13.setText("Tanggal Pemesanan");
        Transaksi.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 100, -1, 45));

        jLabel14.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel14.setText("Nama Konsumen");
        Transaksi.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, 45));

        jLabel15.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel15.setText("Total Item");
        Transaksi.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 160, -1, 45));

        jLabel16.setFont(new java.awt.Font("Arial Narrow", 1, 36)); // NOI18N
        jLabel16.setText("Bayar Awal");
        Transaksi.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 710, -1, 40));

        jLabel17.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel17.setText("Kurang");
        Transaksi.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 460, -1, 45));

        jLabel18.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel18.setText("Diskon");
        Transaksi.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 380, -1, 45));

        jLabel19.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel19.setText("Total Pembayaran");
        Transaksi.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 300, 180, 45));

        jLabel20.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel20.setText("Status");
        Transaksi.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 540, -1, 45));

        ubah.setBackground(new java.awt.Color(65, 165, 222));
        ubah.setText("ubah");
        ubah.setFont(new java.awt.Font("Roboto Medium", 1, 20)); // NOI18N
        ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ubahActionPerformed(evt);
            }
        });
        Transaksi.add(ubah, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 800, 150, 60));

        bayar1.setBackground(new java.awt.Color(65, 222, 112));
        bayar1.setText("bayar");
        bayar1.setFont(new java.awt.Font("Roboto Medium", 1, 20)); // NOI18N
        bayar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bayar1ActionPerformed(evt);
            }
        });
        Transaksi.add(bayar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 800, 150, 60));

        tableDetailPemesanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "No. Pemesanan", "Nama Produk", "Harga", "Jumlah", "Subtotal", "Tgl Pesan", "Keterangan"
            }
        ));
        tableDetailPemesanan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jScrollPane1.setViewportView(tableDetailPemesanan);

        Transaksi.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, 860, 600));

        jLabel49.setIcon(new javax.swing.ImageIcon("D:\\back\\10603249_42589-removebg-preview 1.png")); // NOI18N
        Transaksi.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 761, 650, 240));

        jLabel59.setIcon(new javax.swing.ImageIcon("D:\\back\\10603249_42589-removebg-preview 2.png")); // NOI18N
        Transaksi.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 900, -1, -1));

        nopes.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Transaksi.add(nopes, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, 240, 45));

        tnamakons.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Transaksi.add(tnamakons, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, 240, 45));

        tanggal1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Transaksi.add(tanggal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 100, 240, 45));

        totalItem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Transaksi.add(totalItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 160, 240, 45));

        ttlpembayaran.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Transaksi.add(ttlpembayaran, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 300, 200, 45));

        tkurang.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tkurang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tkurangActionPerformed(evt);
            }
        });
        Transaksi.add(tkurang, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 460, 240, 45));

        tstatus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Transaksi.add(tstatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 540, 240, 45));

        bayarAwalLL.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        bayarAwalLL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bayarAwalLLMouseClicked(evt);
            }
        });
        bayarAwalLL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bayarAwalLLActionPerformed(evt);
            }
        });
        Transaksi.add(bayarAwalLL, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 700, 200, 60));

        tdis.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Transaksi.add(tdis, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 380, 240, 45));

        talamat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Transaksi.add(talamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 100, 240, 45));

        tnotelp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tnotelp.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                tnotelpComponentAdded(evt);
            }
        });
        tnotelp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tnotelpMouseClicked(evt);
            }
        });
        tnotelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnotelpActionPerformed(evt);
            }
        });
        Transaksi.add(tnotelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 155, 240, 45));

        jLabel65.setIcon(new javax.swing.ImageIcon("D:\\back\\previous 1.png")); // NOI18N
        jLabel65.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel65MouseClicked(evt);
            }
        });
        Transaksi.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 50, 60));

        jLabel67.setIcon(new javax.swing.ImageIcon("D:\\back\\TRANSAKSI (1).png")); // NOI18N
        Transaksi.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 0, -1, 60));

        jLabel44.setIcon(new javax.swing.ImageIcon("D:\\back\\Rectangle 11.png")); // NOI18N
        Transaksi.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(-50, 0, 1480, -1));

        jLabel86.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        Transaksi.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 540, -1, -1));

        jLabel87.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        Transaksi.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, -1, -1));

        jLabel88.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        Transaksi.add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, -1, -1));

        jLabel89.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        Transaksi.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 100, -1, -1));

        jLabel90.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        Transaksi.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 160, -1, -1));

        jLabel91.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        Transaksi.add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 380, 240, -1));

        jLabel92.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        Transaksi.add(jLabel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 460, -1, -1));

        jLabel93.setIcon(new javax.swing.ImageIcon("D:\\back\\Rectangle 49.2.png")); // NOI18N
        Transaksi.add(jLabel93, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 700, -1, -1));

        jLabel94.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        Transaksi.add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 300, 240, -1));

        jLabel95.setIcon(new javax.swing.ImageIcon("D:\\back\\percent.png")); // NOI18N
        Transaksi.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(1360, 380, -1, 45));

        jLabel107.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        Transaksi.add(jLabel107, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 100, -1, -1));

        jLabel108.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        Transaksi.add(jLabel108, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 155, 240, -1));

        jLabel23.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel23.setText("No. Telepon");
        Transaksi.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 160, -1, 45));

        jLabel24.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel24.setText("Alamat");
        Transaksi.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 100, -1, 45));

        jLabel118.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        Transaksi.add(jLabel118, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 780, -1, -1));

        info.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        info.setForeground(new java.awt.Color(255, 51, 51));
        info.setText("ak");
        Transaksi.add(info, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 210, 240, 30));

        utama.add(Transaksi, "card4");

        ubahPemesanan.setBackground(new java.awt.Color(255, 255, 255));
        ubahPemesanan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dtanggal.setBackground(new java.awt.Color(229, 229, 229));
        dtanggal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dtanggal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dtanggalMouseClicked(evt);
            }
        });
        ubahPemesanan.add(dtanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 260, 240, 45));

        jLabel26.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel26.setText("Tanggal Pemesanan");
        ubahPemesanan.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 130, -1, 45));

        jLabel27.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel27.setText("Harga");
        ubahPemesanan.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 420, -1, 45));

        jLabel28.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel28.setText("Jumlah");
        ubahPemesanan.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 490, 90, 45));

        jLabel29.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel29.setText("Total Harga");
        ubahPemesanan.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 560, -1, 45));

        jLabel30.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel30.setText("Tanggal Pengambilan");
        ubahPemesanan.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 260, -1, 45));

        bubah.setBackground(new java.awt.Color(65, 165, 222));
        bubah.setText("ubah");
        bubah.setFont(new java.awt.Font("Roboto Medium", 1, 20)); // NOI18N
        bubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bubahActionPerformed(evt);
            }
        });
        ubahPemesanan.add(bubah, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 640, 150, 60));

        btambah.setBackground(new java.awt.Color(65, 222, 112));
        btambah.setText("tambah");
        btambah.setFont(new java.awt.Font("Roboto Medium", 1, 20)); // NOI18N
        btambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btambahActionPerformed(evt);
            }
        });
        ubahPemesanan.add(btambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 850, 150, 60));

        bhapus.setBackground(new java.awt.Color(255, 81, 81));
        bhapus.setText("hapus");
        bhapus.setFont(new java.awt.Font("Roboto Medium", 1, 20)); // NOI18N
        bhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bhapusActionPerformed(evt);
            }
        });
        ubahPemesanan.add(bhapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 850, 150, 60));

        bbayar.setBackground(new java.awt.Color(0, 153, 153));
        bbayar.setText("bayar");
        bbayar.setFont(new java.awt.Font("Roboto Medium", 1, 20)); // NOI18N
        bbayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bbayarActionPerformed(evt);
            }
        });
        ubahPemesanan.add(bbayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 640, 150, 60));

        hrg3.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        hrg3.setText("Rp.");
        ubahPemesanan.add(hrg3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 560, -1, 40));

        tableTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "No. Pemesanan", "Tgl Pesan", "Total Item", "Bayar Awal", "Kurang", "Total Bayar", "Diskon"
            }
        ));
        tableTransaksi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tableTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableTransaksiMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tableTransaksi);

        ubahPemesanan.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, 830, 570));

        jLabel58.setIcon(new javax.swing.ImageIcon("D:\\back\\minimal-shopping-cart-shopping-concept-orange-background-3d-rendering-removebg-preview 1.png")); // NOI18N
        ubahPemesanan.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 660, -1, -1));

        jLabel50.setIcon(new javax.swing.ImageIcon("D:\\back\\Group 11.png")); // NOI18N
        ubahPemesanan.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 830, 580, -1));

        jLabel56.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel56.setText("Nama Produk");
        ubahPemesanan.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 340, -1, 45));

        jLabel57.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel57.setText("No. Pemesanan");
        ubahPemesanan.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, -1, 45));

        noPes.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        noPes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noPesActionPerformed(evt);
            }
        });
        ubahPemesanan.add(noPes, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 130, 240, 45));

        ttanggal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ubahPemesanan.add(ttanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 130, 240, 45));

        combokode.setBackground(new java.awt.Color(229, 229, 229));
        combokode.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        combokode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combokodeActionPerformed(evt);
            }
        });
        ubahPemesanan.add(combokode, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 340, 240, 45));

        tharga.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ubahPemesanan.add(tharga, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 410, 240, 45));

        tjumlah.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tjumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tjumlahActionPerformed(evt);
            }
        });
        ubahPemesanan.add(tjumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 490, 240, 45));

        ttotal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ttotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ttotalActionPerformed(evt);
            }
        });
        ubahPemesanan.add(ttotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 560, 240, 45));

        jLabel66.setIcon(new javax.swing.ImageIcon("D:\\back\\previous 1.1.png")); // NOI18N
        jLabel66.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel66MouseClicked(evt);
            }
        });
        ubahPemesanan.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(1370, 0, -1, 60));

        jLabel68.setIcon(new javax.swing.ImageIcon("D:\\back\\Ubah Pemesanan.png")); // NOI18N
        ubahPemesanan.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 0, -1, 60));

        jLabel53.setIcon(new javax.swing.ImageIcon("D:\\back\\Rectangle 11.png")); // NOI18N
        ubahPemesanan.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(-50, 0, 1480, -1));

        jLabel96.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        ubahPemesanan.add(jLabel96, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 560, -1, -1));

        jLabel97.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        ubahPemesanan.add(jLabel97, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 130, -1, -1));

        jLabel98.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        ubahPemesanan.add(jLabel98, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 130, -1, -1));

        jLabel99.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        ubahPemesanan.add(jLabel99, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 410, -1, -1));

        jLabel100.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        ubahPemesanan.add(jLabel100, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 490, -1, -1));

        hrg4.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        hrg4.setText("Rp.");
        ubahPemesanan.add(hrg4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 410, -1, 40));

        utama.add(ubahPemesanan, "card6");

        ubahTransaksi.setBackground(new java.awt.Color(255, 255, 255));
        ubahTransaksi.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel31.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel31.setText("No. Pemesanan");
        ubahTransaksi.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 180, 40));

        jLabel32.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel32.setText("Total Item");
        ubahTransaksi.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 240, 100, 40));

        jLabel33.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel33.setText("Total Pembayaran");
        ubahTransaksi.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 310, 170, 30));

        jLabel34.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel34.setText("Bayar Awal");
        ubahTransaksi.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 240, 120, 40));

        jLabel35.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel35.setText("Kurang");
        ubahTransaksi.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 310, 110, 40));

        jLabel36.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel36.setText("Diskon");
        ubahTransaksi.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 170, 90, 40));

        hrg5.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        hrg5.setText("Rp.");
        ubahTransaksi.add(hrg5, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 240, -1, 40));

        tableubahpsn.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "No.Pemesanan", "Nama Produk", "Harga", "Jumlah", "Total Harga", "Tanggal Ambil", "Keterangan"
            }
        ));
        tableubahpsn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jScrollPane2.setViewportView(tableubahpsn);

        ubahTransaksi.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 420, 1030, -1));

        jButton1.setBackground(new java.awt.Color(65, 222, 112));
        jButton1.setText("bayar");
        jButton1.setFont(new java.awt.Font("Roboto Medium", 1, 20)); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        ubahTransaksi.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 800, 150, 60));

        jLabel62.setIcon(new javax.swing.ImageIcon("D:\\back\\10603249_42589-removebg-preview 2.png")); // NOI18N
        ubahTransaksi.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 900, -1, -1));

        jLabel51.setIcon(new javax.swing.ImageIcon("D:\\back\\10603249_42589-removebg-preview 1.png")); // NOI18N
        ubahTransaksi.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 701, 650, 300));

        noPesanan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        noPesanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noPesananActionPerformed(evt);
            }
        });
        ubahTransaksi.add(noPesanan, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 170, 240, 45));

        TotalItem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        TotalItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TotalItemActionPerformed(evt);
            }
        });
        ubahTransaksi.add(TotalItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 240, 240, 45));

        TotalHarga.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ubahTransaksi.add(TotalHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 300, 240, 45));

        bayarAwL.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        bayarAwL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bayarAwLActionPerformed(evt);
            }
        });
        ubahTransaksi.add(bayarAwL, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 240, 240, 45));

        txtKurang.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtKurang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKurangActionPerformed(evt);
            }
        });
        ubahTransaksi.add(txtKurang, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 310, 240, 45));

        jLabel69.setIcon(new javax.swing.ImageIcon("D:\\back\\UBAH TRANSAKSI.png")); // NOI18N
        ubahTransaksi.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 0, -1, 60));

        jLabel71.setIcon(new javax.swing.ImageIcon("D:\\back\\previous 1.png")); // NOI18N
        jLabel71.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel71MouseClicked(evt);
            }
        });
        ubahTransaksi.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 50, 60));

        jLabel54.setIcon(new javax.swing.ImageIcon("D:\\back\\Rectangle 11.png")); // NOI18N
        ubahTransaksi.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1430, -1));

        tdiskon.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tdiskon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tdiskonActionPerformed(evt);
            }
        });
        ubahTransaksi.add(tdiskon, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 170, 240, 45));

        jLabel112.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        ubahTransaksi.add(jLabel112, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 310, -1, -1));

        jLabel113.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        ubahTransaksi.add(jLabel113, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 170, -1, -1));

        jLabel114.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        ubahTransaksi.add(jLabel114, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 240, -1, -1));

        jLabel115.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        ubahTransaksi.add(jLabel115, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 300, -1, -1));

        jLabel116.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        ubahTransaksi.add(jLabel116, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 170, -1, -1));

        jLabel117.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        ubahTransaksi.add(jLabel117, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 240, -1, -1));

        hrg6.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        hrg6.setText("Rp.");
        ubahTransaksi.add(hrg6, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 300, -1, 50));

        hrg7.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        hrg7.setText("Rp.");
        ubahTransaksi.add(hrg7, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 310, -1, 40));

        utama.add(ubahTransaksi, "card7");

        Pelunasan.setBackground(new java.awt.Color(255, 255, 255));
        Pelunasan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel37.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel37.setText("No. Pemesanan");
        Pelunasan.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 150, 160, 45));

        jLabel38.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel38.setText("Kurang");
        Pelunasan.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 280, 170, 45));

        jLabel39.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel39.setText("Bayar");
        Pelunasan.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 370, 170, 45));

        jLabel40.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel40.setText("Total Pembayaran");
        Pelunasan.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 460, 170, 45));

        jLabel41.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel41.setText("Status");
        Pelunasan.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 540, 170, 45));

        print.setBackground(new java.awt.Color(255, 255, 255));
        print.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        print.setIcon(new javax.swing.ImageIcon("D:\\back\\printer.png")); // NOI18N
        print.setBorder(null);
        print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printActionPerformed(evt);
            }
        });
        Pelunasan.add(print, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 750, 90, 70));

        jLabel46.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel46.setText("Kembali");
        Pelunasan.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 620, 170, 45));

        tlunas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No. Pemesanan", "Total Bayar", "Kurang", "Status", "Bayar Awal"
            }
        ));
        tlunas.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tlunas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tlunasMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tlunas);

        Pelunasan.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, 850, 560));

        bbyr.setBackground(new java.awt.Color(65, 222, 112));
        bbyr.setText("bayar");
        bbyr.setFont(new java.awt.Font("Roboto Medium", 1, 20)); // NOI18N
        bbyr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bbyrActionPerformed(evt);
            }
        });
        Pelunasan.add(bbyr, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 760, 160, 60));

        jLabel63.setIcon(new javax.swing.ImageIcon("D:\\back\\10603249_42589-removebg-preview 1.png")); // NOI18N
        Pelunasan.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 680, 650, 300));

        jLabel64.setIcon(new javax.swing.ImageIcon("D:\\back\\10603249_42589-removebg-preview 2.png")); // NOI18N
        Pelunasan.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 880, -1, -1));

        tnotrans.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tnotrans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnotransActionPerformed(evt);
            }
        });
        Pelunasan.add(tnotrans, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, 240, 45));

        tkurrang.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Pelunasan.add(tkurrang, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 280, 240, 45));

        tbaayar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tbaayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbaayarActionPerformed(evt);
            }
        });
        Pelunasan.add(tbaayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 370, 240, 45));

        ttotalpmbyrn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Pelunasan.add(ttotalpmbyrn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 460, 240, 45));

        tstts.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Pelunasan.add(tstts, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 540, 240, 45));

        tkembali.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Pelunasan.add(tkembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 620, 240, 45));

        jLabel70.setIcon(new javax.swing.ImageIcon("D:\\back\\PELUNASAN1.png")); // NOI18N
        Pelunasan.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 0, -1, 60));

        jLabel45.setIcon(new javax.swing.ImageIcon("D:\\back\\Rectangle 11.png")); // NOI18N
        Pelunasan.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1430, 60));

        jLabel101.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        Pelunasan.add(jLabel101, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 620, -1, -1));

        jLabel102.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        Pelunasan.add(jLabel102, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, -1, -1));

        jLabel103.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        Pelunasan.add(jLabel103, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 280, -1, -1));

        jLabel104.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        Pelunasan.add(jLabel104, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 370, -1, -1));

        jLabel105.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        Pelunasan.add(jLabel105, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 460, -1, -1));

        jLabel106.setIcon(new javax.swing.ImageIcon("D:\\back\\49.1.png")); // NOI18N
        Pelunasan.add(jLabel106, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 540, -1, -1));

        utama.add(Pelunasan, "card5");

        jPanel1.add(utama, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 0, 1430, 1000));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void kasirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kasirMouseClicked
        // TODO add your handling code here:
        utama.removeAll();
        utama.repaint();
        utama.revalidate();
        
        utama.add(dashKasir);
        utama.repaint();
        utama.revalidate();
        tampilpsnmsk();
        tampilpsnpss();
        tampilpsnsls();
        tablpsnbsk();
        tablpsnmgg();
    }//GEN-LAST:event_kasirMouseClicked

    private void kasirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kasirMouseEntered
        // TODO add your handling code here:
        kasir.setBackground(new Color(240,180,107));
        kasir.setOpaque(true);
    }//GEN-LAST:event_kasirMouseEntered

    private void kasirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kasirMouseExited
        // TODO add your handling code here:
        kasir.setBackground(new Color(44,140,136));
    }//GEN-LAST:event_kasirMouseExited

    private void pemesananMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pemesananMouseClicked
        // TODO add your handling code here:
        utama.removeAll();
        utama.repaint();
        utama.revalidate();
        
        utama.add(Pemesanan);
        utama.repaint();
        utama.revalidate();
    }//GEN-LAST:event_pemesananMouseClicked

    private void pemesananMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pemesananMouseEntered
        // TODO add your handling code here:
        pemesanan.setBackground(new Color(240,180,107));
        pemesanan.setOpaque(true);
    }//GEN-LAST:event_pemesananMouseEntered

    private void pemesananMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pemesananMouseExited
        // TODO add your handling code here:
        pemesanan.setBackground(new Color(44,140,136));
    }//GEN-LAST:event_pemesananMouseExited

    private void ubahPemesanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ubahPemesanMouseClicked
        // TODO add your handling code here:
        utama.removeAll();
        utama.repaint();
        utama.revalidate();
        
        utama.add(ubahPemesanan);
        utama.repaint();
        utama.revalidate();
        utama();
        Date date=new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        tanggal.setText(s.format(date));
        tanggal1.setText(s.format(date));
        no_transaksi.requestFocus();
        tid_kons.requestFocus();
        tanggal.requestFocus();
        tanggal1.requestFocus();
        tampilIdUser();
        tampilTablee();
        tampiltabletransaksi();
        tampilIdUser();
        tampilpsnmsk();
        tampilpsnpss();
        tampilpsnsls();
        tablpsnbsk();
        tablpsnmgg();
    }//GEN-LAST:event_ubahPemesanMouseClicked

    private void ubahPemesanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ubahPemesanMouseEntered
        // TODO add your handling code here:
        ubahPemesan.setBackground(new Color(240,180,107));
        ubahPemesan.setOpaque(true);
    }//GEN-LAST:event_ubahPemesanMouseEntered

    private void ubahPemesanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ubahPemesanMouseExited
        // TODO add your handling code here:
        ubahPemesan.setBackground(new Color(44,140,136));
    }//GEN-LAST:event_ubahPemesanMouseExited

    private void pelunasanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pelunasanMouseClicked
        // TODO add your handling code here:
        utama.removeAll();
        utama.repaint();
        utama.revalidate();
        
        utama.add(Pelunasan);
        utama.repaint();
        utama.revalidate();
        tkurrang.setText("");
        ttotalpmbyrn.setText("");
        tstts.setText("");
        tkembali.setText("");
        tnotrans.setText("");
        tbaayar.setText("");
        tampilTablee();
        
    }//GEN-LAST:event_pelunasanMouseClicked

    private void pelunasanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pelunasanMouseEntered
        // TODO add your handling code here:
        pelunasan.setBackground(new Color(240,180,107));
        pelunasan.setOpaque(true);
    }//GEN-LAST:event_pelunasanMouseEntered

    private void pelunasanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pelunasanMouseExited
        // TODO add your handling code here:
        pelunasan.setBackground(new Color(44,140,136));
    }//GEN-LAST:event_pelunasanMouseExited

    private void logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseClicked
        notif obj = new notif(this);
        obj.showMessage("Peringatan", "Apakah Anda Yakin Ingin Keluar");
        if (obj.getMessageType() == notif.MessageType.OK) {
            new login().setVisible(true);
        } else {
            System.out.println("User Click Batal");
        }
        this.dispose();
    }//GEN-LAST:event_logoutMouseClicked

    private void bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bayarActionPerformed
    try {                                      
        try {
            String sql="insert into konsumen values ('"+tid_kons.getText()+"','','','')";
            Connection con=k.getcon();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.execute();
            ps.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        setergeter1.setNoTrans(no_transaksi.getText().toString());
        DefaultTableModel tabel=(DefaultTableModel) jTable1.getModel();
        try {
            String sql = "INSERT INTO pemesanan VALUES"
                    + "('"+no_transaksi.getText()+"',now(),'0','0','0','0','0','','0','"+tiduser.getText()+"','"
                    +tid_kons.getText()+"')";
            Connection conn = k.getcon();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            pst.close();
        } catch (Exception e) {
        }
        try {
            int jumlahbaris = jTable1.getRowCount();
            for(int i=0; i<=jumlahbaris; i++){
                String sql = "INSERT INTO detail_pemesanan VALUES('"
                        +jTable1.getValueAt(i, 0)+"','"+jTable1.getValueAt(i, 1)+
                        "','"+jTable1.getValueAt(i,2)+"','"+jTable1.getValueAt(i, 3)+"','"+jTable1.getValueAt(i, 4)+"','"+jTable1.getValueAt(i, 5)
                        +"','"+jTable1.getValueAt(i, 6)+"')";
                Connection conn = k.getcon();
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.execute();
                pst.close();}
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Berhasil Tersimpan");
            if (obj.getMessageType() == notif.MessageType.OK) {
                System.out.println("User click ya");
            } else {
                System.out.println("User click batal");
            }
            jTable1.setModel(tabel);
        } catch (Exception e) {
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Berhasil Menambahkan Barang");
            if (obj.getMessageType() == notif.MessageType.OK) {
                System.out.println("User click ya");
                utama.removeAll();
                utama.repaint();
                utama.revalidate();
        
                utama.add(Transaksi);
                utama.repaint();
                utama.revalidate();
            } else {
                System.out.println("User click batal");
            }
        }
        
        tampilNopes();
        tampilTable();
        totalHarga1();
        jumlahItem();
        tungdis();
        ngitungdis();
        Date date=new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        tanggal.setText(s.format(date));
        tanggal.requestFocus();
    } catch (SQLException ex) {
        Logger.getLogger(kasir.class.getName()).log(Level.SEVERE, null, ex);
    }     
    }//GEN-LAST:event_bayarActionPerformed

    private void hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusActionPerformed
        // TODO add your handling code here:
        DefaultTableModel tabel= (DefaultTableModel) jTable1.getModel();
        int row= jTable1.getSelectedRow();
        tabel.removeRow(row);
        totalHarga();
    }//GEN-LAST:event_hapusActionPerformed

    private void tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahActionPerformed
        // TODO add your handling code here:
        tambahTransaksi();
        totalHarga();
        jumlah.setText("");
        harga.setText("");
        total_harga.setText("");
        keterangan.setText("");
    }//GEN-LAST:event_tambahActionPerformed

    private void bayar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bayar1ActionPerformed
        try {
            String sql="UPDATE konsumen SET nama_konsumen='"+tnamakons.getText()+"', alamat_konsumen='"
                    +talamat.getText()+"', notelp='"+tnotelp.getText()+"' WHERE alamat_konsumen='' AND notelp='';";
            Connection c=k.getcon();
            PreparedStatement p=c.prepareStatement(sql);
            p.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        try {
            String sql="update pemesanan set total_item ='"+totalItem.getText()+"', bayar_awal ='"
                    +bayarAwalLL.getText()+"', kurang ='"+tkurang.getText()+"', total_pembayaran ='"
                    +ttlpembayaran.getText()+"', diskon ='"+tdis.getText()+"',  status='"+tstatus.getText()
                    +"' where no_pemesanan ='"+nopes.getText()+"'";
            Connection con=k.getcon();
            PreparedStatement stm=con.prepareStatement(sql);
            stm.executeUpdate();
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Pembayaran DP Berhasil");
            if (obj.getMessageType() == notif.MessageType.OK) {
                System.out.println("User click ya");
            } else {
                System.out.println("User click batal");
            }
            tampilcomboubah();
            utama.removeAll();
        utama.repaint();
        utama.revalidate();
        
        utama.add(Pemesanan);
        utama.repaint();
        utama.revalidate();
        } catch (Exception e) {
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Gagal Melakukan Pembayaran DP");
            if (obj.getMessageType() == notif.MessageType.OK) {
                System.out.println("User click ya");
            } else {
                System.out.println("User click batal");
            }
        }
        tampiltabletransaksi();
        clear1();
        kosong();
        Date date=new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        tanggal.setText(s.format(date));
        try {
        Connection c =k.getcon();
        
        try{
            String report = ("C:\\Users\\lenovo\\Documents\\NetBeansProjects\\sriknd\\src\\sriknd\\report1.jrxml");
            HashMap hash = new HashMap();
            hash.put("no", nopes.getText());
            JasperReport JRpt = JasperCompileManager.compileReport(report);
            JasperPrint JPrint = JasperFillManager.fillReport(JRpt,hash,c);
            JasperViewer.viewReport(JPrint, false);
        }catch(Exception rptexcpt){
            JOptionPane.showMessageDialog(this, rptexcpt.getMessage());
        }
        }catch(Exception e){
            System.out.println(e);
        }
        bayarAwalLL.setText("");
    }//GEN-LAST:event_bayar1ActionPerformed

    private void dtanggalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dtanggalMouseClicked

    }//GEN-LAST:event_dtanggalMouseClicked

    private void ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ubahActionPerformed
        utama.removeAll();
        utama.repaint();
        utama.revalidate();
         utama.add(ubahPemesanan);
        utama.repaint();
        utama.revalidate();
        tampilcomboubah();
        
        Date date=new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        ttanggal.setText(s.format(date));
        ttanggal.requestFocus();
    }//GEN-LAST:event_ubahActionPerformed

    private void combonamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combonamaActionPerformed
        try {
            String sql="SELECT * FROM produk where nama_produk='"+combonama.getSelectedItem()+"'";
            Connection c=k.getcon();
            PreparedStatement p=c.prepareStatement(sql);
            ResultSet rs=p.executeQuery();
            while (rs.next()) {
            harga.setText(rs.getString("harga_jual"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_combonamaActionPerformed

    private void jumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jumlahActionPerformed
        int jmlh, hrg, total;
        jmlh = Integer.valueOf(jumlah.getText());
        hrg = Integer.valueOf(harga.getText());
        total = jmlh*hrg;
        total_harga.setText(String.valueOf(total));
        keterangan.setText("Pesanan Masuk");
    }//GEN-LAST:event_jumlahActionPerformed

    private void bayarAwalLLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bayarAwalLLActionPerformed

        int bayarawal, totalpmbyrn, kurang,persen;
        bayarawal = Integer.valueOf(bayarAwalLL.getText());
        totalpmbyrn = Integer.valueOf(tkurang.getText());
        kurang = totalpmbyrn-bayarawal;
        persen=totalpmbyrn*20/100;
        this.tkurang.setText(String.valueOf(kurang));
        if (bayarawal<persen) {
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Pembayaran Awal Minimal 20% /n sebesar Rp."+String.valueOf(persen));
            if (obj.getMessageType() == notif.MessageType.OK) {
                System.out.println("User click ya");
        int total, dis, hasil, totall;
        total=Integer.valueOf(ttlpembayaran.getText());
        dis=Integer.valueOf(tdis.getText());
        hasil=total*dis/100;
        totall=total-hasil;
        tkurang.setText(String.valueOf(totall));
            } else {
                System.out.println("User click batal");
            }
        } else {
            tstatus.setText("Belum lunas");
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Pembayaran Awal Sudah Memenuhi 20%");
            if (obj.getMessageType() == notif.MessageType.OK) {
                System.out.println("User click ya");
            } else {
                System.out.println("User click batal");
            }
        }
    }//GEN-LAST:event_bayarAwalLLActionPerformed

    private void noPesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noPesActionPerformed
        // TODO add your handling code here:
        DefaultTableModel mdl=new DefaultTableModel();
        mdl.addColumn(("No. Pemesanan"));
        mdl.addColumn(("Kode Produk"));
        mdl.addColumn("Harga Satuan");
        mdl.addColumn(("Jumlah"));
        mdl.addColumn(("Total Harga"));
        mdl.addColumn("Tanggal Pengambilan");

        try {
            int no=1;
            String sql="select * from detail_pemesanan where no_pemesanan= '"+noPes.getText()+"'";
            Connection c=k.getcon();
            PreparedStatement p=c.prepareStatement(sql);
            ResultSet rs=p.executeQuery();
            while (rs.next()) {
                mdl.addRow(new Object[] {
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6)});

            tableTransaksi.setModel(mdl);
        }
        } catch (Exception e) {
        }
        
    }//GEN-LAST:event_noPesActionPerformed

    private void combokodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combokodeActionPerformed
        // TODO add your handling code here:
        try {
            String sql = "SELECT * FROM produk where nama_produk='"+combokode.getSelectedItem()+"'";
            java.sql.Connection con = k.getcon();
            java.sql.PreparedStatement pst = con.prepareStatement(sql);
            java.sql.ResultSet rst = pst.executeQuery();
            while(rst.next()){
                tharga.setText(rst.getString("harga_jual"));
                tjumlah.setText("");
                ttotal.setText("");
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_combokodeActionPerformed

    private void tjumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tjumlahActionPerformed
        // TODO add your handling code here:
        int jumlah,harga,total;
        jumlah=Integer.valueOf(tjumlah.getText());
        harga=Integer.valueOf(tharga.getText());
        total=jumlah*harga;
        ttotal.setText(String.valueOf(total));
    }//GEN-LAST:event_tjumlahActionPerformed

    private void bubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bubahActionPerformed
        // TODO add your handling code here:
        TableModel model=tableTransaksi.getModel();
        int i=tableTransaksi.getSelectedRow();
        String getDate = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(getDate);
        String tanggal = String.valueOf(fm.format(dtanggal.getDate()));
        String produk=model.getValueAt(i, 1).toString();
        String tgl=model.getValueAt(i, 5).toString();
        try {
            String sql="update detail_pemesanan set jumlah= '"+tjumlah.getText()
            +"', total_harga= '"+ttotal.getText()+"', tanggal_ambil= '"
            +tanggal+"' where no_pemesanan= '"+noPes.getText()+"' and kode_produk= '"
            +produk+"' and tanggal_ambil= '"+tgl+"'";
            Connection c=k.getcon();
            PreparedStatement p=c.prepareStatement(sql);
            p.executeUpdate();
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Berhasil Mengubah Pesanan");
            if (obj.getMessageType() == notif.MessageType.OK) {
                System.out.println("User click ya");
            } else {
                System.out.println("User click batal");
            }
            noPesActionPerformed(evt);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        tharga.setText("");
        tjumlah.setText("");
        ttotal.setText("");
        dtanggal.setDate(null);
    }//GEN-LAST:event_bubahActionPerformed

    private void bhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bhapusActionPerformed
        try {
            DefaultTableModel model=new DefaultTableModel();
            model.addColumn(("No. Pemesanan"));
            model.addColumn(("Tanggal Transaksi"));
            model.addColumn(("Total Item"));
            model.addColumn(("Total Harga"));
            model.addColumn("Bayar Awal");
            model.addColumn("Kurang");
            model.addColumn("Kode Diskon");
            model.addColumn("Id Karyawan");
            String sql="delete from pemesanan where no_pemesanan= '"+nopes.getText()+"'";
            Connection c=k.getcon();
            PreparedStatement p=c.prepareStatement(sql);
            p.execute();
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Transaksi Berhasil Digapus");
            if (obj.getMessageType() == notif.MessageType.OK) {
                System.out.println("User click ya");
            } else {
                System.out.println("User click batal");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        try {
            String sql="delete from detail_pemesanan where no_pemesanan= '"+nopes.getText()+"'";
            Connection c=k.getcon();
            PreparedStatement p=c.prepareStatement(sql);
            p.execute();
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Transaksi Berhasil Dihapus");
            if (obj.getMessageType() == notif.MessageType.OK) {
                System.out.println("User click ya");
            } else {
                System.out.println("User click batal");
            }
            tampilTable();
        } catch (Exception e) {
        }
    try {
        tampilTable();
    } catch (SQLException ex) {
        Logger.getLogger(kasir.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_bhapusActionPerformed

    private void btambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btambahActionPerformed
       //tambahTransaksi();
        String getDate = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(getDate);
        String tanggal = String.valueOf(fm.format(dtanggal.getDate()));
        String kode=null;
        try {
            String sql="select kode_produk from produk where nama_produk='"+combokode.getSelectedItem()+"'";
            Connection c=k.getcon();
            PreparedStatement p=c.prepareStatement(sql);
            ResultSet r=p.executeQuery();
            if (r.next()) {
            kode=r.getString("kode_produk");
            }
        } catch (Exception e) {
        }
        try {
            String sql="insert into detail_pemesanan values ('"+noPes.getText()+"','"
            +String.valueOf(kode)+"','"+tharga.getText()+"','"+tjumlah.getText()+"','"
            +ttotal.getText()+"','"+tanggal+"','Pesanan masuk')";
            Connection c=k.getcon();
            PreparedStatement p=c.prepareStatement(sql);
            p.execute();
            p.close();
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Berhasil Menambahkan Produk");
            if (obj.getMessageType() == notif.MessageType.OK) {
                System.out.println("User click ya");
            } else {
                System.out.println("User click batal");
            }
        } catch (Exception e) {
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Gagal Menambahkan Produk");
            if (obj.getMessageType() == notif.MessageType.OK) {
                System.out.println("User click ya");
            } else {
                System.out.println("User click batal");
            }
        }
        loadDataubahtransaksi();
    }//GEN-LAST:event_btambahActionPerformed

    private void bbayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbayarActionPerformed
        utama.removeAll();
        utama.repaint();
        utama.revalidate();
        
        utama.add(ubahTransaksi);
        utama.repaint();
        utama.revalidate();
        tampilNoPesanan();
        tampilTabl();
        totalitemubah();
        totalubahpesan();
        bayarawalubah();
        tungdisubah();
        ngitungdisubah();
    }//GEN-LAST:event_bbayarActionPerformed

    private void tableTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTransaksiMouseClicked
        // TODO add your handling code here:
        int i = tableTransaksi.getSelectedRow();
        TableModel model = tableTransaksi.getModel();
        String nopess=model.getValueAt(i, 0).toString();
        String tanggal=model.getValueAt(i, 1).toString();
        String harga=model.getValueAt(i, 2).toString();
        String jumlah=model.getValueAt(i, 3).toString();
        noPes.setText(nopess);
        tharga.setText(harga);
        tjumlah.setText(jumlah);
    }//GEN-LAST:event_tableTransaksiMouseClicked

    private void combodskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combodskActionPerformed
        txtKurang.setText("");
        
    }//GEN-LAST:event_combodskActionPerformed

    private void bayarAwLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bayarAwLActionPerformed
        int bayarawal, totalpmbyrn, kurang,persen;
        bayarawal = Integer.valueOf(bayarAwL.getText());
        totalpmbyrn = Integer.valueOf(this.txtKurang.getText());
        int tobayar=Integer.valueOf(TotalHarga.getText());
        kurang = totalpmbyrn-bayarawal;
        persen=totalpmbyrn*20/100;
        this.txtKurang.setText(String.valueOf(kurang));
        if (bayarawal<persen) {
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Pembayaran Awal Minimal 20% /n DP harus sebesar Rp."+ String.valueOf(persen));
            if (obj.getMessageType() == notif.MessageType.OK) {
                System.out.println("User click ya");
        int total, dis, hasil, totall;
        total=Integer.valueOf(TotalHarga.getText());
        dis=Integer.valueOf(tdiskon.getText());
        hasil=total*dis/100;
        totall=total-hasil;
        txtKurang.setText(String.valueOf(totall));
            
            } else {
                System.out.println("User click batal");
            }
        } else {
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Pembayaran Sudah Memenuhi 20%");
            if (obj.getMessageType() == notif.MessageType.OK) {
                System.out.println("User click ya");
            } else {
                System.out.println("User click batal");
            }
        }
    }//GEN-LAST:event_bayarAwLActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            String sql="update pemesanan set total_item ='"+TotalItem.getText()+"', bayar_awal ='"
            +bayarAwL.getText()+"', kurang ='"+txtKurang.getText()+"', total_pembayaran ='"
            +TotalHarga.getText()+"', diskon ='"+tdiskon.getText()
            +"' where no_pemesanan ='"+noPesanan.getText()+"'";
            Connection con=k.getcon();
            PreparedStatement stm=con.prepareStatement(sql);
            stm.executeUpdate();
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Pesanan Telah Diperbarui");
            if (obj.getMessageType() == notif.MessageType.OK) {
                System.out.println("User click ya");
        try {
        Connection c =k.getcon();
        
        try{
            String report = ("C:\\Users\\lenovo\\Documents\\NetBeansProjects\\sriknd\\src\\sriknd\\report1.jrxml");
            HashMap hash = new HashMap();
            hash.put("no", noPesanan.getText());
            JasperReport JRpt = JasperCompileManager.compileReport(report);
            JasperPrint JPrint = JasperFillManager.fillReport(JRpt,hash,c);
            JasperViewer.viewReport(JPrint, false);
        }catch(Exception rptexcpt){
            JOptionPane.showMessageDialog(this, rptexcpt.getMessage());
        }
        }catch(Exception e){
            System.out.println(e);
        }
        bayarAwL.setText("");
            } else {
                System.out.println("User click batal");
            }
            utama.removeAll();
            utama.repaint();
            utama.revalidate();
        
            utama.add(ubahPemesanan);
            utama.repaint();
            utama.revalidate();
            noPes.setText("");
            tharga.setText("");
            tjumlah.setText("");
            tampiltabletransaksi();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tlunasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tlunasMouseClicked
        int i = tlunas.getSelectedRow();
        TableModel model = tlunas.getModel();
        String no = model.getValueAt(i, 0).toString();
        String tp = model.getValueAt(i, 1).toString();
        String k = model.getValueAt(i, 2).toString();
        String s = model.getValueAt(i, 3).toString();

        tnotrans.setText(no);
        ttotalpmbyrn.setText(tp);
        tkurrang.setText(k);
        tstts.setText(s);
    }//GEN-LAST:event_tlunasMouseClicked

    private void tbaayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbaayarActionPerformed
        int totalpembayaran,kurang,kembali;
        kurang=Integer.valueOf(tkurrang.getText());
        totalpembayaran=Integer.valueOf(tbaayar.getText());
        kembali=totalpembayaran-kurang;
        tkembali.setText(String.valueOf(kembali));
        if (totalpembayaran<kurang) {
            tstts.setText("Belum lunas");
            tkembali.setText("");
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Uang Anda Kurang RP." +kembali);
            if (obj.getMessageType() == notif.MessageType.OK) {
                System.out.println("User click ya");
            } else {
                System.out.println("User click batal");
            }
        } else {
            tstts.setText("Lunas");
            tkurrang.setText("0");
        }
        angka = Integer.parseInt(tkembali.getText());
        ganti = NumberFormat.getNumberInstance(Locale.US).format(angka);
        token = new StringTokenizer(ganti,".");
        ganti = token.nextToken();
        ganti = ganti.replace(',','.');
        ttotal.setText("Rp. " + String.format(ganti));
    }//GEN-LAST:event_tbaayarActionPerformed

    private void bbyrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbyrActionPerformed
        // TODO add your handling code here:
        try {
            String sql="update pemesanan set bayar_awal ='"+ttotalpmbyrn.getText()+"', kurang ='"
            +tkurrang.getText()+"', status ='"+tstts.getText()+"', kembali='"+
            tkembali.getText()+"' where no_pemesanan ='"
            +tnotrans.getText()+"'";
            Connection c=k.getcon();
            PreparedStatement p=c.prepareStatement(sql);
            p.executeUpdate();
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Transaksi Lunas");
            if (obj.getMessageType() == notif.MessageType.OK) {
                System.out.println("User click ya");
            } else {
                System.out.println("User click batal");
            }
        } catch (Exception e) {
        }
         try {
        Connection c =k.getcon();
        
        try{
            String report = ("C:\\Users\\lenovo\\Documents\\NetBeansProjects\\sriknd\\src\\sriknd\\report1.jrxml");
            HashMap hash = new HashMap();
            hash.put("no", tnotrans.getText());
            JasperReport JRpt = JasperCompileManager.compileReport(report);
            JasperPrint JPrint = JasperFillManager.fillReport(JRpt,hash,c);
            JasperViewer.viewReport(JPrint, false);
        }catch(Exception rptexcpt){
            JOptionPane.showMessageDialog(this, rptexcpt.getMessage());
            tkurrang.setText("");
            ttotalpmbyrn.setText("");
            tstts.setText("");
            tkembali.setText("");
            tnotrans.setText("");
            tbaayar.setText("");
            tampilTablee();
        }
        }catch(Exception e){
            System.out.println(e);
        }
         tkurrang.setText("");
            ttotalpmbyrn.setText("");
            tstts.setText("");
            tkembali.setText("");
            tnotrans.setText("");
            tampilTablee();
    }//GEN-LAST:event_bbyrActionPerformed

    private void keteranganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keteranganActionPerformed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_keteranganActionPerformed

    private void jLabel52MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel52MouseClicked
        // TODO add your handling code here:
        utama.removeAll();
        utama.repaint();
        utama.revalidate();
        
        utama.add(Transaksi);
        utama.repaint();
        utama.revalidate();
    }//GEN-LAST:event_jLabel52MouseClicked

    private void jLabel65MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel65MouseClicked
        // TODO add your handling code here:
        utama.removeAll();
        utama.repaint();
        utama.revalidate();
        
        utama.add(Pemesanan);
        utama.repaint();
        utama.revalidate();
    }//GEN-LAST:event_jLabel65MouseClicked

    private void jLabel66MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel66MouseClicked
        // TODO add your handling code here:
        utama.removeAll();
        utama.repaint();
        utama.revalidate();
        
        utama.add(ubahTransaksi);
        utama.repaint();
        utama.revalidate();
    }//GEN-LAST:event_jLabel66MouseClicked

    private void jLabel71MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel71MouseClicked
        // TODO add your handling code here:
        utama.removeAll();
        utama.repaint();
        utama.revalidate();
        
        utama.add(ubahPemesanan);
        utama.repaint();
        utama.revalidate();
    }//GEN-LAST:event_jLabel71MouseClicked

    private void bayarAwalLLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bayarAwalLLMouseClicked

    }//GEN-LAST:event_bayarAwalLLMouseClicked

    private void txtKurangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKurangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKurangActionPerformed

    private void TotalItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TotalItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalItemActionPerformed

    private void keterangan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keterangan1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_keterangan1ActionPerformed

    private void printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printActionPerformed
        // TODO add your handling code here:
        
        try {
        Connection c =k.getcon();
        
        try{
            String report = ("C:\\Users\\lenovo\\Documents\\NetBeansProjects\\sriknd\\src\\sriknd\\report1.jrxml");
            HashMap hash = new HashMap();
            hash.put("no", tnotrans.getText());
            JasperReport JRpt = JasperCompileManager.compileReport(report);
            JasperPrint JPrint = JasperFillManager.fillReport(JRpt,hash,c);
            JasperViewer.viewReport(JPrint, false);
        }catch(Exception rptexcpt){
            JOptionPane.showMessageDialog(this, rptexcpt.getMessage());
        }
        }catch(Exception e){
            System.out.println(e);
        }
    }//GEN-LAST:event_printActionPerformed

    private void tnotelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnotelpActionPerformed
        if (!tnotelp.getText().matches("[0-9]*")) {
            info.setText("No. telpon harus berupa angka");
            info.setVisible(true);
        } else if (tnotelp.getText().trim().length()>13 ){
            info.setText("No.telpon tidak boleh lebih dari 13");
            info.setVisible(true);
        }else if (tnotelp.getText().trim().length()<11){
            info.setText("No.telpon tidak boleh kurang dari 11");
            info.setVisible(true);
        } else {
            info.setVisible(false);
        }
    }//GEN-LAST:event_tnotelpActionPerformed

    private void tid_konsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tid_konsActionPerformed
        try {
            String sql="insert into konsumen values ('"+tid_kons.getText()+"','','','')";
            Connection con=k.getcon();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.execute();
            ps.close();
            //JOptionPane.showMessageDialog(null, "Berhasil");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_tid_konsActionPerformed

    private void textFieldSuggestion1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textFieldSuggestion1MouseClicked
       // new masuk().setVisible(true);
    }//GEN-LAST:event_textFieldSuggestion1MouseClicked

    private void textFieldSuggestion2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldSuggestion2ActionPerformed
        //new proses().setVisible(true);
    }//GEN-LAST:event_textFieldSuggestion2ActionPerformed

    private void textFieldSuggestion3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldSuggestion3ActionPerformed
       //new selesai().setVisible(true);
    }//GEN-LAST:event_textFieldSuggestion3ActionPerformed

    private void textFieldSuggestion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldSuggestion1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldSuggestion1ActionPerformed

    private void jLabel80MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel80MouseClicked
        // TODO add your handling code here:
        new masuk().setVisible(true);
    }//GEN-LAST:event_jLabel80MouseClicked

    private void jLabel109MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel109MouseClicked
        // TODO add your handling code here:
        new proses().setVisible(true);
    }//GEN-LAST:event_jLabel109MouseClicked

    private void jLabel79MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel79MouseClicked
        // TODO add your handling code here:
        new selesai().setVisible(true);
    }//GEN-LAST:event_jLabel79MouseClicked

    private void tnotransActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnotransActionPerformed
        DefaultTableModel model=new DefaultTableModel();
        model.addColumn(("No. Pemesanan"));
        model.addColumn(("Total Bayar"));
        model.addColumn(("Kurang"));
        model.addColumn(("Status"));
        model.addColumn("Bayar Awal");
        
        try {
            String sql="SELECT kurang, total_pembayaran, status FROM `pemesanan` WHERE no_pemesanan='"+tnotrans.getText()+"'";
            Connection c=k.getcon();
            PreparedStatement p=c.prepareStatement(sql);
            ResultSet rs=p.executeQuery();
            while (rs.next()) {
                tkurrang.setText(rs.getString("kurang"));
                ttotalpmbyrn.setText(rs.getString("total_pembayaran"));
                tstts.setText(rs.getString("status"));
            }
        } catch (Exception e) {
        }
        try {
            int no=1;
            PreparedStatement pst=k.getcon().prepareStatement("Select no_pemesanan,total_pembayaran, "
                    + "kurang,status,bayar_awal From "
                    + "pemesanan where no_pemesanan='"+tnotrans.getText()+"'");
            ResultSet rst=pst.executeQuery();
            
            while (rst.next()) {                
                model.addRow(new Object[] {
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)});
                
                tlunas.setModel(model);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        int i = tlunas.getSelectedRow();
        TableModel m=tlunas.getModel();
        String no = m.getValueAt(i, 0).toString();
        String tp = m.getValueAt(i, 1).toString();
        String k = m.getValueAt(i, 2).toString();
        String s = m.getValueAt(i, 3).toString();

        tnotrans.setText(no);
        ttotalpmbyrn.setText(tp);
        tkurrang.setText(k);
        tstts.setText(s);
    }//GEN-LAST:event_tnotransActionPerformed

    private void tnotelpComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_tnotelpComponentAdded

        if (!tnotelp.getText().matches("[0-9]*")) {
            info.setText("No. telpon harus berupa angka");
            info.setVisible(true);
        } else if (tnotelp.getText().trim().length()>13 || tnotelp.getText().trim().length()<11){
            info.setText("No.telpon tidak boleh lebih dari 13 dan kurang 11");
            info.setVisible(true);
        } else {
            info.setVisible(false);
        }
    }//GEN-LAST:event_tnotelpComponentAdded

    private void tnotelpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tnotelpMouseClicked
        
    }//GEN-LAST:event_tnotelpMouseClicked

    private void ttotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ttotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ttotalActionPerformed

    private void jLabel75MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel75MouseClicked

    }//GEN-LAST:event_jLabel75MouseClicked

    private void jLabel73MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel73MouseClicked

    }//GEN-LAST:event_jLabel73MouseClicked

    private void jLabel74MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel74MouseClicked

    }//GEN-LAST:event_jLabel74MouseClicked

    private void tkurangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tkurangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tkurangActionPerformed

    private void tdiskonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tdiskonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tdiskonActionPerformed

    private void noPesananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noPesananActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noPesananActionPerformed

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
            java.util.logging.Logger.getLogger(kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new kasir().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(kasir.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Pelunasan;
    private javax.swing.JPanel Pemesanan;
    private custom.TextFieldSuggestion TotalHarga;
    private custom.TextFieldSuggestion TotalItem;
    private javax.swing.JPanel Transaksi;
    private rojerusan.RSMaterialButtonRectangle bayar;
    private rojerusan.RSMaterialButtonRectangle bayar1;
    private custom.TextFieldSuggestion bayarAwL;
    private custom.TextFieldSuggestion bayarAwalLL;
    private rojerusan.RSMaterialButtonRectangle bbayar;
    private rojerusan.RSMaterialButtonRectangle bbyr;
    private rojerusan.RSMaterialButtonRectangle bhapus;
    private rojerusan.RSMaterialButtonRectangle btambah;
    private rojerusan.RSMaterialButtonRectangle bubah;
    private custom.card card1;
    private custom.card card2;
    private custom.card card3;
    private custom.ComboBoxSuggestion combokode;
    private custom.ComboBoxSuggestion combonama;
    private javax.swing.JPanel dashKasir;
    private com.toedter.calendar.JDateChooser dtanggal;
    private rojerusan.RSMaterialButtonRectangle hapus;
    private custom.TextFieldSuggestion harga;
    private javax.swing.JLabel hrg;
    private javax.swing.JLabel hrg1;
    private javax.swing.JLabel hrg2;
    private javax.swing.JLabel hrg3;
    private javax.swing.JLabel hrg4;
    private javax.swing.JLabel hrg5;
    private javax.swing.JLabel hrg6;
    private javax.swing.JLabel hrg7;
    private javax.swing.JLabel hrg8;
    private javax.swing.JLabel info;
    private rojerusan.RSMaterialButtonRectangle jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
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
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private custom.table_custom jTable1;
    private javax.swing.JLabel jml;
    private custom.TextFieldSuggestion jumlah;
    private javax.swing.JPanel kasir;
    private custom.TextFieldSuggestion keterangan;
    private custom.TextFieldSuggestion keterangan1;
    private javax.swing.JLabel logout;
    private javax.swing.JPanel menu;
    private javax.swing.JLabel nama;
    private custom.TextFieldSuggestion noPes;
    private custom.TextFieldSuggestion noPesanan;
    private custom.TextFieldSuggestion no_transaksi;
    private custom.TextFieldSuggestion nopes;
    private javax.swing.JLabel notrans;
    private javax.swing.JPanel pelunasan;
    private javax.swing.JPanel pemesanan;
    private javax.swing.JButton print;
    private custom.table_custom tableDetailPemesanan;
    private custom.table_custom tableTransaksi;
    private custom.table_custom table_custom1;
    private custom.table_custom table_custom2;
    private custom.table_custom tableubahpsn;
    private custom.TextFieldSuggestion talamat;
    private rojerusan.RSMaterialButtonRectangle tambah;
    private custom.TextFieldSuggestion tanggal;
    private custom.TextFieldSuggestion tanggal1;
    private com.toedter.calendar.JDateChooser tanggalAmbil;
    private custom.TextFieldSuggestion tbaayar;
    private custom.TextFieldSuggestion tdis;
    private custom.TextFieldSuggestion tdiskon;
    private custom.TextFieldSuggestion textFieldSuggestion1;
    private custom.TextFieldSuggestion textFieldSuggestion2;
    private custom.TextFieldSuggestion textFieldSuggestion3;
    private javax.swing.JLabel tgl;
    private custom.TextFieldSuggestion tharga;
    private custom.TextFieldSuggestion tid_kons;
    private custom.TextFieldSuggestion tiduser;
    private custom.TextFieldSuggestion tjumlah;
    private custom.TextFieldSuggestion tkembali;
    private custom.TextFieldSuggestion tkurang;
    private custom.TextFieldSuggestion tkurrang;
    private custom.table_custom tlunas;
    private custom.TextFieldSuggestion tnamakons;
    private custom.TextFieldSuggestion tnotelp;
    private custom.TextFieldSuggestion tnotrans;
    private custom.TextFieldSuggestion totalItem;
    private custom.TextFieldSuggestion total_harga;
    private custom.TextFieldSuggestion total_harga1;
    private javax.swing.JLabel totalharga;
    private custom.TextFieldSuggestion tstatus;
    private custom.TextFieldSuggestion tstts;
    private custom.TextFieldSuggestion ttanggal;
    private custom.TextFieldSuggestion ttlpembayaran;
    private custom.TextFieldSuggestion ttotal;
    private custom.TextFieldSuggestion ttotalpmbyrn;
    private custom.TextFieldSuggestion txtKurang;
    private rojerusan.RSMaterialButtonRectangle ubah;
    private javax.swing.JPanel ubahPemesan;
    private javax.swing.JPanel ubahPemesanan;
    private javax.swing.JPanel ubahTransaksi;
    private javax.swing.JPanel utama;
    // End of variables declaration//GEN-END:variables
}
