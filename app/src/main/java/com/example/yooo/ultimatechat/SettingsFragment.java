package com.example.yooo.ultimatechat;

import android.os.Bundle;
import 	android.support.v7.preference.PreferenceFragmentCompat;

import java.util.Objects;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
        getPreferenceScreen().findPreference("takePhoto").setOnPreferenceClickListener(new android.support.v7.preference.Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(android.support.v7.preference.Preference preference) {
                ((SettingsActivity)Objects.requireNonNull(getActivity())).captureImage();
                return true;
            }
        });
    }
}
