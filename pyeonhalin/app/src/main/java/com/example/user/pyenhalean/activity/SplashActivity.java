package com.example.user.pyenhalean.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.user.pyenhalean.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SplashActivity extends AppCompatActivity {

    Intent mainIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mainIntent = new Intent(this, HomeActivity.class);
        getMenuTask getMenuTask= new getMenuTask(this);
        getMenuTask.execute(mainIntent);

    }

}

class getMenuTask extends AsyncTask<Intent,Void,Intent> {
    String sUrl = "http://18.217.11.241:1234";

    private Elements element;

    SplashActivity activity;


    getMenuTask(SplashActivity activity){
        super();
        this.activity  = activity;
    }

    @Override
    protected void onPostExecute(Intent result) {
        super.onPostExecute(result);
        //startActivity(activity,result,result.getExtras());
    }



    @Override
    protected Intent doInBackground(Intent... params) {
        Intent returnIntent = params[0];

        return returnIntent;
    }
}


