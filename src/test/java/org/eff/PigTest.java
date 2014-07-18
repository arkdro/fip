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
        int width = 3;
        int height = 3;
        Field field = new Field(width, height);
        Pig pig = new PigBuilder()
                .setX(1)
                .setY(1)
                .setSpeed(1)
                .setDir(Direction.NW)
                .createPig();
        pig.update_coordinates(field);
        assertEquals(0, pig.getX());
        assertEquals(2, pig.getY());
        assertEquals(Direction.NW, pig.getDir());
    }

    @Test
    public void test_update_coordinates2() {
        int width = 3;
        int height = 3;
        Field field = new Field(width, height);
        Pig pig = new PigBuilder()
                .setX(0)
                .setY(1)
                .setSpeed(1)
                .setDir(Direction.NW)
                .createPig();
        pig.update_coordinates(field);
        assertEquals(1, pig.getX());
        assertEquals(2, pig.getY());
        assertEquals(Direction.NE, pig.getDir());
    }

    @Test
    public void test_update_coordinates3() {
        int width = 3;
        int height = 3;
        Field field = new Field(width, height);
        Pig pig = new PigBuilder()
                .setX(2)
                .setY(1)
                .setSpeed(1)
                .setDir(Direction.NE)
                .createPig();
        pig.update_coordinates(field);
        assertEquals(1, pig.getX());
        assertEquals(2, pig.getY());
        assertEquals(Direction.NW, pig.getDir());
    }

    @Test
    public void test_update_coordinates4() {
        int width = 3;
        int height = 3;
        Field field = new Field(width, height);
        Pig pig = new PigBuilder()
                .setX(2)
                .setY(2)
                .setSpeed(1)
                .setDir(Direction.NE)
                .createPig();
        pig.update_coordinates(field);
        assertEquals(1, pig.getX());
        assertEquals(1, pig.getY());
        assertEquals(Direction.SW, pig.getDir());
    }

    /**
     * Test of step method, of class Pig.
     */
    @Test
    public void test_step1() {
        int width = 3;
        int height = 3;
        Field field = new Field(width, height);
        Pig pig = new PigBuilder()
                .setX(1)
                .setY(1)
                .setSpeed(Pig.MAX_SPEED + 1)
                .setDir(Direction.NE)
                .createPig();
        pig.step(field);
        assertEquals(2, pig.getX());
        assertEquals(2, pig.getY());
    }

    @Test
    public void test_step2() {
        int width = 3;
        int height = 3;
        Field field = new Field(width, height);
        Pig pig = new PigBuilder()
                .setX(1)
                .setY(1)
                .setSpeed(Pig.MAX_SPEED - 1)
                .setDir(Direction.NE)
                .createPig();
        pig.step(field);
        assertEquals(1, pig.getX());
        assertEquals(1, pig.getY());
    }
}
