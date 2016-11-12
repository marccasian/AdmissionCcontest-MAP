package domain;

import sun.security.validator.ValidatorException;

public class CandidateValidator implements IValidator<Candidat> {

	@Override
	public void validateEntity(Candidat e) throws ValidatorException {
		String msg ="";
		if (e.getNume().isEmpty()){
			msg += "Numele nu poate fi vid\n";
		}
		if (e.getTel().length() != 10){
			msg += "Numarul de telefon trebuie sa aiba lungimea 10!\n";
	    }
		if (e.getAdresa().length() == 0){
			msg += "Adresa nu poate fi vida!\n";
	    }
		if (e.getVarsta() <0){
			msg += "Varsta trebuie sa fie pozitiva!";
    	}
    	if (e.getVarsta() > 99){
    		msg += "Student prea batran!(Varsta maxima 99 ani)\n";
    	}
    	if (msg != ""){
    		throw new ValidatorException(msg);
    	}
	}
}