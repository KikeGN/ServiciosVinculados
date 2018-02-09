package com.bmx.kike.serviciovinculado;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by quiqu on 08/02/2018.
 */

public class BindingActivity extends AppCompatActivity {
    LocalService mService;
    boolean mBound = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Se vincula al LocalService
        Intent intent = new Intent(this, LocalService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onStop() {
        super.onStop();
        // se desvincula del servicio
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    /** Se llama cuando se hace click en un botón (el botón en el archivo layout se une a
     * este método con el atributo android:onClick)
    public void onButtonClick(View v) {
        if (mBound) {
            // Llama a un método desde el LocalService.
            // Sin embargo, si en esta llamada hubiera algo suspendido, entonces la petición debería
            // realizarse en un hilo por separado para evitar la ralentización de la actividad.
            int num = mService.getRandomNumber();
            Toast.makeText(this, "number: " + num, Toast.LENGTH_SHORT).show();
        }
    }*/

    /** Define los callback pasados al método bindService() para la vinculación de los servicios*/
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // Se ha hecho la vinculación al LocalService, el cast del IBinder y un get de la instancia del LocalService
            LocalService.LocalBinder binder = (LocalService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            Log.e(null, "onServiceDisconnected");
            mBound = false;
        }
       

    };

}
