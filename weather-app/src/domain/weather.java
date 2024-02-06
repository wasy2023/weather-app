package domain;

public class weather {
    private int starttime;
    private int endtime;
    private int temperature;
    private int precipitation;

    @Override
    public String toString() {
        return "weather{" +
                "starttime=" + starttime +
                ", endtime=" + endtime +
                ", temperature=" + temperature +
                ", precipitation=" + precipitation +
                ", description='" + description + '\'' +
                '}';
    }

    public int getStarttime() {
        return starttime;
    }

    public int getEndtime() {
        return endtime;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getPrecipitation() {
        return precipitation;
    }

    public String getDescription() {
        return description;
    }

    private String description;

    public weather(int starttime, int endtime, int temperature, int precipitation, String description) {
        this.starttime = starttime;
        this.endtime = endtime;
        this.temperature = temperature;
        this.precipitation = precipitation;
        this.description = description;
    }
    public void update(int precipitation, String description){
        this.precipitation = precipitation;
        this.description = description;
    }
}
