package com.uca.isi.axelor.api;

import com.google.gson.JsonObject;
import com.uca.isi.axelor.data.UserData;
import com.uca.isi.axelor.entities.Login;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @Headers({"Accept: application/json"})
    @POST("login.jsp")
    Call<ResponseBody> login(@Body Login user);

    @Headers({"Accept: application/json"})
    @POST("ws/rest/com.axelor.auth.db.User/search")
    Call<UserData> getCurrentUserInformation(@Body JsonObject object, @Header("Cookie") String sessionID);

    @GET("ws/rest/com.axelor.apps.base.db.Product")
    Call<ResponseBody> getProducts(@Header("Cookie") String sessionID);

    @GET("logout")
    Call<ResponseBody> logout(@Header("Cookie") String sessionID);

    @GET("ws/rest/com.axelor.auth.db.User/{id}/image/download")
    Call<ResponseBody> getImageUser(@Header("Cookie") String sessionID,
                                @Path("id") int pictureID,
                                @Query("parentModel") String parentModel,
                                @Query("image") boolean image);

    @GET("ws/rest/com.axelor.meta.db.MetaFile/{id}/content/download")
    Call<ResponseBody> getImage(@Header("Cookie") String sessionID,
                          @Path("id") int pictureID,
                          @Query("v") int version,
                          @Query("parentId") int parentId,
                          @Query("parentModel") String parentModel);
    /**
     * Web service Azure Machine Learning
     */
    @Headers({"Accept: application/json"})
    @POST("e8765711780241c39950eb440477ba5b/services/f49ca90394314815a2e0365e17f3d019/execute")
    Call<ResponseBody> getForecastInfo(@Body() JsonObject object,
                                       @Header("Authorization") String authorization,
                                       @Query("api-version") double apiVersion,
                                       @Query("details") boolean details);

}