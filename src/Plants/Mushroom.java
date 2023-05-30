package Plants;

import java.io.IOException;

public class Mushroom extends Plants {

    public Mushroom() throws IOException {
        super("Champignon", 7, 7);
        super.setIcon(0,1);
    }
}
