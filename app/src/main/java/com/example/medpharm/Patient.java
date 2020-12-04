package com.example.medpharm;

public class Patient {
    private String nomPatient,prenPatient;
    private int nmrPatient;
    private double taille,surfcorp,poids;

    public Patient() {
    }

    public Patient(String nomPatient, String prenPatient, int nmrPatient, double taille, double surfcorp, double poids) {
        this.nomPatient = nomPatient;
        this.prenPatient = prenPatient;
        this.nmrPatient = nmrPatient;
        this.taille = taille;
        this.surfcorp = surfcorp;
        this.poids = poids;
    }

    public String getNomPatient() {
        return nomPatient;
    }

    public void setNomPatient(String nomPatient) {
        this.nomPatient = nomPatient;
    }

    public String getPrenPatient() {
        return prenPatient;
    }

    public void setPrenPatient(String prenPatient) {
        this.prenPatient = prenPatient;
    }

    public int getNmrPatient() {
        return nmrPatient;
    }

    public void setNmrPatient(int nmrPatient) {
        this.nmrPatient = nmrPatient;
    }

    public double getTaille() {
        return taille;
    }

    public void setTaille(double taille) {
        this.taille = taille;
    }

    public double getSurfcorp() {
        return surfcorp;
    }

    public void setSurfcorp(double surfcorp) {
        this.surfcorp = surfcorp;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }
}
