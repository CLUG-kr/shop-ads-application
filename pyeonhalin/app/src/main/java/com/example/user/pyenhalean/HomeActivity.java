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

        getHTMLTask task = new getHTMLTask();
        task.execute();
        Log.d("test","start");
    }
}

class getHTMLTask extends AsyncTask<Void, Void, String> {
    String sUrl = "http://18.188.162.184:5000/signUp";

    private Elements element;

    @Override
    protected void onPostExecute(String html) {
        super.onPostExecute(html);
        //startActivity(activity,result,result.getExtras());
    }


    @Override
    protected String doInBackground(Void... voids) {
        String returnString = "";
        Document doc = null;
        try {
            doc = Jsoup.connect(sUrl).get();
            returnString = doc.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("test",returnString);
        return returnString;

    }
}
