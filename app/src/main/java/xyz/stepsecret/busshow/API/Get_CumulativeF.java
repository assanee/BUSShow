package xyz.stepsecret.busshow.API;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import xyz.stepsecret.busshow.Model.Get_CumulativeF_Model;

/**
 * Created by Mylove on 3/1/2559.
 */
public interface Get_CumulativeF {

    @GET("/task_manager/v2")
    public void Get_CumulativeF_API(
            @Query("type") String type,
            @Query("roundflowR") String roundflowR,
            @Query("EVflowR") String EVflowR,
            Callback<Get_CumulativeF_Model> response);
}
