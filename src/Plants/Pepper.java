package Plants;

import java.awt.*;
import java.io.IOException;

public class Pepper extends Plants {

    public Pepper() throws IOException {
        super("Piment", 9, 12, 9);
    }

    public static Image getImage() throws IOException {
        return Plants.getImage(4, 4);
    }

    @Override
    public int getUnitPrice() {
        return super.getUnitPrice();
    }
}
