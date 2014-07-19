package org.eff;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author user1
 */
public class Props {

    public static Properties props = null;

    static {
        props = get_props();
    }

    public static Properties get_props() {
        Properties pr = new Properties();
        try {
            pr.load(Props.class.getResourceAsStream("/properties"));
        } catch (IOException e) {
            System.out.println("props, exception:" + e);
        }
        return pr;
    }
}
