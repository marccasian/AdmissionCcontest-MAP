package domain;


public class CandidateValidator implements IValidator<Candidat> {

	public CandidateValidator() {
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void validateEntity(Candidat e) throws Exception {
		// TODO Auto-generated method stub
		if (e.getNume().isEmpty()){
			throw new Exception("Numele nu poate fi vid");
		}
	}

}
