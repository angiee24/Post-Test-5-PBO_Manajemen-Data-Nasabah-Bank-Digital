package com.mycompany.bankapp.repository;

import com.mycompany.bankapp.model.Nasabah;
import java.util.List;
import java.util.Optional;

public interface NasabahRepository {
    
    Nasabah save(Nasabah nasabah);
    
    // CRUD
    void delete(Nasabah nasabah);
    List<Nasabah> findAll();
    
    // Queries Khusus
    Optional<Nasabah> findByNomorRekening(String noRek);
    List<Nasabah> findByNamaContainingIgnoreCase(String keyword);
    
    // Method untuk Menu 9/10 (JDBC)
    List<Nasabah> findAllWithJdbc(); 
}