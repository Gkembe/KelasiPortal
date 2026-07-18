-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Host: db
-- Generation Time: Jul 18, 2026 at 12:44 PM
-- Server version: 9.4.0
-- PHP Version: 8.2.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kelasidb`
--

-- --------------------------------------------------------

--
-- Table structure for table `courses`
--

CREATE TABLE `courses` (
  `courseID` int NOT NULL,
  `schoolID` int NOT NULL,
  `courseName` varchar(100) NOT NULL,
  `courseCode` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `createdAT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` enum('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `courses`
--

INSERT INTO `courses` (`courseID`, `schoolID`, `courseName`, `courseCode`, `createdAT`, `status`) VALUES
(2, 23, 'Calcul 1', 'MATH001', '2026-06-05 16:07:01', 'ACTIVE'),
(3, 23, 'English 1010', 'ENG1010', '2026-06-05 17:53:03', 'ACTIVE'),
(4, 23, 'Geography', 'GEO0101', '2026-06-08 18:41:46', 'ACTIVE');

-- --------------------------------------------------------

--
-- Table structure for table `departments`
--

CREATE TABLE `departments` (
  `departmentID` int NOT NULL,
  `schoolID` int NOT NULL,
  `departmentName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` enum('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
  `createdAT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `departments`
--

INSERT INTO `departments` (`departmentID`, `schoolID`, `departmentName`, `status`, `createdAT`) VALUES
(1, 23, 'Electronic', 'ACTIVE', '2026-02-20 03:11:40'),
(2, 23, 'Mathematic', 'ACTIVE', '2026-02-20 03:50:19'),
(3, 23, 'Electricity', 'ACTIVE', '2026-02-26 02:28:22'),
(4, 23, 'Mecanic', 'ACTIVE', '2026-03-12 00:35:08'),
(5, 23, 'Mecatronic', 'ACTIVE', '2026-03-27 17:44:47'),
(6, 23, 'Electronic embarquer', 'ACTIVE', '2026-07-12 16:23:03');

-- --------------------------------------------------------

--
-- Table structure for table `levelCourse`
--

