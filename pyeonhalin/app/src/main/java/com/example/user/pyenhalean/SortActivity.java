package com.example.user.pyenhalean;

import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.concurrent.ExecutionException;

public class SortActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        RecyclerView recyclerView= (RecyclerView)findViewById(R.id.item_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CardRecyclerViewAdapter(initialCardViewItem()));

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        configBottomNavigation(SortActivity.this,navigation);


    }

    CardViewItemDTO[] initialCardViewItem(){
        CardViewItemDTO[] returnCardViewItemDTO;
        GetHTMLTask taskTestData = new GetHTMLTask();
        String[] data = new String[3];
        try {
            String[] response = taskTestData.execute("testGS25").get().substring(1).split("!");
            returnCardViewItemDTO = new CardViewItemDTO[response.length];
            for(int i = 0; i < response.length; i++){
                if (response[i].split("#").length != 3){
                    data[0] = data[1] = data[2] = response[i].split("#")[0];
                }
                else
                    data = response[i].split("#");
                returnCardViewItemDTO[i] = new CardViewItemDTO(R.drawable.preparing_image
                        , data[0],data[2] + " 행사", data[1]);
            }
            return returnCardViewItemDTO;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        };
        returnCardViewItemDTO = new CardViewItemDTO[5];
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
