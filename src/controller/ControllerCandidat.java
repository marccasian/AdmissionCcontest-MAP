package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import domain.Candidat;
import domain.ValidatorException;

import utils.Observable;
import utils.Observer;

public class ControllerCandidat implements Observable<Candidat>{
	private repository.RepoCandidatiXML _repoC;
	
	public repository.RepoCandidatiXML get_repoC() {
		return _repoC;
	}

	public void set_repoC(repository.RepoCandidatiXML _repoC) {
		this._repoC = _repoC;
	}

	private domain.CandidateValidator cValidator;
	
	public domain.CandidateValidator getcValidator() {
		return cValidator;
	}

	public void setcValidator(domain.CandidateValidator cValidator) {
		this.cValidator = cValidator;
	}

	protected List <Observer<Candidat>> observers = new ArrayList<Observer<Candidat>>();
	
	Predicate<Candidat> majori=c->{return ((domain.Candidat) c).getVarsta()>=18;};
    Predicate<Candidat> startWithC=c->((domain.Candidat) c).getNume().startsWith("C");

	public ControllerCandidat(){
	}
	
	public void adaugaCandidat(Integer id, String nume, String tel,
			String adresa , Integer varsta) throws ValidatorException, sun.security.validator.ValidatorException{
		
		domain.Candidat cand = new domain.Candidat(getNewID(),nume,tel,adresa,varsta);
		cValidator.validateEntity(cand);
		this._repoC.add(cand);
	}
	
	public Integer getNewID() {
		int max = 0;
		for (Candidat c: this.getCandidati()){
			if (c.getId() > max) max = c.getId();
		}
		return max+1;
	}

	public Iterable<domain.Candidat> getCandidati(){
		return this._repoC.getAll();
	}
	
	public int getNrCandidati(){
		return this._repoC.getElemsNr();
	}
	
	public domain.Candidat stergeCandidat(int id){
		int pos = this._repoC.getPosId(id);
		domain.Candidat c = this._repoC.delete(pos);
		return c;
	}
	
	public domain.Candidat getCandidat(int id){ 
		domain.Candidat c = this._repoC.findOne(id); 
		return c;
	}
	
	public void saveRepo() {
		_repoC.saveData();
	}
	
	public static <E> List<E> filtrare(List<E> list, Predicate<E> prd){
		List<E> filtrata = list
				.stream()
			    .filter(prd)
			    .collect(Collectors.toList());
		//Collections.sort(filtrata,(f1,f2)->-(int)(f1.getGreutate()-f2.getGreutate()));
		return filtrata;
	}
	
	public List<Candidat> filterCandidatiMajori(){
		List<Candidat> filt = filtrare((List<Candidat>)getCandidati(), majori);
		Collections.sort(filt,(f1,f2)->(int)(((domain.Candidat) f1).getVarsta()-((domain.Candidat) f2).getVarsta()));
		return filt;
	}
	
	public List<Candidat> filterCandidatiAni(Integer nr){
		Predicate<Candidat> aniFilter=c->{return ((domain.Candidat) c).getVarsta()>=nr;};
		List<Candidat> filt = filtrare((List<Candidat>)getCandidati(), aniFilter);
		Collections.sort(filt,(f1,f2)->(int)(((domain.Candidat) f1).getVarsta()-((domain.Candidat) f2).getVarsta()));
		return filt;
	}
	
	public List<Candidat> filterCandidatiC(){
		List<Candidat> filt =  filtrare((List<Candidat>)getCandidati(), startWithC);
		Collections.sort(filt,(f1,f2)->(((domain.Candidat) f1).getNume().compareTo(((domain.Candidat) f2).getNume())));
		return filt;
	}
	
	public List<Candidat> filterCandidatiNume(String nu){
		Predicate<Candidat> filterNume=c->((domain.Candidat) c).getNume().contains(nu);
		List<Candidat> filt =  filtrare((List<Candidat>)getCandidati(), filterNume);
		Collections.sort(filt,(f1,f2)->(((domain.Candidat) f1).getNume().compareTo(((domain.Candidat) f2).getNume())));
		return filt;
	}
	
	@Override
	public void addObserver(Observer<Candidat> o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer<Candidat> o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers(){
		for(Observer<Candidat> o : observers){
			o.update(this);
		}
	}
	
	public domain.Candidat updateC(domain.Candidat t) throws ValidatorException, sun.security.validator.ValidatorException
	{
		cValidator.validateEntity(t);
		domain.Candidat s = _repoC.update(t);
		if (s==null)
		{
			notifyObservers();
		}
		//saveRepo();
		return s;
	}
	
	public domain.Candidat saveC(domain.Candidat t) throws ValidatorException, sun.security.validator.ValidatorException
	{
		cValidator.validateEntity(t);
		domain.Candidat c= null;
		c = _repoC.save(t);
		if (c==null)
		{
			notifyObservers();
		}
		//saveRepo();
		return c;
	}
	
	public domain.Candidat deleteC(domain.Candidat t) throws ValidatorException, sun.security.validator.ValidatorException
	{
		cValidator.validateEntity(t);
		domain.Candidat c= null;
		int id = t.getId();
		int pos = _repoC.getPosId(id);
		c = _repoC.delete(pos);
		if (c!=null)
		{
			notifyObservers();
		}
		//saveRepo();
		return c;
	}
}