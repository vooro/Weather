package com.example.voro.weather.weatherObj;

import java.text.DecimalFormat;

/**
 * Created by voro on 12.06.2016.
 */
public class Weather extends Forecast {

    private  double wind_speed;
    private double wind_deg;
    private long dt;
    private long sunrise;
    private  long sunset;
    private double temp;
    private int humidity;
    private int pressure;

    public String humidity_s;
    public String pressure_s;
    public String wind_s;

    public void transformData(){
        setTemp_max_s();
        setTemp_min_s();
        setTemp_s();
        setHumidity_s();
        setPressure_s();
        setWind_s();
    }
    public String direction(){
        String dir = "";
        double degree = getWind_deg();
        if( degree > 350 && degree <= 10)
            dir = "N";
        else if( degree > 10 && degree <= 80)
            dir = "NE";
        else if( degree > 80 && degree <= 100)
            dir = "E";
        else if ( degree > 100 && degree <= 170)
            dir = "SE";
        else if (degree > 170 && degree <= 190)
            dir = "S";
        else if (degree > 190 && degree <= 260 )
            dir = "SV";
        else if (degree > 260 && degree <= 280)
            dir = "V";
        else if (degree > 280 && degree <= 350)
            dir = "NV";
        return dir;
    }
    public void setWind_s(){
        double kmperhour = (this.wind_speed * 3600) / 1000;
        DecimalFormat precision = new DecimalFormat("#.#");
        this.wind_s = precision.format(kmperhour) + "km/h " + direction();
    }
    public void setHumidity_s(){
        this.humidity_s = Integer.toString(getHumidity()) + "%";
    }
    public void setPressure_s(){
        this.pressure_s = Integer.toString(getPressure()) + " mBar";
    }
    public void setTemp_s() {
        int celsius =(int)(temp + KELVIN_TO_CELSIUS);
        this.temp_s = Integer.toString(celsius) + '\u00B0';
    }
    @Override
    public void setTemp_max_s() {
       int celsius =(int)(temp_min + KELVIN_TO_CELSIUS);
       temp_min_s = '\u2193' + Integer.toString(celsius) + '\u00B0';
    }

    @Override
    public void setTemp_min_s() {
        int celsius =(int)(temp_max + KELVIN_TO_CELSIUS);
        temp_max_s = '\u2191'+ Integer.toString(celsius) + '\u00B0';
    }

    public String getTemp_s() {
        return temp_s;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }

    public void setWind_speed(double wind_speed) {
        this.wind_speed = wind_speed;
    }

    public void setWind_deg(double wind_deg) {
        this.wind_deg = wind_deg;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public long getDt() {
        return dt;
    }


    public double getTemp_min() {
        return temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public double getWind_speed() {
        return wind_speed;
    }

    public double getWind_deg() {
        return wind_deg;
    }

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public double getTemp() {
        return temp;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }


    public String getWind_s() {
        return wind_s;
    }

    public String getPressure_s() {
        return pressure_s;
    }

    public String getHumidity_s() {
        return humidity_s;
    }
}
