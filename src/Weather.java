import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.Observable;
import java.util.Random;

public class Weather extends Observable implements Serializable {

    public enum WeatherNames {
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
                    Random rand = new Random();

                    updateMeteoState();
                    Thread.sleep((rand.nextInt(15)+5)*1000); // Adjust the interval as needed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void updateMeteoState() {
        if (randomState) {
            Random rand = new Random();
            int aleaMeteo = rand.nextInt(10) + 1;  // Generate a random number between 1 and 10

            if (aleaMeteo <= 2) {  // 20% chance for PLUVIEUX
                this.meteoState = WeatherNames.PLUVIEUX;
                this.ratio = 2;
            } else if (aleaMeteo <= 6) {  // 40% chance for SOLEIL
                this.meteoState = WeatherNames.SOLEIL;
                this.ratio = 1;
            } else if (aleaMeteo <= 8) {  // 20% chance for NEIGE
                this.meteoState = WeatherNames.NEIGE;
                this.ratio = 0.5;
            } else {  // 20% chance for SECHERESSE
                this.meteoState = WeatherNames.SECHERESSE;
                this.ratio = 0;
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

    public void setRandomState(boolean randomState) {
        this.randomState = randomState;
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
