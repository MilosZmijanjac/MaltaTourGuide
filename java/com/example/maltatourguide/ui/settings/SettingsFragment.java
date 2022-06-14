package com.example.maltatourguide.ui.settings;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.maltatourguide.MainActivity;
import com.example.maltatourguide.R;

import java.util.Objects;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey)   {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        requireActivity().setTitle(getString(R.string.menu_settings));
        Preference examplePreference = findPreference("button");
        examplePreference.setOnPreferenceClickListener(l->{
             this.requireActivity().recreate();
            return true;
        });
    }


}