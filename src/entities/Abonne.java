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
public class Abonne {
    
    private int id;
    private String nom;
    private String prenom;
    private int age;
    private String sexe; // "Homme", "Femme", etc.

    
    public Abonne(int id, String nom, String prenom, int age, String sexe) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.sexe = sexe;
    }

    public Abonne() {
} 
    public Abonne(String nom, String prenom, int age, String sexe) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.sexe = sexe;
    }

    

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public int getAge() {
        return age;
    }

    public String getSexe() {
        return sexe;
    }

    public String getPrenom() {
        return prenom;
    }
    
    
  
    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public String toString() {
        return "Abonne{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", age=" + age + ", sexe=" + sexe + '}';
    }
    
    

   

    
    
   
}
