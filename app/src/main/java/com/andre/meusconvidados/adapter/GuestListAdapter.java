package com.andre.meusconvidados.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andre.meusconvidados.R;
import com.andre.meusconvidados.entity.GuestEntity;
import com.andre.meusconvidados.listener.OnGuestInteractionListener;
import com.andre.meusconvidados.viewholder.GuestViewHolder;

import java.util.List;

public class GuestListAdapter extends RecyclerView.Adapter<GuestViewHolder> {

    private List<GuestEntity> mGuestList;
    private OnGuestInteractionListener onGuestInteractionListener;

    public GuestListAdapter(List<GuestEntity> guestEntityList, OnGuestInteractionListener listener) {
        this.mGuestList = guestEntityList;
        this.onGuestInteractionListener = listener;
    }

    // Instancia Layout pra manipular
    @NonNull
    @Override
    public GuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.row_guest_list, parent, false);

        return new GuestViewHolder(view, parent.getContext());
    }

    // Troca do layout para posição da lista
    @Override
    public void onBindViewHolder(@NonNull GuestViewHolder holder, int position) {
        GuestEntity guestEntity = this.mGuestList.get(position);
        holder.bindData(guestEntity, this.onGuestInteractionListener);
    }

    // Qnt Elementos
    @Override
    public int getItemCount() {
        return this.mGuestList.size();
    }
}
