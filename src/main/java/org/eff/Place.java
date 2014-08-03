/*
 * a place of the walking pig
 */
package org.eff;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Set;
import javax.swing.*;

/**
 *
 * @author user1
 */
public class Place {
    private int CELL_SIZE = 10;
    private int EXTRA_SIZE = 30;
    private int TEXT_SIZE = 30;
    private int width = 5;
    private int height = 5;
    private Field field;
    private Pig[] pigs;
    private Mower mower;
    private boolean initial_plot = true;
    private int right_gap = 3;
    private int left_gap = 3;
    private int top_gap = 2;
    private int bottom_gap = 2;
    private int start_level;
    private int run_loop_delay;
    private int run_loop_delay_step; // when level goes up
    private int add_dirt_pig_inactivity_timeout;
    private int dirt_pigs_max_number;
    private int dirt_pigs_init_number;
    private int current_run_loop_delay;
    private JLabel label;
    private int current_level;

    private void init() {
        init_props();
    }

    private void init_level(int level) {
        init_props();
        init_field();
        init_mower();
        init_pigsty(level);
        init_run_props();
        current_level = level;

    }

    private void init_run_props() {
        current_run_loop_delay = run_loop_delay - start_level * run_loop_delay_step;
        if(run_loop_delay < 0)
            run_loop_delay = 0;
    }

    private void init_props() {
        start_level = Integer.parseInt(
                Props.props.getProperty("start_level", "1"));
        run_loop_delay = Integer.parseInt(
                Props.props.getProperty("run_loop_delay", "100"));
        run_loop_delay_step = Integer.parseInt(
                Props.props.getProperty("run_loop_delay_step", "10"));
        add_dirt_pig_inactivity_timeout = Integer.parseInt(
                Props.props.getProperty("add_dirt_pig_inactivity_timeout", "60000"));
        dirt_pigs_max_number = Integer.parseInt(
                Props.props.getProperty("dirt_pigs_max_number", "3"));
        dirt_pigs_init_number = Integer.parseInt(
                Props.props.getProperty("dirt_pigs_init_number", "2"));
    }

    private void init_field() {
        field = new Field(width, height);
        plant_weed();
    }

    private void plant_weed() {
        int min_field_width = 2;
        int horizontal_gap = left_gap + right_gap + min_field_width;
        int min_field_height = 2;
        int vertical_gap = top_gap + bottom_gap + min_field_height;
        if(width < horizontal_gap)
            return;
        if(height < vertical_gap)
            return;
        for(int x = left_gap; x < width - right_gap; x++) {
            for(int y = top_gap; y < height - bottom_gap; y++) {
                field.set_cell(x, y, Cell.GRASS);
            }
        }
    }

    private void init_mower() {
        mower = new Mower(0, 0);
    }

    private void init_pigsty(int level) {
        Pig[] dirt = init_dirt_pigs(level);
        Pig[] grass = init_grass_pigs(level);
        int total = dirt.length + grass.length;
        Pig[] p = new Pig[total];
        System.arraycopy(dirt, 0, p, 0, dirt.length);
        System.arraycopy(grass, 0, p, dirt.length, grass.length);
        pigs = p;
    }

    private Pig[] init_grass_pigs(int level) {
        Random r = new Random();
        int num_of_pigs = level;
        Pig[] p = new Pig[num_of_pigs];
        int max_x = width - right_gap - left_gap;
        int max_y = height - top_gap - bottom_gap;
        for (int i = 0; i < num_of_pigs; i++) {
            int x = r.nextInt(max_x) + right_gap;
            int y = r.nextInt(max_y) + top_gap;
            int speed = choose_speed(level);
            p[i] = new PigBuilder()
                    .setX(x)
                    .setY(y)
                    .setSpeed(speed)
                    .createPigShort();
        }
        return p;
    }

    private Pig[] init_dirt_pigs(int level) {
        Random r = new Random();
        Pig[] p = new Pig[dirt_pigs_init_number];
        int max_x = right_gap + left_gap;
        int max_y = top_gap + bottom_gap;
        for(int i = 0; i < dirt_pigs_init_number; i++) {
            int x = r.nextInt(width);
            int y = height - 1;
            int speed = choose_speed(level);
            p[i] = new PigBuilder()
                    .setX(x)
                    .setY(y)
                    .setSpeed(speed)
                    .createPigShort();
        }
        return p;
    }

    private int choose_speed(int max) {
        int r = (int) (Math.random() * max);
        if(r < max/2) {
            r = Pig.MAX_SPEED;
        }
        return r;
    }

    private void print_pigsty() {
        for (Pig pig : pigs) {
            System.out.println(pig);
        }
    }

    private void pigsty_one_step() {
        for(Pig pig: pigs) {
            pig.step(field, mower);
        }
    }

    private void mower_one_step() {
        mower.step(field);
    }

    private JFrame prepare_frame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    private void prepare_label(JFrame frame) {
        label = new JLabel("label text");
        frame.getContentPane().add(BorderLayout.SOUTH, label);
    }

    private MyDrawPanel prepare_panel(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MyDrawPanel drawPanel = new MyDrawPanel();

        frame.getContentPane().add(drawPanel);
        frame.setSize(width * CELL_SIZE + EXTRA_SIZE,
                height * CELL_SIZE + TEXT_SIZE + EXTRA_SIZE);
        frame.setVisible(true);
        return drawPanel;
    }

