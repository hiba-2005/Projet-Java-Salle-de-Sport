/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import connexion.Connexion;
import dao.IDao;
import entities.Abonnement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import entities.Abonne;
import java.util.logging.Logger;

/**
 *
 * @author pc
 */
public class AbonnementService implements IDao<Abonnement> {

    private AbonneService as;

    public AbonnementService() {
        as = new AbonneService();
    }

    @Override
    public boolean create(Abonnement o) {
        try {
            String req = "INSERT INTO abonnement (abonneId, type, duree, prix) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = Connexion.getConnection().prepareStatement(req);
            ps.setInt(1, o.getAbonne().getId());
            ps.setString(2, o.getType());
            ps.setInt(3, o.getDuree());
            ps.setDouble(4, o.getPrix());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AbonnementService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean update(Abonnement o) {
        try {
            String req = "UPDATE abonnement SET abonneId = ?, type = ?, duree = ?, prix = ? WHERE id = ?";
            PreparedStatement ps = Connexion.getConnection().prepareStatement(req);
                ps.setInt(1, o.getAbonne().getId()); // abonneId
        ps.setString(2, o.getType());        // type
        ps.setInt(3, o.getDuree());          // duree
        ps.setDouble(4, o.getPrix());        // prix
        ps.setInt(5, o.getId());             // ✅ WHERE id = ?
        ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AbonnementService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean delete(Abonnement o) {
        try {
            String req = "DELETE FROM abonnement WHERE id = ?";
            PreparedStatement ps = Connexion.getConnection().prepareStatement(req);
            ps.setInt(1, o.getId());   // ✅ id de l’abonnement
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AbonnementService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public Abonnement findById(int id) {
        try {
            String req = "SELECT * FROM abonnement WHERE id = ?";
            PreparedStatement ps = Connexion.getConnection().prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int abonneId = rs.getInt("abonneId");
                Abonne ab = as.findById(abonneId);

                return new Abonnement(
                        rs.getInt("id"),
                        ab,
                        rs.getString("type"),
                        rs.getInt("duree"),
                        rs.getDouble("prix")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(AbonnementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Abonnement> findAll() {
        List<Abonnement> abonnements = new ArrayList<>();
        try {
            String req = "SELECT * FROM abonnement";
            PreparedStatement ps = Connexion.getConnection().prepareStatement(req);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int abonneId = rs.getInt("abonneId");
                Abonne ab = as.findById(abonneId);

                Abonnement a = new Abonnement(
                        rs.getInt("id"),
                        ab,
                        rs.getString("type"),
                        rs.getInt("duree"),
                        rs.getDouble("prix")
                );
                abonnements.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AbonnementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return abonnements;
    }

    // Retourner tous les abonnements d’un abonné (par son id)
public List<Abonnement> findByAbonne(int abonneId) {
    List<Abonnement> abonnements = new ArrayList<>();
    try {
        String sql = "SELECT * FROM abonnement WHERE abonneId = ?";
        PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
        ps.setInt(1, abonneId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Abonnement a = new Abonnement();
            a.setId(rs.getInt("id"));
            // si tu veux, tu peux récupérer l’abonné avec AbonneService
            a.setType(rs.getString("type"));
            a.setDuree(rs.getInt("duree"));
            a.setPrix(rs.getDouble("prix"));
            abonnements.add(a);
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return abonnements;
}
// Trouver les abonnements par type (ex : "Mensuel", "Annuel", etc.)
public List<Abonnement> findByType(String type) {
    List<Abonnement> abonnements = new ArrayList<>();
    try {
        String sql = "SELECT * FROM abonnement WHERE type = ?";
        PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
        ps.setString(1, type);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Abonnement a = new Abonnement();
            a.setId(rs.getInt("id"));
            a.setType(rs.getString("type"));
            a.setDuree(rs.getInt("duree"));
            a.setPrix(rs.getDouble("prix"));
            abonnements.add(a);
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return abonnements;
}
// Liste des abonnements dont le total des paiements < prix
public List<Abonnement> findImpayes() {
    List<Abonnement> impayes = new ArrayList<>();
    try {
        String sql =
            "SELECT a.*, IFNULL(SUM(p.montant),0) AS total_paye " +
            "FROM abonnement a " +
            "LEFT JOIN paiement p ON a.id = p.abonnementId " +
            "GROUP BY a.id, a.abonneId, a.type, a.duree, a.prix " +
            "HAVING total_paye < a.prix";

        PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Abonnement a = new Abonnement();
            a.setId(rs.getInt("id"));
            a.setType(rs.getString("type"));
            a.setDuree(rs.getInt("duree"));
            a.setPrix(rs.getDouble("prix"));
            impayes.add(a);
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return impayes;
}

}
