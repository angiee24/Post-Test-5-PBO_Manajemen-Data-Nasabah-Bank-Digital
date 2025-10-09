package com.mycompany.bankapp.service;

import com.mycompany.bankapp.model.*;
import com.mycompany.bankapp.repository.NasabahRepository;
import com.mycompany.bankapp.repository.NasabahRepositoryImpl;
import com.mycompany.bankapp.util.JpaUtil; 
import jakarta.persistence.EntityManager; 
import jakarta.persistence.TypedQuery; 
import java.util.List;
import java.util.Optional;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

public class BankService {

    private final NasabahRepository nasabahRepository;
    private final AtomicLong noRekCounter; 

    private static final double SALDO_MINIMUM = 50_000.0;
    private static final double MIN_SETOR_AWAL = 50_000.0;
    private static final double MIN_PRIORITAS = 10_000_000.0;
    private static final double ADMIN_BULANAN_BIASA = 5_000.0;
    private static final double ADMIN_BULANAN_PRIORITAS = 3_000.0;
    private static final double RATE_BUNGA_PRIORITAS = 0.02;

    private static final DecimalFormat DF = new DecimalFormat("#,###");
    private static String rp(double v) { return "Rp" + DF.format(Math.round(v)); } 

    private final Map<String, YearMonth> lastAdminFeeMonth = new HashMap<>(); 

    // Constructor: Melakukan inisialisasi counter dari database
    public BankService(NasabahRepository repository) {
        this.nasabahRepository = repository;
        
        long maxNomorRekening = ((NasabahRepositoryImpl)repository).findMaxNomorRekening();
        this.noRekCounter = new AtomicLong(maxNomorRekening); 
    }

    public void tambahNasabah(String nama, double setoranAwal) {
        if (setoranAwal < MIN_SETOR_AWAL) {
            System.out.println("Gagal: setoran awal minimal " + rp(MIN_SETOR_AWAL) + ".");
            return;
        }
        
        String noRek = String.valueOf(noRekCounter.incrementAndGet()); 

        Nasabah n = (setoranAwal >= MIN_PRIORITAS)
                ? new NasabahPrioritas(noRek, nama, setoranAwal)
                : new NasabahBiasa(noRek, nama, setoranAwal);

        // FIX TRANSIENT ENTITY: HAPUS panggilan n.setor() untuk setoran awal.
        // Biarkan Nasabah disimpan mentah. Mutasi akan terjadi saat transaksi pertama.
        // Saldo awal sudah diatur di constructor Nasabah.
        // Logika mutasi setoran awal sekarang HARUS DIHAPUS agar tidak ada HHH000437.

        nasabahRepository.save(n);
        System.out.println("Nasabah ditambahkan. No Rek: " + noRek + " | Saldo: " + rp(n.getSaldo()));
    }

    public void tampilkanSemuaNasabah() {
        List<Nasabah> daftar = nasabahRepository.findAll();
        if (daftar.isEmpty()) {
            System.out.println("Belum ada nasabah di database.");
            return;
        }
        for (Nasabah n : daftar) {
            n.tampilkanInfo();
        }
    }

    private Optional<Nasabah> cariByNoRek(String noRek) {
        return nasabahRepository.findByNomorRekening(noRek);
    }

    public Nasabah cariNasabah(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            System.out.println("Keyword pencarian kosong.");
            return null;
        }
        
        Optional<Nasabah> byRek = cariByNoRek(keyword.trim());
        if (byRek.isPresent()) {
            System.out.println("Ditemukan (berdasarkan No.Rek):");
            byRek.get().tampilkanInfo();
            return byRek.get();
        }
        
        String kw = keyword.trim().toLowerCase();
        List<Nasabah> hasil = nasabahRepository.findByNamaContainingIgnoreCase(kw);
        
