package com.mycompany.bankapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaksi_entry")
public class TransaksiEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "nasabah_id", nullable = false)
    private Nasabah nasabah; 
    
    @Column(name = "jenis_transaksi", nullable = false)
    private String jenisTransaksi; 
    
    @Column(name = "tipe_pergerakan", nullable = false)
    private String tipePergerakan; // "KREDIT" atau "DEBIT"

    @Column(name = "waktu", nullable = false)
    private LocalDateTime waktu;

    @Column(name = "jumlah", nullable = false)
    private double jumlah;

    @Column(name = "keterangan", length = 255)
    private String keterangan;

    // WAJIB: Constructor tanpa argumen untuk JPA/Hibernate
    public TransaksiEntry() {} 

    // Constructor FINAL (dengan jenisTransaksi)
    public TransaksiEntry(Nasabah nasabah, String jenisTransaksi, String tipePergerakan, double jumlah, String keterangan) {
        this.nasabah = nasabah;
        this.jenisTransaksi = jenisTransaksi;
        this.tipePergerakan = tipePergerakan;
        this.jumlah = jumlah;
        this.keterangan = keterangan;
        this.waktu = LocalDateTime.now(); 
    }

    // ======= GETTER/SETTER =======
    public Long getId() { return id; }
    public Nasabah getNasabah() { return nasabah; }
    public String getJenisTransaksi() { return jenisTransaksi; }
    public String getTipePergerakan() { return tipePergerakan; }
    public LocalDateTime getWaktu() { return waktu; }
    public double getJumlah() { return jumlah; }
    public String getKeterangan() { return keterangan; }
}