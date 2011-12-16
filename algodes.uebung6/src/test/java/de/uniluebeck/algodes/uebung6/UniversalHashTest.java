package de.uniluebeck.algodes.uebung6;

import static org.junit.Assert.*;

import org.junit.Test;

import de.uniluebeck.algodes.uebung6.hashes.Hashfunction;
import de.uniluebeck.algodes.uebung6.hashes.UniversalHash;

public class UniversalHashTest {

	@Test
	public void testCreateNext() {
		Hashfunction uH = new UniversalHash();
		uH.createNext();
	}

}
