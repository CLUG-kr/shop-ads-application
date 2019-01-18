package com.example.user.pyenhalean;

import android.os.AsyncTask;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class HomeActivity extends BaseActivity {

    Button testBtn_1;
    Button testBtn_2;
    Button testBtn_3;

    String[] taskResult;
    String key;
    String state;
    String cookie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        testBtn_1 = (Button)findViewById(R.id.test_btn_1);
        testBtn_2 = (Button)findViewById(R.id.test_btn_2);
        testBtn_3 = (Button)findViewById(R.id.test_btn_3);
        configBottomNavigation(HomeActivity.this,navigation);

        Button.OnClickListener onClickListener = new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                try {
                switch (view.getId()){
                    case R.id.test_btn_1:
                        GetHTMLTask taskSignUp = new GetHTMLTask();
                        taskSignUp.execute("signUp", "testID2", "testPW");
                        break;
                    case R.id.test_btn_2:
                        GetHTMLTask taskSignIn = new GetHTMLTask();
                        taskResult = taskSignIn.execute("signIn", "testID2", "testPW",String.valueOf(System.currentTimeMillis())).get().split("#");
                        key = taskResult[1];
                        cookie = taskResult[2];
                        testBtn_1.setText(key);
                        break;
                    case R.id.test_btn_3:
                        GetHTMLTask taskTestData = new GetHTMLTask();
                        taskTestData.execute("testData","testID2", key, cookie);
                }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };

        testBtn_1.setOnClickListener(onClickListener);
        testBtn_2.setOnClickListener(onClickListener);
        testBtn_3.setOnClickListener(onClickListener);

        Log.d("test","start");
    }
}

