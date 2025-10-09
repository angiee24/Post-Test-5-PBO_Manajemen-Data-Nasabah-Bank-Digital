package com.mycompany.bankapp.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BIASA")
public class NasabahBiasa extends Nasabah {
    public NasabahBiasa() {} // WAJIB untuk JPA
    public NasabahBiasa(String nomorRekening, String nama, double saldoAwal) {
        super(nomorRekening, nama, saldoAwal);
    }

    @Override
    public String getJenisNasabah() { return "Nasabah Biasa"; }

    @Override
    protected double biayaTransfer(double jumlah) {
        return (jumlah <= 1_000_000) ? 2500.0 : 5000.0;
    }
    
    // Tampilkan info tanpa System.out.print di subclass, biarkan di BankService.
    @Override
    public void tampilkanInfo() {
        System.out.println("[Nasabah Biasa] Rek: " + getNomorRekening() +
                " | Nama: " + getNama() + " | Saldo: " + getSaldo());
    }
}