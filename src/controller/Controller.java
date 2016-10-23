package controller;

import domain.Candidat;
import domain.Sectie;

public class Controller {
	private repository.RepoCandidati _repoC;
	private repository.RepoSectii _repoS;
	private domain.CandidateValidator cValidator;
	private domain.SectieValidator sValidator;
	public Controller(repository.RepoCandidati repoC,repository.RepoSectii repoS){
		this._repoC = repoC;
		this._repoS = repoS;
	}
	
	public void adaugaCandidat(Integer id, String nume, String tel,
			String adresa , Integer varsta){
		//Validare candidat
		
		domain.Candidat cand = new domain.Candidat(id,nume,tel,adresa,varsta);
		try {
			cValidator.validateEntity(cand);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
		this._repoC.add(cand);
	}

	public void adaugaSectie(Integer id, String nume, Integer nrLoc){
		//Validare sectie
		domain.Sectie sectie = new domain.Sectie(id,nume,nrLoc);
		try {
			sValidator.validateEntity(sectie);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
		this._repoS.add(sectie);
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
	
	
}
