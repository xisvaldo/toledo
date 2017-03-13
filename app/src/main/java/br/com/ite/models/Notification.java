package br.com.ite.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by leonardo.borges on 13/03/2017.
 */
public class Notification implements Serializable {

    @SerializedName("DtInicio") private String startDate;
    @SerializedName("Dtfim") private String endDate;
    @SerializedName("Mensagem") private String message;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
