package controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.RomanList;
import com.itextpdf.text.pdf.PdfWriter;

import domain.Candidat;
import domain.Inscriere;
import domain.RaportItem;
import domain.Sectie;
import domain.ValidatorException;
import utils.Observable;
import utils.Observer;

public class ControllerInscrieri implements Observable<Inscriere>{
	private repository.RepoInscrieriXML _repoI;
	
	public repository.RepoInscrieriXML get_repoI() {
		return _repoI;
	}

	public void set_repoI(repository.RepoInscrieriXML _repoI) {
		this._repoI = _repoI;
	}

	protected List <Observer<Inscriere>> observers = new ArrayList<Observer<Inscriere>>();
	
	public ControllerInscrieri(){
	}
	
	public void adaugaInscriere(Integer id, Candidat c, Sectie s) throws ValidatorException, sun.security.validator.ValidatorException{
		
		domain.Inscriere insc = new domain.Inscriere(getNewID(),c,s);
		this._repoI.add(insc);
	}
	
	public Integer getNewID() {
		int max = 0;
		for (Inscriere i: this.getInscrieri()){
			if (i.getId() > max) max = i.getId();
		}
		return max+1;
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
	
	public void saveRepo() {
		_repoI.saveData();
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
		//saveRepo();
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
		return c;
	}
	
	public static class comp implements Comparator<RaportItem>{
		@Override
		public int compare(RaportItem e1, RaportItem e2){
			return -1 * e1.getNrLocOcupate().compareTo(e2.getNrLocOcupate());
		}
	}

	public Collection<? extends RaportItem> getRaport(Integer nr) {
		ArrayList<domain.RaportItem> rap = new ArrayList<domain.RaportItem>();
		for (Inscriere i: getInscrieri()){
			Boolean ok = false; 
			for (RaportItem r : rap){
				if (r.getSectie().getId() == i.get_sectie().getId()){
					ok = true;
					r.setNrLocOcupate(r.getNrLocOcupate() + 1);
					break;
				}
			}
			if (!ok){
				RaportItem new_ri = new RaportItem(i.get_sectie(),1);
				rap.add(new_ri);
			}
		}
		Comparator<RaportItem> com = new comp();
		rap.sort(com);
		ArrayList<domain.RaportItem> result = new ArrayList<domain.RaportItem>();
		for (int i = 0;  i < nr && i < rap.size(); i++ ){
			result.add(new RaportItem());
		}
		for (int i = 0; i < nr && i < rap.size(); i++ ){
			result.set(i, rap.get(i));
		}
		return result;
	}

	public String exportToPDF(String fileName, Integer nr) {
		Document document = new Document();
		Collection<? extends RaportItem> rap = getRaport(nr);
  		try
  		{
	     	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
	     	document.open();
	     	document.addAuthor("Casian Nicolae Marc");
	     	document.addCreationDate();
	     	document.addCreator("Casian Nicolae Marc");
	     	String title = String.format("Top %s Sectii",nr.toString());
	     	document.addTitle(title);
	     	String subject = String.format("Raport: top %s cele mai solicitate sectii", nr.toString());
	     	document.addSubject(subject);
	     	Font fontbold = FontFactory.getFont("Times-Roman", 20, Font.BOLD);
	     	String ps = String.format("Top %s sectii", nr.toString());
	     	Paragraph p = new Paragraph(ps, fontbold);
	     	p.setAlignment(Element.ALIGN_CENTER);
	     			
	     	document.add(p); 
	     	document.add(new Paragraph("\n"));
	     	RomanList romanList = new RomanList();
	     	for(RaportItem r :rap){
	     		romanList.add(new ListItem(r.toString()));
	     	}
	        document.add(romanList);
	     	document.close();
	     	writer.close();
  		} catch (DocumentException e)
  		{
	  		e.printStackTrace();
  		} catch (FileNotFoundException e)
  		{
	  		e.printStackTrace();
  		}
  		Path currentRelativePath = Paths.get(fileName);
  		String s = currentRelativePath.toAbsolutePath().toString();
		return s;
	}
	
}
