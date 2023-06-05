package Plants;

import java.awt.*;
import java.io.IOException;

public class Salad extends Plants {

    public Salad() throws IOException {
        super("Salade", 3, 20, 2);
    }

    public static Image getImage() throws IOException {
        return Plants.getImage(0, 0);
    }
}
