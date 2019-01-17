package com.example.user.pyenhalean;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends AppCompatActivity {

    public static final int INDEX_MAP_ACTIVITY = 0;
    public static final int INDEX_SORT_ACTIVITY = 1;
    public static final int INDEX_HOME_ACTIVITY = 2;
    public static final int INDEX_EVENT_ACTIVITY = 3;
    public static final int INDEX_COMMUNITY_ACTIVITY = 4;

    BottomNavigationView navigation;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.above_menu, menu);
        return true;
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    public void configBottomNavigation(final Context context, BottomNavigationView navigation) {
        final int contextIndex = getContextIndex(context);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.navigation_map:
                        if (contextIndex != INDEX_MAP_ACTIVITY) {
                            intent = new Intent(context, MapActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                        }
                        return true;
                    case R.id.navigation_sort:
                        if (contextIndex != INDEX_SORT_ACTIVITY) {
                            intent = new Intent(context, SortActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                        }
                        return true;
                    case R.id.navigation_home:
                        if (contextIndex != INDEX_HOME_ACTIVITY) {
                            intent = new Intent(context, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                        }
                        return true;

                    case R.id.navigation_event:
                        if (contextIndex != INDEX_EVENT_ACTIVITY) {
                            intent = new Intent(context, EventActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                        }
                        return true;

                    case R.id.navigation_community:
                        if (contextIndex != INDEX_COMMUNITY_ACTIVITY) {
                            intent = new Intent(context, CommunityActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                        }
                        return true;

                }
                return false;
            }
        });
    }

    private int getContextIndex(Context context) {

        if (context instanceof HomeActivity) {
            return INDEX_HOME_ACTIVITY;
        } else if (context instanceof CommunityActivity) {
            return INDEX_COMMUNITY_ACTIVITY;
        } else if (context instanceof EventActivity) {
            return INDEX_EVENT_ACTIVITY;
        } else if (context instanceof MapActivity) {
            return INDEX_MAP_ACTIVITY;
        } else if (context instanceof SortActivity) {
            return INDEX_SORT_ACTIVITY;
        }

        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

    }

    public void updateBottomMenu(Context context, BottomNavigationView navigation) {
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(getContextIndex(context));
        menuItem.setChecked(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateBottomMenu(this,navigation);
    }

}
