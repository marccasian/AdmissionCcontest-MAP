package domain;

public class SectieValidator implements IValidator<Sectie>{

	@Override
	public void validateEntity(Sectie e) throws Exception {
		// TODO Auto-generated method stub
		if (e.getNume().isEmpty()){
			throw new Exception("Numele nu poate fi vid");
		}
	}
	

}
