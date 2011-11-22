package de.uniluebeck.itm.vs.uebung3.types;

public class Speise {

	private String description;

	private int priceInCent;

	public Speise(final String description, final int priceInCent) {
		this.description = description;
		this.priceInCent = priceInCent;
	}

	public String getDescription() {
		return description;
	}

	public int getPriceInCent() {
		return priceInCent;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final Speise speise = (Speise) o;

		if (priceInCent != speise.priceInCent) {
			return false;
		}
		if (!description.equals(speise.description)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = description.hashCode();
		result = 31 * result + priceInCent;
		return result;
	}

	@Override
	public String toString() {
		return "Speise{" +
				"description='" + description + '\'' +
				", priceInCent=" + priceInCent +
				'}';
	}
}
