package repository;
import domain.Candidat;

public class RepositoryCandidati {
	    private Candidat candidati[];
	    private int _capacity, _size;

	    public RepositoryCandidati() {
	        _capacity = 100;
	        _size = 0;
	        candidati = new domain.Candidat[100];
	        //this(100);
	    }

	    public RepositoryCandidati(int capacity) {
	        _capacity = capacity;
	        _size = 0;
	        candidati = new domain.Candidat[capacity];
	    }
	    
	    public Candidat[] getAll(){
	    	Candidat[] res = candidati;
	    	Candidat aux;
	    	for (int i = 0; i<this._size -1; i++){
	    		for(int j = i+1; j<this._size;j++){
	    			if (candidati[i].getId() > candidati[j].getId()){
	    				aux = candidati[i];
	    				candidati[i] = candidati[j];
	    				candidati[j] = aux;
	    			}
	    		}
	    	}
	    	return res;
	    }
	    
	    private void resize() {
	        //To Do
	    }

	    public void add(domain.Candidat candidat) {
	        if (_size == _capacity) {
	            resize();
	        }

	        candidati[_size] = candidat;
	        _size++;
	    }

	    public void addCandidat(domain.Candidat c, Integer pos) {
	        if (pos >= 0) {
	            if (_size == _capacity) {
	                resize();
	            }
	            for (Integer i = _size; i > pos; i--) {
	            	candidati[i] = candidati[i - 1];
	            }
	            candidati[pos] = c;
	            _size++;
	        }
	    }

	    public domain.Candidat removePos(Integer pos) {
	        domain.Candidat candidatSters = null;
	        if (pos >= 0 && pos < _size) {
	        	candidatSters = candidati[pos];
	            for (Integer i = pos; i < _size; i++) {
	            	candidati[i] = candidati[i + 1];
	            }
	            _size--;
	        }

	        return candidatSters;
	    }
	    
	    public int getPosId(Integer id) {
            for (Integer i = 0; i < _size; i++) {
            	if (candidati[i].getId() == id){
            		return i;
            	}
            }
	        throw new RuntimeException("Nu exista Candidat cu ID-ul introdus!");
	    }
	    
	    public Candidat getCandidat(Integer id) {
            for (Integer i = 0; i < _size; i++) {
            	if (candidati[i].getId() == id){
            		return candidati[i];
            	}
            }
	        throw new RuntimeException("Nu exista Candidat cu ID-ul introdus!");
	    }

	    public Integer size() {
	        return _size;
	    }
}
