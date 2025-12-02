/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author pc
 */
public class Abonnement {
    private int id;          
    private Abonne abonne;    
    private String type;      
    private int duree;        
    private double prix;
    
    public Abonnement() {
}
    
     // Constructeur complet
    public Abonnement(int id, Abonne abonne, String type, int duree, double prix) {
        this.id = id;
        this.abonne = abonne;
        this.type = type;
        this.duree = duree;
        this.prix = prix;
    }

    // Constructeur sans id (avant insertion en BD)
    public Abonnement(Abonne abonne, String type, int duree, double prix) {
        this.abonne = abonne;
        this.type = type;
        this.duree = duree;
        this.prix = prix;
    }

    // Getters
    public int getId() {
        return id;
    }

    public Abonne getAbonne() {
        return abonne;
    }

    public String getType() {
        return type;
    }

    public int getDuree() {
        return duree;
    }

    public double getPrix() {
        return prix;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setAbonne(Abonne abonne) {
        this.abonne = abonne;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    // toString
    @Override
    public String toString() {
        return "Abonnement{" +
                "id=" + id +
                ", abonne=" + (abonne != null ? abonne.getNom() : "null") +
                ", type='" + type + '\'' +
                ", duree=" + duree +
                ", prix=" + prix +
                '}';
    }
}


    