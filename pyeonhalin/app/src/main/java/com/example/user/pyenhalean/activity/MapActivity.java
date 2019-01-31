package com.example.user.pyenhalean.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.example.user.pyenhalean.GetHTMLTask;
import com.example.user.pyenhalean.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Align;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MapActivity extends BaseActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;

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

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // Map객체 생가면 onMapReady 호출 -> callBack
    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        naverMap.setLocationSource(locationSource);

        String[] response = null;

        //variable for get user location and store location
        double user_latitude;
        double user_longitude;
        double latitude;
        double longitude;
        String storeName;
        final Geocoder geocoder = new Geocoder(this);
        String locationName = null;
        List<Address> list = null;
        Location location;
        LocationManager manager;

        //variable for map overlay
        LocationOverlay locationOverlay;
        CameraUpdate cameraUpdate;

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if((location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER)) == null
                && (location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)) == null) {

            Toast.makeText(MapActivity.this,
                    "위치정보가 잡히지 않습니다.\n 잠시후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        locationOverlay = naverMap.getLocationOverlay();
        locationOverlay.setVisible(true);

        latitude = user_latitude = location.getLatitude();
        longitude = user_longitude = location.getLongitude();

        try {
            //get current location
            list = geocoder.getFromLocation(user_latitude, user_longitude, 10);
            locationName = list.get(0).getLocality() + "_" + list.get(0).getSubLocality() + "_"
                    + list.get(0).getThoroughfare() + "_";

            if(list.get(0).getLocality() != null && list.get(0).getSubLocality() != null
                    && list.get(0).getThoroughfare() != null){
                GetHTMLTask tesk = new GetHTMLTask();


                locationName = "서울특별시_강남구_대치동_"; //set string to test response

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

        // set marker
        for(int i = 0; i < response.length; i++){
            Marker marker = new Marker();
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

        // set map camera location
        // change latitude to user_latitude longitude to user_longitude after test and change 117 line
        cameraUpdate = CameraUpdate.scrollTo(new LatLng(latitude, longitude));
        locationOverlay.setPosition(new LatLng(user_latitude, user_longitude));
        naverMap.setLocationTrackingMode(LocationTrackingMode.NoFollow);
        naverMap.moveCamera(cameraUpdate);
    }

    public void showItem(String storename)
    {
        final List<String> ListItems = new ArrayList<>();
        ListItems.add("롯데)레쓰비카페타임아메\n1+1\n1,000원\n");
        ListItems.add("롯데)레쓰비카페타임라떼\n1+1\n1,000원\n");
        ListItems.add("다논)액티비아업딸기210ml\n1+1이벤트\n1,800원\n");
        ListItems.add("해피바스)로즈바디워시\n1+1\n5,000원\n");
        ListItems.add("메디안치석케어치약\n1+1\n4,500원\n");
        ListItems.add("메디안센서티브칫솔\n1+1\n3,000원\n");
        ListItems.add("2080)칫솔\n1+1\n2,600원\n");
        ListItems.add("화이트)시크릿울날대16입\n1+1\n8,200원\n");

        final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(storename + " 할인품목");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int pos) {
                String selectedText = items[pos].toString();
                // 후에 이벤트 항목 추가
                //Toast.makeText(MapActivity.this, selectedText, Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

}
