-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Host: db
-- Generation Time: Jan 14, 2026 at 03:29 AM
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
-- Table structure for table `classes`
--

CREATE TABLE `classes` (
  `id` int NOT NULL,
  `className` varchar(50) NOT NULL,
  `classLevel` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
  `schoolLevel` varchar(30) NOT NULL,
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

INSERT INTO `school` (`schoolID`, `schoolName`, `shortName`, `registrationNumber`, `schoolType`, `schoolLevel`, `country`, `city`, `schoolAddress`, `schoolEmail`, `website`, `schoolLogo`, `isActive`, `createdAt`) VALUES
(20, 'GEDE', 'GKLFG2', 'GL002', 'PUBLIC', 'PRIMARY', 'Bangladesh', 'Lincoln', '5, av de la science', 'kembe3@gmail.com', 'https://weber.com', NULL, 1, '2025-12-15 05:02:31'),
(21, 'GEDE', 'GKLFG2', 'GL002', 'PUBLIC', 'PRIMARY', 'Bangladesh', 'Lincoln', '5, av de la science', 'kembe3@gmail.com', 'https://weber.com', 'school_1765777080003.JPG\n', 1, '2025-12-15 05:11:50'),
(22, 'Gede', 'GKLFG', 'GHHH2', 'PRIVATE', 'PRIMARY', 'Albania', 'Lincoln', '5, av de la science', 'kembe3@gmail.com', 'https://weber.com', 'school_1765777080003.JPG', 1, '2025-12-15 05:38:00'),
(23, 'CS Les Genies', 'SGS', 'FRTYT3423', 'PRIVATE', 'HIGH', 'Democratic Republic of the Congo', 'Kinshasa', '5, av de la science', 'kembe1@gmail.com', 'https://weber.com', 'school_23_1767581100334.png', 1, '2025-12-18 18:20:26');

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
  `lastName` varchar(100) NOT NULL,
  `gender` enum('MALE','FEMALE','OTHER') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `dateOfBirth` date DEFAULT NULL,
  `gradeLevel` varchar(50) DEFAULT NULL,
  `department` varchar(100) DEFAULT NULL,
  `enrollmentDate` date DEFAULT NULL,
  `academicYear` varchar(20) DEFAULT NULL,
  `phone` varchar(30) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `isActive` enum('ACTIVE','INACTIVE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'ACTIVE',
  `createdAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`studentID`, `schoolID`, `userID`, `registrationNumber`, `firstName`, `lastName`, `gender`, `dateOfBirth`, `gradeLevel`, `department`, `enrollmentDate`, `academicYear`, `phone`, `address`, `isActive`, `createdAt`, `updatedAt`) VALUES
(7, 23, 48, 'GK78785', 'Kembe', 'Kabimbi', 'MALE', '1999-02-20', 'Freshman', 'CyBersecurity', '2025-12-20', '2025-2026', '+14024722334', '5, av de la science', 'ACTIVE', '2025-12-21 03:01:01', '2026-01-02 05:29:21'),
(8, 23, 49, 'GK78782', 'Kembe', 'Kabimbi', 'MALE', '1999-02-20', 'Freshman', 'CyBersecurity', '2025-12-20', '2025-2026', '+14024722334', '5, av de la science', 'ACTIVE', '2025-12-31 03:11:30', '2025-12-31 05:35:12');

-- --------------------------------------------------------

--
-- Table structure for table `teachers`
--

CREATE TABLE `teachers` (
  `teacherID` int NOT NULL,
  `user_id` int NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `subject` varchar(100) DEFAULT NULL,
  `qualification` varchar(100) DEFAULT NULL,
  `phoneNumber` varchar(20) NOT NULL,
  `officeLocation` varchar(50) NOT NULL,
  `isActive` enum('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
  `hireDate` date NOT NULL,
  `createdAT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `schoolID` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `teachers`
--

INSERT INTO `teachers` (`teacherID`, `user_id`, `first_name`, `last_name`, `subject`, `qualification`, `phoneNumber`, `officeLocation`, `isActive`, `hireDate`, `createdAT`, `schoolID`) VALUES
(3, 41, 'Kembe', 'Kabimbi', 'Math', 'BS Computer', '14024722334', 'Bs 29', 'ACTIVE', '2025-12-18', '2025-12-19 00:11:34', 23);

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
(54, 'GedeonG', 'd1e62f0d24983b17c76c605458f56ea2$4096$2fb1b68acf39108c0f409e642565201516edbd9bb7997177c12580f9fb03f6d3', 'ADMIN', 'gedeonG@gmail.com', '14024722334', '2026-01-03 23:10:57', 23, 'ACTIVE');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `classes`
--
ALTER TABLE `classes`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `school`
--
ALTER TABLE `school`
  ADD PRIMARY KEY (`schoolID`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`studentID`),
  ADD UNIQUE KEY `registrationNumber` (`registrationNumber`),
  ADD KEY `fk_School_Students` (`schoolID`),
  ADD KEY `fk_Users_Students` (`userID`);

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
-- AUTO_INCREMENT for table `classes`
--
ALTER TABLE `classes`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `school`
--
ALTER TABLE `school`
  MODIFY `schoolID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
  MODIFY `studentID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `teachers`
--
ALTER TABLE `teachers`
  MODIFY `teacherID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `students`
--
ALTER TABLE `students`
  ADD CONSTRAINT `students_ibfk_1` FOREIGN KEY (`schoolID`) REFERENCES `school` (`schoolID`),
  ADD CONSTRAINT `students_ibfk_2` FOREIGN KEY (`userID`) REFERENCES `users` (`id`);

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
