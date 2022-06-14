package com.example.maltatourguide.ui.sights;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.maltatourguide.R;
import com.example.maltatourguide.databinding.FragmentSightsBinding;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import java.util.List;
import java.util.Locale;

public class SightsAdapter extends RecyclerView.Adapter<SightsAdapter.ViewHolder> {
    List<SightsList> sights_lists;
    Context ct;

    public SightsAdapter(List<SightsList> sights_lists, Context ct) {
        this.sights_lists = sights_lists;
        this.ct = ct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(ct);
        Locale locale;
        if(!sharedPreferences.getString("language_pref","English").equals("English")){
            locale = new Locale("sr");
        }
        else{
            locale = new Locale("en");
        }
        Locale.setDefault(locale);
        Resources resources = ct.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_list,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final SightsList productList= sights_lists.get(i);
        String pimg=productList.getImage();

        Picasso.with(ct).load(pimg).into(viewHolder.img);

        viewHolder.img.setOnClickListener(v->{
            Intent intent = new Intent(ct, ScrollingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            String intentData =new Gson().toJson(productList);
            intent.putExtra("markerSight",intentData);
            ct.startActivity(intent);
        });

        viewHolder.tv.setText(productList.getName());

        if (SightsFragment.favoriteDatabase.favoriteDao().isFavorite(productList.getId())==1)
            viewHolder.fav_btn.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
        else
            viewHolder.fav_btn.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp);

        viewHolder.fav_btn.setOnClickListener(v -> {
            FavoriteList favoriteList=new FavoriteList();

            int id=productList.getId();
            String image=productList.getImage();
            String name=productList.getName();
            String desc=productList.getDescription();
            String lon=productList.getLongitude();
            String lat=productList.getLatitude();

            favoriteList.setId(id);
            favoriteList.setImage(image);
            favoriteList.setName(name);
            favoriteList.setDescription(desc);
            favoriteList.setLatitude(lat);
            favoriteList.setLongitude(lon);

            if (SightsFragment.favoriteDatabase.favoriteDao().isFavorite(id)!=1){
                viewHolder.fav_btn.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
                saveTask(favoriteList);
            }else {
                viewHolder.fav_btn.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp);
                deleteTask(favoriteList);
            }
        });
    }


    @Override
    public int getItemCount() {
        return sights_lists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        Button fav_btn;
        TextView tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img= itemView.findViewById(R.id.img_pr);
            tv= itemView.findViewById(R.id.tv_name);
            fav_btn= itemView.findViewById(R.id.fav_btn);

        }
    }
    private void deleteTask(FavoriteList flist) {
        @SuppressLint("StaticFieldLeak")
        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                SightsFragment.favoriteDatabase.favoriteDao().delete(flist);
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(ct.getApplicationContext(), ct.getString(R.string.deleted), Toast.LENGTH_LONG).show();
            }
        }
        new SaveTask().execute();
    }
    private void saveTask(FavoriteList flist) {
        @SuppressLint("StaticFieldLeak")
        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                SightsFragment.favoriteDatabase.favoriteDao().addData(flist);
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(ct.getApplicationContext(), ct.getString(R.string.saved), Toast.LENGTH_LONG).show();
            }
        }
        new SaveTask().execute();
    }
}