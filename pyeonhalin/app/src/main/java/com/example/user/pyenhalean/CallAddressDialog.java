package com.example.user.pyenhalean;

import android.app.Dialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.pyenhalean.activity.EventActivity;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CallAddressDialog extends Dialog implements View.OnClickListener {

    CallAddressDialogListener callAddressDialogListener;

    String[] taskResult;
    private Context context;

    private String address = "";
    private String addressX;
    private String addressY;
    private WebView webView;
    private Handler handler;

    Button submitButton;


    public CallAddressDialog(Context context) {
        super(context);
        this.context = context;
    }

    public void setCallAddressDialogListener(CallAddressDialogListener callAddressDialogListener) {
        this.callAddressDialogListener = callAddressDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_call_address);
        submitButton = (Button) findViewById(R.id.submit);
        // WebView 초기화
        init_webView();

        // 핸들러를 통한 JavaScript 이벤트 반응
        handler = new Handler();
        submitButton.setOnClickListener(this);

    }


    public void init_webView() {
        // WebView 설정
        webView = (WebView) findViewById(R.id.webView);
        // JavaScript 허용
        webView.getSettings().setJavaScriptEnabled(true);
        // JavaScript의 window.open 허용
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        // 두 번째 파라미터는 사용될 php에도 동일하게 사용해야함
        webView.addJavascriptInterface(new CallAddressDialog.AndroidBridge(), "TestApp");
        // web client 를 chrome 으로 설정
        webView.setWebChromeClient(new WebChromeClient());
        // webview url load
        webView.loadUrl("http://18.188.162.184:5010/inputAddress");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submit:

        }

    }

    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        address = arg1;
                        Geocoder geocoder = new Geocoder(context);
                        List<Address> location = geocoder.getFromLocationName(address,1);
                        addressX = String.valueOf(location.get(0).getLatitude());
                        addressY = String.valueOf(location.get(0).getLongitude());
                        Log.d("addressX",addressX);
                        Log.d("addressY",addressY);
                        if(!address.equals("")){
                            callAddressDialogListener.onPositiveClicked(address, addressX, addressY);
                            dismiss();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }


}
