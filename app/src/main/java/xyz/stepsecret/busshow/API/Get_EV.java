package xyz.stepsecret.busshow.API;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import xyz.stepsecret.busshow.Model.Get_EV_Model;

/**
 * Created by Mylove on 3/1/2559.
 */
public interface Get_EV {
    @GET("/task_manager/v1/Get_EV")
    public void Get_EV_API(Callback<Get_EV_Model> response);
}
