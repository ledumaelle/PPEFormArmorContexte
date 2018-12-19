/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modele.Client;
import modele.GestionSql;
import modele.Session;
import modele.lesBoutons;

/**
 * FXML Controller class
 *
 * @author AngeliqueLeRoux
 */
public class FXMLGestionInscriptionController implements Initializable
{
    @FXML
    private ComboBox<Client> cmb_ChoixCandidat;
    //@FXML
    //Button btnInscription;
    @FXML
    private TableView<Session> tableSessionsAutorisees;
    
    @FXML
    private TableColumn<Session, String> colonneFormations;
    
    @FXML
    private TableColumn<Session, String> colonneNiveau;
    
    @FXML
    private TableColumn<Session, String> colonneType;
    
    @FXML
    private TableColumn<Session, String> colonneDateD;
    
    @FXML
    private TableColumn<Session, String> colonneNbPlaces;
    
    @FXML
    private TableColumn<Session, String> colonneNbInscrits;
    
    @FXML 
    private TableColumn<Session, Button> colonneInscription;
    
    private Stage dialogStage;
    
    private boolean okClick = false;
    
    private Button boutonSession;
    
    private Stage secondaryStage;
    
    private lesBoutons unBouton;
    
    private ObservableList<lesBoutons> CollectionBoutons = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        ObservableList<Client> lesClients = GestionSql.getLesClients();
        cmb_ChoixCandidat.setItems(lesClients);  
    } 
    
    @FXML
    public void handleChoixMatricule()
    {
        if (cmb_ChoixCandidat.getValue() != null)
        {                        
            // Initialise le TableView tableSessionsAutorisees
            colonneFormations.setCellValueFactory(new PropertyValueFactory<Session, String>("formationNom"));
            colonneNiveau.setCellValueFactory(new PropertyValueFactory<Session, String>("niveau"));
            colonneType.setCellValueFactory(new PropertyValueFactory<Session, String>("nature"));
            colonneDateD.setCellValueFactory(new PropertyValueFactory<Session, String>("dateModifier"));
            colonneNbPlaces.setCellValueFactory(new PropertyValueFactory<Session, String>("nb_places"));
            colonneNbInscrits.setCellValueFactory(new PropertyValueFactory<Session, String>("nb_inscrits"));
            colonneInscription.setCellValueFactory(new PropertyValueFactory<Session, Button>("leBouton"));

            ObservableList<Session> lesSessions = FXCollections.observableArrayList();
            lesSessions = GestionSql.getLesSessions(cmb_ChoixCandidat.getValue().getId());
            
            for(int i=0; i<lesSessions.size(); i++)
            {
                boutonSession = new Button("Inscription");
                boutonSession.setOnAction(new HandleInscription());
                unBouton = new lesBoutons(lesSessions.get(i), boutonSession); 
                lesSessions.get(i).setMonBouton(unBouton);
                
                CollectionBoutons.add(unBouton);
            }
            
            tableSessionsAutorisees.setItems(lesSessions);
            
            // Pour redimensionner les colonnes automatiquement
            tableSessionsAutorisees.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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
 
    
    public class HandleInscription implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {       
            Button b = (Button)event.getSource();
            int i = 0;
            while(!(b.equals(CollectionBoutons.get(i).getMonButton())))
            {
                i = i + 1;
            }
            Session maSession = CollectionBoutons.get(i).getMaSession();
            MainApp.setMonClientSelectionne(cmb_ChoixCandidat.getValue());
            MainApp.setMaSessionSelectionnee(maSession);
            if(MainApp.afficheConfirmationDialog())
            {
                ObservableList<Session> lesSessions = FXCollections.observableArrayList();
                lesSessions = GestionSql.getLesSessions(cmb_ChoixCandidat.getValue().getId());

                for(i=0; i<lesSessions.size(); i++)
                {
                    boutonSession = new Button("Inscription");
                    boutonSession.setOnAction(new HandleInscription());
                    unBouton = new lesBoutons(lesSessions.get(i), boutonSession); 
                    lesSessions.get(i).setMonBouton(unBouton);

                    CollectionBoutons.add(unBouton);
                }
                
                tableSessionsAutorisees.setItems(lesSessions);
            }
        }
        
    }
}
