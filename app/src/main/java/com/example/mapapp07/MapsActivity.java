package com.example.mapapp07;

import android.content.Context;
import android.content.Intent;
import android.icu.text.StringSearch;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

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

        LatLng japan=new LatLng(40.6080361, 140.463806);
        mMap.addMarker(new MarkerOptions().position(japan).title("弘前公園で”お宝を発見した!!”"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(japan));
        LatLng jp=new LatLng(38.5248500, 140.1967508);
        mMap.addMarker(new MarkerOptions().position(jp).title("ハズレ　見つけられなかった"));
        LatLng jap=new LatLng(36.65131389, 138.1809972);
        mMap.addMarker(new MarkerOptions().position(jap).title("ハズレ　見つけられなかった"));

        //経度を指定して住所を表示
        String result = getAddress(this, 40, 140);
        Log.d("QQEQ", result);

        //ロングタップした場所の住所を表示
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                String result = getAddress(MapsActivity.this, latLng.latitude, latLng.longitude);
                Toast.makeText(MapsActivity.this, " " + result, Toast.LENGTH_SHORT).show();
                Log.d("QQEQ", result);
            }
        });
    }
    //緯度経度から住所を取得するメソッド
    private String getAddress(Context con,double lat,double lng){
        Geocoder geo=new Geocoder(con,Locale.getDefault());
        List<Address> adds;

        try {
            adds=geo.getFromLocation(lat,lng,1);
        }
        catch (IOException e){
            return "";
        }

        Log.d("QQEQ",adds.toString());

        return adds.get(0).getAdminArea();
    }
    public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_maps);

            Button sendButton = findViewById(R.id.send_button);
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplication(), SubActivity.class);
                    startActivity(intent);
                }
            });
        }
    }


}
