package ba.unsa.etf.rma.selmirhasanovic.rma2015_16926;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Selmir Hasanovic on 08-April-16.
 */
public class FragmentLista extends Fragment  implements MojResultReceiver.Receiver  {
    public ArrayList<Muzicar> muzicari = new ArrayList<Muzicar>();
    ArrayList<Pretraga> pretrage = new ArrayList<Pretraga>();
    OnItemClick oic;
    InternetKonekcija receiver = new InternetKonekcija();
    IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
    private ListView lv;
    View rview;
    Button dgm;
    int[] images;
    Boolean izaberiPrikaz;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().registerReceiver(receiver, filter);
        rview = inflater.inflate(R.layout.fragment_lista, container, false);
        izaberiPrikaz = false;
        final EditText tekstPretrage = (EditText)rview.findViewById(R.id.tekstZaPretragu);
        if (getArguments().containsKey("Alista")) {
            muzicari = getArguments().getParcelableArrayList("Alista");
            lv = (ListView) rview.findViewById(R.id.listViewMuzicari);
            String zanr;
            if(muzicari.size() < 5) {
                ArrayList<Muzicar> pomocniMuzicari;
                BazaPodataka baza = new BazaPodataka(getActivity(), BazaPodataka.DATABASE_NAME, null, 1);
                pomocniMuzicari = baza.dajMuzicare();
                int a;
                if (pomocniMuzicari.size() < 5) a = pomocniMuzicari.size();
                else a = 5;
                for (int i = 0; i < a; i++) {
                    boolean trebaD = true;
                    for (int j = 0; j < muzicari.size(); j++) {
                        if (pomocniMuzicari.get(i).getIme().equalsIgnoreCase(muzicari.get(j).getIme())) {
                            trebaD = false;
                        }
                    }
                    if (trebaD) {
                        muzicari.add(pomocniMuzicari.get(i));
                    }
                }
            }
            Pocetni.muzicari = muzicari;
            images = new int[muzicari.size()];
            for (int i = 0; i < muzicari.size(); i++) {
                zanr = muzicari.get(i).getGenre();
                if (zanr == "jazz") images[i] = R.drawable.jazz;
                else if (zanr == "pop") images[i] = R.drawable.pop;
                else if (zanr == "blues") images[i] = R.drawable.blues;
                else if (zanr == "metal") images[i] = R.drawable.metal;
                else if (zanr == "rok") images[i] = R.drawable.rok;
                else if (zanr == "music") images[i] = R.drawable.slika1;
                else images[i] = R.drawable.slika1;
            }
            MuzicarAdapter adapter = new MuzicarAdapter(getActivity(), images);
            lv.setAdapter(adapter);
            try {
                oic = (OnItemClick)getActivity();
            }
            catch (ClassCastException e) {
                throw new ClassCastException(getActivity().toString() +
                        "Greska");
            }
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(izaberiPrikaz){
                        Intent intent = new Intent(Intent.ACTION_SYNC, null, getActivity(), MyIntentService.class);
                        MojResultReceiver mReceiver = new MojResultReceiver(new Handler());
                        mReceiver.setReceiver(FragmentLista.this);
                        intent.putExtra("upit", tekstPretrage.getText().toString());
                        intent.putExtra("receiver", mReceiver);
                        getActivity().startService(intent);
                        MuzicarAdapter aa = new MuzicarAdapter(getActivity(), images);
                        lv.setAdapter(aa);
                        izaberiPrikaz = false;
                    }
                    else {
                        BazaPodataka baza1 = new BazaPodataka(getActivity(), BazaPodataka.DATABASE_NAME, null, 1);
                        baza1.dodajMuzicara(muzicari.get(position));
                        oic.onItemClicked(position);
                    }
                }
            });
            EditText editText = (EditText) rview.findViewById(R.id.tekstZaPretragu);
            Intent intent = getActivity().getIntent();
            String editTextString = intent.getStringExtra(Intent.EXTRA_TEXT);
            if (editTextString != null) editText.setText(editTextString);
            getActivity().registerReceiver(receiver, filter);
            Button pretraga = (Button) rview.findViewById(R.id.button);
            pretraga.setText(R.string.buttonPretraga);
        }

        images = new int[muzicari.size()];
        for (int i = 0; i < muzicari.size(); i++) {
            String zanr = muzicari.get(i).getGenre();
            if (zanr == "jazz") images[i] = R.drawable.jazz;
            else if (zanr == "pop") images[i] = R.drawable.pop;
            else if (zanr == "blues") images[i] = R.drawable.blues;
            else if (zanr == "metal") images[i] = R.drawable.metal;
            else if (zanr == "rok") images[i] = R.drawable.rok;
            else if (zanr == "music") images[i] = R.drawable.slika1;
            else images[i] = R.drawable.slika1;
        }

        Button dugme2 = (Button)rview.findViewById(R.id.buttonHistorija);
        dugme2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                izaberiPrikaz = true;
                AdapterPretraga a = new AdapterPretraga(getActivity(), R.layout.element_historija, pretrage);
                lv.setAdapter(a);
            }
        });

        ((Button)rview.findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                izaberiPrikaz = false;
                boolean pretragaMoguca = false;
                ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    pretragaMoguca = true;
                }
                else
                    Toast.makeText(getContext(), "Nije moguće izvršiti pretragu", Toast.LENGTH_SHORT).show();
                pretrage.add(new Pretraga(tekstPretrage.getText().toString(), new Date(), pretragaMoguca));
                Intent intent = new Intent(Intent.ACTION_SYNC, null, getActivity(), MyIntentService.class);
                MojResultReceiver mReceiver = new MojResultReceiver(new Handler());
                mReceiver.setReceiver(FragmentLista.this);
                intent.putExtra("upit", tekstPretrage.getText().toString());
                intent.putExtra("receiver", mReceiver);
                getActivity().startService(intent);
                MuzicarAdapter aa = new MuzicarAdapter(getActivity(), images);
                lv.setAdapter(aa);
            }
        });
        return rview;
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        ArrayList<Muzicar> musicians = resultData.getParcelableArrayList("result");
        muzicari.clear();

            switch (resultCode) {
                case MyIntentService.STATUS_RUNNING:
                    break;
                case MyIntentService.STATUS_FINISHED:
                    if(musicians != null) {
                        for (Muzicar m : musicians) {
                            muzicari.add(m);
                            ((MuzicarAdapter) lv.getAdapter()).notifyDataSetChanged();
                        }
                        images = new int[muzicari.size()];
                        for (int i = 0; i < muzicari.size(); i++) {
                            String zanr = muzicari.get(i).getGenre();
                            if (zanr == "jazz") images[i] = R.drawable.jazz;
                            else if (zanr == "pop") images[i] = R.drawable.pop;
                            else if (zanr == "blues") images[i] = R.drawable.blues;
                            else if (zanr == "metal") images[i] = R.drawable.metal;
                            else if (zanr == "rok") images[i] = R.drawable.rok;
                            else if (zanr == "music") images[i] = R.drawable.slika1;
                            else images[i] = R.drawable.slika1;
                        }
                    }
                    Pocetni.muzicari = muzicari;
                    break;
                case MyIntentService.STATUS_ERROR:
                    String error = resultData.getString(Intent.EXTRA_TEXT);
                    Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                    break;
        }
    }
    public interface OnItemClick {
        public void onItemClicked(int pos);
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            getActivity().unregisterReceiver(receiver);
        }
        catch (IllegalArgumentException e) {
            getActivity().registerReceiver(receiver, filter);
        }
    }


    public void onDone(ArrayList<Muzicar> _musicians) {
        muzicari.clear();
        for(Muzicar m : _musicians)
            muzicari.add(m);
        ((MuzicarAdapter)lv.getAdapter()).notifyDataSetChanged();
    }
    public class AdapterPretraga extends ArrayAdapter<Pretraga> {
        int r1;
        public AdapterPretraga(Context context, int _r1, List<Pretraga> items) {
            super(context, _r1, items);
            r1 = _r1;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getActivity().getLayoutInflater().inflate(R.layout.element_historija, parent, false);
            }
            Pretraga pretraga = pretrage.get(position);
            TextView tv1 = (TextView)itemView.findViewById(R.id.tekstPretrageTextView);
            TextView tv2 = (TextView)itemView.findViewById(R.id.vrijemePretrageTV);
            TextView tv3 = (TextView)itemView.findViewById(R.id.tipPretrageTV);
            tv1.setText("Tekst pretrage: " + pretraga.dajPretrazivanje());
            tv2.setText("Vrijeme pretrage: " + pretraga.dajVrijeme().getHours() + ":" + pretraga.dajVrijeme().getMinutes());
            if(pretraga.dajUspjesnost())
                tv3.setText("Tip pretrage: uspješna");
            else
                tv3.setText("Tip pretrage: neuspješna");
            if(pretraga.dajUspjesnost())
            {
                tv1.setTextColor(Color.GREEN);
                tv2.setTextColor(Color.GREEN);
                tv3.setTextColor(Color.GREEN);
            }
            else
            {
                tv1.setTextColor(Color.RED);
                tv2.setTextColor(Color.RED);
                tv3.setTextColor(Color.RED);
            }
            return itemView;
        }
    }
}
