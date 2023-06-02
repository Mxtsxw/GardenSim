package Plants;

import java.io.IOException;

public class Corn extends Plants {

    public Corn() throws IOException {
        super("Maïs", 50, 10,8);
        super.setIcon(5,1);
    }
}