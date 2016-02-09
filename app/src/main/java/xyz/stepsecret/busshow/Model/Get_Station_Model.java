package xyz.stepsecret.busshow.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by XEUSLAB on 30/1/2559.
 */
public class Get_Station_Model {

    @SerializedName("data")
    private String[][] dataST ;

    public String[][] dataST() {
        return dataST;
    }

}
