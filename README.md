# 🏋️ Salle de Sport 
## LOGO

<div align="center"> <img src="src/image/HO4.jpg" alt="Résultat Exercice 1" width="600"/> <p><em>Figure 1</em></p> </div>
---

# 📁 Table de Matières
- 🗂 [Contexte](#-contexte)
- ❓ [Problématique](#-problématique)
- 🎯 [Objectif](#-objectif)
- 📊 [Diagrammes](#-diagrammes)
- 🗃 [Tables de Données](#-tables-de-données)
- ✨ [Fonctionnalités Principales](#-fonctionnalités-principales)
- 🔍 [Requêtes SQL](#-requêtes-sql)
- 🏛 [Architecture](#-architecture)
- 🛠 [Technologies Utilisées](#-technologies-utilisées)
- 🎥 [Démo Vidéo](#-démo-vidéo)
- 👤 Auteur

---

# 🗂 Contexte
L’application **Salle de Sport +2** est une solution complète de gestion destinée aux salles de sport.  
Elle permet de centraliser et d’automatiser :

- La gestion des abonnés  
- Les types d'abonnements  
- Les paiements  
- Les filtrages avancés  
- Les statistiques graphiques (revenus mensuels)  
- L’authentification sécurisée avec récupération de mot de passe par email  

---

# ❓ Problématique
Les salles de sport gèrent souvent leurs activités manuellement (papier ou Excel), ce qui entraîne :

- Des erreurs humaines  
- Une perte de temps  
- Des difficultés pour retrouver l’historique  
- Aucun suivi statistique  
- Aucune automatisation des rappels / paiements  

---

# 🎯 Objectif
Développer une **application efficace, moderne et automatisée** permettant :

- Une gestion complète des membres  
- Un suivi des paiements et abonnements  
- Un filtrage rapide des données  
- Un graphique des revenus mensuels  
- Une interface utilisateur claire et intuitive  
- Une installation facile (fichier .exe via Inno Setup)

---
# 📊 Diagrammes :
Diagramme de classe :
![WhatsApp Image 2025-12-02 à 21 37 28_aa776007](https://github.com/user-attachments/assets/c2178d60-20cb-4355-bc90-f131ea4ea665)

------
# 🗃 Tables de Données 

### 👤 Abonne
Abonne (id, nom, prenom, age, sexe)

### 🏷 Abonnement
Abonnement (id, id_abonne, type, duree, prix)

### 💰 Paiement
Paiement (id, id_abonne, datePaiement, montant)

### 🔐 User
User (id, login, password, email)

-------
# ✨ Fonctionnalités Principales

### 🔐 **Authentification + Email**
- Connexion sécurisée  
- Réinitialisation du mot de passe  
- Envoi d’un nouveau mot de passe par email via JavaMail  

### 🧍‍♂️ **Gestion des Abonnés**
- Ajouter / modifier / supprimer  
- Recherche par nom / prénom  

### 🏷 **Gestion des Abonnements**
- Types : Mensuel / Trimestriel / Annuel  
- Prix + durée  
- Lien avec l'abonné sélectionné  

### 💰 **Gestion des Paiements**
- Saisie de la date  
- Montant du paiement  
- Historique détaillé  

### 🔎 **Filtrages**
- Par tranche d’âge  
- Par type d'abonnement  

### 📈 **Graphique JFreeChart**
- Revenus mensuels  
- Calcul automatique du total par mois  
- Affichage en barres  

### 💻 **Installation Windows (Inno Setup)**
- Création d’un `.exe`  
- Icône sur le Bureau  
- Lancement automatique  
- Fonctionnement hors NetBeans

---

# 🔍 Requêtes SQL
````
CREATE TABLE abonne (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    sexe VARCHAR(10) NOT NULL
);

CREATE TABLE abonnement (
    id INT AUTO_INCREMENT PRIMARY KEY,
    abonneId INT NOT NULL,
    type VARCHAR(50) NOT NULL,
    duree INT NOT NULL,
    prix DOUBLE NOT NULL,
    FOREIGN KEY (abonneId) REFERENCES abonne(id) ON DELETE CASCADE
);

CREATE TABLE paiement (
    id INT AUTO_INCREMENT PRIMARY KEY,
    abonneId INT NOT NULL,
    abonnementId INT NOT NULL,
    datePaiement DATE NOT NULL,
    montant DOUBLE NOT NULL,
    FOREIGN KEY (abonneId) REFERENCES abonne(id) ON DELETE CASCADE,
    FOREIGN KEY (abonnementId) REFERENCES abonnement(id) ON DELETE CASCADE
);

CREATE TABLE user (
    login VARCHAR(100) PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL
);
````
----
# 🏛 Architecture
````
projet/
│
├── src/
│   ├── connexion/
│   ├── dao/
│   ├── services/
│   ├── entities/
│   ├── ui/
│   ├──Test/
│   └── image/
│
├── salle_sport.sql
├── video/
├── dist/
└── setup/
````
-----
# 🛠 Technologies Utilisées:

- **Framework d'interface graphique :** Java Swing
- **Base de données :** MySQL
- **Accès aux données :** JDBC
- **Outils de développement :**
NetBeans (IDE Java)
- **Gestion de base de données :** phpMyAdmin
- **Bibliothèque d'icônes :** Icons8

-----
# 🎥 Démo Vidéo


https://github.com/hiba-2005/Projet-Java-Salle-de-Sport/releases/download/v2.0/Screen.Recording.2.mp4
----

# 👤 Auteur

Hiba Ouirouane – 2025
Développement Java | Interfaces Swing | MySQL | JFreeChart | Inno Setup
