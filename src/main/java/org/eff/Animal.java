/*
 * a entity, that walks inside a place
 */
package org.eff;

import java.util.Random;

/**
 *
 * @author user1
 * A thing that moves inside a place.
 */
public abstract class Animal {
    private int speed;
    private int move_rest = MAX_SPEED;
    private int id = (int) (Math.random() * 1000);

    int x;
    int y;
    Direction dir;

    public int getId() {
        return id;
    }

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

    public Direction getDir() {
        return dir;
    }

    private static Random r = new Random();

    public static final int MAX_SPEED = 10;

    public Animal(int x, int y) {
        this.x = x;
        this.y = y;
        speed = r.nextInt(MAX_SPEED) + 1;
        dir = Direction.diagonal_random_direction();
    }

    public Animal(int x, int y, int speed, Direction dir){
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.dir = dir;
    }

    @Override
    public String toString(){
        return ""
                + "id=" + getId()
                + ", x=" + getX()
                + ", y=" + getY()
                + ", spd=" + getSpeed()
                + ", rest=" + move_rest
                + ", dir=" + dir.toString();
    }

    public void step(Field field) {
        int cur_step = move_rest - getSpeed();
        if(cur_step > 0) {
            move_rest = cur_step;
        } else {
            update_coordinates(field);
            move_rest = cur_step + MAX_SPEED;
        }
    }

    public void set_dir(Direction d) {
        dir = d;
    }

    abstract public void update_coordinates(Field field);

}
