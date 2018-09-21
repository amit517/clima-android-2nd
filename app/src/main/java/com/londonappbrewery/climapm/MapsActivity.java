package com.londonappbrewery.climapm;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private float DEFAULTZOOM = 14.0f;

    public final float getMaxZoomLevel() {
        return 0;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


    }

    public void geoLocate(View v) throws IOException
    {
        hideSoftKey(v);


        EditText loactionText = (EditText) findViewById(R.id.LocationText);
        String Location = loactionText.getText().toString();



        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(Location,1 );
        Address add = list.get(0);

        String locality = add.getLocality();

        Toast.makeText(this, locality, Toast.LENGTH_SHORT).show();

        double lat = add.getLatitude();
        double lng = add.getLongitude();

        gotoLocation(lat,lng,DEFAULTZOOM);

    }

    private void gotoLocation(double lat, double lng, float defaultzoom) {
        LatLng myLatLang = new LatLng(lat,lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(myLatLang, defaultzoom);
        mMap.moveCamera(update);

    }



    private void hideSoftKey (View v)
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

}
