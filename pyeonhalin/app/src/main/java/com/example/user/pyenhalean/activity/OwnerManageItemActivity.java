package com.example.user.pyenhalean.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;

import com.example.user.pyenhalean.CardRecyclerViewAdapter;
import com.example.user.pyenhalean.GetHTMLTask;
import com.example.user.pyenhalean.R;
import com.example.user.pyenhalean.model.CardViewItemDTO;

import java.util.ArrayList;
import java.util.List;
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
                finish();
                overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
            }
        });



        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        configBottomNavigation(OwnerManageItemActivity.this,navigation);
    }

    CardViewItemDTO[] initialCardViewItem(){
        CardViewItemDTO[] returnCardViewItemDTO = null;
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
        final List<String> ListItems = new ArrayList<>();
        GetHTMLTask task = new GetHTMLTask();
        try {
            String[] result = task.execute("ownerItemDownload", idTV.getText().toString()).get().split("#");
            if(result[0] == ""){
                Log.d("t","없음");
                returnCardViewItemDTO = new CardViewItemDTO[1];
                returnCardViewItemDTO[0] = new CardViewItemDTO(R.drawable.preparing_image,"상품을 입력해주세요.","","");
            }
            else {
                returnCardViewItemDTO = new CardViewItemDTO[result.length];
                for(int i = 0; i < result.length; i++){
                    data = result[i].split("!");
                    returnCardViewItemDTO[i] = new CardViewItemDTO(R.drawable.preparing_image, data[0]
                            , data[2], data[1]);
            }


            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }



        return returnCardViewItemDTO;
    }

    public void showItem(String storename)
    {
        final List<String> ListItems = new ArrayList<>();
        GetHTMLTask task = new GetHTMLTask();
        try {
            String[] result = task.execute("ownerItemDownload", storename).get().split("#");
            for(int i = 0; i < result.length; i++){
                result[i] = result[i].replace('!','\n');
                result[i]+='\n';
                ListItems.add(result[i]);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(storename + " 할인품목");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int pos) {
                String selectedText = items[pos].toString();
                // 후에 이벤트 항목 추가
                //Toast.makeText(MapActivity.this, selectedText, Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }
}
