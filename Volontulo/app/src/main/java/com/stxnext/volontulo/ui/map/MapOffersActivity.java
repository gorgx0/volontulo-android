package com.stxnext.volontulo.ui.map;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseActivity;
import com.stxnext.volontulo.model.Ofer;
import com.stxnext.volontulo.ui.offers.OfferDetailsActivity;

public class MapOffersActivity extends VolontuloBaseActivity implements OnMapReadyCallback {
    public static final int REQUEST_LOCATION_PERMISSION = 100;
    public static final String[] PERMISSIONS = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private SupportMapFragment mapFragment;
    private MockOffersMapAdapter mapAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nodrawer);
        init(R.string.map_offers);
        mapFragment = SupportMapFragment.newInstance(
            new GoogleMapOptions()
                .rotateGesturesEnabled(false)
                .tiltGesturesEnabled(false)
                .zoomControlsEnabled(true)
        );
        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content, mapFragment)
                .commit();
        mapAdapter = new MockOffersMapAdapter(this);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        final LatLngBounds.Builder boundaryBuilder = new LatLngBounds.Builder();
        for (Ofer item : mapAdapter.getObjects()) {
            boundaryBuilder.include(item.getPlacePosition());
            googleMap.addMarker(new MarkerOptions()
                    .position(item.getPlacePosition())
                    .title(String.format("%s - %s", item.getName(), item.getPlaceName()))
                    .snippet(String.format("%s - %s", item.getFormattedStartTime(), item.getFormattedEndTime())));
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundaryBuilder.build(), 50));
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                startActivity(new Intent(MapOffersActivity.this, OfferDetailsActivity.class));
            }
        });
        enableMyLocation(googleMap);
    }

    private void enableMyLocation(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_LOCATION_PERMISSION);
            return;
        }
        googleMap.setMyLocationEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (canProcessPermissionResult(requestCode, grantResults)) {
            mapFragment.getMapAsync(this);
        }
    }

    private boolean canProcessPermissionResult(int requestCode, @NonNull int[] grantResults) {
        return requestCode == REQUEST_LOCATION_PERMISSION && hasPermissionResults(grantResults) && isPermissionGranted(grantResults);
    }

    private boolean hasPermissionResults(@NonNull int[] grantResults) {
        return grantResults.length > 0;
    }

    private boolean isPermissionGranted(@NonNull int[] grantResults) {
        return grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED;
    }
}
