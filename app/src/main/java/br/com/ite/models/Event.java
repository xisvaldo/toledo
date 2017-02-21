package br.com.ite.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by leonardo.borges on 14/02/2017.
 */
public class Event implements Serializable {

    @SerializedName("ID")
    private long ID;

    @SerializedName("Descricao")
    private String description;

    @SerializedName("Data_Inicio")
    private String startDate;

    @SerializedName("Data_Fim")
    private String endDate;


    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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
}
