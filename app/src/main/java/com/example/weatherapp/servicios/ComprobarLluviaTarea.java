package com.example.weatherapp.servicios;

import android.os.AsyncTask;

import com.example.weatherapp.MainActivity;
import com.example.weatherapp.modelos.Coordenadas;
import com.example.weatherapp.modelos.DatosActualModelo;
import com.example.weatherapp.modelos.DatosTiempoModelo;
import com.example.weatherapp.modelos.ParDatos;
import com.example.weatherapp.util.ServiciosWeb;
import com.example.weatherapp.util.Utils;

import java.util.List;

public class ComprobarLluviaTarea extends AsyncTask<Void,Void, Boolean> {
    private MainActivity act;

    private Coordenadas coordenadas;

    private boolean datosDescargados;

    private boolean it_will_rain;

    public ComprobarLluviaTarea(MainActivity act, Coordenadas coordenadas) {
        this.act = act;
        this.coordenadas = coordenadas;
        this.datosDescargados = false;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return ServiciosWeb.comprobarLluvia(coordenadas, act);
    }

    @Override
    protected void onPostExecute(Boolean will_it_rain) {
        super.onPostExecute(will_it_rain);
        it_will_rain = will_it_rain;
        datosDescargados = true;
    }

    public boolean datosDescargados() {
        return datosDescargados;
    }

    public boolean it_will_rain() {
        return it_will_rain;
    }
}
