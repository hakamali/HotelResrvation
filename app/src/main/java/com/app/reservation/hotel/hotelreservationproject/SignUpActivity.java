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

import com.app.reservation.hotel.hotelreservationproject.Model.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    Button btnCreateSignup;
    EditText etEmailSignup, etPasswordSignup, etNameSignUp;
    FirebaseAuth auth;
    TextView tvDonthaveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etEmailSignup = (EditText) findViewById(R.id.etEmailSignup);
        etPasswordSignup = (EditText) findViewById(R.id.etPasswordSignup);
        etNameSignUp = (EditText) findViewById(R.id.etNameSignUp);
        tvDonthaveLogin = (TextView) findViewById(R.id.tvDonthaveLogin);

        tvDonthaveLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("account");

        btnCreateSignup = (Button) findViewById(R.id.btnCreateSignup);
        auth = FirebaseAuth.getInstance();

        btnCreateSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = etNameSignUp.getText().toString();
                final String email = etEmailSignup.getText().toString();
                final String pass = etPasswordSignup.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    etNameSignUp.setError("Please Enter Username");
                    etNameSignUp.requestFocus();
                } else if (TextUtils.isEmpty(email)) {
                    etEmailSignup.setError("Please Enter Email");
                    etEmailSignup.requestFocus();
                } else if (TextUtils.isEmpty(pass)) {
                    etPasswordSignup.setError("Please Enter Password");
                    etPasswordSignup.requestFocus();
                } else if (pass.length() < 6) {
                    etPasswordSignup.setError("Password must be greater than 5");
                    etPasswordSignup.requestFocus();
                } else {

                    final ProgressDialog dialog = new ProgressDialog(SignUpActivity.this);
                    dialog.setTitle("Creating new Account..");
                    dialog.setMessage("Please wait while setting up new account..");
                    dialog.setCancelable(true);
                    dialog.show();

                    final String id = reference.push().getKey();

                    final UserData data = new UserData();

                    data.setUsername(username);
                    data.setEmail(email);
                    data.setId(id);

                     auth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {

                                        reference.child(id).setValue(data)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            dialog.dismiss();
                                                            SharedPreferences preferences = getSharedPreferences("userdata", MODE_PRIVATE);
                                                            SharedPreferences.Editor editor = preferences.edit();
                                                            editor.putString("email" , email);
                                                            editor.putString("password", pass);
                                                            editor.apply();
                                                            dialog.dismiss();

                                                            Toast.makeText(SignUpActivity.this, "User added successfully", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(SignUpActivity.this, MainPageActivity.class);
                                                            startActivity( intent );
                                                            finish();
                                                        }
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        dialog.dismiss();
                                                        Toast.makeText(SignUpActivity.this, "" + e.getMessage() ,  Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    dialog.dismiss();
                                    Toast.makeText(SignUpActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                }


            }
        });

    }
}
