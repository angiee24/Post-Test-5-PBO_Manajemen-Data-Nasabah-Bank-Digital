package com.mycompany.bankapp.view;

import com.mycompany.bankapp.repository.NasabahRepositoryImpl;
import com.mycompany.bankapp.service.BankService;
import com.mycompany.bankapp.util.JpaUtil; 
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Inisialisasi BankService dengan Repository
        NasabahRepositoryImpl repository = new NasabahRepositoryImpl();
        BankService service = new BankService(repository);

        while (true) {
            System.out.println("\n--- Program Bank Digital ---");
            System.out.println("1. Tambah Nasabah");
            System.out.println("2. Tampilkan Semua Nasabah");
            System.out.println("3. Ubah Nama Nasabah");
            System.out.println("4. Hapus Nasabah");
            System.out.println("5. Lakukan Transaksi (Setor/Tarik/Transfer)");
            System.out.println("6. Lihat Mutasi Rekening");
            System.out.println("7. Cari Nasabah (Nama/No. Rek)");
            System.out.println("8. Bonus untuk Nasabah Prioritas (Bunga)");
            System.out.println("9. Tampilkan Laporan (JDBC)");
            System.out.println("10. Keluar");
            System.out.print("Pilih menu (1-10): ");

            String input = scanner.nextLine();
            int pilihan;

            try {
                pilihan = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid, masukkan angka!");
                continue;
            }

            switch (pilihan) {
                case 1 -> {
                    System.out.print("Nama Nasabah: ");
                    String nama = scanner.nextLine();
                    double setoranAwal;
                    while (true) {
                        System.out.print("Setoran Awal (min Rp50.000): ");
                        String s = scanner.nextLine().trim();
                        try {
                            setoranAwal = Double.parseDouble(s);
                            break;
                        } catch (NumberFormatException ex) {
                            System.out.println("Input harus ANGKA. Contoh: 65000");
                        }
                    }
                    service.tambahNasabah(nama, setoranAwal);
                }

                case 2 -> service.tampilkanSemuaNasabah();

                case 3 -> {
                    System.out.print("No.Rek: ");
                    String noRek = scanner.nextLine();
                    System.out.print("Nama Baru: ");
                    String namaBaru = scanner.nextLine();
                    service.ubahNamaNasabah(noRek, namaBaru);
                }

                case 4 -> {
                    System.out.print("No.Rek yang akan dihapus: ");
                    String noRek = scanner.nextLine();
                    service.hapusNasabah(noRek);
                }

                case 5 -> handleTransaksi(scanner, service);

                case 6 -> {
                    System.out.print("No.Rek untuk Mutasi: ");
                    String noRek = scanner.nextLine();
                    service.lihatMutasiRekening(noRek);
                }

                case 7 -> {
                    System.out.print("Masukkan Nama/No.Rek: ");
                    String keyword = scanner.nextLine();
                    service.cariNasabah(keyword);
                }
                
                case 8 -> service.beriBungaNasabahPrioritas();
                
                // PANGGILAN SUDAH BENAR, SEKARANG METHOD SUDAH ADA DI BANKSERVICE
                case 9 -> service.tampilkanLaporanJdbcMurni(); 

                case 10 -> { 
                    System.out.println("Program berakhir. Menutup koneksi database (JPA/Hibernate).");
                    JpaUtil.closeFactory(); 
                    scanner.close();
                    return; 
                }

                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void handleTransaksi(Scanner scanner, BankService service) {
        while (true) {
            System.out.println("\n-- Lakukan Transaksi --");
            System.out.println("1. Setor Tunai");
            System.out.println("2. Tarik Tunai");
            System.out.println("3. Transfer Dana");
            System.out.println("4. Kembali ke Menu Utama");
            System.out.print("Pilih transaksi (1-4): ");
            String input = scanner.nextLine();
            int pilihanTransaksi;
            try {
                pilihanTransaksi = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid, masukkan angka!");
                continue;
            }

            switch (pilihanTransaksi) {
                case 1 -> {
                    System.out.print("No.Rek: ");
                    String noRekSetor = scanner.nextLine();
                    double jumlahSetor;
                    while (true) {
                        System.out.print("Jumlah Setor: ");
                        String s = scanner.nextLine().trim();
                        try {
                            jumlahSetor = Double.parseDouble(s);
                            break;
                        } catch (NumberFormatException ex) {
                            System.out.println("Input harus ANGKA. Contoh: 100000");
                        }
                    }
                    service.setorTunai(noRekSetor, jumlahSetor);
                }

                case 2 -> {
                    System.out.print("No.Rek: ");
                    String noRekTarik = scanner.nextLine();
                    double jumlahTarik;
                    while (true) {
                        System.out.print("Jumlah Tarik: ");
                        String s = scanner.nextLine().trim();
                        try {
                            jumlahTarik = Double.parseDouble(s);
                            break;
                        } catch (NumberFormatException ex) {
                            System.out.println("Input harus ANGKA. Contoh: 50000");
                        }
                    }
                    service.tarikTunai(noRekTarik, jumlahTarik);
                }

                case 3 -> {
                    System.out.print("No.Rek Pengirim: ");
                    String noRekPengirim = scanner.nextLine();
                    System.out.print("No.Rek Penerima: ");
                    String noRekPenerima = scanner.nextLine();
                    double jumlahTransfer;
                    while (true) {
                        System.out.print("Jumlah Transfer: ");
                        String s = scanner.nextLine().trim();
                        try {
                            jumlahTransfer = Double.parseDouble(s);
                            break;
                        } catch (NumberFormatException ex) {
                            System.out.println("Input harus ANGKA. Contoh: 250000");
                        }
                    }
                    service.transferDana(noRekPengirim, noRekPenerima, jumlahTransfer);
                }

                case 4 -> { return; }

                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }
}