package xyz.stepsecret.busshow.API;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import xyz.stepsecret.busshow.Model.Get_CumulativeFS_Model;

/**
 * Created by Mylove on 3/1/2559.
 */
public interface Get_CumulativeFS {

    @GET("/task_manager/v1/CumulativeFS")
    public void Get_CumulativeFS_API(
            @Query("roundflowF") String roundflowF,
            @Query("EVflowF") String EVflowF,
            @Query("roundflowS") String roundflowS,
            @Query("EVflowS") String EVflowS,
            Callback<Get_CumulativeFS_Model> response);

}
