package com.example.user.pyenhalean.activity;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.user.pyenhalean.R;
import com.example.user.pyenhalean.StoreCardViewAdapter;
import com.example.user.pyenhalean.model.StoreItem;

import java.util.ArrayList;

public class AddItemActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recently_viewed);
        addToolbar();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        configBottomNavigation(AddItemActivity.this,navigation);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Intent intent = getIntent();
        updateBottomMenu(intent.getIntExtra("prevActivity",1),navigation);
    }
}
