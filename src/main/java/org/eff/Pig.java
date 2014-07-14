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
    private int speed;
    private Direction dir;
    private int move_rest = MAX_SPEED;

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

    private static final int MAX_SPEED = 10;
    private static Random r = new Random();
    public void update_coordinates(int min_x, int max_x, int min_y, int max_y) {
        int dx = dir.getDx();
        int dy = dir.getDy();
        int new_x = getX() + dx;
        if(new_x < min_x || getX() > max_x) {
            dir = Direction.flip_x_dir(dir);
        } else {
            x = new_x;
        }
        int new_y = getY() + dy;
        if(new_y < min_y || getY() > max_y) {
            dir = Direction.flip_y_dir(dir);
        } else {
            y = new_y;
        }
    }

    public Pig(int x, int y) {
        this.x = x;
        this.y = y;
        speed = r.nextInt(MAX_SPEED) + 1;
        int dir_idx = r.nextInt(Direction.values().length);
        dir = Direction.values()[dir_idx];
    }

    public Pig(int x, int y, int speed, Direction dir){
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.dir = dir;
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
