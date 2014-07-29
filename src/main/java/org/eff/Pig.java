package org.eff;

public class Pig extends Animal {

    private void check_for_mower(Field field, Mower mower) {
        boolean caught = check_for_mower_itself(field, mower);
        if(!caught)
            check_for_mower_steps(field, mower);
    }

    private boolean check_for_mower_itself(Field field, Mower mower) {
        if(mower.getX() == x && mower.getY() == y) {
            mower.walk_into_bad_place(field);
            return true;
        }
        return false;
    }

    private void check_for_mower_steps(Field field, Mower mower) {
        Cell cell = field.get_cell(x, y);
        if (cell == Cell.STEP)
            mower.walk_into_bad_place(field);
    }

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

    public Pig(int x, int y) {
        super(x, y);
    }

    public Pig(int x, int y, int speed, Direction dir) {
        super(x, y, speed, dir);
    }

    /**
     * - space. - horizontal wall. Flip y. - vertical wall. Flip x. - (inner |
     * outer) corner. Flip x, y.
     *
     * a pig requires at least a two-cell wide road to move (bouncing from
     * walls)
     * @param field
     */
    @Override
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

    public void step(Field field, Mower mower) {
        step(field);
        check_for_mower(field, mower);
    }
}
