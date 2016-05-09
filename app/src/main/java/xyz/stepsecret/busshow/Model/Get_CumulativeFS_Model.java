package xyz.stepsecret.busshow.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mylove on 3/1/2559.
 */
public class Get_CumulativeFS_Model {

    @SerializedName("TempcumuF")
    private Double TempcumuF ;

    @SerializedName("TempcumuS")
    private Double TempcumuS ;

    public Double TempcumuF() {
        return TempcumuF;
    }

    public Double TempcumuS() {
        return TempcumuS;
    }
}
