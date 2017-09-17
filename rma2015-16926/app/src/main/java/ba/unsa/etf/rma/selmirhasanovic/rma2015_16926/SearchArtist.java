package ba.unsa.etf.rma.selmirhasanovic.rma2015_16926;

import android.os.AsyncTask;
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
import java.util.List;

/**
 * Created by Selmir Hasanovic on 16-May-16.
 */
public class SearchArtist extends AsyncTask<String, Integer, Void> {
    OnSearchMusicianDone pozivatelj;
    ArrayList<Muzicar> muzicari = new ArrayList<>();
    Muzicar rez;
    public SearchArtist(OnSearchMusicianDone p) {
        pozivatelj = p;
    }
    @Override
    protected Void doInBackground(String... params) {
        String query = null;
        try {
            query = URLEncoder.encode(params[0], "utf-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url1 = "https://api.spotify.com/v1/search?q=" + query + "&type=artist&limit=5";
        try {
            URL url = new URL(url1);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            InputStream in = new BufferedInputStream( connection.getInputStream());
            String rezultat = convertStreamToString(in);
            JSONArray items = new JSONObject(rezultat).getJSONObject("artists").getJSONArray("items");
            for(int i = 0; i < items.length(); i++){
                JSONObject artist = items.getJSONObject(i);
                String name = artist.getString("name");
                String artist_ID = artist.getString("id");
                String webStranica = artist.getJSONObject("external_urls").getString("spotify");
                int popularity = artist.getInt("popularity");
                int followers = artist.getJSONObject("followers").getInt("total");
                String genre = "";
                JSONArray genres = artist.getJSONArray("genres");
                if(genres.length() != 0) {
                    genre = genres.getString(0);
                }
                rez = new Muzicar(name, "", genre, webStranica, "");
                rez.setSpotifyPopularity(popularity);
                rez.setSpotifyFollowers(followers);
                rez.setMusicians(muzicari);
                rez.setAlbums(getAlbums(artist.getString("id")));

                muzicari.add(rez);

            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e ) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<String> izdvojiPjesme(String id) {
        try {
            URL url = new URL("https://api.spotify.com/v1/artists/" + id + "/top-tracks?country=us");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            int c = connection.getResponseCode();
            InputStream is = new BufferedInputStream( connection.getInputStream());
            String rezultat = convertStreamToString(is);
            JSONArray pjesme = new JSONObject(rezultat).getJSONArray("tracks");
            ArrayList<String> listaPjesama = new ArrayList<>();
            for(int i = 0; i < pjesme.length(); i++){
                JSONObject track = pjesme.getJSONObject(i);
                listaPjesama.add(track.getString("name"));
            }
            return listaPjesama;
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
    public ArrayList<Pair<String, String>> getAlbums(String id) {
        try {
            URL url = new URL("https://api.spotify.com/v1/artists/" + id +"/albums?album_type=album&market=DE&limit=10");

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            int c = connection.getResponseCode();
            InputStream in = new BufferedInputStream( connection.getInputStream());

            String rezultat = convertStreamToString(in);

            JSONArray albumsArr = new JSONObject(rezultat).getJSONArray("items");

            ArrayList<Pair<String, String>> albums = new ArrayList<Pair<String, String>>();

            for(int i = 0; i < albumsArr.length(); i++){
                JSONObject track = albumsArr.getJSONObject(i);
                albums.add(new Pair<String, String>(track.getString("name"),
                        track.getJSONObject("external_urls")
                                .getString("spotify")));
            }

            return albums;

        }
        catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        catch (JSONException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public interface OnSearchMusicianDone {
        public void onDone(ArrayList<Muzicar> rez);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        pozivatelj.onDone(muzicari);
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
