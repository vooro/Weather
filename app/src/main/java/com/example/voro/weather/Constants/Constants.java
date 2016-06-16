package com.example.voro.weather.Constants;

/**
 * Created by voro on 12.06.2016.
 */
public class Constants {
    //URL from retrieval of JSON obj,API and ICON
    public static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    public static final String FORECAST_ULR = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
    public static final String WEATHER_API = "&APPID=6fa3fa3a3f053ee8266b7de13434f6de";
    public static final String ICON_URL = "http://openweathermap.org/img/w/";


    // JSON objects and arrays
    public static final String MAIN = "main";
    public static final String TEMP = "temp";
    public static final String TEMP_MIN = "temp_min";
    public static final String TEMP_MAX = "temp_max";
    public static final String PRESSURE = "pressure";
    public static final String HUMIDITY = "humidity";



    public static final String WIND = "wind";
    public static final String WIND_SPEED = "speed";
    public static final String WIND_DEG = "deg";

    public static final String SYS = "sys";
    public static final String SUNRISE = "sunrise";
    public static final String SUNSET = "sunset";



    public static final String WEATHER = "weather";   //array
    public static final String MAIN_WEATHER = "main";
    public static final String ICON = "icon";
    public static final String DESCRIPTION = "description";
    public static final String NAME = "name";
    public static final String DT = "dt";

    public static final String CITY = "city";
    public static final String LIST = "list";   // array
    public static final String MIN = "min";
    public static final String MAX = "max";

    public  static final int DAYS_OF_FORECAST = 7;
}