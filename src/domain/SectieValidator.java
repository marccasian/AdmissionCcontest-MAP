package domain;

import sun.security.validator.ValidatorException;

public class SectieValidator implements IValidator<Sectie>{

	@Override
	public void validateEntity(Sectie e) throws ValidatorException {		
		String msg ="";
		if (e.getNume().isEmpty()){
			msg += "Numele nu poate fi vid\n";
		}
		
		if (e.getNrLoc()< 0){
			msg += "Numarul de locuri trebuie sa fie pozitiv!\n";
	    }
		if (msg != ""){
    		throw new ValidatorException(msg);
    	}
	}
}