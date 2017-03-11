package appathon.anonu;

import java.util.ArrayList;

/**
 * Created by itsmebadr on 3/11/17.
 */

public class SchoolRemovedEvent {

    public SchoolRemovedEvent(ArrayList<School> removedSchools) {
        this.removedSchools = removedSchools;
    }
    public ArrayList<School>removedSchools;
}
