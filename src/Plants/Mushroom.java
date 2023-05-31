package Plants;

import java.io.IOException;

public class Mushroom extends Plants {

    public Mushroom() throws IOException {
        super("Champignon", 7, 2, 1);
        super.setIcon(1,0);
    }
}
