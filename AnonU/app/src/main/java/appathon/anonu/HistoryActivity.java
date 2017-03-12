package appathon.anonu;


import android.content.Context;

import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private static RequestQueue queue = null;
    private static String confirmationToken;
    private ArrayList<String> arrayList;
    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Bundle extras = getIntent().getExtras();
        confirmationToken = (String) extras.get("confirmation_token");
        EventBus.getDefault().register(this);

        Button homeButton = (Button) findViewById(R.id.homeButtonID);
        Button historyButton = (Button) findViewById(R.id.HistoryButtonID);
        Button schoolsButton = (Button) findViewById(R.id.SchoolsID);
        listView = (ListView) findViewById(R.id.historyListview);

        arrayList = new ArrayList<String>();
        loadOldPosts(this);

        //WHEN CLICKING HOME BUTTON
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent homeIntent = new Intent(HistoryActivity.this, MainActivity.class);
//                startActivity(homeIntent);
                finish();
                //NOTE finish will just kill this app and load the old home page up (possibility of old home page that is not updated with new post.)

            }
        });

        //WHEN CLICKING HISTORY BUTTON
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent historyIntent = new Intent(HistoryActivity.this, HistoryActivity.class);
//                startActivity(historyIntent);
               adapter.notifyDataSetChanged();
            }
        });

        //WHEN CLICKING SCHOOLS BUTTON
        schoolsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
//                Intent schoolsIntent = new Intent(HistoryActivity.this, SchoolsActivity.class);
//                startActivity(schoolsIntent);


            }
        });
        //VIEW MY POST BUTTON
        //DELETE POST BUTTON INSIDE
        //SCROLL LAYOUT WITH COMMMENTS

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);

    }

    public static void loadOldPosts(Context context){
        if( queue == null){
            queue = Volley.newRequestQueue(context);
        }
        String url ="https://anonymousuniversity.com/api/get/history.php?token=" + confirmationToken;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("BA", response);
                        ArrayList<Post> posts = new ArrayList<>();
                        try {
                            JSONObject successjsonObject = new JSONObject(response);
                            JSONArray jsonArray =  successjsonObject.getJSONArray("posts");
                            for(int index = 0; index < jsonArray.length(); index++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(index);
                                String parsedPostID = jsonObject.getString("id");
                                String parsedOwnerID = jsonObject.getString("owner_id");
                                String parsedSchoolID = jsonObject.getString("school_id");
                                String parsedDateUtc = jsonObject.getString("date_utc");
                                String parsedContent = jsonObject.getString("contents");
                                int parsedCommentCount = jsonObject.getInt("comment_count");
                                int parsedVoteCount = jsonObject.getInt("vote_count");
                                Log.d("BA", response);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PostAddedEvent postAddedEvent) {

        ArrayList<Post> postsThatWereAdded = postAddedEvent.postsAddedList;

        for(int i = 0; i < postsThatWereAdded.size(); i++){
            arrayList.add(0, postsThatWereAdded.get(i).getContents());
        }

    }

}
