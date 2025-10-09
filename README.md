# Post Test 4-PBO Manajemen-Data-Nasabah-Bank-Digital

# Manajemen Data Nasabah Bank Digital

Angela Caroline Budiman (2409116008)

Sistem Informasi A'2024


# Deskripsi
Program ini merupakan sebuah program sederhana CRUD dengan tema Manajemen Data Nasabah Bank Digital. Melalui menu konsol yang interaktif, pengguna dapat melakukan operasi data nasabah secara lengkap, mulai dari menambah (Create), melihat (Read), mengubah (Update), hingga menghapus (Delete) data. Selain itu, program ini juga dilengkapi dengan fitur transaksi dasar seperti setor tunai, tarik tunai, dan transfer dana antar rekening, sehingga dapat menjadi simulasi sederhana dalam pengelolaan dan manajemen data nasabah bank secara digital.

# Alur Program
Program ini berjalan dengan menampilkan sembilan menu utama kepada pengguna. Opsi 1 sampai 4 digunakan untuk mengelola data dasar nasabah (tambah, lihat, ubah, hapus). Menu 5 membuka sub-menu transaksi finansial, menu 6 dan 7 digunakan untuk melihat riwayat rekening dan mencari nasabah, menu 8 dipakai untuk memberikan bonus nasabah prioritas, menu 9 dipakai untuk menampilakan database langsung dari MYSQL, dan menu 10 dipakai untuk keluar.

Setiap input yang bukan merupakan angka 1–9 akan dianggap tidak valid, lalu program secara otomatis mengarahkan kembali pengguna ke menu utama.

### Penjelasan Alur per Fitur
1. Tambah Nasabah (Create)
Pengguna mendaftarkan nasabah baru dengan nama dan setoran awal. Sistem akan membuatkan nomor rekening unik secara otomatis.
2. Tampilkan Nasabah (Read)
Menampilkan daftar lengkap seluruh data nasabah yang tersimpan.
3. Ubah Nama Nasabah (Update)
Pengguna dapat mengubah nama nasabah yang sudah ada dengan mencarinya berdasarkan nomor rekening.
4. Hapus Nasabah (Delete)
Menghapus data nasabah dari sistem berdasarkan nomor rekening. Program akan memberi notifikasi jika rekening tidak ditemukan.
5. Lakukan Transaksi
Membuka sub-menu untuk melakukan transaksi finansial, yang meliputi:
- Setor Tunai: Menambahkan dana ke rekening.
- Tarik Tunai: Menarik dana dari rekening, dengan validasi saldo minimum.
- Transfer Dana: Memindahkan dana antar rekening, dengan validasi saldo dan biaya administrasi.
- Kembali Ke Menu Utama: Opsi untuk keluar dari sub-menu dan kembali ke menu utama.
6. Lihat Mutasi Rekening
Menampilkan seluruh riwayat transaksi (mutasi) dari sebuah rekening berdasarkan nomornya.
7. Cari Nasabah (Search)
Mencari data nasabah secara fleksibel menggunakan input nama atau nomor rekening.
8. Bonus Untuk Nasabah Prioritas
Menambahkan bonus 2% dari saldo hanya untuk nasabah prioritas, lalu menampilkan besarnya bonus dan saldo terbaru sekaligus mencatatnya di mutasi rekening.
9. Tampilkan Laporan (JDBC)
Menu ini dirancang untuk membaca data nasabah secara langsung dari database (MySQL) menggunakan driver JDBC Murni (java.sql.Statement dan ResultSet). Fungsinya adalah memverifikasi koneksi low-level ke database dan menampilkan daftar lengkap seluruh data nasabah yang tersimpan.
10. Keluar (Exit)
Menghentikan dan keluar dari program.

## Struktur Project

<img width="663" height="840" alt="image" src="https://github.com/user-attachments/assets/0590ff3b-c044-48bd-ab73-f9f91347efed" />

1. com.mycompany.bankapp.model (Layer Model - Entitas JPA)
Layer ini sekarang bertanggung jawab atas data dan mapping ke database.

- Nasabah.java: Ini adalah Abstract Entity (@Entity, @Inheritance). Atribut mutasiRekening (lama) telah diganti dengan relasi @OneToMany ke TransaksiEntry, dilengkapi CascadeType.ALL untuk menghapus mutasi saat nasabah dihapus.
- NasabahBiasa.java & NasabahPrioritas.java: Ini adalah Subclass Entitas yang menggunakan @DiscriminatorValue untuk memetakan diri ke tabel nasabah (Single Table Inheritance).
- Transaksi.java: Interface (Kontrak) yang mendefinisikan method transaksi.
- TransaksiEntry.java: Entitas Baru (@Entity, @ManyToOne) yang bertindak sebagai log mutasi persisten.

2. com.mycompany.bankapp.repository (Layer Repository - Data Access Layer)
Layer ini adalah jembatan ORM yang baru, menyediakan akses data terstruktur.

- NasabahRepository.java: Ini adalah Interface Repository yang mendefinisikan semua method CRUD berbasis objek, termasuk method findAllWithJdbc() untuk laporan murni.
- NasabahRepositoryImpl.java: Ini adalah implementasi yang menggunakan EntityManager untuk operasi ORM (Save, Find, Delete) dan code JDBC Murni untuk method pelaporan.

3. com.mycompany.bankapp.service (Layer Service/Controller)
Layer ini tetap menjadi pusat logika bisnis, tetapi kini beroperasi di atas Repository.

- Berisi Class: BankService.java.
- Logika Bisnis: Semua logika CRUD sekarang dipanggil melalui NasabahRepository. Constructor-nya telah diperbaiki untuk membaca nomor rekening terbesar dari database (findMaxNomorRekening()) saat startup, menyelesaikan masalah duplikasi counter yang persisten.
- Fungsi Utama: Bertindak sebagai controller yang menghubungkan Model (melalui Repository) dengan Tampilan (View).

