package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    private LocationManager localizacionM;
    private String ubicacionAct;
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

        localizacionM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, codigoPermisos);
        }
        Location localizacion = localizacionM.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        ubicacionAct = getNombreCiudad(localizacion.getLongitude(), localizacion.getAltitude());
        getInfoTiempo(ubicacionAct);

        busqueda.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String ciudad) {
                if(ciudad.isEmpty()){
                    Toast.makeText(MainActivity.this, "Introduce una ciudad", Toast.LENGTH_SHORT).show();
                }else{
                    lugar.setText(ciudad);
                    getInfoTiempo(ciudad);
                }
                return true;
            }

            @Override // Se puede utilizar para sugerir los paises (opcional)
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        //String ciudadInput = busqueda.getQuery().toString();

    }

    private String getNombreCiudad(double x, double y){
        String ciudad = "No encontrada";
        Geocoder geoloc = new Geocoder(getBaseContext(), Locale.getDefault());
        try{
            List<Address> posiblesCiudades = geoloc.getFromLocation(x, y, 10);
            String nombre;
            for(int i = 0; i < posiblesCiudades.size(); i++){
               Address dir = posiblesCiudades.get(i);
               nombre = dir.getLocality();
               if(nombre != null && !nombre.equals("")){
                   ciudad = nombre;
               }else{
                   Log.d("TAG", "No se ha encontrado la ciudad");
                   Toast.makeText(this, "Ubicación real no encontrada", Toast.LENGTH_SHORT).show();
               }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return ciudad;
    }

    private void getInfoTiempo(String ciudad){
        String ulr = "https://api.weatherapi.com/v1/forecast.json?key=ae53fdd1e6f9497e9a8160639222612&q="+ciudad+"&days=7&aqi=no&alerts=yes";


    }
}