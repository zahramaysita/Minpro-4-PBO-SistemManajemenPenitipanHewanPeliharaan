package model;

import java.util.ArrayList;

public class Service {

    private ArrayList<Hewan> daftarHewan = new ArrayList<>();
    private ArrayList<Penitipan> daftarPenitipan = new ArrayList<>();

    public ArrayList<Hewan> getDaftarHewan() {
        return daftarHewan;
    }

    public ArrayList<Penitipan> getDaftarPenitipan() {
        return daftarPenitipan;
    }
}