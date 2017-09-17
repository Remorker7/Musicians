package ba.unsa.etf.rma.selmirhasanovic.rma2015_16926;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Selmir Hasanovic on 08-May-16.
 */
public class SlicniMuzicari extends BaseAdapter {

    Context mContext;
    TextView textView;
    ArrayList<String> artists;

    public SlicniMuzicari(Context mContext, ArrayList<String> artists) {
        this.mContext = mContext;
        this.artists = artists;
    }

    @Override
    public int getCount() {
        return artists.size();
    }

    @Override
    public Object getItem(int position) {
        return artists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.element_liste5, null);
        textView = (TextView) view.findViewById(R.id.textViewTop5);
        textView.setText(artists.get(position));

        return view;
    }
}
