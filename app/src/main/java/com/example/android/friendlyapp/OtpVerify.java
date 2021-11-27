package com.example.android.friendlyapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.android.friendlyapp.databinding.ContentOtpVerifyBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class OtpVerify extends AppCompatActivity
{
    String number;

    FirebaseAuth mauth;
  ContentOtpVerifyBinding binding;
    private String verificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ContentOtpVerifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mauth=FirebaseAuth.getInstance();
        number=  "+33"+getIntent().getStringExtra("phone").toString().trim();
        /*binding.tvMobile.setText(String.format(number=  "+91-%s"+getIntent().getStringExtra("phone")
));*/
        binding.Resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OtpVerify.this, ""+number, Toast.LENGTH_SHORT).show();
                  resendVerificationCode();
            }
        });

        verificationId = getIntent().getStringExtra("verificationId");
        binding.btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.pinView.getText().toString().trim().isEmpty()) {
                    binding.btnVerify.setVisibility(View.VISIBLE);
                    Toast.makeText(OtpVerify.this, "please fill the field", Toast.LENGTH_SHORT).show();
                } else {

                    if (verificationId != null) {
                        String code = binding.pinView.getText().toString().trim();
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
                        binding.progressBarVerify.setVisibility(View.VISIBLE);
                        binding.btnVerify.setVisibility(View.INVISIBLE);
                        FirebaseAuth
                                .getInstance()
                                .signInWithCredential(credential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull  Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            binding.progressBarVerify.setVisibility(View.VISIBLE);
                                            binding.btnVerify.setVisibility(View.INVISIBLE);
                                            Toast.makeText(OtpVerify.this, "Welcome..."+code, Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(OtpVerify.this,MainActivity2.class);
                                            startActivity(intent);

                                        } else {
                                            binding.progressBarVerify.setVisibility(View.GONE);
                                            binding.btnVerify.setVisibility(View.VISIBLE);
                                            Toast.makeText(OtpVerify.this, "OTP is not Valid!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            }
        });
}

    public void resendVerificationCode()
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+923107869149",60,TimeUnit.SECONDS,OtpVerify.this,
        new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
        {


            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential)
            {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(OtpVerify.this, "OTP is successfully send.", Toast.LENGTH_SHORT).show();

            }
        }
    );
    }
}



