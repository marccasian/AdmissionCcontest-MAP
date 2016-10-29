package userInterface;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import domain.Candidat;
import domain.Sectie;
import sun.security.validator.ValidatorException;

public class UI {
	private Scanner input = new Scanner(System.in);
	private controller.Controller  _ctr;
	public UI(controller.Controller ctr){
		this._ctr = ctr;
		Meniu();
		
	}
	public void Meniu(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "Adauga candidat");
		map.put("2", "Adauga optiune");
		map.put("3", "Sterge candidat");
		map.put("4", "Sterge optiune");
		map.put("5", "Modificare candidat");
		map.put("6", "Modificare optiune");
		map.put("7", "Afisare candidati");
		map.put("8", "Afisare sectii");
		map.put("0", "Iesire");
		
		String opts[] = {"0","1","2","3","4","5","6","7","8"};
		
		while (true) {
			System.out.println("-----> MENIU <-----");
	        System.out.println("1 - Adauga candidat");
	        System.out.println("2 - Adauga optiune");
	        System.out.println("3 - Sterge candidat");
	        System.out.println("4 - Sterge optiune");
	        System.out.println("5 - Modificare candidat");
	        System.out.println("6 - Modificare optiune");
	        System.out.println("7 - Afisare candidati");
	        System.out.println("8 - Afisare sectii");
	        System.out.println("0 - Iesire");
	        System.out.print("	Introduceti optiunea: ");
	        
	        String opt = input.next();
	     
	        System.out.println();
	        
	        if (Arrays.asList(opts).contains(opt)){
	        	System.out.print("Ati ales optiunea : "+map.get(opt)+"\n");
	        }
	        else{
	        	System.out.println("Ati ales o optiune invalida. Va rugam introduceti o valoare intre 1 - 6\n");
	        	System.out.println();
	        }
	        if (opt.equals("0")) {
	        	_ctr.saveRepo();
	        	break;
	        }
	        else if (opt.equals("1")){
	        	try {
	        		adaugaCandidatUI();
				} catch (ValidatorException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.out.println(e.getMessage());
				} catch (domain.ValidatorException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.out.println(e.getMessage());
				}
	        }
	        else if (opt.equals("2")){
	        	try {
					adaugaSectieUI();
				} catch (ValidatorException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.out.println(e.getMessage());
				} catch (domain.ValidatorException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.out.println(e.getMessage());
				}
	        }
	        else if (opt.equals("3")){
	        	stergeCandidatUI();
	        }
	        else if (opt.equals("4")){
	        	stergeSectieUI();
	        }
	        else if (opt.equals("5")){
	        	try {
					modificareCandidatUI();
				} catch (ValidatorException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.out.println(e.getMessage());
				} catch (domain.ValidatorException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.out.println(e.getMessage());
				}
	        }
	        else if (opt.equals("6")){
	        	try {
					modificareSectieUI();
				} catch (ValidatorException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.out.println(e.getMessage());
				} catch (domain.ValidatorException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.out.println(e.getMessage());
				}
	        }
	        else if (opt.equals("7")){
	        	afisareCandidati();
	        }
	        else if (opt.equals("8")){
	        	afisareSectii();
	        }
		}
		
	}
	
	public void adaugaCandidatUI() throws domain.ValidatorException, ValidatorException{
		System.out.print("	Introduceti ID-ul candidatului: ");
        String ids = input.next(); 
        System.out.println();
        Integer id;
        Integer varsta;
	    try{ 
	    	id = Integer.parseInt(ids);
	    }
	    catch (Exception e){
	    	System.out.println("ID-ul introdus este incorect, trebuie sa fie numar intreg!"+e);
	    	return;
	    }
	    
		System.out.print("	Introduceti Numele Candidatului: ");
		String nume = input.next(); 
        System.out.println();
//	    if (nume.length() == 0){
//	    	System.out.println("Numele nu poate fi vid!");
//	    	return;
//	    }
//	    
	    
		System.out.print("	Introduceti Telefonul Candidatului: ");
		String tel = input.next(); 
        System.out.println();
//	    if (tel.length() != 10){
//	    	System.out.println("Numarul de telefon trebuie sa aiba lungimea 10!");
//	    	return;
//	    }
	    
		System.out.print("	Introduceti Adresa Candidatului: ");
		String adresa = input.next(); 
        System.out.println();
//	    if (adresa.length() == 0){
//	    	System.out.println("Adresa nu poate fi vida!");
//	    	return;
//	    }
		System.out.print("	Introduceti Varsta Candidatului: ");
		String varstas = input.next(); 
        System.out.println();
	    try{ 
	    	varsta = Integer.parseInt(varstas);
//	    	if (varsta <0){
//	    		throw new ValidatorException("Varsta trebuie sa fie pozitiva!");
//	    	}
//	    	if (varsta > 99){
//	    		throw new ValidatorException("Student prea batran!");
//	    	}
	    }
	    catch (Exception e){
	    	System.out.println("Varsta introdus este incorecta, trebuie sa fie numar intreg! "+e.getMessage());
	    	return;
	    }
	    
		this._ctr.adaugaCandidat(id, nume, tel, adresa, varsta);
		
	    //domain.Candidat c = new domain.Candidat(id, nume, tel, adresa ,varsta);
	    //System.out.println(c);	    
	}
	
	public void adaugaSectieUI() throws domain.ValidatorException, ValidatorException{
		System.out.print("	Introduceti ID-ul Sectiei: ");
        String ids = input.next(); 
        System.out.println();
        Integer id;
        Integer nrLoc;
	    try{ 
	    	id = Integer.parseInt(ids);
	    }
	    catch (Exception e){
	    	System.out.println("ID-ul introdus este incorect, trebuie sa fie numar intreg!"+e);
	    	return;
	    }
	    
	    		
		System.out.print("	Introduceti Numele Sectiei: ");
		String nume = input.next(); 
        System.out.println();
//	    if (nume.length() == 0){
//	    	System.out.println("Numele nu poate fi vid!");
//	    	return;
//	    }
	    
	    System.out.print("	Introduceti numerul de locuri ale sectiei: ");
		String nrLocs = input.next(); 
        System.out.println();
	    try{ 
	    	nrLoc = Integer.parseInt(nrLocs);
//	    	if (nrLoc <0){
//	    		throw new ValidatorException("Numarul de locuri trebuie sa fie pozitiv!");
//	    	}
	    }
	    catch (Exception e){
	    	System.out.println("Numarul de locuri introdus este incorect, trebuie sa fie numar intreg! "+e.getMessage());
	    	return;
	    }
	    
	    try{
	    	this._ctr.adaugaSectie(id, nume, nrLoc);
	    }catch (ValidatorException | domain.ValidatorException e){
	    	System.out.println(e.getMessage());
	    }
	    
	        
	}
	
	public void stergeCandidatUI(){
		afisareCandidati();
		System.out.print("	Introduceti ID-ul Candidatului pe care doriti sa il stergeti: ");
        String ids = input.next(); 
        System.out.println();
        Integer id;
	    try{ 
	    	id = Integer.parseInt(ids);
	    }
	    catch (Exception e){
	    	System.out.println("ID-ul introdus este incorect, trebuie sa fie numar intreg!"+e.getMessage());
	    	return;
	    }
	    try{
	    	this._ctr.stergeCandidat(id);
	    }
	    catch (RuntimeException e){
	    	System.out.println(e.getMessage());
	    	return;
	    }
	}
	
	public void stergeSectieUI(){
		afisareSectii();
		System.out.print("	Introduceti ID-ul Sectiei pe care doriti sa o stergeti: ");
        String ids = input.next(); 
        System.out.println();
        Integer id;
	    try{ 
	    	id = Integer.parseInt(ids);
	    }
	    catch (Exception e){
	    	System.out.println("ID-ul introdus este incorect, trebuie sa fie numar intreg!"+e.getMessage());
	    	return;
	    }
	    try{
	    	this._ctr.stergeSectie(id);
	    }
	    catch (RuntimeException e){
	    	System.out.println(e.getMessage());
	    	return;
	    }
	}
	
	public void modificareCandidatUI()throws domain.ValidatorException, ValidatorException{
		afisareCandidati();
		System.out.print("	Introduceti ID-ul Candidatului pe care doriti sa il modificati: ");
        String ids = input.next(); 
        System.out.println();
        Integer id;
	    try{ 
	    	id = Integer.parseInt(ids);
	    }
	    catch (Exception e){
	    	System.out.println("ID-ul introdus este incorect, trebuie sa fie numar intreg!"+e.getMessage());
	    	return;
	    }
	    try{
	    	Candidat c =this._ctr.getCandidat(id);
	    	System.out.println("Modificare: "+c);
	    	int size = this._ctr.getNrCandidati();
	    	Candidat cs = this._ctr.stergeCandidat(id);
	    	adaugaCandidatUI();
	    	if (size  == this._ctr.getNrCandidati()){
	    		return;
	    	}
	    	else if(size-1  == this._ctr.getNrCandidati()){
	    		this._ctr.adaugaCandidat(cs.getId(), cs.getNume(), cs.getTel(), cs.getAdresa(), cs.getVarsta());
	    	}
	    }
	    catch (RuntimeException e){
	    	System.out.println(e.getMessage());
	    	return;
	    }
	}
	
	public void modificareSectieUI() throws domain.ValidatorException, ValidatorException{
		afisareSectii();
		System.out.print("	Introduceti ID-ul Sectiei pe care doriti sa o modificati: ");
        String ids = input.next(); 
        System.out.println();
        Integer id;
	    try{ 
	    	id = Integer.parseInt(ids);
	    }
	    catch (Exception e){
	    	System.out.println("ID-ul introdus este incorect, trebuie sa fie numar intreg!"+e.getMessage());
	    	return;
	    }
	    try{
	    	domain.Sectie s =this._ctr.getSectie(id);
	    	System.out.println("Modificare: "+s);
	    	int size = this._ctr.getNrSectii();
	    	Sectie ss  = this._ctr.stergeSectie(id);
	    	adaugaSectieUI();
	    	if (size  == this._ctr.getNrSectii()){
	    		return;
	    	}else if(size-1  == this._ctr.getNrSectii()){
	    		this._ctr.adaugaSectie(ss.getId(), ss.getNume(), ss.getNrLoc());
	    	}
	    }
	    catch (RuntimeException e){
	    	System.out.println(e.getMessage());
	    	return;
	    } 
	}
	
	public void afisareCandidati(){
		Iterable<Candidat> candidati= this._ctr.getCandidati();
		System.out.println();
		System.out.println("Lista candidatilor: ");
		for (domain.Candidat c : candidati) {
			if (c != null){
				System.out.println(c);
			}
        }
		
	}
	
	public void afisareSectii(){
		Iterable<Sectie> sectii= this._ctr.getSectii();
		System.out.println();
		System.out.println("Lista sectilor: ");
		for (domain.Sectie s : sectii) {
			if (s != null){
				System.out.println(s);
			}
        }
	}
	
	
}

