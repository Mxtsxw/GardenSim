import Plants.Plants;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Parcel extends JPanel {

    private ImageIcon backgroundImage;
    private Image imagePlant;

    private Plants plant;

    public Parcel(){
        super();

        this.plant = null;
        this.imagePlant = null;

        backgroundImage = new ImageIcon(getClass().getResource("/resources/images/soilTexture.jpg"));
    }

    public Image getImagePlant() {
        return imagePlant;
    }

    public void setImagePlant(Image image) {
        this.imagePlant = image;
    }

    public Plants getPlant() {
        return plant;
    }

    public void setPlant(Plants plant) {
        this.plant = plant;
    }

    public void reset() {
        this.imagePlant = null;
    }

    /**
     * Applique un filtre sur l'image de la plante.
     */
//    public void pourris() {
//        if (imagePlant != null) {
//            // Convertir l'image en niveaux de gris
//            for (int x = 0; x < imagePlant.getWidth(); x++) {
//                for (int y = 0; y < imagePlant.getHeight(); y++) {
//                    int rgb = imagePlant.getRGB(x, y);
//                    int red = (rgb >> 16) & 0xFF;
//                    int green = (rgb >> 8) & 0xFF;
//                    int blue = rgb & 0xFF;
//
//                    int grayLevel = (red + green + blue) / 3;
//                    int gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel;
//
//                    imagePlant.setRGB(x, y, gray);
//                }
//            }
//        }
//    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dessiner l'image de fond
        if (backgroundImage != null) {
            g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        }

        // Dessiner l'image de la plante
        if (imagePlant != null && plant != null) {
            switch (this.plant.getGerminationState()){
                case BEGINNING:
                    drawScaledImage(g, 4);
                    break;
                case INTERMEDIATE:
                    drawScaledImage(g, 3);
                    break;
                case ADVANCED:
                    drawScaledImage(g, 2);
                    break;
                case GERMINATED:
                    drawScaledImage(g, 1);
                    break;
                default:
                    break;
            }
        }
    }

    public void drawScaledImage(Graphics g, int ratio){
        int scaledWidth = getWidth() / ratio;  // Adjust the scaling as needed
        int scaledHeight = getHeight() / ratio;
        int xPos = (getWidth() - scaledWidth) / ratio;  // Calculate the x position for centering
        int yPos = (getHeight() - scaledHeight) / ratio;  // Calculate the y position for centering

        g.drawImage(imagePlant, xPos, yPos, scaledWidth, scaledHeight, this);
    }

}