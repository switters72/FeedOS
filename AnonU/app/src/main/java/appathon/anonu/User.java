package appathon.anonu;

/**
 * Created by HomeBuild on 3/10/2017.
 */

public class User {
    String id;
    String email;
    String score;
    String token;

    public User(String id, String email, String score, String token) {
        this.id = id;
        this.email = email;
        this.score = score;
        this.token = token;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getScore() {
        return score;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", score='" + score + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
