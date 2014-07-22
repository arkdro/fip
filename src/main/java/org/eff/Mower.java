/*
 * the same pig, only controllable little bit
 */
package org.eff;

/**
 *
 * @author user1
 */
public class Mower extends Pig {

    private static final int max_speed = 5;
    private boolean is_moving;

    public Mower(int x, int y) {
        super(x, y, max_speed, null);
        is_moving = false;
    }

    @Override
    public void update_coordinates(Field field){
        if(is_moving) {
            super.update_coordinates(field);
        }
    }

    public void change_motion(Direction dir, Move move) {
        switch (move) {
            case MOVE:
                set_dir(dir);
                is_moving = true;
                break;
            case STOP:
                is_moving = false;
                break;
        }
    }
}
