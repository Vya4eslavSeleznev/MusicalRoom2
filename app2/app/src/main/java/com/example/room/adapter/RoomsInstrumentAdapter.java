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
import com.example.room.model.RoomsInstrument;
import com.example.room.model.gateways.Gateway;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RoomsInstrumentAdapter extends RecyclerView.Adapter<RoomsInstrumentAdapter.ViewHolder> {

    private final Context context;
    private final List<RoomsInstrument> roomsInstrument;
    private final ArrayList<String> roomName;
    private final ArrayList<String> instrumentName;
    private final Gateway gateway;
    private final String token;

    public RoomsInstrumentAdapter(Context context, List<RoomsInstrument> roomsInstrument,
                                  ArrayList<String> roomName, ArrayList<String> instrumentName,
                                  Gateway gateway, String token) {
        this.context = context;
        this.roomsInstrument = roomsInstrument;
        this.roomName = roomName;
        this.instrumentName = instrumentName;
        this.gateway = gateway;
        this.token = token;
    }

    @NonNull
    @NotNull
    @Override
    public RoomsInstrumentAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rooms_instrument_row, parent, false);

        return new RoomsInstrumentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.roomNameTxt.setText(String.valueOf(roomName.get(position)));
        holder.instrumentNameTxt.setText(String.valueOf(instrumentName.get(position)));

        holder.deleteBtn.setOnClickListener(v -> {
            gateway.deleteRoomsInstrument(token, roomsInstrument.get(position).getId());

            roomsInstrument.remove(position);
            roomName.remove(position);
            instrumentName.remove(position);

            notifyItemRemoved(position);
            notifyItemRangeChanged(position, roomsInstrument.size());
        });
    }

    @Override
    public int getItemCount() {
        return roomName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView roomNameTxt;
        private final TextView instrumentNameTxt;
        private final Button deleteBtn;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            roomNameTxt = itemView.findViewById(R.id.roomsinstrument_room_name_textView);
            instrumentNameTxt = itemView.findViewById(R.id.roomsinstrument_instrument_name_textView);
            deleteBtn = itemView.findViewById(R.id.rooms_instrument_delete_button);
            LinearLayout mainLayout = itemView.findViewById(R.id.rooms_instrument_layout);
            Animation translateAnim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translateAnim);
        }
    }
}
