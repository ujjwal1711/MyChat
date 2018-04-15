package mychat.com.chat.app.mychat.Data;

import android.app.Notification;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

import mychat.com.chat.app.mychat.Model.Message;
import mychat.com.chat.app.mychat.R;

/**
 * Created by UjjwalNUtsav on 14-04-2018.
 */

public class ChatAdapter extends ArrayAdapter<Message> {

    private String mUserId;




    public ChatAdapter(Context context, String userId, List<Message> messages) {
        super(context, 0, messages);
        mUserId = userId;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null)
        {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_row, parent, false);
            final ViewHolder holder = new ViewHolder();
            holder.imageLeft = (ImageView) convertView.findViewById(R.id.Profileleft);
            holder.imageRight = (ImageView) convertView.findViewById(R.id.Profileright);
            holder.body = (TextView) convertView.findViewById(R.id.tvbody);
            convertView.setTag(holder);


        }
        final Message message = (Message)getItem(position);
        final  ViewHolder holder = (ViewHolder) convertView.getTag();
        final boolean isMe = message.getUserId().equals(mUserId);


        if (isMe) {
            holder.imageRight.setVisibility(View.VISIBLE);
            holder.imageLeft.setVisibility(View.GONE);
            holder.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);

        }
        else {

            holder.imageLeft.setVisibility(View.VISIBLE);
            holder.imageRight.setVisibility(View.GONE);
            holder.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        }
        final ImageView profileView = isMe ? holder.imageRight : holder.imageLeft;

        Picasso.with(getContext()).load(getProfileGravatar(message.getUserId())).into(profileView);
        holder.body.setText(message.getBody());

        return convertView;

    }

    private static String getProfileGravatar(final String userId) {

        String hex = "";
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final byte[] hash = digest.digest(userId.getBytes());
            final BigInteger bigInt = new BigInteger(hash);
            hex = bigInt.abs().toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "http://www.gravatar.com/avatar/" + hex + "?d=identicon";

    }

    class ViewHolder
    {

        public ImageView imageLeft;
        public ImageView imageRight;
        public TextView body;

    }


}
