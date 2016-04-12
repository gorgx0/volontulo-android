package com.stxnext.volontulo;

import com.stxnext.volontulo.api.Offer;
import com.stxnext.volontulo.api.UserProfile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface VolontuloApi {

    @GET("api/users_profiles/{id}.json")
    Call<UserProfile> getVolunteer(@Path("id") int id);

    @GET("api/users_profiles.json")
    Call<List<UserProfile>> listVolunteers();
//
    @GET("/api/offers/{id}.json")
    Call<Offer> getOffer(@Path("id") int id);
//
    @GET("/api/offers.json")
    Call<List<Offer>> listOffers();
//
//    @FormUrlEncoded
//    @POST("/rest-auth/login/")
//    Call<Volunteer> login(@Field("username") String username, @Field("password") String password);
//
//    @POST("/rest-auth/logout/")
//    Call<Void> logout(@Header("Authorization") String authorization);
}
