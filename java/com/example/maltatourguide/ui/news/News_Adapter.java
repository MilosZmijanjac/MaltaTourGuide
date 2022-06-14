package com.example.maltatourguide.ui.news;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.maltatourguide.R;
import java.util.ArrayList;

public class News_Adapter extends RecyclerView.Adapter<NewsViewHolder> {

    private final ArrayList<News_Data> items= new ArrayList<>();
    private final NewsItemClicked listener;
    Context context;
    //Constructor
    public News_Adapter(Context context,NewsItemClicked listener) {
     this.listener = listener;
     this.context =context;
    }

    @NonNull
    @Override
    //it converts the news_item.xml(view) into view and it returns NewsViewHolder
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       LayoutInflater inflater= LayoutInflater.from(parent.getContext());
       View view = inflater.inflate(R.layout.news_item,parent,false);
       NewsViewHolder viewHolder = new NewsViewHolder(view);

       view.setOnClickListener(v -> listener.onItemClicked(items.get(viewHolder.getAdapterPosition())));
       return viewHolder;
    }

    //corresponding to each item it fills the value ...it has value position which tells at which position it is present
    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News_Data currentItem = items.get(position);
        holder.item_title.setText(currentItem.getTitle());
        holder.item_author.setText(currentItem.getAuthor());

        String date=currentItem.getPublishedAt();
        int iend = date.indexOf("T");
        String subString;
        if(iend!=-1)
        {
            subString = date.substring(0,iend);
            holder.item_date.setText(subString);
        }

        Glide.with(context).load(currentItem.getImageURL()).into(holder.item_image);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    void updatedNews(ArrayList<News_Data> UpdateNews){
        items.clear();
        items.addAll(UpdateNews);
        notifyDataSetChanged();
    }

}

//NewsViewHolder contains all the items which we want to inflate
class NewsViewHolder extends RecyclerView.ViewHolder{
    TextView item_title;
    ImageView item_image;
    TextView item_author;
    TextView item_date;
    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);
        item_title = itemView.findViewById(R.id.news_item);
        item_image = itemView.findViewById(R.id.image_item);
        item_author = itemView.findViewById(R.id.author);
        item_date = itemView.findViewById(R.id.item_date);
    }
}
interface NewsItemClicked {
    void onItemClicked(News_Data item);
}