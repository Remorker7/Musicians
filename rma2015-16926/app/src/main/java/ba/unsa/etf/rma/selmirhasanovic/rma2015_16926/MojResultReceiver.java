package ba.unsa.etf.rma.selmirhasanovic.rma2015_16926;

import android.os.Bundle;
import android.os.ResultReceiver;

import android.os.Handler;

/**
 * Created by Selmir Hasanovic on 16-May-16.
 */
public class MojResultReceiver extends ResultReceiver {
    private Receiver mReceiver;
    public MojResultReceiver(Handler handler) {
        super(handler);
    }
    public void setReceiver(Receiver receiver) {
        mReceiver = receiver;
    }
    /* Deklaracija interfejsa koji će se trebati implementirati */
    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);
    }
    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }
}
