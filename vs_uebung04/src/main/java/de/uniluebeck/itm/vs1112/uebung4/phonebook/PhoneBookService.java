package de.uniluebeck.itm.vs1112.uebung4.phonebook;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

import de.uniluebeck.itm.vs1112.uebung4.employeedb.EmployeeDB;
import de.uniluebeck.itm.vs1112.uebung4.employeedb.EmployeeDBEntry;
import de.uniluebeck.itm.vs1112.uebung4.employeedb.EmployeeDBIdAlreadyExistsException;
import de.uniluebeck.itm.vs1112.uebung4.employeedb.EmployeeDBUnknownIdException;
import de.uniluebeck.itm.vs1112.uebung4.xml.PhoneBookEntry;
import de.uniluebeck.itm.vs1112.uebung4.xml.PhoneBookEntryList;
import de.uniluebeck.itm.vs1112.uebung4.xml.PhoneBookEntryWithUri;

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

	/**
	 * Returns all phone book entries.
	 * @param name optional Parameter (null or an empty string) to filter returned phone book entries.
	 * @return If name is omitted (null or an empty string) returns all phone book entries. 
	 * If present only returns phone book entries that have a name of which name is a substring.
	 */
	@GET
	@Produces("application/xml")
	public Response getPhonebook(
	        @DefaultValue("") @QueryParam("name") final String name
	) {
	    Collection<EmployeeDBEntry> entries = employeeDB.getEntries();
	    
	    // filter will only be done if name is not empty
	    if(name != null && !name.isEmpty()) {
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
	    
	    
	    
	    PhoneBookEntryList respData = new PhoneBookEntryList();
	    
	    for (EmployeeDBEntry dbEntry : entries) {
	        String number = dbEntry.getPhoneNumber();
	        
	        // entries are only in the phone book if they have a number
	        if(number != null && !number.isEmpty()) {
    	        PhoneBookEntry pbe = new PhoneBookEntry();
    	        pbe.setName(dbEntry.getName());
    	        pbe.setNumber(dbEntry.getPhoneNumber());
    	        
    	        PhoneBookEntryWithUri pbu = new PhoneBookEntryWithUri();
    	        pbu.setPhoneBookEntry(pbe);
    	        pbu.setUri("/phonebook/"+dbEntry.getEmployeeId());
    	        respData.getPhoneBookEntryWithUri().add(pbu);
	        }
        }
	    if(respData.getPhoneBookEntryWithUri().size() == 0) {
            return Response.noContent().build();
        } else {
            return Response.ok(respData).build();
        }
	}
	
	/**
	 * Creates or replaces the complete phone book resource. The payload of the request must contain all entries 
	 * of the phone book.
	 * @param newEntryList the phone book to set
	 * @return all phone book entries
	 * @throws EmployeeDBUnknownIdExceptioncan can not happen since we modify only existing entries
	 * @throws EmployeeDBIdAlreadyExistsException can not happen since we assure it is there
	 */
	@PUT
    @Consumes("application/xml")
    @Produces("application/xml")
    public Response putPhonebook(PhoneBookEntryList newEntryList) throws EmployeeDBUnknownIdException, EmployeeDBIdAlreadyExistsException {
	    // will store all entries in the request with there ids as key
	    HashMap<Long, PhoneBookEntryWithUri> entriesInRequest = new HashMap<Long, PhoneBookEntryWithUri>();
	    
	    for (PhoneBookEntryWithUri newEntry : newEntryList.getPhoneBookEntryWithUri()) {
	        Long id = Util.getIDFromUriEntry(newEntry);
            if(id != null) {
                entriesInRequest.put(id, newEntry);
            }
            // according to mailinglist bad uris in request should be treated as bad request
            else {
                return Response.status(Status.BAD_REQUEST)
                        .entity("Uri of item has invalid format: "+newEntry.getUri())
                        .build();
            }
        }
	    
	    /* loop over all entries in db and set there phonenumbers to an empty string to clear
	     * all phonebook entries */
	    Collection<EmployeeDBEntry> dbEntries = employeeDB.getEntries();
	    for (EmployeeDBEntry dbEntry : dbEntries) {
            dbEntry.setPhoneNumber("");
            employeeDB.update(dbEntry);
        }
	    
	    /* for alle entries in the request document, set the values in the db to the data
	     * in the document entry */
	    for (Entry<Long, PhoneBookEntryWithUri> requestEntry : entriesInRequest.entrySet()) {
	        EmployeeDBEntry entryInDb = employeeDB.findById(requestEntry.getKey());
	        PhoneBookEntry phoneBookentry = requestEntry.getValue().getPhoneBookEntry();
	        
	        if(entryInDb == null) {
	            entryInDb = employeeDB.create(requestEntry.getKey(), phoneBookentry.getName());
	            entryInDb.setPhoneNumber(phoneBookentry.getNumber());
	        } else {
	            entryInDb.setName(phoneBookentry.getName());
	            entryInDb.setPhoneNumber(phoneBookentry.getNumber());
	        }
	        employeeDB.update(entryInDb);
        }
	    
	    // reuse getPhonebook to return db content 
	    return this.getPhonebook("");
	}
	
	/**
	 * Creates or updates all entries contained in updateEntryList.
	 * @param updateEntryList phone book entries to update or created
	 * @return all entries of the phone book
	 * @throws EmployeeDBIdAlreadyExistsException can not happen since we insert only non existing
	 * @throws EmployeeDBUnknownIdException can not happen since we assure it is there
	 */
	@POST
    @Consumes("application/xml")
    @Produces("application/xml")
    public Response postPhonebook(PhoneBookEntryList updateEntryList) throws EmployeeDBIdAlreadyExistsException, EmployeeDBUnknownIdException {
	    
	    for (PhoneBookEntryWithUri updateEntry : updateEntryList.getPhoneBookEntryWithUri()) {
	        Long id = Util.getIDFromUriEntry(updateEntry);
            if(id != null) {
                EmployeeDBEntry entryInDb = employeeDB.findById(id);
                if(entryInDb == null) {
                    entryInDb = employeeDB.create(id, updateEntry.getPhoneBookEntry().getName());
                    entryInDb.setPhoneNumber(updateEntry.getPhoneBookEntry().getNumber());
                } else {
                    entryInDb.setName(updateEntry.getPhoneBookEntry().getName());
                    entryInDb.setPhoneNumber(updateEntry.getPhoneBookEntry().getNumber());
                }
                employeeDB.update(entryInDb);
                //TODO
            }
            // according to mailinglist bad uris in request should be treated as bad request
            else {
                return Response.status(Status.BAD_REQUEST)
                        .entity("Uri of item has invalid format: "+updateEntry.getUri())
                        .build();
            }
	        
	    }

	    return this.getPhonebook("");
	}
	
	/**
	 * Deletes all phone book entries.
	 * @return 204 No Content
	 * @throws EmployeeDBUnknownIdException can not happen, since we assure it is there
	 */
	@DELETE
    @Consumes("application/xml")
    public Response deletePhonebook() throws EmployeeDBUnknownIdException {
	    for (EmployeeDBEntry dbEntry : employeeDB.getEntries()) {
            dbEntry.setPhoneNumber("");
            employeeDB.update(dbEntry);
        }
	    return this.getPhonebook("");
	}
	
	/**
	 * Returns a phone book entry.
	 * @param id id of the phone book entry
	 * @return the entry if it exists, otherwise 204 No Content
	 */
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
	
	/**
	 * Creates or updates the entry with the given ID according to the newEntry parameter.
	 * @param id id of the resource to update
	 * @param newEntry data to set for this resource
	 * @return the new data of the resource
	 * @throws EmployeeDBUnknownIdException can not happen since we assure it is there
	 * @throws EmployeeDBIdAlreadyExistsException can not happen because we knoe it is not there
	 */
	@PUT
    @Path("/{id: [0-9]+}")
	@Consumes("application/xml")
    @Produces("application/xml")
    public Response putEntry(
            @PathParam("id") int id,
            PhoneBookEntry newEntry
    ) throws EmployeeDBUnknownIdException, EmployeeDBIdAlreadyExistsException { 
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
        
        // we use the normal get view to return the actual data in the db
        return Response.status(status).entity(this.getEntry(id).getEntity()).build();
    }
	
	/**
	 * Deletes the phone book entry of the employee with ID.
	 * @param id resource to delete
	 * @return 204 No Content
	 * @throws EmployeeDBUnknownIdException can not happen since we assure it is there
	 */
	@DELETE
	@Path("/{id: [0-9]+}")
    public Response deleteEntry(@PathParam("id") int id) throws EmployeeDBUnknownIdException {
	    EmployeeDBEntry employee = employeeDB.findById(id);
	    if(employee != null) {
	        employee.setPhoneNumber(null);
	        employeeDB.update(employee);
	    }
	    return this.getEntry(id);
	}

}
