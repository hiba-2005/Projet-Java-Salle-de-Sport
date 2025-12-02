package test;

import entities.Abonne;
import services.AbonneService;

public class TestAbonne {
    public static void main(String[] args) {

        AbonneService service = new AbonneService();

        // Insertion simple
        Abonne a = new Abonne("ouirouane", "hajar", 20, "femme");
        service.create(a);

         // 3) FIND BY ID
         Abonne abonneTrouve = service.findById(2);
        if (abonneTrouve != null) {
            System.out.println("Trouvé : " + abonneTrouve);
        } else {
            System.out.println("Aucun abonné avec ID = 1");
        }
         // 4) UPDATE
        
        if (abonneTrouve != null) {
            abonneTrouve.setNom("OUIROUANE");   // changement de nom
            abonneTrouve.setPrenom("HIBA");
            abonneTrouve.setAge(25);       // changement d’âge
        }
            service.update(abonneTrouve);
        // 5) DELETE
             Abonne aSupprimer = service.findById(10);
        if (aSupprimer != null) {
            service.delete(aSupprimer);
        }
        // ******* TEST : findByAgeBetween**********
System.out.println("\n******* Test findByAgeBetween(21, 30) *******");
        for (Abonne ab : service.findByAgeBetween(21, 30)) {
            System.out.println(ab);
}
// Affichage depuis BD
        System.out.println("Abonnés dans la base :");
        for (Abonne ab : service.findAll()) {
            System.out.println(ab);
        }
    }
}
