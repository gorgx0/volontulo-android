package com.stxnext.volontulo.model;

import android.support.annotation.DrawableRes;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Offer {
    private String offerName;
    private String offerPlace;
    private long offerStartTime;
    private long offerEndTime;
    private int offerImageResource;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd MM yyyy, HH:mm");

    public static Offer mockOffer(String name, String place, DateTime startTime, DateTime endTime, @DrawableRes int imageResource) {
        final Offer result = new Offer();
        result.offerName = name;
        result.offerPlace = place;
        result.offerStartTime = startTime.getMillis();
        result.offerEndTime = endTime.getMillis();
        result.offerImageResource = imageResource;
        return result;
    }

    public String getOfferName() {
        return offerName;
    }

    public String getOfferPlace() {
        return offerPlace;
    }

    public String getFormattedStartTime() {
        if (offerStartTime > 0) {
            return new DateTime(offerStartTime).toString(DATE_FORMAT);
        }
        return "";
    }

    public String getFormattedEndTime() {
        if (offerEndTime > 0) {
            return new DateTime(offerEndTime).toString(DATE_FORMAT);
        }
        return "";
    }

    public int getOfferImageResource() {
        return offerImageResource;
    }
}
