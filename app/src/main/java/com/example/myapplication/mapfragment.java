package com.example.myapplication;

import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.MarkerIcons;



public class mapfragment extends AppCompatActivity implements OnMapReadyCallback, NaverMap.OnMapClickListener {

    private NaverMap naverMap;
    Marker marker = new Marker();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapfragment);



        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;

        // 지도 설정 및 마커 추가 예시
//        naverMap.setMapType(NaverMap.MapType.Basic);
//        naverMap.setContentPadding(100,100,30,40);


        // 색 크기 설정
        marker.setIcon(MarkerIcons.BLACK);
        marker.setIconTintColor(Color.RED);
        marker.setWidth(Marker.SIZE_AUTO);
        marker.setHeight(Marker.SIZE_AUTO);

        // 클릭 리스너 등록
        naverMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
        // 클릭한 좌표 값(lat, lng) 가져오기
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;

        // 로그에 출력
        Log.d("Clicked Coordinate", "Latitude: " + latitude + ", Longitude: " + longitude);

        // 마커 추가 또는 위치 업데이트
        if (marker.getMap() == null) {
            marker.setPosition(latLng);
            marker.setMap(naverMap);
        } else {
            marker.setPosition(latLng);
        }
    }
}
