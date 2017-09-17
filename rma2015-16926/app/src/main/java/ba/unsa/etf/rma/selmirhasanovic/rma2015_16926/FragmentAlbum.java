package ba.unsa.etf.rma.selmirhasanovic.rma2015_16926;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Selmir Hasanovic on 30-May-16.
 */
public class FragmentAlbum extends Fragment {
    TextView labelTopFive;
    ListView lv;
    Muzicar muzicar;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_top5, container, false);

        if (getArguments() != null && getArguments().containsKey("muzicar")) {
            labelTopFive = (TextView) view.findViewById(R.id.labelTopFive);
            muzicar = getArguments().getParcelable("muzicar");

            labelTopFive.setText("Albums: ");
            lv = (ListView) view.findViewById(R.id.listViewTopFive);
            Albumi adapter = new Albumi(getActivity().getApplicationContext(), muzicar);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Pair<String, String> album = muzicar.getAlbums().get(position);
                    String alb = album.second;

                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(alb));
                    startActivity(i);
                }
            });
        }
        return view;
    }
}
