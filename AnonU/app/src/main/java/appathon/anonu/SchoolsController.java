package appathon.anonu;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by itsmebadr on 3/11/17.
 */

public class SchoolsController {

    private static ArrayList<School> schoolsArray = new ArrayList<>();

    public static void addSchools(School newSchool) {
        schoolsArray.add(newSchool);
        ArrayList<School>schoolArrayList = new ArrayList<>();
        schoolArrayList.add(newSchool);
        EventBus.getDefault().post(new SchoolAddedEvent(schoolArrayList));
    }
    public static void addSchools(ArrayList<School> schoolArrayList) {
        schoolsArray.addAll(schoolsArray.size(), schoolArrayList);
        EventBus.getDefault().post(new SchoolAddedEvent(schoolArrayList));
    }
    public static void removeschools(School schoolToRemove){
        schoolsArray.remove(schoolToRemove);
        removePostHelper(schoolToRemove);
    }
    private static void removePostHelper(School schoolToRemove) {
        ArrayList<School> schoolsArrayList = new ArrayList<>();
        schoolsArrayList.add(schoolToRemove);
        EventBus.getDefault().post(new SchoolRemovedEvent(schoolsArrayList));
    }
    public static void removeSchools(int indexToRemove){
        School removedSchool = schoolsArray.remove(indexToRemove);
        removePostHelper(removedSchool);
    }
    public static ArrayList<School> getSchools() {
        return schoolsArray;
    }

}
