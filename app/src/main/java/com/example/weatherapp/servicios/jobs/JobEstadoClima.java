package com.example.weatherapp.servicios.jobs;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.location.Location;

import com.example.weatherapp.MainActivity;
import com.example.weatherapp.modelos.Coordenadas;
import com.example.weatherapp.notificaciones.ManejadorNotificacion;
import com.example.weatherapp.servicios.ComprobarLluviaTarea;
import com.example.weatherapp.servicios.ThreadNotificacion;
import com.example.weatherapp.util.ServiciosWeb;

public class JobEstadoClima extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Thread th = new Thread(new ThreadNotificacion(this, new ManejadorNotificacion(this)));
        th.start();
        Actualizador.programar(this);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
