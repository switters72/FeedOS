package appathon.anonu;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by itsmebadr on 3/10/17.
 */

public class WebServicesController {

    private static RequestQueue queue = null;
    private static String confirmationToken;
    private static boolean emailConfirmation = false;
    private static String parsedPostID;
    private static User user;
/*
    public static void verifyEmail(Context context, String email){
        if( queue == null){
            queue = Volley.newRequestQueue(context);
        }
        String url ="https://anonymousuniversity.com/api/set/users.php?email=" + email;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("BA", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                                emailConfirmation = jsonObject.getBoolean("successful");
                                String parsedID = jsonObject.getString("id");
                                confirmationToken = jsonObject.getString("token");
                                int parsedScore = jsonObject.getInt("score");
                                user = new User(parsedID, parsedScore, confirmationToken);
                            Log.d("BA", "HERE");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("BA", "That didn't work!");
            }
        });
        queue.add(stringRequest);
        queue.start();
    }
*/
    public User getUser(){
        return user;
    }

    public static boolean isEmailConfirmation() {
        return emailConfirmation;
    }

    public static void loadPosts(Context context){
        if( queue == null){
            queue = Volley.newRequestQueue(context);
        }
        String url ="https://anonymousuniversity.com/api/get/home_posts.php?token=" + emailConfirmation;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("BA", response);
                        ArrayList<Post> posts = new ArrayList<>();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int index = 0; index < jsonArray.length(); index++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(index);
                                JSONObject successfulJSONObject = jsonObject.getJSONObject("successful");
                                boolean successPosts = successfulJSONObject.getBoolean("successful");
                                parsedPostID = successfulJSONObject.getString("id");
                                String parsedOwnerID = successfulJSONObject.getString("owner_id");
                                String parsedSchoolID = successfulJSONObject.getString("school_id");
                                String parsedDateUtc = successfulJSONObject.getString("date_utc");
                                String parsedContent = successfulJSONObject.getString("contents");
                                int parsedCommentCount = successfulJSONObject.getInt("comment_count");
                                int parsedVoteCount = successfulJSONObject.getInt("vote_count");
                                Post post = new Post(parsedPostID, parsedOwnerID, parsedSchoolID, parsedDateUtc, parsedContent, parsedCommentCount, parsedVoteCount);
                                posts.add(post);
                            }
                            PostsController.addPosts(posts);
                            Log.d("BA", "HERE");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("BA", "That didn't work!");
            }
        });
        queue.add(stringRequest);
        queue.start();
    }
    public static void loadComments(Context context){
        if( queue == null){
            queue = Volley.newRequestQueue(context);
        }
        String url ="https://anonymousuniversity.com/api/get/comments.php?token=" + emailConfirmation + "&post_id=" + parsedPostID;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("BA", response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int index = 0; index < jsonArray.length(); index++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(index);
                                JSONObject successfulJSONObject = jsonObject.getJSONObject("successful");
                                boolean successPosts = successfulJSONObject.getBoolean("successful");
                                parsedPostID = successfulJSONObject.getString("id");
                                String parsedOwnerID = successfulJSONObject.getString("owner_id");
                                String parsedSchoolID = successfulJSONObject.getString("school_id");
                                String parsedDateUtc = successfulJSONObject.getString("date_utc");
                                String parsedContent = successfulJSONObject.getString("contents");
                                int parsedCommentCount = successfulJSONObject.getInt("comment_count");
                                int parsedVoteCount = successfulJSONObject.getInt("vote_count");
                            }
                            Log.d("BA", "HERE");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("BA", "That didn't work!");
            }
        });
        queue.add(stringRequest);
        queue.start();
    }

    public static void loadSchools(Context context){
        if( queue == null){
            queue = Volley.newRequestQueue(context);
        }
        String url ="https://anonymousuniversity.com/api/get/schools.php?token=" + emailConfirmation;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("BA", response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int index = 0; index < jsonArray.length(); index++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(index);
                                JSONObject successfulJSONObject = jsonObject.getJSONObject("successful");
                                emailConfirmation = successfulJSONObject.getBoolean("successful");
                                String parsedID = successfulJSONObject.getString("id");
                                confirmationToken = successfulJSONObject.getString("token");
                                int parsedScore = successfulJSONObject.getInt("score");
                                user = new User(parsedID, parsedScore, confirmationToken);
                            }
                            Log.d("BA", "HERE");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("BA", "That didn't work!");
            }
        });
        queue.add(stringRequest);
        queue.start();
    }

}
