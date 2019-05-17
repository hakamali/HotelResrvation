package com.app.reservation.hotel.hotelreservationproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.reservation.hotel.hotelreservationproject.Model.HotelData;

import java.util.List;


class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {

    private List<HotelData> postLists;
    private SearchResultActivity context;


    HotelAdapter(List<HotelData> postLists, SearchResultActivity context) {
        this.postLists = postLists;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_hotel_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final HotelData model = postLists.get(position);

        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage( model.getImg(), holder.imageView);
        holder.singleHotelName.setText( model.getHotelname());
        holder.singlePrice.setText( model.getRoomcharges());
        holder.singleAddress.setText( model.getAddress());

        holder.cdFullView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ViewHotelDetails.class);
                Bundle b = new Bundle();

                b.putString("hotel" , model.getHotelname());
                b.putString("city" , model.getCity());
                b.putString("url" , model.getImg());
                b.putString("roomAvailablitiy" ,model.getRoomavailable());
                b.putString("charges" , model.getRoomcharges());
                b.putString("address" , model.getAddress());
                b.putString("services" , model.getServices());
                b.putString("latitude" , model.getLatitude());
                b.putString("longitude" , model.getLongitude());

                ActivityOptionsCompat optionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation( context , holder.imageView ,"myImage");

                intent.putExtras( b );
                context.startActivity( intent, optionsCompat.toBundle() );

            }
        });

    }


    @Override
    public int getItemCount() {
        return postLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView singleAddress;
        private TextView singleHotelName;
        private TextView singlePrice;
        private CardView cdFullView;


        ViewHolder(View itemView) {
            super(itemView);

             imageView = (ImageView) itemView.findViewById(R.id.singleImage);
            singleAddress = (TextView) itemView.findViewById(R.id.singleAddress);
            singleHotelName = (TextView) itemView.findViewById(R.id.singleHotelName);
             singlePrice = (TextView) itemView.findViewById(R.id.singlePrice);
             cdFullView = (CardView) itemView.findViewById(R.id.cdFullView);

        }

    }


}



