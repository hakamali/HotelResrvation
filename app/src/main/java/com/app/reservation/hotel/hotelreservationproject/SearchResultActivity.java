package com.app.reservation.hotel.hotelreservationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.reservation.hotel.hotelreservationproject.Model.HotelData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.app.reservation.hotel.hotelreservationproject.R.id.rvMainPage;

public class SearchResultActivity extends AppCompatActivity {

    private RecyclerView rvSearchResult;
    private List hotelsList;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("hotels");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        rvSearchResult = (RecyclerView) findViewById(R.id.rvSearchResult);
        rvSearchResult.setHasFixedSize(true);
        rvSearchResult.setLayoutManager(new LinearLayoutManager(this));

        hotelsList = new ArrayList();

        Intent intent = getIntent();

        String amount = null;
        String city = null;

        if (intent != null) {
            amount = intent.getStringExtra("amount");
            city = intent.getStringExtra("city");

        }


        final String finalAmount = amount;

        final String finalCity = city;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                hotelsList.clear();

                for( DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    HotelData data = snapshot.getValue(HotelData.class);

                    if( !( Integer.parseInt(data.getRoomcharges()) > Integer.parseInt(finalAmount))  && finalCity.equals(data.getCity()))
                    {
                        hotelsList.add( data );
                    }
                }

                rvSearchResult.setAdapter( new HotelAdapter(hotelsList,SearchResultActivity.this) );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public static class HotelsViewHolder2 extends RecyclerView.ViewHolder {
        View mView;

        public HotelsViewHolder2(View itemView) {
            super(itemView);

            mView = itemView;

        }

        void setDetails(final SearchResultActivity context, final HotelData model, final String hotelname, String price, String address, String imgurl) {

            ImageView imageView = (ImageView) mView.findViewById(R.id.singleImage);
            TextView singleAddress = (TextView) mView.findViewById(R.id.singleAddress);
            TextView singleHotelName = (TextView) mView.findViewById(R.id.singleHotelName);
            TextView singlePrice = (TextView) mView.findViewById(R.id.singlePrice);
            CardView cdFullView = (CardView) mView.findViewById(R.id.cdFullView);

            cdFullView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, ViewHotelDetails.class);
                    Bundle b = new Bundle();

                    b.putString("hotel" , model.getHotelname());
                    b.putString("city" , model.getCity());
                    b.putString("url" , model.getImg());
                    b.putString("id" , model.getId());
                    b.putString("roomAvailablitiy" ,model.getRoomavailable());
                    b.putString("charges" , model.getRoomcharges());
                    b.putString("address" , model.getAddress());
                    b.putString("services" , model.getServices());
                    b.putString("latitude" , model.getLatitude());
                    b.putString("longitude" , model.getLongitude());

                    ActivityOptionsCompat optionsCompat =
                            ActivityOptionsCompat.makeSceneTransitionAnimation( context ,mView.findViewById(R.id.singleImage) ,"myImage");

                    intent.putExtras( b );
                    context.startActivity( intent, optionsCompat.toBundle() );

                }
            });

            com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(imgurl, imageView);
            singleAddress.setText(address);
            singleHotelName.setText(hotelname);
            singlePrice.setText(price);

        }


    }

}
