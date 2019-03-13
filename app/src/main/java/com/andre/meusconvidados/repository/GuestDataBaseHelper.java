package com.andre.meusconvidados.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.andre.meusconvidados.constants.DatabaseConstants;

public class GuestDataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MeusConvidados.db";
    private static final String SQL_CREATE_TABLE_GUEST =
            "CREATE TABLE " + DatabaseConstants.GUEST.TABLE_NAME + " (" +
                    DatabaseConstants.GUEST.COLUMNS.ID + " integer primary key autoincrement, " +
                    DatabaseConstants.GUEST.COLUMNS.NAME + " text, " +
                    DatabaseConstants.GUEST.COLUMNS.DOCUMENT + " text null, " +
                    DatabaseConstants.GUEST.COLUMNS.PRESENCE + " integer" +
            ");";

    private static final String DROP_TABLE_GUEST = "DROP TABLE IF EXISTS " + DatabaseConstants.GUEST.TABLE_NAME;

    public GuestDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_GUEST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE_GUEST);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_GUEST);
    }
}
