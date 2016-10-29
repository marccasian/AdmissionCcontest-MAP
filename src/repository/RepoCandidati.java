package repository;

import java.util.ArrayList;
import java.util.Comparator;
import domain.Candidat;

public class RepoCandidati extends RepoGeneric<Candidat, Integer>{

    public RepoCandidati() {
        all = new ArrayList<Candidat>();
        all = SerializableIO.deserializeCandidat("Candidati.bin");
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
        System.out.println(id);
        throw new RuntimeException("Nu exista Candidat cu ID-ul introdus!"+id);
    }
 
    @Override
	public Iterable<Candidat> getAll() {
		// TODO Auto-generated method stub
    	Comparator<Candidat> com = new comp();
    	all.sort(com);
    	return all;
    }
    

	@Override
	public int getPosId(Integer id) {
		// TODO Auto-generated method stub
		for (int i=0; i< all.size(); i++){
			//System.out.println("Repo for id: "+all.get(i).getId());
    		if (all.get(i).getId() == id){
    			return i;
    		}
    	}
        throw new RuntimeException("Nu exista Candidat cu ID-ul introdus!"+id);
    }
	
	@Override
	public void serializeEntities(){
		SerializableIO.serializeCandidat("Candidati.bin", all);
	}

    public void addCandidat(domain.Candidat c, Integer pos) {
        if (pos >= 0) {
        	all.remove(pos);
            all.add(pos,c);
        }
    }
}


