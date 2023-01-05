package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView lugar;
    private TextView temperatura;
    private TextView condicion;
    private ImageView icono;
    private SearchView busqueda;
    private TextView bienvenida;
    private RecyclerView listaHoras;
    private List<ListaHorasModelo> lista;
    private ListaHorasAdaptador adaptador;
    private LocationManager localizacion;
    private int codigoPermisos = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);
        lugar = findViewById(R.id.city);
        temperatura = findViewById(R.id.temp);
        condicion = findViewById(R.id.condicion);
        icono = findViewById(R.id.fotoTiempo);
        bienvenida = findViewById(R.id.bienvenida);
        busqueda = findViewById(R.id.busqueda);
        listaHoras = findViewById(R.id.recyclerview);
        lista = new ArrayList<>();
        adaptador = new ListaHorasAdaptador(this,lista);
        listaHoras.setAdapter(adaptador);
        localizacion = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    }

    private void infoTiempo(String ciudad){
        String ulr = "https://api.weatherapi.com/v1/forecast.json?key=ae53fdd1e6f9497e9a8160639222612&q="+ciudad+"&days=7&aqi=no&alerts=yes";


    }
}