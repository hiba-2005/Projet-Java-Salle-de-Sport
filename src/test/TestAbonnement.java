/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import entities.Abonne;
import entities.Abonnement;
import services.AbonneService;
import services.AbonnementService;
import java.util.List;

/**
 *
 * @author pc
 */
public class TestAbonnement {
     public static void main(String[] args) {

        AbonneService abService = new AbonneService();
        AbonnementService aboService = new AbonnementService();

          
        // Récupérer ou créer un abonné
     
        Abonne ab = abService.findById(7);   // tu peux changer l'ID

        if (ab == null) {
            System.out.println("Aucun abonné avec id = 7, création d'un abonné test...");
            ab = new Abonne("Test", "Abonne", 25, "Homme");
            abService.create(ab);

            // On récupère un abonné (le dernier inséré par ex.)
            List<Abonne> abonnes = abService.findAll();
            if (abonnes.isEmpty()) {
                System.out.println("Impossible de créer un abonné, arrêt du test.");
                return;
            }
            ab = abonnes.get(abonnes.size() - 1);
        }

        System.out.println("Abonné utilisé pour les tests : " + ab);
/*
        // 1) CREATE : créer des abonnements
      
        System.out.println("\n=== Test create() ===");
        Abonnement abo1 = new Abonnement(ab, "Mensuel", 1, 150);
        Abonnement abo2 = new Abonnement(ab, "Annuel", 12, 1200);

        aboService.create(abo1);
        aboService.create(abo2);
        */
        
  //  FIND ALL : afficher tous les abonnements

        System.out.println("\n=== Test findAll() ===");
        List<Abonnement> tous = aboService.findAll();
        for (Abonnement a : tous) {
            System.out.println(a);
        }

        if (tous.isEmpty()) {
            System.out.println("Aucun abonnement trouvé, arrêt du test.");
            return;
        }
        // On prend le premier abonnement pour tester findById / update / delete
        Abonnement premier = tous.get(0);

        
        // 3) FIND BY ID
     
        System.out.println("\n=== Test findById() ===");
        Abonnement trouve = aboService.findById(premier.getId());
        System.out.println("Abonnement trouvé par ID : " + trouve);
        /*
          // *4) UPDATE
        // -----------------------------------------
        System.out.println("\n=== Test update() ===");
        if (trouve != null) {
            double ancienPrix = trouve.getPrix();
            trouve.setPrix(ancienPrix + 50);      // on augmente le prix
            trouve.setType("Mensuel_Modif");      // on change le type
            aboService.update(trouve);

            Abonnement apresUpdate = aboService.findById(trouve.getId());
            System.out.println("Après update : " + apresUpdate);
        }
        */
        // 5) DELETE
        
        System.out.println("\n=== Test delete() ===");
        if (trouve != null) {
            aboService.delete(trouve);
        }

        System.out.println("Liste des abonnements après delete() :");
        for (Abonnement a : aboService.findAll()) {
            System.out.println(a);
        }
        
        // 6) TEST : findByAbonne (abonneId)

        System.out.println("\n=== Test findByAbonne(abonneId) ===");
        int idAbonne = ab.getId();   // l'abonné utilisé plus haut
        for (Abonnement ab1 : aboService.findByAbonne(idAbonne)) {
            System.out.println(ab1);
        }

        
        // 7) TEST : findByType
       
        System.out.println("\n=== Test findByType(\"Mensuel\") ===");
        for (Abonnement ab1 : aboService.findByType("Mensuel")) {
            System.out.println(ab1);
        }

        
        // 8) TEST : findImpayes
      
        System.out.println("\n=== Test findImpayes() ===");
        for (Abonnement ab1 : aboService.findImpayes()) {
            System.out.println(ab1);
        }

        System.out.println("\n=== FIN TestAbonnement ===");
    }
}


       

