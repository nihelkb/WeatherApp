package com.example.weatherapp.modelos;

import android.graphics.Bitmap;

public class DatosTiempoModelo {

    private String hora;
    private String temperatura;
    private String imagen;
    private String condicion;

    public DatosTiempoModelo(String hora, String temperatura, String icono, String condicion) {
        this.hora = hora;
        this.temperatura = temperatura;
        this.imagen = icono;
        this.condicion = condicion;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }
}
