package ba.unsa.etf.rma.selmirhasanovic.rma2015_16926;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Selmir Hasanovic on 16-May-16.
 */
public class MyIntentService extends IntentService {
    ArrayList<Muzicar> muzicari = new ArrayList<Muzicar>();
    public MyIntentService() {
        super(null);
    }
    public MyIntentService(String name) {
        super(name);
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }
    Muzicar rez;
    public static  final int STATUS_RUNNING = 0;
    public static  final int STATUS_FINISHED = 1;
    public static  final int STATUS_ERROR = 2;

    @Override
    protected void onHandleIntent(Intent intent) {
        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        Bundle bundle = new Bundle();
        String upit = intent.getStringExtra("upit");
        String query = null;
        try {
            query = URLEncoder.encode(upit, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url1 = "https://api.spotify.com/v1/search?q=" + query + "&type=artist&limit=5";
        try {
            URL url = new URL(url1);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(connection.getInputStream());
            String rezultat = convertStreamToString(in);
            JSONObject jo = new JSONObject(rezultat);
            JSONObject artists = jo.getJSONObject("artists");
            JSONArray items = artists.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject artist = items.getJSONObject(i);
                String name = artist.getString("name");
                String webStranica = artist.getJSONObject("external_urls").getString("spotify");
                int popularity = artist.getInt("popularity");
                int followers = artist.getJSONObject("followers").getInt("total");
                String genre = " ";
                JSONArray genres = artist.getJSONArray("genres");
                for(int j = 0; j < genres.length(); j++){
                    if(genres.getString(j) == "blues" || genres.getString(j) == "jazz" || genres.getString(j) == "metal" ||
                            genres.getString(j) == "pop" || genres.getString(j) == "rock")
                        genre = genres.getString(j);
                }
                rez = new Muzicar(name, "", webStranica, "", genre);
                rez.setSpotifyPopularity(popularity);
                rez.setSpotifyFollowers(followers);
                rez.setMusicians(muzicari);
                String id = artist.getString(("id"));
                rez.setIdMuzicara(id);
                rez.setAlbums(pronadjiAlbume(id));
                rez.setTopFive(pronadjiPjesme(id));

                muzicari.add(rez);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Pocetni.muzicari = muzicari;
        bundle.putParcelableArrayList("result", muzicari);
        receiver.send(STATUS_FINISHED, bundle);
    }
    public ArrayList<Pair<String, String>> pronadjiAlbume(String id) {
        try {
            URL u = new URL("https://api.spotify.com/v1/artists/" + id + "/albums?album_type=album&market=DE&limit=10");
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            int c = connection.getResponseCode();
            InputStream stream = new BufferedInputStream(connection.getInputStream());
            String result = convertStreamToString(stream);
            JSONArray albumi = new JSONObject(result).getJSONArray("items");
            ArrayList<Pair<String, String>> a = new ArrayList<Pair<String, String>>();
            for (int i = 0; i < albumi.length(); i++) {
                JSONObject track = albumi.getJSONObject(i);
                a.add(new Pair<String, String>(track.getString("name"), track.getJSONObject("external_urls").getString("spotify")));
            }
            return a;
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<String> pronadjiPjesme(String id) {
        try {
            URL u = new URL("https://api.spotify.com/v1/artists/" + id +"/top-tracks?country=us");
            HttpURLConnection connection = (HttpURLConnection)u.openConnection();
            int c = connection.getResponseCode();
            InputStream stream = new BufferedInputStream( connection.getInputStream());
            String result = convertStreamToString(stream);
            JSONArray pjesme = new JSONObject(result).getJSONArray("tracks");
            ArrayList<String> lista = new ArrayList<>();
            for(int i = 0; i < pjesme.length(); i++){
                JSONObject track = pjesme.getJSONObject(i); lista.add(track.getString("name"));
            }
            return lista;
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
        return sb.toString();
    }
}
