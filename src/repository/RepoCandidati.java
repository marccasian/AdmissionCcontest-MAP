package repository;

import java.util.ArrayList;
import java.util.Comparator;
import domain.Candidat;

public class RepoCandidati extends RepoGeneric<Candidat, Integer>{

    public RepoCandidati() {
        all = new ArrayList<Candidat>();
    }
    
    public static class comp implements Comparator<Candidat>{
		@Override
		public int compare(Candidat e1, Candidat e2){
			return e1.getId().compareTo(e2.getId());
		}
	}

    @Override
	public Candidat findOne(Integer id) {
		// TODO Auto-generated method stub
        for (Integer i = 0; i < all.size(); i++) {
        	if (all.get(i).getId() == id){
        		return all.get(i);
        	}
        }
        throw new RuntimeException("Nu exista Candidat cu ID-ul introdus!");
    }
 
    @Override
	public Iterable<Candidat> getAll() {
		// TODO Auto-generated method stub
    	Comparator<Candidat> com = new comp();
    	all.sort(com);
    	return all;
    }
    

	@Override
	public int getPosId(int id) {
		// TODO Auto-generated method stub
		for (int i=0; i< all.size(); i++){
    		if (all.get(i).getId() == id){
    			return i;
    		}
    	}
        throw new RuntimeException("Nu exista Candidat cu ID-ul introdus!");
    }

    public void addCandidat(domain.Candidat c, Integer pos) {
        if (pos >= 0) {
        	all.remove(pos);
            all.add(pos,c);
        }
    }
}


