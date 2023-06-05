package Plants;

import java.awt.*;
import java.io.IOException;

public class Cauliflower extends Plants {

    public Cauliflower() throws IOException {
        super("Chou-fleur", 17, 15,4);
    }

    public static Image getImage() throws IOException {
        return Plants.getImage(3, 2);
    }
}