4. com.mycompany.bankapp.util (Layer Utilitas Koneksi)
Ini adalah folder baru yang menampung helper class untuk koneksi database.

- DbManager.java: Helper koneksi JDBC Murni yang menyediakan method getConnection() langsung ke MySQL.
- JpaUtil.java: Helper JPA/Hibernate yang menginisialisasi dan mengelola EntityManagerFactory (membaca persistence.xml).
- ReportGenerator.java: Helper untuk menampilkan laporan dengan JDBC Murni (menggunakan DbManager).

5. com.mycompany.bankapp.view (Layer View)
Berisi Class: Main.java.
- Perubahan Fungsi: menyajikan antarmuka konsol dengan sepuluh menu utama yang berfungsi sebagai pusat kendali program, di mana Menu 1 hingga 4 didedikasikan untuk operasi manajemen data dasar nasabah (tambah, lihat, ubah, hapus). Menu 5 membuka sub-menu untuk seluruh transaksi finansial yang setiap aksinya memicu pencatatan log mutasi ke database, diikuti Menu 6 (Lihat Mutasi) dan Menu 7 (Cari Nasabah) sebagai utility query. Menu 8 adalah logic bonus bunga, dan implementasi Menu 9 (Tampilkan Laporan JDBC) berfungsi memvalidasi koneksi low-level ke database dengan menampilkan data melalui JDBC Murni; sementara itu, pada opsi Keluar (Menu 10), program secara eksplisit memastikan pemanggilan JpaUtil.closeFactory() untuk memutuskan koneksi Hibernate secara aman sebelum menutup aplikasi.

## Letak Penerapan ORM

### 1. Nasabah.java

<img width="908" height="91" alt="image" src="https://github.com/user-attachments/assets/a5056091-52ce-40a4-8fe8-13887d9a45b7" />

<img width="662" height="42" alt="image" src="https://github.com/user-attachments/assets/bda4de4d-3efd-4c9e-968b-5eaa09fefae8" />

<img width="863" height="19" alt="image" src="https://github.com/user-attachments/assets/32772747-5906-4dde-9f9a-d7a5b1bfac9c" />

<img width="963" height="25" alt="image" src="https://github.com/user-attachments/assets/50e9d1be-f389-40c1-8d96-4a902b596437" />

Sebagai : Entity dan Mappping Utama  
- Didefinisikan dengan @Entity, @Table, dan @Inheritance. Menggunakan @OneToMany untuk relasi ke TransaksiEntry, dengan cascade = CascadeType.ALL untuk memastikan mutasi ikut terhapus saat Nasabah dihapus.

### 2. NasabahBiasa.java & NasabahPrioritas.java

<img width="413" height="26" alt="image" src="https://github.com/user-attachments/assets/31a8dd5d-69b8-405a-9ebd-763504702cb6" />

<img width="439" height="21" alt="image" src="https://github.com/user-attachments/assets/567313ce-c65c-4629-9da2-a999cf583de7" />

Sebagai : Subclass Entitas  
- Menggunakan @DiscriminatorValue untuk memetakan subclass ke kolom JENIS pada tabel tunggal (nasabah), mengikuti strategi InheritanceType.SINGLE_TABLE.

### 3. TransaksiEntry.java

<img width="210" height="32" alt="image" src="https://github.com/user-attachments/assets/0494b674-4645-44e4-be4b-e93cc1d8a580" />


<img width="652" height="49" alt="image" src="https://github.com/user-attachments/assets/dac2fea3-d5fa-4115-b4b0-39d2ffd4ca7c" />

Sebagai :  Entity Relasi 
- enggunakan @Entity dan @ManyToOne dengan @JoinColumn(name = "nasabah_id"), mendefinisikan kolom, tipe data, dan relasi Foreign Key yang dikelola oleh JPA.

### 4. NasabahRepositoryImpl.java

<img width="547" height="72" alt="image" src="https://github.com/user-attachments/assets/1a003cf3-1030-47be-9f4b-eb5b90cd15cd" />

<img width="894" height="34" alt="image" src="https://github.com/user-attachments/assets/32eea938-d0b3-4821-9361-c805567920ce" />

<img width="1086" height="14" alt="image" src="https://github.com/user-attachments/assets/f4235cea-f748-47c6-8768-8390badaaf8f" />

<img width="659" height="33" alt="image" src="https://github.com/user-attachments/assets/0df8ce56-b4ec-462c-9451-296794e636f9" />

Sebagai : Implementasi Repository (Inti JPA)
- Semua method CRUD standar (save, delete, findAll, findByNomorRekening, findByNamaContainingIgnoreCase) diimplementasikan menggunakan EntityManager dari JpaUtil untuk berinteraksi dengan database melalui objek Java, bukan SQL.

### 5. JpaUtil.java

<img width="805" height="25" alt="image" src="https://github.com/user-attachments/assets/b658efb0-3ed1-4aff-ae41-4ef2a1e785a4" />

<img width="960" height="32" alt="image" src="https://github.com/user-attachments/assets/cf5f9ef1-eee1-48b4-9e33-a81087359d36" />

<img width="539" height="31" alt="image" src="https://github.com/user-attachments/assets/c40a26eb-ed06-4601-aba1-7fa4fd8031f4" />

Sebagai : Inisialisasi JPA/Hibernate
- Menyediakan method statis getEntityManager() yang bertanggung jawab untuk membuat dan mengelola EntityManagerFactory dari unit persistensi yang didefinisikan di persistence.xml.

### BankService.java

<img width="1163" height="39" alt="image" src="https://github.com/user-attachments/assets/37abdff8-9943-4313-b0a8-1883f05543d0" />

