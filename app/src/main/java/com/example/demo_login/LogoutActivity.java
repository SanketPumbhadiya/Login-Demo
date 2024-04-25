package com.example.demo_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.demo_login.model.ModelLatLongTime;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class LogoutActivity extends AppCompatActivity {
    Button btnLogout, btnSubmit;

    ListView listViewLatLong;
    SharedPreferenceClass sharedPreferences;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String previousLatitude, previousLongitude;
    LocationListener locationListener;
    LatLongTimeAdapter adapter;
    ArrayList<ModelLatLongTime> locationInfoList;
    LayoutInflater inflater;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        btnLogout = findViewById(R.id.btn_logout);
        btnSubmit = findViewById(R.id.btn_submit);
        listViewLatLong = findViewById(R.id.listView);
        imageView = findViewById(R.id.image);
        locationInfoList = new ArrayList<>();
        inflater = getLayoutInflater();

        sharedPreferences = new SharedPreferenceClass(this);
        btnSubmit.setOnClickListener(onClickFindCurrentLocation);
        btnLogout.setOnClickListener(onClickLogout);
        adapter = new LatLongTimeAdapter(this, locationInfoList, inflater);
        listViewLatLong.setAdapter(adapter);
        LocationListener();


        if (imageView != null) {
            Glide.with(this)
                    .load(R.drawable.cacheimage)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(imageView);
        }
    }

    View.OnClickListener onClickLogout = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sharedPreferences.clearLoginCredentials();
            Intent i = new Intent(LogoutActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    };

    View.OnClickListener onClickFindCurrentLocation = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            } else {
                getLocation();
            }
        }
    };

    private void LocationListener() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
//                Log.d("OnLocationChanged","OnLocationChanged function called");
                UpdateLocationInfo(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
            }
        };
    }
    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    private void UpdateLocationInfo(Location location) {
        if (location != null) {
            double lat = location.getLatitude();
            double lon = location.getLongitude();

            DecimalFormat decimalFormat = new DecimalFormat("##.######");
            lat = Double.parseDouble(decimalFormat.format(lat));
            lon = Double.parseDouble(decimalFormat.format(lon));

            String currentLatitude = String.valueOf(lat);
            String currentLongitude = String.valueOf(lon);

            if (!isLocationAlreadyAdded(currentLatitude, currentLongitude)) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                sdf.setTimeZone(TimeZone.getDefault());
                String time = sdf.format(new Date(location.getTime()));

                ModelLatLongTime model = new ModelLatLongTime(lat, lon, time);

                locationInfoList.add(model);
                previousLatitude = currentLatitude;
                previousLongitude = currentLongitude;
            }
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isLocationAlreadyAdded(String currentLatitude, String currentLongitude) {
        for (ModelLatLongTime item : locationInfoList) {
            if (currentLatitude.equals(String.valueOf(item.getLatitude())) && currentLongitude.equals(String.valueOf(item.getLongitude()))) {
                return true;
            }
        }
        return false;
    }

    @SuppressLint("SetTextI18n")
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(getApplicationContext(), "Location PermissionGranted", Toast.LENGTH_SHORT).show();
                getLocation();
            } else {
                Toast.makeText(getApplicationContext(), "Location Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void locationsFunctionsTest() {
//        double bearing = location.bearingTo(location);
//            float accuracy = location.getAccuracy();//this object true location in get location
//            double altitude = location.getAltitude();
//            double Speed = location.getSpeed();//location at that time in mps speed return
//
//            Date time = new Date(location.getTime());//return long value convert simple date format
//            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//            sdf.setTimeZone(TimeZone.getDefault());
//            sdf.format(time);
//            double describeContents = location.describeContents();
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                Log.d("getBearingAccuracyDegrees: ", String.valueOf(location.getBearingAccuracyDegrees()));
//            }
//            Log.d("getBearing: ", String.valueOf(location.getBearing()));//at that time of this location in degrees return bearing.
//            Log.d("Time: ", String.valueOf(time));
//            Log.d("Altitude: ", String.valueOf(altitude));
//            Log.d("describeContents: ", String.valueOf(describeContents));
//            Log.d("GetExtras: ", String.valueOf(location.getExtras()));//Returns an optional bundle of additional information associated with this location.
//            Log.d("getProvider: ", String.valueOf(location.getProvider()));//if provider set this return
//
//            Location location1 = new Location("");
//            location1.setLatitude(22.296924);
//            location1.setLongitude(70.758347);
//
//            Location location2 = new Location("");
//            location2.setLatitude(22.295782);
//            location2.setLongitude(70.762139);
//
//            double distance = location1.distanceTo(location2);//location 1 to location 2 distance
//            latitude = String.valueOf(lat);
//            longitude = String.valueOf(lon);
//            tvLongitudeLatitude.setText("Latitude : " + latitude + " \nLongitude : " + longitude);
//
//            Geocoder geocoder = new Geocoder(LogoutActivity.this);
//            try {
//                List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
//                if (addresses != null && !addresses.isEmpty()) {
//                    country = addresses.get(0).getCountryName();
//                    String city = addresses.get(0).getLocality();
//                    String PostalCode = addresses.get(0).getPostalCode();
//                    tvLongitudeLatitude.append("\nCountry: " + country);
//                    tvLongitudeLatitude.append("\nCity: " + city);
//                    tvLongitudeLatitude.append("\nPostalCode: " + PostalCode);
//                    tvLongitudeLatitude.append("\nBearing: " + bearing);
//                    tvLongitudeLatitude.append("\nAccuracy: " + accuracy);
//                    tvLongitudeLatitude.append("\nDistance: " + distance);
//                    tvLongitudeLatitude.append("\nSpeed: " + Speed);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
    }
}