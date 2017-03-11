package appathon.anonu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Button homeButton = (Button) findViewById(R.id.homeButtonID);
        Button historyButton = (Button) findViewById(R.id.HistoryButtonID);
        Button schoolsButton = (Button) findViewById(R.id.SchoolsID);

        //WHEN CLICKING HOME BUTTON
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent homeIntent = new Intent(HistoryActivity.this, MainActivity.class);
//                startActivity(homeIntent);
                finish();
            }
        });

        //WHEN CLICKING HISTORY BUTTON
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent historyIntent = new Intent(HistoryActivity.this, HistoryActivity.class);
//                startActivity(historyIntent);
            }
        });

        //WHEN CLICKING SCHOOLS BUTTON
        schoolsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent schoolsIntent = new Intent(HistoryActivity.this, SchoolsActivity.class);
                startActivity(schoolsIntent);


            }
        });
        //VIEW MY POST BUTTON
        //DELETE POST BUTTON INSIDE
        //SCROLL LAYOUT WITH COMMMENTS

    }

}
