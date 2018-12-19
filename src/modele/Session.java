/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.Date;
import java.util.ArrayList;
import javafx.scene.control.Button;

/**
 *
 * @author Philippe
 */
public class Session
{
    private int id;
    private String libFormation;
    private Date date_debut;
    private int nb_places, nb_inscrits;
    private Formation LaFormation;
    private ArrayList<Client> LesClientsInscrits = new ArrayList();
    private lesBoutons monBouton;
    
    public Session(int id, String libFormation, Date date_debut, int nb_places, int nb_inscrits,Formation laFormation)
    {
        this.id = id;
        this.libFormation = libFormation;
        this.date_debut = date_debut;
        this.nb_places = nb_places;
        this.nb_inscrits = nb_inscrits;
        this.LaFormation = laFormation;
    }

    public Session(int id, String libFormation, Date date_debut, int nb_places, int nb_inscrits)
    {
        this.id = id;
        this.libFormation = libFormation;
        this.date_debut = date_debut;
        this.nb_places = nb_places;
        this.nb_inscrits = nb_inscrits;
    }

    public ArrayList<Client> getLesClientsInscrits() {
        return LesClientsInscrits;
    }

    public void setLesClientsInscrits(ArrayList<Client> LesClientsInscrits) {
        this.LesClientsInscrits = LesClientsInscrits;
    }
    
    
    public Formation getLaFormation() {
        return LaFormation;
    }
    
    public String getFormationNom()
    {
        return LaFormation.getLibelle(); 
    }
    
    public String getNiveau ()
    {
        return LaFormation.getNiveau();
    }

    public String getNature()
    {
        return LaFormation.getType_form();
    }
    public void setLaFormation(Formation LaFormation) {
        this.LaFormation = LaFormation;
    }
    
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }

    public String getLibFormation()
    {
        return libFormation;
    }
    public void setLibFormation(String libFormation)
    {
        this.libFormation = libFormation;
    }
    
    public String getDateModifierPDF()
    {
        String annee,mois,jour,UneDate;
        UneDate  = String.valueOf(date_debut);
        String[] tab = UneDate.split("-");
        annee=tab[0];
        mois=tab[1];
        jour=tab[2];
        String NouvelleDate = jour + "-"+mois+"-"+annee;
        return NouvelleDate;
    }

    public String getDateModifier()
    {
        String annee,mois,jour,UneDate;
        UneDate  = String.valueOf(date_debut);
        String[] tab = UneDate.split("-");
        annee=tab[0];
        mois=tab[1];
        jour=tab[2];
        String NouvelleDate = jour + "/"+mois+"/"+annee;
        return NouvelleDate;
    }
    
    public Date getDate_debut()
    {
        return date_debut;
    }
    
    public void setDate_debut(Date date_debut)
    {
        this.date_debut = date_debut;
    }

    public int getNb_places()
    {
        return nb_places;
    }
    public void setNb_places(int nb_places)
    {
        this.nb_places = nb_places;
    }

    public int getNb_inscrits()
    {
        return nb_inscrits;
    }
    public void setNb_inscrits(int nb_inscrits)
    {
        this.nb_inscrits = nb_inscrits;
    }
    
    
    public lesBoutons getMonBouton()
    {
        return monBouton;
    }

    public void setMonBouton(lesBoutons monBouton)
    {
        this.monBouton = monBouton;
    }
    
    public Button getLeBouton()
    {
        return monBouton.getMonButton();
    }
    
    @Override
    public String toString()
    {
        return String.valueOf(LaFormation.toString());
    } 
}
