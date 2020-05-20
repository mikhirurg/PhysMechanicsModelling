/*
 * PhysMechanicsModelling - a physics modulation project
 * Copyright (C) 2020  Mikhail Ushakov
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.mikhirurg.physlab1;

import java.awt.*;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class PhysObject {
    double x0;
    double y0;
    double accX0;
    double accY0;
    double speedX0;
    double speedY0;
    private final double w;
    private final double h;
    NumberFormat format;

    PhysObject(double x0, double y0, double accX0, double accY0, double speedX0, double speedY0, double width, double height) {
        this.x0 = x0;
        this.y0 = y0;
        this.w = width;
        this.h = height;
        this.accX0 = accX0;
        this.accY0 = accY0;
        this.speedX0 = speedX0;
        this.speedY0 = speedY0;
        format = new DecimalFormat("#0.00");
    }

    Point2D getPosition(double time) {
        return new Point2D.Double(
                x0 + speedX0 * time + accX0 * time * time / 2.0,
                y0 - speedY0 * time - accY0 * time * time / 2.0
        );
    }

    Point2D getSpeed(double time) {
        return new Point2D.Double(
                speedX0 + accX0 * time,
                speedY0 + accY0 * time
        );
    }

    double getSpeedD(double time) {
        Point2D speed = getSpeed(time);
        return Math.sqrt(speed.getX() * speed.getX() + speed.getY() * speed.getY());
    }

    Point2D getAcc() {
        return new Point2D.Double(
                accX0,
                accY0
        );
    }

    double getAccD() {
        Point2D acc = getAcc();
        return Math.sqrt(acc.getX() * acc.getX() + acc.getY() * acc.getY());
    }

    public void paint(Graphics g2, int width, int height, double time, double shift) {
        g2.setColor(Color.RED);
        Point2D pos = getPosition(time);
        g2.fillOval((int) (pos.getX()), (int) ((height - pos.getY() - w)), (int) w, (int) h);
        g2.setColor(Color.BLACK);
        g2.drawString("(" + format.format(pos.getX()) + "; " + format.format(pos.getY()) + ")", (int) ((int) pos.getX() + w), (int) (height - pos.getY() - w / 2));
    }

    public String log(double time) {
        Point2D pos = getPosition(time);
        return "(x: " + pos.getX() + ", y: " + pos.getY() + ")";
    }

    public String getInfo(double time) {
        StringBuilder out = new StringBuilder();

        Point2D pos = getPosition(time);
        Point2D speed = getSpeed(time);
        Point2D acc = getAcc();

        out.append("Информация: \n");
        out.append("Время: ").append(format.format(time)).append(" с\n");
        out.append("Координаты тела: \n -- (x: ").append(format.format(pos.getX())).append(" мм; ").append(format.format(pos.getY())).append(" мм)\n");
        out.append("Скорость: \n -- ")
                .append("V = ").append(format.format(getSpeedD(time))).append(" мм/с")
                .append("\n -- Vx = ").append(format.format(speed.getX())).append(" мм/с")
                .append("\n -- Vy = ").append(format.format(speed.getY())).append(" мм/с")
                .append("\n");
        out.append("Ускорение: \n -- ")
                .append("A = ").append(format.format(getAccD())).append(" мм/с^2")
                .append("\n -- Ax = ").append(format.format(acc.getX())).append(" мм/с^2")
                .append("\n -- Ay = ").append(format.format(acc.getY())).append(" мм/с^2")
                .append("\n");

        return out.toString();
    }

    public double getHeight() {
        return h;
    }

    private double getWidth() {
        return w;
    }
}
