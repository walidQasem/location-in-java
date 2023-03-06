package com.first.location_pr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.tv.AdRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  {
    Button btn;
    FusedLocationProviderClient fusedLocationClient;
    TextView longitude,largitude,ville;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);
        fusedLocationClient  = LocationServices.getFusedLocationProviderClient(this);
        longitude = findViewById(R.id.longe);
        largitude = findViewById(R.id.larg);
        ville = findViewById(R.id.ville);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }


        });
    }

    private void getLocation() {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            requisteLoca();
        }else
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},200);
        }
    }

    @SuppressLint("MissingPermission")
    private void requisteLoca() {
        LocationManager manger =  (LocationManager) getSystemService(LOCATION_SERVICE);
        manger.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5, (LocationListener) this);
        Location lock = manger.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(lock!=null){
            double  lat =lock.getLatitude();
            double  log =lock.getLongitude();
            Geocoder geocoder  =new Geocoder(this,Locale.getDefault());
            try {
                List<Address> adressList = geocoder.getFromLocation(lat,log,1);
                ville.setText(adressList.get(0).getCountryName());
                largitude.setText(adressList.get(0).getLatitude()+"");
                longitude.setText(adressList.get(0).getLongitude()+"");
            } catch (IOException e) {
                System.out.println(e.getMessage()+"");
                   Log.d("tag",e.getMessage()+"ffffffffffffff");
            }
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==200 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            getLocation();
        }
        else{
            longitude.setText("permission deny");
            largitude.setText("permission deny");
            ville.setText("permission deny");
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }








}