/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import connexion.Connexion;
import dao.IDao;
import entities.Abonne;
import entities.Abonnement;
import entities.Paiement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author pc
 */
public class PaiementService implements IDao<Paiement> {

    private AbonneService abonneService;
    private AbonnementService abonnementService;

    public PaiementService() {
        abonneService = new AbonneService();
        abonnementService = new AbonnementService();
    }

    @Override
    public boolean create(Paiement o) {
        try {
            String req = "INSERT INTO paiement (abonneId, abonnementId, datePaiement, montant) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = Connexion.getConnection().prepareStatement(req);
            ps.setInt(1, o.getAbonne().getId());
            ps.setInt(2, o.getAbonnement().getId());
            ps.setDate(3, Date.valueOf(o.getDatePaiement()));
            ps.setDouble(4, o.getMontant());
            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(PaiementService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean update(Paiement o) {
        try {
            String req = "UPDATE paiement SET abonneId = ?, abonnementId = ?, datePaiement = ?, montant = ? WHERE id = ?";
            PreparedStatement ps = Connexion.getConnection().prepareStatement(req);
            ps.setInt(1, o.getAbonne().getId());
            ps.setInt(2, o.getAbonnement().getId());
            ps.setDate(3, Date.valueOf(o.getDatePaiement()));
            ps.setDouble(4, o.getMontant());
            ps.setInt(5, o.getId());
            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(PaiementService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean delete(Paiement o) {
        try {
            String req = "DELETE FROM paiement WHERE id = ?";
            PreparedStatement ps = Connexion.getConnection().prepareStatement(req);
            ps.setInt(1, o.getId());
            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(PaiementService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public Paiement findById(int id) {
        try {
            String req = "SELECT * FROM paiement WHERE id = ?";
            PreparedStatement ps = Connexion.getConnection().prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int abonneId = rs.getInt("abonneId");
                int abonnementId = rs.getInt("abonnementId");

                Abonne ab = abonneService.findById(abonneId);
                Abonnement abo = abonnementService.findById(abonnementId);

                LocalDate datePaiement = rs.getDate("datePaiement").toLocalDate();
                double montant = rs.getDouble("montant");

                return new Paiement(
                        rs.getInt("id"),
                        ab,
                        abo,
                        datePaiement,
                        montant
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(PaiementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Paiement> findAll() {
        List<Paiement> paiements = new ArrayList<>();
        try {
            String req = "SELECT * FROM paiement";
            PreparedStatement ps = Connexion.getConnection().prepareStatement(req);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int abonneId = rs.getInt("abonneId");
                int abonnementId = rs.getInt("abonnementId");

                Abonne ab = abonneService.findById(abonneId);
                Abonnement abo = abonnementService.findById(abonnementId);

                LocalDate datePaiement = rs.getDate("datePaiement").toLocalDate();
                double montant = rs.getDouble("montant");

                Paiement p = new Paiement(
                        rs.getInt("id"),
                        ab,
                        abo,
                        datePaiement,
                        montant
                );

                paiements.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PaiementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paiements;
    }
// Retourner tous les paiements d’un abonné (via ses abonnements)
public List<Paiement> findByAbonne(int abonneId) {
    List<Paiement> paiements = new ArrayList<>();
    try {
        String sql =
            "SELECT p.* FROM paiement p " +
            "JOIN abonnement a ON p.abonnementId = a.id " +
            "WHERE a.abonneId = ?";
        PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
        ps.setInt(1, abonneId);
        ResultSet rs = ps.executeQuery();

        AbonnementService abonnementService = new AbonnementService();

        while (rs.next()) {
            Paiement p = new Paiement();
            p.setId(rs.getInt("id"));
            p.setMontant(rs.getDouble("montant"));
           p.setDatePaiement(rs.getDate("datePaiement").toLocalDate());
            // reconstruire l’abonnement
            Abonnement ab = abonnementService.findById(rs.getInt("abonnementId"));
            p.setAbonnement(ab);
            paiements.add(p);
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return paiements;
}

}