<img width="727" height="31" alt="image" src="https://github.com/user-attachments/assets/1aec7127-741c-4c4b-a71a-ba13103efe3d" />

<img width="619" height="34" alt="image" src="https://github.com/user-attachments/assets/d3af4653-1042-4422-9256-219d6c364f6d" />

Sebagai : Transaction & Query JPQL
- Method lihatMutasiRekening() menggunakan JPQL (Java Persistence Query Language): SELECT t FROM TransaksiEntry t WHERE t.nasabah.id = :nasabahId... untuk mengambil daftar mutasi berdasarkan ID Nasabah.

## Letak Penerapan JDBC

### 1. DbManager.java

<img width="747" height="33" alt="image" src="https://github.com/user-attachments/assets/0f579374-8320-4055-91c4-1cb483b03a54" />

Membuat objek Connection (koneksi fisik) yang merupakan dasar dari semua operasi JDBC.

### 2. NasabahRepositoryImpl.java

<img width="1027" height="32" alt="image" src="https://github.com/user-attachments/assets/cf52eaff-db7e-45aa-94c6-6c0d11545015" />

Blok code yang menggunakan API java.sql.* untuk menjalankan raw query SQL (SELECT...).

<img width="708" height="23" alt="image" src="https://github.com/user-attachments/assets/aeecd986-7ebb-4b41-978d-d99c2c424df9" />

Memproses hasil query SQL secara manual dari ResultSet.

### 3. BankService.java

<img width="771" height="39" alt="image" src="https://github.com/user-attachments/assets/535fd84f-0ee1-445e-b6a7-07aee482afe6" />

Memanggil implementasi JDBC murni dari Repository untuk mendapatkan data yang kemudian ditampilkan.

### 4. Main.java

<img width="831" height="54" alt="image" src="https://github.com/user-attachments/assets/2f83c213-3609-43c2-bc78-08f1378e74cf" />

Titik masuk yang diakses pengguna untuk menjalankan fungsionalitas pelaporan JDBC murni.

### 5. JpaUtil.java

<img width="978" height="30" alt="image" src="https://github.com/user-attachments/assets/f8cdf19e-4b96-4e34-aded-b184c83a9dbb" />

Meskipun ini JPA, ia menginisialisasi provider yang di dalamnya memuat driver JDBC yang sama untuk koneksi ORM.

# Konfigurasi Sistem (Persistence & Build)

### persistence.xml

Pusat Kendali JPA/JDBC. Mendefinisikan persistence-unit (BankAppUnit), mendaftarkan semua class Entitas, dan menyediakan properties koneksi JDBC (jakarta.persistence.jdbc.url) yang digunakan oleh Hibernate.

### pom.xml

Manajemen Dependency. Deklarasi hibernate-core, jakarta.persistence-api, dan mysql-connector-java yang menyediakan semua library ORM dan JDBC yang dibutuhkan aplikasi.

# Database

### Tables

<img width="455" height="254" alt="image" src="https://github.com/user-attachments/assets/bce0c2f0-b696-422d-b298-bba99299de0a" />

Gambar yang menunjukkan hasil query show tables dari XAMPP Shell, struktur database ini adalah bukti kunci bahwa migrasi ORM (JPA/Hibernate) Anda telah berhasil diselesaikan. Struktur tersebut mengonfirmasi adanya dua tabel utama yang saling terkait: nasabah (berfungsi sebagai tabel induk/parent yang menyimpan data dasar akun dan mendukung inheritance untuk NasabahBiasa/NasabahPrioritas) dan transaksi_entry (berfungsi sebagai tabel anak/child yang mencatat seluruh log mutasi keuangan). Keberadaan kedua tabel ini memvalidasi bahwa telah berhasil memindahkan semua logic dan data storage dari ArrayList di memori ke Persistence Layer yang terstruktur, aman, dan siap untuk operasi CRUD yang diotomatisasi.
### nasabah

<img width="933" height="299" alt="image" src="https://github.com/user-attachments/assets/6bfa7026-2628-4d99-964e-a0148635968a" />

Tabel nasabah ini adalah representasi utama dari entitas Nasabah di database Anda, mengonfirmasi keberhasilan mapping JPA/ORM. Struktur ini mendukung Single Table Inheritance yang mana kolom JENIS (VARCHAR(31), NOT NULL) berfungsi sebagai discriminator untuk membedakan antara NasabahBiasa dan NasabahPrioritas. Kolom id (BIGINT, PRIMARY KEY) adalah kunci utama yang di-generasi otomatis oleh database, sementara nomor_rekening (VARCHAR(15), UNIQUE) menjamin bahwa setiap akun nasabah memiliki identitas yang unik.

<img width="1116" height="714" alt="image" src="https://github.com/user-attachments/assets/21cd0766-4ac6-452c-9da3-0389e28a6a65" />

 Berdasarkan gambar yang menunjukkan hasil query SELECT * FROM nasabah dari database MariaDB/MySQL, data ini adalah bukti visual langsung yang memvalidasi keberhasilan seluruh proses migrasi ORM dan logic counter Anda. Tabel ini mengonfirmasi bahwa data awal (baris 1-20) dan data baru yang ditambahkan oleh aplikasi Java (angie nji di baris 43, oline di baris 44, dan lin di baris 45) semuanya berhasil tersimpan dan dimuat oleh Hibernate, sementara kolom JENIS memvalidasi penggunaan Polimorfisme Data (Inheritance Mapping) di mana PRIORITAS dan BIASA disimpan dalam satu tabel tunggal. Kolom saldo menunjukkan nilai uang yang akurat setelah transaksi (termasuk update bunga yang terakumulasi), dan counter otomatis telah melewati nomor 2025023 untuk menghindari error duplikasi yang persisten, menandakan startup logic aplikasi kini stabil dan berfungsi.

### transaksi_entry

