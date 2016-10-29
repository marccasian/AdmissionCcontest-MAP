package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import domain.Sectie;

public class TestSectie {
	
	private Sectie s;
	
	@Before
	public void setUp() throws Exception{
		s = new Sectie(1, "S1", 2);
	}
	
	@Test
	public void testGetId() {
		//fail("Not yet implemented");
		int id = s.getId();
		assertTrue("getId() returned"+id+"instead of 1",id == 1);
	}

	@Test
	public void testGetNume() {
		//fail("Not yet implemented");
		String nume = s.getNume();
		assertEquals(nume,"S1");
	}

	@Test
	public void testGetNrLoc() {
		//fail("Not yet implemented");
		int nr = s.getNrLoc();
		assertTrue("getNrLoc() returned"+nr+"instead of 2",nr == 2);
	}

	@Test
	public void testSetId() {
		//fail("Not yet implemented");
		s.setId(2);
		assertTrue(s.getId() == 2);
	}

	@Test
	public void testSetNume() {
		//fail("Not yet implemented");
		s.setNume("S2");
		assertTrue(s.getNume() == "S2");
	}

	@Test
	public void testSetNrLoc() {
		s.setNrLoc(1);
		assertTrue(s.getNrLoc() == 1);
	}

}
