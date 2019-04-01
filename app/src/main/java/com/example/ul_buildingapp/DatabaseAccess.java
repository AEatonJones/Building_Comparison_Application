package com.example.ul_buildingapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c = null;

    private DatabaseAccess(Context context) {
        this.openHelper= new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if(instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.db = openHelper.getWritableDatabase();
    }

    public void close() {
        if(db!=null) {
            this.db.close();
        }
    }

    public String getSubjectInfo(String code, String subject) {
        StringBuffer buffer = new StringBuffer();
        c = db.rawQuery("SELECT "+subject+" FROM Buildings WHERE CODE = '"+code+"'", new String[]{});

        while(c.moveToNext()) {
            String result = c.getString(0);
            buffer.append(result);
        }
        return buffer.toString();
    }


}
