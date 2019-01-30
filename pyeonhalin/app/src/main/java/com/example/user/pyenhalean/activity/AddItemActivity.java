package com.example.user.pyenhalean.activity;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.pyenhalean.GetHTMLTask;
import com.example.user.pyenhalean.LoginDialog;
import com.example.user.pyenhalean.LoginDialogListener;
import com.example.user.pyenhalean.R;
import com.example.user.pyenhalean.StoreCardViewAdapter;
import com.example.user.pyenhalean.model.StoreItem;

import java.util.ArrayList;

public class AddItemActivity extends BaseActivity {

    EditText price;
    EditText name;
    Button submitBtn;
    Spinner eventSpinner;
    String eventName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        addToolbar();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        price = (EditText) findViewById(R.id.itemPriceTV);
        name = (EditText)findViewById(R.id.itemNameTV);
        submitBtn=(Button)findViewById(R.id.submitButton);
        eventSpinner=(Spinner)findViewById(R.id.event_spinner);

        configBottomNavigation(AddItemActivity.this,navigation);


        eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                eventName = (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Button.OnClickListener onclickListener = new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.submitButton:
                        GetHTMLTask task = new GetHTMLTask();
                        task.execute("ownerItemUpload",idTV.getText().toString(),keyTV.getText().toString() ,name.getText().toString(), price.getText().toString(),"event");
                }
            }
        };

        submitBtn.setOnClickListener(onclickListener);

    }

    @Override
    protected void onStart(){
        super.onStart();
        Intent intent = getIntent();
        updateBottomMenu(intent.getIntExtra("prevActivity",1),navigation);
    }
}
