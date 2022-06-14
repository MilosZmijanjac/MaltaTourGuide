package com.example.maltatourguide.ui.news;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import com.bumptech.glide.Glide;
import com.example.maltatourguide.R;
import com.google.gson.Gson;

public class News_Information extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news__information);

        Bundle extras =getIntent().getExtras();
        if(extras!=null) {
            Gson gson = new Gson();
            String intentData = extras.getString("item");
            News_Data news_data = gson.fromJson(intentData, News_Data.class);

            TextView item_title = findViewById(R.id.information_title);
            item_title.setText(news_data.getTitle());

            TextView item_author = findViewById(R.id.information_author);
            if(news_data.getAuthor().equals("null"))
                item_author.setText("Unknown");
            else
            item_author.setText(news_data.getAuthor());

            ImageView item_Image = findViewById(R.id.information_image);
            Glide.with(this).load(news_data.getImageURL()).centerCrop().into(item_Image);

            TextView item_description = findViewById(R.id.description);
            item_description.setText(news_data.getDescription());

            String date=news_data.getPublishedAt();
            int iend = date.indexOf("T");
            String subString;
            if(iend!=-1)
            {
                subString = date.substring(0,iend);
                TextView item_date = findViewById(R.id.Date);
                item_date.setText(subString);
            }

            ImageButton back_btn =findViewById(R.id.back_btn);
            back_btn.setOnClickListener(v -> finish());
            Button url_btn = findViewById(R.id.url_btn);
            url_btn.setOnClickListener(v -> {
                ConnectivityManager cm = (ConnectivityManager) getApplicationContext().
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if(activeNetwork !=null && activeNetwork.isConnectedOrConnecting() ) {
                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                    CustomTabsIntent customTabsIntent = builder.build();
                    customTabsIntent.launchUrl(News_Information.this, Uri.parse(news_data.getUrl()));
                }
                else
                    Toast.makeText(News_Information.this, "No internet connection", Toast.LENGTH_SHORT).show();

            });
        }

    }

}