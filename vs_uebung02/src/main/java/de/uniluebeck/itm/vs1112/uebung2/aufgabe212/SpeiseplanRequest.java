package de.uniluebeck.itm.vs1112.uebung2.aufgabe212;

public class SpeiseplanRequest {

	private int dayOfYear;

	public SpeiseplanRequest(final int dayOfYear) {
		this.dayOfYear = dayOfYear;
	}

	public int getDayOfYear() {
		return dayOfYear;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final SpeiseplanRequest that = (SpeiseplanRequest) o;

		if (dayOfYear != that.dayOfYear) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return dayOfYear;
	}

	@Override
	public String toString() {
		return "SpeiseplanRequest{" +
				"dayOfYear=" + dayOfYear +
				'}';
	}
}
