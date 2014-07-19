/*
 * a place of the walking pig
 */
package org.eff;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 *
 * @author user1
 */
public class Place {
    private int CELL_SIZE = 10;
    private int EXTRA_SIZE = 30;
    private int width = 5;
    private int height = 5;
    private int n_pigs = 3;
    private Field field;
    private Pig[] pigs;
    private boolean initial_plot = true;

    private void init() {
        init_field();
        init_pigsty();
    }

    private void init_field() {
        field = new Field(width, height);
    }

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
            pig.step(field);
        }
    }

    private MyDrawPanel prepare_plot() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MyDrawPanel drawPanel = new MyDrawPanel();

        frame.getContentPane().add(drawPanel);
        frame.setSize(width * CELL_SIZE + EXTRA_SIZE, height * CELL_SIZE + EXTRA_SIZE);
        frame.setVisible(true);
        return drawPanel;
    }

    private Color choose_color(int i) {
        Color[] colors = {
            Color.BLACK,
            Color.BLUE,
            Color.CYAN,
            Color.GREEN,
            Color.MAGENTA,
            Color.ORANGE,
            Color.PINK,
            Color.RED,
            Color.YELLOW
        };
        int idx = i % colors.length;
        return colors[idx];
    }

    private void plot_field(Graphics g) {
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, width * CELL_SIZE, height * CELL_SIZE);
    }

    private void plot_pigs(Graphics g) {
        // g.fillOval(x, y, 40, 40);
        // g.setColor(Color.green);
        int idx = 0;
        for (Pig pig : pigs) {
            Color color = choose_color(idx++);
            g.setColor(color);
            plot_field_point(pig.getX(), pig.getY(), g);
            //System.out.println("" + pig + ", " + color);
        }
    }

    private void plot_field_point(int x, int y, Graphics g) {
        int scaled_x = x * CELL_SIZE;
        int scaled_y = y * CELL_SIZE;
        g.fillRect(scaled_x, scaled_y, CELL_SIZE, CELL_SIZE);
    }

    public Place() {
        init();
    }

    public Place(int w, int h, int p) {
        width = w;
        height = h;
        n_pigs = p;
        init();
    }

    public void run() {
        MyDrawPanel drawPanel = prepare_plot();
        drawPanel.repaint();
        initial_plot = false;
        for(int i = 0; i < 130; i++){
//            System.out.println("i=" + i);
//            print_pigsty();
            pigsty_one_step();
            drawPanel.repaint();
            try {
                Thread.sleep(100);
            } catch (Exception ex) {}
        }
    }

    class MyDrawPanel extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            if (initial_plot) {
                g.setColor(Color.decode(
                        Props.props.getProperty("border_color", "#959595")));
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
            }
            plot_field(g);
            plot_pigs(g);
        }
    }
}
