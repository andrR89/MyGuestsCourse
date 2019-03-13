package com.andre.meusconvidados.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.andre.meusconvidados.R;
import com.andre.meusconvidados.business.GuestBusiness;
import com.andre.meusconvidados.constants.GuestConstatns;
import com.andre.meusconvidados.entity.GuestEntity;

public class GuestFormActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private GuestBusiness mGuestBusiness;
    private int guestId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_form);

        this.mViewHolder.mEditName = this.findViewById(R.id.edit_name);
        this.mViewHolder.mEditDocument = this.findViewById(R.id.edit_document);
        this.mViewHolder.mRadioNotConfirmed = this.findViewById(R.id.radio_not_confimed);
        this.mViewHolder.mRadioPresent = this.findViewById(R.id.radio_present);
        this.mViewHolder.mRadioAbsent = this.findViewById(R.id.radio_absent);
        this.mViewHolder.mButtonSave = this.findViewById(R.id.button_save);

        this.mGuestBusiness = new GuestBusiness(this);
        this.setListeners();

        this.loadDataFromActivity();
    }

    private void loadDataFromActivity() {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            this.guestId = bundle.getInt(GuestConstatns.BUNDLE_CONSTANTS.GUEST_ID);
            GuestEntity guestEntity = this.mGuestBusiness.load(this.guestId);
            this.mViewHolder.mEditName.setText(guestEntity.getName());
            this.mViewHolder.mEditDocument.setText(guestEntity.getDocument());
            if (guestEntity.getConfirmed() == GuestConstatns.CONFIRMATION.NOT_CONFIRMED) {
                this.mViewHolder.mRadioNotConfirmed.setChecked(true);
            } else if (guestEntity.getConfirmed() == GuestConstatns.CONFIRMATION.PRESENT) {
                this.mViewHolder.mRadioPresent.setChecked(true);
            } else {
                this.mViewHolder.mRadioAbsent.setChecked(true);
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.button_save) {
            this.handleSave();
        }
    }

    private void setListeners() {
        this.mViewHolder.mButtonSave.setOnClickListener(this);
    }

    private void handleSave() {

        if (!this.validateSave()) {
            return;
        }

        GuestEntity guest = new GuestEntity();
        guest.setName(this.mViewHolder.mEditName.getText().toString());
        guest.setDocument(this.mViewHolder.mEditDocument.getText().toString());
        if (this.mViewHolder.mRadioNotConfirmed.isChecked()) {
            guest.setConfirmed(GuestConstatns.CONFIRMATION.NOT_CONFIRMED);
        } else if (this.mViewHolder.mRadioPresent.isChecked()) {
            guest.setConfirmed(GuestConstatns.CONFIRMATION.PRESENT);
        } else {
            guest.setConfirmed(GuestConstatns.CONFIRMATION.ABSENT);
        }

        if (this.guestId == 0) {
            if (this.mGuestBusiness.insert(guest)) {
                Toast.makeText(this, R.string.convidado_salvo, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, R.string.erro_salvar_convidado, Toast.LENGTH_LONG).show();
            }
        }else{
            guest.setId(this.guestId);
            if (this.mGuestBusiness.update(guest)) {
                Toast.makeText(this, R.string.convidado_salvo, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, R.string.erro_salvar_convidado, Toast.LENGTH_LONG).show();
            }
        }


        this.finish();
    }

    private boolean validateSave() {

        if (this.mViewHolder.mEditName.getText().toString().equals("")) {
            this.mViewHolder.mEditName.setError(getString(R.string.nome_obrigatorio));
            return false;
        }

        return true;
    }

    private static class ViewHolder {
        EditText mEditName;
        EditText mEditDocument;
        RadioButton mRadioNotConfirmed;
        RadioButton mRadioPresent;
        RadioButton mRadioAbsent;
        Button mButtonSave;
    }
}
