package com.example.maltatourguide.ui.sights;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.maltatourguide.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

public class SightsInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{
    private final Context context;

    public SightsInfoWindowAdapter(Context ctx){
        context = ctx;
    }

    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        @SuppressLint("InflateParams") View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.sights_infowindow, null);

        TextView name = view.findViewById(R.id.infowindow_name);
        ImageView img = view.findViewById(R.id.infowindow_img);

        name.setText(marker.getTitle());

        SightsList infoWindowData = (SightsList) marker.getTag();
        assert infoWindowData != null;
        Picasso.with(context).load(infoWindowData.getImage()).into(img);


        return view;
    }

}
