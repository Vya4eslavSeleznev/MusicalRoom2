package com.example.room.view.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room.R;
import com.example.room.adapter.ConfirmationAdapter;
import com.example.room.databinding.FragmentConfirmationBinding;
import com.example.room.model.Reservation;
import com.example.room.model.gateways.Gateway;
import com.example.room.presenter.Repository;
import com.example.room.presenter.fragment.ConfirmationPresenter;

import java.util.ArrayList;
import java.util.List;

public class ConfirmationFragment extends Fragment implements ConfirmationPresenter.View {

    private FragmentConfirmationBinding binding;
    private ImageView emptyImageView;
    private TextView emptyTextView;
    private RecyclerView recyclerView;
    private ConfirmationPresenter presenter;
    private Repository repository;
    private ArrayList<String> roomName, roomPrice, reservationDate, reservationConfirmed;
    private ConfirmationAdapter reservationAdapter;

    private ViewGroup container;
    LayoutInflater inflater;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentConfirmationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.container = container;
        this.inflater = inflater;

        recyclerView = root.findViewById(R.id.confirmation_recyclerview);
        emptyImageView = root.findViewById(R.id.confirmation_empty_imageView);
        emptyTextView = root.findViewById(R.id.confirmation_empty_textView);

        roomName = new ArrayList<>();
        roomPrice = new ArrayList<>();
        reservationDate = new ArrayList<>();
        reservationConfirmed = new ArrayList<>();

        presenter = new ConfirmationPresenter(this);
        repository = new Repository();

        presenter.setReservations();
        presenter.setRecycleView();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public SharedPreferences getSharedPreferences() {
        return repository.getSharedPreferences(getActivity());
    }

    @Override
    public void setReservations(List<Reservation> reservations) {
        repository.setReservations(reservations, roomName, roomPrice, reservationDate,
                reservationConfirmed, emptyImageView, emptyTextView);
    }

    @Override
    public void setRecycleView(List<Reservation> reservations, Gateway gateway,String token) {
        reservationAdapter = new ConfirmationAdapter(getActivity(), roomName, reservationDate,
                reservationConfirmed, reservations, gateway, token, inflater, container);

        recyclerView.setAdapter(reservationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void updateRecycleView() {
        reservationAdapter.notifyDataSetChanged();
    }
}
