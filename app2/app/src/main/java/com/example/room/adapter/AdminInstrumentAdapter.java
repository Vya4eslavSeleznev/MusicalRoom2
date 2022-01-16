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
import com.example.room.model.gateways.Gateway;
import com.example.room.model.Instrument;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdminInstrumentAdapter extends RecyclerView.Adapter<AdminInstrumentAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<String> instrumentName;
    private final ArrayList<String> instrumentDescription;
    private final Gateway gateway;
    private final String token;
    private final List<Instrument> instruments;

    public AdminInstrumentAdapter(Context context, ArrayList<String> instrumentName,
                                  ArrayList<String> instrumentDescription, List<Instrument> instruments,
                                  Gateway gateway, String token) {
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
        private final TextView instrumentNameTxt;
        private final TextView instrumentDescriptionTxt;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            instrumentNameTxt = itemView.findViewById(R.id.admin_first_textView);
            instrumentDescriptionTxt = itemView.findViewById(R.id.admin_second_textView);
            LinearLayout mainLayout = itemView.findViewById(R.id.adminLayout);
            Button deleteBtn = itemView.findViewById(R.id.delete_admin_button);
            Animation translateAnim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translateAnim);

            deleteBtn.setOnClickListener(v -> {
                gateway.deleteInstrument(token, instruments.get(getAdapterPosition()).getId());
                instruments.remove(getAdapterPosition());

                Intent intent = new Intent(context, AdminInstrumentActivity.class);
                context.startActivity(intent);
            });
        }
    }
}
