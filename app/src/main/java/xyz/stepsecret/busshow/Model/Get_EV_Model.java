package xyz.stepsecret.busshow.Model;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Mylove on 3/1/2559.
 */
public class Get_EV_Model {

    @SerializedName("data")
    private String[][] dataEV ;

    public String[][] dataEV() {
        return dataEV;
    }

}
