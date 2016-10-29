package repository;

public interface IRepository<E, ID> {
	 void add(E entity);
	 E delete(int pos);
	 E findOne(ID id);
	 Iterable<E> getAll();
	 int getElemsNr();
	 int getPosId(ID id);
}
