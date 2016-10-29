package repository;

//import java.util.ArrayList;
import java.util.Comparator;
import domain.Sectie;

public class RepoSectii extends RepoGeneric<Sectie, Integer>{

    public RepoSectii() {
        //all = SerializableIO.deserializeSectie("Sectie.bin");
    }
    
    public static class comp implements Comparator<Sectie>{
		@Override
		public int compare(Sectie e1, Sectie e2){
			return e1.getId().compareTo(e2.getId());
		}
	}
 
    @Override
	public Iterable<Sectie> getAll() {
		// TODO Auto-generated method stub
    	Comparator<Sectie> com = new comp();
    	all.sort(com);
    	return all;
    }
	
//	@Override
//	public void serializeEntities(){
//		SerializableIO.serializeSectie("Sectie.bin", all);
//	}

}


