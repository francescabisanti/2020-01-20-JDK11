package it.polito.tdp.artsmia.model;

public class Artist {
	
	int artistID;
	String name;
	public Artist(int artistID, String name) {
		super();
		this.artistID = artistID;
		this.name = name;
	}
	public int getArtistID() {
		return artistID;
	}
	public void setArtistID(int artistID) {
		this.artistID = artistID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return artistID+"" ;
	}
	
	

}
