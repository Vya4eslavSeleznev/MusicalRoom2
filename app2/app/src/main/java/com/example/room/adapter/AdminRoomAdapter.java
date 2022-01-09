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
import com.example.room.gateways.Gateway;
import com.example.room.model.Room;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdminRoomAdapter extends RecyclerView.Adapter<AdminRoomAdapter.ViewHolder>{

    private Context context;
    private ArrayList roomName, roomDescription, roomPrice;
    private Animation translateAnim;
    private Gateway gateway;
    private String token;
    private List<Room> rooms;
    private OnItemClickListener listener;

    public AdminRoomAdapter(Context context, ArrayList roomName, ArrayList roomDescription,
                            ArrayList roomPrice, Gateway gateway, String token, List<Room> rooms) {
        this.context = context;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.roomPrice = roomPrice;
        this.translateAnim = translateAnim;
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

        private TextView roomNameTxt, roomDescriptionTxt, roomPriceTxt;
        private LinearLayout mainLayout;
        private Button deleteBtn;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            roomNameTxt = itemView.findViewById(R.id.reservation_room_name_textView);
            roomDescriptionTxt = itemView.findViewById(R.id.reservation_date_or_description_textView);
            roomPriceTxt = itemView.findViewById(R.id.reservation_price_textView);
            mainLayout = itemView.findViewById(R.id.reservation_layout);
            deleteBtn = itemView.findViewById(R.id.reservation_delete_button);
            translateAnim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translateAnim);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gateway.deleteRoom(token, rooms.get(getAdapterPosition()).getId());
                    rooms.remove(getAdapterPosition());

                    Intent intent = new Intent(context, RoomActivity.class);
                    context.startActivity(intent);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
