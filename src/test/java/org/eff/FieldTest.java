/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eff;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 * @author user1
 */
public class FieldTest {

    private void cut_corner(int width, int height, Field field) {
        for (int x = width / 2 + 1; x < width; x++) {
            for (int y = height / 2 + 1; y < height; y++) {
                field.set_cell(x, y, Cell.GRASS);
            }
        }
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public FieldTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void test_look_ahead1() {
        int width = 3;
        int height = 3;
        Field field = new Field(width, height);
        thrown.expect(AssertionError.class);
        thrown.expectMessage("current is OUT, x=3, y=1, w=3, h=3");
        field.look_ahead(3, 1, 1, 1);
    }

    @Test
    public void test_look_ahead2() {
        int width = 3;
        int height = 3;
        Field field = new Field(width, height);
        field.set_cell(0, 0, Cell.GRASS);
        Wall wall = field.look_ahead(1, 1, -1, -1);
        assertEquals(Wall.CORNER, wall);
    }

    @Test
    public void test_look_ahead3() {
        int width = 3;
        int height = 3;
        Field field = new Field(width, height);
        field.set_cell(0, 0, Cell.GRASS);
        field.set_cell(1, 0, Cell.GRASS);
        field.set_cell(2, 0, Cell.GRASS);
        Wall wall = field.look_ahead(1, 1, -1, -1);
        assertEquals(Wall.HORIZONTAL_WALL, wall);
    }

    @Test
    public void test_look_ahead4() {
        int width = 3;
        int height = 3;
        Field field = new Field(width, height);
        field.set_cell(0, 0, Cell.GRASS);
        field.set_cell(0, 1, Cell.GRASS);
        field.set_cell(0, 2, Cell.GRASS);
        Wall wall = field.look_ahead(1, 1, -1, -1);
        assertEquals(Wall.VERTICAL_WALL, wall);
    }

    @Test
    public void test_look_ahead5() {
        int width = 6;
        int height = 6;
        Field field = new Field(width, height);
        cut_corner(width, height, field);
        //field.print_field();
        Wall wall = field.look_ahead(5, 3, 1, 1);
        assertEquals(Wall.CORNER, wall);
    }

}
