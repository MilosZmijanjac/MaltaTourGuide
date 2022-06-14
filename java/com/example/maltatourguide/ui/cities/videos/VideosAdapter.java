package com.example.maltatourguide.ui.cities.videos;

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

public class VideosAdapter extends RecyclerView.Adapter<VideoViewHolder> {

    private final Context context;
    private final List<VideoModel> videoModelList;

    public VideosAdapter(Context context, List<VideoModel> videoModelList) {
        this.context = context;
        this.videoModelList = videoModelList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_item,parent,false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        Glide.with(context).load(videoModelList.get(position).getImageUrl()).into(holder.imageView);
        holder.imageView.setOnClickListener(view -> context.startActivity(new Intent(context,VideoPlayerActivity.class)
                .putExtra("video_url",videoModelList.get(position).getOriginalUrl())));
    }

    @Override
    public int getItemCount() {
        return videoModelList.size();
    }
}
class VideoViewHolder extends RecyclerView.ViewHolder{
    ImageView imageView;
    public VideoViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.imageViewItem);
    }
}
