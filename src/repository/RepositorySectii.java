package repository;

import domain.Sectie;

public class RepositorySectii {
	private Sectie sectii[];
    private int _capacity, _size;

    public RepositorySectii() {
        _capacity = 100;
        _size = 0;
        sectii = new domain.Sectie[100];
        //this(100);
    }

    public RepositorySectii(int capacity) {
        _capacity = capacity;
        _size = 0;
        sectii = new domain.Sectie[capacity];
    }
    
    public Sectie[] getAll(){
    	Sectie[] res = sectii;
    	Sectie aux;
    	for (int i = 0; i<this._size -1; i++){
    		for(int j = i+1; j<this._size;j++){
    			if (sectii[i].getId() > sectii[j].getId()){
    				aux = sectii[i];
    				sectii[i] = sectii[j];
    				sectii[j] = aux;
    			}
    		}
    	}
    	return res;
    }
    
    private void resize() {
        //To Do
    }

    public void add(domain.Sectie sectie) {
        if (_size == _capacity) {
            resize();
        }

        sectii[_size] = sectie;
        _size++;
    }
    
    public int getPosId(Integer id) {
        for (Integer i = 0; i < _size; i++) {
        	if (sectii[i].getId() == id){
        		return i;
        	}
        }
        throw new RuntimeException("Nu exista Sectie cu ID-ul introdus");
    }

    public void addSectie(domain.Sectie c, Integer pos) {
        if (pos >= 0) {
            if (_size == _capacity) {
                resize();
            }

            for (Integer i = _size; i > pos; i--) {
            	sectii[i] = sectii[i - 1];
            }

            sectii[pos] = c;
            _size++;
        }
    }

    public domain.Sectie removePos(Integer pos) {
        domain.Sectie sectieStearsa = null;
        if (pos >= 0 && pos < _size) {
        	sectieStearsa = sectii[pos];

            for (Integer i = pos; i < _size; i++) {
            	sectii[i] = sectii[i + 1];
            }
            _size--;
        }

        return sectieStearsa;
    }

    public Sectie getSectie(Integer id) {
        for (Integer i = 0; i < _size; i++) {
        	if (sectii[i].getId() == id){
        		return sectii[i];
        	}
        }
        throw new RuntimeException("Nu exista Sectie cu ID-ul introdus!");
    }
    
    public Integer size() {
        return _size;
    }

}
