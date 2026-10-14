
CREATE DATABASE db_penitipan_hewan;
USE db_penitipan_hewan;

-- Tabel data hewan
CREATE TABLE hewan (
    id_hewan INT PRIMARY KEY,
    nama_hewan VARCHAR(100) NOT NULL,
    jenis_hewan VARCHAR(30) NOT NULL,
    ras VARCHAR(100) NOT NULL
);

-- Tabel data penitipan
CREATE TABLE penitipan (
    id_penitipan INT PRIMARY KEY,
    id_hewan INT NOT NULL,
    tanggal_masuk DATE NOT NULL,
    lama_penitipan INT NOT NULL,
    FOREIGN KEY (id_hewan)
        REFERENCES hewan(id_hewan)
);

-- Dummy data hewan
INSERT INTO hewan
    (id_hewan, nama_hewan, jenis_hewan, ras)
VALUES
    (1, 'Milo', 'Kucing', 'Persia'),
    (2, 'Bruno', 'Anjing', 'Bulldog'),
    (3, 'Mochi', 'Kelinci', 'Anggora'),
    (4, 'Coco', 'Hamster', 'Syrian');

-- Dummy data penitipan
INSERT INTO penitipan
    (id_penitipan, id_hewan, tanggal_masuk, lama_penitipan)
VALUES
    (1, 1, '2026-10-10', 3),
    (2, 2, '2026-10-11', 5),
    (3, 3, '2026-10-12', 2),
    (4, 4, '2026-10-13', 4);

