package repository;

import java.util.ArrayList;
//import java.util.List;

import domain.HasId;
import domain.ValidatorException;

public abstract class RepoGeneric<E extends HasId<ID> , ID> implements IRepository<E , ID>{
	protected ArrayList<E> all = new ArrayList<>();
	
	@Override
	public void add(E entity) throws ValidatorException {
		// TODO Auto-generated method stub
		for (E e:all)
			if (e.getId().equals(entity.getId()))
				throw new ValidatorException("Exista deja o entitate cu acest ID");
		all.add(entity);
	}

	@Override
	public E delete(int pos) {
		// TODO Auto-generated method stub
		E deletedEntity = all.get(pos);
        Boolean a =all.remove(deletedEntity);
       	if (a){
       		//serializeEntities();
       		return deletedEntity;
       	}
        return null;
	}

	@Override
	public E findOne(ID id) {
		if (null==id)
			return null;
		for(E e:all)
			if (e.getId().equals(id))
				return e;
		return null;
	}

//	@Override
//	public Iterable<E> getAll(){
//		return all;
//	}

	@Override
	public int getElemsNr() {
		// TODO Auto-generated method stub
		return all.size();
	}

	@Override
	public  int getPosId(ID id){
		// TODO Auto-generated method stub
		for (int i=0; i< all.size(); i++){
			//System.out.println("Repo for id: "+all.get(i).getId());
    		if (all.get(i).getId().equals(id)){
    			return i;
    		}
    	}
        throw new RuntimeException("Nu exista Candidat cu ID-ul introdus!"+id);
    }
	
//	@Override
//	public abstract void serializeEntities() ;

}
