/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modele.Client;
import modele.GestionSql;
import modele.Session;


/**
 * FXML Controller class
 *
 * @author Rabelais
 */
public class FXMLGestionRentabiliteController implements Initializable
{    
    @FXML
    private TableView<Session> tabSessionsAchevees;
    @FXML
    private TableColumn<Session, String> colonneFormations;
    @FXML
    private TableColumn<Session, String> colonneNiveau;
    @FXML
    private TableColumn<Session, String> colonneType;
    @FXML
    private TableColumn<Session, Date> colonneDateD;
    @FXML
    private TableColumn<Session, Integer> colonneNbPlaces;
    @FXML
    private TableColumn<Session, Integer> colonneNbInscrits;
    
    private Stage primaryStage;
    private Stage dialogStage;
    private boolean okClick = false;   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // Initialise le TableView tabSessionsAutorisees
        colonneFormations.setCellValueFactory(new PropertyValueFactory<Session, String>("FormationNom"));
        colonneNiveau.setCellValueFactory(new PropertyValueFactory<Session, String>("Niveau"));
        colonneType.setCellValueFactory(new PropertyValueFactory<Session, String>("Nature"));
        colonneDateD.setCellValueFactory(new PropertyValueFactory<Session, Date>("dateModifier"));
        colonneNbPlaces.setCellValueFactory(new PropertyValueFactory<Session, Integer>("nb_places"));
        colonneNbInscrits.setCellValueFactory(new PropertyValueFactory<Session, Integer>("nb_inscrits"));
        
        // Pour redimensionner les colonnes automatiquement
        tabSessionsAchevees.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);            
        tabSessionsAchevees.setItems(GestionSql.getLesSessions()); 
    }  
    
    public boolean handleDescriptionSession()
    {  
        MainApp.setMaSessionSelectionnee((Session) tabSessionsAchevees.getSelectionModel().getSelectedItem());  
        Session UneSession = MainApp.getMaSessionSelectionnee();
        ArrayList<Client> lesClientsInscrits =  GestionSql.getClientsInscritParSession(UneSession.getId());
        UneSession.setLesClientsInscrits(lesClientsInscrits);
        MainApp.setMaSessionSelectionnee(UneSession);
        try
        {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/vue/FXMLDescriptionSessionAchevee.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Description de la session achevée");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            FXMLDescriptionSessionAcheveeController controleur = loader.getController();
            controleur.setDialogStage(dialogStage);
            dialogStage.showAndWait();
            return controleur.isOkClick();
        }

        catch(IOException ioe)
        {
            System.out.println("ERREUR chargement boite dialogue FXMLGestionRentabiliteController.handleDescriptionSession() :" + ioe.getMessage());
            return false;
        }
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
