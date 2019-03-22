/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

public class Client
{
    private int id, nbhcpta, nbhbur;
    private Statut UnStatut; 
    private String nom, prenom, password, adresse, cp, ville, email, telephone;
    
    public Client()
    {
    }
    
    public Client(String nom, String prenom)
    {
        this.nom = nom;
        this.prenom =prenom;
    }

    public Client(int id, Statut UnStatut, int nbhcpta, int nbhbur,String nom, String prenom, String password, String adresse, String cp, String ville, String email, String telephone) 
    {
        this.id = id;
        this.UnStatut = UnStatut;
        this.nbhcpta = nbhcpta;
        this.nbhbur = nbhbur;               
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.adresse = adresse;
        this.cp = cp;
        this.ville = ville;
        this.email = email;
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbhcpta() {
        return nbhcpta;
    }

    public void setNbhcpta(int nbhcpta) {
        this.nbhcpta = nbhcpta;
    }

    public int getNbhbur() {
        return nbhbur;
    }

    public void setNbhbur(int nbhbur) {
        this.nbhbur = nbhbur;
    }

    public Statut getUnStatut() {
        return UnStatut;
    }

    public void setUnStatut(Statut UnStatut) {
        this.UnStatut = UnStatut;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public String getNomComplet() {
        return nom + " " + prenom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
     public Double getTauxHoraire() {
        return UnStatut.getTauxHoraire();
    }
    
    @Override
    public String toString()
    {
        return nom + " "+ prenom;
    }
}
