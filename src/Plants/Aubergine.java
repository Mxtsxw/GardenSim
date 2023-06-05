package Plants;

import java.awt.*;
import java.io.IOException;

public class Aubergine extends Plants{
    public Aubergine() throws IOException {
        super("Aubergine", 50, 5,15);
    }

    public static Image getImage() throws IOException {
        return Plants.getImage(2, 1);
    }
}
