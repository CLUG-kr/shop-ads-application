package com.example.user.pyenhalean.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.user.pyenhalean.R;

public class EventActivity extends BaseActivity {

    private WebView webView;
    private TextView result;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        addToolbar();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        configBottomNavigation(EventActivity.this,navigation);

        result = (TextView) findViewById(R.id.result);

        // WebView 초기화
        init_webView();

        // 핸들러를 통한 JavaScript 이벤트 반응
        handler = new Handler();


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
        webView.addJavascriptInterface(new AndroidBridge(), "TestApp");
        // web client 를 chrome 으로 설정
        webView.setWebChromeClient(new WebChromeClient());
        // webview url load
        webView.loadUrl("http://18.188.162.184:5010/inputAddress");
    }

    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    result.setText(String.format("%s",arg1 ));
                    // WebView를 초기화 하지않으면 재사용할 수 없음
                    init_webView();
                }
            });
        }
    }




}
