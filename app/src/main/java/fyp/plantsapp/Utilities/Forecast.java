package fyp.plantsapp.Utilities;

public class Forecast {
    private String highTemp;
    private String lowTemp;
    private String weather;
    private String weatherId;
    private String date;

    public Forecast(String highTemp, String lowTemp, String weather, String weatherId, String date) {
        this.highTemp = highTemp;
        this.lowTemp = lowTemp;
        this.weather = weather;
        this.weatherId = weatherId;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // max temp
    public void setHighTemp(String value) {
        this.highTemp = value;
    }

    public String getHighTemp() {
        return this.highTemp;
    }

    // low temp
    public void setLowTemp(String value) {
        this.lowTemp = value;
    }

    public String getLowTemp() {
        return this.lowTemp;
    }

    // weather description
    public void setWeather(String value) {
        this.weather = value;
    }

    public String getWeather() {
        return this.weather;
    }

    // weather icon
    public void setWeatherId(String value) {
        this.weatherId = value;
    }

    public String getWeatherId() {
        return this.weatherId;
    }
}
