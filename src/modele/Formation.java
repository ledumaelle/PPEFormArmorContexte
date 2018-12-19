/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

/**
 *
 * @author Pc-Thomas
 */
public class Formation {
    private int id,duree,diplomante;
    private double coutrevient;
    private String libelle,niveau,type_form,description;

    public Formation(int id, int duree, int diplomante, double coutrevient, String libelle, String niveau, String type_form, String description) {
        this.id = id;
        this.duree = duree;
        this.diplomante = diplomante;
        this.coutrevient = coutrevient;
        this.libelle = libelle;
        this.niveau = niveau;
        this.type_form = type_form;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getDiplomante() {
        return diplomante;
    }

    public void setDiplomante(int diplomante) {
        this.diplomante = diplomante;
    }

    public double getCoutrevient() {
        return coutrevient;
    }

    public void setCoutrevient(double coutrevient) {
        this.coutrevient = coutrevient;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getType_form() {
        return type_form;
    }

    public void setType_form(String type_form) {
        this.type_form = type_form;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString()
    {
        return String.valueOf(libelle);
    }
    
    
}
