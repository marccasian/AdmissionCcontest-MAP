package repository;

//import java.util.ArrayList;
import java.util.Comparator;
import domain.Candidat;

public class RepoCandidati extends RepoGeneric<Candidat, Integer>{

    public RepoCandidati() {
        //all = SerializableIO.deserializeCandidat("Candidati.bin");
    }
    
    public static class comp implements Comparator<Candidat>{
		@Override
		public int compare(Candidat e1, Candidat e2){
			return e1.getId().compareTo(e2.getId());
		}
	}
 
    @Override
	public Iterable<Candidat> getAll() {
		// TODO Auto-generated method stub
    	Comparator<Candidat> com = new comp();
    	all.sort(com);
    	return all;
    }
	
	public void serializeCandidat(){
		SerializableIO.serializeCandidat("Candidati.bin", all);
	}

    public void addCandidat(domain.Candidat c, Integer pos) {
        if (pos >= 0) {
        	all.remove(pos);
            all.add(pos,c);
        }
    }
}


