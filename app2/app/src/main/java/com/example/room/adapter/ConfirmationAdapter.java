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

public class ConfirmationAdapter extends RecyclerView.Adapter<ConfirmationAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<String> roomName;
    private final ArrayList<String> reservationDate;
    private final ArrayList<String> reservationConfirmed;
    private final List<Reservation> reservations;
    private final Gateway gateway;
    private final String token;

    public ConfirmationAdapter(Context context, ArrayList<String> roomName, ArrayList<String> reservationDate,
                               ArrayList<String> reservationConfirmed, List<Reservation> reservations,
                               Gateway gateway, String token) {
        this.context = context;
        this.roomName = roomName;
        this.reservationDate = reservationDate;
        this.reservationConfirmed = reservationConfirmed;
        this.reservations = reservations;
        this.gateway = gateway;
        this.token = token;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.confirmation_row, parent, false);

        return new ConfirmationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.roomNameTxt.setText(String.valueOf(roomName.get(position)));
        holder.dateTxt.setText(String.valueOf(reservationDate.get(position)));

        if (reservationConfirmed.get(position).equals("false"))
            holder.confirmationTxt.setText(R.string.not_confirmed);
        else
            holder.confirmationTxt.setText(R.string.confirmed);

        holder.confirmBtn.setOnClickListener(v -> {
            gateway.confirmReservation(token, reservations.get(position).getId());
            reservationConfirmed.set(position, "true");
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return roomName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView roomNameTxt;
        private final TextView dateTxt;
        private final TextView confirmationTxt;
        private final Button confirmBtn;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            roomNameTxt = itemView.findViewById(R.id.confirmation_room_name_textView);
            dateTxt = itemView.findViewById(R.id.confirmation_date_textView);
            confirmationTxt = itemView.findViewById(R.id.confirmation_textView);
            LinearLayout mainLayout = itemView.findViewById(R.id.confirmation_layout);
            confirmBtn = itemView.findViewById(R.id.confirmation_button);
            Animation translateAnim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translateAnim);
        }
    }
}
