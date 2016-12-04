package repository;

import java.util.Comparator;

import domain.Inscriere;

public class RepoInscrieri extends RepoGeneric<Inscriere, Integer> {

	public static class comp implements Comparator<Inscriere>{
		@Override
		public int compare(Inscriere e1, Inscriere e2){
			return e1.getId().compareTo(e2.getId());
		}
	}
	
	@Override
	public Iterable<Inscriere> getAll() {
		Comparator<Inscriere> com = new comp();
    	all.sort(com);
		return all;
	}
	
	public void addInscriere(domain.Inscriere i, Integer pos) {
        if (pos >= 0) {
        	all.remove(pos);
            all.add(pos,i);
        }
    }	
}