<img width="956" height="314" alt="image" src="https://github.com/user-attachments/assets/bb05d670-6c18-4f05-bd84-78a1b8d33c9a" />

Tabel transaksi_entry adalah immutable log (jurnal) yang merekam seluruh riwayat mutasi keuangan aplikasi, mengonfirmasi keberhasilan refactoring logic mutasi ke persistence layer. Struktur ini secara eksplisit mendukung kebutuhan akuntansi dengan kolom id sebagai Primary Key dan nasabah_id (BIGINT, MUL) sebagai Foreign Key yang mengikat setiap transaksi secara wajib ke entitas induk di tabel nasabah. Kolom jenis_transaksi dan tipe_pergerakan (VARCHAR, NOT NULL) secara bersamaan menyimpan kategori transaksi (misalnya, SETOR) dan arah pergerakan saldo (KREDIT/DEBIT), yang sangat penting untuk memastikan integritas dan akurasi historis data keuangan.

<img width="1269" height="871" alt="image" src="https://github.com/user-attachments/assets/d5d1c71b-61a7-4b22-a44b-bb6b6385c8ca" />

Tabel transaksi_entry berfungsi sebagai jurnal transaksi digital (Immutable Log) proyek ini. Gambar ini adalah bukti langsung dari keberhasilan implementasi persistence melalui JPA/Hibernate, mengonfirmasi bahwa seluruh logika transaksi di Service Layer dan Model Layer sudah tersimpan secara benar di database.

##  Letak penerapan Abstraction (class/interface)

Abstraksi adalah konsep di mana detail implementasi yang rumit disembunyikan, dan hanya fungsionalitas esensial yang diekspos. Dalam kode Anda, ini diterapkan melalui interface dan abstract class.

### Transaksi.java (interface)

<img width="802" height="232" alt="image" src="https://github.com/user-attachments/assets/088391f6-273e-4c0a-9498-81f0f15c7ac1" />

Ini adalah sebuah interface yang mendefinisikan "kontrak" untuk fungsionalitas transaksi. Kelas apa pun yang mengimplementasikan Transaksi harus menyediakan implementasi untuk semua method yang ada di dalamnya, tanpa peduli bagaimana detailnya bekerja.

### Nasabah.java (Abstract Class)

Ini adalah abstract class yang tidak dapat dibuat objeknya secara langsung. Ia berperan sebagai "cetak biru" yang menyatukan atribut dan method umum untuk semua jenis nasabah (baik nasabah biasa maupun prioritas).

<img width="641" height="30" alt="image" src="https://github.com/user-attachments/assets/854fda39-340a-49a7-9fd1-bfee17e4da0b" />

<img width="706" height="61" alt="image" src="https://github.com/user-attachments/assets/dcfbce38-3930-4f94-b05b-40d5d2ef88c0" />

Kedua method ini (getJenisNasabah() dan biayaTransfer()) didefinisikan sebagai abstract, yang berarti implementasinya wajib disediakan oleh kelas anak (NasabahBiasa dan NasabahPrioritas).

## Letak penerapan Polymorphism (Overloading & Overriding)

Polimorfisme memungkinkan objek dari kelas yang berbeda untuk diperlakukan sebagai objek dari kelas yang sama. Dalam kode Anda, polimorfisme diterapkan melalui dua cara: Overloading dan Overriding.

Overloading
Overloading adalah penggunaan nama method yang sama tetapi dengan parameter yang berbeda. Ini terjadi di interface Transaksi dan diimplementasikan di kelas Nasabah.

### Nasabah.java

<img width="838" height="113" alt="image" src="https://github.com/user-attachments/assets/a6239310-5ebe-4814-9023-844ec8af1bc2" />

<img width="901" height="119" alt="image" src="https://github.com/user-attachments/assets/90e2fe6f-b330-4714-8ec9-130cb2462959" />

<img width="731" height="55" alt="image" src="https://github.com/user-attachments/assets/72149895-d508-4714-aa97-bd48c3473778" />

<img width="878" height="54" alt="image" src="https://github.com/user-attachments/assets/eb7d1303-a82d-431e-949e-ddb980630419" />

Ini adalah inti dari Polimorfisme Overloading, yang berarti satu nama method (setor, tarik, transfer) digunakan untuk melakukan operasi yang serupa namun dapat menerima jumlah atau jenis argumen yang berbeda. Fleksibilitas ini memungkinkan kode di layer BankService dan Main untuk memanggil fungsi transfer secara singkat (transfer(tujuan, jumlah)) atau dengan detail tambahan (transfer(tujuan, jumlah, keterangan)), tanpa harus mengingat banyak nama fungsi, sehingga membuat interface kode menjadi lebih intuitif, bersih, dan maintainable.

## Overriding
Overriding adalah ketika kelas anak mendefinisikan ulang method yang sudah ada di kelas induk. Ini terjadi pada kelas NasabahBiasa dan NasabahPrioritas, yang menyediakan implementasi spesifik untuk method abstrak dari Nasabah.

### NasabahBiasa.java
<img width="725" height="116" alt="image" src="https://github.com/user-attachments/assets/4bbd5bae-53d4-4b48-8b8a-73c3423987b2" />

<img width="439" height="53" alt="image" src="https://github.com/user-attachments/assets/99e5012a-cd11-4f66-8bec-f1ebdb435972" />

1. getJenisNasabah() 

Method ini, yang bersifat abstract di kelas induk, diimplementasikan di sini untuk secara eksplisit mengembalikan nilai "Nasabah Biasa". Ini memberikan identitas konkret bagi objek NasabahBiasa.

2. biayaTransfer(double jumlah) 

Method ini di-override untuk menetapkan kebijakan biaya transfer yang spesifik untuk Nasabah Biasa. Implementasi detailnya (misalnya, biaya Rp2.500 atau Rp5.000) akan berada di dalam blok method ini.

