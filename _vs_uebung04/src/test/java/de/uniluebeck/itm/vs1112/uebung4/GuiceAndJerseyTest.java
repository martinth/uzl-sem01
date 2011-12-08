package de.uniluebeck.itm.vs1112.uebung4;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.LowLevelAppDescriptor;
import de.uniluebeck.itm.vs1112.uebung4.phonebook.PhoneBookService;

/**
 * This class must not be modified.
 */
public abstract class GuiceAndJerseyTest extends JerseyTest {

	@Override
	protected AppDescriptor configure() {

		Injector injector = Guice.createInjector(new PhoneBookRESTServerModule());
		injector.injectMembers(this);

		setTestContainerFactory(new GuiceInMemoryTestContainerFactory(injector));

		return new LowLevelAppDescriptor.Builder(PhoneBookService.class.getPackage().getName()).build();
	}
}