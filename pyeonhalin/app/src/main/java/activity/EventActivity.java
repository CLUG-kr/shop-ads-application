package activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;

import com.example.user.pyenhalean.R;

public class EventActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        addToolbar();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        configBottomNavigation(EventActivity.this,navigation);
    }

}
