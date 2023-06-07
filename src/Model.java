import Plants.PlantNames;
import Plants.Plants;

import java.util.Observable;
import java.util.Random;
import java.io.Serializable;

/**
 * Classe représentant le modèle.
 * @extends Observable : La modèle va notifier les autres classes abonné (notamment la view) lorsque le modèle est mise à jour.
 * @implemtents Runnable : Permet de faire tourner le modèle dans un Thread en continue.
 * @attributes :
 *  - boolean[10][10] grid : Représente l'état du potager.
 *  - boolean isPaused : Indique l'état du jeu (si il est en pause).
 *  - int cooldown : Le taux de rafraichissement du modèle, par défaut à 1 seconde.
 */
public class Model extends Observable implements Serializable {


    protected Plants[][] grid;
    private boolean isPaused = false;
    protected int cooldown = 1000;
    private PlantNames selected = null;

    private int argent;

    public Model(){
        this.grid = new Plants[10][10];
        this.argent=100;
    }

    public Model(Plants[][] grid, int argent) {
        this.grid = grid;
        this.argent=argent;
    }

    public PlantNames getSelected() {
        return selected;
    }

    public void setSelected(PlantNames selected) {
        this.selected = selected;
    }

    public void setPlants(int i, int j, Plants plant){
        this.grid[i][j] = plant;
        System.out.println("Plante ajouté sur " + i + " " +j);
        setChanged();
        notifyObservers();
    }

    public Plants[][] getPlants() {
        return grid;
    }

    public String getStringArgent(){
        return String.valueOf(this.argent);
    }

    public int getArgent() {
        return argent;
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

    public int getCooldown() {
        return cooldown;
    }

    public void UpdatePlants() {
        for (int i = 0; i < this.grid.length; i++) {
            for (int j = 0; j < this.grid.length; j++) {
                if (this.grid[i][j] != null){
                    this.grid[i][j].updateGerminationState();
                }
            }
        }
        setChanged();
        notifyObservers();
    }

    // La fonction run va être déplacé dans la classe d'ordonnanceur.
}

