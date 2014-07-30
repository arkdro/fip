/*
 * the same pig, only controllable little bit
 */
package org.eff;

/**
 *
 * @author user1
 */
public class Mower extends Animal {

    private static final int max_speed = 5;
    private int pots = 3;
    private boolean is_moving;
    private boolean is_doing_mow;

    private void start_moving() {
        is_moving = true;
    }

    private void stop_moving() {
        is_moving = false;
    }

    private void start_doing_mow() {
        is_doing_mow = true;
    }

    private void stop_doing_mow() {
        is_doing_mow = false;
    }

    private void move_coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private void store_step(int x, int y, Field field) {
        field.set_cell(x, y, Cell.STEP);
    }

    private void decrease_pots() {
        pots--;
    }

    private void fix_steps(Field field) {
        set_steps(field, Cell.DIRT);
    }

    private void clear_steps(Field field) {
        set_steps(field, Cell.GRASS);
    }

    private void set_steps(Field field, Cell filler) {
        field.fill_cells(Cell.STEP, filler);
    }

    private void init_mower_data() {
        init_mower_data(0, 0);
    }

    private void init_mower_data(int x, int y) {
        this.x = x;
        this.y = y;
        stop_moving();
        stop_doing_mow();
    }

    public Mower(int x, int y) {
        super(x, y, max_speed, null);
        init_mower_data(x, y);
    }

    @Override
    public void update_coordinates(Field field){
        if(is_moving) {
            handle_move(field);
        }
    }

    public void handle_move(Field field) {
        int dx = dir.getDx();
        int dy = dir.getDy();
        int next_x = x + dx;
        int next_y = y + dy;
        Cell next_cell = field.get_cell_bounded(next_x, next_y);
        if(next_cell == Cell.OUT) {
            return; // no move
        }
        if (next_cell == Cell.PIG || next_cell == Cell.STEP) {
            walk_into_bad_place(field);
            return;
        }
        Cell cur_cell = field.get_cell(x, y);
        if (cur_cell == Cell.DIRT && next_cell == Cell.GRASS) {
            start_doing_mow();
            move_coord(next_x, next_y);
            return;
        }
        if (is_doing_mow) {
            store_step(x, y, field);
            move_coord(next_x, next_y);
            if (next_cell == Cell.DIRT) {
                stop_moving();
                stop_doing_mow();
                fix_steps(field);
            }
            return;
        }
        move_coord(next_x, next_y);
    }

    public void walk_into_bad_place(Field field) {
        clear_steps(field);
        decrease_pots();
        init_mower_data(); // should the invincibility be set on init?
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

    public boolean has_pots() {
        return (pots > 0);
    }
}
