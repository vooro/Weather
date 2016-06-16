package com.example.voro.weather.weatherObj;

import android.graphics.Bitmap;

import com.example.voro.weather.Constants.Constants;

/**
 * Created by voro on 14.06.2016.
 */
public class Forecast {
    public String name;
    public String description;
    public String icon;
    public double temp_min;
    public double temp_max;
    public String main;


    public final double KELVIN_TO_CELSIUS = -275.15;

    public Bitmap icon_png;

    //Strings for UI
    public String temp_s;
    public String temp_min_s;
    public String temp_max_s;

    public void transformData(){
        setTemp_max_s();
        setTemp_min_s();
    }

    public void setTemp_min_s() {
        int celsius =(int)(temp_min + KELVIN_TO_CELSIUS);
        this.temp_min_s = Integer.toString(celsius) + '\u00B0';
    }
    public void setTemp_max_s() {
        int celsius =(int)(temp_max + KELVIN_TO_CELSIUS);
        this.temp_max_s = Integer.toString(celsius) + '\u00B0';
    }

    public void setIcon(String icon) {
        this.icon = Constants.ICON_URL + icon + ".png";
    }

    public void setIcon_png(Bitmap icon_png) {
        this.icon_png = icon_png;
    }

    public Bitmap getIcon_png() {
        return icon_png;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public String getTemp_s() {
        return temp_s;
    }

    public String getTemp_min_s() {
        return temp_min_s;
    }

    public String getTemp_max_s() {
        return temp_max_s;
    }

    public String getMain() {
        return main;
    }

    public void setName(String name) {
        this.name= name;
    }

    public void setDescription(String description) {
        this.description = description;
     }

    public void setMain(String main) {
        this.main = main;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }
}
