package de.uniluebeck.itm.vs1112.uebung4;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import de.uniluebeck.itm.vs1112.uebung4.employeedb.EmployeeDB;
import org.junit.Test;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import static org.junit.Assert.assertEquals;

/**
 * Test case example that runs tests against the phone book REST service.
 */
public class PhoneBookServiceTest extends GuiceAndJerseyTest {

	/**
	 * Reference to the same instance of {@link EmployeeDB} that is available in the phone REST service implementation
	 * {@link de.uniluebeck.itm.vs1112.uebung4.phonebook.PhoneBookService}. Can be used to set the database to a certain
	 * state before executing further test logic. For every test method this reference points to a freshly instantiated
	 * implementation of {@link EmployeeDB}.
	 */
	@Inject
	private EmployeeDB employeeDB;

	@Test
	public void testGetOnPhoneBookShouldReturn204IfEmpty() throws Exception {

		WebResource.Builder requestBuilder = client()
				.resource(UriBuilder.fromPath("/phonebook").build())
				.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML);

		ClientResponse response = requestBuilder.get(ClientResponse.class);
		assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}
}