CREATE TABLE `levelCourse` (
  `levelCourseID` int NOT NULL,
  `levelID` int NOT NULL,
  `courseID` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `levelCourse`
--

INSERT INTO `levelCourse` (`levelCourseID`, `levelID`, `courseID`) VALUES
(1, 24, 2),
(2, 24, 3),
(3, 25, 2),
(4, 25, 3),
(5, 24, 2),
(6, 24, 3),
(7, 26, 3),
(8, 26, 2),
(9, 26, 2),
(10, 24, 2),
(11, 24, 3),
(12, 24, 4);

-- --------------------------------------------------------

--
-- Table structure for table `levels`
--

CREATE TABLE `levels` (
  `levelID` int NOT NULL,
  `schoolID` int NOT NULL,
  `departmentID` int DEFAULT NULL,
  `levelName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `levelCode` varchar(10) DEFAULT NULL,
  `createdAT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` enum('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `levels`
--

INSERT INTO `levels` (`levelID`, `schoolID`, `departmentID`, `levelName`, `levelCode`, `createdAT`, `status`) VALUES
(18, 23, 1, 'Premiere', '1th', '2026-02-20 03:54:05', 'ACTIVE'),
(23, 23, 2, 'Premiere', '1th', '2026-02-20 04:21:33', 'ACTIVE'),
(24, 23, 1, 'Deuxieme', '2th', '2026-02-26 02:28:52', 'ACTIVE'),
(25, 23, 4, 'Premiere', '1th', '2026-03-12 00:35:42', 'ACTIVE'),
(26, 23, 4, 'Troisieme', '3th', '2026-03-12 00:37:40', 'ACTIVE');

-- --------------------------------------------------------

--
-- Table structure for table `school`
--

CREATE TABLE `school` (
  `schoolID` int NOT NULL,
  `schoolName` varchar(150) NOT NULL,
  `shortName` varchar(50) DEFAULT NULL,
  `registrationNumber` varchar(60) NOT NULL,
  `schoolType` varchar(50) NOT NULL,
  `country` varchar(100) NOT NULL,
  `city` varchar(60) NOT NULL,
  `schoolAddress` varchar(120) NOT NULL,
  `schoolEmail` varchar(120) NOT NULL,
  `website` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `schoolLogo` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `isActive` tinyint(1) NOT NULL DEFAULT '1',
  `createdAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `school`
--

INSERT INTO `school` (`schoolID`, `schoolName`, `shortName`, `registrationNumber`, `schoolType`, `country`, `city`, `schoolAddress`, `schoolEmail`, `website`, `schoolLogo`, `isActive`, `createdAt`) VALUES
(20, 'GEDE', 'GKLFG2', 'GL002', 'PUBLIC', 'Bangladesh', 'Lincoln', '5, av de la science', 'kembe3@gmail.com', 'https://weber.com', NULL, 1, '2025-12-15 05:02:31'),
(21, 'GEDE', 'GKLFG2', 'GL002', 'PUBLIC', 'Bangladesh', 'Lincoln', '5, av de la science', 'kembe3@gmail.com', 'https://weber.com', 'school_1765777080003.JPG\n', 1, '2025-12-15 05:11:50'),
(22, 'Gede', 'GKLFG', 'GHHH2', 'PRIVATE', 'Albania', 'Lincoln', '5, av de la science', 'kembe3@gmail.com', 'https://weber.com', 'school_1765777080003.JPG', 1, '2025-12-15 05:38:00'),
(23, 'CS Les Genies', 'SGS', 'FRTYT3423', 'PRIVATE', 'Democratic Republic of the Congo', 'Kinshasa', '5, av de la science', 'kembe1@gmail.com', 'https://weber.com', 'school_23_1775699332340.jpg', 1, '2025-12-18 18:20:26'),
(24, 'CS La Borne', 'CLB', 'LB56748667', '', 'Democratic Republic of the Congo', 'Lincoln', '5, av de la science', 'laborne@gmail.com', 'https://weber.com', NULL, 1, '2026-02-04 02:37:18'),
(25, 'CS La Bonte', 'CLBO', 'LB56748668', 'CHARTER', 'Democratic Republic of the Congo', 'Lincoln', '5, av de la science', 'labonte@gmail.com', 'https://weber.com', 'school_1770175354076.jpg', 1, '2026-02-04 03:22:34');

-- --------------------------------------------------------

--
-- Table structure for table `schoolCycle`
--

CREATE TABLE `schoolCycle` (
  `schoolCycleID` int NOT NULL,
  `schoolID` int NOT NULL,
  `cycleName` enum('NURSERY','PRIMARY','SECONDARY') NOT NULL,
  `status` enum('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `schoolCycle`
--

INSERT INTO `schoolCycle` (`schoolCycleID`, `schoolID`, `cycleName`, `status`) VALUES
(3, 24, 'PRIMARY', 'ACTIVE'),
(4, 25, 'PRIMARY', 'ACTIVE'),
(5, 25, 'SECONDARY', 'ACTIVE');

-- --------------------------------------------------------

--
-- Table structure for table `studentGuardians`
--

CREATE TABLE `studentGuardians` (
  `guardianID` int NOT NULL,
  `studentID` int NOT NULL,
  `fullName` varchar(100) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `address` varchar(250) DEFAULT NULL,
  `occupation` varchar(100) DEFAULT NULL,
  `relationshipType` varchar(50) NOT NULL,
  `isPrimary` enum('YES','NO','') NOT NULL DEFAULT 'NO',
  `createdAT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `isActive` enum('ACTIVATE','INACTIVATE') NOT NULL DEFAULT 'ACTIVATE'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `studentGuardians`
--

INSERT INTO `studentGuardians` (`guardianID`, `studentID`, `fullName`, `phone`, `email`, `address`, `occupation`, `relationshipType`, `isPrimary`, `createdAT`, `updatedAT`, `isActive`) VALUES
(3, 10, 'Gedeon Nyonganyi Kem', '+14025804849', 'kembegedeonnn@gmail.com', '1609 D street apt 3', 'bezos', 'Father', 'YES', '2026-03-30 15:33:04', '2026-05-26 03:29:05', 'INACTIVATE');

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `studentID` int NOT NULL,
  `schoolID` int DEFAULT NULL,
  `userID` int DEFAULT NULL,
  `registrationNumber` varchar(50) NOT NULL,
  `firstName` varchar(100) NOT NULL,
  `middleName` varchar(50) DEFAULT NULL,
  `lastName` varchar(100) NOT NULL,
  `gender` enum('MALE','FEMALE','OTHER') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `dateOfBirth` date DEFAULT NULL,
  `enrollmentDate` date DEFAULT NULL,
  `academicYear` varchar(20) DEFAULT NULL,
  `phone` varchar(30) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `isActive` enum('ACTIVE','INACTIVE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'ACTIVE',
  `createdAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `levelID` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`studentID`, `schoolID`, `userID`, `registrationNumber`, `firstName`, `middleName`, `lastName`, `gender`, `dateOfBirth`, `enrollmentDate`, `academicYear`, `phone`, `address`, `isActive`, `createdAt`, `updatedAt`, `levelID`) VALUES
(9, 23, 59, 'RK50505', 'Robert', NULL, 'Kembe', 'MALE', '2026-03-08', '2026-03-08', '2025-2026', '+14024722334', '5, av de la science', 'ACTIVE', '2026-03-08 21:57:47', '2026-03-08 21:57:47', 18),
(10, 23, 60, 'KM58676', 'Kamba', 'Nyonganyi', 'Kembee', 'MALE', '2026-03-09', '2026-03-09', '2025-2026', '+14024722334', '5, av de la science', 'ACTIVE', '2026-03-09 17:29:07', '2026-05-26 03:29:53', 23);

-- --------------------------------------------------------

--
-- Table structure for table `teacherAssignments`
--

CREATE TABLE `teacherAssignments` (
  `assignmentID` int NOT NULL,
  `schoolID` int NOT NULL,
  `teacherID` int NOT NULL,
  `levelID` int NOT NULL,
  `courseID` int NOT NULL,
  `status` enum('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
  `createdAT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `teacherAssignments`
--

INSERT INTO `teacherAssignments` (`assignmentID`, `schoolID`, `teacherID`, `levelID`, `courseID`, `status`, `createdAT`) VALUES
(10, 23, 6, 24, 2, 'INACTIVE', '2026-07-17 04:11:33'),
(11, 23, 6, 25, 2, 'ACTIVE', '2026-07-17 04:11:42'),
(12, 23, 6, 24, 3, 'INACTIVE', '2026-07-17 04:13:10'),
(13, 23, 6, 25, 3, 'ACTIVE', '2026-07-17 04:13:22');

-- --------------------------------------------------------

--
-- Table structure for table `teachers`
--

CREATE TABLE `teachers` (
  `teacherID` int NOT NULL,
  `user_id` int NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `middleName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `last_name` varchar(50) NOT NULL,
  `gender` varchar(10) NOT NULL,
  `subject` varchar(100) DEFAULT NULL,
  `qualification` varchar(100) DEFAULT NULL,
  `phoneNumber` varchar(20) NOT NULL,
  `officeLocation` varchar(50) NOT NULL,
  `address` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `isActive` enum('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
  `hireDate` date NOT NULL,
  `createdAT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `schoolID` int NOT NULL,
  `dateOfBirth` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `teachers`
--

INSERT INTO `teachers` (`teacherID`, `user_id`, `first_name`, `middleName`, `last_name`, `gender`, `subject`, `qualification`, `phoneNumber`, `officeLocation`, `address`, `email`, `isActive`, `hireDate`, `createdAT`, `schoolID`, `dateOfBirth`) VALUES
(3, 41, 'Kembe', '', 'Kabimbi', '', 'Math', 'BS Computer', '14024722334', 'Bs 29', '', '', 'ACTIVE', '2025-12-18', '2025-12-19 00:11:34', 23, NULL),
(4, 63, 'Gedeon', 'Nyonganyi', 'Kembe', 'MALE', 'Math', 'BS Computer', '14025804849', 'Bs 29', '1609 D street apt 3', 'kembegeredeon@gmail.com', 'ACTIVE', '2026-06-07', '2026-06-08 00:48:11', 23, '1998-06-08'),
(5, 65, 'Jan jack', 'Nyonganyi', 'Kem', 'MALE', 'Math', 'BS Computer', '14025804849', 'Bs 29', '1609 D street apt 3', 'Janjack@gmail.com', 'ACTIVE', '2026-06-07', '2026-06-08 01:25:34', 23, '2000-04-08'),
(6, 66, 'Fred', '', 'Scote', 'MALE', 'Math', 'BS Computer', '+14025804849', 'Bs 29', '1609 D street apt 3', 'scote@gmail.com', 'ACTIVE', '2026-07-12', '2026-07-12 16:17:36', 23, '2001-07-12');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role` enum('ADMIN','TEACHER','STUDENT') NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `schoolID` int NOT NULL,
  `isActive` enum('ACTIVE','INACTIVE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'ACTIVE'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `role`, `email`, `phone`, `created_at`, `schoolID`, `isActive`) VALUES
(26, 'KembeF', '941f1cc4e79433524f57eab2d3f37068$4096$d791ef965f013cd7c2f67f5cf75e13b10c070df20ca1243e123d37b51c2398d1', 'ADMIN', 'raphaelkembe3@gmail.com', '14024722334', '2025-12-15 05:02:31', 20, 'ACTIVE'),
(27, 'KembeFFF', '8abf009fd37a01d5889e77fd2dcc8d35$4096$0ef48011803011b08a57df927d9274cdea097ccd136f47940b1875e04e0dd2f1', 'ADMIN', 'raphaelkembe33@gmail.com', '14024722334', '2025-12-15 05:11:50', 21, 'ACTIVE'),
(28, 'KembeGG', '4d29418bfec65ff574b4813006af2606$4096$248ac4c53375510486ff98d2bcd1d6b800954b974da1421103f2293f01abdc8d', 'ADMIN', 'raphaelkembe332@gmail.com', '14024722334', '2025-12-15 05:38:00', 22, 'ACTIVE'),
(29, 'Sagesse', '0e129bb4930862c38aa437b41cafef22$4096$f0f10cb9fcc39f465aa79bfbb3f2ac2e61ec94255746cb34bea58914e1589ec7', 'ADMIN', 'raphaelkembe1@gmail.com', '14024722334', '2025-12-18 18:20:26', 23, 'ACTIVE'),
(41, 'GedeonK', '495a82888a6c49883683c98b3fa48b48$4096$6587e4ec970e624bcbbd99c4390be6bae7ad7c46d7c1dfbcde2d10151540e6b8', 'TEACHER', 'kembe3@gmail.com', '14024722334', '2025-12-19 00:11:34', 23, 'ACTIVE'),
(48, 'GedeonKembe', '1adf4a48deed509bc8800a055f75abfd$4096$337b666290fd991b535a53925b9af311c406d13783162d26026071825e22b34f', 'STUDENT', 'kembe345@gmail.com', '+14024722334', '2025-12-21 03:01:01', 23, 'ACTIVE'),
(49, 'GedeonNYONGA', '879356775cb909a544f22bcf3c594f06$4096$3b4d933b2e294478bc8cc26fe111431e5cf20b1c7fc263603b4c707b37b2ac20', 'STUDENT', 'kembe34555@gmail.com', '+14024722334', '2025-12-31 03:11:30', 23, 'ACTIVE'),
(51, 'GDKEMBE', 'e2054e7cba2390a4d6b4a408c7768d93$4096$e63989209b9b448e91d3bcb945f4eca6a97b5105fa117d6e393b8005f65ea6c9', 'ADMIN', 'gedeonkembe33@gmail.com', '+14024722334', '2025-12-31 04:02:08', 23, 'ACTIVE'),
(52, 'GedeonKE', '24a5964121dd86ee5f483d6af9bcf296$4096$7c7def03830488110b4b7db7e44342c590a8c6e827e80b68f171810803f039ed', 'ADMIN', 'kembe3213@gmail.com', '14024722334', '2026-01-03 22:40:08', 23, 'ACTIVE'),
(53, 'GedeonKEm', '28c15d9414b8af774de5e766d28d43a8$4096$86e0bb6e86f41611cf4c6b9a1b98ba24d1ab413c4cbffe0261d217542932da3a', 'ADMIN', 'kembe32133@gmail.com', '14024722334', '2026-01-03 23:07:15', 23, 'ACTIVE'),
(54, 'GedeonG', 'd1e62f0d24983b17c76c605458f56ea2$4096$2fb1b68acf39108c0f409e642565201516edbd9bb7997177c12580f9fb03f6d3', 'ADMIN', 'gedeonG@gmail.com', '14024722334', '2026-01-03 23:10:57', 23, 'ACTIVE'),
(55, 'GedeonKW', '58e82cde91c18ace0f27bfc9aab07868$4096$f4393d5b86bdf2976c86f4dbfcbf6dea01638a5b0edd7234c5705a25da5f8c5b', 'ADMIN', 'gedeonGGG@gmail.com', '+14024722334', '2026-01-26 23:59:37', 23, 'ACTIVE'),
(57, 'Laborne', 'af7cf33b571b0ea681719608cb487374$4096$7dde336c17635a182c0669cc09911b93d7119a0a9544b1548b4e9aba24acc0f0', 'ADMIN', 'laborneadmin@gmail.com', '14024722334', '2026-02-04 02:37:18', 24, 'ACTIVE'),
(58, 'Labonte', 'd14c77c1bc65abd1d106731f76ac82b7$4096$49972f312efe41a314dc738aedc7adbcea3d14208463ff168b1dd88c5684607d', 'ADMIN', 'labonteadmin@gmail.com', '14024722334', '2026-02-04 03:22:34', 25, 'ACTIVE'),
(59, 'Rkembe', 'cc12e3fffe13e398f8db0f2d99778bdb$4096$82abf49495ddd7cbd2767e90ef94568c488b7da08ceae6bbff196594af0b7052', 'STUDENT', 'robertkembe3@gmail.com', '+14024722334', '2026-03-08 21:57:47', 23, 'ACTIVE'),
(60, 'EKamba', 'a42d04a0881d82d00fd4da7130c37c95$4096$db8ea5f20307227d0f770e1cfc7bb8645aad040203552de5bd21d7fb2f7f162a', 'STUDENT', 'eliekamba333333@gmail.com', '+14024722334', '2026-03-09 17:29:07', 23, 'ACTIVE'),
(63, 'Kembere', '0c85bb352dd015916406dd4b22dddd62$4096$1d75551c08232026e0b2b27ff02aa4c68ab9ee603b2db72705b0d58cae561ef2', 'TEACHER', 'kembegeredeon@gmail.com', '14025804849', '2026-06-08 00:48:11', 23, 'ACTIVE'),
(65, 'Jkembe', 'a675ed31b6360f46e625b69e6721ad46$4096$476a4c77445b6b2dd03f823633dc361dfd0eb9d5c0f989bacfea1e5db6097462', 'TEACHER', 'Janjack@gmail.com', '14025804849', '2026-06-08 01:25:34', 23, 'ACTIVE'),
(66, 'FScote', '5508c9d02a6ac35cbebdc593094e5269$4096$8f6b102dc287a8e92227d751956eb040cf05c8a56e1a583a81967c3d5da68bc1', 'TEACHER', 'scote@gmail.com', '+14025804849', '2026-07-12 16:17:36', 23, 'ACTIVE');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`courseID`),
  ADD KEY `schoolID` (`schoolID`);

--
-- Indexes for table `departments`
--
ALTER TABLE `departments`
  ADD PRIMARY KEY (`departmentID`),
  ADD KEY `school_options_fk` (`schoolID`);

--
-- Indexes for table `levelCourse`
--
ALTER TABLE `levelCourse`
  ADD PRIMARY KEY (`levelCourseID`),
  ADD KEY `levelID` (`levelID`),
  ADD KEY `courseID` (`courseID`);

--
-- Indexes for table `levels`
--
ALTER TABLE `levels`
  ADD PRIMARY KEY (`levelID`),
  ADD UNIQUE KEY `school_level_idx` (`schoolID`,`departmentID`,`levelName`) USING BTREE,
  ADD KEY `departmentID` (`departmentID`);

--
-- Indexes for table `school`
--
ALTER TABLE `school`
  ADD PRIMARY KEY (`schoolID`);

--
-- Indexes for table `schoolCycle`
--
ALTER TABLE `schoolCycle`
  ADD PRIMARY KEY (`schoolCycleID`),
  ADD KEY `school_cycle_fk` (`schoolID`);

--
-- Indexes for table `studentGuardians`
--
ALTER TABLE `studentGuardians`
  ADD PRIMARY KEY (`guardianID`),
  ADD KEY `fk_student_studentGardians` (`studentID`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`studentID`),
  ADD UNIQUE KEY `registrationNumber` (`registrationNumber`),
  ADD KEY `fk_School_Students` (`schoolID`),
  ADD KEY `fk_Users_Students` (`userID`),
  ADD KEY `levelID` (`levelID`);

--
-- Indexes for table `teacherAssignments`
--
ALTER TABLE `teacherAssignments`
  ADD PRIMARY KEY (`assignmentID`),
  ADD UNIQUE KEY `uk_school_level_course` (`schoolID`,`levelID`,`courseID`),
  ADD KEY `idx_ta_teacher` (`teacherID`),
  ADD KEY `idx_ta_level` (`levelID`),
  ADD KEY `idx_ta_course` (`courseID`);

--
-- Indexes for table `teachers`
--
ALTER TABLE `teachers`
  ADD PRIMARY KEY (`teacherID`),
  ADD KEY `fk_teacher_user` (`user_id`),
  ADD KEY `fk_school_teachers` (`schoolID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `schoolID` (`schoolID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `courses`
--
ALTER TABLE `courses`
  MODIFY `courseID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `departments`
--
ALTER TABLE `departments`
  MODIFY `departmentID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `levelCourse`
--
ALTER TABLE `levelCourse`
  MODIFY `levelCourseID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `levels`
--
ALTER TABLE `levels`
  MODIFY `levelID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `school`
--
ALTER TABLE `school`
  MODIFY `schoolID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `schoolCycle`
--
ALTER TABLE `schoolCycle`
  MODIFY `schoolCycleID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `studentGuardians`
--
ALTER TABLE `studentGuardians`
  MODIFY `guardianID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
  MODIFY `studentID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `teacherAssignments`
--
ALTER TABLE `teacherAssignments`
  MODIFY `assignmentID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `teachers`
--
ALTER TABLE `teachers`
  MODIFY `teacherID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=67;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `courses`
--
ALTER TABLE `courses`
  ADD CONSTRAINT `courses_ibfk_1` FOREIGN KEY (`schoolID`) REFERENCES `school` (`schoolID`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `departments`
--
ALTER TABLE `departments`
  ADD CONSTRAINT `school_options_fk` FOREIGN KEY (`schoolID`) REFERENCES `school` (`schoolID`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `levelCourse`
--
ALTER TABLE `levelCourse`
  ADD CONSTRAINT `levelcourse_ibfk_1` FOREIGN KEY (`levelID`) REFERENCES `levels` (`levelID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `levelcourse_ibfk_2` FOREIGN KEY (`courseID`) REFERENCES `courses` (`courseID`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `levels`
--
ALTER TABLE `levels`
  ADD CONSTRAINT `levels_ibfk_1` FOREIGN KEY (`schoolID`) REFERENCES `school` (`schoolID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `levels_ibfk_2` FOREIGN KEY (`departmentID`) REFERENCES `departments` (`departmentID`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `schoolCycle`
--
ALTER TABLE `schoolCycle`
  ADD CONSTRAINT `school_cycle_fk` FOREIGN KEY (`schoolID`) REFERENCES `school` (`schoolID`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `studentGuardians`
--
ALTER TABLE `studentGuardians`
  ADD CONSTRAINT `fk_student_studentGardians` FOREIGN KEY (`studentID`) REFERENCES `students` (`studentID`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `students`
--
ALTER TABLE `students`
  ADD CONSTRAINT `students_ibfk_1` FOREIGN KEY (`schoolID`) REFERENCES `school` (`schoolID`),
  ADD CONSTRAINT `students_ibfk_2` FOREIGN KEY (`userID`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `students_ibfk_3` FOREIGN KEY (`levelID`) REFERENCES `levels` (`levelID`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `teacherAssignments`
--
ALTER TABLE `teacherAssignments`
  ADD CONSTRAINT `fk_ta_course` FOREIGN KEY (`courseID`) REFERENCES `courses` (`courseID`),
  ADD CONSTRAINT `fk_ta_level` FOREIGN KEY (`levelID`) REFERENCES `levels` (`levelID`),
  ADD CONSTRAINT `fk_ta_teacher` FOREIGN KEY (`teacherID`) REFERENCES `teachers` (`teacherID`);

--
-- Constraints for table `teachers`
--
ALTER TABLE `teachers`
  ADD CONSTRAINT `fk_school_teachers` FOREIGN KEY (`schoolID`) REFERENCES `school` (`schoolID`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`schoolID`) REFERENCES `school` (`schoolID`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