    private void prepare_keys(MyDrawPanel panel) {
        addAction(panel, "LEFT", Direction.W, Move.MOVE);
        addAction(panel, "RIGHT", Direction.E, Move.MOVE);
        /**
         * graphics coordinates increase from the top of the screen to the bottom.
         * So 'UP' key moves to smaller coordinates, namely 'South'.
         * And 'DOWN' key moves to bigger coordinates, e.g. 'North'.
         */
        addAction(panel, "UP", Direction.S, Move.MOVE);
        addAction(panel, "DOWN", Direction.N, Move.MOVE);
        addAction(panel, "SPACE", null, Move.STOP);
        addAction(panel, "ENTER", null, Move.STOP);
    }

    private void show_final_picture(Graphics g) {
        g.setColor(Color.decode("#a5a5a5"));
        g.fillRect(0, 0, width * CELL_SIZE, height * CELL_SIZE);
        label.setText("Game Over");
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

    /**
     * TODO: make if faster: plot only changed cells.
     */
    private void plot_field(Graphics g) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++){
                Cell cell = field.get_cell(x, y);
                Color color = choose_cell_color(cell);
                g.setColor(color);
                plot_field_point(x, y, g);
            }
        }
    }

    private Color choose_cell_color(Cell cell) {
        switch(cell) {
            case GRASS:
                return Color.decode(
                        Props.props.getProperty("grass_color", "#20a050"));
            case DIRT:
                return Color.decode(
                        Props.props.getProperty("dirt_color", "#f5f5f5"));
            case STEP:
                return Color.decode(
                        Props.props.getProperty("step_color", "#505050"));
            default:
                return Color.decode(
                        Props.props.getProperty("dirt_color", "#f5f5f5"));
        }
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

    private void plot_mower(Graphics g) {
        plot_mower_steps(g);
        plot_mower_oneself(g);
    }

    private void plot_mower_oneself(Graphics g) {
        int idx = 0;
        Color color = choose_color(idx);
        g.setColor(color);
        plot_field_point(mower.getX(), mower.getY(), g);
    }

    private void plot_mower_steps(Graphics g) {
        Set<Point> steps = mower.get_steps();
        Color color = Color.decode(
                Props.props.getProperty("step_color", "#505050"));
        g.setColor(color);
        for (Point c: steps) {
            plot_field_point(c.x, c.y, g);
        }
    }

    private void plot_field_point(int x, int y, Graphics g) {
        int scaled_x = x * CELL_SIZE;
        int scaled_y = y * CELL_SIZE;
        g.fillRect(scaled_x, scaled_y, CELL_SIZE, CELL_SIZE);
    }

    private void plot_level_info(Graphics g) {
        String text = build_label_text();
        label.setText(text);
    }

    private String build_label_text() {
        return "Level: " + current_level + ", Pot: " + mower.get_pots();
    }

    public Place() {
        init();
    }

    public Place(int w, int h, int p) {
        width = w;
        height = h;
        init();
    }

    public void run() {
        JFrame frame = prepare_frame();
        prepare_label(frame);
        MyDrawPanel drawPanel = prepare_panel(frame);
        prepare_keys(drawPanel);
        drawPanel.repaint();
        initial_plot = false;

        LStatus res = LStatus.LEVEL_COMPLETED;
        for(int i = start_level; res == LStatus.LEVEL_COMPLETED; i++) {
            res = run_one_level(i, drawPanel);
            if (res == LStatus.NO_MORE_POTS) {
                break;
            }
        }
        drawPanel.repaint(); // show final picture
        try {
            Thread.sleep(3000);
        } catch (Exception ex) {}
        frame.dispose();
    }

    public LStatus run_one_level(int level, MyDrawPanel drawPanel) {
        init_level(level);

        LStatus st = null;
        for(int i = 0; i < 130; ){
            mower_one_step();
            if (mower.just_fixed_steps()) {
                field.update_mowed_regions(pigs);
                field.update_mowed_percentage();
                mower.clear_just_fixed_steps();
            }
            if(field.get_mowed_percentage() > 0.67) {
                st = LStatus.LEVEL_COMPLETED;
                break;
            }
            pigsty_one_step();
            if(!mower.has_pots()) {
                st = LStatus.NO_MORE_POTS;
                break;
            }
            drawPanel.repaint();
            try {
                Thread.sleep(current_run_loop_delay);
            } catch (Exception ex) {}
        }
        return st;
    }

    class MyDrawPanel extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            if (initial_plot) {
                g.setColor(Color.decode(
                        Props.props.getProperty("border_color", "#959595")));
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
            }
            if (mower.has_pots()) {
                plot_field(g);
                plot_mower(g);
                plot_pigs(g);
                plot_level_info(g);
            } else {
                show_final_picture(g);
            }
        }
    }

    public MotionAction addAction(JComponent component, String name,
            Direction dir, Move move) {
        MotionAction action = new MotionAction(name, dir, move);

        KeyStroke pressedKeyStroke = KeyStroke.getKeyStroke(name);
        InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(pressedKeyStroke, name);
        component.getActionMap().put(name, action);

        return action;
    }

    private class MotionAction extends AbstractAction implements ActionListener {

        private Direction dir;
        private Move move;

        public MotionAction(String name, Direction dir, Move move) {
            super(name);

            this.dir = dir;
            this.move = move;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // update direction for manually controlled pig
            mower.change_motion(dir, move);
        }
    }

}
