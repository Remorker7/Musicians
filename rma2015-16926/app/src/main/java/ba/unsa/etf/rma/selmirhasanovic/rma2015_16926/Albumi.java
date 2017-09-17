package ba.unsa.etf.rma.selmirhasanovic.rma2015_16926;

import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Selmir Hasanovic on 30-May-16.
 */
public class Albumi extends BaseAdapter {

    Context mContext;
    TextView textViewTop5;
    Muzicar temp;

    Albumi(Context context, Muzicar temp) {
        this.mContext = context;
        this.temp = temp;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return temp.getTopFive().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.element_liste5, null);
        textViewTop5 = (TextView) view.findViewById(R.id.textViewTop5);
        if(position < temp.getAlbums().size()) {
            Pair<String, String> album = temp.getAlbums().get(position);
            textViewTop5.setText(position + 1 + ". " + album.first);
        }
        return view;
    }
}