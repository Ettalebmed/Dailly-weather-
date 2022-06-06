package com.example.ettalebweatherapp;

import static com.koushikdutta.ion.Ion.*;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String API="3d2729381f80d2a7a82d3c0bedf24141";
    Button btnsearch;
    EditText editText;
    ImageView weatherImage;
    TextView tvtemp,tvcityname,Descriptiontext;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        btnsearch =findViewById(R.id.btnSearch);
        editText =findViewById(R.id.etcityname);
        weatherImage = findViewById(R.id.iconWeather);
        tvtemp = findViewById(R.id.tvTemp);
        tvcityname = findViewById(R.id.tvcityname);
        listView = findViewById(R.id.lvDailyweather);
        Descriptiontext = findViewById(R.id.idDescription);

       btnsearch.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String city = editText.getText().toString();
               if(city.isEmpty()){
                   Toast.makeText(MainActivity.this,"Please enter a city name",Toast.LENGTH_SHORT).show();
               }
               else{
                   loadWeatherByCityName(city);

               }
           }
       });
    }

    private void loadWeatherByCityName(String city) {
        with(this)
                .load("https://api.openweathermap.org/data/2.5/weather?q="+city+"&&units=metric&appid=3d2729381f80d2a7a82d3c0bedf24141")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.d("result",result.toString());
                        if(result == null){
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this,"Error in the Server",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            JsonObject main = result.get("main").getAsJsonObject();
                            double temp = main.get("temp").getAsDouble();
                            tvtemp.setText(temp +"°C");

                            JsonObject sys = result.get("sys").getAsJsonObject();

                            String countryname = sys.get("country").getAsString();

                           tvcityname.setText(city +", "+countryname);
                            JsonArray weither = result.get("weather").getAsJsonArray();
                            String icon = weither.get(0).getAsJsonObject().get("icon").getAsString();
                            loadImage(icon);
                            String Description = weither.get(0).getAsJsonObject().get("description").getAsString();
                            Descriptiontext.setText(Description);
                            //get lon and lat to use it in the second api for next days weather
                            JsonObject location = result.get("coord").getAsJsonObject();
                            double lon = location.get("lon").getAsDouble();
                            double lat = location.get("lat").getAsDouble();
                            loadDaillyNextDaysWeather(lon,lat);
                        }
                    }
                });
    }


    private void loadDaillyNextDaysWeather(double lon,double lat) {
        with(this)
                .load("https://api.openweathermap.org/data/2.5/onecall?lat="+lat+"&lon="+lon+"&exclude=hourly,minutly,curent&units=metric&appid=3d2729381f80d2a7a82d3c0bedf24141")
                        .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.d("result",result.toString());
                        if(result == null){
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this,"Error in the Server",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            List<Weather> weatherList = new ArrayList<>();
                            String timeZone  = result.get("timezone").getAsString();
                            JsonArray daily = result.get("daily").getAsJsonArray();
                            for(int i=1;i<daily.size();i++){
                                Long date = daily.get(i).getAsJsonObject().get("dt").getAsLong();
                                Long sunriseTime = daily.get(i).getAsJsonObject().get("sunrise").getAsLong();
                                Long sunsetTime = daily.get(i).getAsJsonObject().get("sunset").getAsLong();
                                Double temp  =daily.get(i).getAsJsonObject().get("temp").getAsJsonObject().get("day").getAsDouble();
                                Double maxTemp  =daily.get(i).getAsJsonObject().get("temp").getAsJsonObject().get("max").getAsDouble();
                                Double minTemp  =daily.get(i).getAsJsonObject().get("temp").getAsJsonObject().get("min").getAsDouble();
                                String icon = daily.get(i).getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString();
                                weatherList.add(new Weather(date,timeZone,temp,icon,maxTemp,minTemp,sunsetTime,sunriseTime));

                            }
                            weatherAdapter daillyweatheradapter = new weatherAdapter(MainActivity.this,weatherList);
                            listView.setAdapter(daillyweatheradapter);

                        }
                    }
                });



                        }
    private void loadImage(String name){
        Ion.with(this)
                .load("http://openweathermap.org/img/w/"+name+".png").intoImageView(weatherImage);
    }
    }
