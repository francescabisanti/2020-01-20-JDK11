package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Adiacenz;
import it.polito.tdp.artsmia.model.Artist;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;

    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	txtResult.clear();
    	String ruolo= this.boxRuolo.getValue();
    	txtResult.appendText("Calcola artisti connessi+\n");
    	List<Adiacenz> result= model.getDao().getAdiacenze(model.getIdMap(), ruolo);
    	Collections.sort(result);
    	for(Adiacenz a : result) {
    		this.txtResult.appendText(a.toString()+"\n");
    	}
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Calcola percorso");
    	String artistaS= this.txtArtista.getText();
    	int aId;
    	try {
    		aId= Integer.parseInt(artistaS);
    		
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Inserisci un valido id");
    		return;
    	}
    	Artist a= this.model.getIdMap().get(aId);
    	List <Artist> result=this.model.trovaPercorso(a);
    	for(Artist aa: result) {
    		this.txtResult.appendText(aa.toString()+"\n");
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Crea grafo \n");
    	String ruolo= this.boxRuolo.getValue();
    	if(ruolo==null) {
    		this.txtResult.appendText("SELEZIONA UN RUOLO!");
    		return;
    	}
    	this.model.creaGrafo(ruolo);
    	this.txtResult.appendText("#VERTICI: "+this.model.getNVertici()+"\n");
    	this.txtResult.appendText("#ARCHI: "+this.model.getNArchi()+"\n");
    	
    }

    public void setModel(Model model) {
    	this.model = model;
    	this.boxRuolo.getItems().addAll(model.listRuoli());
    }

    
    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }
}
