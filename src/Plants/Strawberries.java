package Plants;

import java.awt.*;
import java.io.IOException;

public class Strawberries extends Plants {

    public Strawberries() throws IOException {
        super("Fraises", 15, 6, 15);
    }

    public static Image getImage() throws IOException {
        return Plants.getImage(1, 0);
    }
}