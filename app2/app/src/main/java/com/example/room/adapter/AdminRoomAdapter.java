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
import com.example.room.view.activity.RoomActivity;
import com.example.room.model.gateways.Gateway;
import com.example.room.model.Room;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdminRoomAdapter extends RecyclerView.Adapter<AdminRoomAdapter.ViewHolder>{

    private final Context context;
    private final ArrayList<String> roomName;
    private final ArrayList<String> roomDescription;
    private final ArrayList<String> roomPrice;
    private final Gateway gateway;
    private final String token;
    private final List<Room> rooms;
    private OnItemClickListener listener;

    public AdminRoomAdapter(Context context, ArrayList<String> roomName, ArrayList<String> roomDescription,
                            ArrayList<String> roomPrice, Gateway gateway, String token, List<Room> rooms) {
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
        View view = inflater.inflate(R.layout.reservation_row, parent, false);

        return new AdminRoomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.roomNameTxt.setText(String.valueOf(roomName.get(position)));
        holder.roomDescriptionTxt.setText(String.valueOf(roomDescription.get(position)));
        holder.roomPriceTxt.setText(String.valueOf(roomPrice.get(position)));
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

    public void setOnItemClickListener(AdminRoomAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView roomNameTxt;
        private final TextView roomDescriptionTxt;
        private final TextView roomPriceTxt;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            roomNameTxt = itemView.findViewById(R.id.reservation_room_name_textView);
            roomDescriptionTxt = itemView.findViewById(R.id.reservation_date_or_description_textView);
            roomPriceTxt = itemView.findViewById(R.id.reservation_price_textView);
            LinearLayout mainLayout = itemView.findViewById(R.id.reservation_layout);
            Button deleteBtn = itemView.findViewById(R.id.reservation_delete_button);
            Animation translateAnim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translateAnim);

            deleteBtn.setOnClickListener(v -> {
                gateway.deleteRoom(token, rooms.get(getAdapterPosition()).getId());
                rooms.remove(getAdapterPosition());

                Intent intent = new Intent(context, RoomActivity.class);
                context.startActivity(intent);
            });

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
