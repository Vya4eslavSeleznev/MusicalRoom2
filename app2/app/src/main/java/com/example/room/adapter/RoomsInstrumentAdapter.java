package com.example.room.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RoomsInstrumentAdapter extends RecyclerView.Adapter<RoomsInstrumentAdapter.ViewHolder>{

    private final Context context;
    private final ArrayList<String> roomName;
    private final ArrayList<String> roomPrice;
    private final ArrayList<String> roomDescription;
    private final ArrayList<String> instrumentName;
    private final ArrayList<String> instrumentDescription;
    private OnItemClickListener listener;

    public RoomsInstrumentAdapter(Context context, ArrayList<String> roomName, ArrayList<String> roomPrice,
                                  ArrayList<String> roomDescription, ArrayList<String> instrumentName,
                                  ArrayList<String> instrumentDescription) {
        this.context = context;
        this.roomName = roomName;
        this.roomPrice = roomPrice;
        this.roomDescription = roomDescription;
        this.instrumentName = instrumentName;
        this.instrumentDescription = instrumentDescription;
    }

    @NonNull
    @NotNull
    @Override
    public RoomsInstrumentAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rooms_instrument_row, parent, false);

        return new RoomsInstrumentAdapter.ViewHolder(view, this.listener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.roomNameTxt.setText(String.valueOf(roomName.get(position)));
        holder.priceTxt.setText(String.valueOf(roomPrice.get(position)));
        holder.roomDescriptionTxt.setText(String.valueOf(roomDescription.get(position)));
        holder.instrumentNameTxt.setText(String.valueOf(instrumentName.get(position)));
        holder.instrumentDescriptionTxt.setText(String.valueOf(instrumentDescription.get(position)));
    }

    @Override
    public int getItemCount() {
        return roomName.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView roomNameTxt;
        private final TextView priceTxt;
        private final TextView roomDescriptionTxt;
        private final TextView instrumentNameTxt;
        private final TextView instrumentDescriptionTxt;

        public ViewHolder(@NonNull @NotNull View itemView, OnItemClickListener listener) {
            super(itemView);

            roomNameTxt = itemView.findViewById(R.id.room_name_textView);
            priceTxt = itemView.findViewById(R.id.price_textView);
            roomDescriptionTxt = itemView.findViewById(R.id.room_description_textView);
            instrumentNameTxt = itemView.findViewById(R.id.instrument_name_textView);
            instrumentDescriptionTxt = itemView.findViewById(R.id.instrument_description_textView);
            LinearLayout mainLayout = itemView.findViewById(R.id.rooms_instrument_layout);
            Animation translateAnim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translateAnim);

            itemView.setOnClickListener(v -> {
                if(listener != null) {
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}
