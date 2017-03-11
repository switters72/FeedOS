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

/**
 * Created by itsmebadr on 3/10/17.
 */

public class WebServicesController {

    private static RequestQueue queue = null;

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
                            JSONArray jsonArray = new JSONArray(response);
                            for(int index = 0; index < jsonArray.length(); index++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(index);
                                JSONObject successfulJSONObject = jsonObject.getJSONObject("success");
                                boolean emailConfermation = successfulJSONObject.getBoolean("success");
                                if (emailConfermation == true){

                                }
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
