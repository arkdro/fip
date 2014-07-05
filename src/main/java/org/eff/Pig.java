/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eff;

import java.util.Random;

/**
 *
 * @author user1
 * A thing that moves inside a place.
 */
public class Pig {
    private int x;
    private int y;
    private int speed, move_rest;
    private dirs dir;

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }

    private static enum dirs {

        NW(-1, 1),  N(0, 1),  NE(1, 1),
         W(-1, 0),             E(1, 0),
        SW(-1, -1), S(0, -1), SE(1, -1);
        private final int dx, dy;

        dirs(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
        private static dirs flip_x_dir(dirs d) {
            switch(d) {
                    case NW: return NE;
                    case W: return E;
                    case SW: return SE;
                    case N: return N;
                    case S: return S;
                    case NE: return NW;
                    case E: return W;
                    case SE: return SW;
            }
            assert false : "should not happen";
            return NW;
        }

        private static dirs flip_y_dir(dirs d) {
            switch(d) {
                    case NW: return SW;
                    case W: return W;
                    case SW: return NW;
                    case N: return S;
                    case S: return N;
                    case NE: return SE;
                    case E: return E;
                    case SE: return NE;
            }
            assert false : "should not happen";
            return NW;
        }

    }

    private static final int MAX_SPEED = 10;
    private static Random r = new Random();
    public void update_coordinates(int min_x, int max_x, int min_y, int max_y) {
        int dx = dir.dx;
        int dy = dir.dy;
        int new_x = getX() + dx;
        if(new_x < min_x || getX() > max_x) {
            dir = dirs.flip_x_dir(dir);
        } else {
            x = new_x;
        }
        int new_y = getY() + dy;
        if(new_y < min_y || getY() > max_y) {
            dir = dirs.flip_y_dir(dir);
        } else {
            y = new_y;
        }
    }

    public Pig(int x, int y) {
        this.x = x;
        this.y = y;
        speed = r.nextInt(MAX_SPEED) + 1;
        move_rest = MAX_SPEED;
        int dir_idx = r.nextInt(dirs.values().length);
        dir = dirs.values()[dir_idx];
    }

    @Override
    public String toString(){
        return "x=" + getX()
                + ", y=" + getY()
                + ", spd=" + getSpeed()
                + ", rest=" + move_rest
                + ", dir=" + dir.toString();
    }

    public void step(int min_x, int max_x, int min_y, int max_y) {
        int cur_step = move_rest - getSpeed();
        if(cur_step > 0) {
            move_rest = cur_step;
        } else {
            update_coordinates(min_x, max_x, min_y, max_y);
            move_rest = cur_step + MAX_SPEED;
        }
    }
}
