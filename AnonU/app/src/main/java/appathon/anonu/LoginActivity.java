package appathon.anonu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private static RequestQueue queue = null;
    private static String confirmationToken;
    private static boolean emailConfirmation = false;
    private static User user;

    private TextView email;
    private EditText addEmail;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (TextView)findViewById(R.id.email);
        addEmail = (EditText)findViewById(R.id.enterEmail);


        login = (Button)findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginClick();
            }
        });

    }

    public void verifyEmail(Context context, String email){
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
                            if(emailConfirmation == true) {
                                Log.d("BA", "inside check is true");
                                Intent homeIntent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(homeIntent);
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

    private void loginClick() {

        verifyEmail(this, addEmail.getText().toString());

     /*   Log.d("BA", "outside of check");

        if(emailConfirmation == true) {
            Log.d("BA", "inside check is true");
            Intent homeIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(homeIntent);
        } */

    }
}
