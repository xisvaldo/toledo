package br.com.ite.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by leonardo.borges on 26/01/2017.
 */
public class Student implements Serializable {

    @SerializedName("ALUNO")
    private String id;

    @SerializedName("Nome")
    private String fullName;

    @SerializedName("PrimeiroNome")
    private String firstName;

    @SerializedName("CURSO")
    private String course;

    @SerializedName("TURNO")
    private String turn;

    @SerializedName("ANO_INGRESSO")
    private String ingressYear;

    @SerializedName("CURRICULO")
    private String curriculum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public String getIngressYear() {
        return ingressYear;
    }

    public void setIngressYear(String ingressYear) {
        this.ingressYear = ingressYear;
    }

    public String getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }
}
