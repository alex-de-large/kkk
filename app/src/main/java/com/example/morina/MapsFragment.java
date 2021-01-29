package com.example.morina;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.morina.api.App;
import com.example.morina.api.Data;
import com.example.morina.model.Car;
import com.example.morina.tools.PermissionTools;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment implements
    LocationListener,
    ActivityCompat.OnRequestPermissionsResultCallback,
    GoogleMap.OnMarkerClickListener {

    private final OnMapReadyCallback callback = googleMap -> {
        map = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        enableDeviceLocationLayer();
        showCars(googleMap);
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private void showCars(GoogleMap googleMap) {
        for (Car car : Data.get().getCars()) {
            Marker carMarker = googleMap.addMarker(new MarkerOptions().position(
                new LatLng(Double.parseDouble(car.getLat()), Double.parseDouble(car.getLon()))
            ).icon(BitmapDescriptorFactory.fromResource(R.drawable.car_yellow))
                .anchor(0.5f, 1));
        }
    }

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    private static final float ZOOM = 15;

    private GoogleMap map;
    private Location lastDeviceLocation;

    private boolean permissionDenied = false;

    LocationManager locationManager;

    private void enableDeviceLocationLayer() {
        if (ContextCompat.checkSelfPermission(
            getActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                getActivity(),
                new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                LOCATION_PERMISSION_REQUEST_CODE
            );
            return;
        }
        if (map != null) {
            locationManager = ((LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE));
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                MIN_TIME,
                MIN_DISTANCE,
                this
            );
            map.setMyLocationEnabled(true);
            map.getUiSettings().setCompassEnabled(false);
            map.getUiSettings().setMyLocationButtonEnabled(false);
            map.getUiSettings().setMapToolbarEnabled(false);

            map.setOnMarkerClickListener(this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (getActivity() == null) return;
        lastDeviceLocation = location;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM));
    }

    @Override
    public void onProviderEnabled(String provider) {
        locationManager.removeUpdates(this);
        enableDeviceLocationLayer();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(App.getContext(), "Geolocation is disabled, enable it in settings.", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(
            marker.getPosition(),
            ZOOM
        ));

//        for (Car car : markers) {
//            if (car.getMarker().getPosition().toString().equals(marker.getPosition().toString())) {
//                new BookBottomDialog(activity, map, car).show();
//                return true;
//            }
//        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (permissionDenied) {
            showMissingPermissionError();
            permissionDenied = false;
        }
        enableDeviceLocationLayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(
            getActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED) {
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(
        int requestCode,
        @NonNull String[] permissions,
        @NonNull int[] grantResults
    ) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }
        if (PermissionTools.isPermissionGranted(permissions,
            grantResults,
            Manifest.permission.ACCESS_FINE_LOCATION
        )) {
            enableDeviceLocationLayer();
        } else {
            permissionDenied = true;
        }
    }

    private void showMissingPermissionError() {
        PermissionTools.PermissionDeniedDialog
            .newInstance(true).show(getActivity().getSupportFragmentManager(), "dialog");
    }

}