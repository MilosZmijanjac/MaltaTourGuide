package com.example.maltatourguide.ui.news;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.maltatourguide.R;
import com.example.maltatourguide.databinding.FragmentNewsBinding;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsFragment extends Fragment implements NewsItemClicked{

  private FragmentNewsBinding binding;
  private News_Adapter news_adapter;
  RecyclerView news_recyclerView;

  ArrayList<News_Data> newsArray;
  RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentNewsBinding.inflate(inflater, container, false);
      requireActivity().setTitle(getString(R.string.menu_news));
    View root = binding.getRoot();

    newsArray = new ArrayList<>();
    news_recyclerView = binding.newsRecylerView;
    news_recyclerView.setLayoutManager(layoutManager);

    news_recyclerView.setItemAnimator(new DefaultItemAnimator());
    news_recyclerView.setNestedScrollingEnabled(false);
    news_adapter = new News_Adapter(getContext(), this);
    news_recyclerView.setAdapter(news_adapter);

    ConnectivityManager cm = (ConnectivityManager) requireActivity().getApplicationContext().
            getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    if(activeNetwork !=null && activeNetwork.isConnectedOrConnecting() && newsArray!=null ) {
      fetchData();
    }
    else{
      fetchfromRoom();
    }

    return root;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
  private void fetchData(){
    newsArray.clear();
    String url ="https://newsapi.org/v2/everything?q=malta&language=en&apiKey=0b3c352507f34aaea4964243696d897a";


    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
            url, null, response -> {
              if (response == null) {

                Toast.makeText(requireActivity().getApplicationContext(), "Couldn't fetch the menu! Pleas try again.", Toast.LENGTH_LONG).show();
                return;
              }
              try { //articles in url contains array of json object
                JSONArray newsJsonArray = response.getJSONArray("articles");
                for(int i=0;i< newsJsonArray.length();i++){
                  JSONObject newsJsonObject = newsJsonArray.getJSONObject(i);
                  News_Data news_data = new News_Data(
                          newsJsonObject.getString("title"),
                          newsJsonObject.getString("author"),
                          newsJsonObject.getString("url"),
                          newsJsonObject.getString("urlToImage"),
                          newsJsonObject.getString("description"),
                          newsJsonObject.getString("publishedAt")
                  );
                  newsArray.add(news_data);
                }
              } catch (JSONException e) {
                e.printStackTrace();
              }
              news_adapter.updatedNews(newsArray);


      SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(requireContext());
        if(sharedPreferences.getBoolean("caching",false))
              saveTask();
        else
              deleteTask();

            }, error -> {

      Log.e("TAG", "Error: " + error.getMessage());
      Toast.makeText(requireActivity().getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
    }){@Override
    public Map<String, String> getHeaders() {
      HashMap<String, String> headers = new HashMap<>();
      headers.put("User-Agent", "Mozilla/5.0");
      return headers;
    }};

    
    jsonObjectRequest.setShouldCache(false);
    MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
  }

  private void saveTask() {
     @SuppressLint("StaticFieldLeak")
     class SaveTask extends AsyncTask<Void, Void, Void> {

      @Override
      protected Void doInBackground(Void... voids) {
        //creating a task

        for (int i = 0; i < newsArray.size(); i++) {
          News news= new News();
          news.setTitle(newsArray.get(i).getTitle());
          news.setAuthor(newsArray.get(i).getAuthor());
          news.setImageURL(newsArray.get(i).getImageURL());
          news.setUrl(newsArray.get(i).getUrl());
          news.setDescription(newsArray.get(i).getDescription());
          news.setPublishedAt(newsArray.get(i).getPublishedAt());
          DatabaseClient.getInstance(requireActivity().getApplicationContext()).getAppDataBase().news_dao().Insert(news);
        }

        return null;
      }
      @Override
      protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(requireActivity().getApplicationContext(), getString(R.string.cached), Toast.LENGTH_LONG).show();
      }
    }
    SaveTask st = new SaveTask();
    st.execute();
  }
 private  void deleteTask(){
    new Thread(()->DatabaseClient.getInstance(requireActivity().getApplicationContext()).getAppDataBase().news_dao().deleteAllNews()).start();
 }
  private void fetchfromRoom() {
    Thread thread = new Thread(() -> {

      List<News> NewsList = DatabaseClient.getInstance(requireActivity().getApplicationContext()).getAppDataBase().
              news_dao().getAllData();

      newsArray.clear();

      for(News news:NewsList){
        News_Data news_data = new News_Data(
                news.getTitle(),
                news.getAuthor().equals("null")?"Unknown":news.getAuthor(),
                news.getUrl(),
                news.getImageURL(),
                news.getDescription(),
                news.getPublishedAt()
        );
        newsArray.add(news_data);
      }
      requireActivity().runOnUiThread(() -> news_adapter.updatedNews(newsArray));

    });
    thread.start();
  }
  @Override
  public void onItemClicked(News_Data item) {
    Gson gson = new Gson();
    Intent intent = new Intent(getContext(),News_Information.class);
    String intentData =gson.toJson(item);
    intent.putExtra("item",intentData);
    startActivity(intent);

  }


}
