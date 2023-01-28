package com.example.weatherapp.servicios;

import android.os.AsyncTask;
import android.view.View;

import com.example.weatherapp.MainActivity;
import com.example.weatherapp.modelos.Coordenadas;
import com.example.weatherapp.modelos.ParDatos;
import com.example.weatherapp.modelos.DatosActualModelo;
import com.example.weatherapp.modelos.DatosTiempoModelo;
import com.example.weatherapp.util.Constantes;
import com.example.weatherapp.util.ServiciosWeb;
import com.example.weatherapp.util.Utils;

import java.util.List;

public class DescargarDatosTiempoTarea extends AsyncTask<Void,Void, ParDatos> {
    private MainActivity act;

    private Coordenadas coordenadas;

    private boolean datosDescargados;

    private ParDatos datos;

    public DescargarDatosTiempoTarea(MainActivity act, Coordenadas coordenadas){
        this.act = act;
        this.coordenadas = coordenadas;
        this.datosDescargados = false;
    }

    @Override
    protected ParDatos doInBackground(Void... voids) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MainActivity.act.home.setVisibility(View.GONE);
                MainActivity.act.loading.setVisibility(View.VISIBLE);
            }
        });

        return ServiciosWeb.getInfoTiempo(coordenadas,act);
    }

    @Override
    protected void onPostExecute(ParDatos datos) {
        super.onPostExecute(datos);
        DatosActualModelo tiempoActual = datos.getDatosTiempoActual();
        List<DatosTiempoModelo> listaTiempos = datos.getListaTiempos();
        act.lugar.setText(tiempoActual.getLugar());
        act.temperatura.setText(tiempoActual.getTemperatura());
        String url;
        if(Constantes.icons.containsKey(tiempoActual.getCodigo())){
            if(("1000").equals(tiempoActual.getCodigo())){ // Soleado o despejado
                if(("Despejado").equals(tiempoActual.getCondicion())){ // Noche
                    url = Constantes.icons.get("1001");
                }else{
                    url = Constantes.icons.get("1000");
                }
            }else{
                url = Constantes.icons.get(tiempoActual.getCodigo());
            }
            Utils.setImageToImageView(act.icono,url);
        }else{
            Utils.setImageToImageView(act.icono,tiempoActual.getImagen());
        }
        act.condicion.setText(tiempoActual.getCondicion());
        act.lista = listaTiempos;
        act.adaptador.setListaModelo(act.lista);
        act.adaptador.notifyDataSetChanged();

        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MainActivity.act.loading.setVisibility(View.GONE);
                MainActivity.act.home.setVisibility(View.VISIBLE);
            }
        });
    }
}
