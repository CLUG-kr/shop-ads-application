package com.example.user.pyenhalean.activity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.util.Log;

import com.example.user.pyenhalean.GetHTMLTask;
import com.example.user.pyenhalean.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;


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
        double latitude;
        double longitude;
        final Geocoder geocoder = new Geocoder(this);
        String locationName = null;
        List<Address> list = null;

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        try {
            //get current location
            list = geocoder.getFromLocation(latitude, longitude, 10);
            locationName = list.get(0).getLocality() + "_" + list.get(0).getSubLocality() + "_"
                    + list.get(0).getThoroughfare() + "_";

            if(list.get(0).getLocality() != null && list.get(0).getSubLocality() != null
                    && list.get(0).getThoroughfare() != null){
                GetHTMLTask tesk = new GetHTMLTask();


                locationName = "서울특별시_동작구_대방동_"; //set string to test response

                String response = tesk.execute("loadStore", locationName).get();
                Log.d("위치", locationName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // set marker
        Marker marker = new Marker();
        marker.setPosition(new LatLng(latitude, longitude));
        marker.setMap(this.naverMap);

        // set map camera location
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(latitude, longitude));
        naverMap.moveCamera(cameraUpdate);
    }

}
