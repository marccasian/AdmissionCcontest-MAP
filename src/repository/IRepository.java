package repository;

import domain.ValidatorException;

public interface IRepository<E, ID> {
	 void add(E entity) throws ValidatorException;
	 E delete(int pos);
	 E findOne(ID id);
	 Iterable<E> getAll();
	 int getElemsNr();
	 int getPosId(ID id);
	 //void serializeEntities();
}
