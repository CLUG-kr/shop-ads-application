package activity;

import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;

import com.example.user.pyenhalean.R;

public class CommunityActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        addToolbar();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        configBottomNavigation(CommunityActivity.this,navigation);
    }

}
