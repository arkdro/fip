/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eff;

import java.util.Random;

/**
 *
 * @author user1
 */
public enum Direction {
    NW(-1, 1),  N(0, 1),  NE(1, 1),
     W(-1, 0),             E(1, 0),
    SW(-1, -1), S(0, -1), SE(1, -1);
    private final int dx;
    private final int dy;

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static Direction random_direction() {
        Random r = new Random();
        int dir_idx = r.nextInt(Direction.values().length);
        return Direction.values()[dir_idx];
    }

    public static Direction diagonal_random_direction() {
        Random r = new Random();
        int len = Direction.values().length;
        int dir_idx = r.nextInt(len);
        Direction dir = null;
        for(int offset = 0; offset < len; offset++) {
            int rounded_idx = (dir_idx + offset) % len;
            dir = Direction.values()[rounded_idx];
            int dx = dir.getDx();
            int dy = dir.getDy();
            if(dx != 0 && dy != 0)
                break;
        }
        return dir;
    }

    public static Direction flip_x_dir(Direction d) {
        switch (d) {
            case NW:
                return NE;
            case W:
                return E;
            case SW:
                return SE;
            case N:
                return N;
            case S:
                return S;
            case NE:
                return NW;
            case E:
                return W;
            case SE:
                return SW;
        }
        assert false : "should not happen";
        return NW;
    }

    public static Direction flip_y_dir(Direction d) {
        switch (d) {
            case NW:
                return SW;
            case W:
                return W;
            case SW:
                return NW;
            case N:
                return S;
            case S:
                return N;
            case NE:
                return SE;
            case E:
                return E;
            case SE:
                return NE;
        }
        assert false : "should not happen";
        return NW;
    }

}
