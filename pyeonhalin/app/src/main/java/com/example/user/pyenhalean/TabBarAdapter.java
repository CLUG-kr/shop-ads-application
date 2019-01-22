package com.example.user.pyenhalean;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabBarAdapter extends FragmentPagerAdapter {
    private final List<Fragment> sortList = new ArrayList<>();
    private final List<String> listTitle = new ArrayList<>();
    public TabBarAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFrgament(Fragment fragment, String title){
        sortList.add(fragment);
        listTitle.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTitle.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return sortList.get(position);
    }

    @Override
    public int getCount() {
        return listTitle.size();
    }
}
