/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.eff;


public class PigBuilder {
    private int x;
    private int y;
    private int speed;
    private Direction dir;

    public PigBuilder() {
    }

    public PigBuilder setX(int x) {
        this.x = x;
        return this;
    }

    public PigBuilder setY(int y) {
        this.y = y;
        return this;
    }

    public PigBuilder setSpeed(int speed) {
        this.speed = speed;
        return this;
    }

    public PigBuilder setDir(Direction dir) {
        this.dir = dir;
        return this;
    }

    public Pig createPig() {
        return new Pig(x, y, speed, dir);
    }

    public Pig createPigShort() {
        return new Pig(x, y);
    }

}
