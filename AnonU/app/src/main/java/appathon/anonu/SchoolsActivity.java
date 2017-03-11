package appathon.anonu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class SchoolsActivity extends AppCompatActivity {
    ArrayList<Button> buttonList  = new ArrayList<Button>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schools);

        Button homeButton = (Button)findViewById(R.id.homeButtonID);
        Button historyButton = (Button)findViewById(R.id.HistoryButtonID);
        Button schoolsButton = (Button)findViewById(R.id.SchoolsID);

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
                finish();
                Intent historyIntent = new Intent(SchoolsActivity.this, HistoryActivity.class);
                startActivity(historyIntent);
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

        buttonList.add((Button)findViewById(R.id.btn1));
        buttonList.add((Button)findViewById(R.id.btn2));
        buttonList.add((Button)findViewById(R.id.btn3));
        buttonList.add((Button)findViewById(R.id.btn4));
        buttonList.add((Button)findViewById(R.id.btn5));
        buttonList.add((Button)findViewById(R.id.btn6));

        for (int i = 0; i < buttonList.size(); i++) {
            final int current = i;

            buttonList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    buttonList.get(movCnt).setBackgroundResource(R.drawable.mov1n);
//                    buttonList.get(current).setBackgroundResource(R.drawable.mov1o);
//                    movCnt = current;
                }
            });
        }
    }
}
