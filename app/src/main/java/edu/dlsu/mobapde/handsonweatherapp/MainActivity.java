package edu.dlsu.mobapde.handsonweatherapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    RecyclerView rvWeather;
    TextView tvTemp;
    TextView tvWind;
    TextView tvDate;
    ImageView ivIcon;
    TextView tvNext;

    boolean isCelsius = true;
    Weather currentWeather;
    WeatherAdapter weatherAdapter;

    /*Variation*/
    boolean isKph = true;
    boolean showTemp = false;
    /*Variation*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvWeather = (RecyclerView) findViewById(R.id.rv_weather);
        tvTemp = (TextView) findViewById(R.id.tv_temp);
        tvWind = (TextView) findViewById(R.id.tv_wind);
        tvDate = (TextView) findViewById(R.id.tv_date);
        ivIcon = (ImageView) findViewById(R.id.iv_icon);
        tvNext = (TextView) findViewById(R.id.tv_next);

        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                weatherAdapter.setWeather(generateWeatherForecast(7));


                showTemp = !showTemp;
                weatherAdapter.showTemperature(showTemp);
            }
        });

        weatherAdapter = new WeatherAdapter(generateWeatherForecast(7));
        weatherAdapter.setOnItemClickListener(new WeatherAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Weather w) {
                displayWeather(w);
                currentWeather = w;
            }
        });
        rvWeather.setAdapter(weatherAdapter);
        rvWeather.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));

        tvWind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*commented in variation*/
//                ChangeReadingDialog changeReadingDialog  = ChangeReadingDialog.newInstance(isCelsius);
//                changeReadingDialog.setOnYesSelectedListener(new ChangeReadingDialog.OnYesSelectedListener() {
//                    @Override
//                    public void onYesSelected() {
//                        isCelsius = !isCelsius;
//                        displayWeather(currentWeather);
//                    }
//                });
//                changeReadingDialog.show(getSupportFragmentManager(), "");
                /*commented in variation*/

                /*variation*/
                ChangeReadingWindDialog changeReadingWindDialog  = ChangeReadingWindDialog.newInstance(isKph);
                changeReadingWindDialog.setOnYesSelectedListener(new ChangeReadingWindDialog.OnYesSelectedListener() {
                    @Override
                    public void onYesSelected() {
                        isKph = !isKph;
                        displayWeather(currentWeather);
                    }
                });
                changeReadingWindDialog.show(getSupportFragmentManager(), "");
                /*variation*/

            }
        });
    }

    public void displayWeather(Weather w){
        String temp;
        /*Variation*/
        String wind = convertWind(isKph, w.getWind());
        /*Variation*/
        temp=convertTemp(isCelsius, w.getTemp());

        tvTemp.setText(temp);
        tvDate.setText(w.getDate());
        tvWind.setText(w.getWind()+"kph");
        ivIcon.setImageResource(w.getIcon());

        /*Variation*/
        tvWind.setText(wind);
        /*Variation*/
    }

    public String convertTemp(boolean toCelsius, int temp){

        if(!toCelsius){
            return Math.round(temp * 1.8 + 32) + ((char) 0x00B0 + "F");
        }else {
            return Math.round(temp) + ((char) 0x00B0 + "C");
        }
    }

    public String convertWind(boolean toKph, int wind){
        if(!toKph){
            return Math.round(wind*0.62137) + "mph";
        }else{
            return Math.round(wind*1.60934) + "kph";
        }
    }

    int[] weatherIcons = new int[]{R.drawable.cloudy, R.drawable.stormy, R.drawable.sun_cloudy,
            R.drawable.sun_rainy, R.drawable.sunny, R.drawable.thunder_storm,
            R.drawable.windy};
    int offset = 0;

    public ArrayList<Weather> generateWeatherForecast(int numOfDays){
        ArrayList<Weather> forecast = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, offset);
        offset += numOfDays;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, EEEE");

        for(int i=0; i<numOfDays; i++){
            calendar.add(Calendar.DATE, 1);
            int randomTemp = new Random().nextInt(20);
            int randomWind = new Random().nextInt(10);
            int randomIcon = new Random().nextInt(7);

            forecast.add(new Weather(simpleDateFormat.format(calendar.getTime()),
                    20 + randomTemp, 10 + randomWind, weatherIcons[randomIcon] ));
        }

        currentWeather = forecast.get(0);
        displayWeather(currentWeather);

        return forecast;
    }
}
