package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	ArtsmiaDAO dao;
	SimpleWeightedGraph <Artist, DefaultWeightedEdge> grafo;
	Map <Integer, Artist> idMap;
	List <Artist> best;
	public Model() {
		dao= new ArtsmiaDAO();	
		idMap= new HashMap <Integer, Artist>();
		
	}
	
	public List<String> listRuoli() {
		return dao.listRuoli();
	}
	
	public void creaGrafo(String ruolo) {
		grafo= new SimpleWeightedGraph <Artist, DefaultWeightedEdge> (DefaultWeightedEdge.class);
		dao.listArtisti(idMap);
		Graphs.addAllVertices(this.grafo, dao.getVertici(idMap, ruolo));
		for(Adiacenz a: dao.getAdiacenze(idMap, ruolo)) {
			Graphs.addEdgeWithVertices(this.grafo, a.getA1(), a.getA2(),a.getPeso());
		}
	}
	
	public int getNVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}

	public ArtsmiaDAO getDao() {
		return dao;
	}

	public void setDao(ArtsmiaDAO dao) {
		this.dao = dao;
	}

	public SimpleWeightedGraph<Artist, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public void setGrafo(SimpleWeightedGraph<Artist, DefaultWeightedEdge> grafo) {
		this.grafo = grafo;
	}

	public Map<Integer, Artist> getIdMap() {
		return idMap;
	}

	public void setIdMap(Map<Integer, Artist> idMap) {
		this.idMap = idMap;
	}
	
	public List <Artist> trovaPercorso(Artist sorgente){
		this.best= new ArrayList <>();
		List <Artist> parziale= new ArrayList <>();
		parziale.add(sorgente);
		//Lancio la ricorsione
		cerca(parziale, -1); //so che quel peso non c è, mi serve per impostare
		return best;
		
	}
	
	public void cerca(List <Artist> parziale, int peso) {
		if(parziale.size()>best.size()) {
			best= new ArrayList<>(parziale);
			
		}

		Artist ultimo= parziale.get(parziale.size()-1);
		//mi prendo cosi l'ultimo cjhe ho inserito
		//prendo tutti i vicini
		List <Artist>vicini= Graphs.neighborListOf(grafo, ultimo);
		for(Artist vicino: vicini) {
			//controlla sempre che parziale non contenga vicino
			if(peso==-1 && !parziale.contains(vicino)) {
			parziale.add(vicino);
			//OCCHIO A PRENDERE SOLO I PESI UGUALI CHE TI IMPOSTO ORA
			cerca(parziale, (int) this.grafo.getEdgeWeight(this.grafo.getEdge(ultimo, vicino)));
			parziale.remove(vicino);
			}else {
				//ora ho un peso impostato e devo prendere solo quei
				// determinati vicini
				if( !parziale.contains(vicino)&&this.grafo.getEdgeWeight(this.grafo.getEdge(ultimo, vicino))==peso) {
					//LO IMPOSTO UGUALE AL PESO IMPOSTATO AL PRIMO PASSO
					parziale.add(vicino);
					cerca(parziale, peso);
					parziale.remove(vicino);
				}
			}
		}
	}
	
	
	
}
