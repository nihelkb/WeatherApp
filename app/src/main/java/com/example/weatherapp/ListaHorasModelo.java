package com.example.weatherapp;

public class ListaHorasModelo {

    private String hora;
    private String temperatura;
    private String icono;
    private String condicion;

    public ListaHorasModelo(String hora, String temperatura, String icono, String condicion) {
        this.hora = hora;
        this.temperatura = temperatura;
        this.icono = icono;
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

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }
}
