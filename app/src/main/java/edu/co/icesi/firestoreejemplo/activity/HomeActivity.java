package edu.co.icesi.firestoreejemplo.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.util.ArrayList;

import edu.co.icesi.firestoreejemplo.R;
import edu.co.icesi.firestoreejemplo.model.User;

public class HomeActivity extends AppCompatActivity {

    private User user;

    private ListView userListView;
    private ArrayList<User> users;
    private ArrayAdapter<User> adapter;

    private Button signoutBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        user = (User) getIntent().getExtras().get("user");

        FirebaseMessaging.getInstance().subscribeToTopic(user.getId());


        signoutBtn = findViewById(R.id.signoutBtn);

        signoutBtn.setOnClickListener(v->{
            FirebaseMessaging.getInstance().unsubscribeFromTopic(user.getId());
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        });



        userListView = findViewById(R.id.userListView);
        users = new ArrayList<>();
        adapter =new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        userListView.setAdapter(adapter);

        FirebaseFirestore.getInstance().collection("users").get().addOnCompleteListener(
                task ->{
                    for(DocumentSnapshot doc : task.getResult()){
                        User user = doc.toObject(User.class);
                        users.add(user);
                        adapter.notifyDataSetChanged();
                    }
                }
        );

        userListView.setOnItemClickListener((parent, view, position, id) -> {
            User contact = users.get(position);
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra("user", user);
            intent.putExtra("contact", contact);
            startActivity(intent);
        });

    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Salida")
                .setMessage("¿Desea salir?")
                .setPositiveButton("SI",(dialog, id)->{
                    finish();
                })
                .setNegativeButton("NO",(dialog, id)->{
                    dialog.dismiss();
                })
                .show();
    }
}