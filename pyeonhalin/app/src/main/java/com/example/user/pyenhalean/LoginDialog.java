package com.example.user.pyenhalean;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class LoginDialog extends Dialog implements View.OnClickListener {

    LoginDialogListener LoginDialogListener;

    String[] taskResult;
    private Context context;
    private EditText idEditText;
    private EditText pwEditText;

    private Button loginButton;
    private Button cancleButton;
    private Button registerButton;

    private String ID;
    private String PW;
    private String cookie;
    private String key;
    private String type;

    public LoginDialog(Context context) {
        super(context);
        this.context = context;
    }

    public void setLoginDialogListener(LoginDialogListener loginDialogListener) {
        this.LoginDialogListener = loginDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_login);

        idEditText = (EditText) findViewById(R.id.idEditText);
        pwEditText = (EditText) findViewById(R.id.pwEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
        cancleButton = (Button) findViewById(R.id.cancleButon);
        registerButton = (Button) findViewById(R.id.registerButon);

        loginButton.setOnClickListener(this);
        cancleButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                try {
                    ID = idEditText.getText().toString();
                    PW = pwEditText.getText().toString();
                    GetHTMLTask taskSignIn = new GetHTMLTask();
                    taskResult = taskSignIn.execute("signIn", ID, PW, String.valueOf(System.currentTimeMillis())).get().split("#");
                    if (taskResult.length == 4) {
                        cookie = taskResult[3];
                        key = taskResult[1];
                        type = taskResult[2];
                        LoginDialogListener.onPositiveClicked(ID, PW, key, cookie, type);
                        this.dismiss();
                        break;
                    }
                    else {
                        Toast.makeText(getContext(), "로그인 실패", Toast.LENGTH_LONG).show();
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            case R.id.cancleButon:
//                LoginDialogListener.onNegativeClicked();
                cancel();
                break;
            case R.id.registerButon:
                break;
        }
    }
}
