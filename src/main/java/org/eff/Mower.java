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
        super(x, y, max_speed, Direction.E);
        is_moving = false;
    }

    @Override
    public void update_coordinates(Field field){
        if(is_moving) {
            super.update_coordinates(field);
        }
    }
}
