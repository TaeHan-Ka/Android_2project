package com.example.myapplication;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class map extends AppCompatActivity implements OnMapReadyCallback {

        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment) fragmentManager
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

        @Override
        public void onMapReady ( final GoogleMap map){

        LatLng Gunsan = new LatLng(35.945239, 126.682153);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(Gunsan);
        markerOptions.title("군산대");
        markerOptions.snippet("국립대");
        map.addMarker(markerOptions);

        map.moveCamera(CameraUpdateFactory.newLatLng(Gunsan));
        map.animateCamera(CameraUpdateFactory.zoomTo(10));

    }
}
