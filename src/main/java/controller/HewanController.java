package controller;

import java.util.ArrayList;
import java.util.Scanner;

import model.Anjing;
import model.Hamster;
import model.Hewan;
import model.Kelinci;
import model.Kucing;
import model.Penitipan;
import model.Service;
import view.HewanView;
import model.dao.HewanDAO;
import model.dao.PenitipanDAO;
import java.util.List;


public class HewanController {

    private HewanView view;
    private Scanner scanner;
    private HewanDAO hewanDAO = new HewanDAO();
    private PenitipanDAO penitipanDAO = new PenitipanDAO();

    
    public HewanController(
            HewanView view,
            Scanner scanner) {

        this.view = view;
        this.scanner = scanner;
    }

    // =========================
    // JALANKAN PROGRAM
    // =========================
    public void jalankanProgram() {
        boolean berjalan = true;
        while (berjalan) {
            view.tampilkanMenu();
            if (!scanner.hasNextInt()) {
                view.tampilkanPesan(">> Pilihan harus berupa angka!");
                scanner.nextLine();
                continue;
            }

            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1 -> tambahHewan();
                case 2 -> tampilkanHewan();
                case 3 -> updateHewan();
                case 4 -> hapusHewan();
                case 5 -> tambahPenitipan();
                case 6 -> tampilkanPenitipan();
                case 7 -> updatePenitipan();
                case 8 -> hapusPenitipan();
                case 9 -> cariHewan();
                case 10 -> {
                    view.tampilkanPesan("\n=== Terima kasih sudah menggunakan program ini ===");
                    view.tampilkanPesan("==== Byeee ====");
                    berjalan = false;
                }

                default -> view.tampilkanPesan(">> Pilihan tidak valid!");
            }
        }
    }

    // =========================
    // TAMBAH HEWAN
    // =========================
    
    public void tambahHewan() {

        // Mengambil data hewan yang sudah tersimpan di database
        List<Hewan> daftarHewan = hewanDAO.getSemuaHewan();

        view.tampilkanInput("ID Hewan: ");

        if (!scanner.hasNextInt()) {
            view.tampilkanPesan(">> ID hewan harus berupa angka!");
            scanner.nextLine();
            return;
        }

        int id = scanner.nextInt();
        scanner.nextLine();

        if (id <= 0) {
            view.tampilkanPesan(">> ID hewan tidak valid!");
            return;
        }

        // Memeriksa ID agar tidak duplikat
        for (Hewan h : daftarHewan) {
            if (h.getIdHewan() == id) {
                view.tampilkanPesan(">> ID hewan sudah digunakan!");
                return;
            }
        }

        view.tampilkanInput("Nama Hewan: ");
        String nama = scanner.nextLine().trim();

        if (nama.isEmpty()) {
            view.tampilkanPesan(">> Nama hewan tidak boleh kosong!");
            return;
        }

        view.tampilkanPesan("\nPilih Jenis Hewan:");
        view.tampilkanPesan("1. Kucing");
        view.tampilkanPesan("2. Anjing");
        view.tampilkanPesan("3. Kelinci");
        view.tampilkanPesan("4. Hamster");

        view.tampilkanInput("Pilihan: ");

        if (!scanner.hasNextInt()) {
            view.tampilkanPesan(">> Pilihan harus berupa angka!");
            scanner.nextLine();
            return;
        }

        int pilihan = scanner.nextInt();
        scanner.nextLine();

        Hewan hewanBaru;
        String jenis;
        String ras;

        switch (pilihan) {
            case 1 -> {
                jenis = "Kucing";
                view.tampilkanInput("Ras Kucing: ");
                ras = scanner.nextLine().trim();

                if (ras.isEmpty()) {
                    view.tampilkanPesan(">> Ras kucing tidak boleh kosong!");
                    return;
                }

                hewanBaru = new Kucing(id, nama, ras);
            }

            case 2 -> {
                jenis = "Anjing";
                view.tampilkanInput("Ras Anjing: ");
                ras = scanner.nextLine().trim();

                if (ras.isEmpty()) {
                    view.tampilkanPesan(">> Ras anjing tidak boleh kosong!");
                    return;
                }

                hewanBaru = new Anjing(id, nama, ras);
            }

            case 3 -> {
                jenis = "Kelinci";
                view.tampilkanInput("Ras Kelinci: ");
                ras = scanner.nextLine().trim();

                if (ras.isEmpty()) {
                    view.tampilkanPesan(">> Ras kelinci tidak boleh kosong!");
                    return;
                }

                hewanBaru = new Kelinci(id, nama, ras);
            }

            case 4 -> {
                jenis = "Hamster";
                view.tampilkanInput("Ras Hamster: ");
                ras = scanner.nextLine().trim();

                if (ras.isEmpty()) {
                    view.tampilkanPesan(">> Ras hamster tidak boleh kosong!");
                    return;
                }

                hewanBaru = new Hamster(id, nama, ras);
            }

            default -> {
                view.tampilkanPesan(">> Pilihan jenis hewan tidak valid!");
                return;
            }
        }

        // Menyimpan data melalui DAO ke database MySQL
        boolean berhasil = hewanDAO.tambahHewan(
                hewanBaru, jenis, ras
        );

        if (berhasil) {
            view.tampilkanPesan(">> Hewan berhasil ditambahkan ke database!");
        } else {
            view.tampilkanPesan(">> Hewan gagal ditambahkan ke database!");
        }
    }


    // =========================
    // TAMPILKAN HEWAN
    // =========================
    
    public void tampilkanHewan() {

        List<Hewan> daftarHewan = hewanDAO.getSemuaHewan();

        if (daftarHewan.isEmpty()) {
            view.tampilkanPesan(">> Belum ada data hewan.");
            return;
        }

        for (Hewan h : daftarHewan) {

            view.tampilkanPesan("ID Hewan: " + h.getIdHewan());
            view.tampilkanPesan("Nama Hewan: " + h.getNamaHewan());
            view.tampilkanPesan("Info Hewan: " + h.getInfo());

            if (h instanceof Kucing k) {
                view.tampilkanPesan("Jenis Hewan: Kucing");
                view.tampilkanPesan("Ras: " + k.getRas());

            } else if (h instanceof Anjing a) {
                view.tampilkanPesan("Jenis Hewan: Anjing");
                view.tampilkanPesan("Ras: " + a.getRas());

            } else if (h instanceof Kelinci k) {
                view.tampilkanPesan("Jenis Hewan: Kelinci");
                view.tampilkanPesan("Ras: " + k.getRasKelinci());

            } else if (h instanceof Hamster hm) {
                view.tampilkanPesan("Jenis Hewan: Hamster");
                view.tampilkanPesan("Ras: " + hm.getRasHamster());
            }

            view.tampilkanPesan("----------------------------");
        }
    }


    // =========================
    // HAPUS HEWAN
    // =========================
    
    public void hapusHewan() {

        // Mengambil data hewan dari database
        List<Hewan> daftarHewan = hewanDAO.getSemuaHewan();

        view.tampilkanInput("Masukkan ID Hewan: ");

        if (!scanner.hasNextInt()) {
            view.tampilkanPesan(">> ID hewan harus berupa angka!");
            scanner.nextLine();
            return;
        }

        int idTarget = scanner.nextInt();
        scanner.nextLine();

        // Memeriksa apakah ID hewan tersedia
        boolean ditemukan = false;

        for (Hewan h : daftarHewan) {
            if (h.getIdHewan() == idTarget) {
                ditemukan = true;
                break;
            }
        }

        if (!ditemukan) {
            view.tampilkanPesan(">> Hewan tidak ditemukan!");
            return;
        }

        // Menghapus data hewan dari database
        boolean berhasil = hewanDAO.hapusHewan(idTarget);

        if (berhasil) {
            view.tampilkanPesan(
                ">> Hewan berhasil dihapus dari database!"
            );
        } else {
            view.tampilkanPesan(
                ">> Hewan gagal dihapus dari database!"
            );
        }
    }


    // =========================
    // UPDATE HEWAN
    // =========================
    
    public void updateHewan() {

        // Mengambil data hewan dari database
        List<Hewan> daftarHewan = hewanDAO.getSemuaHewan();

        view.tampilkanInput("Masukkan ID Hewan: ");

        if (!scanner.hasNextInt()) {
            view.tampilkanPesan(">> ID hewan harus berupa angka!");
            scanner.nextLine();
            return;
        }

        int idTarget = scanner.nextInt();
        scanner.nextLine();

        // Memeriksa apakah ID hewan ada di database
        Hewan hewanDitemukan = null;

        for (Hewan h : daftarHewan) {
            if (h.getIdHewan() == idTarget) {
                hewanDitemukan = h;
                break;
            }
        }

        if (hewanDitemukan == null) {
            view.tampilkanPesan(">> Hewan tidak ditemukan!");
            return;
        }

        view.tampilkanInput("Nama Hewan Baru: ");
        String namaBaru = scanner.nextLine().trim();

        if (namaBaru.isEmpty()) {
            view.tampilkanPesan(">> Nama hewan tidak boleh kosong!");
            return;
        }

        // Memperbarui nama hewan di database
        boolean berhasil = hewanDAO.updateNamaHewan(
            idTarget, namaBaru
        );

        if (berhasil) {
            view.tampilkanPesan(
                ">> Data hewan berhasil diperbarui!"
            );
        } else {
            view.tampilkanPesan(
                ">> Data hewan gagal diperbarui!"
            );
        }
    }

    
    // =========================
    // TAMBAH PENITIPAN
    // =========================
    
    public void tambahPenitipan() {
        List<Hewan> daftarHewan = hewanDAO.getSemuaHewan();
        List<Penitipan> daftarPenitipan = penitipanDAO.getSemuaPenitipan();

        if (daftarHewan.isEmpty()) {
            view.tampilkanPesan(">> Belum ada data hewan di database!");
            return;
        }

        view.tampilkanInput("Masukkan ID Penitipan: ");

        if (!scanner.hasNextInt()) {
            view.tampilkanPesan(">> ID harus berupa angka!");
            scanner.nextLine();
            return;
        }

        int idPenitipan = scanner.nextInt();
        scanner.nextLine();

        if (idPenitipan <= 0) {
            view.tampilkanPesan(">> ID harus lebih dari 0!");
            return;
        }

        for (Penitipan p : daftarPenitipan) {
            if (p.getIdPenitipan() == idPenitipan) {
                view.tampilkanPesan(">> ID Penitipan sudah digunakan!");
                return;
            }
        }

        view.tampilkanInput("Masukkan ID Hewan: ");

        if (!scanner.hasNextInt()) {
            view.tampilkanPesan(">> ID Hewan harus berupa angka!");
            scanner.nextLine();
            return;
        }

        int idHewan = scanner.nextInt();
        scanner.nextLine();

        boolean hewanDitemukan = false;

        for (Hewan h : daftarHewan) {
            if (h.getIdHewan() == idHewan) {
                hewanDitemukan = true;
                break;
            }
        }

        if (!hewanDitemukan) {
            view.tampilkanPesan(">> ID Hewan tidak ditemukan!");
            return;
        }

        view.tampilkanInput("Tanggal Masuk (yyyy-MM-dd): ");
        String tanggalMasuk = scanner.nextLine().trim();

        try {
            java.time.LocalDate.parse(tanggalMasuk);
        } catch (java.time.format.DateTimeParseException e) {
            view.tampilkanPesan(
                ">> Format tanggal salah! Gunakan yyyy-MM-dd."
            );
            return;
        }

        view.tampilkanInput("Lama Penitipan (hari): ");

        if (!scanner.hasNextInt()) {
            view.tampilkanPesan(">> Lama penitipan harus berupa angka!");
            scanner.nextLine();
            return;
        }

        int lamaPenitipan = scanner.nextInt();
        scanner.nextLine();

        if (lamaPenitipan <= 0) {
            view.tampilkanPesan(">> Lama penitipan harus lebih dari 0!");
            return;
        }

        Penitipan penitipanBaru = new Penitipan(
            idPenitipan,
            idHewan,
            tanggalMasuk,
            lamaPenitipan
        );

        boolean berhasil = penitipanDAO.tambahPenitipan(penitipanBaru);

        if (berhasil) {
            view.tampilkanPesan(
                ">> Data penitipan berhasil ditambahkan ke database!"
            );
        } else {
            view.tampilkanPesan(
                ">> Data penitipan gagal ditambahkan!"
            );
        }
    }

    // =========================
    // TAMPILKAN PENITIPAN
    // =========================
    
    public void tampilkanPenitipan() {
        List<Penitipan> daftarPenitipan =
                penitipanDAO.getSemuaPenitipan();

        List<Hewan> daftarHewan =
                hewanDAO.getSemuaHewan();

        if (daftarPenitipan.isEmpty()) {
            view.tampilkanPesan(">> Belum ada data penitipan.");
            return;
        }

        view.tampilkanPesan("===== DAFTAR PENITIPAN =====");

        for (Penitipan p : daftarPenitipan) {
            view.tampilkanPesan(
                "ID Penitipan: " + p.getIdPenitipan()
            );

            view.tampilkanPesan(
                "ID Hewan: " + p.getIdHewan()
            );

            boolean hewanDitemukan = false;

            for (Hewan h : daftarHewan) {
                if (h.getIdHewan() == p.getIdHewan()) {
                    view.tampilkanPesan(
                        "Nama Hewan: " + h.getNamaHewan()
                    );

                    hewanDitemukan = true;
                    break;
                }
            }

            if (!hewanDitemukan) {
                view.tampilkanPesan(
                    "Nama Hewan: Tidak ditemukan"
                );
            }

            view.tampilkanPesan(
                "Tanggal Masuk: " + p.getTanggalMasuk()
            );

            view.tampilkanPesan(
                "Lama Penitipan: "
                + p.getLamaPenitipan() + " hari"
            );

            view.tampilkanPesan("----------------------------");
        }
    }


    // =========================
    // HAPUS PENITIPAN
    // =========================
    
    public void hapusPenitipan() {
        List<Penitipan> daftarPenitipan =
                penitipanDAO.getSemuaPenitipan();

        if (daftarPenitipan.isEmpty()) {
            view.tampilkanPesan(">> Belum ada data penitipan.");
            return;
        }

        view.tampilkanInput("Masukkan ID Penitipan: ");

        if (!scanner.hasNextInt()) {
            view.tampilkanPesan(">> ID harus berupa angka!");
            scanner.nextLine();
            return;
        }

        int idTarget = scanner.nextInt();
        scanner.nextLine();

        boolean ditemukan = false;

        for (Penitipan p : daftarPenitipan) {
            if (p.getIdPenitipan() == idTarget) {
                ditemukan = true;
                break;
            }
        }

        if (!ditemukan) {
            view.tampilkanPesan(">> Data penitipan tidak ditemukan!");
            return;
        }

        boolean berhasil = penitipanDAO.hapusPenitipan(idTarget);

        if (berhasil) {
            view.tampilkanPesan(
                ">> Data penitipan berhasil dihapus dari database!"
            );
        } else {
            view.tampilkanPesan(
                ">> Data penitipan gagal dihapus!"
            );
        }
    }

    // =========================
    // UPDATE PENITIPAN
    // =========================
    
    public void updatePenitipan() {
        List<Penitipan> daftarPenitipan =
                penitipanDAO.getSemuaPenitipan();

        if (daftarPenitipan.isEmpty()) {
            view.tampilkanPesan(">> Belum ada data penitipan.");
            return;
        }

        view.tampilkanInput("Masukkan ID Penitipan: ");

        if (!scanner.hasNextInt()) {
            view.tampilkanPesan(">> ID harus berupa angka!");
            scanner.nextLine();
            return;
        }

        int idTarget = scanner.nextInt();
        scanner.nextLine();

        boolean ditemukan = false;

        for (Penitipan p : daftarPenitipan) {
            if (p.getIdPenitipan() == idTarget) {
                ditemukan = true;
                break;
            }
        }

        if (!ditemukan) {
            view.tampilkanPesan(">> Data penitipan tidak ditemukan!");
            return;
        }

        view.tampilkanInput("Tanggal Masuk Baru (yyyy-MM-dd): ");
        String tanggalBaru = scanner.nextLine().trim();

        try {
            java.time.LocalDate.parse(tanggalBaru);
        } catch (java.time.format.DateTimeParseException e) {
            view.tampilkanPesan(
                ">> Format tanggal salah! Gunakan yyyy-MM-dd."
            );
            return;
        }

        view.tampilkanInput("Lama Penitipan Baru (hari): ");

        if (!scanner.hasNextInt()) {
            view.tampilkanPesan(
                ">> Lama penitipan harus berupa angka!"
            );
            scanner.nextLine();
            return;
        }

        int lamaBaru = scanner.nextInt();
        scanner.nextLine();

        if (lamaBaru <= 0) {
            view.tampilkanPesan(
                ">> Lama penitipan harus lebih dari 0!"
            );
            return;
        }

        boolean berhasil = penitipanDAO.updatePenitipan(
            idTarget, tanggalBaru, lamaBaru
        );

        if (berhasil) {
            view.tampilkanPesan(
                ">> Data penitipan berhasil diperbarui!"
            );
        } else {
            view.tampilkanPesan(
                ">> Data penitipan gagal diperbarui!"
            );
        }
    }

    // =========================
    // CARI HEWAN
    // =========================
    public void cariHewan() {

        List<Hewan> daftarHewan = hewanDAO.getSemuaHewan();

        if (daftarHewan.isEmpty()) {
            view.tampilkanPesan(">> Belum ada data hewan di database.");
            return;
        }

        view.tampilkanInput("Masukkan ID Hewan yang dicari: ");

        if (!scanner.hasNextInt()) {
            view.tampilkanPesan(">> ID hewan harus berupa angka!");
            scanner.nextLine();
            return;
        }

        int idTarget = scanner.nextInt();
        scanner.nextLine();

        for (Hewan h : daftarHewan) {

            if (h.getIdHewan() == idTarget) {

                view.tampilkanPesan("=== DATA HEWAN DITEMUKAN ===");
                view.tampilkanPesan("ID Hewan: " + h.getIdHewan());
                view.tampilkanPesan("Nama Hewan: " + h.getNamaHewan());

                if (h instanceof Kucing k) {
                    view.tampilkanPesan("Jenis Hewan: Kucing");
                    view.tampilkanPesan("Ras: " + k.getRas());

                } else if (h instanceof Anjing a) {
                    view.tampilkanPesan("Jenis Hewan: Anjing");
                    view.tampilkanPesan("Ras: " + a.getRas());

                } else if (h instanceof Kelinci k) {
                    view.tampilkanPesan("Jenis Hewan: Kelinci");
                    view.tampilkanPesan("Ras: " + k.getRasKelinci());

                } else if (h instanceof Hamster hm) {
                    view.tampilkanPesan("Jenis Hewan: Hamster");
                    view.tampilkanPesan("Ras: " + hm.getRasHamster());
                }

                return;
            }
        }

        view.tampilkanPesan(">> Hewan tidak ditemukan!");
    }
}