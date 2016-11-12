package domain;

public interface IValidator<E> {
	void validateEntity(E e) throws ValidatorException, sun.security.validator.ValidatorException;
}