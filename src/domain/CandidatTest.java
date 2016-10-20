package domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by CMARC on 17.10.2016.
 */
@SuppressWarnings("unused")
public class CandidatTest {
	private Candidat s;
    @Before
    public void setUp() throws Exception {
        s = new Candidat(1,"Casian","1234567890","Cluj",34);
    }

    @After
    public void tearDown() throws Exception {
        s = null;
    }

    @Test
    public void getId() throws Exception {

    }

}