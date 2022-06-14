package com.example.maltatourguide.ui.cities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.maltatourguide.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {
    List<City> cityList;
    Context ct;

    public CitiesAdapter(List<City> cityList, Context ct) {
        this.cityList = cityList;
        this.ct = ct;
    }

    @NonNull
    @Override
    public CitiesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cities_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CitiesAdapter.ViewHolder viewHolder, int i) {
        final City city=cityList.get(i);
        String pimg=city.getImage();
        Picasso.with(ct).load(pimg).into(viewHolder.img);
        viewHolder.img.setOnClickListener(v->{
            Gson gson = new Gson();
            Intent intent = new Intent(ct, CityScrollingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            String intentData =gson.toJson(city);
            intent.putExtra("markerCity",intentData);
            ct.startActivity(intent);
        });
        viewHolder.name.setText(city.getName());

    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img= itemView.findViewById(R.id.city_img);
            name= itemView.findViewById(R.id.city_name);
        }
    }

}