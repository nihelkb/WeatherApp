package com.example.weatherapp.modelos;

public class DatosEspecificosModelo {

    private String hora;
    private String localidad;
    private String temperatura;
    private String sensacion;
    private String probabilidadLluvia;
    private String humedad;
    private String presion;
    private String viento;

    private boolean datosPreparados;

    public DatosEspecificosModelo() {
        this.hora = "";
        this.localidad = "";
        this.temperatura = "";
        this.sensacion = "";
        this.probabilidadLluvia = "";
        this.humedad = "";
        this.presion = "";
        this.viento = "";
        this.datosPreparados = false;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public void setSensacion(String sensacion) {
        this.sensacion = sensacion;
    }

    public void setProbabilidadLluvia(String probabilidadLluvia) {
        this.probabilidadLluvia = probabilidadLluvia;
    }

    public void setHumedad(String humedad) {
        this.humedad = humedad;
    }

    public void setPresion(String presion) {
        this.presion = presion;
    }

    public void setViento(String viento) {
        this.viento = viento;
    }

    public String getHora() {
        return hora;
    }

    public String getLocalidad() {
        return localidad;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public String getSensacion() {
        return sensacion;
    }

    public String getProbabilidadLluvia() {
        return probabilidadLluvia;
    }

    public String getHumedad() {
        return humedad;
    }

    public String getPresion() {
        return presion;
    }

    public String getViento() {
        return viento;
    }

    public void setDatosPreparados(boolean datosPreparados) {
        this.datosPreparados = datosPreparados;
    }

    public boolean datosPreparados() {
        return datosPreparados;
    }

    @Override
    public String toString() {
        return "DatosEspecificosModelo{" +
                "hora='" + hora + '\'' +
                ", localidad='" + localidad + '\'' +
                ", temperatura='" + temperatura + '\'' +
                ", sensacion='" + sensacion + '\'' +
                ", probabilidadLluvia='" + probabilidadLluvia + '\'' +
                ", humedad='" + humedad + '\'' +
                ", presion='" + presion + '\'' +
                ", viento='" + viento + '\'' +
                ", datosPreparados=" + datosPreparados +
                '}';
    }
}