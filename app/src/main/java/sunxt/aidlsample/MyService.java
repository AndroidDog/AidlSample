package sunxt.aidlsample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

/**
 * Created by xtsun on 2017/11/13.
 */

public class MyService extends Service {
    private static final String TAG = "MyService";

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    class MyBinder extends IMyAidlInterface.Stub {

        @Override
        public String getName() throws RemoteException {
            return TAG + ":pid-" + android.os.Process.myPid() + ":" + Thread.currentThread().toString();
        }
    }
}
