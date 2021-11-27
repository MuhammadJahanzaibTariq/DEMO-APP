package com.example.android.friendlyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.friendlyapp.MesageAdapter.Message_adapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class chat_activity extends AppCompatActivity {
    String senderUid;
    String receiverUid;
    DatabaseReference database;
    String senderRoom, receiverRoom;
    Button sendBtn;
    EditText message;
    Message_adapter adapter;
    ArrayList<Message> messages2=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_activity);
        Toast.makeText(this, "ERROR !", Toast.LENGTH_SHORT).show();
        receiverUid = getIntent().getStringExtra("uid");
        FirebaseUser userval = FirebaseAuth.getInstance().getCurrentUser();
        senderUid = userval.getPhoneNumber();

        senderRoom = senderUid + receiverUid;
        Toast.makeText(this, ""+senderRoom, Toast.LENGTH_SHORT).show();
        receiverRoom = receiverUid + senderUid;
        message=findViewById(R.id.messageBox);
        sendBtn=findViewById(R.id.button3);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

      adapter=new Message_adapter(chat_activity.this,messages2);
      recyclerView.setLayoutManager(new LinearLayoutManager(chat_activity.this));
      recyclerView.setAdapter(adapter);

     database=FirebaseDatabase.getInstance().getReference();
      database.child("chats")
              .child(senderRoom)
              .child("messages")

              .addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot snapshot) {

                      messages2.clear();
                      for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                          Message message = snapshot1.getValue(Message.class);
                          messages2.add(message);
                      }

                      adapter.notifyDataSetChanged();
                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {

                  }
              });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageTxt = message.getText().toString().trim();
                Message messages = new Message(senderUid,messageTxt);
                message.setText("");
                database.child("chats")
                        .child(senderRoom)
                        .child("messages")
                        .push()
                        .setValue(messages).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        database.child("chats")
                                .child(receiverRoom)
                                .child("messages")
                                .push()
                                .setValue(messages).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }
                });

            }
        });
    }
}