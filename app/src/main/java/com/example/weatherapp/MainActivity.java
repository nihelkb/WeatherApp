package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView lugar;
    private TextView temperatura;
    private TextView condicion;
    private ImageView icono;
    private ImageView fondo;
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
        fondo = findViewById(R.id.fondo);
        bienvenida = findViewById(R.id.bienvenida);
        busqueda = findViewById(R.id.busqueda);
        listaHoras = findViewById(R.id.recyclerview);
        lista = new ArrayList<>();
        adaptador = new ListaHorasAdaptador(this,lista);
        listaHoras.setAdapter(adaptador);

        localizacionM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
                new AlertDialog.Builder(this).setTitle("Se necesitan permisos").setMessage("Se necesita el permiso de ubicación").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, codigoPermisos);

                    }
                }).setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                }).create().show();
            }
            else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, codigoPermisos);
            }

        }

        Location localizacion = localizacionM.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        String logitud = localizacion.getLongitude()+"";
        String latitud = localizacion.getLatitude()+"";
        Log.d("logitud", logitud);
        Log.d("logitud", latitud);
        ubicacionAct = getNombreCiudad(localizacion.getLongitude(), localizacion.getLatitude());
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == codigoPermisos) {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permisos concedidos", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Por favor, conceda los permisos", Toast.LENGTH_SHORT).show();
                Log.d("casa","pedrito");
                finish();

            }
        }
    }

    private void getInfoTiempo(String ciudad){
        String url = "https://api.weatherapi.com/v1/forecast.json?key=ae53fdd1e6f9497e9a8160639222612&q="+ciudad+"&days=7&aqi=no&alerts=yes";
        lugar.setText(ciudad);
        RequestQueue peticion = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonPet = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {
                lista.clear();
                try{
                    String temperaturaJson = response.getJSONObject("current").getString("temp_c");
                    temperatura.setText(temperaturaJson+"ºC");
                    int dia = response.getJSONObject("current").getInt("is_day");
                    String cond = response.getJSONObject("current").getJSONObject("condition").getString("text");
                    String iconoCond = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                    Picasso.get().load("http:".concat(iconoCond)).into(icono);
                    condicion.setText(cond);
                    if(dia == 1){
                        Picasso.get().load("https://images.unsplash.com/photo-1558418294-9da149757efe?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80").into(fondo);
                    }else{
                        Picasso.get().load("https://images.unsplash.com/photo-1472552944129-b035e9ea3744?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80").into(fondo);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Introduzca una ciudad valida", Toast.LENGTH_SHORT).show();

            }
        });
        peticion.add(jsonPet);


    }
}