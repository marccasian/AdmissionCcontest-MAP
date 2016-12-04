package domain;

import java.io.Serializable;

public class Inscriere implements HasId<Integer>,Serializable{
	private Candidat _candidat;
	private Sectie _sectie;
	private String numec;
	
	public String isNumec(){
		return numec;
	}
	
	public String isNumes(){
		return numes;
	}
	
	public String getNumec() {
		return numec;
	}

	public String getNumes() {
		return numes;
	}

	private String numes;
	private Integer _id;

	private static final long serialVersionUID = 1L;
	
	public Inscriere(Integer id, Candidat c, Sectie s){
		this._sectie = s;
		this._candidat = c;	
		this._id = id;
		this.numec = this._candidat.getNume();
		this.numes = this._sectie.getNume();
	}
	
	public Inscriere(){
		this(0,null, null);
	}
	
	
	public Candidat get_candidat() {
		return _candidat;
	}

	public void set_candidat(Candidat _candidat) {
		this._candidat = _candidat;
	}

	public Sectie get_sectie() {
		return _sectie;
	}

	public void set_sectie(Sectie _sectie) {
		this._sectie = _sectie;
	}
	
	@Override
	public Integer getId() {
		return _id;
	}

	@Override
	public void setId(Integer _id) {
		this._id = _id;
	}

	
	public String toString(){
		return this._candidat.getNume()+" s-a inscris la "+this._sectie.getNume();
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inscriere other = (Inscriere) obj;
		if (_candidat == null) {
			if (other._candidat != null)
				return false;
		} else if (!(this.getId().equals(other.getId())))
			return false;
		return true;
	}

}
