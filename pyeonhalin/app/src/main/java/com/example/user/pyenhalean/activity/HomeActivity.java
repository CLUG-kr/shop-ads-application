package com.example.user.pyenhalean.activity;

import android.Manifest;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.pyenhalean.GetHTMLTask;
import com.example.user.pyenhalean.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.overlay.Align;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HomeActivity extends BaseActivity {

    String[] taskResult;
    String state;;
    String ID = "tesdID2";
    String userMode = "";
    LocationManager manager;
    Location location;
    double user_latitude;
    double user_longitude;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;

    String[] response = null;



    //variable for get user location and store location
    double latitude;
    double longitude;
    String storeName;
    final Geocoder geocoder = new Geocoder(this);
    String locationName = null;
    List<Address> list = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addToolbar();
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
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



        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if((location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER)) == null
                && (location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)) == null) {

            Toast.makeText(HomeActivity.this,
                    "위치정보가 잡히지 않습니다.\n 잠시후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        user_latitude = location.getLatitude();
        user_longitude = location.getLongitude();

        try {
            //get current location
            list = geocoder.getFromLocation(user_latitude, user_longitude, 10);

            if(list.get(0).getAddressLine(0) == null){
                GetHTMLTask tesk = new GetHTMLTask();
                Log.d("address",list.get(0).toString());

                locationName = "서울특별시_강남구_대치동_"; //set string to test response

                response = tesk.execute("loadStore", locationName).get().split("#");
                Log.d("위치", locationName);
            }
            else {
                GetHTMLTask tesk = new GetHTMLTask();
                String[] tempString = list.get(0).getAddressLine(0).split(" ");
                locationName = tempString[1] + "_" + tempString[2] + "_" + tempString[3] + "_";
                response = tesk.execute("loadStore", locationName).get().split("#");
                Log.d("위치", locationName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < response.length; i++){
            Marker marker = new Marker();
            if(response[i].split("!").length >= 3){
                storeName = response[i].split("!")[0];
                latitude = Double.parseDouble(response[i].split("!")[1]);
                longitude = Double.parseDouble(response[i].split("!")[2]);

                marker.setPosition(new LatLng(latitude, longitude));
                marker.setCaptionText(storeName);
                marker.setCaptionAlign(Align.Top);
                marker.setTag(storeName);

                //주의!!! 마커가 많을 경우 Out Of Memory 오류
                // Dialog test 후 수정
                marker.setOnClickListener(new Overlay.OnClickListener(){
                    @Override
                    public boolean onClick(@NonNull Overlay overlay) {
                        showItem((String)overlay.getTag());
                        return true;
                    }
                });

                marker.setMap(naverMap);
            }

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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

