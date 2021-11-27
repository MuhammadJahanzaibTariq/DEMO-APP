 package com.example.android.friendlyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddUser extends AppCompatActivity {

    EditText edt;
    String number;
    String phoneNumber;
    DatabaseReference mFirebaseDatabase;
    boolean count=true;
    HashMap<String,String> list;
    FirebaseAuth firebase;
    DatabaseReference database;
    private String two;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

    }
    public void useradd(View view) {
        Toast.makeText(this, "I am in", Toast.LENGTH_SHORT).show();

        FirebaseUser userval = FirebaseAuth.getInstance().getCurrentUser();
        phoneNumber = userval.getPhoneNumber();
        edt=findViewById(R.id.etPhone2);
        number=edt.getText().toString();
        mFirebaseDatabase=FirebaseDatabase.getInstance().getReference();
        list=new HashMap<>();
       readdata(new Firebasecallback() {
           @Override
           public void onCallback(HashMap<String,String> list) {
               database = FirebaseDatabase.getInstance().getReference("User");
               database.orderByValue().limitToLast(4).addChildEventListener(new ChildEventListener() {
                   @Override
                   public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChild) {
                       Toast.makeText(AddUser.this, "I am in queue", Toast.LENGTH_SHORT).show();
                       Toast.makeText(AddUser.this, "I a queue"+list.values(), Toast.LENGTH_SHORT).show();
                       database.child(phoneNumber).child(two).setValue(list);

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

                   // ...
               });


           }
       });




    }
    private void  readdata(Firebasecallback firebasecallback)
    {
        if(!number.isEmpty())
        {
            Query query = mFirebaseDatabase.child("message").orderByChild("uid").equalTo(number);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        if(data.exists())
                        {
                            count=false;
                            Toast.makeText(AddUser.this, "Found the user", Toast.LENGTH_SHORT).show();
                            String one = data.child("name").getValue(String.class);
                           two = data.child("uid").getValue(String.class);
                            list.put("name",one);
                            list.put("uid",two);


                        }
                    }
                    if(count)
                    {

                        Toast.makeText(AddUser.this, "Not Found the user", Toast.LENGTH_SHORT).show();
                    }
                    firebasecallback.onCallback(list);
                    count=true;
                }

                @Override
                public void onCancelled(final DatabaseError databaseError) {
                }
            });
        }
    }
    private interface Firebasecallback
    {
        void onCallback(HashMap<String,String>list);
    }

}