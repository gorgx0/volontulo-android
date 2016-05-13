package com.stxnext.volontulo.ui.offers;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

class ThumbnailMap implements OnMapReadyCallback {
    private LatLng position;
    private CharSequence positionTitle;

    ThumbnailMap(LatLng marker, CharSequence markerTitle) {
        position = marker;
        positionTitle = markerTitle;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
        googleMap.addMarker(new MarkerOptions()
                .position(position)
                .title(String.valueOf(positionTitle)));
    }
}
