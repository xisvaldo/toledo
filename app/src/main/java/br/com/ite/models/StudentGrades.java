package br.com.ite.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by leonardo.borges on 02/02/2017.
 */
public class StudentGrades {

    @SerializedName("ALUNO")
    private String studentId;

    @SerializedName("ANO")
    private String year;

    @SerializedName("DISCIPLINA")
    private String subject;

    @SerializedName("P1")
    private String testOne;

    @SerializedName("P2")
    private String testTwo;

    @SerializedName("P3")
    private String testThree;

    @SerializedName("P4")
    private String testFour;

    @SerializedName("SUB")
    private String substituteOne;

    @SerializedName("SUB2")
    private String substituteTwo;

    @SerializedName("EX")
    private String exam;

    @SerializedName("FALTAS")
    private String failures;

    /** For Post Graduation only **/
    @SerializedName("CONCEITO_FINAL")
    private String finalConcept;

    @SerializedName("PERCENTUAL_PRESENCA")
    private String presencePercentage;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTestOne() {
        return testOne;
    }

    public void setTestOne(String testOne) {
        this.testOne = testOne;
    }

    public String getTestTwo() {
        return testTwo;
    }

    public void setTestTwo(String testTwo) {
        this.testTwo = testTwo;
    }

    public String getTestThree() {
        return testThree;
    }

    public void setTestThree(String testThree) {
        this.testThree = testThree;
    }

    public String getTestFour() {
        return testFour;
    }

    public void setTestFour(String testFour) {
        this.testFour = testFour;
    }

    public String getSubstituteOne() {
        return substituteOne;
    }

    public void setSubstituteOne(String substituteOne) {
        this.substituteOne = substituteOne;
    }

    public String getSubstituteTwo() {
        return substituteTwo;
    }

    public void setSubstituteTwo(String substituteTwo) {
        this.substituteTwo = substituteTwo;
    }

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public String getFailures() {
        return failures;
    }

    public void setFailures(String failures) {
        this.failures = failures;
    }

    public String getFinalConcept() {
        return finalConcept;
    }

    public void setFinalConcept(String finalConcept) {
        this.finalConcept = finalConcept;
    }

    public String getPresencePercentage() {
        return presencePercentage;
    }

    public void setPresencePercentage(String presencePercentage) {
        this.presencePercentage = presencePercentage;
    }
}
