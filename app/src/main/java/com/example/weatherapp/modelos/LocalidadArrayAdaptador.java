package com.example.weatherapp.modelos;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class LocalidadArrayAdaptador<T> extends ArrayAdapter<T> {

    List<Coordenadas> coordenadasLista;

    public LocalidadArrayAdaptador(@NonNull Context context, int resource, @NonNull List<T> objects, List<Coordenadas> coordenadasLista) {
        super(context, resource, objects);
        this.coordenadasLista = coordenadasLista;
    }

    public List<Coordenadas> getCoordenadasLista() {
        return coordenadasLista;
    }
}