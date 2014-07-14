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

/**
 *
 * @author user1
 */
public class PigTest {

    public PigTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void create_pig() {
        Pig pig = new PigBuilder()
                .setX(1)
                .setY(1)
                .setSpeed(1)
                .setDir(Direction.NW)
                .createPig();
    }

    @Test
    public void test_update_coordinates1() {
        Pig pig = new PigBuilder()
                .setX(1)
                .setY(1)
                .setSpeed(1)
                .setDir(Direction.NW)
                .createPig();
        pig.update_coordinates(0, 3, 0, 3);
        assertEquals(0, pig.getX());
        assertEquals(2, pig.getY());
        assertEquals(Direction.NW, pig.getDir());
    }

    @Test
    public void test_update_coordinates2() {
        Pig pig = new PigBuilder()
                .setX(0)
                .setY(1)
                .setSpeed(1)
                .setDir(Direction.NW)
                .createPig();
        pig.update_coordinates(0, 3, 0, 3);
        assertEquals(0, pig.getX());
        assertEquals(2, pig.getY());
        assertEquals(Direction.NE, pig.getDir());
    }

    @Test
    public void test_update_coordinates3() {
        Pig pig = new PigBuilder()
                .setX(3)
                .setY(1)
                .setSpeed(1)
                .setDir(Direction.NE)
                .createPig();
        pig.update_coordinates(0, 3, 0, 3);
        assertEquals(3, pig.getX());
        assertEquals(2, pig.getY());
        assertEquals(Direction.NW, pig.getDir());
    }

    /**
     * Test of step method, of class Pig.
     */
    @Test
    public void test_step() {
    }
}
