package com.app.reservation.hotel.hotelreservationproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.reservation.hotel.hotelreservationproject.Model.HotelData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class MainPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    private static final int REQUEST_INVITE = 234;
    private FirebaseAuth mAuth;
    private RecyclerView rvMainPage;
    private Spinner spCitiesMain;
    private String[] cities;
    private String city;
    private Button btnSearch;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("hotels");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spCitiesMain = (Spinner) findViewById(R.id.spCitiesMain);

        btnSearch = (Button) findViewById(R.id.btnSearch);

        rvMainPage = (RecyclerView) findViewById(R.id.rvMainPage);
        mAuth = FirebaseAuth.getInstance();
        rvMainPage.setHasFixedSize(true);
        rvMainPage.setLayoutManager(new LinearLayoutManager(this));

        cities = this.getResources().getStringArray(R.array.cities2);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cities);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCitiesMain.setAdapter(aa);
        spCitiesMain.setOnItemSelectedListener(this);


        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, MainPanel.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        showdata("All");

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s;
                int pos = spCitiesMain.getSelectedItemPosition();
                switch (pos) {
                    case 0:
                        s = "All";     break;
                    case 1:
                        s = "Sahiwal";       break;
                    case 2:
                        s = "Lahore";        break;
                    case 3:
                        s = "Karachi";        break;
                    case 4:
                        s = "Islamabad";      break;
                    case 5:
                        s = "Murree";       break;

                    default:
                        s = "All";
                }

                showdata(s);

            }
        });


    }


    public void showdata(String myString) {

        Toast.makeText(this, "Search Started..", Toast.LENGTH_SHORT).show();
        rvMainPage.removeAllViews();
        FirebaseRecyclerAdapter<HotelData, HotelsViewHolder> firebaseRecyclerAdapter;

        if (myString.equals("All")) {

            firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<HotelData, HotelsViewHolder>(
                    HotelData.class,
                    R.layout.single_hotel_item,
                    HotelsViewHolder.class,
                    reference
            ) {
                @Override
                protected void populateViewHolder(HotelsViewHolder viewHolder, HotelData model, int position) {
                    viewHolder.setDetails(MainPageActivity.this, model, model.getHotelname(), model.getRoomcharges(), model.getAddress(), model.getImg());
                }
            };


        } else {


            Query firebaseQuery = reference.orderByChild("city").startAt(myString).endAt(myString + "\uf8ff");
            firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<HotelData, HotelsViewHolder>(
                    HotelData.class,
                    R.layout.single_hotel_item,
                    HotelsViewHolder.class,
                    firebaseQuery
            ) {
                @Override
                protected void populateViewHolder(HotelsViewHolder viewHolder, HotelData model, int position) {

                    viewHolder.setDetails(MainPageActivity.this, model, model.getHotelname(), model.getRoomcharges(), model.getAddress(), model.getImg());

                }
            };


        }

//        if( firebaseRecyclerAdapter.getItemCount() < 0)
//        {
//            Toast.makeText(this, "No Item Available to display", Toast.LENGTH_SHORT).show();
//        }

        rvMainPage.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if( id == R.id.nav_search)
        {
            Intent intent = new Intent(MainPageActivity.this, SearchActivity.class);
            startActivity( intent );
        }
        else
        if( id == R.id.nav_developer)
        {
            Intent intent = new Intent(MainPageActivity.this, ContactDeveloperActivity.class);
            startActivity( intent );
        }
        else
        if( id == R.id.nav_invite)
        {
          onInviteCliked();
        }
        else
        if (id == R.id.nav_logout) {
            // Handle the camera action
            mAuth.signOut();
            SharedPreferences preferences = getSharedPreferences("userdata", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("email", "n/a");
            editor.apply();
            Intent intent = new Intent(MainPageActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void onInviteCliked() {

        Intent intent = new AppInviteInvitation.IntentBuilder("invitation title")
                .setMessage("hi there , this is nice app").setDeepLink(Uri.parse("http://google.com"))
                .setCallToActionText("Invitation CTA")
                .build();
        startActivityForResult(intent,REQUEST_INVITE );


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        city = cities[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_INVITE ) {
            if (resultCode == RESULT_OK) {
                // Get the invitation IDs of all sent messages
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                for (String id : ids) {
                    Toast.makeText(this, "Invitation Sent", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Sending failed or it was canceled, show failure message to the user
                // ...
            }
        }
    }

    public static class HotelsViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public HotelsViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        void setDetails(final MainPageActivity context, final HotelData model, final String hotelname, String price, String address, String imgurl) {

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
                    b.putString("id" , model.getId());
                    b.putString("city" , model.getCity());
                    b.putString("url" , model.getImg());
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












