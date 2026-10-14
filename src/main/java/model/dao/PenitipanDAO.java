
package model.dao;

import config.Koneksi;
import model.Penitipan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PenitipanDAO {

    // Menampilkan semua data penitipan
    public List<Penitipan> getSemuaPenitipan() {
        List<Penitipan> daftarPenitipan = new ArrayList<>();

        String sql = "SELECT * FROM penitipan";

        try (
            Connection koneksi = Koneksi.getConnection();
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Penitipan penitipan = new Penitipan(
                    rs.getInt("id_penitipan"),
                    rs.getInt("id_hewan"),
                    rs.getString("tanggal_masuk"),
                    rs.getInt("lama_penitipan")
                );

                daftarPenitipan.add(penitipan);
            }

        } catch (SQLException e) {
            System.out.println(
                "Gagal mengambil data penitipan: " + e.getMessage()
            );
        }

        return daftarPenitipan;
    }

    // Menambahkan data penitipan
    public boolean tambahPenitipan(Penitipan penitipan) {
        String sql = "INSERT INTO penitipan "
                + "(id_penitipan, id_hewan, tanggal_masuk, lama_penitipan) "
                + "VALUES (?, ?, ?, ?)";

        try (
            Connection koneksi = Koneksi.getConnection();
            PreparedStatement ps = koneksi.prepareStatement(sql)
        ) {
            ps.setInt(1, penitipan.getIdPenitipan());
            ps.setInt(2, penitipan.getIdHewan());
            ps.setString(3, penitipan.getTanggalMasuk());
            ps.setInt(4, penitipan.getLamaPenitipan());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                "Gagal menambahkan penitipan: " + e.getMessage()
            );
            return false;
        }
    }

    // Mengubah tanggal dan lama penitipan
    public boolean updatePenitipan(
            int id, String tanggalBaru, int lamaBaru) {

        String sql = "UPDATE penitipan "
                + "SET tanggal_masuk = ?, lama_penitipan = ? "
                + "WHERE id_penitipan = ?";

        try (
            Connection koneksi = Koneksi.getConnection();
            PreparedStatement ps = koneksi.prepareStatement(sql)
        ) {
            ps.setString(1, tanggalBaru);
            ps.setInt(2, lamaBaru);
            ps.setInt(3, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                "Gagal memperbarui penitipan: " + e.getMessage()
            );
            return false;
        }
    }

    // Menghapus data penitipan
    public boolean hapusPenitipan(int id) {
        String sql = "DELETE FROM penitipan WHERE id_penitipan = ?";

        try (
            Connection koneksi = Koneksi.getConnection();
            PreparedStatement ps = koneksi.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                "Gagal menghapus penitipan: " + e.getMessage()
            );
            return false;
        }
    }
}
