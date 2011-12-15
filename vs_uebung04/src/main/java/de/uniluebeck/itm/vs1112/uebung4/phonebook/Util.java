package de.uniluebeck.itm.vs1112.uebung4.phonebook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.uniluebeck.itm.vs1112.uebung4.xml.PhoneBookEntryWithUri;

public class Util {
    
    // pattern the URI in the request document must have
    private static final Pattern uriPattern = Pattern.compile("^/phonebook/([0-9]+)$");
    
    /**
     * Extracts the id in the URI element of a PhoneBookEntry.
     * @param entry the PhoneBookEntry to extract the id from
     * @return the id as Long or null if an error occurred (wrong format in URI, id not numeric, etc.)
     */
    public static Long getIDFromUriEntry(PhoneBookEntryWithUri entry) {
        Matcher result = uriPattern.matcher(entry.getUri());
        try {
            if(result.matches()) {
                return Long.parseLong(result.group(1));
            } 
            return null;
        } catch (Exception nfe) {
            return null;
        } 
    }

}
