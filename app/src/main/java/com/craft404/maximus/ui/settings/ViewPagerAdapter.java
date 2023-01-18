package com.craft404.maximus.ui.settings;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position == 0)
            fragment = new KeywordsFragment();
        else if (position == 1)
            fragment = new LinksFragment();
        else
            fragment = new OtherFilterFragment();
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
            title = "Keywords";
        else if (position == 1)
            title = "Links";
        else if (position == 2)
            title = "Other";
        return title;
    }

}
