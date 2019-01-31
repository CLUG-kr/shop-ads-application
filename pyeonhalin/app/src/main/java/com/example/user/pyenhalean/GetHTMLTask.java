package com.example.user.pyenhalean;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Connection;
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


    @Override
    protected void onPostExecute(String html) {
        super.onPostExecute(html);
        //startActivity(activity,result,result.getExtras());
    }


    @Override
    protected String doInBackground(String... parm) {
        String returnString = "";
        Connection.Response res;
        Document doc;
        try {
            if(parm[0].equals("signIn")){
                res = Jsoup.connect(sUrl + parm[0]).data("id", parm[1]).data("pw", parm[2]).data("time", parm[3]).method(Connection.Method.POST).execute();
                element = res.parse().select("h1");
                if(element.size() == 3){
                    returnString = element.get(0).text() + "#" + element.get(1).text() + "#" +element.get(2).text() + "#"+ res.cookie(parm[1]);
                }
                else {
                    returnString = element.get(0).text();
                }

            }
            else if(parm[0].equals("signUp")){
                res = Jsoup.connect(sUrl + parm[0]).data("id", parm[1]).data("pw", parm[2]).method(Connection.Method.POST).execute();
                element = res.parse().select("h1");
                returnString = element.get(0).text();
            }
            else if(parm[0].equals("testData")){
                doc = Jsoup.connect(sUrl + parm[0]).data("id", parm[1]).cookie(parm[1],parm[2]).post();
                element = doc.select("h1");
                returnString = element.text();
            }
            else if(parm[0].equals("logout")){
                doc = Jsoup.connect(sUrl+parm[0]).data("id",parm[1]).cookie(parm[1],parm[2]).post();
                element = doc.select("h1");
                returnString = element.text();
            }
            else if(parm[0].equals("testCU")){
                doc = Jsoup.connect(sUrl+parm[0]).get();
                element = doc.select("h1");
                returnString = element.text();
            }
            else if(parm[0].equals("testGS25")){
                doc = Jsoup.connect(sUrl+parm[0]).get();
                element = doc.select("h1");
                returnString = element.text();
            }
            else if(parm[0].equals("logout")){
                doc = Jsoup.connect(sUrl+parm[0]).data("id",parm[1]).post();
                element = doc.select("h1");
                returnString = element.text();
            }
            else if(parm[0].equals("ownerItemUpload")){
                doc = Jsoup.connect(sUrl+parm[0]).data("id",parm[1]).data("itemName",parm[3]).data("itemPrice",parm[4]).data("event",parm[5]).cookie(parm[1],parm[2]).post();
                element = doc.select("h1");
                returnString = element.text();
            }
            else if(parm[0].equals("ownerSignUp")){
                doc = Jsoup.connect(sUrl+parm[0]).data("id",parm[1]).data("pw",parm[2]).data("address",parm[3]).data("addressX",parm[4]).data("addressY",parm[5]).post();
                element = doc.select("h1");
                returnString = element.text();
            }
            else if(parm[0].equals("loadStore")){
                doc = Jsoup.connect(sUrl+parm[0]).data("address",parm[1]).post();
                element = doc.select("h1");
                for(int i = 0; i < element.size();i++){
                    returnString += element.get(i).text()+"#";
                }
            }
            else if(parm[0].equals("ownerItemDownload")){
                doc = Jsoup.connect(sUrl+parm[0]).data("id",parm[1]).post();
                element = doc.select("h1");
                for(int i = 0; i < element.size();i++){
                    returnString += element.get(i).text()+"#";
                }
            }
            else if(parm[0].equals("ownerItemDelete")){
                doc = Jsoup.connect(sUrl+parm[0]).data("id",parm[1]).data("itemName",parm[2]).post();
                element = doc.select("h1");
                returnString = element.text();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("test",returnString);
        return returnString;

    }
}
