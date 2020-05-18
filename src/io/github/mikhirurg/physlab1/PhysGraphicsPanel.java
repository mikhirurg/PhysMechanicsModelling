package io.github.mikhirurg.physlab1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PhysGraphicsPanel extends JPanel {

    private final DecimalFormat format;
    private ArrayList<PhysicsExperimentContainer> containers;
    private int size = 10;
    private int mul = 1000;
    private double scale;
    private int mod = 20;
    private double shiftX2 = getWidth() * 0.8;
    private double shiftY2 = 0;
    private double shiftX1 = 0;
    private double shiftY1 = 0;


    private double x0;
    private double y0;
    private double x1;
    private double y1;


    private boolean showDots;
    private boolean showGrid;

    PhysGraphicsPanel(ArrayList<PhysicsExperimentContainer> containers) {
        this.containers = containers;
        format = new DecimalFormat("#0.00");
        showDots = false;
        showGrid = false;

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                x0 = e.getX();
                y0 = e.getY();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();
                double dx = (x1 - x0);
                double dy = (y0 - y1);

                if (x0 >= 0 && x0 <= getWidth() / 2.0 && y0 >= 0 && y0 <= getHeight()) {
                    shiftX1 += dx;
                    shiftY1 += dy;
                }

                if (x0 >= getWidth() / 2.0 && x0 <= getWidth() && y0 >= 0 && y0 <= getHeight()) {
                    shiftX2 += dx;
                    shiftY2 += dy;
                }

                x0 = x1;
                y0 = y1;

                repaint();
            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setBackground(Color.WHITE);
        g2.setColor(Color.BLACK);

        if (containers.size() > 0) {

            // Graph1

            double sumZY = 0;
            double sumZ2 = 0;

            for (PhysicsExperimentContainer cont : containers) {
                double y = cont.getX2() - cont.getX1();
                double z = (cont.getT2() * cont.getT2() - cont.getT1() * cont.getT1()) * mul / 2;
                g2.fillOval((int) (z * getScale() - size / 2 + shiftX1), (int) ((getHeight() - (y - size / 2)) - shiftY1), size, size);

                if (showDots) {
                    g2.drawString("(" + format.format(z / mul) + "; " + format.format(y / 1000) + ")", (int) (z * getScale() - size / 2 - shiftX1), (float) ((getHeight() - (y - size / 2) - shiftY1)));
                }

                sumZY += y * z;
                sumZ2 += z * z;
            }

            double a = sumZY / (sumZ2 * getScale());

            g2.setColor(Color.RED);
            g2.drawLine((int) shiftX1, (int) (getHeight() - shiftY1), (int) (getWidth() / 2 + shiftX1), (int) (getHeight() - (getWidth() + shiftX1) / 2 * a - shiftY1));

            // Graph2

            double sumAsinA = 0;
            double sumA = 0;
            double sumSinA = 0;
            double sumSinA2 = 0;


            g2.setColor(Color.BLACK);

            for (PhysicsExperimentContainer cont : containers) {
                a = cont.getAcc() + shiftY2;
                double sinA = cont.getSinA() * mul + shiftX2;
                g2.fillOval((int) sinA - size / 2, (int) (getHeight() - a - size / 2), size, size);

                if (showDots) {
                    g2.drawString("(" + format.format(cont.getSinA()) + "; " + format.format(cont.getAcc()) + ")", (int) sinA, (int) (getHeight() - a - size / 2));
                }

            }



        }

        g2.setColor(Color.BLACK);

        g2.drawRect(0, 0, getWidth() / 2, getHeight());
        g2.drawRect(getWidth() / 2, 0, getWidth(), getHeight());

        drawAxis1(g2);
        drawAxis2(g2);
    }

    void drawAxis1(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);

        int lenY = isShowGrid() ? getHeight() : 10;
        int lenX = isShowGrid() ? getWidth() / 2 : 10;

        g2.setStroke(new BasicStroke(0.3f));

        for (int i = 1; i < getWidth() / 2 - g2.getFontMetrics().getHeight(); i++) {
            if (i % mod == 0) {
                g2.drawLine(i, getHeight() - lenY, i, getHeight());
            }
            if (i % (mod * 2) == 0) {
                int shift = g2.getFontMetrics().stringWidth(String.valueOf(i / (double) mul)) / 2;
                g2.drawString(format.format((i - shiftX1) / ((double) mul * getScale())), i - shift, getHeight() - 20);
            }
        }

        g2.drawString("Z(c^2)", getWidth() / 2 - 40, getHeight() - 40);

        for (int i = 1; i < getHeight() - g2.getFontMetrics().getHeight(); i++) {
            if (i % mod == 0) {
                g2.drawLine(0, getHeight() - i, lenX, getHeight() - i);
            }
            if (i % (mod * 3) == 0) {
                g2.drawString(String.valueOf(format.format((i - shiftY1) / 1000.0)), 20, (float) (getHeight() - i));
            }
        }
        g2.drawString("Y(M)", 10, 20);
    }

    void drawAxis2(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);

        int lenY = isShowGrid() ? getHeight() : 10;
        int lenX = isShowGrid() ? getWidth() / 2 : 10;

        g2.setStroke(new BasicStroke(0.3f));


        for (int i = getWidth() / 2; i < getWidth() - g2.getFontMetrics().getHeight(); i++) {
            if (i % mod == 0) {
                g2.drawLine(i, getHeight() - lenY, i, getHeight());
            }
            if (i % (mod * 2) == 0) {
                int shift = g2.getFontMetrics().stringWidth(String.valueOf(i / (double) mul)) / 2;
                g2.drawString(format.format((i - shiftX2) / ((double) mul * getScale())), i - shift, getHeight() - 20);
            }
        }

        g2.drawString("sin a", getWidth() - 40, getHeight() - 40);

        for (int i = 1; i < getHeight() - g2.getFontMetrics().getHeight(); i++) {
            if (i % mod == 0) {
                g2.drawLine((int) (getWidth() / 2.0), getHeight() - i, (int) (getWidth() / 2.0 + lenX), getHeight() - i);
            }
            if (i % (mod * 3) == 0) {
                g2.drawString(String.valueOf(format.format((i - shiftY2) / 1000.0)), (float) (getWidth() / 2.0 + 20), (float) (getHeight() - i));
            }
        }
        g2.drawString("a м/с^2", getWidth() / 2 + 10, 20);
    }

    public boolean isShowDots() {
        return showDots;
    }

    public void setShowDots(boolean showDots) {
        this.showDots = showDots;
    }

    public boolean isShowGrid() {
        return showGrid;
    }

    public void setShowGrid(boolean showGrid) {
        this.showGrid = showGrid;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }
}
