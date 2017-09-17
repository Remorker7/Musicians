package ba.unsa.etf.rma.selmirhasanovic.rma2015_16926;

import android.os.Parcel;
import android.os.Parcelable;
import java.sql.Time;
import java.util.Date;

/**
 * Created by Selmir Hasanovic on 12-Jun-16.
 */
public class Pretraga implements Parcelable {
    public String pretrazivanje;
    public Date time;
    public Boolean Uspjesnost = false;
    public String dajPretrazivanje(){ return pretrazivanje; }
    public void postaviPretrazivanje(String p1){ pretrazivanje = p1; }
    public Date dajVrijeme(){ return time; }
    public void postaviVrijeme(Time time1){ time = time1; }
    public Boolean dajUspjesnost(){ return Uspjesnost; }
    public void vratiUspjesnost(Boolean uspj){ Uspjesnost = uspj; }
    public Pretraga(String p1, Date vrijeme1, Boolean uspjesno1) {
        pretrazivanje = p1;
        time = vrijeme1;
        Uspjesnost = uspjesno1;
    }

    public Pretraga() {}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.pretrazivanje);
        dest.writeLong(this.time != null ? this.time.getTime() : -1);
        dest.writeValue(this.Uspjesnost);
    }

    protected Pretraga(Parcel in) {
        this.pretrazivanje = in.readString();
        long tmpVrijeme = in.readLong();
        this.time = tmpVrijeme == -1 ? null : new Date(tmpVrijeme);
        this.Uspjesnost = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<Pretraga> CREATOR = new Parcelable.Creator<Pretraga>() {
        @Override
        public Pretraga createFromParcel(Parcel source) {
            return new Pretraga(source);
        }
        @Override
        public Pretraga[] newArray(int size) {
            return new Pretraga[size];
        }
    };
}