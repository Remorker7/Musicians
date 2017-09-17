package ba.unsa.etf.rma.selmirhasanovic.rma2015_16926;

import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Selmir Hasanovic on 07-Mar-16.
 */
public class Pocetni extends AppCompatActivity implements FragmentLista.OnItemClick {
    static ArrayList<Muzicar> muzicari = new ArrayList<Muzicar>();/* {{
        add(new Muzicar("Đorđe", "Balašević", "http://www.balasevic.info", "Srbijanski i jugoslavenski pjevač i kantautor.", "blues"));
        add(new Muzicar("Darko", "Rundek", "https://www.darko-rundek.com", "Pjevač i gitarist, autor i skladatelj u zagrebačkom bendu Haustor.", "rock"));
        add(new Muzicar("Željko", "Joksimović", "http://zeljkojoksimovic.rs", "Željko Joksimović je jedan od najcenjenijih i najpopularnijih pevača Srbije.", "blues"));
        add(new Muzicar("Goran", "Šepa", "http://nevena-svatara.blogspot.ba/2008/04/goran-epa-gale-kerber.html", "Goran (Gale) Šepa je srpski rok pjevač.", "rock"));
        add(new Muzicar("Dino", "Merlin", "http://www.dinomerlin.com", "Edin Dervišhalidović je bosanskohercegovački kantautor, pjevač i kompozitor.", "pop"));
        add(new Muzicar("Jura", "Stublić", "http://www.akordi-za-gitaru.com/j/jura-stublic.html", "Jurislav Stublić (Sarajevo, 1954.), hrvatski je glazbenik i skladatelj", "rock"));
        add(new Muzicar("Bryan", "Adams", "https://www.bryanadams.com", "Bryan Adams (5. studenog 1959.) kanadski je kantautor, gitarist i fotograf.", "pop"));
        add(new Muzicar("Bob", "Dylan", "http://bobdylan.com/", "Bob Dylan (pravim imenom Robert Allen Zimmerman) američki je pjevač, skladatelj i pjesnik.", "blues"));
        add(new Muzicar("Johnny", "Štulić", "http://www.vecernji.hr/biografije/branimir-johnny-stulic-536", "Branimir Johnny Štulić, jugoslavenski rock glazbenik, poznat kao frontman grupe Azra.", "rock"));
        add(new Muzicar("Bora", "Đorđević", "http://www.riblja-corba.com/", "Borisav (Bora) Đorđević (rođen 1. novembra 1952. u Čačku), poznat i kao Bora Čorba.", "rock"));
    }};*/

    boolean siriL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocetni);

        try{
            ContentValues novi = new ContentValues();
            novi.put(MuzicarDBOpenHelper.MUZICAR_IME,"Miladin Šobić");
            novi.put(MuzicarDBOpenHelper.MUZICAR_ZANR, "Pop");
        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(), e.toString() , Toast.LENGTH_LONG).show();
        }
        siriL = false;
        FragmentManager fm = getFragmentManager();
        FrameLayout ldetalji = (FrameLayout)findViewById(R.id.detalji_muzicar);
        if (ldetalji != null) {
            siriL = true;
            FragmentDetalji fdetalji;
            fdetalji = (FragmentDetalji)fm.findFragmentById(R.id.detalji_muzicar);
            if (fdetalji == null) {
                fdetalji = new FragmentDetalji();
                Bundle bun = new Bundle();
                bun.putParcelable("muzicar", muzicari.get(0));
                fdetalji.setArguments(bun);
                fm.beginTransaction().replace(R.id.detalji_muzicar, fdetalji).commit();
            }
        }
        FragmentLista flista = (FragmentLista)fm.findFragmentByTag("Lista");
        if (flista == null) {
            flista = new FragmentLista();
            Bundle argumenti = new Bundle();
            argumenti.putParcelableArrayList("Alista", muzicari);
            flista.setArguments(argumenti);
            fm.beginTransaction().replace(R.id.muzicari_lista, flista, "Lista").commit();
        }
        else {
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

    }

    @Override
    public void onItemClicked(int pos) {
        Bundle argumenti = new Bundle();
        argumenti.putParcelable("muzicar", muzicari.get(pos));
        FragmentDetalji fdetalji = new FragmentDetalji();
        fdetalji.setArguments(argumenti);

        if (siriL) {
            getFragmentManager().beginTransaction().replace(R.id.detalji_muzicar, fdetalji).addToBackStack(null).commit();
        }
        else {
            getFragmentManager().beginTransaction().replace(R.id.muzicari_lista, fdetalji).addToBackStack(null).commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }


}
