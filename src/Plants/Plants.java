package Plants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Plants {

    private String name;
    private BufferedImage image;
    private Image icon;
    private int speedGerminationRate;
    private int collectTime;

    private int price;

    public Plants(String name, int speedGerminationRate, int collectTime, int price) throws IOException {
        this.name = name;
        this.speedGerminationRate = speedGerminationRate;
        this.collectTime = collectTime;
        this.image = ImageIO.read(new File("./images/data.png")); // chargement de l'image globale
        this.price=price;
    }

    public int getPrice(){return price;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCustomImage(String data) throws IOException {this.image = ImageIO.read(new File("./images/"+data+".png"));}

    public void setIcon(int x, int y) {

        image = image.getSubimage(x, y, 10, 10);

        this.icon = this.image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);

    }

    public Image getIcon() {
        return icon;
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
