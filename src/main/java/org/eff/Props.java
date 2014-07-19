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
        // why the fuck 'try-with' throws???
        try {
            try (FileInputStream in = new FileInputStream("properties")) {
                pr.load(in);
            }
        } catch (IOException e) {
            // "Oh, fuck it." (c)
        }
        return pr;
    }
}
