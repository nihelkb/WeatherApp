package com.example.weatherapp.modelos;

public class Coordenadas {

    private String longitud;
    private String latitud;

    public Coordenadas(String longitud, String latitud) {
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    @Override
    public String toString() {
        return "Coordenadas{" +
                "longitud='" + longitud + '\'' +
                ", latitud='" + latitud + '\'' +
                '}';
    }
}