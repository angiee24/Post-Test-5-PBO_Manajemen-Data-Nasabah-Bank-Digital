package com.mycompany.bankapp.repository;

import com.mycompany.bankapp.model.Nasabah;
import com.mycompany.bankapp.model.NasabahBiasa;
import com.mycompany.bankapp.model.NasabahPrioritas;
import com.mycompany.bankapp.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NasabahRepositoryImpl implements NasabahRepository {
       
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/bank_app_db?serverTimezone=UTC";
    private static final String JDBC_USER = "root"; 
    private static final String JDBC_PASS = "";     

    // Method findMaxNomorRekening() 
    public long findMaxNomorRekening() {
        EntityManager em = JpaUtil.getEntityManager(); 
        try {
            // JPQL untuk mencari nomor rekening terbesar (MAX(String) 
            String maxNoRekString = em.createQuery(
                "SELECT MAX(n.nomorRekening) FROM Nasabah n", String.class)
                .getSingleResult();
            
            if (maxNoRekString == null) {
                return 2025000; 
            }
            
            return Long.parseLong(maxNoRekString);
            
        } catch (Exception e) {
            // Jika ada masalah (misal: DB kosong atau data error), kembalikan nilai aman
            return 2025000; 
        } finally {
            em.close();
        }
    }
    
    // ====================================================================
    // Implementasi Method dari Interface NasabahRepository
    // ====================================================================

    @Override
    public Nasabah save(Nasabah nasabah) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            if (nasabah.getId() == null) {
                em.persist(nasabah);
            } else {
                nasabah = em.merge(nasabah);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Gagal menyimpan/update nasabah.", e);
        } finally {
            em.close();
        }
        return nasabah;
    }

    @Override
    public Optional<Nasabah> findByNomorRekening(String nomorRekening) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Nasabah> query = em.createQuery(
                "SELECT n FROM Nasabah n WHERE n.nomorRekening = :noRek", Nasabah.class);
            query.setParameter("noRek", nomorRekening);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Nasabah> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT n FROM Nasabah n", Nasabah.class).getResultList();
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<Nasabah> findByNamaContainingIgnoreCase(String keyword) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Nasabah> query = em.createQuery(
                "SELECT n FROM Nasabah n WHERE LOWER(n.nama) LIKE LOWER(:keyword)", Nasabah.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    @Override
    public void delete(Nasabah nasabah) {
        EntityManager em = JpaUtil.getEntityManager(); 
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            // Dapatkan objek yang terkelola (managed)
            Nasabah managedNasabah = em.find(Nasabah.class, nasabah.getId());
            
            if (managedNasabah != null) {
                em.remove(managedNasabah); // CASCADE di Nasabah.java akan menghapus TransaksiEntry
            }
            
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Gagal menghapus nasabah. (Cek Foreign Key)", e);
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<Nasabah> findAllWithJdbc() {
        List<Nasabah> daftar = new ArrayList<>();
        String SQL = "SELECT JENIS, nomor_rekening, nama, saldo FROM nasabah";
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {
            
            while (rs.next()) {
                String jenis = rs.getString("JENIS");
                String noRek = rs.getString("nomor_rekening");
                String nama = rs.getString("nama");
                double saldo = rs.getDouble("saldo");
                
                Nasabah n;
                if ("PRIORITAS".equals(jenis)) {
                    n = new NasabahPrioritas(noRek, nama, saldo);
                } else {
                    n = new NasabahBiasa(noRek, nama, saldo);
                }
                daftar.add(n);
            }
        } catch (Exception e) {
            System.err.println("Gagal membaca data dengan JDBC Murni.");
            e.printStackTrace();
        }
        return daftar;
    }
}