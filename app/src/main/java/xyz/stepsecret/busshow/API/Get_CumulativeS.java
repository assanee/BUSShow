package xyz.stepsecret.busshow.API;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import xyz.stepsecret.busshow.Model.Get_CumulativeS_Model;

/**
 * Created by Mylove on 3/1/2559.
 */
public interface Get_CumulativeS {

    @GET("/task_manager/v1/CumulativeS")
    public void Get_CumulativeS_API(
            @Query("roundflowS") String roundflowS,
            @Query("EVflowS") String EVflowS,
            Callback<Get_CumulativeS_Model> response);
}
