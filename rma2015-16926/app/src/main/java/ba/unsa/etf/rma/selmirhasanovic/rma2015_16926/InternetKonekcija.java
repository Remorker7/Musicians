package ba.unsa.etf.rma.selmirhasanovic.rma2015_16926;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Selmir Hasanovic on 21-Mar-16.
 */
public class InternetKonekcija extends BroadcastReceiver {

    NetworkInfo wifi;
    NetworkInfo mobile;
    ConnectivityManager connMng;
    @Override
    public void onReceive(Context context, Intent intent) {
        connMng = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        mobile = connMng.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        wifi = connMng.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mobile != null && !mobile.isConnected() && !wifi.isConnected()) {
            Toast.makeText(context, "Nema internet konekcije", Toast.LENGTH_SHORT).show();
        }

    }
}
