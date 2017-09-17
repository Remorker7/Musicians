package ba.unsa.etf.rma.selmirhasanovic.rma2015_16926;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Selmir Hasanovic on 13-May-16.
 */
public class FragmentSlicni extends Fragment {
    TextView labelSimilar;
    ListView lv;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_slicni_muzicari, container, false);

        if (getArguments() != null && getArguments().containsKey("slicniMuzicari")) {
            labelSimilar = (TextView) view.findViewById(R.id.slicniMuzicariTekst);
            labelSimilar.setText(R.string.slicniMuzicariTekst);
            labelSimilar.setText(labelSimilar.getText() + ":");

            lv = (ListView) view.findViewById(R.id.listViewSimilarArtists);
            SlicniMuzicari adapter = new SlicniMuzicari(getActivity().getApplicationContext(), getArguments().getStringArrayList("slicniMuzicari"));
            lv.setAdapter(adapter);
        }

        return view;
    }
}
