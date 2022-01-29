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
import com.example.room.model.Room;
import com.example.room.model.gateways.Gateway;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder>{

    private final Context context;
    private final ArrayList<String> roomName;
    private final ArrayList<String> roomDescription;
    private final ArrayList<String> roomPrice;
    private final Gateway gateway;
    private final String token;
    private final List<Room> rooms;

    public RoomAdapter(Context context, ArrayList<String> roomName, ArrayList<String> roomDescription,
                       ArrayList<String> roomPrice, List<Room> rooms, Gateway gateway, String token) {
        this.context = context;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.roomPrice = roomPrice;
        this.gateway = gateway;
        this.token = token;
        this.rooms = rooms;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.admin_room_row, parent, false);

        return new RoomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.roomNameTxt.setText(String.valueOf(roomName.get(position)));
        holder.roomDescriptionTxt.setText(String.valueOf(roomDescription.get(position)));

        String price = roomPrice.get(position) + "$";
        holder.roomPriceTxt.setText(price);

        holder.deleteBtn.setOnClickListener(v -> {
            gateway.deleteRoom(token, rooms.get(position).getId());

            rooms.remove(position);
            roomName.remove(position);
            roomDescription.remove(position);
            roomPrice.remove(position);

            notifyItemRemoved(position);
            notifyItemRangeChanged(position, rooms.size());
        });
    }

    @Override
    public int getItemCount() {
        return roomName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView roomNameTxt;
        private final TextView roomDescriptionTxt;
        private final TextView roomPriceTxt;
        private final Button deleteBtn;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            roomNameTxt = itemView.findViewById(R.id.admin_room_name_textView);
            roomDescriptionTxt = itemView.findViewById(R.id.admin_room_description_textView);
            roomPriceTxt = itemView.findViewById(R.id.admin_room_price_textView);
            LinearLayout mainLayout = itemView.findViewById(R.id.admin_room_layout);
            deleteBtn = itemView.findViewById(R.id.admin_room_delete_button);
            Animation translateAnim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translateAnim);
        }
    }
}
