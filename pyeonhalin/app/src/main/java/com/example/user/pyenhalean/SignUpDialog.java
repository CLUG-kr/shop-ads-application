package com.example.user.pyenhalean;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class SignUpDialog extends Dialog implements View.OnClickListener {

    String taskResult;
    private Context context;
    private EditText idEditText;
    private EditText pwEditText;
    private EditText addressEditText;

    private Button addressCallButton;
    private Button cancleButton;
    private Button registerButton;

    private Spinner eventSpinner;

    private String ID;
    private String PW;
    private String address;
    private String addressX;
    private String addressY;
    String eventName = "";

    public SignUpDialog(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sign_up);

        idEditText = (EditText) findViewById(R.id.idEditText);
        pwEditText = (EditText) findViewById(R.id.pwEditText);
        addressEditText = (EditText)findViewById(R.id.addressEditText);
        addressCallButton = (Button) findViewById(R.id.addressButton);
        cancleButton = (Button) findViewById(R.id.cancleButon);
        registerButton = (Button) findViewById(R.id.registerButon);

        addressCallButton.setOnClickListener(this);
        cancleButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        eventSpinner = (Spinner)findViewById(R.id.userTypeSpinner);
        eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                eventName = (String) parent.getItemAtPosition(position);


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addressButton:
                CallAddressDialog callAddressDialog = new CallAddressDialog(context);
                callAddressDialog.setCallAddressDialogListener(new CallAddressDialogListener() {
                    @Override
                    public void onPositiveClicked(String _address, String _addressX, String _addressY) {
                        address = _address;
                        addressX = _addressX;
                        addressY = _addressY;
                        addressEditText.setText(address);
                    }

                    @Override
                    public void onNegativeClicked() {

                    }
                });
                callAddressDialog.show();
                break;

            case R.id.cancleButon:
//                LoginDialogListener.onNegativeClicked();
                cancel();
                break;
            case R.id.registerButon:
                try {
                    ID = idEditText.getText().toString();
                    PW = pwEditText.getText().toString();
                    GetHTMLTask taskSignIn = new GetHTMLTask();
                    if(eventName.equals("점주")){
                        taskResult = taskSignIn.execute("ownerSignUp", ID, PW, address,addressX,addressY).get();
                        if (taskResult.equals("success ")) {
                            Toast.makeText(getContext(), "회원가입 성공", Toast.LENGTH_LONG).show();
                            this.dismiss();
                            break;
                        }
                        else {
                            Toast.makeText(getContext(), "로그인 실패", Toast.LENGTH_LONG).show();
                            break;
                        }
                    }
                    else if(eventName.equals("이용자")){
                        taskResult = taskSignIn.execute("signUp", ID, PW).get();
                        if (taskResult.equals("success ")) {
                            Toast.makeText(getContext(), "회원가입 성공", Toast.LENGTH_LONG).show();
                            this.dismiss();
                            break;
                        }
                        else {
                            Toast.makeText(getContext(), "로그인 실패", Toast.LENGTH_LONG).show();
                            break;
                        }
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

        }
    }
}
