package de.uniluebeck.itm.vs1112.uebung4.employeedb;

import java.util.Set;

/**
 * This interface must not be modified.
 * <p/>
 * A simple employee database that allows basic operations (create, read (find), update, delete).
 * <p/>
 * <b>Important!!!</b>
 * The data objects returned by this interface are immutable in the sense that changes to them will not be reflected in
 * the database. In order to persist changes to an entry object always call {@link EmployeeDB#update(EmployeeDBEntry)}.
 */
public interface EmployeeDB {

	/**
	 * Returns all entries of the database.
	 *
	 * @return a set of entries or an empty set if no entries are found
	 */
	Set<EmployeeDBEntry> getEntries();

	/**
	 * Creates a new employee database entry using the given employee ID and name.
	 *
	 * @param employeeId
	 * 		the id of the employee to create
	 * @param name
	 * 		the name of the employee
	 *
	 * @return a newly created {@link EmployeeDBEntry} instance
	 *
	 * @throws EmployeeDBIdAlreadyExistsException
	 * 		if an employee with the id {@code employeeId} already exists
	 */
	EmployeeDBEntry create(long employeeId, String name) throws EmployeeDBIdAlreadyExistsException;

	/**
	 * Returns the set of entries in which {@code name} is a substring of or equals {@link EmployeeDBEntry#name}.
	 *
	 * @param name
	 * 		the name to search for
	 *
	 * @return a set of entries or an empty set if no entries are found
	 */
	Set<EmployeeDBEntry> findByName(String name);

	/**
	 * Returns the entry that has the same id as {@code employeeId}.
	 *
	 * @param employeeId
	 * 		the id of the employee
	 *
	 * @return the entry with the id {@code employeeId} or {@code null} if no employee with the ID {@code employeeId}
	 *         exists
	 */
	EmployeeDBEntry findById(long employeeId);

	/**
	 * Updates the entry that has the same id as {@code entry.getEmployeeId()} according to the data in the {@code entry}
	 * object.
	 *
	 * @param entry
	 * 		the entry to update
	 *
	 * @throws EmployeeDBUnknownIdException
	 * 		if {@code entry.getEmployeeId()} is unknown
	 */
	void update(EmployeeDBEntry entry) throws EmployeeDBUnknownIdException;

	/**
	 * Deletes the entry that has the same id as {@code employeeId}.
	 *
	 * @param employeeId
	 * 		the id of the employee
	 *
	 * @throws EmployeeDBUnknownIdException
	 * 		if {@code employeeId} is unknown
	 */
	void delete(long employeeId) throws EmployeeDBUnknownIdException;

}
