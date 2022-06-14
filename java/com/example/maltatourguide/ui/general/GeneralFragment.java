package com.example.maltatourguide.ui.general;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.maltatourguide.R;
import com.example.maltatourguide.databinding.FragmentGeneralBinding;


public class GeneralFragment extends Fragment {

  private FragmentGeneralBinding binding;

  @RequiresApi(api = Build.VERSION_CODES.O)
  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    GeneralViewModel generalViewModel =
            new ViewModelProvider(this).get(GeneralViewModel.class);

    binding = FragmentGeneralBinding.inflate(inflater, container, false);
    requireActivity().setTitle(getString(R.string.menu_general));
    View root = binding.getRoot();

    MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.anthem);
    Button btn=binding.playbtn;
    btn.setOnClickListener(v -> {
      if(!mediaPlayer.isPlaying()){
        mediaPlayer.start();
        generalViewModel.setText("STOP").observe(getViewLifecycleOwner(), btn::setText);
      }
      else {
        mediaPlayer.pause();
        generalViewModel.setText("PLAY").observe(getViewLifecycleOwner(), btn::setText);
      }

    });
    return root;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}
