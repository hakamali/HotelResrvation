package com.app.reservation.hotel.hotelreservationproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.reservation.hotel.hotelreservationproject.Model.HotelData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainPanel extends AppCompatActivity implements LocationListener, AdapterView.OnItemSelectedListener {

    private static final int IMAGE_REQUEST_CODE = 30253;
    EditText etHotelName, etRoomCharges, etMealCharges, etRoomAvailable, etAddress, etContact;
    Button btnSelectPic, btnUploadHotel;
    Spinner spCities;
    String city;
    private StorageReference postImagesRef;
    private LocationManager locationManager;
    private Uri imgUri;
    String filePath;

    String[] cities;

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_panel);

        etHotelName = (EditText) findViewById(R.id.etHotelName);
        etRoomCharges = (EditText) findViewById(R.id.etRoomCharges);
        etMealCharges = (EditText) findViewById(R.id.etMealCharges);
        etRoomAvailable = (EditText) findViewById(R.id.etRoomAvailable);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etContact = (EditText) findViewById(R.id.etContact);

        btnSelectPic = (Button) findViewById(R.id.btnSelectPic);
        btnUploadHotel = (Button) findViewById(R.id.btnUploadHotel);

        cities = this.getResources().getStringArray(R.array.cities);

        spCities = (Spinner) findViewById(R.id.spCities);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cities);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCities.setAdapter(aa);
        spCities.setOnItemSelectedListener(this);

        postImagesRef = FirebaseStorage.getInstance().getReference("Images");

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("hotels");

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        final Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        onLocationChanged(location);


        btnUploadHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog dialog = new ProgressDialog(MainPanel.this);
                dialog.setTitle("Creating new Account..");
                dialog.setMessage("Please wait while setting up new reporter account..");
                dialog.setCancelable(true);
                dialog.show();

                final String id = reference.push().getKey();
                HotelData data = new HotelData();

                data.setId(id);
                data.setAddress(etAddress.getText().toString());
                data.setCity(city);
                data.setContact(etContact.getText().toString());
                data.setHotelname(etHotelName.getText().toString());
                data.setImg(etHotelName.getText().toString());
                data.setLatitude(location.getLatitude() + "");
                data.setLongitude(location.getLongitude() + "");
                data.setImg(" test url");
                data.setServices("Bath");
                data.setMealcharges(etMealCharges.getText().toString());
                data.setRoomavailable(etRoomAvailable.getText().toString());
                data.setRoomcharges(etRoomCharges.getText().toString());

                reference.child(id).setValue(data)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                Toast.makeText(MainPanel.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }).
                        addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainPanel.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                            }
                        });


            }

        });

        btnSelectPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent photoPicker = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(photoPicker, "Select Any Image"), IMAGE_REQUEST_CODE);

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            imgUri = data.getData();
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(imgUri, projection, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();
                int collumnIndex = cursor.getColumnIndex(projection[0]);
                filePath = cursor.getString(collumnIndex);
                cursor.close();

            }
        }

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        city = cities[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
