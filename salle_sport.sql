-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mar. 02 déc. 2025 à 11:37
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `salle_sport`
--

-- --------------------------------------------------------

--
-- Structure de la table `abonne`
--

CREATE TABLE `abonne` (
  `id` int(11) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  `age` int(11) NOT NULL,
  `sexe` enum('Homme','Femme') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `abonne`
--

INSERT INTO `abonne` (`id`, `nom`, `prenom`, `age`, `sexe`) VALUES
(2, 'OUIROUANE', 'HIBA', 25, 'Homme'),
(3, 'Fatima', 'Zohra', 52, 'Femme'),
(4, 'Ali', 'Amine', 25, 'Homme'),
(5, 'Ali', 'Amine', 25, 'Homme'),
(6, 'Sara', 'Lamia', 22, 'Femme'),
(7, 'ouirouane', 'hiba', 20, 'Femme'),
(8, 'ouirouane', 'hajar', 20, 'Femme'),
(9, 'ouirouane', 'hajar', 20, 'Femme'),
(11, 'ouirouane', 'hajar', 20, 'Femme'),
(12, 'ouirouane', 'hajar', 20, 'Femme'),
(13, 'ouirouane', 'hajar', 20, 'Femme'),
(14, 'ouirouane', 'hajar', 20, 'Femme'),
(16, 'ouirouane', 'rodayna', 22, 'Femme'),
(18, 'hibkljhRGR', 'hgQEFE', 45, 'Femme'),
(20, 'ME', 'ME', 15, 'Femme'),
(21, 'cc', 'cc', 37, 'Homme');

-- --------------------------------------------------------

--
-- Structure de la table `abonnement`
--

CREATE TABLE `abonnement` (
  `id` int(11) NOT NULL,
  `abonneId` int(11) NOT NULL,
  `type` varchar(50) NOT NULL,
  `duree` int(11) NOT NULL,
  `prix` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `abonnement`
--

INSERT INTO `abonnement` (`id`, `abonneId`, `type`, `duree`, `prix`) VALUES
(4, 7, 'Mensuel', 1, 150),
(5, 7, 'Mensuel', 1, 150),
(6, 7, 'Mensuel', 1, 150),
(7, 7, 'Mensuel', 1, 150),
(8, 7, 'Annuel', 12, 1200),
(9, 7, 'Mensuel', 1, 150),
(10, 7, 'Annuel', 12, 1200),
(11, 7, 'Mensuel', 1, 150),
(12, 7, 'Annuel', 12, 1200),
(13, 7, 'Mensuel', 1, 150),
(14, 7, 'Annuel', 12, 1200),
(15, 7, 'Mensuel', 1, 150),
(16, 7, 'Annuel', 12, 1200),
(18, 20, 'Annuel ', 12, 5000),
(19, 21, 'Trimestriel', 3, 500);

-- --------------------------------------------------------

--
-- Structure de la table `paiement`
--

CREATE TABLE `paiement` (
  `id` int(11) NOT NULL,
  `abonneId` int(11) NOT NULL,
  `abonnementId` int(11) NOT NULL,
  `datePaiement` date NOT NULL,
  `montant` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `paiement`
--

INSERT INTO `paiement` (`id`, `abonneId`, `abonnementId`, `datePaiement`, `montant`) VALUES
(4, 7, 4, '2025-11-14', 150),
(5, 7, 4, '2025-11-14', 150),
(7, 20, 18, '2025-12-01', 1000),
(8, 7, 4, '2025-09-01', 356),
(9, 21, 19, '2025-07-08', 500);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `login` varchar(50) NOT NULL,
  `password` char(40) NOT NULL,
  `securityQuestion` varchar(255) NOT NULL,
  `securityAnswer` char(40) NOT NULL,
  `email` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`login`, `password`, `securityQuestion`, `securityAnswer`, `email`) VALUES
('hiba', 'c69862ad84308d0b295779748776362e5d2e5ebb', 'Quel est le nom de votre ville préférée ?', 'eb732649f9e2327bb6f534be4b9ba085b3250ac9', 'hiba.ouirouane.05@gmail.com');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `abonne`
--
ALTER TABLE `abonne`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `abonnement`
--
ALTER TABLE `abonnement`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_abonnement_abonne` (`abonneId`);

--
-- Index pour la table `paiement`
--
ALTER TABLE `paiement`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_paiement_abonne` (`abonneId`),
  ADD KEY `fk_paiement_abonnement` (`abonnementId`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`login`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `abonne`
--
ALTER TABLE `abonne`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT pour la table `abonnement`
--
ALTER TABLE `abonnement`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT pour la table `paiement`
--
ALTER TABLE `paiement`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `abonnement`
--
ALTER TABLE `abonnement`
  ADD CONSTRAINT `fk_abonnement_abonne` FOREIGN KEY (`abonneId`) REFERENCES `abonne` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `paiement`
--
ALTER TABLE `paiement`
  ADD CONSTRAINT `fk_paiement_abonne` FOREIGN KEY (`abonneId`) REFERENCES `abonne` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_paiement_abonnement` FOREIGN KEY (`abonnementId`) REFERENCES `abonnement` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
