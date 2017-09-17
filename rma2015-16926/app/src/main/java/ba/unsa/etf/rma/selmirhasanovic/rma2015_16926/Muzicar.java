package ba.unsa.etf.rma.selmirhasanovic.rma2015_16926;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Selmir Hasanovic on 27-Mar-16.
 */
public class Muzicar implements Parcelable {
    private String ime, prezime, webStranica, biografija, idMuzicara;
    private String genre;
    private Integer spotifyPopularity;
    private Integer spotifyFollowers;
    private ArrayList<String> pjesme;
    private ArrayList<Pair<String, String>> albums;
    private ArrayList<Muzicar> _muzicari;

    public Muzicar(String ime, String prezime, String webStranica, String biografija, String zanr) {
        this.ime = ime;
        this.prezime = prezime;
        this.webStranica = webStranica;
        this.biografija = biografija;
        this.genre = zanr;
        spotifyFollowers = 0;
        spotifyPopularity = 0;
    }
    protected Muzicar(Parcel in) {
        String[] temp1 = in.readString().split(" ");
        ime = temp1[0];
        prezime = temp1[1];

        String temp = in.readString();
        webStranica = in.readString();
        biografija = in.readString();
        genre = " ";
        switch (temp) {
            case "Pop":
                genre = "pop";
                break;
            case "Rock":
                genre = "rock";
                break;
            case "Blues":
                genre = "blues";
                break;
            case "Jazz":
                genre = "jazz";
                break;
            case "Metal":
                genre = "metal";
                break;
        }
        if(genre == " ") {genre = "music";}
    }
    public static final Creator<Muzicar> CREATOR = new Creator<Muzicar>() {
        @Override
        public Muzicar createFromParcel(Parcel in) {
            return new Muzicar(in);
        }
        @Override
        public Muzicar[] newArray(int size) {
            return new Muzicar[size];
        }
    };

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getIdMuzicara() {
        return idMuzicara;
    }

    public void setIdMuzicara(String idd) {
        this.idMuzicara = idd;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getWebStranica() {
        return webStranica;
    }

    public void setWebStranica(String webStranica) {
        this.webStranica = webStranica;
    }

    public String getBiografija() {
        return biografija;
    }

    public void setBiografija(String biografija) {
        this.biografija = biografija;
    }

    public ArrayList<String> getTopFive() {
        return pjesme;
    }
    public void setGenre(String _genre) {
        genre = _genre;
    }
    public String getGenre() {
        return genre;
    }

    public void setTopFive(ArrayList<String> _pjesme) {
        this.pjesme = _pjesme;
    }
    public void setSpotifyPopularity(int pop) {
        spotifyPopularity = pop;
    }
    public Integer getSpotifiyPopularity(){ return spotifyPopularity;}

    public Integer getSpotifiyFollowers(){ return spotifyFollowers; }
    public void setSpotifyFollowers(int fol) {
        spotifyFollowers = fol;
    }
    public void setMusicians(List<Muzicar> _musicians) {
        _muzicari = new ArrayList<>(_musicians);
    }
    public void setSongs(ArrayList<String> _songs) {
        pjesme = _songs;
    }
    public void setAlbums(ArrayList<Pair<String, String>> _albums) {
        albums = _albums;
    }

    public ArrayList<String> getSongs() {return pjesme; }
    public ArrayList<Pair<String, String>> getAlbums() {return albums;}

    public void addSong(String _song) {
        pjesme.add(_song);
    }
    public List<String> getTop5Songs() {
        return pjesme.subList(0, 5);
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ime + " " + prezime);
        dest.writeString(genre);
        dest.writeString(webStranica);
        dest.writeString(biografija);
    }
}
