package edu.co.icesi.firestoreejemplo.services;

import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.json.JSONObject;

import edu.co.icesi.firestoreejemplo.activity.MainActivity;
import edu.co.icesi.firestoreejemplo.app.AppMoviles;
import edu.co.icesi.firestoreejemplo.model.Message;
import edu.co.icesi.firestoreejemplo.util.NotificationUtils;

public class FCMService extends FirebaseMessagingService {




    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

            JSONObject object = new JSONObject(remoteMessage.getData());
            String json = object.toString();

            Message message = new Gson().fromJson(json, Message.class);


            Intent intent = new Intent(AppMoviles.getGlobalContext(), MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(AppMoviles.getGlobalContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationUtils.showNotification("Nuevo mensaje", message.getMessage(), pendingIntent);
    }


}
