package com.example.room.adapter;

import android.content.Context;
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
import com.example.room.model.Reservation;
import com.example.room.model.gateways.Gateway;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<String> roomName;
    private final ArrayList<String> roomPrice;
    private final ArrayList<String> reservationDate;
    private final ArrayList<String> reservationConfirmed;
    private final List<Reservation> reservations;
    private final Gateway gateway;
    private final String token;

    public ReservationAdapter(Context context, ArrayList<String> roomName, ArrayList<String> roomPrice,
                              ArrayList<String> reservationDate, List<Reservation> reservations,
                              ArrayList<String> reservationConfirmed, Gateway gateway, String token) {
        this.context = context;
        this.roomName = roomName;
        this.roomPrice = roomPrice;
        this.reservationDate = reservationDate;
        this.reservations = reservations;
        this.gateway = gateway;
        this.token = token;
        this.reservationConfirmed = reservationConfirmed;
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

        String price = roomPrice.get(position) + "$";
        holder.priceTxt.setText(price);

        holder.dateTxt.setText(String.valueOf(reservationDate.get(position)));

        if (reservationConfirmed.get(position).equals("false"))
            holder.confirmationTxt.setText(R.string.not_confirmed);
        else
            holder.confirmationTxt.setText(R.string.confirmed);

        holder.deleteBtn.setOnClickListener(v -> {
            gateway.deleteReservation(token, reservations.get(position).getId());

            reservations.remove(position);
            roomName.remove(position);
            roomPrice.remove(position);
            reservationDate.remove(position);

            notifyItemRemoved(position);
            notifyItemRangeChanged(position, reservations.size());
        });

    }

    @Override
    public int getItemCount() {
        return roomName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView roomNameTxt;
        private final TextView priceTxt;
        private final TextView dateTxt;
        private final TextView confirmationTxt;
        private final Button deleteBtn;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            roomNameTxt = itemView.findViewById(R.id.reservation_room_name_textView);
            priceTxt = itemView.findViewById(R.id.reservation_price_textView);
            dateTxt = itemView.findViewById(R.id.reservation_date_or_description_textView);
            confirmationTxt = itemView.findViewById(R.id.reservation_confirmation_textView);
            LinearLayout mainLayout = itemView.findViewById(R.id.reservation_layout);
            deleteBtn = itemView.findViewById(R.id.reservation_delete_button);
            Animation translateAnim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translateAnim);
        }
    }
}
