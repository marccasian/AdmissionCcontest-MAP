package domain;

public class Candidat {
	private Integer _id;
	private String _nume;
	private String _tel;
	private String _adresa;
	private Integer	_varsta;
	
	public Candidat(Integer id, String nume, String tel,
	String adresa , Integer varsta) {
		this._id = id;
		this._nume = nume;
		this._tel = tel;
		this._adresa = adresa;
		this._varsta = varsta;
	}
	
	public Candidat(){
		this(0, "", "","",0);
	}
	
	public Integer getId(){
		return this._id;
	}
	
	public String getNume(){
		return this._nume;
	}
	
	public Integer getVarsta(){
		return this._varsta;
	}
	
	public String getTel(){
		return this._tel;
	}
	
	public String getAdresa(){
		return this._adresa;
	}
	
	public void setId(Integer id){
		this._id = id;
	}
	
	public void setNume(String nume){
		this._nume = nume;
	}
	
	public void setTel(String tel){
		this._tel = tel;
	}
	
	public void setAdresa(String adresa){
		this._adresa = adresa;
	}
	
	public void setVarsta(Integer varsta){
		this._varsta = varsta;
	}
		
	public String toString(){
		return this._id+" | "+this._nume+" | "+this._varsta+" | "+this._tel+" | "+this._adresa;
	}
	
	public Boolean compareTo(Candidat c){
		return this.getId() > c.getId();
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Candidat other = (Candidat) obj;
		if (_id == null) {
			if (other._id != null)
				return false;
		} else if (!_id.equals(other._id))
			return false;
		return true;
	}
	
}
