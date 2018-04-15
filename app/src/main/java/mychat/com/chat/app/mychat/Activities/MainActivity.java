package mychat.com.chat.app.mychat.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ParseInstallation;

import mychat.com.chat.app.mychat.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loginButton;
    private Button createAccountButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createAccountButton = (Button) findViewById(R.id.createaccountbtn);
        loginButton = (Button) findViewById(R.id.loginbutton);

        createAccountButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginbutton:
              startActivity(new Intent(MainActivity.this, LoginActivity.class));

                break;
            case R.id.createaccountbtn:
                startActivity(new Intent(MainActivity.this, CreateAccount.class));
        }


    }
}
