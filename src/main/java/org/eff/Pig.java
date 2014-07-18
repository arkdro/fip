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
public class Pig {
    private int x;
    private int y;
    private int speed;
    private Direction dir;
    private int move_rest = MAX_SPEED;
    private int id = (int) (Math.random() * 1000);

    private void move_common() {
        int dx = dir.getDx();
        int dy = dir.getDy();
        x += dx;
        y += dy;
    }

    private void move_further() {
        move_common();
    }

    private void bounce_corner() {
        dir = Direction.flip_x_dir(Direction.flip_y_dir(dir));
        move_common();
    }

    private void bounce_horizontal_wall() {
        dir = Direction.flip_y_dir(dir);
        move_common();
    }

    private void bounce_vertical_wall() {
        dir = Direction.flip_x_dir(dir);
        move_common();
    }

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

    /**
     * - space.
     * - horizontal wall. Flip y.
     * - vertical wall. Flip x.
     * - (inner | outer) corner. Flip x, y.
     */
    public void update_coordinates(Field field) {
        int dx = dir.getDx();
        int dy = dir.getDy();
        switch (field.look_ahead(x, y, dx, dy)) {
            case SPACE:
                move_further();
                break;
            case CORNER:
                bounce_corner();
                break;
            case HORIZONTAL_WALL:
                bounce_horizontal_wall();
                break;
            case VERTICAL_WALL:
                bounce_vertical_wall();
                break;
        }
    }

    public Pig(int x, int y) {
        this.x = x;
        this.y = y;
        speed = r.nextInt(MAX_SPEED) + 1;
        dir = Direction.random_direction();
    }

    public Pig(int x, int y, int speed, Direction dir){
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
}
