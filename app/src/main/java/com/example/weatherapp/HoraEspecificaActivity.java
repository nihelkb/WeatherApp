package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.weatherapp.modelos.Coordenadas;
import com.example.weatherapp.servicios.DescargarDatosEspecificosTarea;


public class HoraEspecificaActivity extends AppCompatActivity {
    public static HoraEspecificaActivity act;
    public TextView hora;
    public TextView localizacion;
    public TextView temperatura;
    public TextView sensacion;
    public TextView humedad;
    public TextView precipitaciones;
    public TextView viento;
    public TextView presion;

    public ConstraintLayout home;

    public ProgressBar loading;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especifica);
        act = this;
        hora = findViewById(R.id.especifico_hora);
        localizacion = findViewById(R.id.especifico_loc);
        temperatura = findViewById(R.id.especifico_temp);
        sensacion = findViewById(R.id.especifico_sens);
        humedad = findViewById(R.id.especifico_hum);
        precipitaciones = findViewById(R.id.especifico_prob);
        viento = findViewById(R.id.especifico_viento);
        presion = findViewById(R.id.especifico_presion);
        home = findViewById(R.id.constraint_layout_especifica);
        loading = findViewById(R.id.progressBarEspecifico);

        Bundle extras = getIntent().getExtras();
        String lon = extras.getString("lon");
        String lat = extras.getString("lat");
        int pos = extras.getInt("pos");
        Coordenadas coords = new Coordenadas(lon,lat);
        DescargarDatosEspecificosTarea datosEspecificos = new DescargarDatosEspecificosTarea(act,coords,pos);
        datosEspecificos.execute();
        System.out.println(home + " " + loading);

        //String temperatura = getIntent().getStringExtra()

    }



}
