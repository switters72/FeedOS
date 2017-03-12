package appathon.anonu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static RequestQueue queue = null;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    private static EditText inputUserPost;
    private static String confirmationToken;
    private static String parsedPostID;
    private static String parsedContent;
    private static String parsedSchoolID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        confirmationToken = (String) extras.get("confirmation_token");
        EventBus.getDefault().register(this);


        final Button homeButton = (Button)findViewById(R.id.homeButtonID);
        Button historyButton = (Button)findViewById(R.id.HistoryButtonID);
        Button schoolsButton = (Button)findViewById(R.id.SchoolsID);
        Button postButton = (Button)findViewById(R.id.PostButtonID);
        inputUserPost = (EditText)findViewById(R.id.UserNewPostID);
        listView = (ListView) findViewById(R.id.listViewID);


        arrayList = new ArrayList<String>();
        loadPosts(this);

        //WHEN CLICKING HOME BUTTON (AT HOME SCREEN)
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Intent homeIntent = new Intent(MainActivity.this, MainActivity.class);
            //    startActivity(homeIntent);
                //
                adapter.notifyDataSetChanged();

            }
        });

        //WHEN CLICKING HISTORY BUTTON
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyIntent = new Intent(MainActivity.this, HistoryActivity.class);
                historyIntent.putExtra("confirmation_token", confirmationToken);
                startActivity(historyIntent);
            }
        });

        //WHEN CLICKING SCHOOLS BUTTON
        schoolsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent schoolsIntent = new Intent(MainActivity.this, SchoolsActivity.class);
                schoolsIntent.putExtra("confirmation_token", confirmationToken);
                startActivity(schoolsIntent);
            }
        });

        //WHEN CLICKING POST BUTTON
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputUserPost.getText().toString().length() != 0) {
                    arrayList.add(0, inputUserPost.getText().toString());
                    //  adapter.add(inputUserPost.getText().toString());
                    adapter.notifyDataSetChanged();
                    try {
                        postClick();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

        //WHEN CLICKING ON A POST
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + position, Toast.LENGTH_LONG)
                        .show();

            }
        });

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);


    }

    public static void loadPosts(Context context){
        if( queue == null){
            queue = Volley.newRequestQueue(context);
        }
        String url ="https://anonymousuniversity.com/api/get/home_posts.php?token=" + confirmationToken;
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
                                parsedPostID = jsonObject.getString("id");
                                String parsedOwnerID = jsonObject.getString("owner_id");
                                parsedSchoolID = jsonObject.getString("school_id");
                                String parsedDateUtc = jsonObject.getString("date_utc");
                                parsedContent = jsonObject.getString("contents");
                                int parsedCommentCount = jsonObject.getInt("comment_count");
                                Log.d("BA", jsonObject.toString());
                                int parsedVoteCount = jsonObject.getInt("vote_count");
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

    public static void sendPosts(Context context) throws UnsupportedEncodingException {
        if( queue == null){
            queue = Volley.newRequestQueue(context);
        }
        String url ="https://anonymousuniversity.com/api/set/post.php?token=" + confirmationToken + "&school_id=" + parsedSchoolID + "&contents="+ URLEncoder.encode(inputUserPost.getText().toString(), "UTF-8");
        // Request a string response from the provided URL.
        Log.d("BA", "before string request");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("BA", "in onResponse");
                        Log.d("BA", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("BA", "That didn't work!");
            }
        });
        queue.add(stringRequest);
     //   queue.start();
    }

    private void postClick() throws UnsupportedEncodingException {
        sendPosts(this);
        inputUserPost.setText("");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PostAddedEvent postAddedEvent) {

        ArrayList<Post> postsThatWereAdded = postAddedEvent.postsAddedList;

        for(int i = 0; i < postsThatWereAdded.size(); i++){
            arrayList.add(0, postsThatWereAdded.get(i).getContents());
        }

    }
}
