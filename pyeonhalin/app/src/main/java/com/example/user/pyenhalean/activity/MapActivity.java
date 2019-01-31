package com.example.user.pyenhalean.activity;

import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.example.user.pyenhalean.GetHTMLTask;
import com.example.user.pyenhalean.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;

import java.io.IOException;
import java.util.List;


public class MapActivity extends BaseActivity implements OnMapReadyCallback {
    NaverMap naverMap = null;
    Geocoder geocoder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        addToolbar();
        geocoder = new Geocoder(this);
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
    public void onMapReady(@NonNull final NaverMap naverMap) {
        this.naverMap = naverMap;
        Marker marker = new Marker();
        marker.setPosition(new LatLng(37.505122, 126.957090));
        marker.setMap(this.naverMap);
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(37.505122, 126.957090));
        naverMap.addOnCameraChangeListener(new NaverMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(int i, boolean b) {
                CameraPosition cameraPosition = naverMap.getCameraPosition();
                if(cameraPosition.zoom < 16){
                    GetHTMLTask task = new GetHTMLTask();
                    try {
                        List<Address> addresses = geocoder.getFromLocation(cameraPosition.target.latitude,cameraPosition.target.longitude,1);
                        Log.d("target",addresses.get(0).getAddressLine(0));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // task.execute("loadStore", )
                }
            }
        });
        naverMap.moveCamera(cameraUpdate);
    }
    CameraUpdate.FinishCallback finishCallback = new CameraUpdate.FinishCallback() {
        @Override
        public void onCameraUpdateFinish() {

        }
    };


}
