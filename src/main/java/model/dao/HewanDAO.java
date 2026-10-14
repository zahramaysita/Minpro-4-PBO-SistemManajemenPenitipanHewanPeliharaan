
package model.dao;

import config.Koneksi;
import model.Hewan;
import model.Kucing;
import model.Anjing;
import model.Kelinci;
import model.Hamster;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HewanDAO {

    // Menampilkan semua data hewan dari database
    public List<Hewan> getSemuaHewan() {
        List<Hewan> daftarHewan = new ArrayList<>();
        String sql = "SELECT * FROM hewan";

        try (
            Connection koneksi = Koneksi.getConnection();
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                int id = rs.getInt("id_hewan");
                String nama = rs.getString("nama_hewan");
                String jenis = rs.getString("jenis_hewan");
                String ras = rs.getString("ras");

                Hewan hewan = null;

                switch (jenis.toLowerCase()) {
                    case "kucing":
                        hewan = new Kucing(id, nama, ras);
                        break;

                    case "anjing":
                        hewan = new Anjing(id, nama, ras);
                        break;

                    case "kelinci":
                        hewan = new Kelinci(id, nama, ras);
                        break;

                    case "hamster":
                        hewan = new Hamster(id, nama, ras);
                        break;
                }

                if (hewan != null) {
                    daftarHewan.add(hewan);
                }
            }

        } catch (SQLException e) {
            System.out.println(
                "Gagal mengambil data hewan: " + e.getMessage()
            );
        }

        return daftarHewan;
    }

    // Menambahkan data hewan ke database
    public boolean tambahHewan(Hewan hewan, String jenis, String ras) {
        String sql = "INSERT INTO hewan "
                + "(id_hewan, nama_hewan, jenis_hewan, ras) "
                + "VALUES (?, ?, ?, ?)";

        try (
            Connection koneksi = Koneksi.getConnection();
            PreparedStatement ps = koneksi.prepareStatement(sql)
        ) {
            ps.setInt(1, hewan.getIdHewan());
            ps.setString(2, hewan.getNamaHewan());
            ps.setString(3, jenis);
            ps.setString(4, ras);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                "Gagal menambahkan hewan: " + e.getMessage()
            );
            return false;
        }
    }
    public boolean updateNamaHewan(int id, String namaBaru) {
        String sql = "UPDATE hewan SET nama_hewan = ? WHERE id_hewan = ?";

        try (
            Connection koneksi = Koneksi.getConnection();
            PreparedStatement ps = koneksi.prepareStatement(sql)
        ) {
            ps.setString(1, namaBaru);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                "Gagal memperbarui data hewan: " + e.getMessage()
            );
            return false;
        }
    }
    
    public boolean hapusHewan(int id) {
        String sql = "DELETE FROM hewan WHERE id_hewan = ?";

        try (
            Connection koneksi = Koneksi.getConnection();
            PreparedStatement ps = koneksi.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                "Gagal menghapus data hewan: " + e.getMessage()
            );
            return false;
        }
    }
}

