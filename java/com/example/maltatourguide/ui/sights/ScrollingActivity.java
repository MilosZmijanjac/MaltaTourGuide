package com.example.maltatourguide.ui.sights;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.maltatourguide.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = findViewById(R.id.toolbar);

        Bundle extras =getIntent().getExtras();
        if(extras!=null) {
            Gson gson = new Gson();
            String intentData = extras.getString("markerSight");
            SightsList data = gson.fromJson(intentData, SightsList.class);
            setTitle(data.getName());
            TextView txt=findViewById(R.id.scrolling_txt);
            txt.setText(data.getDescription());
            ImageView img=findViewById(R.id.scrolling_img);
            Picasso.with(this).load(data.getImage()).into(img);
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