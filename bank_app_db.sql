-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 09, 2025 at 04:14 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bank_app_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `nasabah`
--

CREATE TABLE `nasabah` (
  `id` bigint(20) NOT NULL,
  `JENIS` varchar(31) NOT NULL,
  `nomor_rekening` varchar(15) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `saldo` double NOT NULL,
  `nomorRekening` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `nasabah`
--

INSERT INTO `nasabah` (`id`, `JENIS`, `nomor_rekening`, `nama`, `saldo`, `nomorRekening`) VALUES
(1, 'PRIORITAS', '2025001', 'Citra Lestari', 15077073.208864318, NULL),
(2, 'PRIORITAS', '2025003', 'Andi Pratama', 59533768.8, NULL),
(3, 'PRIORITAS', '2025005', 'Dewi Anggraini', 30849316.56, NULL),
(4, 'PRIORITAS', '2025007', 'Hari Susanto', 10824321.6, NULL),
(5, 'PRIORITAS', '2025009', 'Sinta Melati', 37831003.992, NULL),
(6, 'PRIORITAS', '2025011', 'Bayu Kuncoro', 77935115.52, NULL),
(7, 'PRIORITAS', '2025013', 'Lia Handayani', 16344725.616, NULL),
(8, 'PRIORITAS', '2025015', 'Nico Saputra', 96228219.024, NULL),
(9, 'PRIORITAS', '2025017', 'Putri Diana', 44379718.56, NULL),
(10, 'PRIORITAS', '2025019', 'Rizky Fadillah', 23272291.44, NULL),
(11, 'BIASA', '2025002', 'Budi Santoso', 600000, NULL),
(12, 'BIASA', '2025004', 'Eka Fitriani', 7500000, NULL),
(13, 'BIASA', '2025006', 'Fajar Nurhadi', 125000, NULL),
(14, 'BIASA', '2025008', 'Gita Cahyani', 4500000, NULL),
(15, 'BIASA', '2025010', 'Joko Widodo', 60000, NULL),
(16, 'BIASA', '2025012', 'Kiki Amelia', 9800000, NULL),
(17, 'BIASA', '2025014', 'Marta Sari', 100000, NULL),
(18, 'BIASA', '2025016', 'Omar Dani', 3200000, NULL),
(19, 'BIASA', '2025018', 'Tika Wulandari', 85000, NULL),
(20, 'BIASA', '2025020', 'Yoga Permana', 5750000, NULL),
(43, 'BIASA', '2025021', 'angie nji', 102000, NULL),
(44, 'BIASA', '2025022', 'oline', 104000, NULL),
(45, 'BIASA', '2025023', 'lin', 51000, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `transaksi_entry`
--

CREATE TABLE `transaksi_entry` (
  `id` bigint(20) NOT NULL,
  `nasabah_id` bigint(20) NOT NULL,
  `waktu` datetime NOT NULL,
  `jenis_transaksi` varchar(50) NOT NULL,
  `jumlah` double NOT NULL,
  `tipe_pergerakan` varchar(10) NOT NULL,
  `keterangan` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transaksi_entry`
--

INSERT INTO `transaksi_entry` (`id`, `nasabah_id`, `waktu`, `jenis_transaksi`, `jumlah`, `tipe_pergerakan`, `keterangan`) VALUES
(2, 1, '2025-10-08 16:48:43', 'BUNGA', 280500.04, 'KREDIT', 'Bunga tabungan Prioritas'),
(3, 2, '2025-10-08 16:48:43', 'BUNGA', 1100000, 'KREDIT', 'Bunga tabungan Prioritas'),
(4, 3, '2025-10-08 16:48:43', 'BUNGA', 570000, 'KREDIT', 'Bunga tabungan Prioritas'),
(5, 4, '2025-10-08 16:48:43', 'BUNGA', 200000, 'KREDIT', 'Bunga tabungan Prioritas'),
(6, 5, '2025-10-08 16:48:43', 'BUNGA', 699000, 'KREDIT', 'Bunga tabungan Prioritas'),
(7, 6, '2025-10-08 16:48:43', 'BUNGA', 1440000, 'KREDIT', 'Bunga tabungan Prioritas'),
(8, 7, '2025-10-08 16:48:43', 'BUNGA', 302000, 'KREDIT', 'Bunga tabungan Prioritas'),
(9, 8, '2025-10-08 16:48:43', 'BUNGA', 1778000, 'KREDIT', 'Bunga tabungan Prioritas'),
(10, 9, '2025-10-08 16:48:43', 'BUNGA', 820000, 'KREDIT', 'Bunga tabungan Prioritas'),
(11, 10, '2025-10-08 16:48:43', 'BUNGA', 430000, 'KREDIT', 'Bunga tabungan Prioritas'),
(13, 1, '2025-10-08 16:57:18', 'BUNGA', 286110.0408, 'KREDIT', 'Bunga tabungan Prioritas'),
(14, 2, '2025-10-08 16:57:18', 'BUNGA', 1122000, 'KREDIT', 'Bunga tabungan Prioritas'),
(15, 3, '2025-10-08 16:57:18', 'BUNGA', 581400, 'KREDIT', 'Bunga tabungan Prioritas'),
(16, 4, '2025-10-08 16:57:18', 'BUNGA', 204000, 'KREDIT', 'Bunga tabungan Prioritas'),
(17, 5, '2025-10-08 16:57:18', 'BUNGA', 712980, 'KREDIT', 'Bunga tabungan Prioritas'),
(18, 6, '2025-10-08 16:57:18', 'BUNGA', 1468800, 'KREDIT', 'Bunga tabungan Prioritas'),
(19, 7, '2025-10-08 16:57:18', 'BUNGA', 308040, 'KREDIT', 'Bunga tabungan Prioritas'),
(20, 8, '2025-10-08 16:57:18', 'BUNGA', 1813560, 'KREDIT', 'Bunga tabungan Prioritas'),
(21, 9, '2025-10-08 16:57:18', 'BUNGA', 836400, 'KREDIT', 'Bunga tabungan Prioritas'),
(22, 10, '2025-10-08 16:57:18', 'BUNGA', 438600, 'KREDIT', 'Bunga tabungan Prioritas'),
(25, 1, '2025-10-08 23:07:50', 'SETOR', 100000, 'KREDIT', 'Setoran tunai'),
(26, 1, '2025-10-08 23:08:06', 'TARIK', 100000, 'DEBIT', 'Tarik tunai'),
(27, 1, '2025-10-08 23:08:20', 'TRANSFER_KELUAR', 100000, 'DEBIT', 'Transfer ke 2025002 (Biaya: 0.0)'),
(28, 11, '2025-10-08 23:08:20', 'TRANSFER_MASUK', 100000, 'KREDIT', 'Transfer dari 2025002'),
(29, 1, '2025-10-08 23:09:29', 'BUNGA', 289832.24161599996, 'KREDIT', 'Bunga tabungan Prioritas'),
(30, 2, '2025-10-08 23:09:29', 'BUNGA', 1144440, 'KREDIT', 'Bunga tabungan Prioritas'),
(31, 3, '2025-10-08 23:09:29', 'BUNGA', 593028, 'KREDIT', 'Bunga tabungan Prioritas'),
(32, 4, '2025-10-08 23:09:29', 'BUNGA', 208080, 'KREDIT', 'Bunga tabungan Prioritas'),
(33, 5, '2025-10-08 23:09:29', 'BUNGA', 727239.6, 'KREDIT', 'Bunga tabungan Prioritas'),
(34, 6, '2025-10-08 23:09:29', 'BUNGA', 1498176, 'KREDIT', 'Bunga tabungan Prioritas'),
(35, 7, '2025-10-08 23:09:29', 'BUNGA', 314200.8, 'KREDIT', 'Bunga tabungan Prioritas'),
(36, 8, '2025-10-08 23:09:29', 'BUNGA', 1849831.2, 'KREDIT', 'Bunga tabungan Prioritas'),
(37, 9, '2025-10-08 23:09:29', 'BUNGA', 853128, 'KREDIT', 'Bunga tabungan Prioritas'),
(38, 10, '2025-10-08 23:09:29', 'BUNGA', 447372, 'KREDIT', 'Bunga tabungan Prioritas'),
(41, 1, '2025-10-08 23:59:47', 'BUNGA', 295628.88644831994, 'KREDIT', 'Bunga tabungan Prioritas'),
(42, 2, '2025-10-08 23:59:47', 'BUNGA', 1167328.8, 'KREDIT', 'Bunga tabungan Prioritas'),
(43, 3, '2025-10-08 23:59:47', 'BUNGA', 604888.56, 'KREDIT', 'Bunga tabungan Prioritas'),
(44, 4, '2025-10-08 23:59:47', 'BUNGA', 212241.6, 'KREDIT', 'Bunga tabungan Prioritas'),
(45, 5, '2025-10-08 23:59:47', 'BUNGA', 741784.392, 'KREDIT', 'Bunga tabungan Prioritas'),
(46, 6, '2025-10-08 23:59:47', 'BUNGA', 1528139.52, 'KREDIT', 'Bunga tabungan Prioritas'),
(47, 7, '2025-10-08 23:59:47', 'BUNGA', 320484.81600000005, 'KREDIT', 'Bunga tabungan Prioritas'),
(48, 8, '2025-10-08 23:59:47', 'BUNGA', 1886827.824, 'KREDIT', 'Bunga tabungan Prioritas'),
(49, 9, '2025-10-08 23:59:47', 'BUNGA', 870190.56, 'KREDIT', 'Bunga tabungan Prioritas'),
(50, 10, '2025-10-08 23:59:47', 'BUNGA', 456319.44, 'KREDIT', 'Bunga tabungan Prioritas');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `nasabah`
--
ALTER TABLE `nasabah`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nomor_rekening` (`nomor_rekening`);

--
-- Indexes for table `transaksi_entry`
--
ALTER TABLE `transaksi_entry`
  ADD PRIMARY KEY (`id`),
  ADD KEY `nasabah_id` (`nasabah_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `nasabah`
--
ALTER TABLE `nasabah`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;

--
-- AUTO_INCREMENT for table `transaksi_entry`
--
ALTER TABLE `transaksi_entry`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `transaksi_entry`
--
ALTER TABLE `transaksi_entry`
  ADD CONSTRAINT `transaksi_entry_ibfk_1` FOREIGN KEY (`nasabah_id`) REFERENCES `nasabah` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
