package com.example.maltatourguide.ui.cities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.example.maltatourguide.R;
import com.example.maltatourguide.ui.cities.photos.PhotosActivity;
import com.example.maltatourguide.ui.cities.videos.VideosActivity;
import com.example.maltatourguide.ui.cities.weather.WeatherActivity;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class CityScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        Locale locale;
        if(!sharedPreferences.getString("language_pref","English").equals("English")){
            locale = new Locale("sr");
        }
        else{
            locale = new Locale("en");
        }
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        this.getBaseContext().getResources().updateConfiguration(config,
                this.getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_city_scrolling);
        Toolbar toolbar = findViewById(R.id.city_toolbar);

        Bundle extras =getIntent().getExtras();
        if(extras!=null) {
            Gson gson = new Gson();
            String intentData = extras.getString("markerCity");
            City data = gson.fromJson(intentData, City.class);
            setTitle(data.getName());
            TextView txt=findViewById(R.id.city_scrolling_txt);
            txt.setText(data.getDescription());
            ImageView img=findViewById(R.id.city_scrolling_img);
            Picasso.with(this).load(data.getImage()).into(img);
            Button weather=findViewById(R.id.city_weather);
            Button photos=findViewById(R.id.city_photos);
            Button videos=findViewById(R.id.city_videos);
            weather.setOnClickListener(l->{
                Intent intent = new Intent(this, WeatherActivity.class);
                String intentData1 =data.getName();
                intent.putExtra("weather",intentData1);
                startActivity(intent);

            });
            photos.setOnClickListener(l->{
                Intent intent = new Intent(this, PhotosActivity.class);
                String intentData1 =data.getName();
                intent.putExtra("photos",intentData1);
                startActivity(intent);});
            videos.setOnClickListener(l->{
                Intent intent = new Intent(this, VideosActivity.class);
                String intentData1 =data.getName();
                intent.putExtra("videos",intentData1);
                startActivity(intent);});
        }
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}