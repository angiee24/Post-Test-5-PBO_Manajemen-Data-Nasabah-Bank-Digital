package com.mycompany.bankapp.util;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.Locale;

public class ReportGenerator {
    
    // Formatter untuk menampilkan saldo dalam format Rupiah
    private static final DecimalFormat DF = (DecimalFormat) DecimalFormat.getInstance(new Locale("id", "ID"));
    static {
        DF.applyPattern("#,###");
    }
    private static String rp(double v) { return "Rp" + DF.format(Math.round(v)); }

    public void tampilkanSemuaNasabahJDBC() {
        System.out.println("\n========================================================");
        System.out.println("== Laporan Nasabah (Menggunakan JDBC Statement Murni) ==");
        System.out.println("========================================================");
        
        // Query SQL yang mengambil semua data yang dibutuhkan
        final String SQL = "SELECT nomor_rekening, nama, saldo, JENIS FROM nasabah ORDER BY nomor_rekening";

        // Menggunakan try-with-resources untuk memastikan Connection, Statement, dan ResultSet tertutup
        try (Connection conn = DbManager.getConnection(); // 1. Dapatkan koneksi
             Statement stmt = conn.createStatement();    // 2. Buat Statement
             ResultSet rs = stmt.executeQuery(SQL)) {    // 3. Jalankan Query
            
            System.out.printf("%-15s | %-30s | %-15s | %-10s\n", 
                              "No. Rekening", "Nama", "Saldo", "Jenis");
            System.out.println("-----------------------------------------------------------------");
            
            int count = 0;
            while (rs.next()) { // Iterasi melalui hasil
                String noRek = rs.getString("nomor_rekening");
                String nama = rs.getString("nama");
                double saldo = rs.getDouble("saldo");
                String jenis = rs.getString("JENIS");
                
                System.out.printf("%-15s | %-30s | %-15s | %-10s\n", 
                                  noRek, nama, rp(saldo), jenis);
                count++;
            }
            if (count == 0) {
                System.out.println("Tabel nasabah kosong di database.");
            }

        } catch (SQLException e) {
            System.err.println("Gagal menampilkan laporan JDBC. Cek koneksi XAMPP dan driver.");
            e.printStackTrace();
        }
    }
}