import java.util.Observable;
import java.util.Random;

public class Model extends Observable implements Runnable {

    protected boolean[][] grid;

    public Model(){
        this.grid = new boolean[10][10];
    }

    public Model(boolean[][] grid) {
        this.grid = grid;
    }

    @Override
    public void run() {
        while (true){
            Random random = new Random();

            // Used to compute the probability of showing a cell
            for(int i=0; i<10; i++)
                for(int j=0; j<10; j++) {
                    grid[i][j] = random.nextInt(3) == 0;
                }

            // Notify change
            setChanged();
            notifyObservers();

            // 1 second interval loop
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
