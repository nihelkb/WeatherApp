package com.example.weatherapp.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.location.Location;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.MainActivity;
import com.example.weatherapp.modelos.Coordenadas;
import com.example.weatherapp.modelos.DatosActualModelo;
import com.example.weatherapp.modelos.DatosEspecificosModelo;
import com.example.weatherapp.modelos.DatosTiempoModelo;
import com.example.weatherapp.modelos.ParDatos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ServiciosWeb {

    private static ParDatos parDatos = null;
    private static Boolean comprobarLluviaRespuesta = null;

    private static DatosEspecificosModelo datosEspecificos = null;

    private static class ThreadClimaGeneral implements Runnable {
        private Coordenadas coordenadas;
        private MainActivity act;
        public ThreadClimaGeneral(Coordenadas coordenadas, MainActivity act){
            this.coordenadas = coordenadas;
            this.act = act;
        }

        public void run() {
            DatosActualModelo tiempoActual = new DatosActualModelo();
            List<DatosTiempoModelo> datos = new ArrayList<>();
            String url = "https://api.weatherapi.com/v1/forecast.json?key=ae53fdd1e6f9497e9a8160639222612&q="+coordenadas.getLatitud()+","+coordenadas.getLongitud()+"&days=7&aqi=no&alerts=no&lang=es";
            RequestQueue peticion = Volley.newRequestQueue(act);
            RequestFuture<JSONObject> future = RequestFuture.newFuture();
            JsonObjectRequest request = new JsonObjectRequest(url, new JSONObject(), future, future);
            peticion.add(request);
            JsonObjectRequest jsonPet = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(JSONObject response) {
                    parDatos = new ParDatos();
                    datos.clear();
                    try{
                        System.out.println("Start");
                        String temperaturaJson = response.getJSONObject("current").getString("temp_c");
                        System.out.println(coordenadas);
                        String lugar = response.getJSONObject("location").getString("name");
                        String temperatura;
                        String iconoUrl;
                        String condicion;

                        temperatura = temperaturaJson+"ºC";
                        int dia = response.getJSONObject("current").getInt("is_day");
                        String cond = response.getJSONObject("current").getJSONObject("condition").getString("text");
                        condicion = cond;
                        String iconoCond = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                        String codigo = response.getJSONObject("current").getJSONObject("condition").getString("code");
                        iconoUrl = iconoCond;
                        tiempoActual.setLugar(lugar);
                        tiempoActual.setCondicion(condicion);
                        tiempoActual.setTemperatura(temperatura);
                        tiempoActual.setImagen(iconoUrl);
                        tiempoActual.setCodigo(codigo);
                        parDatos.setDatosTiempoActual(tiempoActual);
                        JSONObject prevision = response.getJSONObject("forecast");
                        JSONObject prevHoy = prevision.getJSONArray("forecastday").getJSONObject(0); // dia actual
                        JSONArray prevHoras = prevHoy.getJSONArray("hour");

                        int horaAc = 0;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            Instant instante = Instant.now();
                            LocalDateTime ldt = LocalDateTime.ofInstant(instante, ZoneId.systemDefault());
                            horaAc = ldt.getHour();
                        }

                        for(int i = 0; i < prevHoras.length(); i++){
                            JSONObject horaAct = prevHoras.getJSONObject(i);
                            String hora = horaAct.getString("time");
                            String []horas = hora.split(" ");
                            horas = horas[1].split(":");
                            int horaLista = Integer.parseInt(horas[0]);
                            if(horaLista < horaAc){
                                continue;
                            }
                            String temp = horaAct.getString("temp_c");
                            //condicion = horaAct.getString("wind_kph"); // cambiar
                            condicion = horaAct.getString("chance_of_rain");
                            iconoUrl = horaAct.getJSONObject("condition").getString("icon");
                            datos.add(new DatosTiempoModelo(hora, temp, iconoUrl, condicion));
                        }
                        parDatos.setListaTiempos(datos);
                        parDatos.setDatosListos(true);
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(act, "Introduzca una ciudad valida", Toast.LENGTH_SHORT).show();
                }
            });
            peticion.add(jsonPet);
        }
    }

    private static class ThreadComprobarLluvia implements Runnable {

        private Coordenadas coordenadas;
        private MainActivity act;

        public ThreadComprobarLluvia(Coordenadas coordenadas, MainActivity act){
            this.coordenadas = coordenadas;
            this.act = act;
        }

        public void run() {
            String url = "https://api.weatherapi.com/v1/forecast.json?key=ae53fdd1e6f9497e9a8160639222612&q="+coordenadas.getLatitud()+","+coordenadas.getLongitud()+"&days=7&aqi=no&alerts=no&lang=es";
            RequestQueue peticion = Volley.newRequestQueue(act);
            RequestFuture<JSONObject> future = RequestFuture.newFuture();
            JsonObjectRequest request = new JsonObjectRequest(url, new JSONObject(), future, future);
            peticion.add(request);
            JsonObjectRequest jsonPet = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(JSONObject response) {
                    comprobarLluviaRespuesta = new Boolean(null);
                    try{
                        JSONObject datosHora = response.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(0).getJSONArray("hour").getJSONObject(0);
                        String will_it_rain = datosHora.get("will_it_rain").toString();
                        System.out.println(will_it_rain);
                        comprobarLluviaRespuesta = "1".equals(will_it_rain);
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(act, "Introduzca una ciudad valida", Toast.LENGTH_SHORT).show();
                }
            });
            peticion.add(jsonPet);
        }
    }

    private static class ThreadDatosEspecificos implements Runnable {

        private Coordenadas coordenadas;
        private Activity act;

        private int index;

        public ThreadDatosEspecificos(Coordenadas coordenadas, Activity act, int index){
            this.coordenadas = coordenadas;
            this.act = act;
            this.index = index;
        }

        public void run() {
            String url = "https://api.weatherapi.com/v1/forecast.json?key=ae53fdd1e6f9497e9a8160639222612&q="+coordenadas.getLatitud()+","+coordenadas.getLongitud()+"&days=7&aqi=no&alerts=no&lang=es";
            RequestQueue peticion = Volley.newRequestQueue(act);
            RequestFuture<JSONObject> future = RequestFuture.newFuture();
            JsonObjectRequest request = new JsonObjectRequest(url, new JSONObject(), future, future);
            peticion.add(request);
            JsonObjectRequest jsonPet = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(JSONObject response) {
                    datosEspecificos = new DatosEspecificosModelo();
                    try{
                        System.out.println("Start");
                        String temperaturaJson = response.getJSONObject("current").getString("temp_c");
                        System.out.println(coordenadas);
                        JSONObject forecastDayHour = response.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(0).getJSONArray("hour").getJSONObject(index);
                        System.out.println("FORECAST " + forecastDayHour.toString());
                        String lugar = response.getJSONObject("location").getString("name");
                        String temperatura = forecastDayHour.getString("temp_c") + "ºC";
                        String humedad = forecastDayHour.getString("humidity") + "%";
                        String condicion = forecastDayHour.getJSONObject("condition").getString("text");
                        String hora = forecastDayHour.getString("time").split(" ")[1];
                        String probabilidadLluvia = forecastDayHour.getString("chance_of_rain") + "%";
                        String presion = forecastDayHour.getString("pressure_mb") + " mb";
                        String sensacion = forecastDayHour.getString("feelslike_c") + "ºC";
                        String viento = forecastDayHour.getString("wind_kph") + " km/h";
                        datosEspecificos.setLocalidad(lugar);
                        datosEspecificos.setHora(hora);
                        datosEspecificos.setHumedad(humedad);
                        datosEspecificos.setTemperatura(temperatura);
                        datosEspecificos.setProbabilidadLluvia(probabilidadLluvia);
                        datosEspecificos.setPresion(presion);
                        datosEspecificos.setSensacion(sensacion);
                        datosEspecificos.setViento(viento);
                        datosEspecificos.setDatosPreparados(true);
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(act, "Introduzca una ciudad valida", Toast.LENGTH_SHORT).show();
                }
            });
            peticion.add(jsonPet);
        }
    }

    private static void waitForResponse(){
        while (parDatos == null);
        while(!parDatos.getDatosListos());
        parDatos.setDatosListos(false);
    }

    private static void esperarRespuestaComprobarLluvia(){
        while(comprobarLluviaRespuesta == null);
    }

    private static void esperarRespuestaDatosEspecificos(){
        while(datosEspecificos == null);
        while(!datosEspecificos.datosPreparados());
        datosEspecificos.setDatosPreparados(false);
    }

    public static ParDatos getInfoTiempo(Coordenadas coordenadas, MainActivity act) {
        ThreadClimaGeneral query = new ThreadClimaGeneral(coordenadas,act);
        Thread th = new Thread(query);
        th.start();
        waitForResponse();
        return parDatos;
    }

    public static Boolean comprobarLluvia(Coordenadas coordenadas, MainActivity act){
        System.out.println("Estas son mis coordenadas: " + coordenadas);
        ThreadComprobarLluvia query = new ThreadComprobarLluvia(coordenadas,act);
        query.run();
        esperarRespuestaComprobarLluvia();
        Boolean will_it_rain = comprobarLluviaRespuesta;
        comprobarLluviaRespuesta = null;
        System.out.println(will_it_rain);
        return will_it_rain;
    }

    public static DatosEspecificosModelo getInfoEspecifica(Coordenadas coordenadas, Activity act, int index){
        ThreadDatosEspecificos query = new ThreadDatosEspecificos(coordenadas,act,index);
        Thread th = new Thread(query);
        th.start();
        esperarRespuestaDatosEspecificos();
        return datosEspecificos;
    }
}
