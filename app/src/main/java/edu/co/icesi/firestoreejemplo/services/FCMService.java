package edu.co.icesi.firestoreejemplo.services;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import edu.co.icesi.firestoreejemplo.model.Message;
import edu.co.icesi.firestoreejemplo.util.NotificationUtils;

public class FCMService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

            JSONObject object = new JSONObject(remoteMessage.getData());
            String json = object.toString();

            Message message = new Gson().fromJson(json, Message.class);


            NotificationUtils.showNotification("Nuevo mensaje", message.getMessage());


    }
}
