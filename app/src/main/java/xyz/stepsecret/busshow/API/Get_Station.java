package xyz.stepsecret.busshow.API;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import xyz.stepsecret.busshow.Model.Get_Station_Model;

/**
 * Created by XEUSLAB on 30/1/2559.
 */
public interface Get_Station {

    @GET("/task_manager/v1/Get_ST")
    public void Get_Station_API( Callback<Get_Station_Model> response);

}
