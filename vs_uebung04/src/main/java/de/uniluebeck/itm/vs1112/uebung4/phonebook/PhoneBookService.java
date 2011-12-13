package de.uniluebeck.itm.vs1112.uebung4.phonebook;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.uniluebeck.itm.vs1112.uebung4.employeedb.EmployeeDB;
import de.uniluebeck.itm.vs1112.uebung4.employeedb.EmployeeDBEntry;
import de.uniluebeck.itm.vs1112.uebung4.employeedb.EmployeeDBIdAlreadyExistsException;
import de.uniluebeck.itm.vs1112.uebung4.employeedb.EmployeeDBUnknownIdException;
import de.uniluebeck.itm.vs1112.uebung4.xml.PhoneBookEntry;
import de.uniluebeck.itm.vs1112.uebung4.xml.PhoneBookEntryList;
import de.uniluebeck.itm.vs1112.uebung4.xml.PhoneBookEntryWithUri;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * Skeleton class to implement the phone book REST service specification.
 */
@Path("/phonebook")
public class PhoneBookService {
    
    private final Logger log = Logger.getLogger(this.getClass().getCanonicalName());

	/**
	 * Reference to the legacy employee DB. Will be set by the framework upon program (or unit-test) startup.
	 */
	@Inject
	private EmployeeDB employeeDB;

	@GET
	@Produces("application/xml")
	public Response getPhonebook(
	        @DefaultValue("") @QueryParam("name") final String name
	) throws EmployeeDBIdAlreadyExistsException, EmployeeDBUnknownIdException {
	    Collection<EmployeeDBEntry> entries = employeeDB.getEntries();
	    
	    // filter will only be done if name is not empty
	    if(!name.isEmpty()) {
	        // find all entries that match the name
	        Iterable<EmployeeDBEntry> containingName = Iterables.filter(entries, new Predicate<EmployeeDBEntry>() {
                @Override
                public boolean apply(EmployeeDBEntry entry) {
                    return entry.getName().contains(name);
                }
            });
	        // replace entrylist with filtered items
	        entries = new HashSet<EmployeeDBEntry>();
	        Iterables.addAll(entries, containingName);
	    }
	    
	    if(entries.size() == 0) {
	        return Response.noContent().build();
	    }
	    
	    PhoneBookEntryList respData = new PhoneBookEntryList();
	    
	    for (EmployeeDBEntry dbEntry : entries) {
	        PhoneBookEntry pbe = new PhoneBookEntry();
	        pbe.setName(dbEntry.getName());
	        pbe.setNumber(dbEntry.getPhoneNumber());
	        
	        PhoneBookEntryWithUri pbu = new PhoneBookEntryWithUri();
	        pbu.setPhoneBookEntry(pbe);
	        pbu.setUri("/phonebook/"+dbEntry.getEmployeeId());
	        respData.getPhoneBookEntryWithUri().add(pbu);
        }
	    
	    return Response.ok(respData).build();
	}
	
	@GET
	@Path("/{id: [0-9]+}")
	@Produces("application/xml")
	public Response getEntry(@PathParam("id") int id) {
	    log.debug("GET phonebook entry with id "+id);
	    EmployeeDBEntry employee = employeeDB.findById(id);
	    if(employee == null || employee.getPhoneNumber() == null || employee.getPhoneNumber().isEmpty()) {
	        log.debug("phonebook entry with id "+id+" wasn't found:"+ employee);
	        return Response.noContent().build();
	    } else {
	        PhoneBookEntry pbe = new PhoneBookEntry();
	        pbe.setName(employee.getName());
	        pbe.setNumber(employee.getPhoneNumber());
	        log.debug("phonebook entry with id "+id+" found:\n"+pbe);
	        return Response.ok(pbe).build();
	    }
	}
	
	@PUT
    @Path("/{id: [0-9]+}")
	@Consumes("application/xml")
    @Produces("application/xml")
    public Response putEntry(
            @PathParam("id") int id,
            PhoneBookEntry newEntry
    ) throws EmployeeDBUnknownIdException, // can only happen if employeeDB is not thread save
    EmployeeDBIdAlreadyExistsException, URISyntaxException {   // should not happen in this universe 
        log.debug("PUT phonebook entry with id "+id+" data:\n"+newEntry);
	    
        EmployeeDBEntry employee = employeeDB.findById(id);
        Status status = null;
        
        // employee doesn't exist -> create it
        if(employee == null) {
            employee = employeeDB.create(id, newEntry.getName());
            status = Status.CREATED;
            log.debug("Employee not in DB. Was created");
        }
        // employee is already in db -> update
        else {
            employee.setName(newEntry.getName());
            status = Status.OK;
            log.debug("Employee in DB. Was updated");
        }
        employee.setPhoneNumber(newEntry.getNumber());
        employeeDB.update(employee);
        return Response.status(status).entity(newEntry).build();
    }
	
	
	

	
	

}
