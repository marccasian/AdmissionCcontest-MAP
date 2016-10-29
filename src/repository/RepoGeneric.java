package repository;

import java.util.ArrayList;
//import java.util.List;

import domain.HasId;
import domain.ValidatorException;

public abstract class RepoGeneric<E extends HasId<ID> , ID> implements IRepository<E , ID>{
	protected ArrayList<E> all = new ArrayList<>();
	
	@Override
	public void add(E entity) throws ValidatorException {
		for (E e:all)
			if (e.getId().equals(entity.getId()))
				throw new ValidatorException("Exista deja o entitate cu acest ID");
		all.add(entity);
	}

	@Override
	public E delete(int pos) {
		E deletedEntity = all.get(pos);
        Boolean a =all.remove(deletedEntity);
       	if (a){
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

	@Override
	public int getElemsNr() {
		return all.size();
	}

	@Override
	public  int getPosId(ID id){
		for (int i=0; i< all.size(); i++){
    		if (all.get(i).getId().equals(id)){
    			return i;
    		}
    	}
        throw new RuntimeException("Nu exista Candidat cu ID-ul introdus!"+id);
    }
}
