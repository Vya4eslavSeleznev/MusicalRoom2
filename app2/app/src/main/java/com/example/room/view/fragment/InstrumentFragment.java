package com.example.room.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.room.R;
import com.example.room.databinding.FragmentInstrumentBinding;
import com.example.room.presenter.InstrumentPresenter;
import com.example.room.view.activity.InstrumentActivity;

public class InstrumentFragment extends Fragment implements InstrumentPresenter.View {

    private InstrumentPresenter presenter;
    private FragmentInstrumentBinding binding;
    private Button addInstrument;
    private Button instrumentsButton;
    private TextView nameTextView;
    private TextView descriptionTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInstrumentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        addInstrument = root.findViewById(R.id.add_instrument_button);
        nameTextView = root.findViewById(R.id.instrument_name_edit);
        descriptionTextView = root.findViewById(R.id.instrument_description_edit);
        instrumentsButton = root.findViewById(R.id.view_instruments_button);

        presenter = new InstrumentPresenter(this);

        addInstrument.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addInstrumentEventLogic();
            }
        });

        instrumentsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InstrumentActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public SharedPreferences getSharedPreferences() {
        return getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
    }

    @Override
    public void addInstrumentEventLogic() {
        if(!nameTextView.getText().toString().matches("") ||
                !descriptionTextView.getText().toString().matches(""))
        {
            presenter.addInstrument(getSharedPreferences().getString("token", null),
                    nameTextView.getText().toString(), descriptionTextView.getText().toString());

            Toast.makeText(getActivity(), "Added successfully!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), "Empty field", Toast.LENGTH_SHORT).show();
        }
    }
}
