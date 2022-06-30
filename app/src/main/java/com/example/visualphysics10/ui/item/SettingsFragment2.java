package com.example.visualphysics10.ui.item;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.example.visualphysics10.R;
import com.example.visualphysics10.ui.AboutUs;
import com.example.visualphysics10.ui.AuthorsList;

import java.util.Objects;

public class SettingsFragment2 extends PreferenceFragmentCompat {

    SwitchPreferenceCompat soundSwitch;
    Preference authorsList;
    Preference aboutUs;
    Preference logOut;
    public static boolean soundEnabled;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        soundSwitch = findPreference("sound");
        authorsList = findPreference("authors");
        aboutUs = findPreference("about");
        logOut = findPreference("exit");
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        soundSwitch.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                soundEnabled = soundSwitch.isChecked();
                return soundEnabled;
            }
        });
        authorsList.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                onSelectedAuthorsList();
                return false;
            }
        });
        aboutUs.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                onSelectedAboutUS();
                Log.d("TAG", "onPreferenceClick: ");
                return false;
            }
        });
    }

    private void onSelectedAboutUS() {
        DialogFragment dialogFragment = AboutUs.newInstance();
        dialogFragment.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "about!");
    }

    private void onSelectedAuthorsList() {
        DialogFragment dialogFragment = AuthorsList.newInstance();
        dialogFragment.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "about!");
    }
}