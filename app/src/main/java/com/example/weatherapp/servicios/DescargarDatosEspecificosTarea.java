package com.example.weatherapp.servicios;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;

import com.example.weatherapp.HoraEspecificaActivity;
import com.example.weatherapp.MainActivity;
import com.example.weatherapp.modelos.Coordenadas;
import com.example.weatherapp.modelos.DatosActualModelo;
import com.example.weatherapp.modelos.DatosEspecificosModelo;
import com.example.weatherapp.modelos.DatosTiempoModelo;
import com.example.weatherapp.modelos.ParDatos;
import com.example.weatherapp.util.ServiciosWeb;
import com.example.weatherapp.util.Utils;

import java.util.List;

public class DescargarDatosEspecificosTarea extends AsyncTask<Void,Void, DatosEspecificosModelo> {
    private Activity act;

    private Coordenadas coordenadas;

    private boolean datosDescargados;

    private DatosEspecificosModelo datos;

    private int index;

    public DescargarDatosEspecificosTarea(Activity act, Coordenadas coordenadas, int index){
        this.act = act;
        this.coordenadas = coordenadas;
        this.index = index;
        this.datosDescargados = false;
    }

    @Override
    protected DatosEspecificosModelo doInBackground(Void... voids) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                HoraEspecificaActivity.act.home.setVisibility(View.GONE);
                HoraEspecificaActivity.act.loading.setVisibility(View.VISIBLE);
            }
        });
        return ServiciosWeb.getInfoEspecifica(coordenadas,act,index);
    }

    @Override
    protected void onPostExecute(DatosEspecificosModelo datos) {
        super.onPostExecute(datos);
        HoraEspecificaActivity act = HoraEspecificaActivity.act;
        act.hora.setText(datos.getHora());
        act.localizacion.setText(datos.getLocalidad());
        act.temperatura.setText(datos.getTemperatura());
        act.sensacion.setText(datos.getSensacion());
        act.humedad.setText(datos.getHumedad());
        act.precipitaciones.setText(datos.getProbabilidadLluvia());
        act.viento.setText(datos.getViento());
        act.presion.setText(datos.getPresion());
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                act.loading.setVisibility(View.GONE);
                act.home.setVisibility(View.VISIBLE);
            }
        });

        System.out.println(datos);
        /**act.lugar.setText(datos.getLugar());
        act.temperatura.setText(tiempoActual.getTemperatura());
        if (tiempoActual.getCondicion() == null);
        Utils.setImageToImageView(act.icono,tiempoActual.getImagen());
        act.condicion.setText(tiempoActual.getCondicion());
        act.lista = listaTiempos;
        act.adaptador.setListaModelo(act.lista);
        act.adaptador.notifyDataSetChanged();*/
    }
}
