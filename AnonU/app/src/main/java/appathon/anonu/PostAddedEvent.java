package appathon.anonu;

import java.util.ArrayList;

/**
 * Created by itsmebadr on 3/11/17.
 */

public class PostAddedEvent {

    public ArrayList<Post> postsAddedList;

    public PostAddedEvent(ArrayList<Post> postsAddedList) {
        this.postsAddedList = postsAddedList;
    }
}
