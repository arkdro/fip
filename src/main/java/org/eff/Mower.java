/*
 * the same pig, only controllable little bit
 */
package org.eff;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author user1
 */
public class Mower extends Animal {

    private static final int max_speed = 5;
    private int pots = 3;
    private boolean is_moving;
    private boolean is_doing_mow;
    private HashSet<Point> steps;
    private boolean just_fixed_steps;

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

    private void store_step(int x, int y) {
        steps.add(new Point(x, y));
    }

    private void decrease_pots() {
        pots--;
    }

    private void fix_steps(Field field) {
        for (Point c : steps) {
            field.set_cell(c.x, c.y, Cell.DIRT);
        }
        clear_steps();
        set_just_fixed_steps();
    }

    private void set_just_fixed_steps() {
        just_fixed_steps = true;
    }

    private void clear_steps() {
        steps.clear();
    }

    private void init_mower_data() {
        init_mower_data(0, 0);
    }

    private boolean is_next_cell_step(int x, int y) {
        return steps.contains(new Point(x, y));
    }

    private void init_mower_data(int x, int y) {
        this.x = x;
        this.y = y;
        stop_moving();
        stop_doing_mow();
        steps = new HashSet<>();
        clear_just_fixed_steps();
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
        clear_just_fixed_steps();
        int dx = dir.getDx();
        int dy = dir.getDy();
        int next_x = x + dx;
        int next_y = y + dy;
        Cell next_cell = field.get_cell_bounded(next_x, next_y);
        if(next_cell == Cell.OUT) {
            return; // no move
        }
        if(is_next_cell_step(next_x, next_y)) {
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
            store_step(x, y);
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
        clear_steps();
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

    public Set<Point> get_steps() {
        return steps;
    }

    public void clear_just_fixed_steps() {
        just_fixed_steps = false;
    }

    public boolean just_fixed_steps() {
        return just_fixed_steps;
    }
}
