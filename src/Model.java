import Plants.PlantNames;

import java.util.Observable;
import java.util.Random;

/**
 * Classe représentant le modèle.
 * @extends Observable : La modèle va notifier les autres classes abonné (notamment la view) lorsque le modèle est mise à jour.
 * @implemtents Runnable : Permet de faire tourner le modèle dans un Thread en continue.
 * @attributes :
 *  - boolean[10][10] grid : Représente l'état du potager.
 *  - boolean isPaused : Indique l'état du jeu (si il est en pause).
 *  - int cooldown : Le taux de rafraichissement du modèle, par défaut à 1 seconde.
 */
public class Model extends Observable implements Runnable {


    protected boolean[][] grid;
    private boolean isPaused = false;
    protected int cooldown = 1000;
    private PlantNames selected = null;

    private int argent;

    public Model(){
        this.grid = new boolean[10][10];
        this.argent=100;
    }

    public Model(boolean[][] grid, int argent) {
        this.grid = grid;
        this.argent=argent;
    }

    public PlantNames getSelected() {
        return selected;
    }

    public void setSelected(PlantNames selected) {
        this.selected = selected;
    }

    public void augmentation(int x){
        this.argent+=x;
        setChanged();
        notifyObservers();
    }

    public void diminution(int x){
        this.argent-=x;
        setChanged();
        notifyObservers();
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void togglePause() {
        isPaused = !isPaused;
        setChanged();
        notifyObservers();
    }

    public void setRefreshRate(int i) {
        this.cooldown = i;
        if (this.cooldown > 5000) this.cooldown = 5000;
        if (this.cooldown < 100) this.cooldown = 100;
    }

    public void lowerRefreshRate(int i){
        this.cooldown -= i;
        if (this.cooldown < 100) this.cooldown = 100;
    }

    public void increaseRefreshRate(int i){
        this.cooldown += i;
        if (this.cooldown > 5000) this.cooldown = 5000;
    }

    /**
     * Met à jours le modèle à chaque intervalle de temps selon le taux de rafraichissement
     */
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

            try {
                Thread.sleep(this.cooldown);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

