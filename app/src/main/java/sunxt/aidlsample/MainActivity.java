package sunxt.aidlsample;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mBindServiceBtn;
    private Button mGetNameBtn;

    private IMyAidlInterface mRemoteService;

    private MyServiceConnection mServiceConnection = new MyServiceConnection();

    class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mRemoteService = IMyAidlInterface.Stub.asInterface(service);
            Toast.makeText(getApplication(), "bind service success", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mRemoteService = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBindServiceBtn = (Button) findViewById(R.id.bind_service);
        mGetNameBtn = (Button) findViewById(R.id.get_name_from_service);

        mBindServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyService.class);
                bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
            }
        });
        mGetNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRemoteService != null) {
                    try {
                        Toast.makeText(getApplication(), "get name grom remote service: "
                                + mRemoteService.getName(), Toast.LENGTH_LONG).show();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplication(), "remote service = null", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
