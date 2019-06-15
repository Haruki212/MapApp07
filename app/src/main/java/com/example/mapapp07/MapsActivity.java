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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int qnum =0;
    private  String[] hints={"ヒント1：リンゴが有名","ヒント2：城下町","ヒント3：近くに山",
            "ヒント4三大アルプスのうちの一つ","ヒント５あ","ヒント6い","ひんと７う","ヒント8　え",
            " ヒント９　お"
    };
    private MarkerOptions[] markerOptions={
            new MarkerOptions().position(new LatLng(40.6080361, 140.463806)) .title("弘前公園で”伝説のリンゴを発見した!!”"),
            new MarkerOptions().position(new LatLng(38.5248500, 140.1967508)) .title("ハズレ　見つけられなかった"),
            new MarkerOptions().position(new LatLng(36.65131389, 138.1809972)) .title("ハズレ　見つけられなかった"),
            new MarkerOptions().position(new LatLng(36.522329, 137.633286)) .title("飛騨山脈で　伝説のアルプスを発見した!!”"),
            new MarkerOptions().position(new LatLng(35.786362, 137.803149)) .title("ハズレ　見つけられなかった"),
            new MarkerOptions().position(new LatLng(40.664320, 140.911979)) .title("ハズレ　見つけられなかった"),
            new MarkerOptions().position(new LatLng(34.687496, 135.511230)) .title("弘前公園で”お宝を発見した!!”"),
            new MarkerOptions().position(new LatLng(35.731511, 139.712435)) .title("ハズレ　見つけられなかった"),
            new MarkerOptions().position(new LatLng(35.645241, 139.748634)) .title("ハズレ　見つけられなかった")
    };
    private Marker[]  markers=new Marker[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        final TextView textView2 =findViewById(R.id.textView2);
        final   TextView textView3 =findViewById(R.id.textView3);
        final TextView textView4 =findViewById(R.id.textView4);
        Button button = findViewById(R.id.send_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qnum++;
                textView2.setText(hints[0+(qnum-1)*3]);
                textView3.setText(hints[1 +(qnum-1)*3]);
                textView4.setText(hints[2 +(qnum-1)*3]);
                if (markers[0]!=null) markers[0].remove();
                if (markers[1]!=null) markers[1].remove();
                if (markers[2]!=null) markers[2].remove();
                markers[0]= mMap.addMarker(markerOptions[0+(qnum-1)*3]);
                markers[1]=   mMap.addMarker(markerOptions[1+(qnum-1)*3]);
                markers[2]=  mMap.addMarker(markerOptions[2+(qnum-1)*3]);
                qnum = qnum%3;

            }
        });
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
        mMap.moveCamera(CameraUpdateFactory.newLatLng(japan));


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
