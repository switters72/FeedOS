package appathon.anonu;

/**
 * Created by HomeBuild on 3/10/2017.
 */

public class User {
    String id;
    int score;
    String token;

    public User(String id, int score, String token) {
        this.id = id;
        this.score = score;
        this.token = token;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", score='" + score + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
