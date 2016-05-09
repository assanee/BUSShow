package xyz.stepsecret.busshow.API;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import xyz.stepsecret.busshow.Model.Get_CumulativeF_Model;

/**
 * Created by Mylove on 3/1/2559.
 */
public interface Get_CumulativeF {

    @GET("/task_manager/v1/CumulativeF")
    public void Get_CumulativeF_API(
            @Query("roundflowF") String roundflowF,
            @Query("EVflowF") String EVflowF,
            Callback<Get_CumulativeF_Model> response);
}
