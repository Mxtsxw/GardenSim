package Plants;

import java.awt.*;
import java.io.IOException;

public class Pineapple extends Plants {

    public Pineapple() throws IOException {
        super("Ananas", 70, 3, 5);
    }

    public static Image getImage() throws IOException {
        return Plants.getImage(6, 2);
    }
}
