import Plants.PlantNames;
import Plants.Plants;

import java.util.Observable;
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
    private Weather weather;

    private int argent;

    public Model(Weather weather){
        this.grid = new Plants[10][10];
        this.argent=100;
        this.weather = weather;
    }

    public Model(Plants[][] grid, int argent) {
        this.grid = grid;
        this.argent=argent;
    }

    public PlantNames getSelected() {
        return selected;
    }

    public void setWeatherRandomState(boolean state) {
        this.weather.setRandomState(state);
    }

    public void resetSelection() {
        this.selected = null;
    }

    public void setSelected(PlantNames selected) {
        this.selected = selected;
    }

    public void setPlants(int i, int j, Plants plant){
        this.grid[i][j] = plant;
        if (plant != null) System.out.println("Log: ["+ plant.getName() +"] ajouté sur case (" + i + "," +j + ")");
        setChanged();
        notifyObservers(this);
    }

    public Plants[][] getPlants() {
        return grid;
    }

    public String getStringArgent(){
        return String.valueOf(this.argent);
    }

    public int getArgent(){return this.argent;}
    public void setArgent(int x){
        this.argent= x;
        setChanged();
        notifyObservers(this);
    }

    public void augmentation(int x){
        this.argent+=x;
        setChanged();
        notifyObservers(this);
    }

    public void diminution(int x){
        this.argent-=x;
        if (this.argent<0) {this.argent=0;}
        setChanged();
        notifyObservers(this);
    }

    public boolean isEnoughMoney(int x){
        return (this.argent - x) >=0;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void togglePause() {
        isPaused = !isPaused;
        setChanged();
        notifyObservers(this);
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

    public Weather getMeteo() {
        return weather;
    }

    public void setWeather(Weather.WeatherNames weather){
        this.weather.setWeatherState(weather);
    }

    public void UpdatePlants() {
        for (int i = 0; i < this.grid.length; i++) {
            for (int j = 0; j < this.grid.length; j++) {
                if (this.grid[i][j] != null){
                    this.grid[i][j].updateGerminationState(this.weather.getRatio());
                }
            }
        }
        setChanged();
        notifyObservers(this);
    }

    // La fonction run va être déplacé dans la classe d'ordonnanceur.
}

