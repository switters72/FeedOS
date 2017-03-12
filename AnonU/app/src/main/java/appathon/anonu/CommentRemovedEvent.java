package appathon.anonu;

import java.util.ArrayList;

/**
 * Created by itsmebadr on 3/11/17.
 */

public class CommentRemovedEvent {

    public CommentRemovedEvent(ArrayList<Comment> removedComments) {
        this.removedComments = removedComments;
    }
    public ArrayList<Comment>removedComments;
}
