package com.fewwind.mydesign.net.api;

import com.fewwind.mydesign.net.User;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by fewwind on 2016/4/1.
 */
public interface MyApiEndpointInterface {
    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter
    @GET("/users/{username}")
    Call<User> getUser(@Path("username") String username);
    @GET("/group/{id}/users")
    Call<List<User>> groupList(@Path("id") int groupId, @Query("sort") String sort);
    @POST("/users/new")
    Call<User> createUser(@Body User user);
}