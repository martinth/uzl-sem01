package de.uniluebeck.itm.vs1112.uebung2.aufgabe212;

import java.util.Collection;

public class Speiseplan {

	private Collection<Speise> speisen;

	public Speiseplan(final Collection<Speise> speisen) {

		if (speisen == null) {
			throw new IllegalArgumentException("Argument speisen must be non-null!");
		}

		this.speisen = speisen;
	}

	public Collection<Speise> getSpeisen() {
		return speisen;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final Speiseplan that = (Speiseplan) o;

		if (!speisen.equals(that.speisen)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return speisen.hashCode();
	}

	@Override
	public String toString() {
		return "Speiseplan{" +
				"speisen=" + speisen +
				'}';
	}
}
