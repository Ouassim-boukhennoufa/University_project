package com.example.medpharm;

public class Medicament {
    private String nomMedicament,nomLabo;
    private int presentation;
    private double concentrationinit,concentrationmin,concentrationmax,prix;
    public Medicament(){}

    public Medicament(String nomMedicament, String nomLabo, int presentation, double concentrationinit, double concentrationmin, double concentrationmax, double prix) {
        this.nomMedicament = nomMedicament;
        this.nomLabo = nomLabo;
        this.presentation = presentation;
        this.concentrationinit = concentrationinit;
        this.concentrationmin = concentrationmin;
        this.concentrationmax = concentrationmax;
        this.prix = prix;
    }

    public String getNomMedicament() {
        return nomMedicament;
    }

    public void setNomMedicament(String nomMedicament) {
        this.nomMedicament = nomMedicament;
    }

    public String getNomLabo() {
        return nomLabo;
    }

    public void setNomLabo(String nomLabo) {
        this.nomLabo = nomLabo;
    }

    public int getPresentation() {
        return presentation;
    }

    public void setPresentation(int presentation) {
        this.presentation = presentation;
    }

    public double getConcentrationinit() {
        return concentrationinit;
    }

    public void setConcentrationinit(double concentrationinit) {
        this.concentrationinit = concentrationinit;
    }

    public double getConcentrationmin() {
        return concentrationmin;
    }

    public void setConcentrationmin(double concentrationmin) {
        this.concentrationmin = concentrationmin;
    }

    public double getConcentrationmax() {
        return concentrationmax;
    }

    public void setConcentrationmax(double concentrationmax) {
        this.concentrationmax = concentrationmax;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}
