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
import org.junit.Ignore;

/**
 *
 * @author user1
 */
public class DirectionTest {

    public DirectionTest() {
    }

    /**
     * Test of getDx method, of class Direction.
     */
    @Test
    public void testGetDx() {
        System.out.println("getDx");
        Direction instance = Direction.NE;
        int exp_x = 1;
        int act_x = instance.getDx();
        assertEquals(exp_x, act_x);
    }

    /**
     * Test of getDy method, of class Direction.
     */
    @Test
    public void testGetDy() {
        System.out.println("getDy");
        Direction instance = Direction.NE;
        int exp_y = 1;
        int act_y = instance.getDy();
        assertEquals(exp_y, act_y);
    }

    /**
     * Test of flip_x_dir method, of class Direction.
     */
    @Test
    public void testFlip_x_dir() {
        System.out.println("flip_x_dir");
        Direction d = Direction.NW;
        Direction exp = Direction.NE;
        Direction act = Direction.flip_x_dir(d);
        assertEquals(exp, act);
    }

    /**
     * Test of flip_y_dir method, of class Direction.
     */
    @Test
    public void testFlip_y_dir() {
        System.out.println("flip_y_dir");
        Direction d = Direction.NE;
        Direction exp = Direction.SE;
        Direction act = Direction.flip_y_dir(d);
        assertEquals(exp, act);
    }

}
