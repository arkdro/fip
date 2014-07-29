/*
 * a starter
 */
package org.eff;

/**
 *
 * @author user1
 */
public class Fip {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int width = Integer.parseInt(Props.props.getProperty("field_width", "20"));
        int height = Integer.parseInt(Props.props.getProperty("field_height", "20"));
        int pigs = Integer.parseInt(Props.props.getProperty("number_of_pigs", "3"));
        Place p = new Place(width, height, pigs);
        p.run();
    }
}
