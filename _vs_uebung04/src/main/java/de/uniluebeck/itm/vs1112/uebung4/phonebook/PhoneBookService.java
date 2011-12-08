package de.uniluebeck.itm.vs1112.uebung4.phonebook;

import de.uniluebeck.itm.vs1112.uebung4.employeedb.EmployeeDB;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Skeleton class to implement the phone book REST service specification.
 */
@Path("/phonebook")
public class PhoneBookService {

	/**
	 * Reference to the legacy employee DB. Will be set by the framework upon program (or unit-test) startup.
	 */
	@Inject
	private EmployeeDB employeeDB;

	@GET
	public Response get() {
		// TODO implement
		return null;
	}

}
