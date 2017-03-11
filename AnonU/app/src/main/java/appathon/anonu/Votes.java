package appathon.anonu;

/**
 * Created by HomeBuild on 3/10/2017.
 */

public class Votes {
    String id;
    String owner_id;
    String resource_type;
    String resource_id;
    String vote;

    public Votes(String id, String owner_id, String resource_type, String resource_id, String vote) {
        this.id = id;
        this.owner_id = owner_id;
        this.resource_type = resource_type;
        this.resource_id = resource_id;
        this.vote = vote;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getResource_type() {
        return resource_type;
    }

    public void setResource_type(String resource_type) {
        this.resource_type = resource_type;
    }

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    @Override
    public String toString() {
        return "Votes{" +
                "id='" + id + '\'' +
                ", owner_id='" + owner_id + '\'' +
                ", resource_type='" + resource_type + '\'' +
                ", resource_id='" + resource_id + '\'' +
                ", vote='" + vote + '\'' +
                '}';
    }
}
