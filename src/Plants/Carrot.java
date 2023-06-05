package Plants;

import java.awt.*;
import java.io.IOException;

public class Carrot extends Plants{
    public Carrot() throws IOException {
        super("Carrotte", 10, 10,1);
    }

    public static Image getImage() throws IOException {
        return Plants.getImage(1, 1);
    }
}
