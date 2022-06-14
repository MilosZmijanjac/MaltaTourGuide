package com.example.maltatourguide.ui.sights;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.maltatourguide.R;
import java.util.List;
import java.util.Objects;

public class FavoriteListActivity extends AppCompatActivity {
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        setTitle(getString(R.string.fav_list));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        rv= findViewById(R.id.rec);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        getFavData();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Respond to the action bar's Up/Home button
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getFavData() {
        List<FavoriteList> favoriteLists=SightsFragment.favoriteDatabase.favoriteDao().getFavoriteData();

        FavoriteAdapter adapter = new FavoriteAdapter(favoriteLists, getApplicationContext());
        rv.setAdapter(adapter);
    }
}