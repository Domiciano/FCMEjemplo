package edu.co.icesi.firestoreejemplo.fcm;

import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import edu.co.icesi.firestoreejemplo.activity.ChatActivity;
import edu.co.icesi.firestoreejemplo.app.AppMoviles;
import edu.co.icesi.firestoreejemplo.model.Message;
import edu.co.icesi.firestoreejemplo.model.User;
import edu.co.icesi.firestoreejemplo.util.NotificationUtils;

public class FCMService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {


        try {
            Gson gson = new Gson();
            JSONObject object = new JSONObject(remoteMessage.getData());

            String messageJSON = object.get("message").toString();
            String fromUserJSON = object.get("fromUser").toString();
            String toUserJSON = object.get("toUser").toString();

            Message message = gson.fromJson(messageJSON, Message.class);
            User fromUser = gson.fromJson(fromUserJSON, User.class);
            User toUser = gson.fromJson(toUserJSON, User.class);


            Intent intent = new Intent(AppMoviles.getGlobalContext(), ChatActivity.class);
            intent.putExtra("user", fromUser);
            intent.putExtra("contact", toUser);
            PendingIntent pendingIntent = PendingIntent.getActivity(AppMoviles.getGlobalContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationUtils.showNotification("Nuevo mensaje", message.getMessage(), pendingIntent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
