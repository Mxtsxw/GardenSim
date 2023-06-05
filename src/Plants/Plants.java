package Plants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public abstract class Plants {

    private String name;
    private int speedGerminationRate;
    private int collectTime;
    private int price;

    public static Image getImage(int x, int y) throws IOException {
        URL imageURL = Plants.class.getResource("/resources/images/data.png");
        System.out.println(imageURL);
        BufferedImage bufferedImage = ImageIO.read(imageURL);
        bufferedImage = bufferedImage.getSubimage(x, y, 10, 10);
        return bufferedImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
    }

    public Plants(String name, int speedGerminationRate, int collectTime, int price) throws IOException {
        this.name = name;
        this.speedGerminationRate = speedGerminationRate;
        this.collectTime = collectTime;
        this.price=price;
    }

    // Getters & Setters

    public int getPrice(){return price;}

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
}
