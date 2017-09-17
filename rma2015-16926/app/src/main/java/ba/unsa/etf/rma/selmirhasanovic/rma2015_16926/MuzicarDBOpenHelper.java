package ba.unsa.etf.rma.selmirhasanovic.rma2015_16926;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Selmir Hasanovic on 30-May-16.
 */
public class MuzicarDBOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mojaBaza.db";
    public static final String DATABASE_TABLE = "Muzicari";
    public static final int DATABASE_VERSION = 1;
    public static final String MUZICAR_ID = "_id";
    public static final String MUZICAR_IME = "ime";
    public static final String MUZICAR_ZANR = "zanr";
    private static final String DATABASE_CREATE = "create table " +
            DATABASE_TABLE + " (" + MUZICAR_ID +
            " integer primary key autoincrement, " +
            MUZICAR_IME + " text not null, " +
            MUZICAR_ZANR + " text not null);";
    public MuzicarDBOpenHelper(Context context, String name,
                               SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF IT EXISTS" + DATABASE_TABLE);
        onCreate(db);
    }
}
