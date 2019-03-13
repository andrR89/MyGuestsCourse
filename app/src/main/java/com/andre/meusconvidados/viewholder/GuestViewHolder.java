package com.andre.meusconvidados.viewholder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andre.meusconvidados.R;
import com.andre.meusconvidados.entity.GuestEntity;
import com.andre.meusconvidados.listener.OnGuestInteractionListener;

public class GuestViewHolder extends RecyclerView.ViewHolder {

    private TextView mTextName;
    private ImageView mImageRemove;
    private Context context;

    public GuestViewHolder(@NonNull View itemView, Context context) {
        super(itemView);

        this.mTextName = itemView.findViewById(R.id.text_name);
        this.mImageRemove = itemView.findViewById(R.id.image_remove);
        this.context = context;
    }

    public void bindData(final GuestEntity guestEntity, final OnGuestInteractionListener listener) {
        this.mTextName.setText(guestEntity.getName());
        this.mImageRemove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                showRemoveDialog(guestEntity, listener);
            }
        });
        this.mTextName.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                listener.onListClick(guestEntity.getId());
            }
        });

        this.mTextName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                showRemoveDialog(guestEntity, listener);

                return true;
            }
        });
    }

    private void showRemoveDialog(final GuestEntity guestEntity, final OnGuestInteractionListener listener) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.remover_convidado)
                .setMessage(R.string.question_remove_convidoda)
                .setIcon(R.drawable.remove)
                .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onDeleteClick(guestEntity.getId());
                    }
                })
                .setNeutralButton(R.string.nao, null)
                .show();
    }
}
