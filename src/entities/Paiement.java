/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;
import java.time.LocalDate;
/**
 *
 * @author pc
 */
public class Paiement {
    
    private int id;                
    private Abonne abonne;           
    private Abonnement abonnement;  
    private LocalDate datePaiement;
    private double montant;

    public Paiement() {
}
    public Paiement(int id, Abonne abonne, Abonnement abonnement, LocalDate datePaiement, double montant) {
        this.id = id;
        this.abonne = abonne;
        this.abonnement = abonnement;
        this.datePaiement = datePaiement;
        this.montant = montant;
    }

    public Paiement(Abonne abonne, Abonnement abonnement, LocalDate datePaiement, double montant) {
        this.abonne = abonne;
        this.abonnement = abonnement;
        this.datePaiement = datePaiement;
        this.montant = montant;
    }
    
    

    public int getId() {
        return id;
    }

    public Abonne getAbonne() {
        return abonne;
    }

    public Abonnement getAbonnement() {
        return abonnement;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public double getMontant() {
        return montant;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAbonne(Abonne abonne) {
        this.abonne = abonne;
    }

    public void setAbonnement(Abonnement abonnement) {
        this.abonnement = abonnement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    @Override
    public String toString() {
        return "Paiement{" + "id=" + id + ", abonne=" + abonne + ", abonnement=" + abonnement + ", datePaiement=" + datePaiement + ", montant=" + montant + '}';
    }
    
    
}
