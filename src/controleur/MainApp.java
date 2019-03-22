/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modele.Client;
import modele.GestionSql;
import modele.Session;

/**
 *
 * @author Philippe
 */
public class MainApp extends Application
{
    private Stage primaryStage;
    private BorderPane rootLayout;
    
    // Pour conserver la session sélectionnée dans le TableView de la fenêtre inscription
    private static Session maSessionSelectionne;
    // Pour conserver le client sélectionné dans le ComboBox de la fenêtre inscription
    private static Client monClientSelectionne;
    
    @Override
    public void start(Stage primaryStage) 
    {
        this.primaryStage=primaryStage;
        this.primaryStage.setTitle("Gestion FormArmor");
        
        try
        {
            //chargement du layout racine à partir du fichier fxml file
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/vue/FXMLNoeudRacine.fxml"));
            rootLayout=(BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();            
        }
        catch (IOException e)
        {
            //Exception qui intervient si le fichier fxml file n'a pas pu être chargé
            e.printStackTrace();
        }
        
        AfficherAccueil();
    }
    
    public void creationFenConfirm()
    {
        
    }
    
    // Getter et Setter pour l'item selectionné dans le tableView des sessions (fenetre Inscription)
    public static void setMaSessionSelectionnee(Session maSession)
    {
        maSessionSelectionne = maSession;
    }
    public static Session getMaSessionSelectionnee()
    {
        return maSessionSelectionne;
    }
    // Getter et Setter pour l'item selectionné dans le ComboBox des clients (fenetre Inscription)
    public static void setMonClientSelectionne(Client monClient)
    {
        monClientSelectionne = monClient;
    }
    public static Client getMonClientSelectionne()
    {
        return monClientSelectionne;
    }
    
    public void AfficherAccueil()
    {
        try
        {
            // Charge le fichier fxml (FenFXML_Coordo) et le place au centre du layout principal
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/vue/FXMLAccueil.fxml"));
            AnchorPane overviewPage = (AnchorPane) loader.load();
            rootLayout.setCenter(overviewPage);
        }

        catch (IOException e)
        {
            //Exception qui intervient si le fichier fxml n'a pas pu être chargé
            e.printStackTrace();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
    
    public static boolean afficheConfirmationDialog()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/vue/FenFXML_ConfirmationInscription.fxml"));
            AnchorPane page = (AnchorPane)loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Confirmation de l'inscription à la session de formation");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Place l'étudiant dans le controleur
            FenFXML_ConfirmationInscriptionControleur controleur = loader.getController();
            
            // Affiche la boite de dialogue et attend que l'utilisateur la ferme
            dialogStage.showAndWait();
            
            return controleur.isOkClick();
        }
        catch(IOException ioe)
        {
            System.out.println("ERREUR chargement boite dialogue:" + ioe.getMessage());
    
            return false;
        }
    }
    
}
