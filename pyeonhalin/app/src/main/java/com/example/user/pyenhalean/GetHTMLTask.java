package com.example.user.pyenhalean;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class GetHTMLTask extends AsyncTask<Void, Void, String> {
    String sUrl = "http://18.188.162.184:5000/";
    String routeUrl;

    private Elements element;

    public GetHTMLTask(String routeUrl) {
        this.routeUrl = routeUrl;
    }

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
            doc = Jsoup.connect(sUrl + routeUrl).data("id", "12351235").data("pw", "mypass").post();
            returnString = doc.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("test",returnString);
        return returnString;

    }
}
