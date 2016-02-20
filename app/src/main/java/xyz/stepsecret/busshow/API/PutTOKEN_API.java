package xyz.stepsecret.busshow.API;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.PUT;
import xyz.stepsecret.busshow.Model.PutTOKEN_Model;

/**
 * Created by stepsecret on 15/2/2559.
 */
public interface PutTOKEN_API {

    @FormUrlEncoded
    @PUT("/task_manager/v1/saveToken")
    public void PutTOKEN(@Header("Authorization") String API,
                     @Field("token") String token,
                     Callback<PutTOKEN_Model> response);
}
