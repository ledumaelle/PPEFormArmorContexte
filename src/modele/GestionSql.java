/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javafx.collections.FXCollections;
import sql.GestionBdd;

import javafx.collections.ObservableList;

public class GestionSql
{
    private static ObservableList<Session> lesSessions = FXCollections.observableArrayList();
    //Requete permettant de retourner l'ensemble des clients
    public static ObservableList<Client> getLesClients()
    {
        Connection conn;
        Statement stmt1;
        Client monClient;
        ObservableList<Client> lesClients = FXCollections.observableArrayList();
        try
        {
            // On prévoit 2 connexions à la base
            stmt1 = GestionBdd.connexionBdd(GestionBdd.TYPE_MYSQL, "formarmor","localhost", "root","");
            
            // Liste des clients qui "ont un plan de formation"
            String req = "select distinct c.id, c.statut_id,taux_horaire,type, nom, password, adresse, cp, ville, email, nbhcpta, nbhbur from client c, plan_formation p , statut s "
            + "where c.id = p.client_id and s.id=c.id order by c.id";
            ResultSet rs = GestionBdd.envoiRequeteLMD(stmt1,req);
            while (rs.next())
            {
                Statut UnStatut= new Statut(rs.getInt("statut_id"), rs.getInt("taux_horaire"),rs.getString("type"));
                monClient = new Client(rs.getInt("id"),UnStatut, rs.getInt("nbhcpta"), rs.getInt("nbhbur"), rs.getString("nom"), rs.getString("password"), rs.getString("adresse"), rs.getString("cp"), rs.getString("ville"), rs.getString("email"));
                lesClients.add(monClient);
            }
        }
        catch (SQLException se)
        {
            System.out.println("Erreur SQL requete getLesClients : " + se.getMessage());
        }
        return lesClients;
    }
    
    //Requête permettant de  retourner les sessions autorisées pour le client sélectionné
    public static ObservableList<Session> getLesSessions(int client_id)
    {
        Connection conn;
        Statement stmt1;
        
        try
        {
            lesSessions.removeAll(lesSessions);
            String url = "jdbc:mysql://localhost/formarmor";
            conn = DriverManager.getConnection(url,"root","");
            CallableStatement cStmt = conn.prepareCall("{call sessions_autorisees('" + client_id + "')}");
            cStmt.executeUpdate();
            conn.close();
            // On prévoit 2 connexions à la base
            stmt1 = GestionBdd.connexionBdd(GestionBdd.TYPE_MYSQL, "formarmor","localhost", "root","");
            
            // Sélection des sessions autorisées pour le client choisi
            String req = "select * from formation_autorisee";
            ResultSet rs = GestionBdd.envoiRequeteLMD(stmt1,req);
            //System.out.println(req);
            while (rs.next())
            {
                Formation maFormation = new Formation(0, 0, 0, 0, rs.getString("formation_libelle"), rs.getString("formation_niveau"), rs.getString("type_formation"), null);
                Session maSession = new Session(rs.getInt("session_id"), rs.getString("formation_libelle"), rs.getDate("date_debut"), rs.getInt("nb_places"), rs.getInt("nb_inscrits"), maFormation);
                lesSessions.add(maSession);
            }
        }
        catch (SQLException se)
        {
            System.out.println("Erreur SQL requete getLesSessions : " + se.getMessage());
        }
        return lesSessions;
    }
    
    public static ObservableList<Session> getLesSessions() // Return uniquement les sessions prochaines!!! date début > a aujourd.
    {
        Connection conn;
        Statement stmt1;
        Session maSession;
        ObservableList<Session> lesSessions = FXCollections.observableArrayList();
        try
        {
            // On prévoit 2 connexions à la base
            stmt1 = GestionBdd.connexionBdd(GestionBdd.TYPE_MYSQL, "formarmor","localhost", "root","");
            
            // Sélection de toute les sessions
            String req = "Select s.id 'id_session', formation_id,date_debut,nb_places,nb_inscrits,close,f.id 'id_formation',libelle,niveau,type_form,description,diplomante,duree,coutrevient from session_formation s ,formation f where f.id = s.formation_id and date_debut > NOW() order by date_debut";
            ResultSet rs = GestionBdd.envoiRequeteLMD(stmt1,req);
            while (rs.next())
            {
                Formation laformation = new Formation(rs.getInt("id_formation"), rs.getInt("duree"), rs.getInt("diplomante"), rs.getDouble("coutrevient"), rs.getString("libelle"),rs.getString("niveau"),rs.getString("type_form"),rs.getString("description"));
                maSession = new Session(rs.getInt("id_session"), rs.getString("libelle"), rs.getDate("date_debut"), rs.getInt("nb_places"), rs.getInt("nb_inscrits"),laformation);
                lesSessions.add(maSession);
            }
        }
        catch (SQLException se)
        {
            System.out.println("Erreur SQL requete getLesSessions : " + se.getMessage());
        }
        return lesSessions;
    }
    
