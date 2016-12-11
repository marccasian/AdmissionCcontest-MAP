package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import domain.Sectie;
import domain.ValidatorException;

import utils.Observable;
import utils.Observer;

public class ControllerSectie implements Observable<Sectie>{
	private repository.RepoSectiiXML _repoS;
	private domain.SectieValidator sValidator;
	protected List <Observer<Sectie>> observers = new ArrayList<Observer<Sectie>>();
	
    Predicate<Sectie> moreThan100 = s->{return s.getNrLoc()>=100;};
    Predicate<Sectie> startWithS = s->s.getNume().startsWith("S");
	
	public ControllerSectie(){
		_repoS = new repository.RepoSectiiXML("Sectii.xml");
		
		sValidator = new domain.SectieValidator();
	}
	
	public void adaugaSectie(Integer id, String nume, Integer nrLoc) throws sun.security.validator.ValidatorException, ValidatorException{
		domain.Sectie sect = new domain.Sectie(id,nume,nrLoc);
		sValidator.validateEntity(sect);
		this._repoS.add(sect);
	}
	
	public int getNrSectii(){
		return this._repoS.getElemsNr();
	}
	
	public Iterable<Sectie> getSectii(){
		return this._repoS.getAll();
	}
	
	public domain.Sectie stergeSectie(int id){
		int pos = this._repoS.getPosId(id); 
		Sectie s = this._repoS.delete(pos);
		return s;
	}
	
	public domain.Sectie getSectie(int id){ 
		Sectie s = this._repoS.findOne(id); 
		return s;
	}

	public void saveRepo() {
		_repoS.saveData();
	}
	
	public static <E> List<E> filtrare(List<E> list, Predicate<E> prd){
		List<E> filtrata = list
				.stream()
			    .filter(prd)
			    .collect(Collectors.toList());
		//Collections.sort(filtrata,(f1,f2)->-(int)(f1.getGreutate()-f2.getGreutate()));
		return filtrata;
	}
	
	public List<Sectie> filterSectii100(){
		List<Sectie> filt = filtrare((List<Sectie>)getSectii(), moreThan100);
		Collections.sort(filt,(f1,f2)->(int)(f1.getNrLoc()-f2.getNrLoc()));
		return filt;
	}
	
	public List<Sectie> filterSectiiS(){
		List<Sectie> filt = filtrare((List<Sectie>)getSectii(), startWithS);
		Collections.sort(filt,(f1,f2)->(f1.getNume().compareTo(f2.getNume())));
		return filt;
	}	
	
	@Override
	public void addObserver(Observer<Sectie> o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer<Sectie> o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers(){
		for(Observer<Sectie> o : observers){
			o.update(this);
		}
	}
	
	public Sectie updateS(Sectie t) throws ValidatorException, sun.security.validator.ValidatorException
	{
		sValidator.validateEntity(t);
		Sectie s = _repoS.update(t);
		if (s==null)
		{
			notifyObservers();
		}
		saveRepo();
		return s;
	}
	
	public Sectie saveS(Sectie t) throws ValidatorException, sun.security.validator.ValidatorException
	{
		sValidator.validateEntity(t);
		Sectie c= null;
		c = _repoS.save(t);
		if (c==null)
		{
			notifyObservers();
		}
		saveRepo();
		return c;
	}
	
	public Sectie deleteS(Sectie t) throws ValidatorException, sun.security.validator.ValidatorException
	{
		sValidator.validateEntity(t);
		Sectie c= null;
		int id = t.getId();
		int pos = _repoS.getPosId(id);
		c = _repoS.delete(pos);
		if (c!=null)
		{
			notifyObservers();
		}
		saveRepo();
		return c;
	}
}
