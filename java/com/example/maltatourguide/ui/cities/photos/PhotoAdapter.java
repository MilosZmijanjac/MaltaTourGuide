package com.example.maltatourguide.ui.cities.photos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.maltatourguide.R;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder> {

    private final Context context;
    private final List<PhotoModel> photoModelList;

    public PhotoAdapter(Context context, List<PhotoModel> photoModelList) {
        this.context = context;
        this.photoModelList = photoModelList;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.image_item,parent,false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        Glide.with(context).load(photoModelList.get(position).getMediumUrl()).into(holder.imageView);
        holder.imageView.setOnClickListener(view -> context.startActivity(new Intent(context,FullScreenWallpaper.class)
        .putExtra("originalUrl", photoModelList.get(position).getOriginalUrl())));
    }

    @Override
    public int getItemCount() {
        return photoModelList.size();
    }
}
class PhotoViewHolder extends RecyclerView.ViewHolder{
    ImageView imageView;
    public PhotoViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.imageViewItem);
    }
}
