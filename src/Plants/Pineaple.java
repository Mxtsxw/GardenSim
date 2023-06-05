package Plants;

import java.awt.*;
import java.io.IOException;

public class Pineaple extends Plants {

    public Pineaple() throws IOException {
        super("Ananas", 70, 3, 5);
    }

    public static Image getImage() throws IOException {
        return Plants.getImage(7, 2);
    }
}
