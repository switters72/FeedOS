package appathon.anonu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

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

    private void loginClick() {
        WebServicesController.verifyEmail(this, addEmail.getText().toString());
       // if(WebServicesController.isEmailConfirmation() == true) {
            Intent homeIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(homeIntent);
      //  }
    }
}
