package com.example.voro.weather.Utils;

import android.content.Context;
import android.net.ConnectivityManager;

import com.example.voro.weather.Constants.Constants;
import com.example.voro.weather.Download.DownloadImage;
import com.example.voro.weather.weatherObj.Forecast;
import com.example.voro.weather.weatherObj.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by voro on 14.06.2016.
 */
public class WebUtils {

    public static Weather parseWeather(String str) {
        Weather myWeather = new Weather();
        try {
            JSONObject jsonObject = new JSONObject(str);

            myWeather.setName(jsonObject.optString(Constants.NAME));
            myWeather.setDt(jsonObject.optInt(Constants.DT));

            String main = jsonObject.optString(Constants.MAIN);
            JSONObject jsonMain = new JSONObject(main);
            myWeather.setTemp(jsonMain.optDouble(Constants.TEMP));
            myWeather.setTemp_min(jsonMain.optDouble(Constants.TEMP_MIN));
            myWeather.setTemp_max(jsonMain.optDouble(Constants.TEMP_MAX));
            myWeather.setPressure(jsonMain.optInt(Constants.PRESSURE));
            myWeather.setHumidity(jsonMain.optInt(Constants.HUMIDITY));

            String wind = jsonObject.optString(Constants.WIND);
            JSONObject jsonWind = new JSONObject(wind);
            myWeather.setWind_deg(jsonWind.optDouble(Constants.WIND_DEG));
            myWeather.setWind_speed(jsonWind.optDouble(Constants.WIND_SPEED));

            String sys = jsonObject.optString(Constants.SYS);
            JSONObject jsonSys = new JSONObject(sys);
            myWeather.setSunrise(jsonSys.optLong(Constants.SUNRISE));
            myWeather.setSunset(jsonSys.optLong(Constants.SUNSET));

            JSONArray jsonArray= jsonObject.optJSONArray(Constants.WEATHER);
            JSONObject weather = jsonArray.optJSONObject(0);
            myWeather.setMain(weather.optString(Constants.MAIN_WEATHER));
            myWeather.setDescription(weather.optString(Constants.DESCRIPTION));
            myWeather.setIcon(weather.optString(Constants.ICON));

            myWeather.transformData();
            return myWeather;
        }catch (JSONException e) {
            e.printStackTrace();
        }return null;
    }
    public static ArrayList<Forecast> parseForcast(String str){
        ArrayList<Forecast>  forecast = new ArrayList<Forecast>();


        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray listJsonArray = jsonObject.optJSONArray(Constants.LIST);

            JSONObject city = jsonObject.optJSONObject(Constants.CITY);

            for (int i = 0 ; i < Constants.DAYS_OF_FORECAST ;i++){
                DownloadImage image = new DownloadImage();
                Forecast forecastItem = new Forecast();
                forecastItem.setName(city.optString(Constants.NAME));

                JSONObject temp = listJsonArray.optJSONObject(i).optJSONObject(Constants.TEMP);
                forecastItem.setTemp_max(temp.optDouble(Constants.MAX));
                forecastItem.setTemp_min(temp.optDouble(Constants.MIN));

                JSONArray weatherJsonArray = listJsonArray.optJSONObject(i).optJSONArray(Constants.WEATHER);
                JSONObject weather = weatherJsonArray.optJSONObject(0);
                forecastItem.setMain(weather.optString(Constants.MAIN_WEATHER));
                forecastItem.setDescription(weather.optString(Constants.DESCRIPTION));

                forecastItem.setIcon(weather.optString(Constants.ICON));
                forecastItem.setIcon_png(image.execute(forecastItem.getIcon()).get());

                forecastItem.transformData();
                forecast.add(forecastItem);
            }
            return forecast;

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } return null;
    }

    public static boolean hasInternetConnection(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager != null &&
                connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected();

    }
}
