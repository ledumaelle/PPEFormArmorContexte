/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Rabelais
 */
public class FXMLNoeudRacineController implements Initializable
{   
    private Stage primaryStage;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    
    
    @FXML
    public boolean handleGestionInscription()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/vue/FXMLGestionInscription.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Gestion des inscriptions");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            FXMLGestionInscriptionController controleur = loader.getController();
            controleur.setDialogStage(dialogStage);
            dialogStage.showAndWait();
            return controleur.isOkClick();
            
        }

        catch(IOException ioe)
        {
            System.out.println("ERREUR chargement boite dialogue FXMLNoeudRacineController.handleGestionInscription() :" + ioe.getMessage());
            return false;          
        }
    }  
    
    @FXML
    public boolean handleGestionRentabilite()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/vue/FXMLGestionRentabilite.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Gestion de la rentabilité");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            FXMLGestionRentabiliteController controleur = loader.getController();
            controleur.setDialogStage(dialogStage);
            dialogStage.showAndWait();
            return controleur.isOkClick();
        }

        catch(IOException ioe)
        {
            System.out.println("ERREUR chargement boite dialogue FXMLNoeudRacineController.handleGestionRentabilite() :" + ioe.getMessage());
            return false;
        }
    }  
    
    @FXML
    public boolean handleGestionSession()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/vue/FenFXML_GestionSession.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Gestion des sessions");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            FenFXML_GestionSessionController controleur = loader.getController();
            controleur.setDialogStage(dialogStage);
            dialogStage.showAndWait();
            return controleur.isOkClick();
        }

        catch(IOException ioe)
        {
            System.out.println("ERREUR chargement boite dialogue FXMLNoeudRacineController.handleGestionSession() :" + ioe.getMessage());
            return false;
        }
    }  
}
