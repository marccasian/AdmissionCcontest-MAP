package repository;

import java.util.ArrayList;
import java.util.Comparator;
import domain.Sectie;

public class RepoSectii extends RepoGeneric<Sectie, Integer>{

    public RepoSectii() {
        all = new ArrayList<Sectie>();
    }
    
    public static class comp implements Comparator<Sectie>{
		@Override
		public int compare(Sectie e1, Sectie e2){
			return e1.getId().compareTo(e2.getId());
		}
	}

    @Override
	public Sectie findOne(Integer id) {
		// TODO Auto-generated method stub
        for (Integer i = 0; i < all.size(); i++) {
        	if (all.get(i).getId() == id){
        		return all.get(i);
        	}
        }
        throw new RuntimeException("Nu exista Sectie cu ID-ul introdus!");
    }
 
    @Override
	public Iterable<Sectie> getAll() {
		// TODO Auto-generated method stub
    	Comparator<Sectie> com = new comp();
    	all.sort(com);
    	return all;
    }
    

	@Override
	public int getPosId(Integer id) {
		// TODO Auto-generated method stub
		for (int i=0; i< all.size(); i++){
    		if (all.get(i).getId() == id){
    			return i;
    		}
    	}
        throw new RuntimeException("Nu exista Sectie cu ID-ul introdus!");
    }

    public void addCandidat(domain.Sectie c, Integer pos) {
        if (pos >= 0) {
        	all.remove(pos);
            all.add(pos,c);
        }
    }
}


