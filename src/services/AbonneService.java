/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.IDao;
import entities.Abonne;
import connexion.Connexion;  
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pc
 */
public class AbonneService implements IDao<Abonne> {

    @Override
    public boolean create(Abonne o) {
        String req = "INSERT INTO abonne (nom, prenom, age, sexe) VALUES ('"
                + o.getNom() + "', '"
                + o.getPrenom() + "', "
                + o.getAge() + ", '"
                + o.getSexe() + "')";
        try {
            Statement st = connexion.Connexion.getConnection().createStatement();
            st.executeUpdate(req);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AbonneService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean update(Abonne o) {
        String req = "UPDATE abonne SET "
                + "nom = '" + o.getNom() + "', "
                + "prenom = '" + o.getPrenom() + "', "
                + "age = " + o.getAge() + ", "
                + "sexe = '" + o.getSexe() + "' "
                + "WHERE id = " + o.getId();
        
        try {
            Statement st = connexion.Connexion.getConnection().createStatement();
            st.executeUpdate(req);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AbonneService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean delete(Abonne o) {
        String req = "DELETE FROM abonne WHERE id = " + o.getId();
        try {
            Statement st = connexion.Connexion.getConnection().createStatement();
            st.executeUpdate(req);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AbonneService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }

    @Override
    public Abonne findById(int id) {
        Abonne ab = null;
        String req = "SELECT * FROM abonne WHERE id = " + id;
        
        try {
            Statement st = connexion.Connexion.getConnection().createStatement();
            ResultSet rs = st.executeQuery(req);

            if (rs.next()) {
                ab = new Abonne(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getInt("age"),
                        rs.getString("sexe")
                );
            }
            return ab;

        } catch (SQLException ex) {
            Logger.getLogger(AbonneService.class.getName()).log(Level.SEVERE, null, ex);
            return ab;
        }
    }

    @Override
    public List<Abonne> findAll() {
        List<Abonne> abonnes = new ArrayList<>();
        String req = "SELECT * FROM abonne";
        
        try {
            Statement st = connexion.Connexion.getConnection().createStatement();
            ResultSet rs = st.executeQuery(req);
            
            while (rs.next()) {
                abonnes.add(new Abonne(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getInt("age"),
                        rs.getString("sexe")
                ));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AbonneService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return abonnes;
    }
// Trouver les abonnés dont l’âge est compris entre minAge et maxAge
public List<Abonne> findByAgeBetween(int minAge, int maxAge) {
    List<Abonne> abonnes = new ArrayList<>();
    try {
        String sql = "SELECT * FROM abonne WHERE age BETWEEN ? AND ?";
        PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
        ps.setInt(1, minAge);
        ps.setInt(2, maxAge);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Abonne a = new Abonne();
            a.setId(rs.getInt("id"));
            a.setNom(rs.getString("nom"));
            a.setPrenom(rs.getString("prenom"));
            a.setAge(rs.getInt("age"));
            a.setSexe(rs.getString("sexe"));
            abonnes.add(a);
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return abonnes;
}

}
