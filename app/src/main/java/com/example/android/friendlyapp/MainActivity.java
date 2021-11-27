package com.example.android.friendlyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reference;
    Button click;
    EditText etmessage;
    LinearLayoutManager linearLayout;
    private RecyclerView mMessageListView;
    ArrayList<FriendlyMessage>friendlyMessageArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        //FIRE BASE REFERENCE
        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("message");
        //CLICK LISTENER FOR BUTTON
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, ""+etmessage.getText().toString(), Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
//                FriendlyMessage friendlyMessage=new FriendlyMessage(etmessage.getText().toString(),"king",null);
//                reference.push().setValue(friendlyMessage);
//                etmessage.setText("");

            }
        });
        //GET DATA FROM FIREBASE
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                FriendlyMessage friendlyMessage=snapshot.getValue(FriendlyMessage.class);
                String val=friendlyMessage.getName();
                String val2=friendlyMessage.getText();

                friendlyMessageArrayList.add(new FriendlyMessage(val2,val,null));
                Toast.makeText(MainActivity.this, ""+ friendlyMessageArrayList.size(), Toast.LENGTH_SHORT).show();
//                Adapter adapter=new Adapter(friendlyMessageArrayList);
//                mMessageListView.setAdapter(adapter);
                Adapter adapter=new Adapter(friendlyMessageArrayList);
                mMessageListView.setAdapter(adapter);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        for(FriendlyMessage itr:friendlyMessageArrayList)
        {
            Toast.makeText(this, ""+itr.getName(), Toast.LENGTH_SHORT).show();
        }


    }
    public void init()
    {
        // ids

        mMessageListView = findViewById(R.id.messageListView);
        linearLayout=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        mMessageListView.setLayoutManager(linearLayout);
        click=findViewById(R.id.button);
        etmessage=findViewById(R.id.message);

    }
}