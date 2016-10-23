package domain;

public interface IValidator<E> {
	public void validateEntity(E e) throws Exception;
}
