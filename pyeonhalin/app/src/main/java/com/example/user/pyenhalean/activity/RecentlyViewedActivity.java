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

public class RecentlyViewedActivity extends BaseActivity{
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recently_viewed);
        addToolbar();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        configBottomNavigation(RecentlyViewedActivity.this,navigation);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<StoreItem> StoreInfoArrayList = new ArrayList<>();
        StoreInfoArrayList.add(new StoreItem(R.drawable.preparing_image,"a가게","수산물", "대방동"));
        StoreInfoArrayList.add(new StoreItem(R.drawable.preparing_image,"a가게","수산물", "대방동"));
        StoreInfoArrayList.add(new StoreItem(R.drawable.preparing_image,"a가게","수산물", "대방동"));
        StoreInfoArrayList.add(new StoreItem(R.drawable.preparing_image,"a가게","수산물", "대방동"));
        StoreInfoArrayList.add(new StoreItem(R.drawable.preparing_image,"a가게","수산물", "대방동"));        StoreInfoArrayList.add(new StoreItem(R.drawable.preparing_image,"a가게","수산물", "대방동"));
        StoreInfoArrayList.add(new StoreItem(R.drawable.preparing_image,"a가게","수산물", "대방동"));
        StoreInfoArrayList.add(new StoreItem(R.drawable.preparing_image,"a가게","수산물", "대방동"));
        StoreInfoArrayList.add(new StoreItem(R.drawable.preparing_image,"a가게","수산물", "대방동"));
        StoreInfoArrayList.add(new StoreItem(R.drawable.preparing_image,"a가게","수산물", "대방동"));
        StoreInfoArrayList.add(new StoreItem(R.drawable.preparing_image,"a가게","수산물", "대방동"));
        StoreInfoArrayList.add(new StoreItem(R.drawable.preparing_image,"a가게","수산물", "대방동"));
        StoreInfoArrayList.add(new StoreItem(R.drawable.preparing_image,"a가게","수산물", "대방동"));
        StoreInfoArrayList.add(new StoreItem(R.drawable.preparing_image,"a가게","수산물", "대방동"));        StoreInfoArrayList.add(new StoreItem(R.drawable.preparing_image,"a가게","수산물", "대방동"));
        StoreInfoArrayList.add(new StoreItem(R.drawable.preparing_image,"a가게","수산물", "대방동"));
        StoreInfoArrayList.add(new StoreItem(R.drawable.preparing_image,"a가게","수산물", "대방동"));
        StoreInfoArrayList.add(new StoreItem(R.drawable.preparing_image,"a가게","수산물", "대방동"));

        StoreCardViewAdapter myAdapter = new StoreCardViewAdapter(StoreInfoArrayList);

        mRecyclerView.setAdapter(myAdapter);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Intent intent = getIntent();
        updateBottomMenu(intent.getIntExtra("prevActivity",1),navigation);
    }
}
