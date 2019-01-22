package com.example.user.pyenhalean;

import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MapActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        addToolbar();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        configBottomNavigation(MapActivity.this,navigation);
    }
}
