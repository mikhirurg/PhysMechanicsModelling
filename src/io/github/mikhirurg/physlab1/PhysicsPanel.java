package io.github.mikhirurg.physlab1;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class PhysicsPanel extends JPanel {
    private Rail rail;
    PhysObject object;

    final double scale = 5;
    final double shift = 50;
    final int mod = 15;

    double resize = 0;

    private double time;

    AffineTransform transform;


    PhysicsPanel(Rail rail) {
        this.rail = rail;
        object = rail.createObject();
    }

    void updateObject() {
        object = rail.createObject();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.getTransform();

        g2.setColor(Color.gray);
        g2.fillRect(0, 0, getWidth(), getHeight());
        AffineTransform tmp = g2.getTransform();
        transform = g2.getTransform();

        resize = Math.min(1, getHeight() / (Math.max(rail.getHStart(), rail.getHEnd()) + object.getHeight()) / 1.1);
        transform.translate(0,
                            getHeight() * (1 - resize));
        transform.scale(resize, resize);

        g2.setTransform(transform);

        rail.paint(g2, getWidth(), getHeight());
        object.paint(g2, getWidth(), getHeight() , time, 0);
        drawAxis(g2);
        g2.setTransform(tmp);
        drawPhysObjectInfo(g2);
    }

    void drawAxis(Graphics2D g2) {
        g2.setColor(Color.BLACK);


        for (int i = 0; i < getWidth() / resize; i++) {
            if (i % mod == 0) {
                g2.drawLine(i, getHeight() - 10, i, getHeight());
            }
            if (i % (mod * 3) == 0) {
                int shift = g2.getFontMetrics().stringWidth(String.valueOf(i)) / 2;
                g2.drawString(String.valueOf(i), i - shift, getHeight() - 20);
            }
        }
        for (int i = 0; i < getHeight() / resize; i++) {
            if (i % mod == 0) {
                g2.drawLine(0, getHeight() - i, 10, getHeight() - i);
            }
            if (i % (mod * 3) == 0) {
                g2.drawString(String.valueOf(i), 20, (float) (getHeight() - i));
            }
        }
    }

    void drawPhysObjectInfo(Graphics2D g2) {
        Font old = g2.getFont();

        g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, (int) (20*resize)));

        double y1 = 20;

        double x1 = Integer.MAX_VALUE;

        for (String line : object.getInfo(time).split("\n")) {
            x1 = Math.min(x1, getWidth() - g2.getFontMetrics().stringWidth(line));
        }

        for (String line : object.getInfo(time).split("\n")) {
            g2.drawString(line, (int) x1, (int) (y1));
            y1 += g2.getFontMetrics().getHeight();
        }
        g2.setFont(old);
    }

    public void setTime(double time) {
        this.time = time;
    }
}
