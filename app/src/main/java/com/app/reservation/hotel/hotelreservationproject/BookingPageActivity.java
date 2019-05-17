package com.app.reservation.hotel.hotelreservationproject;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.reservation.hotel.hotelreservationproject.Model.BookingModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookingPageActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spMealPlanBookingPage;
    private String[] mealPlan;
    private EditText etUsername,etCNIC,etPhoneNumber,etNoOfRooms,etCheckin,etCheckout;
    private Button btnBookHotel;
    private DatabaseReference database;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_page);

        spMealPlanBookingPage = (Spinner) findViewById(R.id.spMealPlanBookingPage);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etCNIC = (EditText) findViewById(R.id.etCNIC);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etNoOfRooms = (EditText) findViewById(R.id.etNoOfRooms);
        etCheckout = (EditText) findViewById(R.id.etCheckout);
        etCheckin = (EditText) findViewById(R.id.etCheckin);
        btnBookHotel = (Button) findViewById(R.id.btnBookHotel);

        auth = FirebaseAuth.getInstance();
        SharedPreferences preferences = getSharedPreferences("userdata", MODE_PRIVATE);
        final String email = preferences.getString("email", "n/a");

        database = FirebaseDatabase.getInstance().getReference("bookingRequest");

        mealPlan = this.getResources().getStringArray(R.array.mealplan);
        final ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mealPlan);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMealPlanBookingPage.setAdapter(aa);
        spMealPlanBookingPage.setOnItemSelectedListener(this);

        btnBookHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = etUsername.getText().toString();
                String cnic = etCNIC.getText().toString();
                String phone = etPhoneNumber.getText().toString();
                String rooms = etNoOfRooms.getText().toString();
                String meanplan = spMealPlanBookingPage.getSelectedItem().toString();
                String checkin = etCheckin.getText().toString();
                String checkout = etCheckout.getText().toString();

                if(TextUtils.isEmpty( username ))
                {
                    etUsername.setError("Please Enter username");
                    etUsername.requestFocus();
                }
                else if( TextUtils.isEmpty(cnic))
                {
                    etCNIC.setError("Please Enter CNIC");
                    etCNIC.requestFocus();

                }
                else if( TextUtils.isEmpty(phone))
                {
                    etPhoneNumber.setError("Please Enter Phone Number");
                    etPhoneNumber.requestFocus();

                }
                else if( TextUtils.isEmpty(rooms))
                {
                    etNoOfRooms.setError("Please Enter Rooms");
                    etNoOfRooms.requestFocus();
                }
                else if( TextUtils.isEmpty(checkin))
                {
                    etNoOfRooms.setError("Please Enter Checkin Date");
                    etNoOfRooms.requestFocus();
                }
                else if( TextUtils.isEmpty(checkout))
                {
                    etNoOfRooms.setError("Please Enter Checkout Date");
                    etNoOfRooms.requestFocus();
                }
                else
                {

                    final ProgressDialog dialog = new ProgressDialog(BookingPageActivity.this);
                    dialog.setTitle("Sending Request");
                    dialog.setMessage("Please wait while we sending your request.. ");
                    dialog.setCancelable(true);
                    dialog.show();

                    BookingModel model = new BookingModel();
                    String bid = database.push().getKey();
                    String uid = auth.getCurrentUser().getUid();

                    model.setBid(bid);
                    model.setUid(uid);
                    model.setCheckinDate(checkin);
                    model.setCheckOutDate(checkout);
                    model.setCnic(cnic);
                    model.setEmail(email);
                    model.setMealplan(meanplan);
                    model.setNoOfRooms(rooms);
                    model.setPhone(phone);
                    model.setUsername(username);

                    database.child(bid).setValue( model ).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if( task.isSuccessful())
                            {
                                finish();
                                dialog.dismiss();
                                Toast.makeText(BookingPageActivity.this, "Booked Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(BookingPageActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });



                }

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
