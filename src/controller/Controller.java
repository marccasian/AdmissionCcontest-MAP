package controller;

import java.util.ArrayList;

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
		
		cValidator = new domain.CandidateValidator();
		sValidator = new domain.SectieValidator();
	}
	
	public void adaugaCandidat(Integer id, String nume, String tel,
			String adresa , Integer varsta) throws ValidatorException, sun.security.validator.ValidatorException{
		
		domain.Candidat cand = new domain.Candidat(id,nume,tel,adresa,varsta);
		cValidator.validateEntity(cand);
		this._repoC.add(cand);
	}

	public void adaugaSectie(Integer id, String nume, Integer nrLoc) throws sun.security.validator.ValidatorException, ValidatorException{
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
		int pos = this._repoC.getPosId(id);
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

	@SuppressWarnings("static-access")
	public void saveRepo() {
		_repoC.serializeCandidat((ArrayList<Candidat>) _repoC.getAll());
		_repoS.saveData();
	}
	
	
}
