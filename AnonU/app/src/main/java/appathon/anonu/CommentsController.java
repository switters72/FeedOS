package appathon.anonu;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by itsmebadr on 3/11/17.
 */

public class CommentsController {

    private static ArrayList<Comment> commentsArray = new ArrayList<>();

    public static void addComments(Comment newComment) {
        commentsArray.add(newComment);
        ArrayList<Comment>commentArrayList = new ArrayList<>();
        commentArrayList.add(newComment);
        EventBus.getDefault().post(new CommentAddedEvent(commentArrayList));
    }
    public static void addComments(ArrayList<Comment> commentArrayList) {
        commentsArray.addAll(commentsArray.size(), commentArrayList);
        EventBus.getDefault().post(new CommentAddedEvent(commentArrayList));
    }
    public static void removeComments(Comment commentToRemove){
        commentsArray.remove(commentToRemove);
        removeCommentHelper(commentToRemove);
    }
    private static void removeCommentHelper(Comment commentToRemove) {
        ArrayList<Comment> commentsArrayList = new ArrayList<>();
        commentsArrayList.add(commentToRemove);
        EventBus.getDefault().post(new CommentRemovedEvent(commentsArrayList));
    }
    public static void removeComments(int indexToRemove){
        Comment removedComment = commentsArray.remove(indexToRemove);
        removeCommentHelper(removedComment);
    }
    public static ArrayList<Comment> getPosts() {
        return commentsArray;
    }
}
