package com.example.weatherapp.servicios.jobs;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

import com.example.weatherapp.util.Constantes;

public class Actualizador {
    public static void programar(Context ctx){
        ComponentName serviceComponent = new ComponentName(ctx, JobEstadoClima.class);
        JobInfo.Builder jobConf = new JobInfo.Builder(0, serviceComponent);
        jobConf.setMinimumLatency(Constantes.SERVICIO_NOTIFICACIONES_MS); // 1 hora

        JobScheduler js = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            js = ctx.getSystemService(JobScheduler.class);
            js.schedule(jobConf.build());
        }
    }
}
