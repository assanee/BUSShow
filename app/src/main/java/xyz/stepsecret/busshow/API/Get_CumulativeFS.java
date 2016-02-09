package xyz.stepsecret.busshow.API;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import xyz.stepsecret.busshow.Model.Get_CumulativeFS_Model;

/**
 * Created by Mylove on 3/1/2559.
 */
public interface Get_CumulativeFS {

    @GET("/task_manager/v2")
    public void Get_CumulativeFS_API(
            @Query("type") String type,
            @Query("roundflowR") String roundflowR,
            @Query("EVflowR") String EVflowR,
            @Query("roundflowY") String roundflowY,
            @Query("EVflowY") String EVflowY,
            Callback<Get_CumulativeFS_Model> response);

}
