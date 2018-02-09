package com.bmx.kike.serviciovinculado;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.Toast;

/**
 * Created by quiqu on 08/02/2018.
 */

public class MessengerService extends Service {
    /** Comando para que el servicio muestre un mensaje */
    static final int MSG_SAY_HELLO = 1;

    /**
     * Handler de mensajes que vienen de los clientes.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SAY_HELLO:
                    Toast.makeText(getApplicationContext(), "hello!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    /**
     * Diana que publicamos para que los clientes manden mensajes al IncomingHandler.
     */
    final Messenger mMessenger = new Messenger(new IncomingHandler());

    /**
     * Cuando se vincula al servicio, se devuelve una interfaz a nuestro mensajero
     * para mandar mensajes al servicio.
     */
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(), "binding", Toast.LENGTH_SHORT).show();
        return mMessenger.getBinder();
    }
}
