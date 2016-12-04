package controller;

import java.util.ArrayList;
import java.util.List;
import domain.Candidat;
import domain.Inscriere;
import domain.Sectie;
import domain.ValidatorException;
import utils.Observable;
import utils.Observer;

public class ControllerInscrieri implements Observable<Inscriere>{
	private repository.RepoInscrieriSerializat _repoI;
	
	protected List <Observer<Inscriere>> observers = new ArrayList<Observer<Inscriere>>();
	
	public ControllerInscrieri(){
		_repoI = new repository.RepoInscrieriSerializat();
	}
	
	public void adaugaInscriere(Integer id, Candidat c, Sectie s) throws ValidatorException, sun.security.validator.ValidatorException{
		
		domain.Inscriere insc = new domain.Inscriere(id,c,s);
		this._repoI.add(insc);
	}
	
	public Iterable<domain.Inscriere> getInscrieri(){
		return this._repoI.getAll();
	}
	
	public int getNrInscrieri(){
		return this._repoI.getElemsNr();
	}
	
	public domain.Inscriere stergeInscriere(int id){
		int pos = this._repoI.getPosId(id);
		domain.Inscriere i = this._repoI.delete(pos);
		return i;
	}
	
	public domain.Inscriere getInscriere(int id){ 
		domain.Inscriere c = this._repoI.findOne(id); 
		return c;
	}
	
	@SuppressWarnings("static-access")
	public void saveRepo() {
		_repoI.serializeInscrieri((ArrayList<domain.Inscriere>) _repoI.getAll());
	}
	
	@Override
	public void addObserver(Observer<Inscriere> o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer<Inscriere> o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers(){
		for(Observer<Inscriere> o : observers){
			o.update(this);
		}
	}
	
	public domain.Inscriere updateI(domain.Inscriere t) throws ValidatorException, sun.security.validator.ValidatorException
	{
		domain.Inscriere i = _repoI.update(t);
		if (i==null)
		{
			notifyObservers();
		}
		saveRepo();
		return i;
	}
	
	public domain.Inscriere saveI(domain.Inscriere t) throws ValidatorException, sun.security.validator.ValidatorException
	{
		domain.Inscriere c= null;
		c = _repoI.save(t);
		if (c==null)
		{
			notifyObservers();
		}
		saveRepo();
		return c;
	}
	
	public domain.Inscriere deleteI(domain.Inscriere t) throws ValidatorException, sun.security.validator.ValidatorException
	{
		domain.Inscriere c= null;
		int id = t.getId();
		int pos = _repoI.getPosId(id);
		c = _repoI.delete(pos);
		if (c!=null)
		{
			notifyObservers();
		}
		saveRepo();
		return c;
	}
	
}
