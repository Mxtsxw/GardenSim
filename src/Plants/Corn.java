package Plants;

import java.awt.*;
import java.io.IOException;

public class Corn extends Plants {

    public Corn() throws IOException {
        super("Maïs", 50, 10,8);
    }

    public static Image getImage() throws IOException {
        return Plants.getImage(5, 1);
    }
}