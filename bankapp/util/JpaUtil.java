package com.mycompany.bankapp.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
    private static final String PERSISTENCE_UNIT_NAME = "BankAppUnit"; 
    private static EntityManagerFactory factory;

    public static void initialize() {
        if (factory == null || !factory.isOpen()) {
            try {
                // Membangun koneksi dari persistence.xml
                factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            } catch (Exception e) {
                System.err.println("Gagal menginisialisasi JPA Unit: " + PERSISTENCE_UNIT_NAME);
                e.printStackTrace();
                throw new RuntimeException("Gagal inisialisasi JPA.", e);
            }
        }
    }

    public static EntityManager getEntityManager() {
        if (factory == null || !factory.isOpen()) {
            initialize();
        }
        // Membuat sesi kerja baru
        return factory.createEntityManager();
    }

    public static void closeFactory() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }
}