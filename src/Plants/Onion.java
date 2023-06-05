package Plants;

import java.awt.*;
import java.io.IOException;

public class Onion extends Plants {

    public Onion() throws IOException {
        super("Oignon", 20, 20, 4);
    }

    public static Image getImage() throws IOException {
        return Plants.getImage(7, 1);
    }
}