3. tampilkanInfo()

Method ini di-override untuk menyesuaikan tampilan informasi nasabah. Biasanya, implementasi di sini akan menambahkan awalan spesifik ([Nasabah Biasa]) sebelum memanggil method tampilkanInfo() dari kelas induk.

### NasabahPrioritas.java
<img width="761" height="123" alt="image" src="https://github.com/user-attachments/assets/f069273d-a519-40df-b8d3-b5b26abeb4f9" />

<img width="436" height="58" alt="image" src="https://github.com/user-attachments/assets/c62ffd18-3908-479a-a129-fc8bdec2dea1" />

Tiga method utama yang di-override dari kelas induk (Nasabah.java) adalah:

1. getJenisNasabah() 

Method ini diimplementasikan untuk mengidentifikasi objek secara spesifik sebagai "Nasabah Prioritas". Ini mengisi kekosongan yang ditinggalkan oleh method abstrak di kelas induk.

2. biayaTransfer(double jumlah) 

Method ini di-override untuk menetapkan kebijakan biaya transfer yang berbeda. Dalam kasus nasabah prioritas, kebijakan ini biasanya lebih menguntungkan (misalnya, biaya transfer nol atau lebih rendah) dibandingkan nasabah biasa.

3. tampilkanInfo() 

Method ini di-override untuk menyesuaikan format output. Implementasi di sini memungkinkan penambahan awalan spesifik seperti [Nasabah Prioritas] sebelum menampilkan detail umum nasabah yang diwarisi dari kelas induk.

Penggunaan anotasi @Override memastikan bahwa compiler memverifikasi bahwa signature method di kelas anak ini sudah benar-benar cocok dengan signature method di kelas induk.

## penggunaan Anotasi dan Penerapan Fungsional
- Kedua kelas anak ini memperluas fungsionalitas kelas induk dengan menambahkan teks khusus sebelum memanggil method asli dari kelas induknya (super.tampilkanInfo()).

- Polimorfisme ini memungkinkan method seperti tampilkanSemuaNasabah() di BankService untuk memanggil n.tampilkanInfo() pada setiap objek Nasabah (baik yang merupakan NasabahBiasa maupun NasabahPrioritas) dalam daftar, dan JVM secara otomatis menjalankan versi method yang benar sesuai dengan jenis objeknya.

## Penerapan OOP
- **Encapsulation**  
  Semua atribut Nasabah bersifat private dengan akses melalui **getter & setter**.
- **Inheritance**  
  NasabahBiasa dan NasabahPrioritas merupakan subclass dari Nasabah.
- **Overriding**  
  Method tampilkanInfo() dioverride di setiap subclass untuk menampilkan label berbeda sesuai tipe nasabah.

# Output Program/Dokumentasi Program
## Menu Program

<img width="489" height="285" alt="image" src="https://github.com/user-attachments/assets/a0b09799-abf9-4a85-ae66-716725ad0141" />

Program ini berjalan dengan menampilkan sepuluh menu utama kepada pengguna. Opsi 1 sampai 4 digunakan untuk mengelola data dasar nasabah (tambah, lihat, ubah, hapus). Menu 5 membuka sub-menu untuk transaksi finansial, menu 6 berfungsi untuk melihat riwayat rekening, dan menu 7 digunakan untuk mencari data nasabah secara fleksibel. Menu 8 diterapkan untuk memberikan bonus bunga kepada nasabah prioritas, dan Menu 9 dirancang secara khusus untuk menampilkan laporan seluruh data nasabah menggunakan koneksi JDBC Murni. Program akan selalu kembali ke tampilan menu ini setelah setiap tugas selesai, hingga pengguna memilih Menu 10 untuk keluar dari aplikasi dan menutup koneksi database.


<img width="372" height="322" alt="image" src="https://github.com/user-attachments/assets/d1b7dc74-b162-48cc-855a-b6aea122cf79" />

Setiap input yang salah akan dianggap tidak valid untuk mencegah error ketika pengguna salah memasukkan data. Jika pengguna mengetik angka di luar rentang menu (misalnya 9), sistem akan menampilkan pesan "Pilihan harus antara 1 dan 8", sedangkan jika memasukkan karakter non-angka (misalnya a), sistem akan menampilkan pesan "Input tidak valid, masukkan angka!". Dengan validasi ini, program menjadi lebih aman, stabil, dan tetap berjalan meskipun terjadi kesalahan input dari pengguna.

## Menu Create (Tambah Nasabah)

<img width="478" height="194" alt="image" src="https://github.com/user-attachments/assets/c8b25519-8f9b-4a1f-b7a1-9fde91c32af6" />

Output tersebut menunjukkan interaksi saat mencoba menambahkan nasabah baru. Program meminta nama dan setoran awal. Ketika pengguna memasukkan 10000, program menolaknya karena di bawah setoran minimum Rp50.000, lalu meminta input ulang. Saat pengguna salah memasukkan sepuluh, program mendeteksi input non-angka dan menampilkan pesan kesalahan. Akhirnya, ketika pengguna memasukkan 50000, program menerima nilai tersebut, berhasil membuat nasabah bernama Angie, dan memberikan nomor rekening baru 2025003 sebagai konfirmasi.

## Menu Read (Tampilkan Nasabah)

<img width="1055" height="554" alt="Screenshot 2025-10-09 080353" src="https://github.com/user-attachments/assets/c98a2b27-1544-47f0-9154-dd1fa165e292" />

Pada menu **Tampilkan Nasabah**, program akan menampilkan seluruh data nasabah yang tersimpan dalam daftar, meliputi nomor rekening, nama, dan saldo masing-masing nasabah secara berurutan (contoh: REK2025001 atas nama Budi Santoso, REK2025002 atas nama Citra Lestari, dan REK2025003 atas nama Angie). Setelah data ditampilkan, pengguna diminta menekan Enter untuk kembali ke menu utama.


