package config;

import java.sql.Connection;
import java.sql.SQLException;


public class TesKoneksi {
     public static void main(String[] args) {
        try {
            Connection koneksi = Koneksi.getConnection();

            if (koneksi != null) {
                System.out.println("Koneksi database berhasil!");
                koneksi.close();
            }

        } catch (SQLException e) {
            System.out.println("Koneksi database gagal!");
            System.out.println(e.getMessage());
        }
    }
    
}
