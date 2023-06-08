package Plants;

import java.awt.*;
import java.io.IOException;

public class Mushroom extends Plants {

    public Mushroom() throws IOException {
        super("Champignon", 7, 2, 1);
    }

    public static Image getImage() throws IOException {
        return Plants.getImage(1, 0);
    }

    @Override
    public int getUnitPrice() {
        return super.getUnitPrice();
    }
}
