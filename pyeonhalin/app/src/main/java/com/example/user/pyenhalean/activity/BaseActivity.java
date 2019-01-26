package com.example.user.pyenhalean.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.pyenhalean.HeadbarSharePreferences;
import com.example.user.pyenhalean.R;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int INDEX_MAP_ACTIVITY = 0;
    public static final int INDEX_SORT_ACTIVITY = 1;
    public static final int INDEX_HOME_ACTIVITY = 2;
    public static final int INDEX_EVENT_ACTIVITY = 3;
    public static final int INDEX_COMMUNITY_ACTIVITY = 4;

    BottomNavigationView navigation;
    Toolbar myToolbar;
    ActionBarDrawerToggle dtToggle;
    DrawerLayout dlDrawer;
    NavigationView navigationView;
    View headView;
    TextView nameTV;
    TextView idTV;
    TextView keyTV;
    SharedPreferences loginData;

    void addToolbar() {
        String tempID = "", tempName = "", tempKey = "";
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        dtToggle = new ActionBarDrawerToggle(
                this, dlDrawer, myToolbar,R.string.app_name, R.string.app_name);
        dlDrawer.addDrawerListener(dtToggle);
        dtToggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headView = navigationView.getHeaderView(0);
        nameTV = headView.findViewById(R.id.headNameTV);
        idTV = headView.findViewById(R.id.headIDTV);
        keyTV = headView.findViewById(R.id.headKeyTV);
        loginData = getSharedPreferences("loginData", MODE_PRIVATE);
        load();
    }

    private void setHeadbarData(String tempID, String tempName, String tempKey) {
        nameTV.setText(tempName);
        idTV.setText(tempID);
        keyTV.setText(tempKey);
    }

    protected void setDisplayHomeBtnEnabled(boolean b){
        getSupportActionBar().setDisplayHomeAsUpEnabled(b);
        getSupportActionBar().setDisplayShowHomeEnabled(b);
    }


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
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
*/
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
                            overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
                        }
                        return true;
                    case R.id.navigation_sort:
                        if (contextIndex != INDEX_SORT_ACTIVITY) {
                            intent = new Intent(context, SortActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
                        }
                        return true;
                    case R.id.navigation_home:
                        if (contextIndex != INDEX_HOME_ACTIVITY) {
                            intent = new Intent(context, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
                        }
                        return true;

                    case R.id.navigation_event:
                        if (contextIndex != INDEX_EVENT_ACTIVITY) {
                            intent = new Intent(context, EventActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
                        }
                        return true;

                    case R.id.navigation_community:
                        if (contextIndex != INDEX_COMMUNITY_ACTIVITY) {
                            intent = new Intent(context, CommunityActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
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
    public void updateBottomMenu(int contextIndex, BottomNavigationView navigation) {
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(contextIndex);
        menuItem.setChecked(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateBottomMenu(this,navigation);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.like_market:
                Toast.makeText(this, "첫번째", Toast.LENGTH_SHORT).show();
                break;
            case R.id.recent_market:
                Intent intent = new Intent(BaseActivity.this, RecentlyViewedActivity.class);
                intent.putExtra("prevActivity",getContextIndex(this));
                startActivity(intent);
                break;
            case R.id.setting:
                Toast.makeText(this, "두번째", Toast.LENGTH_SHORT).show();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void save(String ID, String name, String key) {
        // SharedPreferences 객체만으론 저장 불가능 Editor 사용
        SharedPreferences.Editor editor = loginData.edit();

        // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        editor.putString("id", ID);
        editor.putString("name", name);
        editor.putString("key",key);
        editor.apply();
    }

    // 설정값을 불러오는 함수
    public void load() {
        // SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
        // 저장된 이름이 존재하지 않을 시 기본값
        String ID = loginData.getString("id", "null");
        String name = loginData.getString("name", "null");
        String key = loginData.getString("key", "null");
        setHeadbarData(ID, name, key);
    }


}
