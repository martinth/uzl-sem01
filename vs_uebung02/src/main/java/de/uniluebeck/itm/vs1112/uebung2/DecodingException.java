package de.uniluebeck.itm.vs1112.uebung2;

public class DecodingException extends Exception {

	public DecodingException() {
	}

	public DecodingException(final Throwable cause) {
		super(cause);
	}

	public DecodingException(final String message) {
		super(message);
	}

	public DecodingException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
