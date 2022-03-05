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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {

    private Context context;
    private ArrayList customerName, customerPhone;
    private Animation translateAnim;

    public CustomerAdapter(Context context, ArrayList customerName, ArrayList customerPhone) {
        this.context = context;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.instrument_row, parent, false);

        return new CustomerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.customerNameTxt.setText(String.valueOf(customerName.get(position)));
        holder.customerPhoneTxt.setText(String.valueOf(customerPhone.get(position)));
    }

    @Override
    public int getItemCount() {
        return customerName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView customerNameTxt, customerPhoneTxt;
        private LinearLayout mainLayout;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            customerNameTxt = itemView.findViewById(R.id.equipment_instrument_name_textView);
            customerPhoneTxt = itemView.findViewById(R.id.equipment_instrument_description_textView);
            mainLayout = itemView.findViewById(R.id.instrument_equipment_layout);
            translateAnim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translateAnim);
        }
    }
}
