package com.example.user.pyenhalean;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class GetHTMLTask extends AsyncTask<String, Void, String> {
    String sUrl = "http://18.188.162.184:5010/";
    String routeUrl;
    String loginCode;
    String id;
    String pw;
    String time = "temp";
    private Elements element;

    public GetHTMLTask(String routeUrl, String loginCode) {
        this.routeUrl = routeUrl;
        this.loginCode = loginCode;
    }

    public GetHTMLTask(String routeUrl, String id, String pw) {
        this.routeUrl = routeUrl;
        this.id = id;
        this.pw = pw;
    }

    @Override
    protected void onPostExecute(String html) {
        super.onPostExecute(html);
        //startActivity(activity,result,result.getExtras());
    }


    @Override
    protected String doInBackground(String... parm) {
        String returnString = "";
        Document doc = null;
        try {
            if(parm[0].equals("signIn")){
                doc = Jsoup.connect(sUrl + parm[0]).data("id", parm[1]).data("pw", parm[2]).data("time", time).post();
                element = doc.select("h1");
                returnString = element.get(0).text() + "//" + element.get(2).text();
            } else if(parm[0].equals("signUp")){
                doc = Jsoup.connect(sUrl + parm[0]).data("id", parm[1]).data("pw", parm[2]).post();
                element = doc.select("h1");
                returnString = element.get(0).text();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("test",returnString);
        return returnString;

    }
}
