package de.uniluebeck.itm.vs.uebung3.types;

public class SpeiseplanResponse {

	private int dayOfYear;

	private Speiseplan speiseplan;

	public SpeiseplanResponse(final int dayOfYear, final Speiseplan speiseplan) {

		if (speiseplan == null) {
			throw new IllegalArgumentException("Argument speiseplan must be non-null");
		}

		this.dayOfYear = dayOfYear;
		this.speiseplan = speiseplan;
	}

	public int getDayOfYear() {
		return dayOfYear;
	}

	public Speiseplan getSpeiseplan() {
		return speiseplan;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final SpeiseplanResponse that = (SpeiseplanResponse) o;

		if (dayOfYear != that.dayOfYear) {
			return false;
		}
		if (!speiseplan.equals(that.speiseplan)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = dayOfYear;
		result = 31 * result + speiseplan.hashCode();
		return result;
	}

	public String toString() {
		return "SpeiseplanResponse{" +
			"dayOfYear=" + dayOfYear +
			", speiseplan=" + speiseplan +
			'}';
	}
}
