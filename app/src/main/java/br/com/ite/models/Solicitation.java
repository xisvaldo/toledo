package br.com.ite.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by leonardo.borges on 20/02/2017.
 */
public class Solicitation implements Serializable {

    @SerializedName("Servico")
    private String service;

    @SerializedName("Descricao")
    private String description;

    @SerializedName("Valor")
    private String value;

    @SerializedName("Aluno")
    private String student;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }
}
