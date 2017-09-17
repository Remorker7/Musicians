package ba.unsa.etf.rma.selmirhasanovic.rma2015_16926;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Selmir Hasanovic on 08-May-16.
 */
public class FragmentDetalji extends Fragment {
    Muzicar muzicar;
    TextView name;
    TextView genre;
    TextView webPage;
    TextView bio;
    TextView follow;
    TextView popular;
    FrameLayout frameLayout;
    boolean zamijeni;
    boolean zamijeniAlbumi;
    FragmentManager fm;
    Top5Fragment fragmentTopFive;
    FragmentSlicni fragmentSimilar;
    FragmentAlbum fragmentAlbumi;
    View iv;
    Button sendInfo;
    Button buttonPromjena;
    Button promijeniNaAlbume;

    boolean alb;

    private ArrayList<String> pronadjiSlicne() {
        ArrayList<String> temp = new ArrayList<String>();
        for (Muzicar muzicar : Pocetni.muzicari) {
            if (muzicar.getGenre().equals(this.muzicar.getGenre()) && !muzicar.toString().equals(this.muzicar.toString())) temp.add(muzicar.toString());
        }
        return temp;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View av = inflater.inflate(R.layout.fragment_detalji, container, false);
        iv = av;
        alb = false;
        if (getArguments() != null && getArguments().containsKey("muzicar")) {
            muzicar = getArguments().getParcelable("muzicar");
            name = (TextView) iv.findViewById(R.id.imeMuzicara);
            name.setText(R.string.labelName);
            genre = (TextView) iv.findViewById(R.id.zanrMuzicara);
            genre.setText(R.string.labelGenre);
            webPage = (TextView) iv.findViewById(R.id.webStranica);
            webPage.setText(R.string.labelWebPage);
            bio = (TextView) iv.findViewById(R.id.biografijaMuzicara);
            bio.setText(R.string.labelBiography);
            follow = (TextView) iv.findViewById(R.id.followers);
            follow.setText("Spotify followers: " + muzicar.getSpotifiyFollowers());
            popular = (TextView) iv.findViewById(R.id.popularity);
            popular.setText("Spotify popularity: " + muzicar.getSpotifiyPopularity());

            name.setText(name.getText() + ": " + muzicar.toString());
            genre.setText(genre.getText() + ": " + muzicar.getGenre() + "\n");
            webPage.setText(webPage.getText() + ":\n" + muzicar.getWebStranica() + "\n");
            bio.setText(bio.getText() + ":\n" + muzicar.getBiografija());

            frameLayout = (FrameLayout) iv.findViewById(R.id.FrameSimilarTop);
            fm = getChildFragmentManager();
            fragmentTopFive = new Top5Fragment();
            fragmentAlbumi = new FragmentAlbum();

            Bundle arguments = new Bundle();
            arguments.putParcelable("muzicar", muzicar);
            fragmentTopFive.setArguments(arguments);
            fragmentSimilar = new FragmentSlicni();
            Bundle argumentSimilar = new Bundle();
            argumentSimilar.putStringArrayList("slicniMuzicari", this.pronadjiSlicne());
            fragmentSimilar.setArguments(argumentSimilar);
            fragmentAlbumi.setArguments(arguments);
            buttonPromjena = (Button) iv.findViewById(R.id.slicniMuzicari);
            buttonPromjena.setText(R.string.buttonChangeToSimilar);
            promijeniNaAlbume = (Button) iv.findViewById(R.id.buttonAlbumi);
            promijeniNaAlbume.setText("Prikazi albume: ");
            zamijeni = true;
            zamijeniAlbumi = true;

            BazaPodataka baza = new BazaPodataka(getActivity(), BazaPodataka.DATABASE_NAME, null, 1);
            baza.dodajMuzicara(muzicar);

            fm.beginTransaction().replace(R.id.FrameSimilarTop, fragmentTopFive).commit();

            webPage.setOnClickListener(new TextView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = muzicar.getWebStranica();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
            });
            buttonPromjena.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (zamijeni) {
                        zamijeni = false;
                        buttonPromjena.setText(R.string.buttonChangeToTopFive);
                        fm.beginTransaction().replace(R.id.FrameSimilarTop, fragmentSimilar).commit();
                    } else {
                        zamijeni = true;
                        buttonPromjena.setText(R.string.buttonChangeToSimilar);
                        fm.beginTransaction().replace(R.id.FrameSimilarTop, fragmentTopFive).commit();
                    }
                }
            });

            promijeniNaAlbume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (zamijeniAlbumi) {
                        zamijeniAlbumi = false;
                        promijeniNaAlbume.setText("ALBUM");
                        fm.beginTransaction().replace(R.id.FrameSimilarTop, fragmentSimilar).commit();
                    } else {
                        zamijeniAlbumi = true;
                        promijeniNaAlbume.setText(R.string.buttonChangeToSimilar);
                        fm.beginTransaction().replace(R.id.FrameSimilarTop, fragmentAlbumi).commit();
                    }
                }
            });

        }
        return iv;
    }
}