    //Requête permettant l'insertion de l'inscription dans la table inscription et
    //la mise à jour de la table session_formation (+1 inscrit) et
    //la mise à jour de la table plan_formation (effectue passe à 1)
    public static void insereInscription(int matricule, int session_formation_id)
    {
        Statement stmt1;
        
        GregorianCalendar dateJour = new GregorianCalendar();
        String ddate = dateJour.get(GregorianCalendar.YEAR) + "-" + (dateJour.get(GregorianCalendar.MONTH) + 1) + "-" + dateJour.get(GregorianCalendar.DATE);
        // Insertion dans la table inscription
        String req = "Insert into inscription(client_id, session_formation_id, date_inscription, presence) values (" + matricule;
        req += ", " + session_formation_id + ",'" + ddate + "', 1)";
        // M.A.J de la table session_formation (un inscrit de plus)
        String req2 = "Update session_formation set nb_inscrits = nb_inscrits +1 Where id = " + session_formation_id;
        // Récupération du numéro de la session concernée
        String req3 = "Select formation_id from session_formation where id = " + session_formation_id;
        stmt1 = GestionBdd.connexionBdd(GestionBdd.TYPE_MYSQL, "formarmor", "localhost", "root", "");
        ResultSet rs = GestionBdd.envoiRequeteLMD(stmt1, req3);
        int numForm=0;
        try
        {
            rs.first();
            numForm = rs.getInt(1);
        }
        catch(Exception e)
        {
            System.out.println("Erreur requete3 " + e.getMessage());
        }
        // M.A.J de la table plan_formation (effectue passe à 1 pour le client et la session concernés)
        String req4 = "Update plan_formation set effectue = 1 Where client_id = " + matricule;
        req4 += " And formation_id = " + numForm;
        int nb1 = GestionBdd.envoiRequeteLID(stmt1, req);
        int nb2 = GestionBdd.envoiRequeteLID(stmt1, req2);
        int nb3 = GestionBdd.envoiRequeteLID(stmt1, req4);
    }
    
    public static ArrayList getClientsInscritParSession(int id_Session)
    {
        ArrayList<Client> LesClients = new ArrayList();
        
        Connection conn;
        Statement stmt1;
        try
        {
            // On prévoit 2 connexions à la base
            stmt1 = GestionBdd.connexionBdd(GestionBdd.TYPE_MYSQL, "formarmor","localhost", "root","");
            
            // Sélection de toute les sessions
            String req = "Select * from inscription,client,statut where client.id = inscription.client_id and STATUT.id=CLIENT_id and session_formation_id = '"+id_Session+"'";
            ResultSet rs = GestionBdd.envoiRequeteLMD(stmt1,req);
            while (rs.next())
            {
                Statut UnStatut= new Statut(rs.getInt("client.statut_id"), rs.getInt("taux_horaire"),rs.getString("type"));
                Client UnClient = new Client(rs.getInt("client_id"), UnStatut, rs.getInt("nbhcpta"), rs.getInt("nbhbur"), rs.getString("nom"), rs.getString("password"), rs.getString("adresse"), rs.getString("cp"), rs.getString("ville"), rs.getString("email"));
                LesClients.add(UnClient);
            }
        }
        catch (SQLException se)
        {
            System.out.println("Erreur SQL requete getLesSessions : " + se.getMessage());
        }
        
        return LesClients;
    }
    
    public static double getCoutRevientFormation(int pFormationId)
    {
        Connection conn;
        Statement stmt;
        Double CoutRevient=0.0;
        try
        {
            stmt = GestionBdd.connexionBdd(GestionBdd.TYPE_MYSQL, "formarmor","localhost", "root","");
            // Sélection de toute les sessions
            String req = "SELECT SUM(duree*coutrevient)AS "+"'CoutRevient'"+" from session_formation,formation where session_formation.formation_id = formation.id and formation_id="+ pFormationId;
           ResultSet rs = GestionBdd.envoiRequeteLMD(stmt,req);
            while (rs.next())
            {
                CoutRevient = rs.getDouble("CoutRevient"); 
            }
        }
        catch (SQLException se)
        {
            System.out.println("Erreur SQL requete getCoutRevientFormation : " + se.getMessage());
        }
        
        return CoutRevient;
    }
    
    public static double getVenteFormation(int pFormationId)
    {
        Connection conn;
        Statement stmt;
        Double Marge=0.0;
        try
        {
            stmt = GestionBdd.connexionBdd(GestionBdd.TYPE_MYSQL, "formarmor","localhost", "root","");
            // Sélection de toute les sessions
            String req = "Select SUM(duree*taux_horaire) AS "+"'Vente'"+" from inscription,client,statut,formation,session_formation where inscription.client_id=client.id and client.statut_id=statut.id and formation.id=session_formation.formation_id and session_formation.id = inscription.session_formation_id and session_formation_id="+pFormationId; 
            ResultSet rs = GestionBdd.envoiRequeteLMD(stmt,req);
            while (rs.next())
            {
                Marge = rs.getDouble("Vente"); 
            }
        }
        catch (SQLException se)
        {
            System.out.println("Erreur SQL requete getVenteFormation : " + se.getMessage());
        }
        
        return Marge;
    }
    
    public static int getNbAbsentsParSession(int pSessionId)
    {
        Connection conn;
        Statement stmt;
        int NbAbsents=0;
        try
        {
            stmt = GestionBdd.connexionBdd(GestionBdd.TYPE_MYSQL, "formarmor","localhost", "root","");
            // Sélection de toute les sessions
            String req = "SELECT COUNT(*) AS "+"'NbAbsents'"+" FROM INSCRIPTION WHERE presence = 0 and session_formation_id=" +pSessionId;
            ResultSet rs = GestionBdd.envoiRequeteLMD(stmt,req);
            while (rs.next())
            {
                NbAbsents = rs.getInt("NbAbsents"); 
            }
        }
        catch (SQLException se)
        {
            System.out.println("Erreur SQL requete getNbAbsentsParSession : " + se.getMessage());
        }
        
        return NbAbsents;
    }
    
    
}
