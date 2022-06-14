package com.example.maltatourguide.ui.sights;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.example.maltatourguide.MapsSightsActivity;
import com.example.maltatourguide.Utils;
import com.example.maltatourguide.databinding.FragmentSightsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class SightsFragment extends Fragment {

  private FragmentSightsBinding binding;
  private List<SightsList> sights_lists;
  private RecyclerView recyclerView;
  SightsAdapter adapter;
  public static FavoriteDatabase favoriteDatabase;
  String sights;


  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(requireContext());
    Locale locale;
    if(!sharedPreferences.getString("language_pref","English").equals("English")){
      locale = new Locale("sr");
      sights="sights_sr.json";
    }
    else{
      locale = new Locale("en");
      sights="sights.json";
    }

    Locale.setDefault(locale);
    Resources resources = getResources();
    Configuration config = resources.getConfiguration();
    config.setLocale(locale);
    resources.updateConfiguration(config, resources.getDisplayMetrics());
    binding = FragmentSightsBinding.inflate(inflater, container, false);
    View root = binding.getRoot();
    recyclerView=binding.recyclerview;
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    sights_lists =new ArrayList<>();

    favoriteDatabase= Room.databaseBuilder(requireActivity().getApplicationContext(),FavoriteDatabase.class,"myfavdb").fallbackToDestructiveMigration().allowMainThreadQueries().build();

    getData(sights);

    FloatingActionButton fab = binding.fab;
    FloatingActionButton fab1 = binding.fab1;
    fab.setOnClickListener(view -> {

      Gson gson = new Gson();
      Intent intent = new Intent(getContext(), MapsSightsActivity.class);
      String intentData =gson.toJson(sights_lists);
      intent.putExtra("list",intentData);
      startActivity(intent);

    });

    fab1.setOnClickListener(v -> startActivity(new Intent(getContext(),FavoriteListActivity.class)));
    return root;
  }
  private void getData(String fileName) {
  sights_lists= Arrays.asList(new Gson().fromJson(Utils.getJsonFromAssets(requireContext(),fileName), SightsList[].class));
  setupData(sights_lists);
  }

  private void setupData(List<SightsList> sights_lists) {
    adapter=new SightsAdapter(sights_lists,requireActivity().getApplicationContext());
    recyclerView.setAdapter(adapter);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}
