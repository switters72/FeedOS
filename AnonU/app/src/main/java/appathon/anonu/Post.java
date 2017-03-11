package appathon.anonu;

/**
 * Created by HomeBuild on 3/10/2017.
 */
public class Post {
    String id;
    String owner_id;
    String school_id;
    String date_utc;
    String contents;
    int comment_count;

    public Post(String id, String owner_id, String school_id, String date_utc, String contents) {
        this.id = id;
        this.owner_id = owner_id;
        this.school_id = school_id;
        this.date_utc = date_utc;
        this.contents = contents;
        this.comment_count = 0;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", owner_id='" + owner_id + '\'' +
                ", school_id='" + school_id + '\'' +
                ", date_utc='" + date_utc + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public void setDate_utc(String date_utc) {
        this.date_utc = date_utc;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public String getId() {
        return id;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public String getSchool_id() {
        return school_id;
    }

    public String getDate_utc() {
        return date_utc;
    }

    public String getContents() {
        return contents;
    }

    public int getComment_count() {
        return comment_count;
    }
}
