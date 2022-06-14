package com.example.maltatourguide.ui.cities.photos;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.maltatourguide.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PhotosActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    PhotoAdapter photoAdapter;
    List<PhotoModel> photoModelList;
    int pageNumber = 1;

    Boolean isScrolling  = false;
    int currentItems,totalItems,scrollOutItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        setTitle(R.string.photos);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        String city;


        recyclerView = findViewById(R.id.recyclerView);
        photoModelList = new ArrayList<>();
        photoAdapter = new PhotoAdapter(this, photoModelList);

        recyclerView.setAdapter(photoAdapter);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        Bundle extras =getIntent().getExtras();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling= true;
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItems = gridLayoutManager.getChildCount();
                totalItems = gridLayoutManager.getItemCount();
                scrollOutItems = gridLayoutManager.findFirstVisibleItemPosition();

                if(isScrolling && (currentItems+scrollOutItems==totalItems)){
                    isScrolling = false;
                    if(extras!=null) {
                        String city = extras.getString("photos");
                        String url ="https://api.pexels.com/v1/search?query="+city+",Malta&page="+pageNumber+"&per_page=";
                        url=url+sharedPreferences.getString("photos_number","5");
                        fetchWallpaper(url);
                    }
                }


            }
        });

        if(extras!=null) {
            city = extras.getString("photos");
            String url ="https://api.pexels.com/v1/search?query="+city+",Malta&page="+pageNumber+"&per_page=";
            url=url+sharedPreferences.getString("photos_number","5");
            fetchWallpaper(url);
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void fetchWallpaper(String url){
        @SuppressLint("NotifyDataSetChanged") StringRequest request = new StringRequest(Request.Method.GET,url ,
                response -> {

                    try{
                        JSONObject jsonObject = new JSONObject(response);

                        JSONArray jsonArray= jsonObject.getJSONArray("photos");

                        int length = jsonArray.length();

                        for(int i=0;i<length;i++){

                            JSONObject object = jsonArray.getJSONObject(i);

                            int id = object.getInt("id");

                            JSONObject objectImages = object.getJSONObject("src");

                            String orignalUrl = objectImages.getString("original");
                            String mediumUrl = objectImages.getString("medium");

                            PhotoModel photoModel = new PhotoModel(id,orignalUrl,mediumUrl);
                            photoModelList.add(photoModel);

                        }

                        photoAdapter.notifyDataSetChanged();
                        pageNumber++;

                    }catch (JSONException e){
                    e.printStackTrace();
                    }
                }, error -> Toast.makeText(this, getString(R.string.error_photos), Toast.LENGTH_LONG).show()){
            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> params = new HashMap<>();
                params.put("Authorization","563492ad6f91700001000001905a62520ed349d5bc7893eeabd03479");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }

}