        if (hasil.isEmpty()) {
            System.out.println("Tidak ada nasabah dengan nama/no.rek: " + keyword);
            return null;
        }
        System.out.println("Hasil pencarian nama mengandung \"" + keyword + "\":");
        for (Nasabah n : hasil) n.tampilkanInfo();
        return hasil.get(0);
    }

    public void ubahNamaNasabah(String noRek, String namaBaru) {
        Optional<Nasabah> optN = cariByNoRek(noRek);
        if (optN.isEmpty()) { System.out.println("Rekening tidak ditemukan."); return; }
        Nasabah n = optN.get();
        
        n.setNama(namaBaru);
        nasabahRepository.save(n);
        System.out.println("Nama berhasil diubah.");
    }

    public void hapusNasabah(String noRek) {
        Optional<Nasabah> optN = cariByNoRek(noRek);
        if (optN.isEmpty()) { System.out.println("Rekening tidak ditemukan."); return; }
        
        nasabahRepository.delete(optN.get()); 
        lastAdminFeeMonth.remove(noRek);
        System.out.println("Nasabah dengan rekening " + noRek + " dihapus.");
    }

    public void setorTunai(String noRek, double jumlah) {
        Optional<Nasabah> optN = cariByNoRek(noRek);
        if (optN.isEmpty()) { System.out.println("Rekening tidak ditemukan."); return; }
        Nasabah n = optN.get();
        
        ((Transaksi) n).setor(jumlah);
        nasabahRepository.save(n);
        System.out.println("Setor sukses. Saldo: " + rp(n.getSaldo()));
    }

    public void tarikTunai(String noRek, double jumlah) {
        Optional<Nasabah> optN = cariByNoRek(noRek);
        if (optN.isEmpty()) { System.out.println("Rekening tidak ditemukan."); return; }
        Nasabah n = optN.get();
        
        if (!((Transaksi) n).tarik(jumlah)) return;
        
        nasabahRepository.save(n);
        
        if (n.getSaldo() < SALDO_MINIMUM) {
            System.out.println("Peringatan: saldo di bawah minimum (" + rp(SALDO_MINIMUM) + ").");
        }
        System.out.println("Tarik sukses. Saldo: " + rp(n.getSaldo()));
    }

    public void transferDana(String noRekPengirim, String noRekPenerima, double jumlah) {
        Optional<Nasabah> optPengirim = cariByNoRek(noRekPengirim);
        Optional<Nasabah> optPenerima = cariByNoRek(noRekPenerima);
        
        if (optPengirim.isEmpty() || optPenerima.isEmpty()) {
            System.out.println("Rekening pengirim/penerima tidak ditemukan.");
            return;
        }
        Nasabah pengirim = optPengirim.get();
        Nasabah penerima = optPenerima.get();
        
        if (noRekPengirim.equals(noRekPenerima)) {
            System.out.println("Tidak bisa transfer ke rekening sendiri.");
            return;
        }
        
        boolean ok = ((Transaksi) pengirim).transfer(penerima, jumlah,
                "Transfer ke " + noRekPenerima);
                
        if (ok) {
            nasabahRepository.save(pengirim);
            nasabahRepository.save(penerima);
            
            System.out.println("Transfer sukses. Saldo pengirim: " + rp(pengirim.getSaldo()));
        }
    }

    public void lihatMutasiRekening(String noRek) {
        Optional<Nasabah> optN = cariByNoRek(noRek);
        if (optN.isEmpty()) { 
            System.out.println("Rekening tidak ditemukan."); 
            return; 
        }
        Nasabah n = optN.get();
        
        EntityManager em = JpaUtil.getEntityManager();
        
        try {
            TypedQuery<TransaksiEntry> query = em.createQuery(
                "SELECT t FROM TransaksiEntry t WHERE t.nasabah.id = :nasabahId ORDER BY t.waktu ASC", 
                TransaksiEntry.class);
            query.setParameter("nasabahId", n.getId());
            List<TransaksiEntry> mutasiList = query.getResultList();

            System.out.println("\n== Mutasi Rekening " + noRek + " (" + n.getNama() + ") ==");
            
            if (mutasiList.isEmpty()) {
                System.out.println("(kosong)");
                return;
            }

            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            
            for (TransaksiEntry t : mutasiList) {
                String waktu = t.getWaktu().format(fmt);
                String tanda = t.getTipePergerakan().equals("KREDIT") ? "+" : "-"; 
                String jumlahFormatted = rp(t.getJumlah()); 
                
                System.out.printf("- %s | %s | %s%s | %s\n", 
                                   waktu, t.getJenisTransaksi(), tanda, jumlahFormatted, t.getKeterangan());
            }
            
        } catch (Exception e) {
            System.err.println("Gagal membaca mutasi dari DB.");
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    
    private void prosesBiayaAdminBulananOtomatis(String noRek) {
        // Logika ini kompleks dengan ORM, diabaikan
    }

    public void beriBungaNasabahPrioritas() {
        int hitung = 0;
        List<Nasabah> daftar = nasabahRepository.findAll();
        
        for (Nasabah n : daftar) {
            if (n instanceof NasabahPrioritas) {
                double bunga = n.getSaldo() * RATE_BUNGA_PRIORITAS;
                n.setSaldo(n.getSaldo() + bunga);
                
                // Catat bunga sebagai transaksi
                ((NasabahPrioritas)n).catat("BUNGA", "KREDIT", bunga, "Bunga tabungan Prioritas");
                
                nasabahRepository.save(n);
                
                System.out.println("Bunga diberikan ke " + n.getNama() + " "
                        + rp(bunga) + ". Saldo baru: " + rp(n.getSaldo()));
                hitung++;
            }
        }
        if (hitung == 0) {
            System.out.println("Tidak ada Nasabah Prioritas yang diproses.");
        } else {
            System.out.println("Total Nasabah Prioritas diproses: " + hitung);
        }
    }
    
    public void tampilkanLaporanJdbcMurni() {
        System.out.println("\n== Laporan Semua Nasabah (JDBC Murni) ==");
        List<Nasabah> daftar = nasabahRepository.findAllWithJdbc();
        if (daftar.isEmpty()) {
            System.out.println("Belum ada nasabah di database.");
            return;
        }
        
        // Output header tabel
        System.out.printf("%-15s | %-30s | %-15s | %-10s\n", 
                          "No. Rekening", "Nama", "Saldo", "Jenis");
        System.out.println("-----------------------------------------------------------------");
        
        for (Nasabah n : daftar) {
            System.out.printf("%-15s | %-30s | %-15s | %-10s\n", 
                              n.getNomorRekening(), n.getNama(), rp(n.getSaldo()), n.getJenisNasabah());
        }
    }
}