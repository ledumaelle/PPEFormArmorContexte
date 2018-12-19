/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modele.Client;
import modele.GestionSql;
import modele.Session;

/**
 * FXML Controller class
 *
 * @author Rabelais
 */
public class FXMLDescriptionSessionAcheveeController implements Initializable
{
    private Stage dialogStage;
    private boolean okClick = false; 
    
    @FXML
    Label lblLibelle,lblDate, lblNbPlaces, lblNbInscrits, lblNombreAbsents, lblTauxRemplissage, lblCoutRevient, lblVente, lblMarge;
    @FXML
    private TableView<Client> tabParticipants;
    @FXML
    private TableColumn<Client, String> colEmploye;
    @FXML
    private TableColumn<Client, Integer> colHoraire;
    
    Session maSession;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        double NbPlaces,NbInscrits,coutDeRevient,CAFormation,Marge;
        int NbAbsents; 
        String annee,mois,jour;
        NbPlaces = MainApp.getMaSessionSelectionnee().getNb_places();
        NbInscrits = MainApp.getMaSessionSelectionnee().getNb_inscrits();
        lblLibelle.setText(MainApp.getMaSessionSelectionnee().getLibFormation());
        lblDate.setText(MainApp.getMaSessionSelectionnee().getDateModifier());
        lblNbPlaces.setText(String.valueOf(MainApp.getMaSessionSelectionnee().getNb_places()));
        lblNbInscrits.setText(String.valueOf(MainApp.getMaSessionSelectionnee().getNb_inscrits()));
        NbAbsents = GestionSql.getNbAbsentsParSession(MainApp.getMaSessionSelectionnee().getId());        
        double Taux = ((NbInscrits-NbAbsents)/NbPlaces) * 100;
        lblNombreAbsents.setText(String.valueOf(NbAbsents));
        lblTauxRemplissage.setText(String.valueOf(Taux)+"%");
        coutDeRevient = GestionSql.getCoutRevientFormation(MainApp.getMaSessionSelectionnee().getLaFormation().getId());
        lblCoutRevient.setText(String.valueOf(coutDeRevient)+"€");
        CAFormation = GestionSql.getVenteFormation(MainApp.getMaSessionSelectionnee().getId());
        lblVente.setText(String.valueOf(CAFormation)+"€");
        Marge = CAFormation-coutDeRevient ; 
        lblMarge.setText(String.valueOf(Marge)+"€");
        if(Marge > 0.0 )
        {
            //positif
            lblMarge.setStyle("-fx-text-fill: green;");
        }
        else
        {
            lblMarge.setStyle("-fx-text-fill: red;");
        }       
        
         // Initialise le TableView tableSessionsAutorisees
        colEmploye.setCellValueFactory(new PropertyValueFactory<Client, String>("Nom"));
        colHoraire.setCellValueFactory(new PropertyValueFactory<Client, Integer>("TauxHoraire"));
        
        ArrayList<Client> lesParticipants = GestionSql.getClientsInscritParSession(MainApp.getMaSessionSelectionnee().getId());
        ObservableList<Client> lesClients= FXCollections.observableArrayList(lesParticipants);
        
        // Pour redimensionner les colonnes automatiquement
        tabParticipants.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);            
        tabParticipants.setItems(lesClients); 
    }   
    
    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }
    
    public boolean isOkClick()
    {
        return okClick;
    }
    
}
