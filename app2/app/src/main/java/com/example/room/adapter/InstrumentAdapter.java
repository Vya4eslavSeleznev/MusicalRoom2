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
import com.example.room.model.Instrument;
import com.example.room.model.gateways.Gateway;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class InstrumentAdapter extends RecyclerView.Adapter<InstrumentAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<String> instrumentName;
    private final ArrayList<String> instrumentDescription;
    private final Gateway gateway;
    private final String token;
    private final List<Instrument> instruments;

    public InstrumentAdapter(Context context, ArrayList<String> instrumentName,
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

        return new InstrumentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.instrumentNameTxt.setText(String.valueOf(instrumentName.get(position)));
        holder.instrumentDescriptionTxt.setText(String.valueOf(instrumentDescription.get(position)));

        holder.deleteBtn.setOnClickListener(v -> {
            gateway.deleteInstrument(token, instruments.get(position).getId());

            instruments.remove(position);
            instrumentName.remove(position);
            instrumentDescription.remove(position);

            notifyItemRemoved(position);
            notifyItemRangeChanged(position, instruments.size());
        });
    }

    @Override
    public int getItemCount() {
        return instrumentName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView instrumentNameTxt;
        private final TextView instrumentDescriptionTxt;
        private final Button deleteBtn;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            instrumentNameTxt = itemView.findViewById(R.id.admin_first_textView);
            instrumentDescriptionTxt = itemView.findViewById(R.id.admin_second_textView);
            LinearLayout mainLayout = itemView.findViewById(R.id.adminLayout);
            deleteBtn = itemView.findViewById(R.id.delete_admin_button);
            Animation translateAnim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translateAnim);
        }
    }
}
