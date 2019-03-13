package com.andre.meusconvidados.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.andre.meusconvidados.constants.DatabaseConstants;
import com.andre.meusconvidados.constants.GuestConstatns;
import com.andre.meusconvidados.entity.GuestCount;
import com.andre.meusconvidados.entity.GuestEntity;

import java.util.ArrayList;
import java.util.List;

public class GuestRepository {

    private static GuestRepository INSTANCE;
    private GuestDataBaseHelper mGuestDataBaseHelper;

    private GuestRepository(Context context){
        this.mGuestDataBaseHelper = new GuestDataBaseHelper(context);
    }

    public static synchronized GuestRepository getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new GuestRepository(context);
        }
        return INSTANCE;
    }

    public Boolean insert(GuestEntity entity) {
        try{
            // pega instacia do banco
            SQLiteDatabase sqlLiteDb = this.mGuestDataBaseHelper.getWritableDatabase();

            // Append Content Values
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseConstants.GUEST.COLUMNS.NAME, entity.getName());
            contentValues.put(DatabaseConstants.GUEST.COLUMNS.DOCUMENT, entity.getDocument());
            contentValues.put(DatabaseConstants.GUEST.COLUMNS.PRESENCE, entity.getConfirmed());

            // Execute
            sqlLiteDb.insert(DatabaseConstants.GUEST.TABLE_NAME, null, contentValues);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public Boolean update(GuestEntity entity){
        try{
            // pega instacia do banco
            SQLiteDatabase sqlLiteDb = this.mGuestDataBaseHelper.getWritableDatabase();

            // Append Content Values
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseConstants.GUEST.COLUMNS.NAME, entity.getName());
            contentValues.put(DatabaseConstants.GUEST.COLUMNS.DOCUMENT, entity.getDocument());
            contentValues.put(DatabaseConstants.GUEST.COLUMNS.PRESENCE, entity.getConfirmed());

            // Where clause
            String selection = DatabaseConstants.GUEST.COLUMNS.ID + " = ?";
            String[] selectionArgs = {String.valueOf(entity.getId())};

            // Execute
            sqlLiteDb.update(DatabaseConstants.GUEST.TABLE_NAME, contentValues, selection, selectionArgs);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    // RAW QUERY METHOD 1 (Select * from guest)
    public List<GuestEntity> getGuestsByQuery(String query) {
        List<GuestEntity> list = new ArrayList<>();

        try{

            SQLiteDatabase sqLiteDatabase = this.mGuestDataBaseHelper.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);

            // Percorre cada linha que retornou do banco
            if( cursor != null && cursor.getCount() > 0){
                while (cursor.moveToNext()){
                    GuestEntity guest = new GuestEntity();
                    guest.setId(cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.ID)));
                    guest.setName(cursor.getString(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.NAME)));
                    guest.setConfirmed(cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.PRESENCE)));
                    list.add(guest);
                }
            }

            // Fechar cursor
            if(cursor != null){
                cursor.close();
            }

            return list;

        }catch (Exception e){
            return list;
        }
    }

    // QUERY METHOD 2
    public GuestEntity load(int id) {
        GuestEntity entity = new GuestEntity();

        try{
            SQLiteDatabase sqLiteDatabase = this.mGuestDataBaseHelper.getReadableDatabase();

            String[] projection = {
                    DatabaseConstants.GUEST.COLUMNS.ID,
                    DatabaseConstants.GUEST.COLUMNS.NAME,
                    DatabaseConstants.GUEST.COLUMNS.PRESENCE,
                    DatabaseConstants.GUEST.COLUMNS.DOCUMENT
            };

            String selection = DatabaseConstants.GUEST.COLUMNS.ID + " = ?";
            String[] selectionArgs = {String.valueOf(id)};

            Cursor cursor = sqLiteDatabase.query(DatabaseConstants.GUEST.TABLE_NAME, projection, selection, selectionArgs, null, null, DatabaseConstants.GUEST.COLUMNS.NAME);

            if( cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                entity.setId(cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.ID)));
                entity.setName(cursor.getString(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.NAME)));
                entity.setConfirmed(cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.PRESENCE)));
                entity.setDocument(cursor.getString(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.DOCUMENT)));
            }

            // Fechar cursor
            if(cursor != null){
                cursor.close();
            }

            return entity;
        }catch (Exception e){
            return entity;
        }
    }

    public Boolean remove(int id) {
        try{
            // pega instacia do banco
            SQLiteDatabase sqlLiteDb = this.mGuestDataBaseHelper.getWritableDatabase();

            // Where clause
            String selection = DatabaseConstants.GUEST.COLUMNS.ID + " = ?";
            String[] selectionArgs = {String.valueOf(id)};

            // Execute
            sqlLiteDb.delete(DatabaseConstants.GUEST.TABLE_NAME, selection, selectionArgs);
            return true;
        }catch(Exception e){
            return false;
        }

    }

    public GuestCount loadDashboard() {
        GuestCount count = new GuestCount(0,0,0);
        count.setPresentCount(this.getCountForPresenseType(GuestConstatns.CONFIRMATION.PRESENT));
        count.setAbsentCount(this.getCountForPresenseType(GuestConstatns.CONFIRMATION.ABSENT));
        count.setAllInvitedCount(this.getGuestsByQuery("Select * from " + DatabaseConstants.GUEST.TABLE_NAME).size());
        return count;
    }

    private int getCountForPresenseType(int type){
        int count = 0;
        try{
            // pega instacia do banco
            SQLiteDatabase sqlLiteDb = this.mGuestDataBaseHelper.getReadableDatabase();

            String query = "SELECT COUNT(*) FROM " + DatabaseConstants.GUEST.TABLE_NAME +
                    " WHERE " + DatabaseConstants.GUEST.COLUMNS.PRESENCE + " = " + type;
            Cursor cursor = sqlLiteDb.rawQuery(query, null);

            if(cursor != null && cursor.getCount() > 0){
                cursor.moveToFirst();
                count = cursor.getInt(0);
            }
            return count;
        }catch(Exception e){
            return count;
        }
    }
}
