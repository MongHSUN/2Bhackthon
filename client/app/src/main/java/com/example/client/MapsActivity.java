package com.example.client;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

    double lat,lng;
    String device_name;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Bundle params = getIntent().getExtras();
        if (params != null) {
            lat = Double.parseDouble(params.getString ("send_lat")) ;
            lng = Double.parseDouble(params.getString ("send_lng"));
            device_name=params.getString ("send_name");
        }
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void cameraFocusOnMe(double lat, double lng){
        CameraPosition camPosition = new CameraPosition.Builder()
                .target(new LatLng(lat, lng))
                .zoom(16)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camPosition));
    }

    private void set_information(){
        TextView TextView =(TextView)findViewById(R.id.txtOutput);
        String info = "device name : "+ device_name+"     "+
                "longitude : " + lng +"     "+"latitude : " + lat +"     "+"Provider : GPS " ;
        TextView.setText(info);
    }

    private void setUpMap() {
        cameraFocusOnMe(lat,lng);
        MarkerOptions markerOpt = new MarkerOptions();
        markerOpt.position(new LatLng(lat, lng));
        markerOpt.title("NOW I AM HERE...");

        set_information();
        mMap.addMarker(markerOpt);
    }
}
