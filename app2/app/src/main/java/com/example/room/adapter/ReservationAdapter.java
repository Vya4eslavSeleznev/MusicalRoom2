package com.example.room.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room.R;
import com.example.room.view.activity.ReservationActivity;
import com.example.room.gateways.Gateway;
import com.example.room.model.Reservation;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {

    private Context context;
    private ArrayList roomName, roomPrice, reservationDate;
    private Animation translateAnim;
    private List<Reservation> reservations;
    private Gateway gateway;
    private String token;

    public ReservationAdapter(Context context, ArrayList roomName, ArrayList roomPrice, ArrayList reservationDate,
                              List<Reservation> reservations, Gateway gateway, String token) {
        this.context = context;
        this.roomName = roomName;
        this.roomPrice = roomPrice;
        this.reservationDate = reservationDate;
        this.reservations = reservations;
        this.gateway = gateway;
        this.token = token;
    }

    @NonNull
    @NotNull
    @Override
    public ReservationAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reservation_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ReservationAdapter.ViewHolder holder, int position) {
        holder.roomNameTxt.setText(String.valueOf(roomName.get(position)));
        holder.priceTxt.setText(String.valueOf(roomPrice.get(position)));
        holder.dateTxt.setText(String.valueOf(reservationDate.get(position)));
    }

    @Override
    public int getItemCount() {
        return roomName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView roomNameTxt, priceTxt, dateTxt;
        private LinearLayout mainLayout;
        private Button deleteBtn;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            roomNameTxt = itemView.findViewById(R.id.reservation_room_name_textView);
            priceTxt = itemView.findViewById(R.id.reservation_price_textView);
            dateTxt = itemView.findViewById(R.id.reservation_date_or_description_textView);
            mainLayout = itemView.findViewById(R.id.reservation_layout);
            deleteBtn = itemView.findViewById(R.id.reservation_delete_button);
            translateAnim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translateAnim);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gateway.deleteReservation(token, reservations.get(getAdapterPosition()).getId());
                    reservations.remove(getAdapterPosition());

                    Intent intent = new Intent(context, ReservationActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}