## Menu Update (Ubah Nama Nasabah)

<img width="558" height="163" alt="image" src="https://github.com/user-attachments/assets/bda2bc18-5475-4f47-ad26-1a94f978a8b5" />

Pada menu **Ubah Nama Nasabah**, pengguna diminta memasukkan nomor rekening nasabah yang datanya akan diperbarui. Program kemudian menampilkan nama saat ini dari nasabah tersebut, lalu meminta input nama baru. Setelah pengguna memasukkan nama baru (contoh: dari *Angie* menjadi *Angela*), sistem akan langsung memperbarui data dan menampilkan pesan berhasil, kemudian pengguna diarahkan untuk menekan Enter agar kembali ke menu utama.

<img width="465" height="122" alt="image" src="https://github.com/user-attachments/assets/28dab0c6-0f13-4217-9e51-ac2acec2c8d2" />

Pada menu **Ubah Nama Nasabah**, jika pengguna memasukkan nomor rekening yang tidak terdaftar dalam sistem (contoh: `12`), maka program akan menampilkan pesan **"GAGAL: Rekening tidak ditemukan."**. Setelah itu, pengguna diarahkan untuk menekan Enter agar kembali ke menu utama tanpa ada perubahan data nasabah.


## Menu Delete (Hapus Nasabah)

<img width="556" height="115" alt="image" src="https://github.com/user-attachments/assets/b1df4380-413b-4bef-8e9f-dca97dedfe8a" />

Pada menu **Hapus Nasabah**, pengguna diminta memasukkan nomor rekening yang ingin dihapus. Jika nomor rekening tersebut ditemukan dalam daftar (contoh: `REK2025003`), maka program akan menghapus data nasabah yang bersangkutan dari sistem dan menampilkan pesan **"BERHASIL: Nasabah telah dihapus."**. Setelah itu, pengguna diarahkan untuk menekan Enter agar kembali ke menu utama.


<img width="483" height="88" alt="image" src="https://github.com/user-attachments/assets/2b014186-3d09-4ff4-867c-61a940c8a414" />

Pada menu Hapus Nasabah, jika pengguna memasukkan nomor rekening yang tidak terdaftar dalam sistem (contoh: 123), maka program akan menampilkan pesan "GAGAL: Rekening tidak ditemukan.". Setelah itu, pengguna diarahkan untuk menekan Enter agar kembali ke menu utama tanpa ada data yang terhapus

## Menu Transaksi (Lakukan Transaksi)

<img width="323" height="136" alt="image" src="https://github.com/user-attachments/assets/b09d8b3d-678d-426c-8b99-d216fccd87fe" />

Ketika pengguna memilih menu "Lakukan Transaksi" dari menu utama, program akan menampilkan sub-menu khusus transaksi seperti yang terlihat pada gambar. Di sini, pengguna diberikan pilihan untuk melakukan Setor Tunai (1), Tarik Tunai (2), atau Transfer Dana (3). Opsi nomor 4 disediakan agar pengguna dapat kembali ke menu utama jika tidak ingin melanjutkan transaksi.

### Setor Tunai
<img width="392" height="106" alt="image" src="https://github.com/user-attachments/assets/242fdc7b-e5f1-4c5d-aea8-a893fb199e7c" />

Pada menu Setor Tunai, pengguna diminta memasukkan nomor rekening tujuan (contoh: REK2025001) diikuti dengan jumlah nominal yang akan disetor. Jika rekening ditemukan dan nominal valid, program akan menambahkan jumlah tersebut ke saldo nasabah dan mencatatnya di mutasi. Sebagai konfirmasi, sistem akan menampilkan pesan "BERHASIL" beserta saldo terbaru dari rekening tersebut.

<img width="442" height="112" alt="image" src="https://github.com/user-attachments/assets/f5437961-0fbc-4c06-ac17-afc054edc357" />

Alur ini menunjukkan penanganan kesalahan pada menu Setor Tunai. Ketika pengguna memasukkan nomor rekening yang tidak ada dalam sistem (contoh: 20), program akan melakukan pencarian dan gagal menemukan data yang cocok. Akibatnya, sistem akan membatalkan transaksi dan menampilkan pesan "GAGAL: Rekening tidak ditemukan"

<img width="389" height="266" alt="image" src="https://github.com/user-attachments/assets/a2d92312-78fc-4281-bc8f-41382681fd83" />

Setiap input nominal yang bukan berupa angka akan dianggap tidak valid untuk mencegah error saat transaksi. Misalnya pada menu Setor Tunai, jika pengguna memasukkan teks seperti "seratus", sistem akan menampilkan pesan "Input tidak valid, masukkan angka!" dan meminta pengguna mengulang dengan angka yang benar. Setelah pengguna mengetik jumlah yang valid (contoh: 100000), transaksi akan diproses dan saldo terbaru akan ditampilkan.

### Tarik Tunai
<img width="680" height="150" alt="image" src="https://github.com/user-attachments/assets/6f879db1-a207-4212-be14-4be0992fc92b" />

Pada menu Tarik Tunai, alur ini menunjukkan validasi saldo minimum. Pengguna memasukkan nomor rekening yang valid (REK2025001) dan mencoba menarik nominal yang sangat besar (200000000). Program kemudian memeriksa apakah sisa saldo setelah penarikan akan lebih besar atau sama dengan batas saldo minimum yang telah ditentukan. Karena kondisi tersebut tidak terpenuhi, transaksi dibatalkan dan sistem menampilkan pesan "GAGAL: Saldo tidak mencukupi atau akan kurang dari saldo minimum"

<img width="434" height="138" alt="image" src="https://github.com/user-attachments/assets/8f2f7475-2bbb-4462-9bae-42409dbffade" />

