package appathon.anonu;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by itsmebadr on 3/11/17.
 */

public class PostsController {

    private static ArrayList<Post> postsArray = new ArrayList<>();

    public static void addPosts(Post newPost) {
        postsArray.add(newPost);
        ArrayList<Post>postArrayList = new ArrayList<>();
        postArrayList.add(newPost);
        EventBus.getDefault().post(new PostAddedEvent(postArrayList));
    }
    public static void addPosts(ArrayList<Post> postArrayList) {
        postsArray.addAll(postsArray.size(), postArrayList);
        EventBus.getDefault().post(new PostAddedEvent(postArrayList));
    }
    public static void removePosts(Post postToRemove){
        postsArray.remove(postToRemove);
        removePostHelper(postToRemove);
    }
    private static void removePostHelper(Post postToRemove) {
        ArrayList<Post> postsArrayList = new ArrayList<>();
        postsArrayList.add(postToRemove);
        EventBus.getDefault().post(new PostRemovedEvent(postsArrayList));
    }
    public static void removePosts(int indexToRemove){
        Post removedBuilding = postsArray.remove(indexToRemove);
        removePostHelper(removedBuilding);
    }
    public static ArrayList<Post> getPosts() {
        return postsArray;
    }
}

