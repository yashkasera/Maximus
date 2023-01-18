package com.craft404.maximus.ui

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.craft404.maximus.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}