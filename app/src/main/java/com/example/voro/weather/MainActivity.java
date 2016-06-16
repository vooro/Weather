package com.example.voro.weather;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voro.weather.Constants.Constants;
import com.example.voro.weather.Download.DownloadImage;
import com.example.voro.weather.Download.DownloadTask;
import com.example.voro.weather.Utils.WebUtils;
import com.example.voro.weather.weatherObj.Forecast;
import com.example.voro.weather.weatherObj.Weather;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener{

    TextView textCity;
    TextView textDescription;
    TextView textMin;
    TextView textMax;
    TextView temp;
    ImageView myIcon;
    ImageView bigIcon;
    TextView time;
    TextView humidity;
    TextView wind;
    TextView pressure;
    RelativeLayout layout;

    SharedPreferences sharedPreferences;
    ArrayList<Forecast>forecast;
    Weather weather;
    Calendar calendar = Calendar.getInstance();
    int day = calendar.get(Calendar.DAY_OF_WEEK);



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textCity = (TextView) findViewById(R.id.textViewCity);
        textDescription = (TextView) findViewById(R.id.textViewDescription);
        textMax = (TextView) findViewById(R.id.textViewTempMax);
        textMin =(TextView) findViewById(R.id.textViewTempMin);
        myIcon = (ImageView)findViewById(R.id.imageView);
        temp = (TextView) findViewById(R.id.textViewTemp);
        time = (TextView) findViewById(R.id.textViewTime);
        humidity = (TextView) findViewById(R.id.textViewHumidity);
        wind =(TextView)findViewById(R.id.textWind);
        pressure = (TextView) findViewById(R.id.textViewPressure);
        bigIcon = (ImageView)findViewById(R.id.imageViewI);

        layout =(RelativeLayout) findViewById(R.id.layout);





        sharedPreferences = this.getSharedPreferences("com.example.voro.weather",MODE_PRIVATE);
        populateUI(sharedPreferences.getString("search","Suceava"));
        day = calendar.get(Calendar.DAY_OF_WEEK);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);



        return true;
    }
    public boolean onQueryTextSubmit(String query) {

        populateUI(query);

        // User pressed the search button
        return true;
    }
    void populateUI(String str){
        if(!WebUtils.hasInternetConnection(this)) {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
        DownloadTask weatherTask = new DownloadTask();
        DownloadTask forecastTask =  new DownloadTask();
        DownloadImage image = new DownloadImage();
        try {
            String weatherUrl = weatherTask.execute(Constants.WEATHER_URL + str + Constants.WEATHER_API).get();
            String forecastUrl = forecastTask.execute(Constants.FORECAST_ULR + str + Constants.WEATHER_API).get();

            weather = WebUtils.parseWeather(weatherUrl);
            forecast = WebUtils.parseForcast(forecastUrl);
            setBackground(weather.getMain());
            populateListView();

            Bitmap icon = image.execute(weather.getIcon()).get();
            sharedPreferences.edit().putString("search",str).apply();

            wind.setText(weather.getWind_s());
            pressure.setText(weather.getPressure_s());
            humidity.setText(weather.getHumidity_s());
            bigIcon.setImageBitmap(icon);
            myIcon.setImageBitmap(icon);
            textCity.setText(weather.getName());
            textDescription.setText(weather.getDescription());
            textMax.setText(weather.getTemp_max_s());
            textMin.setText(weather.getTemp_min_s());
            temp.setText(weather.getTemp_s());

        }catch (Exception e){e.printStackTrace();}
    }

    public List<String> dayOrder(List<String> daysWeek) {
        List<String> order = new ArrayList<>();
        for(int i = 0,j = day; i <= 7;i++){
            order.add(daysWeek.get(j));
            j++;
            if(j== 7)
                j= 0;
        }
        return order;
    }

    public void populateListView(){
        ArrayAdapter<Forecast> adapter = new MyListAdapter();
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });
        lv.setAdapter(adapter);
    }
    private class MyListAdapter extends ArrayAdapter<Forecast>{
        public MyListAdapter(){
            super(MainActivity.this,R.layout.item_view,forecast);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //make sure we have a view to work with(may have been given null)
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.item_view,parent,false);
            }

            Forecast currentForecast = forecast.get(position);

            TextView min = (TextView) itemView.findViewById(R.id.textView_itemMin);
            min.setText(currentForecast.getTemp_min_s());
            TextView max = (TextView) itemView.findViewById(R.id.textView_itemMax);
            max.setText(currentForecast.getTemp_max_s());
            ImageView image = (ImageView) itemView.findViewById(R.id.imageView_item);
            image.setImageBitmap(currentForecast.getIcon_png());

            TextView dayOfWeek = (TextView) itemView.findViewById(R.id.textView_itemDay);


            ArrayList<String> daysOfWeek = new ArrayList<String>();
            daysOfWeek.add("Sunday");
            daysOfWeek.add("Monday");
            daysOfWeek.add("Tuesday");
            daysOfWeek.add("Wednesday");
            daysOfWeek.add("Thursday");
            daysOfWeek.add("Friday");
            daysOfWeek.add("Saturday");

            List<String> order = dayOrder(daysOfWeek);
            dayOfWeek.setText(order.get(position));

            return itemView;
        }
    }
    public void setBackground(String description ){
        if(description.equals("Rain"))
            layout.setBackgroundResource(R.drawable.rain);
        else if(description.equals("Storm"))
            layout.setBackgroundResource(R.drawable.thunderstorm);
        else if(description.equals("Snow"))
            layout.setBackgroundResource(R.drawable.snow);
        else if(description.equals("Mist"))
            layout.setBackgroundResource(R.drawable.mist);
        else if(description.equals("Clear"))
            layout.setBackgroundResource(R.drawable.clearsky);
        else
            layout.setBackgroundResource(R.drawable.brokenclouds);

    }

   @Override
   public boolean onQueryTextChange(String newText) {
        // User changed the text
       return false;
   }

}
