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

    private void start_moving() {
        is_moving = true;
    }

    private void stop_moving() {
        is_moving = false;
    }

    public Mower(int x, int y) {
        super(x, y, max_speed, null);
        stop_moving();
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
                start_moving();
                break;
            case STOP:
                stop_moving();
                break;
        }
    }
}
