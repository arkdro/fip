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

    public Field(int w, int h) {
        this.width = w;
        this.height = h;
        int total = width * height;
        field = new Cell[total];
        for(int i = 0; i < total; i++) {
            field[i] = Cell.DIRT;
        }
    }

    public int get_width() {
        return width;
    }

    public int get_height() {
        return height;
    }

    public Cell get_cell(int x, int y) {
        int idx = index(x, y);
        return field[idx];
    }

    public Wall look_ahead(int x, int y, int dx, int dy) {
        int idx = index(x, y);
        return Wall.SPACE;
    }
}
