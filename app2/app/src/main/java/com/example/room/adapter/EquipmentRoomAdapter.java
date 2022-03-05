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

public class EquipmentRoomAdapter extends RecyclerView.Adapter<EquipmentRoomAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<String> roomName;
    private final ArrayList<String> roomDescription;
    private final ArrayList<String> roomPrice;
    private OnItemClickListener listener;

    public EquipmentRoomAdapter(Context context, ArrayList<String> roomName, ArrayList<String> roomDescription,
                                ArrayList<String> roomPrice) {
        this.context = context;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.roomPrice = roomPrice;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.room_row, parent, false);

        return new EquipmentRoomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.roomNameTxt.setText(String.valueOf(roomName.get(position)));
        holder.roomDescriptionTxt.setText(String.valueOf(roomDescription.get(position)));

        String price = roomPrice.get(position) + "$";
        holder.roomPriceTxt.setText(price);
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

    public void setOnItemClickListener(EquipmentRoomAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView roomNameTxt;
        private final TextView roomDescriptionTxt;
        private final TextView roomPriceTxt;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            roomNameTxt = itemView.findViewById(R.id.equipment_room_name_textView);
            roomDescriptionTxt = itemView.findViewById(R.id.equipment_description_textView);
            roomPriceTxt = itemView.findViewById(R.id.equipment_price_textView);
            LinearLayout mainLayout = itemView.findViewById(R.id.room_equipment_layout);
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
