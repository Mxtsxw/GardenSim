package Plants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class Meteo {

    public enum MeteoNames {
        ALEATOIRE,
        SOLEIL,
        SECHERESSE,
        NEIGE,
        PLUVIEUX,
    }

    private boolean randomState;
    private double ratio;
    private MeteoNames meteoState;

    public Meteo() {
        this.randomState = true;
        this.ratio = 0;
    }

    public double getRatio() {
        return ratio;
    }

    public MeteoNames getMeteoState() {
        return meteoState;
    }

    public void setMeteoState(MeteoNames meteoState) {
        this.meteoState = meteoState;
        this.ratio = getMeteoRatio(meteoState);
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
                    this.meteoState = MeteoNames.SOLEIL;
                    this.ratio = 1;
                    break;
                case 2 :
                    this.meteoState = MeteoNames.SECHERESSE;
                    this.ratio = 0;
                    break;
                case 3 :
                    this.meteoState = MeteoNames.NEIGE;
                    this.ratio = 0.5;
                    break;
                case 4 :
                    this.meteoState = MeteoNames.PLUVIEUX;
                    this.ratio = 2;
                    break;
            }
        }
    }

    public double getMeteoRatio(MeteoNames name) {
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

    public Image getMeteoIcon(MeteoNames name) throws IOException {
        switch (name) {
            case SOLEIL:
                return ImageIO.read(getClass().getResource("/resources/images/sun.png"));
            case SECHERESSE:
                return ImageIO.read(getClass().getResource("/resources/images/hot.png"));
            case NEIGE:
                return ImageIO.read(getClass().getResource("/resources/images/cold.png"));
            case PLUVIEUX:
                return ImageIO.read(getClass().getResource("/resources/images/cloud.png"));
            default:
                return ImageIO.read(getClass().getResource("/resources/images/sun.png"));
        }
    }

    public Image randomImageMeteo() throws IOException {
        Random rand = new Random();
        int aleaMeteo = rand.nextInt(3) + 1;
        switch (aleaMeteo) {
            case 1:
                return ImageIO.read(getClass().getResource("/resources/images/sun.png"));
            case 2:
                return ImageIO.read(getClass().getResource("/resources/images/hot.png"));
            case 3:
                return ImageIO.read(getClass().getResource("/resources/images/cold.png"));
            case 4:
                return ImageIO.read(getClass().getResource("/resources/images/cloud.png"));
            default:
                return ImageIO.read(getClass().getResource("/resources/images/sun.png"));
        }
    }
}
