package appathon.anonu;

import java.util.ArrayList;

/**
 * Created by itsmebadr on 3/11/17.
 */

public class SchoolAddedEvent {

    public ArrayList<School> schoolsAddedList;

    public SchoolAddedEvent(ArrayList<School> schoolsAddedList) {

        this.schoolsAddedList = schoolsAddedList;
    }
}
