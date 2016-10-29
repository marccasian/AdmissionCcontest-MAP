package repository;

import java.util.Comparator;
import domain.Sectie;

public class RepoSectii extends RepoGeneric<Sectie, Integer>{
    
    public static class comp implements Comparator<Sectie>{
		@Override
		public int compare(Sectie e1, Sectie e2){
			return e1.getId().compareTo(e2.getId());
		}
	}
 
    @Override
	public Iterable<Sectie> getAll() {
    	Comparator<Sectie> com = new comp();
    	all.sort(com);
    	return all;
    }
}


