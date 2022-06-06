package com.example.ettalebweatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.koushikdutta.ion.Ion;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class weatherAdapter extends ArrayAdapter<Weather>{
    private Context context;
    private  List<Weather> weathers;
    public weatherAdapter(@NonNull Context context, @NonNull List<Weather> objects) {
        super(context, 0, objects);
        this.context=context;
        this.weathers = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.item_liste_view,parent,false);
        TextView maxTemp=convertView.findViewById(R.id.idTempmax);
        TextView minTemp=convertView.findViewById(R.id.idTempmin);
        TextView sunrise=convertView.findViewById(R.id.idTvsunrisetime);
        TextView sunset=convertView.findViewById(R.id.idTvsunsettime);
        TextView Temp=convertView.findViewById(R.id.idTemp);
        TextView Date=convertView.findViewById(R.id.idDate);
        ImageView icon=convertView.findViewById(R.id.idiconweather);

        Weather weather = weathers.get(position);
        maxTemp.setText(weather.getMaxTemp()+"°C");
        minTemp.setText(weather.getMinTemp()+"°C");
        Temp.setText(weather.getTemp()+"°C");
        sunrise.setText(weather.getSunriseTime()+"");
        sunset.setText(weather.getSunsetTime()+"");
//        Date.setText(weather.getDate()+"");

        Ion.with(context)
                .load("http://openweathermap.org/img/w/"+weather.getIcon()+".png")
                .intoImageView(icon);
        Date date =new Date(weather.getDate()*1000);
        DateFormat dateFormat= new SimpleDateFormat("EEE,MMM yy", Locale.ENGLISH) ;
        dateFormat.setTimeZone(TimeZone.getTimeZone(weather.getTimezone() ));

        Date.setText(dateFormat.format(date)+"");
        Date sunsetTime =new Date(weather.getSunsetTime()*1000);
        DateFormat dateFormat1= new SimpleDateFormat("hh,mm", Locale.ENGLISH) ;
        dateFormat1.setTimeZone(TimeZone.getTimeZone(weather.getTimezone() ));

        sunset.setText(dateFormat1.format(sunsetTime)+"");


        Date sunriseTime =new Date(weather.getSunriseTime()*1000);
        DateFormat dateFormat2= new SimpleDateFormat("hh,mm", Locale.ENGLISH) ;
        dateFormat2.setTimeZone(TimeZone.getTimeZone(weather.getTimezone() ));

        sunrise.setText(dateFormat2.format(sunriseTime)+"");


        return  convertView;
    }
}
