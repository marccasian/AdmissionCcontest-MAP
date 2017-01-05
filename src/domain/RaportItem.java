package domain;

import java.io.Serializable;

public class RaportItem implements Serializable{
	
	private Sectie _sectie;
	private Integer	_nrLocOcupate;
	private String _numeSectie;
	private static final long serialVersionUID = 1L;
	
	
	public RaportItem( Sectie s, Integer nrLoc) {
		this._sectie = s;
		this._nrLocOcupate = nrLoc;
		this._numeSectie = s.getNume();
	}
	
	public RaportItem(){
		this(new Sectie(), 0);
	}	
	
	public Sectie getSectie(){
		return this._sectie;
	}
	
	public Integer getNrLocOcupate(){
		return this._nrLocOcupate;
	}
	
	public String getNumeSectie(){
		return this._numeSectie;
	}
	
	public void setSectie(Sectie s){
		this._sectie = s;
	}
	
	public void setNrLocOcupate(Integer nrLoc){
		this._nrLocOcupate = nrLoc;
	}
	
	public String toString(){
		return this._sectie+"|"+this._nrLocOcupate;
	}
	
	public static int compareThem(RaportItem a, RaportItem b) {
	    return a._nrLocOcupate.compareTo(b._nrLocOcupate);
	}
}