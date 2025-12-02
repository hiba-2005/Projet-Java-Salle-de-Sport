package services;

import connexion.Connexion;
import dao.IUserDao;
import entities.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Properties;
import java.util.Random;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

public class UserService implements IUserDao {

    // plus besoin de champ Connexion ni de constructeur

    // ------------------------------------------------------------------
    // CRUD de base
    // ------------------------------------------------------------------
    @Override
    public boolean addUser(User user) {
        String req = "INSERT INTO user (login, password, securityQuestion, securityAnswer, email) "
                   + "VALUES (?, SHA1(?), ?, SHA1(?), ?)";
        try (PreparedStatement ps = Connexion.getConnection().prepareStatement(req)) {
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());          // mot de passe en clair -> SHA1 en SQL
            ps.setString(3, user.getSecurityQuestion());
            ps.setString(4, user.getSecurityAnswer());    // réponse en clair -> SHA1 en SQL
            ps.setString(5, user.getEmail());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout de l'utilisateur : " + ex.getMessage());
        }
        return false;
    }

    @Override
    public User findUserByLogin(String login) {
        String req = "SELECT * FROM user WHERE login = ?";
        try (PreparedStatement ps = Connexion.getConnection().prepareStatement(req)) {
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("securityQuestion"),
                        rs.getString("securityAnswer"),
                        rs.getString("email")
                );
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la recherche de l'utilisateur : " + ex.getMessage());
        }
        return null;
    }

    @Override
    public boolean authenticate(String login, String password) {
        String req = "SELECT * FROM user WHERE login = ? AND password = SHA1(?)";
        try (PreparedStatement ps = Connexion.getConnection().prepareStatement(req)) {
            ps.setString(1, login);
            ps.setString(2, password);      // mot de passe en clair
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'authentification : " + ex.getMessage());
        }
        return false;
    }

    // ------------------------------------------------------------------
    // Reset par question secrète
    // ------------------------------------------------------------------
    public boolean resetPasswordBySecurityQuestion(String login, String securityAnswer) {
        try {
            User user = findUserByLogin(login);
            if (user == null) {
                JOptionPane.showMessageDialog(null, "Utilisateur introuvable.");
                return false;
            }

            String req = "SELECT * FROM user WHERE login = ? AND securityAnswer = SHA1(?)";
            try (PreparedStatement ps = Connexion.getConnection().prepareStatement(req)) {
                ps.setString(1, login);
                ps.setString(2, securityAnswer);   // en clair -> SHA1 en SQL
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String newPassword = generateTemporaryPassword();
                    if (updatePassword(login, newPassword)) {
                        sendPasswordByEmail(user.getEmail(), newPassword);
                        return true;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Réponse secrète incorrecte.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
        return false;
    }

    // mise à jour du mot de passe (UNE seule méthode)
    public boolean updatePassword(String login, String newPassword) {
        String sql = "UPDATE user SET password = SHA1(?) WHERE login = ?";
        try (PreparedStatement ps = Connexion.getConnection().prepareStatement(sql)) {
            ps.setString(1, newPassword);   // en clair -> SHA1 en SQL
            ps.setString(2, login);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Erreur update password : " + ex.getMessage());
        }
        return false;
    }

    // ------------------------------------------------------------------
    // Utilitaires
    // ------------------------------------------------------------------
    // (optionnel, presque jamais utilisé ici, mais corrigé au cas où)
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1"); // nom exact
        byte[] hash = digest.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }

    public String generateTemporaryPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 10; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    private void sendPasswordByEmail(String recipientEmail, String newPassword) {
        try {
            // ⚠️ Mets ici TON Gmail + mot de passe d’application
            final String username   = "hiba.ouirouane.05@gmail.com";
            final String appPassword = "TON_MOT_DE_PASSE_APPLICATION_SANS_ESPACES";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, appPassword);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipientEmail));
            message.setSubject("Réinitialisation de votre mot de passe");
            message.setText("Bonjour,\n\nVotre nouveau mot de passe est : "
                    + newPassword
                    + "\n\nVeuillez le modifier après connexion.\n\nSalle de Sport.");

            Transport.send(message);
            JOptionPane.showMessageDialog(null,
                    "Un nouveau mot de passe a été envoyé à votre adresse e-mail.");
        } catch (MessagingException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erreur lors de l'envoi de l'e-mail.");
        }
    }
    // --- Envoi e-mail simple ---
private void sendForgotPasswordMail(String toEmail, String newPassword) throws MessagingException {
          
    final String fromEmail   = "hiba.ouirouane.05@gmail.com";      // ton Gmail
    final String appPassword = "ygqmldbvooguwtze";                 // ton app password

    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");

    Session session = Session.getInstance(props, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(fromEmail, appPassword);
        }
    });

    Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress(fromEmail));
    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
    message.setSubject("Mot de passe oublié - Salle de Sport");
    message.setText(
        "Bonjour,\n\n"
      + "Une demande de réinitialisation de mot de passe a été effectuée.\n"
      + "Votre nouveau mot de passe temporaire est : " + newPassword + "\n\n"
      + "Veuillez vous connecter avec ce mot de passe puis le modifier.\n\n"
      + "Salle de Sport."
    );

    Transport.send(message);
}

}
