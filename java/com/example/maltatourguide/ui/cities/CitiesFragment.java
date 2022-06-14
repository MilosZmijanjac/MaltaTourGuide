package com.example.maltatourguide.ui.cities;

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
import com.example.maltatourguide.MapsCitiesActivity;
import com.example.maltatourguide.R;
import com.example.maltatourguide.Utils;
import com.example.maltatourguide.databinding.FragmentCitiesBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CitiesFragment extends Fragment {

  private FragmentCitiesBinding binding;
  private List<City>  cityList;
  private RecyclerView recyclerView;
  CitiesAdapter adapter;
  String cities;


  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {

    SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(requireContext());
    Locale locale;
    if(!sharedPreferences.getString("language_pref","English").equals("English")){
      locale = new Locale("sr");
      cities="cities_sr.json";
    }
    else{
      locale = new Locale("en");
      cities="cities.json";
    }

    Locale.setDefault(locale);
    Resources resources = getResources();
    Configuration config = resources.getConfiguration();
    config.setLocale(locale);
    resources.updateConfiguration(config, resources.getDisplayMetrics());
    requireActivity().setTitle(getString(R.string.menu_cities));
    binding = FragmentCitiesBinding.inflate(inflater, container, false);
    View root = binding.getRoot();
    recyclerView=binding.recyclerviewCities;
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    cityList=new ArrayList<>();
    getData(cities);
    FloatingActionButton fab = binding.fabCities;
    fab.setOnClickListener(view -> {

      Gson gson = new Gson();
      Intent intent = new Intent(getContext(), MapsCitiesActivity.class);
      String intentData =gson.toJson(cityList);
      intent.putExtra("citiesList",intentData);
      startActivity(intent);

    });

    return root;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

  private void getData(String fileName) {
    cityList= Arrays.asList(new Gson().fromJson(Utils.getJsonFromAssets(requireContext(),fileName),City[].class));
    setupData(cityList);
  }
  private void setupData(List<City> cityList) {
    adapter=new CitiesAdapter(cityList,requireActivity().getApplicationContext());
    recyclerView.setAdapter(adapter);
  }

}
