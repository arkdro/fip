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

    private int index(int x, int y) {
        return y * width + x;
    }

    private Cell get_cell(int x, int y, int dx, int dy) {
        int new_x = x + dx;
        int new_y = y + dy;
        if (new_x < 0
                || new_x >= width
                || new_y < 0
                || new_y >= height) {
            return Cell.OUT;
        }
        int idx = index(new_x, new_y);
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
        Cell vertical = get_cell(x, y, 0, dy);
        Cell diagonal = get_cell(x, y, dx, dy);
        Cell current = get_cell(x, y, 0, 0);
        assert (current != Cell.OUT) : "current is OUT, x=" + x
                + ", y=" + y + ", w=" + width + ", h=" + height;
        if (diagonal == current) {
            return Wall.SPACE;
        } else if (horizontal == vertical) {
            return Wall.CORNER;
        } else if (horizontal == current) {
            return Wall.HORIZONTAL_WALL;
        } else if (vertical == current) {
            return Wall.VERTICAL_WALL;
        }
        assert false : "should not happen"
                + ", x=" + x
                + ", y=" + y
                + ", dx=" + dx
                + ", dy=" + dy
                + ", current=" + current
                + ", diagonal=" + diagonal
                + ", horizontal=" + horizontal
                + ", vertical=" + vertical;
        return Wall.SPACE;
    }
}
