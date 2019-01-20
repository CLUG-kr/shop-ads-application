package com.example.user.pyenhalean;

import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class SortActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        RecyclerView recyclerView= (RecyclerView)findViewById(R.id.item_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CardRecyclerViewAdapter(initialCardViewItem()));
        configBottomNavigation(SortActivity.this,navigation);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
    }

    CardViewItemDTO[] initialCardViewItem(){
        CardViewItemDTO[] returnCardViewItemDTO = new CardViewItemDTO[5];
        returnCardViewItemDTO[0] = new CardViewItemDTO(R.drawable.preparing_image, "첫번째"
                , "설명", "1500원");
        returnCardViewItemDTO[1] = new CardViewItemDTO(R.drawable.preparing_image, "두번째"
                , "설명", "1330원");
        returnCardViewItemDTO[2] = new CardViewItemDTO(R.drawable.preparing_image, "세번째"
                , "설명", "2500원");
        returnCardViewItemDTO[3] = new CardViewItemDTO(R.drawable.preparing_image, "세번째"
                , "설명", "2500원");
        returnCardViewItemDTO[4] = new CardViewItemDTO(R.drawable.preparing_image, "세번째"
                , "설명", "2500원");
        return returnCardViewItemDTO;
    }
}
