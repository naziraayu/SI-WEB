package sriknd;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class koneksi {
    private String url="jdbc:mysql://localhost/griya";
    private String username="root";
    private String passwoard="";
    Connection con;
    
    public void connect(){
        try {
            con=DriverManager.getConnection(url, username, passwoard);
            System.out.println("koneksi berhasil");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public Connection getcon(){
        return con;
    }
}
