package com.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout map_button = (LinearLayout) findViewById(R.id.map_button);
        LinearLayout item_button = (LinearLayout) findViewById(R.id.item_button);
        LinearLayout home_button = (LinearLayout) findViewById(R.id.home_button);
        LinearLayout event_button = (LinearLayout) findViewById(R.id.event_button);
        LinearLayout community_button = (LinearLayout) findViewById(R.id.community_button);

        map_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new MapFragment()).commit();
            }
        });
        item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new ItemFragment()).commit();
            }
        });
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new HomeFragment()).commit();
            }
        });
        event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new EventFragment()).commit();
            }
        });
        community_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new CommunityFragment()).commit();
            }
        });
    }
}
