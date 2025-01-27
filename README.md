# RentalsApp Backend

Ce projet représente la partie backend de l'application **RentalsApp**, une plateforme de mise en relation entre futurs locataires et propriétaires.

## **Fonctionnalités principales**
- Gestion sécurisée des utilisateurs et des propriétés grâce à **Spring Security** et **OAuth2**.
- API REST pour les opérations principales de l'application.
- Documentation des APIs avec **Swagger**.

---

## **Lancement du projet**

### **Prérequis**
- Java 11 ou supérieur
- Maven
- Node.js et Angular CLI (version 14 ou ultérieure)

### **Étapes pour lancer le projet**

1. **Cloner le dépôt** :
   git clone git@github.com:ilyascherfi/chatop2.git
2. **Configurer la base de données**:
   - Installer MySQL Workbench
   - Créer une connexion:
        - Ouvrez MySQL Workbench.
        - Cliquez sur Database > Connect to Database.
        - Configurez les paramètres suivants :
              - Hostname : 127.0.0.1
              - Port : 3306
              - Username : root
              - Password : root
        - Cliquez sur OK pour établir la connexion.
   - Créer la base de données :
        - Exécutez la commande suivante pour créer la base de données : CREATE DATABASE rentals_db;
        - Actualisez la liste des schémas pour vérifier que la base de données rentals_db a été créée.
4. **Lancer le backend** :
   mvn spring-boot:run
5. **Lancer le frontend** :
   ng serve

---

## **Documentation API**

La documentation des APIs est accessible via Swagger : http://localhost:3001/swagger-ui/index.html

### **Étapes pour s'authentifier et utiliser les APIs**

1. **Créer un utilisateur** :
   - Allez dans l'endpoint POST /api/auth/register via Swagger.
   - Cliquez sur Try it out, configurez le Request Body, et cliquez sur Execute.
   - Vous recevrez un token dans la réponse.

2. **Ajouter le token** :
   - Cliquez sur Authorize dans Swagger.
   - Collez le token reçu précédemment pour accéder aux autres APIs.
