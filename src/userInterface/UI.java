package userInterface;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import domain.Candidat;
import domain.Sectie;

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
	        	break;
	        }
	        else if (opt.equals("1")){
	        	adaugaCandidatUI();
	        }
	        else if (opt.equals("2")){
	        	adaugaSectieUI();
	        }
	        else if (opt.equals("3")){
	        	stergeCandidatUI();
	        }
	        else if (opt.equals("4")){
	        	stergeSectieUI();
	        }
	        else if (opt.equals("5")){
	        	modificareCandidatUI();
	        }
	        else if (opt.equals("6")){
	        	modificareSectieUI();
	        }
	        else if (opt.equals("7")){
	        	afisareCandidati();
	        }
	        else if (opt.equals("8")){
	        	afisareSectii();
	        }
		}
		
	}
	
	public void adaugaCandidatUI(){
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
	    if (nume.length() == 0){
	    	System.out.println("Numele nu poate fi vid!");
	    	return;
	    }
	    
	    
		System.out.print("	Introduceti Telefonul Candidatului: ");
		String tel = input.next(); 
        System.out.println();
	    if (tel.length() != 10){
	    	System.out.println("Numarul de telefon trebuie sa aiba lungimea 10!");
	    	return;
	    }
	    
		System.out.print("	Introduceti Adresa Candidatului: ");
		String adresa = input.next(); 
        System.out.println();
	    if (adresa.length() == 0){
	    	System.out.println("Adresa nu poate fi vida!");
	    	return;
	    }
		System.out.print("	Introduceti Varsta Candidatului: ");
		String varstas = input.next(); 
        System.out.println();
	    try{ 
	    	varsta = Integer.parseInt(varstas);
	    	if (varsta <0){
	    		throw new Exception("Varsta trebuie sa fie pozitiva!");
	    	}
	    	if (varsta > 99){
	    		throw new Exception("Student prea batran!");
	    	}
	    }
	    catch (Exception e){
	    	System.out.println("Varsta introdus este incorecta, trebuie sa fie numar intreg! "+e.getMessage());
	    	return;
	    }
	    this._ctr.adaugaCandidat(id, nume, tel, adresa, varsta);
	    //domain.Candidat c = new domain.Candidat(id, nume, tel, adresa ,varsta);
	    //System.out.println(c);	    
	}
	
	public void adaugaSectieUI(){
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
	    if (nume.length() == 0){
	    	System.out.println("Numele nu poate fi vid!");
	    	return;
	    }
	    
	    System.out.print("	Introduceti numerul de locuri ale sectiei: ");
		String nrLocs = input.next(); 
        System.out.println();
	    try{ 
	    	nrLoc = Integer.parseInt(nrLocs);
	    	if (nrLoc <0){
	    		throw new Exception("Numarul de locuri trebuie sa fie pozitiv!");
	    	}
	    }
	    catch (Exception e){
	    	System.out.println("Numarul de locuri introdus este incorect, trebuie sa fie numar intreg! "+e.getMessage());
	    	return;
	    }
	    this._ctr.adaugaSectie(id, nume, nrLoc);	    
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
	    	System.out.println("ID-ul introdus este 4"
	    			+ "incorect, trebuie sa fie numar intreg!"+e.getMessage());
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
	
	public void modificareCandidatUI(){
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
	    	adaugaCandidatUI();
	    	if (size  == this._ctr.getNrCandidati()){
	    		return;
	    	}
	    	this._ctr.stergeCandidat(id);
	    }
	    catch (RuntimeException e){
	    	System.out.println(e.getMessage());
	    	return;
	    }
	}
	
	public void modificareSectieUI(){
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
	    	adaugaSectieUI();
	    	if (size  == this._ctr.getNrSectii()){
	    		return;
	    	}
	    	this._ctr.stergeSectie(id);
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

