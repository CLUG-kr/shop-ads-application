package com.example.user.pyenhalean.activity;

import android.app.Activity;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.pyenhalean.GetHTMLTask;
import com.example.user.pyenhalean.R;
import com.example.user.pyenhalean.*;

import java.util.concurrent.ExecutionException;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int INDEX_MAP_ACTIVITY = 0;
    public static final int INDEX_SORT_ACTIVITY = 1;
    public static final int INDEX_HOME_ACTIVITY = 2;
    public static final int INDEX_EVENT_ACTIVITY = 3;
    public static final int INDEX_COMMUNITY_ACTIVITY = 4;

    private Activity sApplication;
    BottomNavigationView navigation;
    Toolbar myToolbar;
    ActionBarDrawerToggle dtToggle;
    DrawerLayout dlDrawer;
    NavigationView navigationView;
    View headView;
    TextView nameTV;
    public TextView idTV;
    TextView keyTV;
    TextView loginOKTV;
    Button loginButton;
    Button logoutButton;
    SharedPreferences loginData;

    String cookie;
    MenuItem item;


    public Context getContext() {
        return sApplication.getApplicationContext();
    }

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
        headLinkViewID();
        loginData = getSharedPreferences("loginData", MODE_PRIVATE);
        loginLoad();
        sApplication = this;
        if(nameTV.getText().toString().equals("owner")){
            navigationView.getMenu().findItem(R.id.manageItem).setVisible(true);
        }
    }

    private void headLinkViewID() {
        nameTV = headView.findViewById(R.id.headNameTV);
        idTV = headView.findViewById(R.id.headIDTV);
        keyTV = headView.findViewById(R.id.headKeyTV);
        loginOKTV = headView.findViewById(R.id.headLoginOKTV);
        loginButton = headView.findViewById(R.id.loginButton);
        logoutButton = headView.findViewById(R.id.logoutButton);

        loginButton.setOnClickListener(onclickListener);
        logoutButton.setOnClickListener(onclickListener);
    }

    private void setHeadbarData(String tempID, String tempName, String tempKey, String loginOK) {
        nameTV.setText(tempName);
        idTV.setText(tempID);
        keyTV.setText(tempKey);
        loginOKTV.setText(loginOK);
    }

    protected void setDisplayHomeBtnEnabled(boolean b){
        getSupportActionBar().setDisplayHomeAsUpEnabled(b);
        getSupportActionBar().setDisplayShowHomeEnabled(b);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.above_menu, menu);
        item = menu.findItem(R.id.manageItem);

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
                            finish();
                            overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
                        }
                        return true;
                    case R.id.navigation_sort:
                        if (contextIndex != INDEX_SORT_ACTIVITY) {
                            intent = new Intent(context, SortActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
                        }
                        return true;
                    case R.id.navigation_home:
                        if (contextIndex != INDEX_HOME_ACTIVITY) {
                            intent = new Intent(context, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
                        }
                        return true;

                    case R.id.navigation_event:
                        if (contextIndex != INDEX_EVENT_ACTIVITY) {
                            intent = new Intent(context, EventActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
                        }
                        return true;

                    case R.id.navigation_community:
                        if (contextIndex != INDEX_COMMUNITY_ACTIVITY) {
                            intent = new Intent(context, CommunityActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            finish();
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
                Intent resentMarketIntent = new Intent(BaseActivity.this, RecentlyViewedActivity.class);
                resentMarketIntent.putExtra("prevActivity",getContextIndex(this));
                startActivity(resentMarketIntent);
                break;
            case R.id.setting:
                Toast.makeText(this, "두번째", Toast.LENGTH_SHORT).show();
                break;
            case R.id.manageItem:
                Intent manageItemIntent = new Intent(BaseActivity.this, OwnerManageItemActivity.class);
                manageItemIntent.putExtra("prevActivity",getContextIndex(this));
                startActivity(manageItemIntent);
                overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
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

    public void loginSave(String ID, String name, String key, String loginOK) {
        // SharedPreferences 객체만으론 저장 불가능 Editor 사용
        SharedPreferences.Editor editor = loginData.edit();

        // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        if(loginOK.equals("false")){
            editor.putString("id", "로그인을 해주세요.");
            editor.putString("name", "");
            editor.putString("key", "");
            editor.putString("loginOK", "false");
            GetHTMLTask task = new GetHTMLTask();
            try {
                task.execute("logout", ID,key).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        else{
            editor.putString("id", ID);
            editor.putString("name", name);
            editor.putString("key",key);
            editor.putString("loginOK", loginOK);

        }


        editor.apply();
    }

    // 설정값을 불러오는 함수
    public void loginLoad() {
        // SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
        // 저장된 이름이 존재하지 않을 시 기본값
        String loginOK = loginData.getString("loginOK","");
        String ID = loginData.getString("id", "");
        String name = loginData.getString("name", "");
        String key = loginData.getString("key", "");
        setHeadbarData(ID, name, key,loginOK);
        if(loginOK.equals("true")){
            logoutButton.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.GONE);
        }
        else {
            logoutButton.setVisibility(View.GONE);
            loginButton.setVisibility(View.VISIBLE);
        }
    }
    Button.OnClickListener onclickListener = new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.loginButton:
                    LoginDialog myLoginDialog = new LoginDialog(sApplication);
                    myLoginDialog.setLoginDialogListener(new LoginDialogListener(){
                        @Override
                        public void onPositiveClicked(String id, String pw, String key, String cookie1, String type) {
                            loginSave(id,type, key, "true");
                            loginLoad();
                            cookie = cookie1;
                            Toast.makeText(getContext(), "로그인 성공!", Toast.LENGTH_LONG).show();
                            if(type.equals("owner")){
                                navigationView.getMenu().findItem(R.id.manageItem).setVisible(true);
                            }
                        }
                        @Override
                        public void onNegativeClicked() {

                        }
                    });
                    myLoginDialog.show();
                    break;
                case R.id.logoutButton:
                    loginSave("null","null", "null", "false");
                    loginLoad();
                    navigationView.getMenu().findItem(R.id.manageItem).setVisible(false);
                    break;

            }
        }

    };


}
