package domain;

public interface IValidator<E> {
	public Boolean validateEntity(E e);
}
