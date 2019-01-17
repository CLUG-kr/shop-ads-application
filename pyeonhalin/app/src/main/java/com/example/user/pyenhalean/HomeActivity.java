package com.example.user.pyenhalean;

import android.os.AsyncTask;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        configBottomNavigation(HomeActivity.this,navigation);

        GetHTMLTask task = new GetHTMLTask("signUp");
        task.execute();

        Log.d("test","start");
    }
}

