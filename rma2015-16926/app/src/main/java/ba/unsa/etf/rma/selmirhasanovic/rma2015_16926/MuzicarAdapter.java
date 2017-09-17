package ba.unsa.etf.rma.selmirhasanovic.rma2015_16926;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Selmir Hasanovic on 08-May-16.
 */
public class MuzicarAdapter extends BaseAdapter {

    Context mContext;
    int[] images;

    ImageView image;
    TextView name, genre;

    public MuzicarAdapter(Context context, int[] images) {
        this.mContext = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return Pocetni.muzicari.size();
    }

    @Override
    public Object getItem(int position) {
        return Pocetni.muzicari.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = View.inflate(mContext, R.layout.element_liste, null);
        image = (ImageView) view.findViewById(R.id.imageView);
        name = (TextView) view.findViewById(R.id.nameTv);
        genre = (TextView) view.findViewById(R.id.genreTv);
        if(position < Pocetni.muzicari.size()) {
            if(Pocetni.muzicari.get(position).getGenre().equalsIgnoreCase("pop")) image.setImageResource(R.drawable.pop);
            else if(Pocetni.muzicari.get(position).getGenre().equalsIgnoreCase("rock")) image.setImageResource(R.drawable.rok);
            else if(Pocetni.muzicari.get(position).getGenre().equalsIgnoreCase("blues")) image.setImageResource(R.drawable.blues);
            else if(Pocetni.muzicari.get(position).getGenre().equalsIgnoreCase("jazz")) image.setImageResource(R.drawable.jazz);
            else if(Pocetni.muzicari.get(position).getGenre().equalsIgnoreCase("metal")) image.setImageResource(R.drawable.metal);
            else image.setImageResource(R.drawable.slika1);
            name.setText(Pocetni.muzicari.get(position).toString());
            genre.setText(Pocetni.muzicari.get(position).getGenre());
        }
        return view;
    }
}
