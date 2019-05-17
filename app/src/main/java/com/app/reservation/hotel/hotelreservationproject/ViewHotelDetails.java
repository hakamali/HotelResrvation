package com.app.reservation.hotel.hotelreservationproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewHotelDetails extends AppCompatActivity {


    private ImageView vhImageView;
    private TextView tvVhCity,tvRoomAvailablility,tvVhCharges,tvVhAddress,tvVhServices,tvVhHotelname;
    private Button btnVhMap, btnVhBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hotel_details);

        Intent intent = getIntent();

        final String city;
        final String url;
        final String roomAvailablitiy;
        final String charges;
        final String address;
        String id = null;
        String services;
        String hotelname = null;

        String longitude = null;
        String latitude = null;

        tvVhHotelname = (TextView) findViewById(R.id.tvVhHotelname);
        vhImageView = (ImageView) findViewById(R.id.vhImageView);
        tvVhCity = (TextView) findViewById(R.id.tvVhCity);
        tvRoomAvailablility = (TextView) findViewById(R.id.tvRoomAvailablility);
        tvVhCharges = (TextView) findViewById(R.id.tvVhCharges);
        tvVhAddress = (TextView) findViewById(R.id.tvVhAddress);
        tvVhServices = (TextView) findViewById(R.id.tvVhServices);

        btnVhMap = (Button) findViewById(R.id.btnVhMap);
        btnVhBook = (Button) findViewById(R.id.btnVhBook);

        if( intent != null)
        {

            Bundle b = intent.getExtras();


            hotelname = b.getString("hotel");
            city = b.getString("city");
            url = b.getString("url");
            roomAvailablitiy = b.getString("roomAvailablitiy");
            charges = b.getString("charges");
            id = b.getString("id");
            address = b.getString("address");
            services = b.getString("services");
            latitude = b.getString("latitude" );
            longitude = b.getString("longitude");

            com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(url, vhImageView);
            tvVhCity.setText(city);
            tvRoomAvailablility.setText( roomAvailablitiy);
            tvVhCharges.setText(charges);
            tvVhAddress.setText( address);
            services = services.replace("1" , "\n");
            tvVhServices.setText( services );
            tvVhHotelname.setText(hotelname);

        }


        final String finalLongitude = longitude;
        final String finalLatitude = latitude;
        final String finalHotelname = hotelname;

        btnVhMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent map = new Intent(ViewHotelDetails.this, MapsActivity.class);
                map.putExtra("longitude", finalLongitude);
                map.putExtra("latitude", finalLatitude);
                map.putExtra("hotel", finalHotelname);
                startActivity( map );

            }
        });

        final String finalId = id;
        btnVhBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(ViewHotelDetails.this, BookingPageActivity.class);
                intent1.putExtra("id", finalId);

                startActivity( intent1 );

            }
        });



    }
}
