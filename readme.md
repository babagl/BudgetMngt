# Budget Management Application

Une application de gestion de budget comprenant un **back-end** développé en Java 17 avec Spring Boot et un **front-end** développé en React 18.

---

## Prérequis

Avant de commencer, assurez-vous d'avoir les outils suivants installés sur votre machine :
- **Java 17** : Pour exécuter le back-end
- **Maven** : Pour la gestion des dépendances du projet Java
- **Node.js** (version 18 ou supérieure) : Pour exécuter le front-end
- **IntelliJ IDEA** (de préférence) : Pour travailler sur le projet back-end
- **Un navigateur moderne** : Pour tester l'application front-end

---

## Back-end

### Installation et Lancement

1. **Cloner le dépôt** :
   ```bash
    git clone https://github.com/babagl/BudgetMngt.git
    cd BudgetMgn
    ```
    
    Ouvrir le projet avec IntelliJ IDEA :

- Importez le projet comme un projet Maven.
- IntelliJ téléchargera automatiquement les dépendances nécessaires.

 **Lancer l'application :**

- Exécutez la classe principale contenant l'annotation 
    ```java
    public class BudgetMgnApplication {

        public static void main(String[] args) {
            SpringApplication.run(BudgetMgnApplication.class, args);
        }

    }
    ```
- Base de données H2 : Aucune configuration externe n'est requise pour la base de données, car le projet utilise H2 en mémoire. Voici les informations de configuration :

    ```propreties

        spring.application.name=BudgetMgn
        spring.datasource.url=jdbc:h2:mem:DbBudget
        spring.datasource.username=sa
        spring.datasource.password=
        spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
        spring.h2.console.enabled=true
        spring.jpa.hibernate.ddl-auto=create-drop
        spring.jpa.show-sql=true

        server.port=8888
    ```

- Port exposé : http://localhost:8888
- Console H2 : Accessible via http://localhost:8888/h2-console
    - JDBC URL : jdbc:h2:mem:DbBudget
    - Nom d'utilisateur : sa
    - Mot de passe : (laissez vide)

## Front-end : 
 **Lancer l'application :**

```bash
     git clone https://github.com/babagl/BudgetMngt.git
     cd budget-mgn-front
     npm i
     npm run dev

```
 **Lancer l'application :**
 - L'application front-end sera disponible par défaut sur http://localhost:5173.
