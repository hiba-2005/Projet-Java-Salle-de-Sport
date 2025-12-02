/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.*;
import javax.swing.JPanel;

public class RoundedPanel extends JPanel {

    private int cornerRadius = 40;

    public RoundedPanel() {
        setOpaque(false);
    }

    public RoundedPanel(int radius) {
        this.cornerRadius = radius;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Dimension arcs = new Dimension(cornerRadius, cornerRadius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                  RenderingHints.VALUE_ANTIALIAS_ON);

        // Fond de la carte
        graphics.setColor(new Color(255, 230, 230));
        graphics.fillRoundRect(0, 0, width-1, height-1,
                               arcs.width, arcs.height);

        // Bordure
        graphics.setColor(new Color(200, 120, 130));
        graphics.drawRoundRect(0, 0, width-1, height-1,
                               arcs.width, arcs.height);

        super.paintComponent(g);
    }
}

