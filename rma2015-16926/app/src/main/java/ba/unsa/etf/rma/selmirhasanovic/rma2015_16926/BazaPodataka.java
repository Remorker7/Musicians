package ba.unsa.etf.rma.selmirhasanovic.rma2015_16926;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Selmir Hasanovic on 12-Jun-16.
 */
public class BazaPodataka extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "baza7.db";
    public static final String TABELA1 = "Muzicari";
    public static final String TABELA2 = "Pjesme";
    public static final String TABELA3 = "Albumi";
    public static final int DATABASE_VERSION = 1;
    public static final String MUZICAR_ID = "muzicar_id";
    public static final String MUZICAR_ID1 = "muzicar_id1";
    public static final String MUZICAR_IME = "ime";
    public static final String MUZICAR_ZANR = "zanr";
    public static final String PJESME_ID = "pjesme_id";
    public static final String PJESMA_NAZIV = "naziv_pjesme";
    public static final String ALBUMI_ID = "albumi_id";
    public static final String ALBUM_NAZIV = "naziv_albuma";
    public static final String ALBUM_NAZIV1 = "naziv_albuma1";
    private static final String DATABASE_CREATE1 = "create table " +
            TABELA1 + " ("
            + MUZICAR_ID1 + " integer primary key autoincrement, "
            + MUZICAR_ID +
            " text not null, " +
            MUZICAR_IME + " text not null, " +
            MUZICAR_ZANR + " text );";
    private static final String DATABASE_CREATE2 = "create table " +
            TABELA2 + " (" + PJESME_ID +
            " integer primary key autoincrement, " +
            PJESMA_NAZIV + " text not null, "+
            MUZICAR_ID + " integer not null, "+
            "FOREIGN KEY("+MUZICAR_ID+") REFERENCES " + TABELA1 + "(" + MUZICAR_ID1 + "));";
    private static final String DATABASE_CREATE3 = "create table " +
            TABELA3 + " (" + ALBUMI_ID +
            " integer primary key autoincrement, " +
            ALBUM_NAZIV + " text not null, "+
            ALBUM_NAZIV1 + " text not null, "+
            MUZICAR_ID + " integer not null, "+
            "FOREIGN KEY ("+ MUZICAR_ID + ") REFERENCES " + TABELA1 + "(" + MUZICAR_ID1 + "));";
    public BazaPodataka(Context context, String name,
                                          SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE1);
        db.execSQL(DATABASE_CREATE2);
        db.execSQL(DATABASE_CREATE3);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF IT EXISTS" + TABELA1);
        db.execSQL("DROP TABLE IF IT EXISTS" + TABELA2);
        db.execSQL("DROP TABLE IF IT EXISTS" + TABELA3);
        onCreate(db);
    }

    public void dodajPjesmu(String id,String pjesma)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MUZICAR_ID, id);
        values.put(PJESMA_NAZIV, pjesma);
        db.insert(TABELA2, null, values);
    }
    public void dodajAlbum(String id, Pair<String, String> album)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ALBUM_NAZIV, album.first);
        values.put(ALBUM_NAZIV1, album.second);
        values.put(MUZICAR_ID, id);
        db.insert(TABELA3, null, values);
    }
    public ArrayList<String> dajPjesme(String id)
    {
        ArrayList<String> pjesme = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + PJESMA_NAZIV + " FROM " + TABELA2 + " WHERE "
                + MUZICAR_ID + " = " + "'" + id + "';";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                pjesme.add(cursor.getString(cursor.getColumnIndex(PJESMA_NAZIV)));
            } while (cursor.moveToNext());
        }
        return  pjesme;
    }
    public ArrayList<Pair<String, String>> dajAlbume(String id)
    {
        ArrayList<Pair<String, String>> albumi = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String s = "SELECT  * FROM " + TABELA3;
        Cursor cursor = db.rawQuery(s, null);
        if (cursor.moveToFirst()) {
            do {
                albumi.add(Pair.create(cursor.getString(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        return albumi;
    }
    public void dodajMuzicara(Muzicar m)
    {
        boolean trebaDodati = true;
        ArrayList<Muzicar> muz = dajMuzicare();
        for(int i = 0; i < muz.size(); i++){
            if(muz.get(i).getIme().equalsIgnoreCase(m.getIme())) {
                trebaDodati = false;
            }
        }
        if(trebaDodati) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            String imePrezime = m.getIme() + m.getPrezime();
            String zanr = m.getGenre();
            if (zanr == null) zanr = "music";
            values.put(MUZICAR_ID, m.getIdMuzicara());
            values.put(MUZICAR_IME, imePrezime);
            values.put(MUZICAR_ZANR, zanr);
            db.insert(TABELA1, null, values);
            ArrayList<String> pjesme;
            pjesme = m.getTopFive();
            for (int i = 0; i < pjesme.size(); i++) {
                dodajPjesmu(m.getIdMuzicara(), pjesme.get(i));
            }
            ArrayList<Pair<String, String>> albumi;
            albumi = m.getAlbums();
            for (int i = 0; i < albumi.size(); i++) {
                dodajAlbum(m.getIdMuzicara(), albumi.get(i));
            }
        }
    }
    public ArrayList<Muzicar> dajMuzicare() {
        ArrayList<Muzicar> muzicari = new ArrayList<>();
        String s = "SELECT  * FROM " + TABELA1;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(s, null);
        if (cursor.moveToFirst()) {
            do {
                String ID = cursor.getString(1);
                String webStr = "https://open.spotify.com/artist/" + ID;
                Muzicar m = new Muzicar(cursor.getString(2), "", webStr, "", cursor.getString(3));
                m.setIdMuzicara(ID);
                m.setSpotifyFollowers(0);
                m.setSpotifyPopularity(0);
                m.setTopFive(dajPjesme(ID));
                m.setAlbums(dajAlbume(ID));
                boolean trebaD = true;
                for(int i = 0; i < muzicari.size(); i++){
                    if(muzicari.get(i).getIme().equalsIgnoreCase(m.getIme())) {
                        trebaD = false;
                    }
                }
                if(trebaD) {
                    muzicari.add(m);
                }
            } while (cursor.moveToNext());
        }
        return muzicari;
    }
}
