package com.mad2021.maddatahandling.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.mad2021.maddatahandling.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserInfo.db";

    public DBHelper( Context context) {super(context, DATABASE_NAME, null, 1);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + UsersMaster.Users.TABLE_NAME + " (" +
                        UsersMaster.Users._ID+ " INTEGER PRIMARY KEY," +
                        UsersMaster.Users.COLUMN_NAME_USERNAME+ " TEXT,"+
                        UsersMaster.Users.COLUMN_NAME_PASSWORD+ " TEXT)";

        db.execSQL(SQL_CREATE_ENTRIES);

    }

    public Long addInfo(String userName, String password){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(UsersMaster.Users.COLUMN_NAME_USERNAME, userName);
        values.put(UsersMaster.Users.COLUMN_NAME_PASSWORD, password);

        return db.insert(UsersMaster.Users.TABLE_NAME, null, values);
    }

    // new code
    public List readAllInfo(){
        SQLiteDatabase db = getReadableDatabase();

        String [] projection = {
                UsersMaster.Users._ID,
                UsersMaster.Users.COLUMN_NAME_USERNAME,
                UsersMaster.Users.COLUMN_NAME_PASSWORD
        };

        String sortOrder = UsersMaster.Users.COLUMN_NAME_USERNAME + " DESC";

        Cursor cursor = db.query(
                UsersMaster.Users.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List info = new ArrayList<>();

        while (cursor.moveToNext()){
            String userName = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USERNAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_PASSWORD));

            info.add(userName+": "+password);
        }
        cursor.close();

        return info;
    }

    public void deleteInfo(String userName){
        SQLiteDatabase db = getReadableDatabase();
        String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + " LIKE ?";
        String [] selectionArgs = { userName };
        db.delete(UsersMaster.Users.TABLE_NAME,selection,selectionArgs);


    }

    public void updateInfo(View view, String userName, String password){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(UsersMaster.Users.COLUMN_NAME_PASSWORD, password);

        String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + " Like ?";
        String[] selectionArgs = {userName};

        int count = db.update(
                UsersMaster.Users.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );

        Snackbar snackbar = Snackbar.make(view, count + " rows effected",Snackbar.LENGTH_LONG);
        snackbar.setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE);
        snackbar.show();



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
