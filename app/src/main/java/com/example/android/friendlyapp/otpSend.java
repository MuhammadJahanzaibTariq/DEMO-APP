package com.example.android.friendlyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.friendlyapp.databinding.ActivityOtpSend2Binding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class otpSend extends AppCompatActivity {
    private ArrayList<countryitem> mCountryList;
    private spinneradapter  mAdapter;

  ActivityOtpSend2Binding binding;
   private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOtpSend2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mauth=FirebaseAuth.getInstance();
        init();
        Spinner spinnerCountries = findViewById(R.id.spinner);
        mAdapter = new spinneradapter(this, mCountryList);
        spinnerCountries.setAdapter(mAdapter);;

        spinnerCountries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                countryitem clickedItem = (countryitem) parent.getItemAtPosition(position);
                String clickedCountryName = clickedItem.getCountryName();
                Toast.makeText(otpSend.this, clickedCountryName + " selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(binding.etPhone.getText()))
                {
                    binding.etPhone.setError("Please Fill This Field");
                }
                else if(binding.etPhone.getText().toString().trim().length()>20)
                {
                    Toast.makeText(otpSend.this, "INVALID PAKISTAN PHONE NUMBER", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    otpSend();
                }

            }
        });
//       [ binding.login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                login(binding.etPhone.getText().toString().trim());
//            }
//        });]


    }
    private void otpSend()
    {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.btnSend.setVisibility(View.INVISIBLE);
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mauth)
                        .setPhoneNumber("+92"+binding.etPhone.getText().toString().trim())       // Phone number to verify
                        .setTimeout(3L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(otpSend.this)// Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {



                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                binding.progressBar.setVisibility(View.GONE);
                             binding.btnSend.setVisibility(View.VISIBLE);
                            Toast.makeText(otpSend.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(verificationId, forceResendingToken);
                                binding.progressBar.setVisibility(View.GONE);
                            binding.btnSend.setVisibility(View.INVISIBLE);
                            Toast.makeText(otpSend.this, "OTP is successfully send.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(otpSend.this, OtpVerify.class);
                                intent.putExtra("phone", binding.etPhone.getText().toString().trim());
                                intent.putExtra("verificationId", verificationId);
                                startActivity(intent);
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        mauth.setLanguageCode("fr");
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent=new Intent(otpSend.this,MainActivity2.class);
            startActivity(intent);

        }//No User is Logged in

    }
    private void init() {
        mCountryList = new ArrayList<>();
        mCountryList.add(new countryitem("+92", R.drawable.pak));
        mCountryList.add(new countryitem("+33", R.drawable.france));
        mCountryList.add(new countryitem("+49", R.drawable.download));
        mCountryList.add(new countryitem("+44", R.drawable.england));
    }


}