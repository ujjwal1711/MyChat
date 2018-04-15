package mychat.com.chat.app.mychat.Activities;

import android.content.Intent;
import android.net.ParseException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseUser;

import mychat.com.chat.app.mychat.R;

public class LoginActivity extends AppCompatActivity {
    private Button signInButton;
    private EditText userName;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signInButton = (Button) findViewById(R.id.signInId);
        userName = (EditText) findViewById(R.id.usernameId);
        password = (EditText) findViewById(R.id.passwordId);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uName = userName.getText().toString();
                String pWord = password.getText().toString();

                if (!uName.equals("") || !pWord.equals("")){
                ParseUser.logInInBackground(uName, pWord, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, com.parse.ParseException e) {
                        if(e==null)
                        {


                            Toast.makeText(getApplicationContext(), "Login Successfully!"
                                    , Toast.LENGTH_LONG).show();

                            startActivity(new Intent(LoginActivity.this, Chattactivity.class));


                        }else{

                            Toast.makeText(getApplicationContext(), "Not logged in",
                                    Toast.LENGTH_LONG).show();

                        }


                    }
                });


                }else{

                    Toast.makeText(getApplicationContext(), "Please enter username and Password",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

    }

}