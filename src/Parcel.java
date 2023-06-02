import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Parcel extends JPanel {
    private BufferedImage bgParcel;
    private BufferedImage imagePlant;

    public Parcel() throws IOException {
        super();
        setBackground(Color.WHITE);
        this.bgParcel = ImageIO.read(new File("/images/Terre.png"));
        this.imagePlant = null;
    }

    public BufferedImage getImagePlant() {
        return imagePlant;
    }

    public void setImagePlant(String fileName) throws IOException {
        this.imagePlant = ImageIO.read(new File(fileName));
    }

    public void reset() {
        this.imagePlant = null;
    }

    public void pourris() {
        if (imagePlant != null) {
            // Convertir l'image en niveaux de gris
            for (int x = 0; x < imagePlant.getWidth(); x++) {
                for (int y = 0; y < imagePlant.getHeight(); y++) {
                    int rgb = imagePlant.getRGB(x, y);
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;

                    int grayLevel = (red + green + blue) / 3;
                    int gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel;

                    imagePlant.setRGB(x, y, gray);
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dessiner l'image de fond
        if (bgParcel != null) {
            g.drawImage(bgParcel, 0, 0, getWidth(), getHeight(), this);
        }

        // Dessiner l'image de la plante
        if (imagePlant != null) {
            g.drawImage(imagePlant, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
