package de.uniluebeck.itm.vs1112.uebung2.aufgabe222;

public abstract class DleStxEtxConstants {

	public static final byte DLE = 0x10;

	public static final byte STX = 0x02;

	public static final byte ETX = 0x03;

	public static final byte[] DLE_STX = new byte[] { DLE, STX };

	public static final byte[] DLE_ETX = new byte[] { DLE, ETX };

}
