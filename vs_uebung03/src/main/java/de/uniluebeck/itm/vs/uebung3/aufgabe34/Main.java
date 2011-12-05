package de.uniluebeck.itm.vs.uebung3.aufgabe34;

import de.uniluebeck.itm.vs.uebung3.aufgabe32.Client;

/**
 * This is the Main class to test exercise 3.4 and MUST not be modified!
 */
public class Main {

	public static void main(String[] args) {
		final int port = 3344;

		// Server Thread
		new Thread() {
			@Override
			public void run() {
				Server server = new Server(port);
				server.run();
			}
		}.start();

		// Client Thread
		new Thread() {
			@Override
			public void run() {
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Client client = new Client(port);
				client.run();
			}
		}.start();
	}

}
