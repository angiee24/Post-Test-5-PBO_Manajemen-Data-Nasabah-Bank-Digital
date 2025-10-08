-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 08, 2025 at 07:06 PM
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
(1, 'PRIORITAS', '2025001', 'Citra Lestari', 14591612.080799999, NULL),
(2, 'PRIORITAS', '2025003', 'Andi Pratama', 57222000, NULL),
(3, 'PRIORITAS', '2025005', 'Dewi Anggraini', 29651400, NULL),
(4, 'PRIORITAS', '2025007', 'Hari Susanto', 10404000, NULL),
(5, 'PRIORITAS', '2025009', 'Sinta Melati', 36361980, NULL),
(6, 'PRIORITAS', '2025011', 'Bayu Kuncoro', 74908800, NULL),
(7, 'PRIORITAS', '2025013', 'Lia Handayani', 15710040, NULL),
(8, 'PRIORITAS', '2025015', 'Nico Saputra', 92491560, NULL),
(9, 'PRIORITAS', '2025017', 'Putri Diana', 42656400, NULL),
(10, 'PRIORITAS', '2025019', 'Rizky Fadillah', 22368600, NULL),
(11, 'BIASA', '2025002', 'Budi Santoso', 500000, NULL),
(12, 'BIASA', '2025004', 'Eka Fitriani', 7500000, NULL),
(13, 'BIASA', '2025006', 'Fajar Nurhadi', 125000, NULL),
(14, 'BIASA', '2025008', 'Gita Cahyani', 4500000, NULL),
(15, 'BIASA', '2025010', 'Joko Widodo', 60000, NULL),
(16, 'BIASA', '2025012', 'Kiki Amelia', 9800000, NULL),
(17, 'BIASA', '2025014', 'Marta Sari', 100000, NULL),
(18, 'BIASA', '2025016', 'Omar Dani', 3200000, NULL),
(19, 'BIASA', '2025018', 'Tika Wulandari', 85000, NULL),
(20, 'BIASA', '2025020', 'Yoga Permana', 5750000, NULL),
(28, 'PRIORITAS', '2025023', 'ji', 78030000, NULL);

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
(12, 28, '2025-10-08 16:48:43', 'BUNGA', 1500000, 'KREDIT', 'Bunga tabungan Prioritas'),
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
(23, 28, '2025-10-08 16:57:18', 'BUNGA', 1530000, 'KREDIT', 'Bunga tabungan Prioritas');

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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `transaksi_entry`
--
ALTER TABLE `transaksi_entry`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

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
