package xyz.stepsecret.busshow.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Assanee on 8/7/2558.
 */
public class SignIN_Model {

    @SerializedName("error")
    private String error ;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("apiKey")
    private String apiKey;

    @SerializedName("createdAt")
    private String createdAt;


    public String getError() {
        return error;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
