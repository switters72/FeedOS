package appathon.anonu;

import java.util.ArrayList;

/**
 * Created by itsmebadr on 3/11/17.
 */

public class CommentAddedEvent {
    public ArrayList<Comment> commentsAddedList;

    public CommentAddedEvent(ArrayList<Comment> postsAddedList) {

        this.commentsAddedList = commentsAddedList;
    }

}
