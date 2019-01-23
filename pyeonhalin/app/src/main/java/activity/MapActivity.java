package activity;

import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.view.ViewGroup;

import com.example.user.pyenhalean.R;


public class MapActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        addToolbar();

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        configBottomNavigation(MapActivity.this,navigation);
    }

}
