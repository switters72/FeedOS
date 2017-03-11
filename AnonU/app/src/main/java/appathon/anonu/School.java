package appathon.anonu;

/**
 * Created by HomeBuild on 3/10/2017.
 */

public class School {
    String id;
    String name;
    String logo_link;

    public School(String id, String name, String logo_link) {
        this.id = id;
        this.name = name;
        this.logo_link = logo_link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo_link() {
        return logo_link;
    }

    public void setLogo_link(String logo_link) {
        this.logo_link = logo_link;
    }

    @Override
    public String toString() {
        return "School{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", logo_link='" + logo_link + '\'' +
                '}';
    }
}
