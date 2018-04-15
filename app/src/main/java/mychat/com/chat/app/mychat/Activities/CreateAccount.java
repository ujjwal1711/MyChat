package mychat.com.chat.app.mychat.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.dd.processbutton.iml.ActionProcessButton;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import mychat.com.chat.app.mychat.R;
import mychat.com.chat.app.mychat.Util.ProgressGenerator;

public class CreateAccount extends AppCompatActivity implements ProgressGenerator.OnCompleteListener {
    private EditText emailAddress;
    private EditText password;
    private EditText username;
    private ProgressGenerator progressGenerator;
    private ActionProcessButton createAccountButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        emailAddress = (EditText) findViewById(R.id.userEmailid);
        password = (EditText) findViewById(R.id.passwordid);
        username = (EditText) findViewById(R.id.usernameid);

        progressGenerator = new ProgressGenerator(this);

        createAccountButton = (ActionProcessButton) findViewById(R.id.b);
        createAccountButton.setMode(ActionProcessButton.Mode.PROGRESS);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

    }

    private void createAccount()
    {
        final String uEmail = emailAddress.getText().toString();
        final String uName = username.getText().toString();
        final String pWord = password.getText().toString();



        if (uEmail.equals("") || uName.equals("") || pWord.equals("")) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(CreateAccount.this);
            dialog.setTitle("Empty Fields");
            dialog.setMessage("Please complete the form");
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        else {


            ParseUser user = new ParseUser();

            user.setUsername(uName);
            user.setPassword(pWord);
            user.setEmail(uEmail);

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null)
                    {
                        progressGenerator.start(createAccountButton);
                        createAccountButton.setEnabled(false);

                        emailAddress.setEnabled(false);
                        username.setEnabled(false);
                        password.setEnabled(false);


                        logUserIn(uName, pWord);


                    }
                }
            });



        }

    }

    private void logUserIn(String uName, String pWord) {

        if (!uName.equals("") || !pWord.equals("")) {

            ParseUser.logInInBackground(uName, pWord, new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    if (e == null) {
                        Log.v("USER LOGGED IN IS:", parseUser.getUsername() );

                    }else {

                    }
                }
            });
        }

    }

    @Override
    public void onComplete() {
        startActivity(new Intent(CreateAccount.this, Chattactivity.class));
    }
}
