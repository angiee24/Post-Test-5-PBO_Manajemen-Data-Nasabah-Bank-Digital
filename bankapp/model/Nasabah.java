package com.mycompany.bankapp.model;

import com.mycompany.bankapp.util.JpaUtil; 
import jakarta.persistence.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "nasabah")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "JENIS", discriminatorType = DiscriminatorType.STRING)
public abstract class Nasabah implements Transaksi {
    
    // PRIMARY KEY
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    // MAPPING Menyelaraskan nama Java (nomorRekening) dengan DB (nomor_rekening)
    @Column(name = "nomor_rekening", unique = true, nullable = false)
    private String nomorRekening;

    private String nama;
    protected double saldo;
    
    // Relasi One-to-Many dengan CASCADE ALL 
    @OneToMany(mappedBy = "nasabah", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransaksiEntry> mutasiList = new ArrayList<>(); 
    
    public Nasabah() {} 

    public Nasabah(String nomorRekening, String nama, double saldoAwal) {
        this.nomorRekening = nomorRekening;
        this.nama = nama;
        this.saldo = saldoAwal;
        // Mutasi setoran awal akan dicatat oleh BankService.tambahNasabah
    }

    // ====== ABSTRAKSI ======
    public abstract String getJenisNasabah();
    protected abstract double biayaTransfer(double jumlah);

    // ====== GETTER/SETTER 
    public Long getId() { return id; }
    public String getNomorRekening() { return nomorRekening; }
    public void setNomorRekening(String nomorRekening) { this.nomorRekening = nomorRekening; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    
    // METHOD catat() 
    public void catat(String jenisTransaksi, String tipePergerakan, double jumlah, String keterangan) {
        EntityManager em = JpaUtil.getEntityManager(); 
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            TransaksiEntry entry = new TransaksiEntry(this, jenisTransaksi, tipePergerakan, jumlah, keterangan);
            em.persist(entry);
            tr.commit();
        } catch (Exception e) {
            if(tr.isActive()) tr.rollback();
            // System.err.println("Gagal mencatat mutasi ke DB."); // Peringatan ini dihilangkan
        } finally {
            em.close();
        }
    }

    // ====== Implementasi Transaksi 
    @Override
    public void setor(double jumlah) { setor(jumlah, "Setoran tunai"); }

    @Override
    public void setor(double jumlah, String keterangan) {
        if (jumlah <= 0) return;
        saldo += jumlah;
        // Panggil catat() yang sekarang PUBLIC
        catat("SETOR", "KREDIT", jumlah, keterangan); 
    }

    @Override
    public boolean tarik(double jumlah) { return tarik(jumlah, "Tarik tunai"); }

    @Override
    public boolean tarik(double jumlah, String keterangan) {
        if (jumlah <= 0 || jumlah > saldo) return false;
        saldo -= jumlah;
        catat("TARIK", "DEBIT", jumlah, keterangan); 
        return true;
    }

    @Override
    public boolean transfer(Nasabah tujuan, double jumlah) {
        return transfer(tujuan, jumlah, "Transfer ke " + (tujuan != null ? tujuan.getNomorRekening() : "-"));
    }

    @Override
    public boolean transfer(Nasabah tujuan, double jumlah, String keterangan) {
        if (tujuan == null || jumlah <= 0) return false;

        double fee = biayaTransfer(jumlah);
        double total = jumlah + fee;
        if (total > saldo) return false; 
        
        // PENGIRIM
        this.saldo -= total;
        catat("TRANSFER_KELUAR", "DEBIT", total, keterangan + " (Biaya: " + fee + ")");
        
        // PENERIMA
        tujuan.saldo += jumlah;
        tujuan.catat("TRANSFER_MASUK", "KREDIT", jumlah, keterangan.replace("ke", "dari"));
        
        return true;
    }

    public void tampilkanInfo() {
        System.out.println("[" + getJenisNasabah() + "] Rek: " + nomorRekening +
                " | Nama: " + nama + " | Saldo: " + saldo); 
    }
}