Alur ini menunjukkan proses transaksi Tarik Tunai yang berhasil. Setelah memilih menu Tarik Tunai, pengguna memasukkan nomor rekening yang valid (REK2025001) dan jumlah penarikan (100000). Program memvalidasi bahwa saldo nasabah mencukupi untuk penarikan tersebut dan sisa saldonya tidak akan kurang dari batas minimum. Karena semua syarat terpenuhi, saldo nasabah dikurangi dan sistem menampilkan pesan "BERHASIL" beserta informasi sisa saldo yang terbaru.

<img width="437" height="115" alt="image" src="https://github.com/user-attachments/assets/40b63c50-e20b-4594-b05e-e2b360f93114" />

Alur ini menunjukkan penanganan kesalahan ketika pengguna mencoba melakukan penarikan tunai dari rekening yang tidak ada. Pengguna memasukkan nomor rekening yang tidak terdaftar di sistem (contoh: 1000). Program kemudian mencari data tersebut namun tidak berhasil menemukannya, sehingga transaksi dibatalkan dan sistem menampilkan pesan "GAGAL: Rekening tidak ditemukan"

<img width="394" height="263" alt="image" src="https://github.com/user-attachments/assets/78259cdf-9f3a-469b-92d2-9a37286fd92e" />

Setiap input nominal yang tidak sesuai format angka akan dianggap tidak valid untuk mencegah error pada proses transaksi. Misalnya pada menu Tarik Tunai, jika pengguna memasukkan teks seperti "seratus", sistem akan menampilkan pesan "Input tidak valid, masukkan angka!" dan meminta pengguna mengulang input. Setelah pengguna mengetik jumlah yang benar (contoh: 100000), transaksi akan berhasil diproses dan sistem menampilkan saldo terbaru dari rekening tersebut.

### Transfer Dana 
<img width="460" height="172" alt="image" src="https://github.com/user-attachments/assets/a50a426f-9274-4851-a733-c609c735ccd8" />

Alur ini menunjukkan proses Transfer Dana yang berhasil antar dua rekening berbeda. Pengguna memasukkan nomor rekening pengirim (REK2025001), nomor rekening penerima (REK2025002), dan jumlah yang akan ditransfer (100000). Program memvalidasi bahwa semua rekening valid dan saldo pengirim mencukupi untuk jumlah transfer ditambah biaya administrasi. Setelah semua syarat terpenuhi, saldo pengirim dikurangi dan saldo penerima ditambahkan. Terakhir, sistem menampilkan pesan konfirmasi "BERHASIL: Transfer berhasil"

<img width="569" height="151" alt="image" src="https://github.com/user-attachments/assets/96819513-c758-4af3-a533-44e9494fa7b5" />

Alur ini menunjukkan validasi penting pada fitur Transfer Dana, yaitu mencegah pengguna mentransfer uang ke rekeningnya sendiri. Pengguna memasukkan nomor rekening yang sama untuk pengirim dan penerima (REK2025001). Program mendeteksi kesamaan ini, membatalkan transaksi sebelum meminta nominal, dan menampilkan pesan "GAGAL: Anda tidak bisa mentransfer ke rekening sendiri"

<img width="469" height="164" alt="image" src="https://github.com/user-attachments/assets/65111d0c-94a5-460d-80da-f3d170282faa" />

Alur ini menunjukkan validasi saldo pada fitur Transfer Dana. Pengguna telah memasukkan nomor rekening pengirim dan penerima yang valid, namun kemudian memasukkan jumlah transfer yang sangat besar (2000000000). Program memeriksa apakah saldo pengirim mencukupi untuk membayar jumlah transfer ditambah biaya administrasi. Karena tidak cukup, transaksi dibatalkan dan sistem menampilkan pesan "GAGAL: Saldo tidak mencukupi"

<img width="380" height="279" alt="image" src="https://github.com/user-attachments/assets/5a8b528f-ccea-4ebd-a524-5836cabd4ce5" />

Setiap input nominal pada fitur transfer yang tidak berbentuk angka akan dianggap tidak valid agar transaksi tidak gagal. Misalnya pada menu Transfer Dana, jika pengguna mengetik teks seperti "seratus", sistem langsung menampilkan pesan "Input tidak valid, masukkan angka!" dan meminta ulang input. Setelah pengguna memasukkan jumlah yang benar (contoh: 100000), sistem memproses transaksi dengan sukses dan menampilkan pesan "Transfer berhasil."

### Kembali Ke Menu Utama
<img width="377" height="275" alt="image" src="https://github.com/user-attachments/assets/8e7f34c9-cd43-40f1-8b3c-d92e8bf70aa3" />

Alur pada gambar ini menunjukkan cara pengguna kembali ke menu utama dari sub-menu transaksi. Setelah berada di dalam "Menu Transaksi", pengguna memilih opsi nomor 4 ("Kembali ke Menu Utama"). Program kemudian mengeksekusi perintah return untuk keluar dari fungsi menuTransaksi(), dan alur program langsung kembali menampilkan menu utama, siap menerima perintah selanjutnya.


<img width="461" height="231" alt="image" src="https://github.com/user-attachments/assets/215ddc3a-6479-48bf-a95b-7cb1ddc78741" />

Alur ini menunjukkan penanganan input yang tidak valid di dalam sub-menu transaksi. Pengguna memasukkan angka 5, yang tidak termasuk dalam pilihan yang tersedia (1-4). Program kemudian menjalankan blok default pada switch-case di dalam fungsi menuTransaksi(), yang menampilkan pesan "Pilihan tidak valid.". Setelah itu, pengguna diminta menekan Enter untuk melanjutkan sesi transaksi di sub-menu tersebut.


## Menu Mutasi (Lihat Mutasi Rekening)

<img width="809" height="157" alt="image" src="https://github.com/user-attachments/assets/357158c4-f0e2-4f7f-a53f-14f2c83cf894" />

