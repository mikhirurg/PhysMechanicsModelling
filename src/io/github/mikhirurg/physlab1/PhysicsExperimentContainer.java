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

import java.text.DecimalFormat;

public class PhysicsExperimentContainer {
    private final long id;
    private final double x1;
    private final double x2;
    private final double t1;
    private final double t2;
    private final double sinA;
    private final double acc;
    private final DecimalFormat format;

    PhysicsExperimentContainer(long id, double x1, double x2, double t1, double t2, double sinA, double acc) {
        this.id = id;
        this.x1 = x1;
        this.x2 = x2;
        this.t1 = t1;
        this.t2 = t2;
        this.sinA = sinA;
        this.acc = acc;
        format = new DecimalFormat("#0.00");
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public double getT1() {
        return t1;
    }

    public double getT2() {
        return t2;
    }

    public double getSinA() {
        return sinA;
    }

    public double getAcc() {
        return acc;
    }

    public long getID() {
        return id;
    }

    public String toString() {
        return "Эксперимент #" + getID() +
                "\n (x1: " + format.format(getX1()) + " мм, x2: " + format.format(getX2()) +
                " мм)\n (t1: " + format.format(getT1()) + " с, t2: " + format.format(getT2()) +
                " с)\n (sin(a): " + format.format(getSinA()) + ", Ускорение: " + format.format(getAcc()) + " мм/c)\n\n";
    }
}
