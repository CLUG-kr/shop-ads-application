package com.example.user.pyenhalean.activity;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.view.ViewGroup;

import com.example.user.pyenhalean.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;


public class MapActivity extends BaseActivity implements OnMapReadyCallback {
    NaverMap naverMap = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        addToolbar();

        MapFragment mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.map, mapFragment).commit();
        }


        mapFragment.getMapAsync(this);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        configBottomNavigation(MapActivity.this,navigation);
    }

    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        Marker marker = new Marker();
        marker.setPosition(new LatLng(37.505122, 126.957090));
        marker.setMap(this.naverMap);

        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(37.505122, 126.957090));
        naverMap.moveCamera(cameraUpdate);
    }

}
