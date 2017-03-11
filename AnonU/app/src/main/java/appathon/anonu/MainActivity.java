package appathon.anonu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button homeButton = (Button)findViewById(R.id.homeButtonID);
        Button historyButton = (Button)findViewById(R.id.HistoryButtonID);
        Button schoolsButton = (Button)findViewById(R.id.SchoolsID);
        Button postButton = (Button)findViewById(R.id.PostButtonID);

        //WHEN CLICKING HOME BUTTON (AT HOME SCREEN)
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(homeIntent);
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


    }
}
