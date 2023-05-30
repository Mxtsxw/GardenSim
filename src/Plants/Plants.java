package Plants;

public abstract class Plants {

    private String name;
    private String thumbnail;
    private int speedGerminationRate;
    private int collectTime;

    public Plants(String name, String thumbnail, int speedGerminationRate, int collectTime) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.speedGerminationRate = speedGerminationRate;
        this.collectTime = collectTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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
