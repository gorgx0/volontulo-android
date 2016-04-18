package com.stxnext.volontulo.model;

import android.net.Uri;
import android.support.annotation.DrawableRes;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.parceler.Parcel;

import io.realm.OfferRealmProxy;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

@Parcel(implementations = { OfferRealmProxy.class },
            value = Parcel.Serialization.BEAN,
        analyze = { Ofer.class })
public class Ofer extends RealmObject {

    @Ignore
    public static String IMAGE_RESOURCE = "IMAGE-RESOURCE";

    private String name;
    private String place;
    private String description;
    private String timeRequirement;
    private String benefits;

    private double placeLongitude;
    private double placeLatitude;

    private long startTime;
    private long endTime;
    private boolean isUserJoined;

    @Ignore private int imageResource;
    private String imagePath;

    @Ignore private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormat.forPattern("dd-MM-yyyy, HH:mm");
    @Ignore private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd/MM/yy");

    public static Ofer mock(String name, String place, DateTime startTime, DateTime endTime, @DrawableRes int imageResource, boolean isJoined) {
        final Ofer result = new Ofer();
        result.name = name;
        result.place = place;
        result.startTime = startTime.getMillis();
        result.endTime = endTime.getMillis();
        result.imageResource = imageResource;
        result.isUserJoined = isJoined;
        return result;
    }

    public static Ofer mockPlace(String name, String place, LatLng position, DateTime startTime, DateTime endTime, @DrawableRes int imageResource, boolean isJoined) {
        final Ofer result = mock(name, place, startTime, endTime, imageResource, isJoined);
        result.placeLongitude = position.longitude;
        result.placeLatitude = position.latitude;
        return result;
    }

    public void setPlaceNameAndPosition(Place place) {
        final LatLng position = place.getLatLng();
        placeLongitude = position.longitude;
        placeLatitude = position.latitude;
        this.place = String.valueOf(place.getName());
    }

    public String getPlaceName() {
        return place;
    }

    public double getPlaceLongitude() {
        return placeLongitude;
    }

    public double getPlaceLatitude() {
        return placeLatitude;
    }

    public LatLng getPlacePosition() {
        return new LatLng(placeLatitude, placeLongitude);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTimeRequirement(String timeRequirement) {
        this.timeRequirement = timeRequirement;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getName() {
        return name;
    }

    public String getFormattedStartTime() {
        if (startTime > 0) {
            return new DateTime(startTime).toString(DATE_TIME_FORMAT);
        }
        return "";
    }

    public String getFormattedEndTime() {
        if (endTime > 0) {
            return new DateTime(endTime).toString(DATE_TIME_FORMAT);
        }
        return "";
    }

    public String getFormattedStartDay() {
        if (startTime > 0) {
            return new DateTime(startTime).toString(DATE_FORMAT);
        }
        return "";
    }

    public String getFormattedEndDay() {
        if (endTime > 0) {
            return new DateTime(endTime).toString(DATE_FORMAT);
        }
        return "";
    }

    @DrawableRes
    public int getImageResource() {
        return imageResource;
    }

    public boolean isUserJoined() {
        return isUserJoined;
    }

    public String getDescription() {
        return description;
    }

    public String getTimeRequirement() {
        return timeRequirement;
    }

    public String getBenefits() {
        return benefits;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(Uri path) {
        this.imagePath = path.toString();
    }
}
