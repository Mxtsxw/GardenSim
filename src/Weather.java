import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Random;

public class Weather extends Observable {

    public static enum WeatherNames {
        ALEATOIRE,
        SOLEIL,
        SECHERESSE,
        NEIGE,
        PLUVIEUX,
    }

    private boolean randomState;
    private double ratio;
    private WeatherNames meteoState;

    public Weather() {
        this.randomState = true;
        this.ratio = 0;
        startUpdatingMeteoState();
    }

    public double getRatio() {
        return ratio;
    }

    public WeatherNames getWeatherState() {
        return meteoState;
    }

    public void setWeatherState(WeatherNames meteoState) {
        this.meteoState = meteoState;
        this.ratio = getWeatherRatio(meteoState);

        setChanged();
        notifyObservers(this);
    }

    public void startUpdatingMeteoState() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    updateMeteoState();
                    Thread.sleep(3000); // Adjust the interval as needed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void updateMeteoState() {
        if (randomState){
            Random rand = new Random();
            int aleaMeteo = rand.nextInt(3) + 1;

            switch (aleaMeteo){
                case 1 :
                    this.meteoState = WeatherNames.SOLEIL;
                    this.ratio = 1;
                    break;
                case 2 :
                    this.meteoState = WeatherNames.SECHERESSE;
                    this.ratio = 0;
                    break;
                case 3 :
                    this.meteoState = WeatherNames.NEIGE;
                    this.ratio = 0.5;
                    break;
                case 4 :
                    this.meteoState = WeatherNames.PLUVIEUX;
                    this.ratio = 2;
                    break;
            }
        }

        setChanged();
        notifyObservers(this);
    }

    public static double getWeatherRatio(WeatherNames name) {
        switch (name) {
            case SOLEIL:
                return 1;
            case SECHERESSE:
                return 0;
            case NEIGE:
                return 0.5;
            case PLUVIEUX:
                return 2;
            default:
                return 1;
        }
    }

    public static ImageIcon getWeatherIcon(WeatherNames name) throws IOException {
        switch (name) {
            case SOLEIL:
                return new ImageIcon(Weather.class.getResource("/resources/images/sun.png"));
            case SECHERESSE:
                return new ImageIcon(Weather.class.getResource("/resources/images/hot.png"));
            case NEIGE:
                return new ImageIcon(Weather.class.getResource("/resources/images/cold.png"));
            case PLUVIEUX:
                return new ImageIcon(Weather.class.getResource("/resources/images/cloud.png"));
            default:
                return new ImageIcon(Weather.class.getResource("/resources/images/sun.png"));
        }
    }
}
