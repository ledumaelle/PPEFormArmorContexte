/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.swing.filechooser.FileSystemView;
import modele.Client;
import modele.GestionSql;
import modele.Session;

/**
 * FXML Controller class
 *
 * @author Pc-Thomas
 */
public class FenFXML_GestionSessionController implements Initializable {

    @FXML
    private TableColumn<Session, String> intitule;
    @FXML
    private TableColumn<Session, String> niveau;
    @FXML
    private TableColumn<Session, String> nature;
    @FXML
    private TableColumn<Session, String> date;
    @FXML
    private TableView<Session> tableSessionsAutorisees;
    ObservableList<Session>  LesSessions = GestionSql.getLesSessions();    
    private Session SessionSelect;
    private Stage dialogStage;
    private boolean okClick = false;
    
    /// DONNEE POUR PDF ///
    FileSystemView fsv = FileSystemView.getFileSystemView();
    File f = fsv.getDefaultDirectory();
    private String chemin="";
    private static com.itextpdf.text.Font catFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 18,
            com.itextpdf.text.Font.BOLD);
    private static com.itextpdf.text.Font redFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12,
            com.itextpdf.text.Font.NORMAL, BaseColor.RED);
    private static com.itextpdf.text.Font subFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 16,
            com.itextpdf.text.Font.BOLD);
    private static com.itextpdf.text.Font smallBold = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12,
            com.itextpdf.text.Font.BOLD);
    /// FIN DONNE PDF ///
        
        
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
            for (Session UneSession : LesSessions) // Initalisation des objets clients qui sont inscrit dans l'objet Session !
            {
                UneSession.setLesClientsInscrits(GestionSql.getClientsInscritParSession(UneSession.getId()));
            }

            // Initialise le TableView tableSessionsAutorisees
            intitule.setCellValueFactory(new PropertyValueFactory<Session, String>("LibFormation"));
            niveau.setCellValueFactory(new PropertyValueFactory<Session, String>("Niveau"));
            nature.setCellValueFactory(new PropertyValueFactory<Session, String>("Nature"));
            date.setCellValueFactory(new PropertyValueFactory<Session, String>("DateModifier"));
            // Pour redimensionner les colonnes automatiquement
            tableSessionsAutorisees.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            
            tableSessionsAutorisees.setItems(LesSessions);
    }
    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClick()
    {
        return okClick;
    }   
    
    @FXML
    public void handleEditePDF() throws IOException
    {
        SessionSelect = tableSessionsAutorisees.getSelectionModel().getSelectedItem();        
        try 
        {
            chemin = f+"/"+SessionSelect.getFormationNom()+"Le"+SessionSelect.getDateModifierPDF()+".pdf"; 
            Document document = new Document(PageSize.A4.rotate(),20,20,50,0);
            PdfWriter.getInstance(document, new FileOutputStream(chemin));
            document.open();

            document.add(new Phrase("Session : "+SessionSelect.getLibFormation()+ "\t\t"+"Niveau: "+SessionSelect.getNiveau()+"\n"));
            document.add(new Phrase("Débute le : "+SessionSelect.getDateModifier()+ "\t\t"+"Type de formation : "+SessionSelect.getLaFormation().getType_form()+"\n"));
            document.add(new Phrase("Nombre de places : "+SessionSelect.getNb_places()+ "\t\t"+"Durée : "+SessionSelect.getLaFormation().getDuree() +"h"+"\n\n\n"));
            addMetaData(document);
            addTitlePage(document);
            addContent(document);
            document.close();
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        try 
        {
           Desktop desktop = Desktop.getDesktop();
            File f = new File(chemin);
            if (f.exists()) 
            {
                desktop.open(f);
            }
        }
        catch (Exception e) 
        {
            System.out.println(e.toString());
        }
    }
    
private void addMetaData(Document document) {
        document.addTitle("My first PDF");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Lars Vogel");
        document.addCreator("Lars Vogel");
    }

    private void addTitlePage(Document document)
            throws DocumentException {
       
    }

    private void addContent(Document document) throws DocumentException {
        Anchor anchor = new Anchor();

        // Second parameter is the number of the chapter
        //Chapter catPart = new Chapter(0);

        //Paragraph subPara = new Paragraph();
        //Section subCatPart = catPart.addSection(subPara);
        // add a table
        createTable(document);

        // now add all this to the document
        //document.add(catPart);

    }

    private void createTable(Document document)
        throws BadElementException, DocumentException {
        PdfPTable table = new PdfPTable(4);
        
        PdfPCell c1 = new PdfPCell(new Phrase("Nom"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("Adresse"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("Email"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("Signature"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        
        
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        for (Client UnClient : SessionSelect.getLesClientsInscrits()) 
        {
            table.addCell(UnClient.getNom());
            table.addCell(UnClient.getAdresse() + " "+ UnClient.getCp() + " "+ UnClient.getVille());
            table.addCell(UnClient.getEmail());
            table.addCell("\n ");
        }
        
        for (int i = 0; i < 3; i++) {
            table.addCell(" ");
            table.addCell(" ");
            table.addCell(" ");
            table.addCell("\n ");
            
        }
        
        table.setWidthPercentage(100);
        table.setSpacingBefore(0f);
        table.setSpacingAfter(0f);
        document.add(table);
    }
    
    
}
