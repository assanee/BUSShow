package xyz.stepsecret.busshow.API;

/**
 * Created by Assanee on 8/7/2558.
 */
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

import retrofit.Callback;
import xyz.stepsecret.busshow.Model.SignIN_Model;


public interface SignIN_API {

    @FormUrlEncoded
    @POST("/task_manager/v1/login")
    public void SignIN(@Field("email") String email, @Field("password") String password, Callback<SignIN_Model> response);


}
