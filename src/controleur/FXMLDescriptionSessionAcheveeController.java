/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
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
    ArrayList<Client> lesInscrits;
    ArrayList<Client> lesAbscents;
    ArrayList<String> nomAbscents;
    
    @FXML
    Label lblLibelle,lblDate, lblNbPlaces, lblNbInscrits, lblNombreAbsents, lblTauxRemplissage, lblCoutRevient, lblVente, lblMarge;
    @FXML
    private TableView<Client> tabParticipants;
    @FXML
    private TableColumn<Client, String> colEmploye;
    @FXML
    private TableColumn<Client, Double> colHoraire;
    
    Session maSession;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        double NbPlaces,coutDeRevient,CAFormation,Marge;
        int NbAbsents,NbInscrits; 
        String annee,mois,jour;
        NbPlaces = MainApp.getMaSessionSelectionnee().getNb_places();
        
        lblLibelle.setText(MainApp.getMaSessionSelectionnee().getFormationNomNiveau());
        lblDate.setText(MainApp.getMaSessionSelectionnee().getDateModifier());
        lblNbPlaces.setText(String.valueOf(MainApp.getMaSessionSelectionnee().getNb_places()));
           
        NbInscrits = MainApp.getMaSessionSelectionnee().getNb_inscrits();
        lblNbInscrits.setText(String.valueOf(MainApp.getMaSessionSelectionnee().getNb_inscrits()));
        NbAbsents = GestionSql.getNbAbsentsParSession(MainApp.getMaSessionSelectionnee().getId());  
        lblNombreAbsents.setText(String.valueOf(NbAbsents));
        
        double Taux = ((NbInscrits-NbAbsents)/NbPlaces) * 100;        
        lblTauxRemplissage.setText(String.valueOf(Math.round(Taux))+"%");
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
        
        colEmploye.setCellValueFactory(new PropertyValueFactory<Client, String>("NomComplet"));
        colHoraire.setCellValueFactory(new PropertyValueFactory<Client, Double>("TauxHoraire"));
        
        lesInscrits = GestionSql.getClientsInscritParSession(MainApp.getMaSessionSelectionnee().getId());
        lesAbscents= GestionSql.getClientsAbscentsParSession(MainApp.getMaSessionSelectionnee().getId());
        nomAbscents = new  ArrayList<String>();
        
        for (int i=0;i<lesAbscents.size();i++)
        {
            nomAbscents.add(lesAbscents.get(i).getNomComplet());
        }
        
        ObservableList<Client> lesClients= FXCollections.observableArrayList(lesInscrits);        
        
        // Pour redimensionner les colonnes automatiquement
        tabParticipants.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);       
        
        //COULEUR            
        tabParticipants.setItems(lesClients); 
        
        colEmploye.setCellFactory(new Callback<TableColumn<Client, String>, TableCell<Client, String>>() 
        {   
            @Override 
            public TableCell<Client, String> call(TableColumn<Client, String> tc) 
            { 
                return new ClientTableCell(); 
            } 
        });
    }
    
    public class ClientTableCell extends TableCell<Client, String> 
    { 
  
        @Override 
        protected void updateItem(String item, boolean empty) 
        { 
            super.updateItem(item, empty); 
            setGraphic(null);
            setText(null);
            if (!empty && item != null && nomAbscents.contains(item)) 
            { 
                setStyle("-fx-background-color: salmon;");
                setText(item);
            } 
            else
            {
                setText(item);
            }
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
