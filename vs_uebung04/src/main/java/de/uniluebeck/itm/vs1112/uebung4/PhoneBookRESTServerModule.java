package de.uniluebeck.itm.vs1112.uebung4;

import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import de.uniluebeck.itm.vs1112.uebung4.employeedb.EmployeeDB;
import de.uniluebeck.itm.vs1112.uebung4.employeedb.EmployeeDBNonPersistentInMemoryImpl;
import de.uniluebeck.itm.vs1112.uebung4.phonebook.PhoneBookService;

/**
 * This class must not be modified.
 * <p/>
 * Configuration class to set up Google Guice-based dependency injection with the Jersey JAX-RS implementation.
 * For more information please check http://jersey.java.net/nonav/apidocs/latest/contribs/jersey-guice/com/sun/jersey/guice/spi/container/servlet/package-summary.html.
 */
public class PhoneBookRESTServerModule extends JerseyServletModule {

	@Override
	protected void configureServlets() {
		bind(PhoneBookService.class);
		bind(EmployeeDB.class).to(EmployeeDBNonPersistentInMemoryImpl.class);
		serve("/*").with(GuiceContainer.class);
	}
}
