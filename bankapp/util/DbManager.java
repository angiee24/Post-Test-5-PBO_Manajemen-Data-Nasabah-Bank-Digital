package com.mycompany.bankapp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbManager {
    
    // Sesuaikan konfigurasi ini dengan setup XAMPP Anda
    // Pastikan nama database 'bank_app_db' sudah benar
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bank_app_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; 
    private static final String PASS = "";     // Password default XAMPP (kosong)
    
    /**
     * Mendapatkan koneksi ke database MySQL.
     * @return Objek Connection yang siap digunakan.
     * @throws SQLException Jika koneksi gagal atau driver tidak ditemukan.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Memuat driver JDBC MySQL (MySQL Connector/J)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver MySQL tidak ditemukan!");
            // Lemparkan SQLException agar penanganan error seragam
            throw new SQLException("Pastikan library MySQL Connector/J sudah ditambahkan via Maven.", e);
        }
        
        // Membuat dan mengembalikan koneksi
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}