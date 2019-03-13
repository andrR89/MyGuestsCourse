package com.andre.meusconvidados.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.andre.meusconvidados.R;
import com.andre.meusconvidados.adapter.GuestListAdapter;
import com.andre.meusconvidados.business.GuestBusiness;
import com.andre.meusconvidados.constants.GuestConstatns;
import com.andre.meusconvidados.entity.GuestCount;
import com.andre.meusconvidados.entity.GuestEntity;
import com.andre.meusconvidados.listener.OnGuestInteractionListener;

import org.w3c.dom.Text;

import java.util.List;


public class AllInvitedFragment extends Fragment {

    private ViewHolder mViewHolder = new ViewHolder();
    private GuestBusiness mGuestBusiness;
    private OnGuestInteractionListener mOnGuestInteractionListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_invited, container, false);

        // Obter Recycler
        this.mViewHolder.mRecyclerAllInvited = (RecyclerView) view.findViewById(R.id.recycler_all_invited);

        this.mViewHolder.mTextPresentCount = view.findViewById(R.id.text_present_count);
        this.mViewHolder.mTextAbsentCount = view.findViewById(R.id.text_absent_count);
        this.mViewHolder.mTextAllInvented = view.findViewById(R.id.text_all_invited);

        this.mGuestBusiness = new GuestBusiness(view.getContext());

        this.mOnGuestInteractionListener = new OnGuestInteractionListener() {
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
                loadDashboard();
                loadGuests();
            }
        };

        // Definir Layout
        this.mViewHolder.mRecyclerAllInvited.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.loadDashboard();
        this.loadGuests();
    }

    private void loadGuests() {
        List<GuestEntity> guestEntityList = this.mGuestBusiness.getInvited();

        // Definir Adapter
        GuestListAdapter guestListAdapter = new GuestListAdapter(guestEntityList, this.mOnGuestInteractionListener);
        this.mViewHolder.mRecyclerAllInvited.setAdapter(guestListAdapter);

        // Notifica que foi alterado
        guestListAdapter.notifyDataSetChanged();
    }

    private void loadDashboard() {
        GuestCount guestCount =this.mGuestBusiness.loadDashboard();
        this.mViewHolder.mTextPresentCount.setText(String.valueOf(guestCount.getPresentCount()));
        this.mViewHolder.mTextAbsentCount.setText(String.valueOf(guestCount.getAbsentCount()));
        this.mViewHolder.mTextAllInvented.setText(String.valueOf(guestCount.getAllInvitedCount()));
    }

    private static class ViewHolder {
        RecyclerView mRecyclerAllInvited;
        TextView mTextPresentCount;
        TextView mTextAbsentCount;
        TextView mTextAllInvented;
    }

}
