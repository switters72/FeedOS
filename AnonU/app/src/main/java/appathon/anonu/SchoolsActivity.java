package appathon.anonu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

public class SchoolsActivity extends AppCompatActivity {
    private static ArrayList<Button> buttonList  = new ArrayList<Button>();

    private static RequestQueue queue = null;
    private static String confirmationToken;
    private static String passedSchoolID;
    private static String passedSchoolName;
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;
    private Button b6;
    private Button b7;
    private Button b8;
    private static ArrayList<School> schools;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schools);

        Bundle extras = getIntent().getExtras();
        confirmationToken = (String) extras.get("confirmation_token");

        EventBus.getDefault().register(this);



        Button homeButton = (Button)findViewById(R.id.homeButtonID);
        Button historyButton = (Button)findViewById(R.id.HistoryButtonID);
        Button schoolsButton = (Button)findViewById(R.id.SchoolsID);

        loadSchools(this);

        //WHEN CLICKING HOME BUTTON
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent homeIntent = new Intent(SchoolsActivity.this, MainActivity.class);
//                startActivity(homeIntent);
                finish();
            }
        });

        //WHEN CLICKING HISTORY BUTTON
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  finish();
//                Intent historyIntent = new Intent(SchoolsActivity.this, HistoryActivity.class);
//                startActivity(historyIntent);
            }
        });

        //WHEN CLICKING SCHOOLS BUTTON
        schoolsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent schoolsIntent = new Intent(SchoolsActivity.this, SchoolsActivity.class);
//                startActivity(schoolsIntent);
            }
        });

        b1 = (Button)findViewById(R.id.btn1);
        b2 = (Button)findViewById(R.id.btn2);
        b3 = (Button)findViewById(R.id.btn3);
        b4 = (Button)findViewById(R.id.btn4);
        b5 = (Button)findViewById(R.id.btn5);
        b6 = (Button)findViewById(R.id.btn6);
        b7 = (Button)findViewById(R.id.btn7);
        b8 = (Button)findViewById(R.id.btn8);
     /*   buttonList.add(b1);
        buttonList.add(b2);
        buttonList.add(b3);
        buttonList.add(b4);
        buttonList.add(b5);
        buttonList.add(b6);
        buttonList.add(b7);
        buttonList.add(b8); */

/*
            for(int i = 0; i < buttonList.size(); i++) {
                passedSchoolID = schools.get(i).getId();
                buttonList.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent dynamic = new Intent(SchoolsActivity.this, DynamicActivity.class);
                        dynamic.putExtra("confirmation_token", confirmationToken);
                        dynamic.putExtra("school_id", passedSchoolID);
                        startActivity(dynamic);

                    }
                });
            }
            */
    }

    public void loadSchools(Context context){
        if( queue == null){
            queue = Volley.newRequestQueue(context);
        }
        String url ="https://anonymousuniversity.com/api/get/schools.php?token=" + confirmationToken;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("BA", response);
                        schools = new ArrayList<>();
                        try {
                                JSONObject successjsonObject = new JSONObject(response);
                                JSONArray jsonArray =  successjsonObject.getJSONArray("schools");
                                for(int index = 0; index < jsonArray.length(); index++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(index);
                                    String parsedSchoolID = jsonObject.getString("id");
                                    String parsedName = jsonObject.getString("name");
                                    String parsedSchoolLogo = jsonObject.getString("logo_link");
                                    Log.d("BA", jsonObject.toString());
                                    School school = new School(parsedSchoolID, parsedName, parsedSchoolLogo);
                                    schools.add(school);
                            }
                            SchoolsController.addSchools(schools);
                            Log.d("BA", "HERE");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for(int i = 0; i < buttonList.size(); i++) {
                          //  passedSchoolID = schools.get(i).getId();
                            buttonList.get(i).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Button b = (Button)v;
                                    String str = b.getText().toString();
                                    Intent dynamic = new Intent(SchoolsActivity.this, DynamicActivity.class);
                                    for(int i = 0; i < schools.size(); i++) {
                                        if(schools.get(i).getName().equals(str)) {
                                            passedSchoolID = schools.get(i).getId();
                                            dynamic.putExtra("dynamic_confirmation_token", confirmationToken);
                                            dynamic.putExtra("dynamic_school_id", passedSchoolID);
//                                            dynamic.putExtra("dynamic_school_name", schools.get(i).getName());
                                            passedSchoolName = schools.get(i).getName();
                                            dynamic.putExtra("dynamic_school_name", passedSchoolName);

                                        }
                                    }
                                    //Intent dynamic = new Intent(SchoolsActivity.this, DynamicActivity.class);
                                    //dynamic.putExtra("dynamic_confirmation_token", confirmationToken);
                                    //dynamic.putExtra("dynamic_school_id", passedSchoolID);
                                    startActivity(dynamic);

                                }
                            });
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

    private void schoolButtonClicked(){
     //   Intent otherSchoolIntent = new Intent(SchoolsActivity.this, );
     //   startActivity(otherSchoolIntent);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SchoolAddedEvent schoolAddedEvent) {

        ArrayList<School> schoolsThatWereAdded = schoolAddedEvent.schoolsAddedList;

        /*
            b1 = (Button)findViewById(R.id.btn1);
            b2 = (Button)findViewById(R.id.btn2);
            b3 = (Button)findViewById(R.id.btn3);
            b4 = (Button)findViewById(R.id.btn4);
            b5 = (Button)findViewById(R.id.btn5);
            b6 = (Button)findViewById(R.id.btn6);
            b7 = (Button)findViewById(R.id.btn7);
            b8 = (Button)findViewById(R.id.btn8);

*/
            if(schoolsThatWereAdded.get(0) != null) {
                b1.setText(schoolsThatWereAdded.get(0).getName());
                buttonList.add(b1);
            }
        if(schoolsThatWereAdded.get(1) != null) {
            b2.setText(schoolsThatWereAdded.get(1).getName());
            buttonList.add(b2);
        }
        if(schoolsThatWereAdded.get(2) != null) {
            b3.setText(schoolsThatWereAdded.get(2).getName());
            buttonList.add(b3);
        }
        if(schoolsThatWereAdded.get(3) != null) {
            b4.setText(schoolsThatWereAdded.get(3).getName());
            buttonList.add(b4);
        }
        if(schoolsThatWereAdded.get(4) != null) {
            b5.setText(schoolsThatWereAdded.get(4).getName());
            buttonList.add(b5);
        }
        if(schoolsThatWereAdded.get(5) != null) {
            b6.setText(schoolsThatWereAdded.get(5).getName());
            buttonList.add(b6);
        }
        if(schoolsThatWereAdded.get(6) != null) {
            b7.setText(schoolsThatWereAdded.get(6).getName());
            buttonList.add(b7);
        }
        if(schoolsThatWereAdded.get(7) != null){
                b8.setText(schoolsThatWereAdded.get(7).getName());
        buttonList.add(b8);
            }

    }

    }
