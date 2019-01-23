package activity;

import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.user.pyenhalean.GetHTMLTask;
import com.example.user.pyenhalean.R;

import java.util.concurrent.ExecutionException;

public class HomeActivity extends BaseActivity {

    Button testBtn_1;
    Button testBtn_2;
    Button testBtn_3;

    String[] taskResult;
    String key;
    String state;
    String cookie;
    String ID = "tesdID2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addToolbar();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);

        testBtn_1 = (Button)findViewById(R.id.test_btn_1);
        testBtn_2 = (Button)findViewById(R.id.test_btn_2);
        testBtn_3 = (Button)findViewById(R.id.test_btn_3);



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
                        key = taskResult[1];
                        cookie = taskResult[2];
                        testBtn_1.setText(key);
                        break;
                    case R.id.test_btn_3:
                        GetHTMLTask taskTestData = new GetHTMLTask();
                        testBtn_3.setText(taskTestData.execute("testData",ID,key).get());
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
    }

}

