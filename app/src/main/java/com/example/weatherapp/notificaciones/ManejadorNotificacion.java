package com.example.weatherapp.notificaciones;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;

import androidx.core.content.ContextCompat;

import com.example.weatherapp.MainActivity;
import com.example.weatherapp.R;
import com.example.weatherapp.util.Utils;

public class ManejadorNotificacion extends ContextWrapper {

    NotificationManager notificationManager;

    //High priority channel
    private static final String HIGH_PRIORITY_ID = "highPriorityChannel";

    /**
     * Constructor that creates the notification channels.
     * @param base Environment of the call
     */
    public ManejadorNotificacion(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels();
        }
    }

    public NotificationManager getNotificationManager(){
        if(notificationManager == null){
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    public Notification.Builder createNotification(Boolean will_it_rain){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return createNotificationUnderO(will_it_rain);
        String titulo, texto;
        int recursoIcono;
        if (will_it_rain == null){
            titulo = "Te echamos de menos!";
            texto = "Ven a ver el clima del mundo!";
            recursoIcono = R.drawable.baseline_heart_broken_24;
        }
        else if(will_it_rain){
            titulo = "Va a llover!";
            texto = "Recuerda llevar un paraguas si sales de casa.";
            recursoIcono = R.drawable.baseline_water_drop_24;
        }
        else {
            titulo = "Sin lluvias previstas";
            texto = "Puedes salir a la calle sin paraguas.";
            recursoIcono = R.drawable.baseline_wb_sunny_24;
        }
        Intent i = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 1, i, PendingIntent.FLAG_MUTABLE);

        Notification.Builder notification = new Notification.Builder(this, HIGH_PRIORITY_ID)
                .setLargeIcon(Utils.getBitmapFromVectorDrawable(this,recursoIcono))
                .setSmallIcon(R.drawable.icon)
                .setColor(getColor(R.color.clr_mainApp))
                .setContentTitle(titulo)
                .setContentText(texto)
                .setShowWhen(true)
                .setContentIntent(pi);

        return notification;
    }

    private Notification.Builder createNotificationUnderO(Boolean will_it_rain){
        Intent i = new Intent(this, MainActivity.class);

        PendingIntent pi = PendingIntent.getActivity(this, 1, i, PendingIntent.FLAG_MUTABLE);

        return new Notification.Builder(this)
                .setContentTitle("")
                .setContentText("")
                .setShowWhen(true)
                .setContentIntent(pi);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannels(){
        NotificationChannel channelHigh = new NotificationChannel(HIGH_PRIORITY_ID,
                "Lluvia",NotificationManager.IMPORTANCE_HIGH);
        channelHigh.setDescription("Recuerdo lluvias");
        getNotificationManager().createNotificationChannel(channelHigh);
    }
}
