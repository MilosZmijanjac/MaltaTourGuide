package com.example.maltatourguide.ui.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.maltatourguide.R;
import com.example.maltatourguide.databinding.FragmentAboutBinding;

public class AboutFragment extends Fragment {

  private FragmentAboutBinding binding;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentAboutBinding.inflate(inflater, container, false);
    requireActivity().setTitle(getString(R.string.menu_about));
    return binding.getRoot();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding=null;
  }
}
