package appathon.anonu;

import java.util.ArrayList;

/**
 * Created by itsmebadr on 3/11/17.
 */

public class PostRemovedEvent {


        public PostRemovedEvent(ArrayList<Post> removedPosts) {
            this.removedPosts = removedPosts;
        }
        public ArrayList<Post>removedPosts;
}
