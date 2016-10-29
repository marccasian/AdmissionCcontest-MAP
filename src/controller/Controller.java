package controller;

import domain.Candidat;
import domain.Sectie;
import domain.ValidatorException;

public class Controller {
	private repository.RepoCandidatiSerializat _repoC;
	private repository.RepoSectiiFile _repoS;
	private domain.CandidateValidator cValidator;
	private domain.SectieValidator sValidator;
	public Controller(){
		_repoC = new repository.RepoCandidatiSerializat();
		_repoS = new repository.RepoSectiiFile("Sectii.txt");
		
		//this._repoC = repoC;
		//this._repoS = repoS;
		
//		_repoC.add(new domain.Candidat(1,"C1","0746217312","a1",23));
//		_repoC.add(new domain.Candidat(2,"C2","0746217313","a2",24));
//		_repoC.add(new domain.Candidat(3,"C3","0746217314","a2",25));
//		_repoS.add(new domain.Sectie(1,"S1",12));
//		_repoS.add(new domain.Sectie(2,"S2",13));
//		_repoS.add(new domain.Sectie(3,"S3",14));
		
		cValidator = new domain.CandidateValidator();
		sValidator = new domain.SectieValidator();
	}
	
	public void adaugaCandidat(Integer id, String nume, String tel,
			String adresa , Integer varsta) throws ValidatorException, sun.security.validator.ValidatorException{
		//Validare candidat
		
		domain.Candidat cand = new domain.Candidat(id,nume,tel,adresa,varsta);
		cValidator.validateEntity(cand);
		this._repoC.add(cand);
	}

	public void adaugaSectie(Integer id, String nume, Integer nrLoc) throws sun.security.validator.ValidatorException, ValidatorException{
		//Validare sectie
		domain.Sectie sect = new domain.Sectie(id,nume,nrLoc);
		sValidator.validateEntity(sect);
		this._repoS.add(sect);
	}
	
	public Iterable<Candidat> getCandidati(){
		return this._repoC.getAll();
	}
	
	public int getNrCandidati(){
		return this._repoC.getElemsNr();
	}
	
	public int getNrSectii(){
		return this._repoS.getElemsNr();
	}
	
	public Iterable<Sectie> getSectii(){
		return this._repoS.getAll();
	}
	
	public domain.Candidat stergeCandidat(int id){
		//System.out.println("Controller id:"+id);
		int pos = this._repoC.getPosId(id);
		//System.out.println("Controller pos:"+pos);
		Candidat c = this._repoC.delete(pos);
		return c;
	}
	
	public domain.Sectie stergeSectie(int id){
		int pos = this._repoS.getPosId(id); 
		Sectie s = this._repoS.delete(pos);
		return s;
	}
	
	public domain.Candidat getCandidat(int id){ 
		Candidat c = this._repoC.findOne(id); 
		return c;
	}
	
	public domain.Sectie getSectie(int id){ 
		Sectie s = this._repoS.findOne(id); 
		return s;
	}

	public void saveRepo() {
		// TODO Auto-generated method stub
		_repoC.serializeCandidat();
		_repoS.saveData();
	}
	
	
}
