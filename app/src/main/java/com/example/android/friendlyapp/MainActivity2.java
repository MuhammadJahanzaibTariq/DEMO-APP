package com.example.android.friendlyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.android.friendlyapp.databinding.ActivityMain2Binding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    ActivityMain2Binding binding;
    DatabaseReference database;
    ArrayList<User> user;
    UserAdapter adapter;
    FirebaseAuth firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        user=new ArrayList<>();
        adapter=new UserAdapter(MainActivity2.this,user);
        binding.recycle.setAdapter(adapter);
        FirebaseUser userval = FirebaseAuth.getInstance().getCurrentUser();
        String phoneNumber = userval.getPhoneNumber();
        Toast.makeText(this, "number"+phoneNumber, Toast.LENGTH_SHORT).show();
        database=FirebaseDatabase.getInstance().getReference().child("User").child(phoneNumber);
        database.keepSynced(true);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user.clear();
                for (DataSnapshot childSnapshot: snapshot.getChildren())
                {
                    User user1 = childSnapshot.getValue(User.class);
                    user.add(user1);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity2.this, "error"+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });




    }

    public void useradd(View view) {
        Intent intent=new Intent(MainActivity2.this,AddUser.class);
        startActivity(intent);

    }
}