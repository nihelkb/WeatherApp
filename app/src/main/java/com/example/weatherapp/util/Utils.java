package com.example.weatherapp.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.weatherapp.modelos.Coordenadas;
import com.example.weatherapp.modelos.Login;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Utils {

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
    public static Login getUserFromPreferences(Activity act){
        SharedPreferences pref = act.getApplicationContext().getSharedPreferences(Constantes.LOGIN, Context.MODE_PRIVATE);
        if (!pref.contains(Constantes.USUARIO) || !pref.contains(Constantes.CONTRASENA)) return null;
        String usuario = pref.getString(Constantes.USUARIO,"");
        String contrasena = pref.getString(Constantes.CONTRASENA,"");
        return new Login(usuario, contrasena);
    }

    public static void saveUserInPreferences(Activity act, String usuario, String contrasena){
        SharedPreferences pref = act.getApplicationContext().getSharedPreferences(Constantes.LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constantes.USUARIO, usuario);
        editor.putString(Constantes.CONTRASENA, contrasena);
        editor.commit();
    }

    public static void deleteUserInPreferences(Activity act){
        SharedPreferences pref = act.getApplicationContext().getSharedPreferences(Constantes.LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(Constantes.USUARIO);
        editor.remove(Constantes.CONTRASENA);
        editor.commit();
    }

    public static void crearParIconoCond(){
        Constantes.icons.put("1000", "//cdn-icons-png.flaticon.com/512/8841/8841321.png"); // soleado
        Constantes.icons.put("1001", "//cdn-icons-png.flaticon.com/512/1146/1146900.png"); // despejado

        Constantes.icons.put("1003", "//cdn-icons-png.flaticon.com/512/1146/1146869.png"); // Parcialmente nublado

        Constantes.icons.put("1006", "//cdn-icons-png.flaticon.com/512/616/616516.png"); // despejado

        Constantes.icons.put("1009", "//cdn-icons-png.flaticon.com/512/1146/1146881.png"); // soleado

        Constantes.icons.put("1030", "//cdn-icons-png.flaticon.com/512/1146/1146870.png"); // despejado
        Constantes.icons.put("1135", "//cdn-icons-png.flaticon.com/512/1146/1146870.png"); // Parcialmente nublado
        Constantes.icons.put("1147", "//cdn-icons-png.flaticon.com/512/1146/1146870.png"); // despejado

        Constantes.icons.put("1063", "//cdn-icons-png.flaticon.com/512/1146/1146915.png"); // Lluvia con sol
        Constantes.icons.put("1180", "//cdn-icons-png.flaticon.com/512/1146/1146915.png"); // Lluvia con sol
        Constantes.icons.put("1186", "//cdn-icons-png.flaticon.com/512/1146/1146915.png"); // Lluvia con sol
        Constantes.icons.put("1240", "//cdn-icons-png.flaticon.com/512/1146/1146915.png"); // Lluvia con sol
        Constantes.icons.put("1183", "//cdn-icons-png.flaticon.com/512/1146/1146915.png"); // Lluvia con sol

        Constantes.icons.put("1150", "//cdn-icons-png.flaticon.com/512/1146/1146858.png"); // Lluvia sin sol
        Constantes.icons.put("1153", "//cdn-icons-png.flaticon.com/512/1146/1146858.png"); // Lluvia sin sol
        Constantes.icons.put("1189", "//cdn-icons-png.flaticon.com/512/1146/1146858.png"); // Lluvia sin sol
        Constantes.icons.put("1192", "//cdn-icons-png.flaticon.com/512/1146/1146858.png"); // Lluvia sin sol
        Constantes.icons.put("1195", "//cdn-icons-png.flaticon.com/512/1146/1146858.png"); // Lluvia sin sol
        Constantes.icons.put("1243", "//cdn-icons-png.flaticon.com/512/1146/1146858.png"); // Lluvia sin sol
        Constantes.icons.put("1246", "//cdn-icons-png.flaticon.com/512/1146/1146858.png"); // Lluvia sin sol

        Constantes.icons.put("1087", "//cdn-icons-png.flaticon.com/512/1146/1146913.png"); // Tormenta posible

        Constantes.icons.put("1279", "//cdn-icons-png.flaticon.com/512/1146/1146861.png"); // Tormenta
        Constantes.icons.put("1282", "//cdn-icons-png.flaticon.com/512/1146/1146861.png"); // Tormenta

        Constantes.icons.put("1273", "//cdn-icons-png.flaticon.com/512/1146/1146860.png"); // Tormenta con lluvia
        Constantes.icons.put("1276", "//cdn-icons-png.flaticon.com/512/1146/1146860.png"); // Tormenta con lluvia

        Constantes.icons.put("1066", "//cdn-icons-png.flaticon.com/512/1146/1146878.png"); // Nieve
        Constantes.icons.put("1210", "//cdn-icons-png.flaticon.com/512/1146/1146878.png"); // Nieve
        Constantes.icons.put("1216", "//cdn-icons-png.flaticon.com/512/1146/1146878.png"); // Nieve
        Constantes.icons.put("1255", "//cdn-icons-png.flaticon.com/512/1146/1146878.png"); // Nieve
        Constantes.icons.put("1114", "//cdn-icons-png.flaticon.com/512/1146/1146878.png"); // Nieve
        Constantes.icons.put("1117", "//cdn-icons-png.flaticon.com/512/1146/1146878.png"); // Nieve
        Constantes.icons.put("1213", "//cdn-icons-png.flaticon.com/512/1146/1146878.png"); // Nieve
        Constantes.icons.put("1219", "//cdn-icons-png.flaticon.com/512/1146/1146878.png"); // Nieve
        Constantes.icons.put("1222", "//cdn-icons-png.flaticon.com/512/1146/1146878.png"); // Nieve
        Constantes.icons.put("1225", "//cdn-icons-png.flaticon.com/512/1146/1146878.png"); // Nieve
        Constantes.icons.put("1237", "//cdn-icons-png.flaticon.com/512/1146/1146878.png"); // Nieve
        Constantes.icons.put("1258", "//cdn-icons-png.flaticon.com/512/1146/1146878.png"); // Nieve
        Constantes.icons.put("1261", "//cdn-icons-png.flaticon.com/512/1146/1146878.png"); // Nieve
        Constantes.icons.put("1264", "//cdn-icons-png.flaticon.com/512/1146/1146878.png"); // Nieve

        Constantes.icons.put("1204", "//cdn-icons-png.flaticon.com/512/1146/1146862.png"); // Nieve + lluvia
        Constantes.icons.put("1207", "//cdn-icons-png.flaticon.com/512/1146/1146862.png"); // Nieve + lluvia
        Constantes.icons.put("1249", "//cdn-icons-png.flaticon.com/512/1146/1146862.png"); // Nieve + lluvia
        Constantes.icons.put("1252", "//cdn-icons-png.flaticon.com/512/1146/1146862.png"); // Nieve + lluvia
        Constantes.icons.put("1069", "//cdn-icons-png.flaticon.com/512/1146/1146862.png"); // Nieve + lluvia
        Constantes.icons.put("1072", "//cdn-icons-png.flaticon.com/512/1146/1146862.png"); // Nieve + lluvia
        Constantes.icons.put("1168", "//cdn-icons-png.flaticon.com/512/1146/1146862.png"); // Nieve + lluvia
        Constantes.icons.put("1171", "//cdn-icons-png.flaticon.com/512/1146/1146862.png"); // Nieve + lluvia
        Constantes.icons.put("1198", "//cdn-icons-png.flaticon.com/512/1146/1146862.png"); // Nieve + lluvia
        Constantes.icons.put("1201", "//cdn-icons-png.flaticon.com/512/1146/1146862.png"); // Nieve + lluvia
    }


    public static void setImageToImageView(ImageView view, String url){
        Picasso.get().load("https:".concat(url)).into(view);
    }
}
