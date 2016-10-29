package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import domain.Sectie;
import domain.ValidatorException;
import repository.RepoSectii;

public class RepoSectiiTest {
	private Sectie s;
	private RepoSectii rep;
	
	@Before
	public void setUp() throws Exception {
		rep  = new RepoSectii();
		s = new Sectie(1,"S1",2);
	}

	@Test
	public void testAdd() {
		try {
			rep.add(s);
		} catch (ValidatorException e) {
			System.out.println(e.getMessage());
		}
		assertEquals(1,rep.getElemsNr());
	}

	@Test
	public void testDelete() {
		try {
			rep.add(s);
		} catch (ValidatorException e) {
			System.out.println(e.getMessage());
		}
		assertEquals(1,rep.getElemsNr());
		rep.delete(0);
		assertEquals(0,rep.getElemsNr());
	}

	@Test
	public void testFindOne() {
		try {
			rep.add(s);
		} catch (ValidatorException e) {
			System.out.println(e.getMessage());
		}
		assertEquals(1,rep.getElemsNr());
		Sectie s1 = rep.findOne(1);
		assertTrue(s1.getId()==1);
		assertEquals(s1.getNume(),"S1");
	}

	@Test
	public void testGetAll() {
		try {
			rep.add(s);
		} catch (ValidatorException e) {
			System.out.println(e.getMessage());
		}
		assertEquals(1,rep.getElemsNr());
	
		ArrayList<Sectie> l = (ArrayList<Sectie>) rep.getAll();
		assertEquals(l.size(),1);
	}

	@Test
	public void testGetElemsNr() {
		
		try {
			rep.add(s);
		} catch (ValidatorException e) {
			System.out.println(e.getMessage());
		}
		assertEquals(1,rep.getElemsNr());
	}

	@Test
	public void testGetPosId() {
		try {
			rep.add(s);
		} catch (ValidatorException e) {
			System.out.println(e.getMessage());
		}
		assertEquals(0,rep.getPosId(1));
	}

}
