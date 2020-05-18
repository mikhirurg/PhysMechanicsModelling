package io.github.mikhirurg.physlab1;

import java.awt.*;
import java.awt.geom.Path2D;

public class Rail {
    private final int mul = 100;
    private double xStart;
    private double xEnd;
    private double x1;
    private double x2;
    private double t1;
    private double t2;
    private double hStart;
    private double hEnd;
    private double mu;
    private double startSpeed;
    private double maxTime;
    private double sinA;
    private double cosA;
    private double acc;
    private double animDur;

    final double objectRadius = 30;
    final double g = 9.8 * mul;
    final double eps = 1e-10;


    Rail(double xStart, double xEnd, double hStart, double hEnd, double mu, double startSpeed, double x1, double x2) {
        this.xStart = xStart;
        this.xEnd = xEnd;
        this.hStart = hStart;
        this.hEnd = hEnd;
        this.mu = mu;
        this.startSpeed = startSpeed;
        this.x1 = x1;
        this.x2 = x2;

        calculate();
    }

    void calculate() {
        sinA = (hStart - hEnd) / (Math.sqrt((hStart - hEnd) * (hStart - hEnd) + (xStart - xEnd) * (xStart - xEnd)));
        cosA = Math.sqrt(1 - sinA * sinA);
        double a = g * (sinA - mu * cosA);
        double speed0 = startSpeed * cosA;
        double accX = a * cosA;

        double speed1 = speed0 * speed0 + 2.0 * accX * (xEnd - xStart);
        speed1 = speed1 > 0 ? Math.sqrt(speed1) : 0;

        maxTime = (speed1 - speed0) / accX;
        acc = a;

        speed1 = speed0 * speed0 + 2.0 * accX * (x1 - xStart);
        speed1 = speed1 > 0 ? Math.sqrt(speed1) : 0;

        t1 = (speed1 - speed0) / accX;

        speed1 = speed0 * speed0 + 2.0 * accX * (x2 - xStart);
        speed1 = speed1 > 0 ? Math.sqrt(speed1) : 0;

        t2 = (speed1 - speed0) / accX;

        animDur = (long) (getMaxTime() * 1000000000);
    }

    PhysObject createObject() {
        calculate();
        return new PhysObject(xStart, hStart, getAcceleration() * getCosA(), getAcceleration() * getSinA(), startSpeed * getCosA(), startSpeed * getSinA(), objectRadius, objectRadius);
    }

    void paint(Graphics2D g2, int width, int height) {
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect((int) x1 - 5, (int) (height - Math.max(hStart, hEnd) - 20), 10, (int) (Math.max(hStart, hEnd) + 20));
        g2.fillRect((int) x2 - 5, (int) (height - Math.max(hStart, hEnd) - 20), 10, (int) (Math.max(hStart, hEnd) + 20));
        g2.setColor(Color.BLACK);
        g2.drawString(String.valueOf(x1), (int) x1 - 5, (int) (height - Math.max(hStart, hEnd) - g2.getFontMetrics().getHeight() - 20));
        g2.drawString(String.valueOf(x2), (int) x2 - 5, (int) (height - Math.max(hStart, hEnd) - g2.getFontMetrics().getHeight() - 20));
        g2.setColor(Color.ORANGE);
        Path2D.Double wedge = new Path2D.Double();
        wedge.moveTo(xStart, (height - hStart));
        wedge.lineTo(xEnd, height - hEnd);
        wedge.lineTo(xEnd, height);
        wedge.lineTo(xStart, (height));
        wedge.closePath();
        g2.fill(wedge);
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public void setX1(double x1) {
        this.x1 =  x1;
    }

    public void setX2(double x2) {
        this.x2 =  x2;
    }

    public double getxStart() {
        return xStart;
    }

    public void setxStart(double xStart) {
        this.xStart = xStart;
    }

    public double getxEnd() {
        return xEnd;
    }

    public void setxEnd(double xEnd) {
        this.xEnd = xEnd;
    }

    public double getMu() {
        return mu;
    }

    public void setMu(double mu) {
        this.mu = mu;
    }

    public double getStartSpeed() {
        return startSpeed;
    }

    public void setStartSpeed(double startSpeed) {
        this.startSpeed = startSpeed;
    }

    public double getHStart() {
        return hStart;
    }

    public void setHStart(double hStart) {
        this.hStart = hStart;
    }

    public double getHEnd() {
        return hEnd;
    }

    public void setHEnd(double hEnd) {
        this.hEnd = hEnd;
    }

    public double getMaxTime() {
        return maxTime;
    }

    public double getSinA() {
        return sinA;
    }

    public double getCosA() {
        return cosA;
    }

    public double getAcceleration() {
        return acc;
    }

    public double getAnimDur() {
        return animDur;
    }

    public double getT1() {
        return t1;
    }

    public double getT2() {
        return t2;
    }
}
