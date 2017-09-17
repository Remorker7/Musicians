package ba.unsa.etf.rma.selmirhasanovic.rma2015_16926;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Selmir Hasanovic on 08-May-16.
 */
public class Podaci extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podaci);
        final TextView nameInfo = (TextView) findViewById(R.id.imeMuzicara);
        TextView genreInfo = (TextView) findViewById(R.id.zanrMuzicara);
        final TextView webInfo = (TextView) findViewById(R.id.webStranica);
        final TextView bioInfo = (TextView) findViewById(R.id.biografijaMuzicara);
        //final Button sendInfo = (Button) findViewById(R.id.posaljiPodatke);

        nameInfo.setText("Name: " + getIntent().getStringExtra("name"));
        genreInfo.setText("Genre: " + getIntent().getStringExtra("genre"));
        webInfo.setText("Web page:\n" + getIntent().getStringExtra("webPage"));
        bioInfo.setText("Biography:\n" + getIntent().getStringExtra("biography"));
    }
}
