package repository;

import java.util.ArrayList;
import domain.HasId;

public abstract class RepoGeneric<E extends HasId<ID> , ID> implements IRepository<E , ID>{
	protected ArrayList<E> all;
	
	@Override
	public void add(E entity) {
		// TODO Auto-generated method stub
		all.add(entity);
		serializeEntities();
	}

	@Override
	public E delete(int pos) {
		// TODO Auto-generated method stub
		E deletedEntity = all.get(pos);
        Boolean a =all.remove(deletedEntity);
       	if (a){
       		serializeEntities();
       		return deletedEntity;
       	}
        return null;
	}

	@Override
	public abstract E findOne(ID id);

	@Override
	public abstract Iterable<E> getAll();

	@Override
	public int getElemsNr() {
		// TODO Auto-generated method stub
		return all.size();
	}

	@Override
	public abstract int getPosId(ID id) ;
	
	@Override
	public abstract void serializeEntities() ;

}
