package com.example.user.pyenhalean.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.user.pyenhalean.CUSortActivityFragment;
import com.example.user.pyenhalean.CardRecyclerViewAdapter;
import com.example.user.pyenhalean.GS25SortActivityFragment;
import com.example.user.pyenhalean.GetHTMLTask;
import com.example.user.pyenhalean.R;
import com.example.user.pyenhalean.TabBarAdapter;
import com.example.user.pyenhalean.TestSortActivityFragment;
import com.example.user.pyenhalean.model.CardViewItemDTO;

import java.util.concurrent.ExecutionException;

public class OwnerManageItemActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_manage_item);

        addToolbar();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.manageItem_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new CardRecyclerViewAdapter(initialCardViewItem(), this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OwnerManageItemActivity.this, AddItemActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
            }
        });


        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        configBottomNavigation(OwnerManageItemActivity.this,navigation);
    }

    CardViewItemDTO[] initialCardViewItem(){
        CardViewItemDTO[] returnCardViewItemDTO;
        GetHTMLTask taskTestData = new GetHTMLTask();
        String[] data = new String[3];
        /*
        try {
            return returnCardViewItemDTO;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        };
        */
        returnCardViewItemDTO = new CardViewItemDTO[10];
        for(int i = 0; i < 10; i++) {
            returnCardViewItemDTO[i] = new CardViewItemDTO(R.drawable.preparing_image, i+"번"
                    , "설명", "가격");
        }
        return returnCardViewItemDTO;
    }

}
