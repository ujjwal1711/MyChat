package mychat.com.chat.app.mychat.Activities;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

import mychat.com.chat.app.mychat.Model.Message;

/**
 * Created by UjjwalNUtsav on 13-04-2018.
 */

public class ChattApplication extends Application{
    public  static final String APP_KEY_ID="0IA0KX9N5rZZi2rHsO2ep1gQoqHEp4EbmFkbJnuX";
    public  static final String APP_CLIENT_ID="2SniUiFmKXrLXOfxqSAB4zDs8VDNQSYwngyVza9B";
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);

        ParseObject.registerSubclass(Message.class);
        Parse.initialize(this,APP_KEY_ID,APP_CLIENT_ID);
    }
}
