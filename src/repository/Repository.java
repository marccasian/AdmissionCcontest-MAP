/**
 * 
 */
package repository;

import domain.Candidat;
import domain.Sectie;

/**
 * @author CMARC
 *
 */
public class Repository {
	
	private RepositoryCandidati repoCandidati ;
	private RepositorySectii repoSectii ;
	
	public Repository(){
		repoCandidati = new RepositoryCandidati();
		repoSectii = new RepositorySectii();
	}
	
	public Candidat[] getCandidati(){
    	return repoCandidati.getAll();
    }
	
	public int getNrCandidati(){
    	return repoCandidati.size();
    }
	
	public int getNrSectii(){
    	return repoSectii.size();
    }
	
	public Sectie[] getSectii(){
    	return repoSectii.getAll();
    }
	
	public void addCandidat(domain.Candidat candidat) {
        repoCandidati.add(candidat);
    }
	
	public void addSectie(domain.Sectie sectie) {
        repoSectii.add(sectie);
    }
	
	public int getPosIdCandidat(int id){
		return repoCandidati.getPosId(id);
	}
	
	public int getPosIdSectie(int id){
		return repoSectii.getPosId(id);
	}
	
	public Candidat stergeCandidat(int pos){
    	return repoCandidati.removePos(pos);
    }
	
	public Sectie stergeSectie(int pos){
    	return repoSectii.removePos(pos);
    }
	
	public Candidat getCandidat(int id){
    	return repoCandidati.getCandidat(id);
    }
	
	public Sectie getSectie(int id){
    	return repoSectii.getSectie(id);
    }
	
}
