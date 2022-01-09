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
import com.example.room.view.activity.AdminInstrumentActivity;
import com.example.room.gateways.Gateway;
import com.example.room.model.Instrument;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdminInstrumentAdapter extends RecyclerView.Adapter<AdminInstrumentAdapter.ViewHolder> {

    private Context context;
    private ArrayList instrumentName, instrumentDescription;
    private Animation translateAnim;
    private Gateway gateway;
    private String token;
    private List<Instrument> instruments;

    public AdminInstrumentAdapter(Context context, ArrayList instrumentName, ArrayList instrumentDescription,
                             List<Instrument> instruments, Gateway gateway, String token) {
        this.context = context;
        this.instrumentName = instrumentName;
        this.instrumentDescription = instrumentDescription;
        this.instruments = instruments;
        this.gateway = gateway;
        this.token = token;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.admin_row, parent, false);

        return new AdminInstrumentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.instrumentNameTxt.setText(String.valueOf(instrumentName.get(position)));
        holder.instrumentDescriptionTxt.setText(String.valueOf(instrumentDescription.get(position)));
    }

    @Override
    public int getItemCount() {
        return instrumentName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView instrumentNameTxt, instrumentDescriptionTxt;
        private LinearLayout mainLayout;
        private Button deleteBtn;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            instrumentNameTxt = itemView.findViewById(R.id.admin_first_textView);
            instrumentDescriptionTxt = itemView.findViewById(R.id.admin_second_textView);
            mainLayout = itemView.findViewById(R.id.adminLayout);
            deleteBtn = itemView.findViewById(R.id.delete_admin_button);
            translateAnim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translateAnim);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gateway.deleteInstrument(token, instruments.get(getAdapterPosition()).getId());
                    instruments.remove(getAdapterPosition());

                    Intent intent = new Intent(context, AdminInstrumentActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}