Output mutasi ini memvalidasi keberhasilan integrasi Persistence Layer yang telah dimigrasikan. Saat pengguna memilih Menu 6, method lihatMutasiRekening() mengeksekusi kueri JPQL terhadap entitas TransaksiEntry untuk mengambil seluruh riwayat transaksi nasabah 2025001. Data yang ditampilkan membuktikan fungsionalitas transaksional penuh sistem, mencakup transaksi BUNGA (+Rp280,500), SETOR (+Rp100,000), TARIK (-Rp100,000), dan TRANSFER_KELUAR (termasuk biaya Rp0.0), yang semuanya tersimpan secara kronologis di database dan disajikan sebagai bukti log mutasi yang terstruktur.

<img width="909" height="157" alt="image" src="https://github.com/user-attachments/assets/f703dbc2-6d65-4f3c-9ead-45f206b84f0c" />

Output ini memperlihatkan pengguna memilih menu 6 untuk melihat mutasi rekening, lalu memasukkan nomor rekening 2025002. Program mengenali bahwa ini adalah rekening nasabah prioritas dan, sesuai dengan perubahan yang telah Anda buat, secara otomatis memotong biaya administrasi bulanan sebesar Rp3.000,00. Potongan ini terjadi karena ini adalah kali pertama mutasi rekening dilihat pada bulan September 2025. Setelah pemotongan, saldo awal yang tadinya Rp12.000.000,00 berkurang menjadi Rp11.997.000,00, dan mutasi tersebut dicatat dalam riwayat rekening nasabah.

<img width="440" height="119" alt="image" src="https://github.com/user-attachments/assets/a81a8e3d-a015-4441-839e-d22da9b5091a" />

Alur ini menunjukkan penanganan kesalahan saat pengguna mencoba melihat mutasi rekening dengan nomor yang tidak terdaftar. Ketika pengguna memasukkan nomor rekening yang tidak ada di dalam sistem (contoh: 2409116008), program akan gagal menemukan data yang cocok dan akan menampilkan pesan "GAGAL: Rekening tidak ditemukan." untuk menginformasikan pengguna.

## Menu Search (Cari Nasabah)

<img width="652" height="135" alt="image" src="https://github.com/user-attachments/assets/790020ff-a68e-49ea-8107-5c3c4d311f4d" />

Pada menu Cari Nasabah, pengguna dapat memasukkan kata kunci berupa Nama ataupun Nomor Rekening (contoh: REK2025001). Program kemudian akan melakukan iterasi pada seluruh data nasabah. Jika ditemukan data yang cocok dengan kata kunci, baik pada nama maupun nomor rekening, maka detail lengkap dari nasabah tersebut akan ditampilkan di bawah "Hasil Pencarian".

<img width="425" height="143" alt="image" src="https://github.com/user-attachments/assets/6e9e6b32-617e-4914-8221-f80777d8232d" />

Alur ini menunjukkan hasil pencarian jika data tidak ditemukan. Ketika pengguna memasukkan kata kunci yang tidak cocok dengan nama atau nomor rekening manapun yang ada di dalam sistem (contoh: Angie), program akan menyelesaikan proses pencarian tanpa menemukan hasil. Sebagai umpan balik, sistem akan menampilkan pesan informatif "Nasabah tidak ditemukan".

## Menu Bonus Untuk Nasabah Prioritas

<img width="770" height="534" alt="Screenshot 2025-10-09 070936" src="https://github.com/user-attachments/assets/a7f99d9c-6f52-4b98-8a55-ca9c9d551338" />

Fitur Bonus untuk Nasabah Prioritas (Menu 8) ini memvalidasi logic transaksional penting pada lapisan Service dan Model. Program secara otomatis mengambil semua entitas NasabahPrioritas dari database via ORM, menghitung bonus bunga sebesar 2% dari saldo saat ini (misalnya, Citra Lestari menerima Rp289,832), dan menambahkan bonus tersebut ke saldo utama (update transaksional). Proses ini juga secara eksplisit memanggil method n.catat("BUNGA", ...) untuk mencatat transaksi ini ke tabel transaksi_entry, menegaskan keberhasilan Polymorphism (eksekusi logic yang berbeda berdasarkan tipe nasabah) dan integritas data write ke database.

Dengan cara ini, fitur bonus menegaskan perbedaan layanan antara Nasabah Biasa dan Nasabah Prioritas, karena hanya prioritas yang mendapat tambahan saldo otomatis. Ini sesuai konsep bank digital yang memberikan benefit khusus bagi nasabah premium.

## Menu Tampilkan Laporan (JDBC)

<img width="995" height="654" alt="image" src="https://github.com/user-attachments/assets/902bef11-e91b-47a1-b171-53e27476adef" />

Output tersebut menampilkan fitur Tampilkan Laporan (JDBC Murni), yang merupakan validasi keberhasilan implementasi low-level akses data pada aplikasi. Laporan ini menunjukkan daftar lengkap data nasabah, diambil secara langsung dari database MySQL melalui raw query SQL, yang membuktikan koneksi JDBC Murni berfungsi secara independen dari framework ORM. Data yang ditampilkan dikategorikan berdasarkan Nomor Rekening, Nama, Saldo, dan Jenis nasabah, memperlihatkan pemetaan yang akurat antara data tersimpan dan output yang diformat.

## Menu Exit (Keluar)

<img width="758" height="48" alt="image" src="https://github.com/user-attachments/assets/9e41f980-1f0c-4890-9b91-25eacbdad716" />

Terakhir, jika pengguna memilih menu Keluar (contoh: opsi nomor 8), program akan menampilkan pesan perpisahan "Terima kasih!". Setelah itu, perintah return dieksekusi, yang akan menghentikan perulangan utama (main loop) dan mengakhiri sesi program secara permanen.
