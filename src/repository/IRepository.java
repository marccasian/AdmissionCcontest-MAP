package repository;

public interface IRepository<E, ID> {
	 void add(E entity);
	 E delete(ID id);
	 E findOne(ID id);
	 Iterable<E> getAll();
	 int getElemsNr();
	 int getPosId(int id);
}
