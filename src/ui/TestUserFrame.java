/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class TestUserFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Fenêtre principale
                JFrame frame = new JFrame("Login - Salle de Sport");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(950, 520);
                frame.setLocationRelativeTo(null); // centrer l'écran

                // Ton JInternalFrame
                UserFrame uf = new UserFrame();
                uf.setVisible(true);

                // On ajoute l’InternalFrame dans la JFrame
                frame.getContentPane().add(uf);

                frame.setVisible(true);
            }
        });
    }
}
