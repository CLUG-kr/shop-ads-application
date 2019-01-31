package com.example.user.pyenhalean.activity;

import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.example.user.pyenhalean.CUSortActivityFragment;
import com.example.user.pyenhalean.GS25SortActivityFragment;
import com.example.user.pyenhalean.R;
import com.example.user.pyenhalean.TabBarAdapter;
import com.example.user.pyenhalean.TestSortActivityFragment;

public class SortActivity extends BaseActivity {
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        addToolbar();

        viewPager = (ViewPager) findViewById(R.id.sort_container);
        setupViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sort_tabs);
        tabLayout.setupWithViewPager(viewPager);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        configBottomNavigation(SortActivity.this,navigation);
    }

    private void setupViewPager(ViewPager viewPager) {
        TabBarAdapter adapter = new TabBarAdapter(getSupportFragmentManager());
        adapter.addFrgament(new GS25SortActivityFragment(), "GS25");
        adapter.addFrgament(new CUSortActivityFragment(), "CU");
       // adapter.addFrgament(new TestSortActivityFragment(), "test");
        viewPager.setAdapter(adapter);
    }
}
