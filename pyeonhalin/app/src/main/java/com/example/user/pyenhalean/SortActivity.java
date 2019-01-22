package com.example.user.pyenhalean;

import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.concurrent.ExecutionException;

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
        viewPager.setAdapter(adapter);
    }
}
