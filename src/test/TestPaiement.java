/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
import entities.Abonne;
import entities.Abonnement;
import entities.Paiement;
import services.AbonneService;
import services.AbonnementService;
import services.PaiementService;
import java.time.LocalDate;
/**
 *
 * @author pc
 */
public class TestPaiement {
    
   public static void main(String[] args) {

        AbonneService abService = new AbonneService();
        AbonnementService aboService = new AbonnementService();
        PaiementService service = new PaiementService();

        // Récupération des dépendances
        Abonne ab = abService.findById(7);
        Abonnement abo = aboService.findById(7);

        if (ab == null || abo == null) {
            System.out.println("Tu dois d'abord exécuter TestAbonne puis TestAbonnement !");
            return;
        }

        // Insertion simple
        Paiement p = new Paiement(ab, abo, LocalDate.now(), abo.getPrix());
        service.create(p);

        // Affichage depuis BD
        System.out.println("Paiements dans la base :");
        for (Paiement pa : service.findAll()) {
            System.out.println(pa);
        }
    }
}