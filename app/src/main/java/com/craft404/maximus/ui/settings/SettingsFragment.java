package com.craft404.maximus.ui.settings;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.craft404.maximus.R;
import com.craft404.maximus.ui.dialogs.InfoDialog;
import com.craft404.maximus.util.Constants;

import java.io.File;
import java.io.IOException;

public class SettingsFragment extends PreferenceFragmentCompat {
    private static final String TAG = "SettingsFragment";
    private SettingsViewModel mViewModel;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        mViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        Preference important_filters = findPreference("important_filters");
        assert important_filters != null;
        important_filters.setOnPreferenceClickListener(preference -> {
            NavHostFragment.findNavController(SettingsFragment.this).navigate(R.id.action_settingsFragment_to_importantFiltersFragment);
            return true;
        });

        Preference elite = findPreference("elite");
        assert elite != null;

        elite.setOnPreferenceClickListener(preference -> {
             new MaterialAlertDialogBuilder(getContext(), R.style.DialogTheme)
                    .setTitle("Confirm Reset Elite Members")
                    .setMessage("Removes all Elite Members. Their messages will not be deleted" +
                            "\nThis action cannot be undone. Do you still want to proceed?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        try {
                            mViewModel.resetElite();
                            Toast.makeText(getContext(), "All Elite Members removed", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.e(TAG, "onClick: ", e);
                        } finally {
                            dialog.dismiss();
                        }
                    }).setNegativeButton("No", (dialog, which) -> dialog.dismiss()).create().show();
            return true;
        });
        Preference spammers = findPreference("spammers");
        assert spammers != null;
        spammers.setOnPreferenceClickListener(preference -> {
             new MaterialAlertDialogBuilder(getContext(), R.style.DialogTheme)
                    .setTitle("Confirm Reset Spammers")
                    .setMessage("Removes all Spammers. Their messages will not be deleted" +
                            "\nThis action cannot be undone. Do you still want to proceed?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                mViewModel.resetSpammers();
                                Toast.makeText(getContext(), "All Spammers removed", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Log.e(TAG, "onClick: ", e);
                            } finally {
                                dialog.dismiss();
                            }
                        }
                    }).setNegativeButton("No", (dialog, which) -> dialog.dismiss()).create().show();
            return true;
        });
        Preference clearApp = findPreference("clearApp");
        assert clearApp != null;
        clearApp.setOnPreferenceClickListener(preference -> {
             new MaterialAlertDialogBuilder(getContext(), R.style.DialogTheme)
                    .setTitle("Confirm Delete")
                    .setMessage(R.string.clear_data)
                    .setPositiveButton("Yes", (dialog, which) -> {
                        try {
                            mViewModel.deleteAllMessages();
                            Toast.makeText(getContext(), "All Messages deleted Successfully", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.e(TAG, "onClick: ", e);
                        }
                    }).setNegativeButton("No", (dialog, which) -> dialog.dismiss()).create().show();
            return true;
        });
        Preference clearAll = findPreference("clearAll");
        assert clearAll != null;
        clearAll.setOnPreferenceClickListener(preference -> {
             new MaterialAlertDialogBuilder(getContext(), R.style.DialogTheme)
                    .setTitle("Confirm Delete")
                    .setMessage(R.string.delete_app_data)
                    .setPositiveButton("Yes", (dialog, which) -> {
                        try {
                            mViewModel.deleteAllMessages();
                            File file = new File(Constants.APP_DIR);
                            if (file.exists())
                                deleteAllFiles(file);
                            Toast.makeText(getContext(), "All data deleted Successfully", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.e(TAG, "onClick: ", e);
                        }
                    }).setNegativeButton("No", (dialog, which) -> dialog.dismiss()).create().show();
            return true;
        });
    }

    void deleteAllFiles(File parentDir) throws IOException {
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                deleteAllFiles(file);
            } else {
                file.delete();
            }
        }
    }
}