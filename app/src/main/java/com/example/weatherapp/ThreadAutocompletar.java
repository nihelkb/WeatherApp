package com.example.weatherapp;

import android.widget.ArrayAdapter;

import com.example.weatherapp.modelos.Coordenadas;
import com.example.weatherapp.modelos.LocalidadArrayAdaptador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ThreadAutocompletar implements Runnable{


    private String textoPrevio;
    private String texto;

    private String textoActualizado;

    private MainActivity act;
    public ThreadAutocompletar(MainActivity act){
        textoPrevio = null;
        texto = "";
        this.act = act;
    }

    public void actualizarTexto(String s){
        textoActualizado = s;
    }

    @Override
    public void run() {
        while (true) {
            if (textoActualizado != null && !textoActualizado.equals(texto) && !textoActualizado.equals("")) {
                LocalidadArrayAdaptador<String> localidadesAdapter;
                List<String> localidades = new ArrayList<>();
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                Request request = new Request.Builder()
                        .url("https://api.geoapify.com/v1/geocode/autocomplete?text=" + textoActualizado + "&format=json&apiKey=3ee7094059584c3495d5f867ae85e834")
                        .method("GET", null)
                        .build();
                texto = textoActualizado;
                try {
                    Response response = client.newCall(request).execute();
                    String stringResponse = response.body().string();
                    JSONObject json = new JSONObject(stringResponse);
                    JSONArray jsonArray = json.getJSONArray("results");
                    int numeroResultados = jsonArray.length();
                    List<Coordenadas> coordenadasLista = new ArrayList<>();
                    String longitud;
                    String latitud;
                    for (int i = 0; i < numeroResultados; i++) {
                        System.out.println(jsonArray.getJSONObject(i).get("formatted"));
                        localidades.add(jsonArray.getJSONObject(i).get("formatted").toString());
                        longitud = jsonArray.getJSONObject(i).get("lon").toString();
                        latitud = jsonArray.getJSONObject(i).get("lat").toString();
                        coordenadasLista.add(new Coordenadas(longitud,latitud));
                    }
                    localidadesAdapter = new LocalidadArrayAdaptador<>(act, android.R.layout.simple_dropdown_item_1line, localidades, coordenadasLista);
                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            act.busqueda.setAdapter(localidadesAdapter);
                            localidadesAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
