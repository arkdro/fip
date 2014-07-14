/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eff;

import java.util.Random;

/**
 *
 * @author user1
 */
public class Place {

    private int width = 5;
    private int height = 5;
    private int n_pigs = 3;
    private Pig[] pigs;

    private void init_pigsty() {
        Random r = new Random();
        Pig[] p = new Pig[n_pigs];
        for (int i = 0; i < n_pigs; i++) {
            int x = r.nextInt(width);
            int y = r.nextInt(height);
            p[i] = new PigBuilder().setX(x).setY(y).createPigShort();
        }
        pigs = p;
    }

    private void print_pigsty() {
        for (Pig pig : pigs) {
            System.out.println(pig);
        }
    }

    private void pigsty_one_step() {
        for(Pig pig: pigs) {
            pig.step(0, width-1, 0, height-1);
        }
    }

    public Place() {
        init_pigsty();
    }

    public Place(int w, int h, int p) {
        width = w;
        height = h;
        n_pigs = p;
        init_pigsty();
    }

    public void run() {
        System.out.println("begin:");
        print_pigsty();
        for(int i = 0; i < 10; i++) {
            pigsty_one_step();
            System.out.println("i=" + i);
            print_pigsty();
        }
    }
}
