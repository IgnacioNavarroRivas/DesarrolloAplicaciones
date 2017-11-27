package app.pablonavarro.com.prototypeappproyect;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gonzaloperez on 04/21/2016.
 * Edited by pablo on 25/11/2017
 */

public class BaseHelper extends SQLiteOpenHelper {

    String tabla = "CREATE TABLE demand(demand_id INTEGER PRIMARY KEY, demand_name TEXT," +
            "demand_phone TEXT, demand_type TEXT, demand_description TEXT)";

    public BaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + tabla);
        db.execSQL(tabla);
    }
}
