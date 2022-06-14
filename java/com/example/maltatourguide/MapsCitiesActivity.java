package com.example.maltatourguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.maltatourguide.ui.cities.CitiesInfoWindowAdapter;
import com.example.maltatourguide.ui.cities.City;
import com.example.maltatourguide.ui.cities.CityScrollingActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.maltatourguide.databinding.ActivityMapsCitiesBinding;
import com.google.gson.Gson;

import java.util.Objects;

public class MapsCitiesActivity extends AppCompatActivity implements OnMapReadyCallback {

    private City[] list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.maltatourguide.databinding.ActivityMapsCitiesBinding binding = ActivityMapsCitiesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle(getString(R.string.map));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Bundle extras =getIntent().getExtras();
        if(extras!=null) {
            Gson gson = new Gson();
            String intentData = extras.getString("citiesList");
            list = gson.fromJson(intentData, City[].class);}
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setMinZoomPreference(10);

        for(City p:list){
            LatLng latLng = new LatLng(Double.parseDouble(p.getLatitude()), Double.parseDouble(p.getLongitude()));
            MarkerOptions markerOptions=new MarkerOptions().position(latLng).title(p.getName());
            googleMap.setInfoWindowAdapter(new CitiesInfoWindowAdapter(this));
            Marker m= googleMap.addMarker(markerOptions);
            assert m != null;
            m.setTag(p);
            googleMap.setOnInfoWindowClickListener(k->{
                Gson gson = new Gson();
                Intent intent = new Intent(this, CityScrollingActivity.class);
                String intentData =gson.toJson(k.getTag());
                intent.putExtra("markerCity",intentData);
                startActivity(intent);
            });

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }
    }
}