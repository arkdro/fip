/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eff;

/**
 *
 * @author user1
 */
public class Field {

    private final int width;
    private int height;
    private Cell[] field;
    private double mowed;

    private int index(int x, int y) {
        return y * width + x;
    }

    private Cell get_cell(int x, int y, int dx, int dy) {
        int new_x = x + dx;
        int new_y = y + dy;
        return get_cell_bounded(new_x, new_y);
    }

    public Cell get_cell_bounded(int x, int y) {
        if (x < 0
                || x >= width
                || y < 0
                || y >= height) {
            return Cell.OUT;
        }
        int idx = index(x, y);
        return field[idx];
    }

    public Cell get_cell(int x, int y) {
        int idx = index(x, y);
        return field[idx];
    }

    public Field(int w, int h) {
        this.width = w;
        this.height = h;
        int total = width * height;
        field = new Cell[total];
        for (int i = 0; i < total; i++) {
            field[i] = Cell.DIRT;
        }
        mowed = 0;
    }

    public int get_width() {
        return width;
    }

    public int get_height() {
        return height;
    }

    public void set_cell(int x, int y, Cell cell) {
        int idx = index(x, y);
        field[idx] = cell;
    }

    public Wall look_ahead(int x, int y, int dx, int dy) {

        Cell horizontal = get_cell(x, y, dx, 0);
        Cell horizontal_neg_vertical = get_cell(x, y, dx, -dy);
        Cell vertical = get_cell(x, y, 0, dy);
        Cell vertical_neg_horizontal = get_cell(x, y, -dx, dy);
        Cell diagonal = get_cell(x, y, dx, dy);
        Cell current = get_cell(x, y, 0, 0);
        assert (current != Cell.OUT) : "current is OUT, x=" + x
                + ", y=" + y + ", w=" + width + ", h=" + height;
        if (diagonal == current) {
            return Wall.SPACE;
        } else if (horizontal == current && vertical == current) {
            return Wall.CORNER;
        } else if (horizontal != current && vertical != current) {
            return Wall.CORNER;
        } else if (horizontal == current && horizontal_neg_vertical == current) {
            return Wall.HORIZONTAL_WALL;
        } else if (vertical == current && vertical_neg_horizontal == current) {
            return Wall.VERTICAL_WALL;
        } else {
            return Wall.CORNER;
        }
    }

    public void print_field() {
        System.out.println("");
        for (int y = 0; y < height; y++) {
            System.out.print("y=" + y + ", ");
            for (int x = 0; x < width; x++) {
                System.out.print("\t" + get_cell(x, y));
            }
            System.out.println("");
        }
    }

    public void fill_cells(Cell src, Cell dst) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (get_cell(x, y) == src) {
                    set_cell(x, y, dst);
                }
            }
        }
    }

    /**
     * find regions of GRASS. If a region does not contain a pig, then fill the
     * region with DIRT.
     */
    public void update_mowed_regions(Pig[] pigs) {

    }

    public void update_mowed_percentage() {
        int cnt = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (get_cell(x, y) == Cell.DIRT)
                    cnt++;
            }
        }
        mowed = ((double) cnt) / (height * width);
    }

    // do not bother with initial borders set to DIRT
    public double get_mowed_percentage() {
        return mowed;
    }

}
