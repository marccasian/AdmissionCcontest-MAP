package domain;

public class Sectie {
	private Integer _id;
	private String _nume;
	private Integer	_nrLoc;
	
	public Sectie(Integer id, String nume, Integer nrLoc) {
		this._id = id;
		this._nume = nume;
		this._nrLoc = nrLoc;
	}
	
	public Sectie(){
		this(0, "", 0);
	}
	
	public Integer getId(){
		return this._id;
	}
	
	public String getNume(){
		return this._nume;
	}
	
	public Integer getNrLoc(){
		return this._nrLoc;
	}
	
	public void setId(Integer id){
		this._id = id;
	}
	
	public void setNume(String nume){
		this._nume = nume;
	}
	
	public void setNrLoc(Integer nrLoc){
		this._nrLoc = nrLoc;
	}
	
	public String toString(){
		return this._id+" | "+this._nume+" | "+this._nrLoc;
	}
	
	public static int compareThem(Sectie a, Sectie b) {
	    return a._id.compareTo(b._id);
	}
}
