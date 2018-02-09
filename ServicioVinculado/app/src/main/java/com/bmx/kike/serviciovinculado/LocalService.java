package com.bmx.kike.serviciovinculado;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

/**
 * Created by quiqu on 08/02/2018.
 */

public class LocalService  extends Service {
    // Binder dado a los clientes
    private final IBinder mBinder = new LocalBinder();
    // Generador aleatorio de números
    private final Random mGenerator = new Random();

    /**
     * Clase utilizada por el cliente Binder.  Como se sabe que este servicio
     * se ejecuta siempre en el mismo proceso que sus clientes, no se necesita tratar con el IPC.
     */
    public class LocalBinder extends Binder {
        LocalService getService() {
            // Devolver esta instancia del LocalService para que los clientes puedan llamar a métodos públicos
            return LocalService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    /** método para clientes */
    public int getRandomNumber() {
        return mGenerator.nextInt(100);
    }
}
