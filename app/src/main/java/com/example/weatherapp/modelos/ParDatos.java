package com.example.weatherapp.modelos;

import com.example.weatherapp.modelos.DatosActualModelo;
import com.example.weatherapp.modelos.DatosTiempoModelo;

import java.util.List;

public class ParDatos {
    private DatosActualModelo datosTiempoActual;
    private List<DatosTiempoModelo> listaTiempos;

    private boolean datosListos;


    public ParDatos() {
        this.datosTiempoActual = null;
        this.listaTiempos = null;
        this.datosListos = false;
    }

    public ParDatos(DatosActualModelo datosTiempoActual, List<DatosTiempoModelo> listaTiempos) {
        this.datosTiempoActual = datosTiempoActual;
        this.listaTiempos = listaTiempos;
    }

    public DatosActualModelo getDatosTiempoActual() {
        return datosTiempoActual;
    }

    public List<DatosTiempoModelo> getListaTiempos() {
        return listaTiempos;
    }

    public void setDatosTiempoActual(DatosActualModelo datosTiempoActual) {
        this.datosTiempoActual = datosTiempoActual;
    }

    public void setListaTiempos(List<DatosTiempoModelo> listaTiempos) {
        this.listaTiempos = listaTiempos;
    }

    public void setDatosListos(boolean datosListos) {
        this.datosListos = datosListos;
    }

    public boolean getDatosListos() {
        return datosListos;
    }
}