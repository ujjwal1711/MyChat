package mychat.com.chat.app.mychat.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import mychat.com.chat.app.mychat.Data.ChatAdapter;
import mychat.com.chat.app.mychat.Model.Message;
import mychat.com.chat.app.mychat.R;
import mychat.com.chat.app.mychat.Util.ProgressGenerator;

public class Chattactivity extends AppCompatActivity {




    private EditText message;
    private Button sendMessageButton;
    private ProgressGenerator progressGenerator;
    public static final String USER_ID_KEY = "userId";
    private String currentUserId;
    private ListView listView;
    private ArrayList<Message> mMessages;
    private ChatAdapter mAdapter;
    private Handler handler = new Handler();

    private  static final int MAX_CHAT_MSG_TO_SHOW = 70;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chattactivity);
        getCurrentUser();

        handler.postDelayed(runnable, 100);
    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            refreshMessages();
            handler.postDelayed(this, 100);
        }
    };

    private void getCurrentUser()
    {
        currentUserId = ParseUser.getCurrentUser().getObjectId();
        messagePosting();

    }

    private void messagePosting() {


        message = (EditText) findViewById(R.id.etMessage);
        sendMessageButton = (Button) findViewById(R.id.buttonSend);
        listView = (ListView) findViewById(R.id.listview_chat);
        mMessages = new ArrayList<Message>();
        mAdapter = new ChatAdapter(Chattactivity.this, currentUserId, mMessages);
        listView.setAdapter(mAdapter);

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!message.getText().toString().equals("")) {

                    Message msg = new Message();
                    msg.setUserId(currentUserId);
                    msg.setBody(message.getText().toString());
                    msg.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            receiveMessage();
                        }
                    });
                    message.setText("");
                }else{
                    Toast.makeText(getApplicationContext(), "Empty message!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void receiveMessage() {
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        query.setLimit(MAX_CHAT_MSG_TO_SHOW);
        query.orderByAscending("createdAt");

        query.findInBackground(new FindCallback<Message>() {
            @Override
            public void done(List<Message> messages, ParseException e) {
                if (e == null) {
                    mMessages.clear();
                    mMessages.addAll(messages);
                    mAdapter.notifyDataSetChanged();
                    listView.invalidate();
                } else {
                    Log.v("Error:", "Error:" + e.getMessage());
                }
            }
        });
    }


    private void refreshMessages() {
        receiveMessage();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chatt, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logoout) {

            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {

                        startActivity(new Intent(Chattactivity.this, MainActivity.class));

                    }else {

                    }
                }
            });



            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
