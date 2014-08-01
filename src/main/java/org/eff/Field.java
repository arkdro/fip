/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eff;

import java.awt.Point;
import org.eff.fill.Fill;

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

    private int[] copy_field(int wall, int space) {
        int len = width * height;
        int[] tmp = new int[len];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int idx = index(x, y);
                if (get_cell(x, y) == Cell.DIRT) {
                    tmp[idx] = wall;
                } else {
                    tmp[idx] = space;
                }
            }
        }
        return tmp;
    }

    private int[] fill_grassed_rest(int[] input, int wall, int space) {
        int[] tmp = input.clone();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int idx = index(x, y);
                if (tmp[idx] == space) {
                    tmp[idx] = wall;
                }
            }
        }
        return tmp;
    }

    private void fill_field_regions(int[] input, int wall) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int idx = index(x, y);
                if (input[idx] == wall) {
                    set_cell(x, y, Cell.DIRT);
//                } else {
//                    set_cell(x, y, Cell.GRASS);
                }
            }
        }
        
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
     *
     * In other words: fill the region containing a pig with the filler. And do
     * not touch this region in the output field. Fill other grassed regions
     * with dirt. And move these regions in the output field.
     *
     * @param pigs
     */
    public void update_mowed_regions(Pig[] pigs) {
        int wall = 0;
        int space = 1;
        int filler = 2;
        int[] acc = copy_field(wall, space);
        for (Pig pig : pigs) {
            int x = pig.getX();
            int y = pig.getY();
            if (get_cell(x, y) == Cell.DIRT) {
                continue;
            }
            int idx = index(x, y);
            if(acc[idx] == filler) {
                continue;
            }
            int[] tmp = acc.clone();
            Fill f = new Fill(tmp, width, height);
            acc = f.fill(x, y, space, filler);
        }
        int[] res = fill_grassed_rest(acc, wall, space);
        fill_field_regions(res, wall);
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
