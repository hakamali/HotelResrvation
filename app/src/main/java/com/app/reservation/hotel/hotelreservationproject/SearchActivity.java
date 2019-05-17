package com.app.reservation.hotel.hotelreservationproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.app.reservation.hotel.hotelreservationproject.Model.HotelData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private EditText etAmountSearch;
    private Spinner spSearchActivity;
    private Button btnSearchActivity;
    private String[] cities;
    private String city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        spSearchActivity = (Spinner) findViewById(R.id.spSearchActivity);
        etAmountSearch = (EditText) findViewById(R.id.etAmountSearch);
        btnSearchActivity = (Button) findViewById(R.id.btnSearchActivity);

        cities = this.getResources().getStringArray(R.array.cities);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cities);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSearchActivity.setAdapter(aa);
        spSearchActivity.setOnItemSelectedListener(this);

        btnSearchActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(etAmountSearch.getText().toString() ) )
                {
                    etAmountSearch.setError("Please Enter You range");
                    etAmountSearch.requestFocus();
                }
                else
                {
                    String amount = etAmountSearch.getText().toString();
                    String cit = spSearchActivity.getSelectedItem().toString();

                    Intent intent = new Intent( SearchActivity.this, SearchResultActivity.class);
                    intent.putExtra("amount", amount );
                    intent.putExtra("city" , cit);
                    startActivity( intent );


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
