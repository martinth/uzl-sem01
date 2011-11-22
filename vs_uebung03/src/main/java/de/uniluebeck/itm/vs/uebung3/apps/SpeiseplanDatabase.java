package de.uniluebeck.itm.vs.uebung3.apps;

import de.uniluebeck.itm.vs.uebung3.types.Speise;
import de.uniluebeck.itm.vs.uebung3.types.Speiseplan;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class must not be modified. It is just a very simple Database
 */
public class SpeiseplanDatabase {
    public static Speiseplan getSpeiseplan(int dayOfYear){
        Collection<Speise> speisen = new ArrayList<Speise>();
        if(dayOfYear % 2 == 0){
            speisen.add(new Speise("Mettigel Hawaii", 250));
            speisen.add(new Speise("Mettigel Zigeuner Art", 210));
        }
        else{
            speisen.add(new Speise("Mettigel mit Fruechten", 249));
            speisen.add(new Speise("Nachtisch: Mettigel-Schokolade (http://joghurtmitgraeten.blogspot.com/2011/07/ritter-sport-mettigel-die-spezialsorten.html)", 199));
        }

        return new Speiseplan(speisen);
    }
}
