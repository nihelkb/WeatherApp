package com.example.weatherapp.servicios;

import android.content.Context;
import android.location.Location;

import com.example.weatherapp.MainActivity;
import com.example.weatherapp.contexto.UltimaLocalizacion;
import com.example.weatherapp.modelos.Coordenadas;
import com.example.weatherapp.notificaciones.ManejadorNotificacion;

public class ThreadNotificacion implements Runnable{

    private Context ctx;
    private ManejadorNotificacion manejadorNotificacion;

    public ThreadNotificacion(Context ctx, ManejadorNotificacion manejadorNotificacion) {
        this.ctx = ctx;
        this.manejadorNotificacion = manejadorNotificacion;
    }

    public void run() {
        Coordenadas coord;
        Location location;
        Boolean it_will_rain = null;
        if (MainActivity.act != null) {
            location = MainActivity.act.getLastKnownLocation();
            if (location == null) {
                location = UltimaLocalizacion.ultimaLocalizacion;
            }
        }
        else {
            location = UltimaLocalizacion.ultimaLocalizacion;
        }
        if (location != null){
            coord = new Coordenadas(location.getLongitude() + "", location.getLatitude() + "");
            ComprobarLluviaTarea tarea = new ComprobarLluviaTarea(MainActivity.act, coord);
            tarea.execute();
            while (!tarea.datosDescargados()) ;
            it_will_rain = tarea.it_will_rain();
        }
        manejadorNotificacion.getNotificationManager().notify(1, manejadorNotificacion.createNotification(it_will_rain).build());
    }
}
