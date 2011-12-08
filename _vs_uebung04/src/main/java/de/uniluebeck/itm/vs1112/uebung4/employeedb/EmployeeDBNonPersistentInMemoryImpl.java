package de.uniluebeck.itm.vs1112.uebung4.employeedb;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This class must not be modified.
 */
@Singleton
public class EmployeeDBNonPersistentInMemoryImpl implements EmployeeDB {

	private final Map<Long, EmployeeDBEntry> entries = new HashMap<Long, EmployeeDBEntry>();

	@Override
	public Set<EmployeeDBEntry> getEntries() {
		return new HashSet<EmployeeDBEntry>(entries.values());
	}

	@Override
	public EmployeeDBEntry create(final long employeeId, final String name) throws EmployeeDBIdAlreadyExistsException {

		checkNotNull(name);

		synchronized (entries) {

			if (entries.containsKey(employeeId)) {
				throw new EmployeeDBIdAlreadyExistsException("An employee with the ID " + employeeId + " already exists!");
			}

			EmployeeDBEntry entry = new EmployeeDBEntry(employeeId, name);
			entries.put(employeeId, entry);

			return entry;
		}

	}

	@Override
	public Set<EmployeeDBEntry> findByName(final String name) {

		checkNotNull(name);

		Set<EmployeeDBEntry> entriesFound = new HashSet<EmployeeDBEntry>();

		synchronized (entries) {
			for (EmployeeDBEntry entry : entries.values()) {
				if (entry.getName().contains(name)) {
					entriesFound.add(new EmployeeDBEntry(entry));
				}
			}
		}

		return entriesFound;
	}

	@Override
	public EmployeeDBEntry findById(final long employeeId) {

		synchronized (entries) {
			EmployeeDBEntry entryFound = entries.get(employeeId);
			return entryFound != null ? new EmployeeDBEntry(entryFound) : null;
		}
	}

	@Override
	public void update(final EmployeeDBEntry entry) throws EmployeeDBUnknownIdException {

		checkNotNull(entry);

		synchronized (entries) {

			if (!entries.containsKey(entry.getEmployeeId())) {
				throw new EmployeeDBUnknownIdException("Employee id " + entry.getEmployeeId() + " is unknown.");
			}

			entries.put(entry.getEmployeeId(), new EmployeeDBEntry(entry));
		}
	}

	@Override
	public void delete(final long employeeId) throws EmployeeDBUnknownIdException {

		boolean removed;

		synchronized (entries) {
			removed = entries.remove(employeeId) != null;
		}

		if (!removed) {
			throw new EmployeeDBUnknownIdException("Employee id " + employeeId + " is unknown.");
		}

	}
}
