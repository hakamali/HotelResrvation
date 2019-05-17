package com.app.reservation.hotel.hotelreservationproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    Button btnAddaccount;
    EditText etPassLogin,etEmailLogin;
    FirebaseAuth auth;
    TextView tvDonthave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmailLogin = (EditText) findViewById(R.id.etEmailLogin);
        etPassLogin = (EditText) findViewById(R.id.etPassLogin);
        btnAddaccount = (Button) findViewById(R.id.btnAddaccount);
        tvDonthave = (TextView) findViewById(R.id.tvDontHave);

        auth = FirebaseAuth.getInstance();

        tvDonthave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent( LoginActivity.this , SignUpActivity.class);
                startActivity( intent );

            }
        });

        btnAddaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = etEmailLogin.getText().toString();
                final String password = etPassLogin.getText().toString();

                if( TextUtils.isEmpty( email))
                {
                    etEmailLogin.setError("Enter Email");
                    etEmailLogin.requestFocus();
                }
                else
                if( TextUtils.isEmpty( password))
                {
                    etPassLogin.setError("Enter Password");
                    etPassLogin.requestFocus();
                }
                else
                if( password.length() < 6)
                {
                    etPassLogin.setError("Password Must be greater than 5");
                    etPassLogin.requestFocus();
                }
                else {

                        final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
                        dialog.setTitle("Logging In..");
                        dialog.setMessage("Please wait while checking your account..");
                        dialog.setCancelable(true);
                        dialog.show();

                        auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {

                                            Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();

                                            SharedPreferences preferences = getSharedPreferences("userdata", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = preferences.edit();
                                            editor.putString("email" , email);
                                            editor.putString("usertype" , "admin");
                                            editor.putString("password", password);
                                            editor.apply();
                                            dialog.dismiss();

                                            Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);

                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialog.dismiss();
                                Toast.makeText(LoginActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                }


            }
        });


    }
}
