package com.example.weatherapp.modelos;

import android.graphics.Bitmap;

public class DatosActualModelo {
    private String lugar;
    private String temperatura;
    private String imagen;
    private String condicion;
    private String codigo;


    public DatosActualModelo() {
        this.lugar = "";
        this.temperatura = "";
        this.imagen = "";
        this.condicion = "";
        this.codigo = "";
    }

    public DatosActualModelo(String lugar, String temperatura, String imagen, String condicion, String codigo) {
        this.lugar = lugar;
        this.temperatura = temperatura;
        this.imagen = imagen;
        this.condicion = condicion;
        this.codigo = codigo;
    }

    public String getLugar() {
        return lugar;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public String getImagen() {
        return imagen;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public String getCodigo() { return codigo; }

    public void setCodigo(String codigo) { this.codigo = codigo; }
}