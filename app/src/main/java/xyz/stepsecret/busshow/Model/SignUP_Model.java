package xyz.stepsecret.busshow.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Assanee on 8/7/2558.
 */
public class SignUP_Model {

    @SerializedName("message")
    private String message ;

    @SerializedName("error")
    private Boolean error ;

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

}
