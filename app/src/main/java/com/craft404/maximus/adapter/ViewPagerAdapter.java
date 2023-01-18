package com.craft404.maximus.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.craft404.maximus.ui.elite.EliteMessagesFragment;
import com.craft404.maximus.ui.important.ImportantMessagesFragment;
import com.craft404.maximus.ui.pinned.PinnedFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position == 0)
            fragment = new ImportantMessagesFragment();
        else if (position == 1)
            fragment = new EliteMessagesFragment();
        else
            fragment = new PinnedFragment();
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
            title = "Important";
        else if (position == 1)
            title = "Elite";
        else if (position == 2)
            title = "Pinned";
        return title;
    }

}
