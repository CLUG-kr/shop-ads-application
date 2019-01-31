package com.example.user.pyenhalean.activity;

import android.Manifest;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.pyenhalean.GetHTMLTask;
import com.example.user.pyenhalean.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HomeActivity extends BaseActivity {

    Button testBtn_1;
    Button testBtn_2;
    Button testBtn_3;

    String[] taskResult;
    String state;;
    String ID = "tesdID2";
    String userMode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addToolbar();
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getContext(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }

        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .check();


        testBtn_1 = findViewById(R.id.test_btn_1);
        testBtn_2 = findViewById(R.id.test_btn_2);
        testBtn_3 = findViewById(R.id.test_btn_3);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);

        configBottomNavigation(HomeActivity.this,navigation);
        Button.OnClickListener onClickListener = new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                try {
                switch (view.getId()){
                    case R.id.test_btn_1:
                        GetHTMLTask taskSignUp = new GetHTMLTask();
                        taskSignUp.execute("signUp", ID, "testPW");
                        break;
                    case R.id.test_btn_2:
                        GetHTMLTask taskSignIn = new GetHTMLTask();
                        taskResult = taskSignIn.execute("signIn", ID, "testPW",String.valueOf(System.currentTimeMillis())).get().split("#");
                        if(taskResult.length == 4){
                            cookie = taskResult[3];
                            loginSave(ID,taskResult[2],taskResult[1],"true");
                            loginLoad();
                        }
                        else {
                            testBtn_1.setText("error");
                        }

                        break;
                    case R.id.test_btn_3:

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
    }



    @Override
    protected void onPause() {
        super.onPause();
        /*
        if(cookie != null && ID != null) {
            try {
                GetHTMLTask taskLogout = new GetHTMLTask();
                Log.d("logout_result", taskLogout.execute("logout", ID, cookie).get());

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        */
    }

}

