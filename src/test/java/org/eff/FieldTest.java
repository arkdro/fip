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

}
