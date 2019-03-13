package com.andre.meusconvidados.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.andre.meusconvidados.R;
import com.andre.meusconvidados.adapter.GuestListAdapter;
import com.andre.meusconvidados.business.GuestBusiness;
import com.andre.meusconvidados.constants.GuestConstatns;
import com.andre.meusconvidados.entity.GuestEntity;
import com.andre.meusconvidados.listener.OnGuestInteractionListener;

import java.util.List;


public class PresentFragment extends Fragment {

    private ViewHolder mViewHolder = new ViewHolder();
    private OnGuestInteractionListener mOnGuestInteractionListener;
    private GuestBusiness mGuestBusiness;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_present, container, false);

        // Obter Recycler
        this.mViewHolder.mRecyclerPresent = (RecyclerView) view.findViewById(R.id.recycler_present);
        this.mGuestBusiness = new GuestBusiness(view.getContext());

        this.mOnGuestInteractionListener =  new OnGuestInteractionListener() {
            @Override
            public void onListClick(int id) {
                // Abrir Activity de formulario
                Bundle bundle = new Bundle();
                bundle.putInt(GuestConstatns.BUNDLE_CONSTANTS.GUEST_ID, id);

                Intent intent = new Intent(getContext(), GuestFormActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);
            }

            @Override
            public void onDeleteClick(int id) {
                mGuestBusiness.remove(id);
                Toast.makeText(getContext(), getString(R.string.convidado_removido), Toast.LENGTH_LONG).show();
                loadGuests();
            }
        };


        // Definir Layout
        this.mViewHolder.mRecyclerPresent.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.loadGuests();
    }

    private void loadGuests() {
        List<GuestEntity> guestEntityList = this.mGuestBusiness.getPresent();

        // Definir Adapter
        GuestListAdapter guestListAdapter = new GuestListAdapter(guestEntityList, this.mOnGuestInteractionListener);
        this.mViewHolder.mRecyclerPresent.setAdapter(guestListAdapter);

        // Notifica que foi alterado
        guestListAdapter.notifyDataSetChanged();
    }

    private static class ViewHolder {
        RecyclerView mRecyclerPresent;
    }

}
