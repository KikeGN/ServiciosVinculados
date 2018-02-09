package com.bmx.kike.serviciovinculado;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by quiqu on 08/02/2018.
 */

public class ActivityMessenger extends AppCompatActivity {
    /** Mensajero para comunicarse con el servicio. */
    Messenger mService = null;

    /** Flag indicando si se ha llamado a bind en el servicio. */
    boolean mBound;

    /**
     * Clase para interaccionar con la interfaz principal del servicio.
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // Se llama a este método cuando la conexión con el servicio ha sido
            // establecida, dándonos el objeto que podemos utilizar
            // para interactuar con el servicio. Nos estamos comunicando con el
            // servicio utilizando un Messenger, por lo que aquí obtenemos una representación
            // del lado del cliente del objeto IBinder.
            mService = new Messenger(service);
            mBound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            // Se llama a este método cuando la conexión con el servicio ha sido
            // desconectada inesperadamente-- su proceso ha sido termindado.
            mService = null;
            mBound = false;
        }
    };

    public void sayHello(View v) {
        if (!mBound) return;
        // Crear y mandar un mensaje al servicio, utilizando un valor 'what'.
        Message msg = Message.obtain(null, MessengerService.MSG_SAY_HELLO, 0, 0);
        try {
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    public void sayBye(View v) {
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
        Toast.makeText(this, "Bye", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Vincular al servicio
        bindService(new Intent(this, MessengerService.class), mConnection,
                Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Desvincular del servicio
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }
}
