/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sriknd;

import custom.notif;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import oracle.jrockit.jfr.events.ContentTypeImpl;

/**
 *
 * @author lenovo
 */
public class admin extends javax.swing.JFrame {
    koneksi k=new koneksi();
    private void Void() {
        K.setText("");
        N.setText("");
        A.setText("");
        NT.setText("");
        P.setText("");
        TL.setDate(null);
        J.setSelectedIndex(0);
    }
    
    private void table() {
        JTableHeader tableheader = table.getTableHeader();
            DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tableheader.getDefaultRenderer();
            
            Font font = headerRenderer.getFont();
            font = font.deriveFont(Font.BOLD, 30f);
            headerRenderer.setFont(font);
            
            TableColumnModel columnModel = tableheader.getColumnModel();
            for (int columnIndex = 0; columnIndex < columnModel.getColumnCount(); columnIndex++){
                TableColumn column = columnModel.getColumn(columnIndex);
                column.setHeaderRenderer(headerRenderer);
            }
        
        DefaultTableModel kolom = new DefaultTableModel();
        kolom.addColumn("ID");
        kolom.addColumn("Nama");
        kolom.addColumn("Tanggal Lahir");
        kolom.addColumn("Alamat");
        kolom.addColumn("NoTelp");
        kolom.addColumn("Akses");
        kolom.addColumn("Password");
        try {
            
            Statement st = k.getcon().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM karyawan");
            while(rs.next()) {
                kolom.addRow(new Object[] {
                    rs.getString("id_user"),
                    rs.getString("nama"),
                    rs.getString("tgl_lahir"),
                    rs.getString("alamat"),
                    rs.getString("notelp"),
                    rs.getString("akses"),
                    rs.getString("password")
                });
                table.setModel(kolom);
            }
            
            
        } catch (Exception e) {
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Koneksi Database Gagal" + e.getMessage());
        }
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
            String sql="Select no_pemesanan, kode_produk, jumlah, tanggal_ambil, keterangan from detail_pemesanan where tanggal_ambil=curdate() + interval 1 day";
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
            table_custom4.setModel(d);
        } catch (Exception e) {
        }
    }
    public void kosong() {
        DefaultTableModel kolom = new DefaultTableModel();
        while(kolom.getRowCount() > 0) {
            kolom.removeRow(0);
        }
    }
    public static Date getTanggalFromTable(JTable table, int kolom) {
        JTable tabel = table;
        String str_tgl = String.valueOf(tabel.getValueAt(table.getSelectedRow(), kolom));
        Date tanggal = null;
        try {
            tanggal = new SimpleDateFormat("yyyy-MM-dd").parse(str_tgl);
        } catch (ParseException ex) {
            Logger.getLogger(admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tanggal;
    }
    
    void cariKar() {
        DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("ID");
        tbl.addColumn("Nama");
        tbl.addColumn("Tanggal Lahir");
        tbl.addColumn("Alamat");
        tbl.addColumn("No Telp");
        tbl.addColumn("Akses");
        tbl.addColumn("Password");
        
        try{
            String sql = "SELECT * FROM karyawan WHERE nama like '%"  + C.getText() + "%'";
            Connection con=k.getcon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()) {
                tbl.addRow(new Object[] {
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7)
                });
                table.setModel(tbl);
            }
        }catch (Exception e) {
        }
    }
    
    public void cariPro() {
        DefaultTableModel tbl = new DefaultTableModel();
        //tbl.addColumn("No");
        tbl.addColumn("Kode Produk");
        tbl.addColumn("Nama Produk");
        tbl.addColumn("Harga Jual");
        tbl.addColumn("Harga Produk");
        
        try{
            int nomor = 1;
            String sql = "SELECT * FROM produk WHERE nama_produk like '%"  + cr.getText() + "%'";
            Connection con=k.getcon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()) {
                tbl.addRow(new Object[] {
                    //("" + nomor++),
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4)
                });
                t.setModel(tbl);
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    public void kosong1() {
        DefaultTableModel kolom = new DefaultTableModel();
        
        while(kolom.getRowCount() > 0) {
            kolom.removeRow(0);
        }
    }
    
    private void table2() {
        DefaultTableModel tbl = new DefaultTableModel();
        //tbl.addColumn("No");
        tbl.addColumn("Kode Produk");
        tbl.addColumn("Nama Produk");
        tbl.addColumn("Harga Jual");
        tbl.addColumn("Harga Produk");
        
        try {
            //int nomor = 1;
            String sql = "SELECT * FROM produk"; //WHERE nama_kategori = '" + cb.getSelectedItem() + "'";
            Connection con=k.getcon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()) {
                tbl.addRow(new Object[]{
                    //("" + nomor++),
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                });
                t.setModel(tbl);
            }
        } catch (Exception e) {
        }
    }
    
    public void cmbkategori() throws SQLException{
        String sql = "select nama_kategori from detail_produk;";
        Connection con=k.getcon();
        java.sql.PreparedStatement pst = con.prepareStatement(sql);
        ResultSet res = pst.executeQuery(sql);
        while(res.next()){
            cb.addItem(res.getString("nama_kategori"));
        }
        res.last();
        int jumlahdata = res.getRow();
        res.first();
        
    }
    
    private void autonumberDpt() {
        try {
            String sql = "SELECT * from detail_produk where nama_kategori='Dompet' ORDER BY kode_produk DESC";
            Connection con=k.getcon();
            java.sql.PreparedStatement pst = con.prepareStatement(sql);
            ResultSet res = pst.executeQuery(sql);
            
            if(res.next()) {
                String kode = res.getString("kode_produk").substring(2);
                String GS = "" + (Integer.parseInt(kode) + 1);
                String No1 = "";
                
                if(GS.length()==1)
                {No1 = "00";}
                else if(GS.length()==2)
                {No1 = "0";}
                else if(GS.length()==3)
                {No1 = "";}
                kp.setText("DP" + No1 + GS);
            } else {
                kp.setText("DP001");
            }
            
            res.close();
            pst.close();
        } catch (Exception e) {
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Auto Number Error" + e.getMessage());
            if (obj.getMessageType() == notif.MessageType.OK) {
                    System.out.println("User click ya");
                } else {
                    System.out.println("User click batal");
                }
        }
    }
    private void autonumberTas() {
        try {
            String sql = "SELECT * from detail_produk where nama_kategori='Tas' ORDER BY kode_produk DESC";
            Connection con=k.getcon();
            java.sql.PreparedStatement pst = con.prepareStatement(sql);
            ResultSet res = pst.executeQuery(sql);
            
            if(res.next()) {
                String kode = res.getString("kode_produk").substring(2);
                String GS = "" + (Integer.parseInt(kode) + 1);
                String No1 = "";
                
                if(GS.length()==1)
                {No1 = "00";}
                else if(GS.length()==2)
                {No1 = "0";}
                else if(GS.length()==3)
                {No1 = "";}
                kp.setText("TS" + No1 + GS);
            } else {
                kp.setText("TS001");
            }
            
            res.close();
            pst.close();
        } catch (Exception e) {
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Auto Number Error" + e.getMessage());
            if (obj.getMessageType() == notif.MessageType.OK) {
                    System.out.println("User click ya");
                } else {
                    System.out.println("User click batal");
                }
        }
    }
    private void kos() {
        kp.setText("");
        np.setText("");
        h.setText("");
    }
    
    private void tablekeuntungan() {
        DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("No");
        tbl.addColumn("Tanggal");
        tbl.addColumn("No. Pemesanan");
        tbl.addColumn("Total Pembayaran");
        tbl.addColumn("Kode Produk");
        tbl.addColumn("Jumlah");
        tbl.addColumn("Total Harga");
        
        try {
            int nomor = 1;
            String sql = "SELECT pemesanan.no_pemesanan, pemesanan.tgl_pemesanan, floor(pemesanan.total_pembayaran-(pemesanan.total_pembayaran*(pemesanan.diskon/100))) AS total, detail_pemesanan.kode_produk, detail_pemesanan.jumlah, detail_pemesanan.total_harga FROM pemesanan JOIN detail_pemesanan ON pemesanan.no_pemesanan=detail_pemesanan.no_pemesanan WHERE pemesanan.status='Lunas';";
            Connection con=k.getcon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()) {
                tbl.addRow(new Object[]{
                    ("" + nomor++),
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6)
                });
                tabken.setModel(tbl);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "" +e.getMessage());
        }
    }
    
    private void tablepengeluaran() {
        DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("No");
        tbl.addColumn("Kode Produk");
        tbl.addColumn("Nama Produk");
        tbl.addColumn("Harga Jual");
        tbl.addColumn("Harga Produksi");
        
        try {
            int nomor = 1;
            String sql = "SELECT * FROM produk";
            Connection con=k.getcon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()) {
                tbl.addRow(new Object[]{
                    ("" + nomor++),
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4)
                });
                t.setModel(tbl);
            }
        } catch (Exception e) {
        }
    }
    
    private void sum() {
        int total = 0;
        for (int i = 0; i < tabken.getRowCount(); i++) {
            int amount = Integer.parseInt(tabken.getValueAt(i,3).toString());
            total += amount;
        }
        T.setText("" + total);
    }
    
    private void untung() {
        int total,untung;
        
        total = Integer.valueOf(T.getText());
        int keuntungan = total / 10;
        Untung.setText(String.valueOf(keuntungan));
        untung=Integer.valueOf(Untung.getText());
        angka = Integer.parseInt(String.valueOf(Untung.getText()));
        ganti = NumberFormat.getNumberInstance(Locale.US).format(angka);
        token = new StringTokenizer(ganti,".");
        ganti = token.nextToken();
        ganti = ganti.replace(',','.');
        Untung.setText("Rp. " + String.format(ganti));
    }
    
    
    public long angka;
    public String ganti;
    public StringTokenizer token;

    public admin() {
        initComponents();
        k.connect();
        table();
        table2();
        tablekeuntungan();
        kos();
        tampilpsnmsk();
        tampilpsnpss();
        tampilpsnsls();
        tablpsnbsk();
        tablpsnmgg();
        sum();
        untung();
        cariKar();
        cariPro();
        infoo.setVisible(false);
        angka = Integer.parseInt(T.getText());
        ganti = NumberFormat.getNumberInstance(Locale.US).format(angka);
        token = new StringTokenizer(ganti,".");
        ganti = token.nextToken();
        ganti = ganti.replace(',','.');
        T.setText("Rp. " + String.format(ganti));
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
        jPanel2 = new javax.swing.JPanel();
        dashAd = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        dataAdmi = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        produk = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        laporan = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        logout = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        dashAdmin = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_custom1 = new custom.table_custom();
        jLabel75 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        textFieldSuggestion1 = new custom.TextFieldSuggestion();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        textFieldSuggestion2 = new custom.TextFieldSuggestion();
        textFieldSuggestion3 = new custom.TextFieldSuggestion();
        card1 = new custom.card();
        jLabel36 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        table_custom4 = new custom.table_custom();
        jLabel111 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        card2 = new custom.card();
        card3 = new custom.card();
        Admin = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        TL = new com.toedter.calendar.JDateChooser();
        jScrollPane3 = new javax.swing.JScrollPane();
        table = new custom.table_custom();
        jLabel28 = new javax.swing.JLabel();
        cariKar = new rojerusan.RSMaterialButtonRectangle();
        jLabel22 = new javax.swing.JLabel();
        hapusKar = new rojerusan.RSMaterialButtonRectangle();
        editKar = new rojerusan.RSMaterialButtonRectangle();
        tambahKar = new rojerusan.RSMaterialButtonRectangle();
        K = new textfield_suggestion.TextFieldSuggestion();
        N = new textfield_suggestion.TextFieldSuggestion();
        A = new textfield_suggestion.TextFieldSuggestion();
        NT = new textfield_suggestion.TextFieldSuggestion();
        J = new combo_suggestion.ComboBoxSuggestion();
        P = new textfield_suggestion.TextFieldSuggestion();
        car = new javax.swing.JLabel();
        C = new textfield_suggestion.TextFieldSuggestion();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        infoo = new javax.swing.JLabel();
        info = new javax.swing.JLabel();
        Produk = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        kp = new textfield_suggestion.TextFieldSuggestion();
        np = new textfield_suggestion.TextFieldSuggestion();
        h = new textfield_suggestion.TextFieldSuggestion();
        cb = new combo_suggestion.ComboBoxSuggestion();
        jLabel19 = new javax.swing.JLabel();
        cr = new textfield_suggestion.TextFieldSuggestion();
        jLabel58 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        t = new custom.table_custom();
        jLabel41 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        hapusPro = new rojerusan.RSMaterialButtonRectangle();
        editPro = new rojerusan.RSMaterialButtonRectangle();
        tambahPro = new rojerusan.RSMaterialButtonRectangle();
        cariPro = new rojerusan.RSMaterialButtonRectangle();
        jLabel57 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        Laporan = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabken = new custom.table_custom();
        jLabel68 = new javax.swing.JLabel();
        card4 = new custom.card();
        jLabel18 = new javax.swing.JLabel();
        T = new custom.TextFieldSuggestion();
        card5 = new custom.card();
        jLabel21 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        Untung = new custom.TextFieldSuggestion();
        cari = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1920, 1000));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(44, 140, 136));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dashAd.setBackground(new java.awt.Color(44, 140, 136));
        dashAd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashAdMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                dashAdMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                dashAdMouseExited(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setIcon(new javax.swing.ImageIcon("D:\\back\\Dashboard (1).png")); // NOI18N

        jLabel55.setIcon(new javax.swing.ImageIcon("D:\\back\\dashboard (1) 1.png")); // NOI18N

        javax.swing.GroupLayout dashAdLayout = new javax.swing.GroupLayout(dashAd);
        dashAd.setLayout(dashAdLayout);
        dashAdLayout.setHorizontalGroup(
            dashAdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashAdLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel55)
                .addGap(18, 18, 18)
                .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))
        );
        dashAdLayout.setVerticalGroup(
            dashAdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
            .addComponent(jLabel55, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
        );

        jPanel2.add(dashAd, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, 490, 60));

        dataAdmi.setBackground(new java.awt.Color(44, 140, 136));
        dataAdmi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dataAdmiMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                dataAdmiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                dataAdmiMouseExited(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(44, 140, 136));
        jLabel2.setIcon(new javax.swing.ImageIcon("D:\\back\\Data Karyawan.png")); // NOI18N

        jLabel6.setIcon(new javax.swing.ImageIcon("D:\\back\\man 1.png")); // NOI18N

        javax.swing.GroupLayout dataAdmiLayout = new javax.swing.GroupLayout(dataAdmi);
        dataAdmi.setLayout(dataAdmiLayout);
        dataAdmiLayout.setHorizontalGroup(
            dataAdmiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dataAdmiLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        dataAdmiLayout.setVerticalGroup(
            dataAdmiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataAdmiLayout.createSequentialGroup()
                .addGroup(dataAdmiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(dataAdmiLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel2.add(dataAdmi, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 490, 60));

        produk.setBackground(new java.awt.Color(44, 140, 136));
        produk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                produkMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                produkMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                produkMouseExited(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(44, 140, 136));
        jLabel1.setIcon(new javax.swing.ImageIcon("D:\\back\\Data Produk1.png")); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon("D:\\back\\product (1) 1.png")); // NOI18N

        javax.swing.GroupLayout produkLayout = new javax.swing.GroupLayout(produk);
        produk.setLayout(produkLayout);
        produkLayout.setHorizontalGroup(
            produkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, produkLayout.createSequentialGroup()
                .addGap(0, 25, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        produkLayout.setVerticalGroup(
            produkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        jPanel2.add(produk, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 570, 490, 60));

        laporan.setBackground(new java.awt.Color(44, 140, 136));
        laporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                laporanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                laporanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                laporanMouseExited(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(44, 140, 136));
        jLabel4.setIcon(new javax.swing.ImageIcon("D:\\back\\Laporan (2).png")); // NOI18N

        jLabel9.setIcon(new javax.swing.ImageIcon("D:\\back\\document 1.png")); // NOI18N

        javax.swing.GroupLayout laporanLayout = new javax.swing.GroupLayout(laporan);
        laporan.setLayout(laporanLayout);
        laporanLayout.setHorizontalGroup(
            laporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, laporanLayout.createSequentialGroup()
                .addGap(0, 27, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        laporanLayout.setVerticalGroup(
            laporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.add(laporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 650, 490, 60));

        jLabel3.setIcon(new javax.swing.ImageIcon("D:\\back\\Logo.png")); // NOI18N
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, -1, -1));

        jLabel29.setIcon(new javax.swing.ImageIcon("D:\\back\\Team spirit-bro (1) 2.png")); // NOI18N
        jPanel2.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

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
        jPanel2.add(logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 890, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 490, 1000));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.CardLayout());

        dashAdmin.setBackground(new java.awt.Color(255, 255, 255));
        dashAdmin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        dashAdmin.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 390, 1340, 240));

        jLabel75.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel75.setIcon(new javax.swing.ImageIcon("D:\\back\\Pesanan Masuk.1.png")); // NOI18N
        jLabel75.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel75MouseClicked(evt);
            }
        });
        dashAdmin.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, -1, -1));

        jLabel80.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel80.setText("Lihat Detail>");
        jLabel80.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel80MouseClicked(evt);
            }
        });
        dashAdmin.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 270, -1, -1));

        jLabel81.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel81.setText("Lihat Detail>");
        jLabel81.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel81MouseClicked(evt);
            }
        });
        dashAdmin.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 270, -1, -1));

        jLabel82.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel82.setText("Lihat Detail>");
        jLabel82.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel82MouseClicked(evt);
            }
        });
        dashAdmin.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(1240, 270, -1, -1));

        textFieldSuggestion1.setForeground(new java.awt.Color(255, 255, 255));
        textFieldSuggestion1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textFieldSuggestion1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        dashAdmin.add(textFieldSuggestion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 420, 210));

        jLabel73.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel73.setIcon(new javax.swing.ImageIcon("D:\\back\\Proses Pemesanan.1.png")); // NOI18N
        jLabel73.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel73MouseClicked(evt);
            }
        });
        dashAdmin.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 120, -1, -1));

        jLabel74.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel74.setIcon(new javax.swing.ImageIcon("D:\\back\\Pemesanan Selesai.1.png")); // NOI18N
        jLabel74.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel74MouseClicked(evt);
            }
        });
        dashAdmin.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 120, -1, -1));

        textFieldSuggestion2.setForeground(new java.awt.Color(255, 255, 255));
        textFieldSuggestion2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textFieldSuggestion2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        dashAdmin.add(textFieldSuggestion2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, 420, 210));

        textFieldSuggestion3.setForeground(new java.awt.Color(255, 255, 255));
        textFieldSuggestion3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textFieldSuggestion3.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        dashAdmin.add(textFieldSuggestion3, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 100, 420, 210));

        card1.setColor1(new java.awt.Color(255, 136, 36));
        card1.setColor2(new java.awt.Color(255, 250, 229));
        dashAdmin.add(card1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 420, 210));

        jLabel36.setIcon(new javax.swing.ImageIcon("D:\\back\\DASHBOARD (2).png")); // NOI18N
        dashAdmin.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 0, -1, 60));

        jLabel42.setIcon(new javax.swing.ImageIcon("D:\\back\\Rectangle 11.png")); // NOI18N
        dashAdmin.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(-50, 0, -1, -1));

        table_custom4.setModel(new javax.swing.table.DefaultTableModel(
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
        table_custom4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jScrollPane7.setViewportView(table_custom4);

        dashAdmin.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 700, 1340, 240));

        jLabel111.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel111.setText("Pesanan Besok");
        dashAdmin.add(jLabel111, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, -1, -1));

        jLabel110.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel110.setText("Pesanan Minggu Ini");
        dashAdmin.add(jLabel110, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 660, -1, -1));

        card2.setColor1(new java.awt.Color(255, 102, 102));
        card2.setColor2(new java.awt.Color(255, 230, 230));
        dashAdmin.add(card2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, 420, 210));

        card3.setColor1(new java.awt.Color(255, 0, 0));
        card3.setColor2(new java.awt.Color(255, 233, 232));
        dashAdmin.add(card3, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 100, 420, 210));

        jPanel3.add(dashAdmin, "card6");

        Admin.setBackground(new java.awt.Color(255, 255, 255));
        Admin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AdminMouseClicked(evt);
            }
        });
        Admin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel11.setText("ID User");
        Admin.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, -1, 45));

        jLabel12.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel12.setText("Nama");
        Admin.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 280, -1, 45));

        jLabel13.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel13.setText("Tanggal Lahir");
        Admin.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 360, -1, 45));

        jLabel14.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel14.setText("Alamat");
        Admin.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 440, -1, 45));

        jLabel15.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel15.setText("Nomer Telp");
        Admin.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 520, -1, 45));

        jLabel16.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel16.setText("Akses");
        Admin.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 610, -1, 45));

        jLabel17.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel17.setText("Password");
        Admin.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 690, -1, 45));

        TL.setBackground(new java.awt.Color(229, 229, 229));
        TL.setFont(new java.awt.Font("Arial Narrow", 0, 20)); // NOI18N
        Admin.add(TL, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 360, 280, 40));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nama", "Tgl", "Alamat", "NoTelp", "Akses", "Password"
            }
        ));
        table.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        table.setGridColor(new java.awt.Color(255, 255, 255));
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(table);

        Admin.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 160, 810, 690));
        Admin.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 270, -1, -1));

        cariKar.setBackground(new java.awt.Color(0, 153, 153));
        cariKar.setText("CARI");
        cariKar.setActionCommand("Search");
        cariKar.setFont(new java.awt.Font("Roboto Medium", 1, 20)); // NOI18N
        cariKar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cariKarMouseClicked(evt);
            }
        });
        cariKar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariKarActionPerformed(evt);
            }
        });
        Admin.add(cariKar, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 100, 140, 55));
        Admin.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 620, -1, -1));

        hapusKar.setBackground(new java.awt.Color(255, 81, 81));
        hapusKar.setText("HAPUS");
        hapusKar.setFont(new java.awt.Font("Roboto Medium", 1, 20)); // NOI18N
        hapusKar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusKarActionPerformed(evt);
            }
        });
        Admin.add(hapusKar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 800, 140, 60));

        editKar.setBackground(new java.awt.Color(65, 165, 222));
        editKar.setText("EDIT");
        editKar.setFont(new java.awt.Font("Roboto Medium", 1, 20)); // NOI18N
        editKar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editKarActionPerformed(evt);
            }
        });
        Admin.add(editKar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 800, 140, 60));

        tambahKar.setBackground(new java.awt.Color(65, 222, 112));
        tambahKar.setText("TAMBAH");
        tambahKar.setFont(new java.awt.Font("Roboto Medium", 1, 20)); // NOI18N
        tambahKar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahKarActionPerformed(evt);
            }
        });
        Admin.add(tambahKar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 800, 140, 60));

        K.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        K.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KActionPerformed(evt);
            }
        });
        Admin.add(K, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 200, 280, 45));

        N.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Admin.add(N, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 280, 280, 45));

        A.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Admin.add(A, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 440, 280, 45));

        NT.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        NT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NTActionPerformed(evt);
            }
        });
        Admin.add(NT, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 520, 280, 45));

        J.setBackground(new java.awt.Color(229, 229, 229));
        J.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Admin", "Kasir" }));
        J.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        J.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JActionPerformed(evt);
            }
        });
        Admin.add(J, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 620, 280, 45));

        P.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Admin.add(P, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 690, 280, 45));

        car.setIcon(new javax.swing.ImageIcon("D:\\back\\magnifying-glass-search.png")); // NOI18N
        car.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                carMouseClicked(evt);
            }
        });
        Admin.add(car, new org.netbeans.lib.awtextra.AbsoluteConstraints(1310, 100, -1, 45));

        C.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Admin.add(C, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 100, 280, 45));

        jLabel60.setIcon(new javax.swing.ImageIcon("D:\\back\\49.png")); // NOI18N
        Admin.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 280, -1, -1));

        jLabel61.setIcon(new javax.swing.ImageIcon("D:\\back\\49.png")); // NOI18N
        Admin.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 200, -1, -1));

        jLabel62.setIcon(new javax.swing.ImageIcon("D:\\back\\49.png")); // NOI18N
        Admin.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 520, -1, -1));

        jLabel63.setIcon(new javax.swing.ImageIcon("D:\\back\\49.png")); // NOI18N
        Admin.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 690, -1, -1));

        jLabel67.setIcon(new javax.swing.ImageIcon("D:\\back\\49.png")); // NOI18N
        Admin.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 440, -1, -1));

        jLabel65.setIcon(new javax.swing.ImageIcon("D:\\back\\49.png")); // NOI18N
        Admin.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 100, -1, -1));

        jLabel49.setIcon(new javax.swing.ImageIcon("D:\\back\\DATA KARYAWAN1.png")); // NOI18N
        Admin.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 0, -1, 60));

        jLabel23.setIcon(new javax.swing.ImageIcon("D:\\back\\Rectangle 11.png")); // NOI18N
        Admin.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1430, -1));

        infoo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        infoo.setForeground(new java.awt.Color(255, 0, 0));
        infoo.setText("anjing");
        Admin.add(infoo, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 570, 280, 30));

        info.setIcon(new javax.swing.ImageIcon("D:\\back\\front-view-plant-growing-from-golden-coins-removebg-preview 2.png")); // NOI18N
        Admin.add(info, new org.netbeans.lib.awtextra.AbsoluteConstraints(-40, 550, -1, 450));

        jPanel3.add(Admin, "card2");

        Produk.setBackground(new java.awt.Color(255, 255, 255));
        Produk.setForeground(new java.awt.Color(255, 255, 255));
        Produk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ProdukMouseClicked(evt);
            }
        });
        Produk.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel39.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Nama Produk");
        Produk.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 310, 130, 45));

        jLabel40.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Harga");
        Produk.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 390, 110, 45));

        kp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Produk.add(kp, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 230, 280, 45));

        np.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        np.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                npMouseClicked(evt);
            }
        });
        np.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                npActionPerformed(evt);
            }
        });
        Produk.add(np, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 310, 280, 45));

        h.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        h.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hActionPerformed(evt);
            }
        });
        Produk.add(h, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 390, 280, 45));

        cb.setBackground(new java.awt.Color(229, 229, 229));
        cb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tas", "Dompet" }));
        cb.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbActionPerformed(evt);
            }
        });
        Produk.add(cb, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 470, 280, 45));

        jLabel19.setIcon(new javax.swing.ImageIcon("D:\\back\\magnifying-glass-search.png")); // NOI18N
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
        });
        Produk.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 100, -1, 45));

        cr.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Produk.add(cr, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, 280, 45));

        jLabel58.setIcon(new javax.swing.ImageIcon("D:\\back\\49.png")); // NOI18N
        Produk.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 390, 280, -1));

        jScrollPane4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        t.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No", "Kode Produk", "Nama Produk", "Harga", "Kategori"
            }
        ));
        t.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        t.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(t);

        Produk.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 730, 700));

        jLabel41.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Kode Produk");
        Produk.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 230, 130, 45));

        jLabel10.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Kategori");
        Produk.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 470, -1, 45));

        hapusPro.setBackground(new java.awt.Color(255, 81, 81));
        hapusPro.setText("HAPUS");
        hapusPro.setFont(new java.awt.Font("Roboto Medium", 1, 20)); // NOI18N
        hapusPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusProActionPerformed(evt);
            }
        });
        Produk.add(hapusPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 620, 130, 60));

        editPro.setBackground(new java.awt.Color(65, 165, 222));
        editPro.setText("EDIT");
        editPro.setFont(new java.awt.Font("Roboto Medium", 1, 20)); // NOI18N
        editPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProActionPerformed(evt);
            }
        });
        Produk.add(editPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 620, 130, 60));

        tambahPro.setBackground(new java.awt.Color(65, 222, 112));
        tambahPro.setText("TAMBAH");
        tambahPro.setFont(new java.awt.Font("Roboto Medium", 1, 20)); // NOI18N
        tambahPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahProActionPerformed(evt);
            }
        });
        Produk.add(tambahPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(1240, 620, 130, 60));

        cariPro.setBackground(new java.awt.Color(0, 153, 153));
        cariPro.setText("CARI");
        cariPro.setFont(new java.awt.Font("Roboto Medium", 1, 20)); // NOI18N
        cariPro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cariProMouseClicked(evt);
            }
        });
        cariPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariProActionPerformed(evt);
            }
        });
        Produk.add(cariPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 140, 60));

        jLabel57.setIcon(new javax.swing.ImageIcon("D:\\back\\49.png")); // NOI18N
        Produk.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 310, -1, -1));

        jLabel52.setIcon(new javax.swing.ImageIcon("D:\\back\\49.png")); // NOI18N
        Produk.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 230, -1, -1));

        jLabel56.setIcon(new javax.swing.ImageIcon("D:\\back\\49.png")); // NOI18N
        Produk.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, -1, -1));

        jLabel43.setIcon(new javax.swing.ImageIcon("D:\\back\\shopping-bag-package-banner-sign-symbol-shopping-concept-orange-background-3d-rendering-removebg-preview.png")); // NOI18N
        Produk.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 640, 420, -1));

        jLabel50.setIcon(new javax.swing.ImageIcon("D:\\back\\Rectangle 62 (2).png")); // NOI18N
        Produk.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 190, -1, -1));

        jLabel51.setIcon(new javax.swing.ImageIcon("D:\\back\\DATA PRODUK.png")); // NOI18N
        Produk.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 0, -1, 60));

        jLabel35.setIcon(new javax.swing.ImageIcon("D:\\back\\Rectangle 11.png")); // NOI18N
        Produk.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1430, -1));

        jLabel37.setIcon(new javax.swing.ImageIcon("D:\\back\\front-view-plant-growing-from-golden-coins-removebg-preview 2.png")); // NOI18N
        Produk.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, 530, -1, -1));

        jPanel3.add(Produk, "card4");

        Laporan.setBackground(new java.awt.Color(255, 255, 255));
        Laporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LaporanMouseClicked(evt);
            }
        });
        Laporan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel54.setIcon(new javax.swing.ImageIcon("D:\\back\\LAPORAN KEUNTUNGAN.png")); // NOI18N
        Laporan.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 0, -1, 60));

        jLabel38.setIcon(new javax.swing.ImageIcon("D:\\back\\Rectangle 11.png")); // NOI18N
        Laporan.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1430, -1));
        Laporan.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(-40, 270, -1, -1));
        Laporan.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 440, 260, 90));

        tabken.setModel(new javax.swing.table.DefaultTableModel(
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
        tabken.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jScrollPane2.setViewportView(tabken);

        Laporan.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 330, 1310, 520));

        jLabel68.setIcon(new javax.swing.ImageIcon("D:\\back\\front-view-plant-growing-from-golden-coins-removebg-preview 2.png")); // NOI18N
        Laporan.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(-40, 530, -1, -1));

        card4.setForeground(new java.awt.Color(255, 153, 51));
        card4.setColor1(new java.awt.Color(255, 153, 51));
        card4.setColor2(new java.awt.Color(255, 232, 239));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Total Pendapatan");
        card4.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 220, 45));

        T.setForeground(new java.awt.Color(255, 255, 255));
        T.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        T.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TActionPerformed(evt);
            }
        });
        card4.add(T, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 390, 190));

        Laporan.add(card4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 390, 190));

        card5.setColor1(new java.awt.Color(0, 204, 102));
        card5.setColor2(new java.awt.Color(255, 240, 225));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Keuntungan");
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });
        card5.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 160, 45));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel24.setText("Lihat Detail>");
        jLabel24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel24MouseClicked(evt);
            }
        });
        card5.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 150, 130, 40));

        Untung.setForeground(new java.awt.Color(255, 255, 255));
        Untung.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        Untung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UntungActionPerformed(evt);
            }
        });
        card5.add(Untung, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 420, 190));

        Laporan.add(card5, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 80, 420, 190));

        cari.setIcon(new javax.swing.ImageIcon("D:\\back\\magnifying-glass-search.png")); // NOI18N
        cari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cariMouseClicked(evt);
            }
        });
        Laporan.add(cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(1330, 270, 40, 40));

        jDateChooser1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Laporan.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 280, 160, 40));

        jDateChooser2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Laporan.add(jDateChooser2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 280, 160, 40));

        jPanel3.add(Laporan, "card5");

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 0, 1430, 1000));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dataAdmiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dataAdmiMouseClicked
        jPanel3.removeAll();
        jPanel3.repaint();
        jPanel3.revalidate();
        
        jPanel3.add(Admin);
        jPanel3.repaint();
        jPanel3.revalidate();
    }//GEN-LAST:event_dataAdmiMouseClicked

    private void dataAdmiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dataAdmiMouseEntered
        dataAdmi.setBackground(new Color(240,180,107));
        dataAdmi.setOpaque(true);
    }//GEN-LAST:event_dataAdmiMouseEntered

    private void dataAdmiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dataAdmiMouseExited
        // TODO add your handling code here:
        dataAdmi.setBackground(new Color(44,140,136));
    }//GEN-LAST:event_dataAdmiMouseExited

    private void produkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_produkMouseClicked
        // TODO add your handling code here:
        jPanel3.removeAll();
        jPanel3.repaint();
        jPanel3.revalidate();
        
        jPanel3.add(Produk);
        jPanel3.repaint();
        jPanel3.revalidate();
        kos();
    }//GEN-LAST:event_produkMouseClicked

    private void produkMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_produkMouseEntered
        // TODO add your handling code here:
        produk.setBackground(new Color(240,180,107));
        produk.setOpaque(true);
    }//GEN-LAST:event_produkMouseEntered

    private void produkMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_produkMouseExited
        // TODO add your handling code here:
        produk.setBackground(new Color(44,140,136));
    }//GEN-LAST:event_produkMouseExited

    private void laporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_laporanMouseClicked
        // TODO add your handling code here:
        jPanel3.removeAll();
        jPanel3.repaint();
        jPanel3.revalidate();
        
        jPanel3.add(Laporan);
        jPanel3.repaint();
        jPanel3.revalidate();
        tablekeuntungan();
        new dashKasirLaporan().setVisible(false);
    }//GEN-LAST:event_laporanMouseClicked

    private void laporanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_laporanMouseEntered
        // TODO add your handling code here:
        laporan.setBackground(new Color(240,180,107));
        laporan.setOpaque(true);
    }//GEN-LAST:event_laporanMouseEntered

    private void laporanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_laporanMouseExited
        // TODO add your handling code here:
        laporan.setBackground(new Color(44,140,136));
    }//GEN-LAST:event_laporanMouseExited

    private void AdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AdminMouseClicked
        // TODO add your handling code here:
        jPanel3.removeAll();
        jPanel3.repaint();
        jPanel3.revalidate();
        
        jPanel3.add(Admin);
        jPanel3.repaint();
        jPanel3.revalidate();
    }//GEN-LAST:event_AdminMouseClicked

    private void ProdukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProdukMouseClicked
        // TODO add your handling code here:
        jPanel3.removeAll();
        jPanel3.repaint();
        jPanel3.revalidate();
        
        jPanel3.add(Produk);
        jPanel3.repaint();
        jPanel3.revalidate();
    }//GEN-LAST:event_ProdukMouseClicked

    private void LaporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LaporanMouseClicked
        // TODO add your handling code here:
        jPanel3.removeAll();
        jPanel3.repaint();
        jPanel3.revalidate();
        
        jPanel3.add(Laporan);
        jPanel3.repaint();
        jPanel3.revalidate();
    }//GEN-LAST:event_LaporanMouseClicked

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        // TODO add your handling code here:
        int baris = table.rowAtPoint(evt.getPoint());
        String kode = table.getValueAt(baris, 0).toString();
        K.setText(kode);
        if (table.getValueAt(baris, 1)==null) {
            N.setText("");
        } else {
            N.setText(table.getValueAt(baris, 1).toString());
        }
        TL.setDate(getTanggalFromTable(table, 2));
        if (table.getValueAt(baris, 3)==null) {
            A.setText("");
        }else{
            A.setText(table.getValueAt(baris, 3).toString());
        }
        if (table.getValueAt(baris, 4)==null) {
            NT.setText("");
        }else{
            NT.setText(table.getValueAt(baris, 4).toString());
        }
        if (table.getValueAt(baris, 5)==null) {
            J.setSelectedItem("");
        }else{
            J.setSelectedItem(table.getValueAt(baris, 5).toString());
        }
        if (table.getValueAt(baris, 6)==null) {
            P.setText("");
        }else{
            P.setText(table.getValueAt(baris, 6).toString());
        }
    }//GEN-LAST:event_tableMouseClicked

    private void cariKarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariKarActionPerformed
        cariKar();
    }//GEN-LAST:event_cariKarActionPerformed

    private void tMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tMouseClicked
        // TODO add your handling code here:
        try {
            int i = t.getSelectedRow();
        TableModel model = t.getModel();
        String kode=model.getValueAt(i, 0).toString();
        String nm=model.getValueAt(i, 1).toString();
        String hrg=model.getValueAt(i, 2).toString();
        //String ktg=model.getValueAt(i, 4).toString();
        kp.setText(kode);
        np.setText(nm);
        h.setText(hrg);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_tMouseClicked

    private void dashAdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashAdMouseClicked
        // TODO add your handling code here:
        jPanel3.removeAll();
        jPanel3.repaint();
        jPanel3.revalidate();
        
        jPanel3.add(dashAdmin);
        jPanel3.repaint();
        jPanel3.revalidate();
        tampilpsnmsk();
        tampilpsnpss();
        tampilpsnsls();
        tablpsnbsk();
        tablpsnmgg();
    }//GEN-LAST:event_dashAdMouseClicked

    private void dashAdMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashAdMouseEntered
        // TODO add your handling code here:
        dashAd.setBackground(new Color(240,180,107));
        dashAd.setOpaque(true);
    }//GEN-LAST:event_dashAdMouseEntered

    private void dashAdMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashAdMouseExited
        // TODO add your handling code here:
        dashAd.setBackground(new Color(44,140,136));
    }//GEN-LAST:event_dashAdMouseExited

    private void logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseClicked
        notif obj = new notif(this);
        obj.showMessage("Peringatan", "Apakah Anda Yakin Ingin Keluar");
        if (obj.getMessageType() == notif.MessageType.OK) {
            new login().setVisible(true);
        } else {
            System.out.println("User click batal");
        }
        this.dispose();
    }//GEN-LAST:event_logoutMouseClicked

    private void hapusKarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusKarActionPerformed
        // TODO add your handling code here:
        try {
            String sql ="DELETE FROM karyawan WHERE id_user='"+K.getText()+"'";
            java.sql.Connection conn=k.getcon();
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.execute();
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Berhasil Menghapus Data Karyawan");
            if (obj.getMessageType() == notif.MessageType.OK) {
                System.out.println("User click ya");
            } else {
                System.out.println("User click batal");
            }
            //JOptionPane.showMessageDialog(this, "Berhasil Dihapus");
        }catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        Void();
        table();
    }//GEN-LAST:event_hapusKarActionPerformed

    private void editKarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editKarActionPerformed
        // TODO add your handling code here:
        String tampilan = ("yyyy-MM-dd");
        SimpleDateFormat tampilan1 = new SimpleDateFormat(tampilan);
        String TL = String.valueOf(tampilan1.format(this.TL.getDate()));

        try {
            String sql ="UPDATE karyawan SET id_user='"+K.getText()
            +"',nama='"+N.getText()
            +"',tgl_lahir='"+TL
            +"',alamat='"+A.getText()
            +"',notelp='"+NT.getText()
            +"',akses='"+J.getSelectedItem()
            +"',password='"+P.getText()
            +"'WHERE id_user='"+K.getText()+"'";
            java.sql.Connection conn=k.getcon();
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.execute();
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Data Karyawan Berhasil Di Ubah");
            if (obj.getMessageType() == notif.MessageType.OK) {
                System.out.println("User click ya");
            } else {
                System.out.println("User click batal");
            }
            //JOptionPane.showMessageDialog(null, "Data Karyawati Berhasil di Edit");
        }catch (Exception e) {
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Data Karyawan Gagal Di Ubah"+ e.getMessage());
            if (obj.getMessageType() == notif.MessageType.OK) {
                System.out.println("User click ya");
            } else {
                System.out.println("User click batal");
            }
            }
        Void();
        table();
    }//GEN-LAST:event_editKarActionPerformed

    private void tambahKarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahKarActionPerformed
        // TODO add your handling code here:
        String tampilan = ("yyyy-MM-dd");
        SimpleDateFormat tampilan1 = new SimpleDateFormat(tampilan);
        String TL = String.valueOf(tampilan1.format(this.TL.getDate()));

        try {
            String sqll = "INSERT INTO karyawan  (`id_user`, `nama`, `tgl_lahir`, `alamat`, `notelp`, `akses`, "
                    + "`password`) VALUES "
            + "('"+K.getText()+"','"
            +N.getText()+"','"+TL+"','"+
            A.getText()+"','"+
            NT.getText()+"','"+J.getSelectedItem()+"','"+P.getText()+"')";
            java.sql.Connection conn=k.getcon();
            java.sql.PreparedStatement pstl= conn.prepareStatement(sqll);
            pstl.execute();
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Data Karyawan Berhasil Ditambahkan");
            if (obj.getMessageType() == notif.MessageType.OK) {
                System.out.println("User click ya");
            } else {
                System.out.println("User click batal");
            }
            //JOptionPane.showMessageDialog(null, "Penyimpanan Data Berhasil");
        }catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        Void();
        table();
    }//GEN-LAST:event_tambahKarActionPerformed

    private void hapusProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusProActionPerformed
        // TODO add your handling code here:
        try {
            String sql = "DELETE from produk where kode_produk = '" + kp.getText() + "'";
            Connection con=k.getcon();
            java.sql.Connection conn=k.getcon();
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.execute();
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Data Produk Berhasil Dihapus");
            if (obj.getMessageType() == notif.MessageType.OK) {
                System.out.println("User click ya");
            } else {
                System.out.println("User click batal");
                
            }
        } catch (Exception e) {
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Data Produk Gagal Dihapus" + e.getMessage());
        }
        kos();
        table2();
    }//GEN-LAST:event_hapusProActionPerformed

    private void editProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProActionPerformed
        // TODO add your handling code here:
        int rowCount = t.getRowCount();
        int columnIndex = 2;
        String field=np.getText();
        int hp;
        hp=Integer.valueOf(h.getText());
        double kali=hp*0.1;
        double hasil =hp-kali;
        String nm=null;
        try {
            String s="select nama_produk from produk where nama_produk='"+np.getText()+"'";
            Connection c=k.getcon();
            PreparedStatement p=c.prepareStatement(s);
            ResultSet r=p.executeQuery();
            while (r.next()) {
                nm=r.getString("nama_produk");
            }if (nm==null) {
                try {
            String sql = "UPDATE produk SET nama_produk ='"+np.getText()
            +"',harga_jual ='"+h.getText()
            +"', harga_produksi='"+String.valueOf(hasil)+"' WHERE kode_produk ='"+kp.getText()+"'";            
            Connection con=k.getcon();
            java.sql.Connection conn=k.getcon();
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.execute();
            
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Data Produk Berhasil Di Ubah");
            if (obj.getMessageType() == notif.MessageType.OK) {
                System.out.println("User click ya");
            } else {
                System.out.println("User click batal");
            }
        } catch (Exception e) {
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Data Produk Gagal Di Ubah" + e.getMessage());
            if (obj.getMessageType() == notif.MessageType.OK) {
                System.out.println("User click ya");
            } else {
                System.out.println("User click batal");
            }
        }
        kos();
        table2();
            }else{
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Nama Produk telah terdaftar");
            if (obj.getMessageType() == notif.MessageType.OK) {
                System.out.println("User click ya");
            } else {
                System.out.println("User click batal");
            }
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_editProActionPerformed

    private void tambahProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahProActionPerformed
        // TODO add your handling code here:
        int rowCount = t.getRowCount();
        int columnIndex = 2;
        String field=np.getText();
        double hp;
        hp=Double.valueOf(h.getText());
        double kali=hp*0.1;
        double hasil =hp-kali;
        String nm=null;
        try {
            String s="select nama_produk from produk where nama_produk='"+np.getText()+"'";
            Connection c=k.getcon();
            PreparedStatement p=c.prepareStatement(s);
            ResultSet r=p.executeQuery();
            while (r.next()) {
                nm=r.getString("nama_produk");
            }
        if (nm==null) {
            try {
            String sql = "INSERT into produk (kode_produk, nama_produk, harga_jual, harga_produksi) Values"
            + "('" + kp.getText() + "','" + np.getText() 
            + "','" + h.getText() + "','"+String.valueOf(hasil)+"')";
            Connection con=k.getcon();
            java.sql.Connection conn=k.getcon();
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.execute();
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Data Produk Berhasil Ditambahkan");
            if (obj.getMessageType() == notif.MessageType.OK) {
                System.out.println("User click ya");
            } else {
                System.out.println("User click batal");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal Ditambahkan" + e.getMessage() );
        }
        } else{
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Nama Produk telah terdaftar");
            if (obj.getMessageType() == notif.MessageType.OK) {
                System.out.println("User click ya");
            } else {
                System.out.println("User click batal");
            }
        }
        try {
            String sql="insert into detail_produk values ('"+kp.getText()+"','"+cb.getSelectedItem()+"')";
            Connection con=k.getcon();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "gagal");
        }     
        kos();
        table2();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_tambahProActionPerformed

    private void cbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbActionPerformed
        switch(cb.getSelectedItem().toString()){
            case "Tas":
                autonumberTas();
                break;
            case "Dompet":
                autonumberDpt();
                break;
        }
    }//GEN-LAST:event_cbActionPerformed

    private void npMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_npMouseClicked
        //autonumber();
    }//GEN-LAST:event_npMouseClicked

    private void KActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KActionPerformed

    private void carMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_carMouseClicked
        cariKar();
        table();
    }//GEN-LAST:event_carMouseClicked

    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel19MouseClicked

    private void JActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JActionPerformed

    private void TActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TActionPerformed

    private void UntungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UntungActionPerformed
        
    }//GEN-LAST:event_UntungActionPerformed

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        new dashKasirLaporan().setVisible(true);

    }//GEN-LAST:event_jLabel21MouseClicked

    private void hActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hActionPerformed
        
    }//GEN-LAST:event_hActionPerformed

    private void jLabel75MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel75MouseClicked

    }//GEN-LAST:event_jLabel75MouseClicked

    private void jLabel73MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel73MouseClicked
        
    }//GEN-LAST:event_jLabel73MouseClicked

    private void jLabel74MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel74MouseClicked
       
    }//GEN-LAST:event_jLabel74MouseClicked

    private void jLabel80MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel80MouseClicked
        new masuk().setVisible(true);
    }//GEN-LAST:event_jLabel80MouseClicked

    private void jLabel81MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel81MouseClicked
        new proses().setVisible(true);
    }//GEN-LAST:event_jLabel81MouseClicked

    private void jLabel82MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel82MouseClicked
        new selesai().setVisible(true);
    }//GEN-LAST:event_jLabel82MouseClicked

    private void npActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_npActionPerformed
        int rowCount = t.getRowCount();
        int columnIndex = 2;
        String field=np.getText();
        for( int row = 0; row < rowCount; row++) {
            String nilai1 = t.getValueAt(row, columnIndex).toString();
            
            if( field.equals(nilai1)) {
                JOptionPane.showMessageDialog(null, "anjing");
                break;
            } else {
                JOptionPane.showMessageDialog(null, "kontol");
            }
        }
    }//GEN-LAST:event_npActionPerformed

    private void cariProMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cariProMouseClicked
        cariPro();
    }//GEN-LAST:event_cariProMouseClicked

    private void cariProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariProActionPerformed

    }//GEN-LAST:event_cariProActionPerformed

    private void cariKarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cariKarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cariKarMouseClicked

    private void jLabel24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel24MouseClicked
    new dashKasirLaporan().setVisible(true);        
    }//GEN-LAST:event_jLabel24MouseClicked

    private void cariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cariMouseClicked
        DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("No");
        tbl.addColumn("Tanggal");
        tbl.addColumn("No. Pemesanan");
        tbl.addColumn("Total Pembayaran");
        tbl.addColumn("Kode Produk");
        tbl.addColumn("Jumlah");
        tbl.addColumn("Total Harga");
        String getDate = "yyyy-MM-dd";
        String lah = null;
        SimpleDateFormat fm = new SimpleDateFormat(getDate);
        String tanggal = String.valueOf(fm.format(jDateChooser1.getDate()));
        String tanggal1= String.valueOf(fm.format(jDateChooser2.getDate()));
        try {
            int nomor = 1;
            String sql = "SELECT pemesanan.no_pemesanan, pemesanan.tgl_pemesanan, floor(pemesanan.total_pembayaran-(pemesanan.total_pembayaran*(pemesanan.diskon/100))) AS total, detail_pemesanan.kode_produk, detail_pemesanan.jumlah, detail_pemesanan.total_harga FROM pemesanan JOIN detail_pemesanan ON pemesanan.no_pemesanan=detail_pemesanan.no_pemesanan WHERE pemesanan.status='Lunas' and pemesanan.tgl_pemesanan BETWEEN '"+tanggal+"' AND '"+tanggal1+"';";
            Connection con=k.getcon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()) {
                lah = rs.getString(1);
                tbl.addRow(new Object[]{
                    ("" + nomor++),
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6)
                });
            }
            if (lah==null) {
            notif obj = new notif(this);
            obj.showMessage("Message Dialog", "Data transaksi pada rentang tanggal tersebut tidak tersedia");
            if (obj.getMessageType() == notif.MessageType.OK) {
                System.out.println("User click ya");
                DefaultTableModel tbll = new DefaultTableModel();
            tbll.addColumn("No");
            tbll.addColumn("Tanggal");
            tbll.addColumn("No. Pemesanan");
            tbll.addColumn("Total Pembayaran");
            tbll.addColumn("Kode Produk");
            tbll.addColumn("Jumlah");
            tbll.addColumn("Total Harga");
                tabken.setModel(tbll);
            } else {
                System.out.println("User click batal");
            }
            }else{
                tabken.setModel(tbl);
            tablekeuntungan();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "" +e.getMessage());
        }
    }//GEN-LAST:event_cariMouseClicked

    private void NTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NTActionPerformed
        if (!NT.getText().matches("[0-9]*")) {
            infoo.setText("No. telpon harus berupa angka");
            infoo.setVisible(true);
        } else if (NT.getText().trim().length()>13 ){
            infoo.setText("No.telpon tidak boleh lebih dari 13");
            infoo.setVisible(true);
        }else if (NT.getText().trim().length()<11){
            infoo.setText("No.telpon tidak boleh kurang dari 11");
            infoo.setVisible(true);
        } else {
            infoo.setVisible(false);
        }
    }//GEN-LAST:event_NTActionPerformed

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
            java.util.logging.Logger.getLogger(admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private textfield_suggestion.TextFieldSuggestion A;
    private javax.swing.JPanel Admin;
    private textfield_suggestion.TextFieldSuggestion C;
    private combo_suggestion.ComboBoxSuggestion J;
    private textfield_suggestion.TextFieldSuggestion K;
    private javax.swing.JPanel Laporan;
    private textfield_suggestion.TextFieldSuggestion N;
    private textfield_suggestion.TextFieldSuggestion NT;
    private textfield_suggestion.TextFieldSuggestion P;
    private javax.swing.JPanel Produk;
    private custom.TextFieldSuggestion T;
    private com.toedter.calendar.JDateChooser TL;
    private custom.TextFieldSuggestion Untung;
    private javax.swing.JLabel car;
    private custom.card card1;
    private custom.card card2;
    private custom.card card3;
    private custom.card card4;
    private custom.card card5;
    private javax.swing.JLabel cari;
    private rojerusan.RSMaterialButtonRectangle cariKar;
    private rojerusan.RSMaterialButtonRectangle cariPro;
    private combo_suggestion.ComboBoxSuggestion cb;
    private textfield_suggestion.TextFieldSuggestion cr;
    private javax.swing.JPanel dashAd;
    private javax.swing.JPanel dashAdmin;
    private javax.swing.JPanel dataAdmi;
    private rojerusan.RSMaterialButtonRectangle editKar;
    private rojerusan.RSMaterialButtonRectangle editPro;
    private textfield_suggestion.TextFieldSuggestion h;
    private rojerusan.RSMaterialButtonRectangle hapusKar;
    private rojerusan.RSMaterialButtonRectangle hapusPro;
    private javax.swing.JLabel info;
    private javax.swing.JLabel infoo;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane7;
    private textfield_suggestion.TextFieldSuggestion kp;
    private javax.swing.JPanel laporan;
    private javax.swing.JLabel logout;
    private textfield_suggestion.TextFieldSuggestion np;
    private javax.swing.JPanel produk;
    private custom.table_custom t;
    private custom.table_custom tabken;
    private custom.table_custom table;
    private custom.table_custom table_custom1;
    private custom.table_custom table_custom4;
    private rojerusan.RSMaterialButtonRectangle tambahKar;
    private rojerusan.RSMaterialButtonRectangle tambahPro;
    private custom.TextFieldSuggestion textFieldSuggestion1;
    private custom.TextFieldSuggestion textFieldSuggestion2;
    private custom.TextFieldSuggestion textFieldSuggestion3;
    // End of variables declaration//GEN-END:variables
}
