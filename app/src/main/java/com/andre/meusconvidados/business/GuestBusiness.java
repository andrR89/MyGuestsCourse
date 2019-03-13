package com.andre.meusconvidados.business;

import android.content.Context;

import com.andre.meusconvidados.constants.DatabaseConstants;
import com.andre.meusconvidados.constants.GuestConstatns;
import com.andre.meusconvidados.entity.GuestCount;
import com.andre.meusconvidados.entity.GuestEntity;
import com.andre.meusconvidados.repository.GuestRepository;

import java.util.List;

public class GuestBusiness {

    private GuestRepository mGuestRepository;

    public GuestBusiness(Context context) {
        this.mGuestRepository = GuestRepository.getInstance(context);
    }

    public boolean insert(GuestEntity entity) {
        return this.mGuestRepository.insert(entity);
    }


    public List<GuestEntity> getInvited() {
        return this.mGuestRepository.getGuestsByQuery("Select * from " + DatabaseConstants.GUEST.TABLE_NAME);
    }

    public List<GuestEntity> getAbsent() {
        return this.mGuestRepository.getGuestsByQuery("Select * from " + DatabaseConstants.GUEST.TABLE_NAME +
                " where " + DatabaseConstants.GUEST.COLUMNS.PRESENCE + " = " + GuestConstatns.CONFIRMATION.ABSENT);
    }

    public List<GuestEntity> getPresent() {
        return this.mGuestRepository.getGuestsByQuery("Select * from " + DatabaseConstants.GUEST.TABLE_NAME +
                " where " + DatabaseConstants.GUEST.COLUMNS.PRESENCE + " = " + GuestConstatns.CONFIRMATION.PRESENT);
    }

    public GuestEntity load(int guestId) {
        return this.mGuestRepository.load(guestId);
    }

    public boolean update(GuestEntity guest) {
        return this.mGuestRepository.update(guest);
    }

    public Boolean remove(int id) {
        return this.mGuestRepository.remove(id);
    }

    public GuestCount loadDashboard() {
        return this.mGuestRepository.loadDashboard();
    }
}
