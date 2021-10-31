package edu.co.icesi.firestoreejemplo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.Date;
import java.util.UUID;

import edu.co.icesi.firestoreejemplo.R;
import edu.co.icesi.firestoreejemplo.fcm.DetailedMessage;
import edu.co.icesi.firestoreejemplo.model.Chat;
import edu.co.icesi.firestoreejemplo.model.Message;
import edu.co.icesi.firestoreejemplo.model.User;
import edu.co.icesi.firestoreejemplo.fcm.FMCMessage;
import edu.co.icesi.firestoreejemplo.util.HTTPSWebUtilDomi;

public class ChatActivity extends AppCompatActivity {

    private User user;
    private User contact;

    private Chat chat;

    private EditText messageET;
    private TextView messagesTV;
    private Button sendBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        user = (User) getIntent().getExtras().get("user");
        contact = (User) getIntent().getExtras().get("contact");

        messageET = findViewById(R.id.messageET);
        messagesTV = findViewById(R.id.messagesTV);
        sendBtn = findViewById(R.id.sendBtn);


        FirebaseFirestore.getInstance().collection("users").document(user.getId()).collection("chats")
                .whereEqualTo("contactID", contact.getId()).get().addOnCompleteListener(
                        task->{
                            if(task.getResult().size() == 0){
                                createChat();
                            }else{
                                for(DocumentSnapshot doc : task.getResult()){
                                    chat = doc.toObject(Chat.class);
                                    break;
                                }
                            }
                            getMessages();
                        }
        );


        sendBtn.setOnClickListener(
                v->{
                    Message m = new Message(UUID.randomUUID().toString(), messageET.getText().toString(), user.getId(), new Date().getTime());
                    FirebaseFirestore.getInstance().collection("chats")
                            .document(chat.getId()).collection("messages").document(m.getId()).set(m);
                    new Thread(
                            ()->{
                                DetailedMessage detailedMessage = new DetailedMessage(m, user, contact);
                                FMCMessage<DetailedMessage> msg = new FMCMessage<>("/topics/"+contact.getId(), detailedMessage);
                                new HTTPSWebUtilDomi().POSTtoFCM( new Gson().toJson(msg) );
                            }
                    ).start();
                }
        );

    }

    private void getMessages() {
        FirebaseFirestore.getInstance().collection("chats").document(chat.getId()).collection("messages").addSnapshotListener(
                (value, error) -> {

                    messagesTV.setText("");
                    for(DocumentSnapshot doc : value.getDocuments()){
                        Message msg = doc.toObject(Message.class);
                        messagesTV.append(msg.getMessage()+"\n\n");
                    }

                }
        );
    }

    public void createChat(){
        String id = UUID.randomUUID().toString();
        chat = new Chat(id, contact.getId());
        Chat foreingChat = new Chat(id, user.getId());
        FirebaseFirestore.getInstance().collection("users").document(user.getId()).collection("chats").document(id).set(chat);
        FirebaseFirestore.getInstance().collection("users").document(contact.getId()).collection("chats").document(id).set(foreingChat);
    }
}