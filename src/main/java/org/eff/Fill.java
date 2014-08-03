/*
 * 4-way flood fill.
 * http://en.wikipedia.org/wiki/Flood_fill
 */
package org.eff;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;

public class Fill {

    private int[] data;
    private int width;
    private int height;
    private Queue<Point> queue;

    private int index(int x, int y) {
        return y * width + x;
    }

    private Integer get_cell(int x, int y) {
        if (x < 0
                || x >= width
                || y < 0
                || y >= height) {
            return null;
        }
        int idx = index(x, y);
        return data[idx];
    }

    private void set_cell(int x, int y, int replace) {
        int idx = index(x, y);
        data[idx] = replace;
    }

    private void process_one_point(int x, int y, int target, int replace) {
        int west = find_west(x, y, target, replace);
        int east = find_east(x, y, target, replace);
        for (int i = west; i <= east; i++) {
            set_cell(i, y, replace);
            int north = y - 1;
            Integer north_cell = get_cell(i, north);
            if (north_cell != null && north_cell == target) {
                queue.add(new Point(i, north));
            }
            int south = y + 1;
            Integer south_cell = get_cell(i, south);
            if (south_cell != null && south_cell == target) {
                queue.add(new Point(i, south));
            }
        }
    }

    private int find_west(int x, int y, int target, int replace) {
        int step = -1;
        return find_line_end(x, y, target, replace, step);
    }

    private int find_east(int x, int y, int target, int replace) {
        int step = 1;
        return find_line_end(x, y, target, replace, step);
    }

    private int find_line_end(int x, int y, int target, int replace, int step) {
        int i = x;
        for (;;) {
            int next_i = i + step;
            Integer cell = get_cell(next_i, y);
            if (cell == null || cell != target) {
                break;
            }
            i = next_i;
        }
        return i;
    }

    public Fill(int[] input_data, int width, int height) {

        this.width = width;
        this.height = height;
        data = input_data.clone();
        queue = new LinkedList<>();
    }

    public int[] fill(int x, int y, int target, int replace) {
        Integer start_cell = get_cell(x, y);
        if (start_cell == null || start_cell != target) {
            return data;
        }
        queue.add(new Point(x, y));
        while (!queue.isEmpty()) {
            Point n = queue.remove();
            Integer cell = get_cell(n.x, n.y);
            if (cell != null && cell == target) {
                process_one_point(n.x, n.y, target, replace);
            }
        }
        return data;
    }

}
