package appathon.anonu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    EditText inputUserPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button homeButton = (Button)findViewById(R.id.homeButtonID);
        Button historyButton = (Button)findViewById(R.id.HistoryButtonID);
        Button schoolsButton = (Button)findViewById(R.id.SchoolsID);
        Button postButton = (Button)findViewById(R.id.PostButtonID);
        inputUserPost = (EditText)findViewById(R.id.UserNewPostID);
        listView = (ListView) findViewById(R.id.listViewID);

        arrayList = new ArrayList<String>();

        //WHEN CLICKING HOME BUTTON (AT HOME SCREEN)
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Intent homeIntent = new Intent(MainActivity.this, MainActivity.class);
            //    startActivity(homeIntent);
                adapter.notifyDataSetChanged();

            }
        });

        //WHEN CLICKING HISTORY BUTTON
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyIntent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(historyIntent);
            }
        });

        //WHEN CLICKING SCHOOLS BUTTON
        schoolsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent schoolsIntent = new Intent(MainActivity.this, SchoolsActivity.class);
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
//        listView.addView(inputUserPost);
        arrayList.add("test post");
        arrayList.add("Anotha one");
        arrayList.add("uno mas");
        arrayList.add("omg");

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);


    }
}
