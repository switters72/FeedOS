package appathon.anonu;

/**
 * Created by HomeBuild on 3/10/2017.
 */

public class Comment {
    String id;
    String post_id;
    String owner_id;
    String date_utc;
    String contents;

    public Comment(String id, String post_id, String owner_id, String date_utc, String contents) {
        this.id = id;
        this.post_id = post_id;
        this.owner_id = owner_id;
        this.date_utc = date_utc;
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", post_id='" + post_id + '\'' +
                ", owner_id='" + owner_id + '\'' +
                ", date_utc='" + date_utc + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public void setDate_utc(String date_utc) {
        this.date_utc = date_utc;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getId() {
        return id;
    }

    public String getPost_id() {
        return post_id;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public String getDate_utc() {
        return date_utc;
    }

    public String getContents() {
        return contents;
    }
}
