package ba.unsa.etf.rma.selmirhasanovic.rma2015_16926;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Selmir Hasanovic on 08-May-16.
 */
public class Top5Fragment extends Fragment {
    TextView labelTopFive;
    ListView lv;
    Muzicar muzicar;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_top5, container, false);

        if (getArguments() != null && getArguments().containsKey("muzicar")) {
            labelTopFive = (TextView) view.findViewById(R.id.labelTopFive);
            labelTopFive.setText(R.string.labelTopFive);
            labelTopFive.setText(labelTopFive.getText() + ":");

            lv = (ListView) view.findViewById(R.id.listViewTopFive);
            muzicar = getArguments().getParcelable("muzicar");
            Top5 adapter = new Top5(getActivity().getApplicationContext(), muzicar);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String videoId = muzicar.getIme() + " " + muzicar.getPrezime() + " " + muzicar.getTopFive().get(position);

                    Intent intent = new Intent(Intent.ACTION_SEARCH);
                    intent.setPackage("com.google.android.youtube");
                    intent.putExtra("query", videoId);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
        }
        return view;
    }
}
