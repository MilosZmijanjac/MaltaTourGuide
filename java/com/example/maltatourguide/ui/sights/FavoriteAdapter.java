package com.example.maltatourguide.ui.sights;

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

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private final List<FavoriteList> favoriteLists;
    Context context;

    public FavoriteAdapter(List<FavoriteList> favoriteLists, Context context) {
        this.favoriteLists = favoriteLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.favourites_list,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        FavoriteList fl=favoriteLists.get(i);
        Picasso.with(context).load(fl.getImage()).into(viewHolder.img);
        viewHolder.tv.setText(fl.getName());
        viewHolder.img.setOnClickListener(v->{
            Gson gson = new Gson();
            Intent intent = new Intent(context, ScrollingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            String intentData =gson.toJson(new SightsList(fl.getId(),fl.getName(),fl.getImage(),fl.getDescription(),fl.getLongitude(),fl.getLatitude()));
            intent.putExtra("markerSight",intentData);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return favoriteLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img= itemView.findViewById(R.id.fimg_pr);
            tv= itemView.findViewById(R.id.ftv_name);
       }
    }

}