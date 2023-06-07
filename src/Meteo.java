import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class Meteo {
    public enum MeteoNames{
        ALEATOIRE,
        SOLEIL,
        SECHERESSE,
        NEIGE,
        PLUVIEUX,
    };
    public double ratio;

    public MeteoNames MeteoState;

    public double getMeteoRatio(MeteoNames name) throws IOException {
        switch (name){
            case ALEATOIRE:
                Random rand = new Random();
                int aleaMeteo= rand.nextInt(1,4);
                switch (aleaMeteo){
                    case 1:
                        return 1;
                    case 2:
                        return 0;
                    case 3:
                        return 0.5;
                    case 4:
                        return 2;
                    default:
                        return 1;
                }
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
        switch (name){
            case ALEATOIRE:
                return randomImageMeteo();
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
        int aleaMeteo= rand.nextInt(1,4);
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
