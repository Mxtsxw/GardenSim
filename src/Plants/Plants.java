package Plants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

public abstract class Plants implements Serializable {

    private String name;
    private int speedGerminationRate;
    private int collectTime;
    private static int price;
    private int germinationCounter;
    private GerminationState germinationState;

    // Constructor, getters, and setters
    public enum GerminationState {
        BEGINNING,
        INTERMEDIATE,
        ADVANCED,
        GERMINATED,
        ROTTEN
    }

    public static Image getImage(int x, int y) throws IOException {
        URL imageURL = Plants.class.getResource("/resources/images/data.png");
        BufferedImage bufferedImage = ImageIO.read(imageURL);

        int subX = 395 * x;
        int subY = 395 * y;
        int subWidth = 150;
        int subHeight = 150;

        // Adjust subimage coordinates if they exceed image bounds
        if (subX + subWidth > bufferedImage.getWidth()) {
            subWidth = bufferedImage.getWidth() - subX;
        }

        if (subY + subHeight > bufferedImage.getHeight()) {
            subHeight = bufferedImage.getHeight() - subY;
        }

        bufferedImage = bufferedImage.getSubimage(subX, subY, subWidth, subHeight);
        return bufferedImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    }

    public Plants(String name, int speedGerminationRate, int collectTime, int price) throws IOException {
        this.name = name;
        this.speedGerminationRate = speedGerminationRate;
        this.collectTime = collectTime;
        this.price=price;
        this.germinationCounter = 0;
        this.germinationState = GerminationState.BEGINNING;
    }

    // Getters & Setters

    public static int getPrice(){return price;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpeedGerminationRate() {
        return speedGerminationRate;
    }

    public void setSpeedGerminationRate(int speedGerminationRate) {
        this.speedGerminationRate = speedGerminationRate;
    }

    public int getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(int collectTime) {
        this.collectTime = collectTime;
    }

    public GerminationState getGerminationState() {
        return germinationState;
    }

    public void updateGerminationState(Meteo meteo) {
        double ratio = meteo.getRatio();
        germinationCounter++;

        if (germinationCounter >= speedGerminationRate) {
            germinationState = GerminationState.GERMINATED;
        } else if (germinationCounter >= (speedGerminationRate * 0.5)) {
            germinationState = GerminationState.ADVANCED;
        } else if (germinationCounter >= (speedGerminationRate * 0.25)) {
            germinationState = GerminationState.INTERMEDIATE;
        } else {
            germinationState = GerminationState.BEGINNING;
        }
    }
}
