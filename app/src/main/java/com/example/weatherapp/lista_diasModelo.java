package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class lista_diasModelo {
    String icono;
    String max;
    String dia;
    String min;

    public lista_diasModelo(String icono, String max, String dia, String min) {
        this.icono = icono;
        this.max = max;
        this.dia = dia;
        this.min = min;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }


}