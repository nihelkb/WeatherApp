package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.contexto.LoginContext;
import com.example.weatherapp.contexto.UltimaLocalizacion;
import com.example.weatherapp.modelos.Coordenadas;
import com.example.weatherapp.modelos.DatosTiempoModelo;
import com.example.weatherapp.modelos.ListaHorasAdaptador;
import com.example.weatherapp.modelos.LocalidadArrayAdaptador;
import com.example.weatherapp.servicios.DescargarDatosEspecificosTarea;
import com.example.weatherapp.servicios.DescargarDatosTiempoTarea;
import com.example.weatherapp.servicios.jobs.Actualizador;
import com.example.weatherapp.util.Constantes;
import com.example.weatherapp.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListaAuxiliarInterfaz{
    public TextView lugar;
    public TextView temperatura;
    public TextView condicion;
    public ImageView icono;
    public ImageView myloc;
    private ImageView fondo;
    public AutoCompleteTextView busqueda;
    private TextView bienvenida;
    public RecyclerView listaHoras;
    public List<DatosTiempoModelo> lista;
    public ListaHorasAdaptador adaptador;
    private LocationManager localizacionM;

    private String ubicacionAct;
    private int codigoPermisos = 1;
    private boolean permisosDenegados = false;

    public ScrollView home;
    public ProgressBar loading;

    LocationManager mLocationManager;

    public static MainActivity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);
        Utils.crearParIconoCond();
        act = this;
        LoginContext lc = (LoginContext) getApplicationContext();
        if (lc.getLogin() == null) {
            lc.setLogin(Utils.getUserFromPreferences(act));
            if (!lc.credencialesGuardadas()) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                return;
            }
        }
        Actualizador.programar(this);
        home = findViewById(R.id.home);
        loading = findViewById(R.id.progressBar);
        lugar = findViewById(R.id.city);
        temperatura = findViewById(R.id.temp);
        condicion = findViewById(R.id.condicion);
        icono = findViewById(R.id.fotoTiempo);
        myloc = findViewById(R.id.mylocalization);
        fondo = findViewById(R.id.fondo);
        bienvenida = findViewById(R.id.bienvenida);
        busqueda = findViewById(R.id.autoCompletar);
        listaHoras = findViewById(R.id.recyclerview);
        lista = new ArrayList<>();
        adaptador = new ListaHorasAdaptador(this, lista,this);
        listaHoras.setAdapter(adaptador);
        listaHoras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(view);
            }
        });

        localizacionM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if(!localizacionM.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                System.out.println("GPS NO ACTIVADO");
                Toast.makeText(this, "Por favor, activa la ubicación", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        ThreadAutocompletar auto = new ThreadAutocompletar(act);
        Thread th = new Thread(auto);
        th.start();
        System.out.println("LANZO THREAD AUTOCOMPLETAR");

        myloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location localizacion = getLastKnownLocation();
                if(localizacion == null){
                    Toast.makeText(MainActivity.this, "Por favor, activa la geolocalización", Toast.LENGTH_SHORT).show();
                    return;
                }
                String longitud = localizacion.getLongitude() + "";
                String latitud = localizacion.getLatitude() + "";
                Coordenadas coordenadas = new Coordenadas(longitud, latitud);
                DescargarDatosTiempoTarea datos = new DescargarDatosTiempoTarea(MainActivity.this, coordenadas);
                datos.execute();
                busqueda.setText("");
            }
        });

        busqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")){
                    return;
                }
                auto.actualizarTexto(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        busqueda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(),0);
                LocalidadArrayAdaptador<String> adaptador = (LocalidadArrayAdaptador<String>)busqueda.getAdapter();
                List<Coordenadas> listaCoordenadas = adaptador.getCoordenadasLista();
                UltimaLocalizacion.ultimasCoordenadasBuscadas = listaCoordenadas.get(i);
                if (listaCoordenadas != null && i < lista.size()){
                    DescargarDatosTiempoTarea datos = new DescargarDatosTiempoTarea(act,listaCoordenadas.get(i));
                    datos.execute();
                }
            }
        });

        busqueda.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        busqueda.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
            @Override
            public void onDismiss() {
                //InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                //manager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(),0);
            }
        });

        String ciudadInput = busqueda.getText().toString();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
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
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, codigoPermisos);
            }

        }

        // IF CLAVE
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Location localizacion = getLastKnownLocation();
        String longitud = localizacion.getLongitude() + "";
        String latitud = localizacion.getLatitude() + "";
        Coordenadas coordenadas = new Coordenadas(longitud, latitud);
        DescargarDatosTiempoTarea datos = new DescargarDatosTiempoTarea(this, coordenadas);
        datos.execute();
    }

    public Location getLastKnownLocation() {
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        if (bestLocation != null){
            UltimaLocalizacion.ultimaLocalizacion = bestLocation;
        }
        System.out.println(UltimaLocalizacion.ultimaLocalizacion);
        return bestLocation;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == codigoPermisos) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permisos concedidos", Toast.LENGTH_SHORT).show();
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                Location localizacion = getLastKnownLocation();
                String longitud = localizacion.getLongitude() + "";
                String latitud = localizacion.getLatitude() + "";

                Coordenadas coordenadas = new Coordenadas(longitud, latitud);
                DescargarDatosTiempoTarea datos = new DescargarDatosTiempoTarea(this, coordenadas);
                datos.execute();
            }else{
                Toast.makeText(this, "Por favor, conceda los permisos", Toast.LENGTH_SHORT).show();
                Log.d("casa","pedrito");
                permisosDenegados = true;
                finish();
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.logout_button:
                Utils.deleteUserInPreferences(act);
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                break;
            case R.id.share_button:
                Intent intento = new Intent(Intent.ACTION_SEND);
                intento.setType("text/plain");

                StringBuilder previsionDiaria = new StringBuilder("El tiempo para hoy en ");
                previsionDiaria.append(lugar.getText().toString());
                previsionDiaria.append(" es:\n");

                previsionDiaria.append("Condición: ");
                String condicionHoy = condicion.getText().toString();
                String emojiCond;

                if(Constantes.nubladoSet.contains(condicionHoy)){
                    emojiCond = Constantes.emojis[1];
                }else if (Constantes.lluviaSet.contains(condicionHoy)) {
                    emojiCond = Constantes.emojis[0];
                }else if (Constantes.nieveSet.contains(condicionHoy)) {
                    emojiCond = Constantes.emojis[3];
                }else if (condicionHoy.equals("Soleado")) {
                    emojiCond = Constantes.emojis[2];
                }else if (condicionHoy.equals("Cielo cubierto")) {
                    emojiCond = Constantes.emojis[4];
                }else if (condicionHoy.equals("Despejado")) {
                    emojiCond = Constantes.emojis[5];
                }else{
                    emojiCond = "";
                }
                previsionDiaria.append(condicionHoy + " " + emojiCond + "\n"); // condicion
                previsionDiaria.append("Temperatura: ");
                String temperaturaHoy = temperatura.getText().toString();
                String[] tempArr = temperaturaHoy.split("º");
                String emojiTemp;
                Double t = Double.parseDouble(tempArr[0]);
                if(t < 15){
                    emojiTemp = "🥶";
                }else if(t > 25){
                    emojiTemp = "🥵";
                }else{
                    emojiTemp = "😃";
                }
                previsionDiaria.append(temperatura.getText().toString() + " " + emojiTemp + "\n"); // temp
                //previsionDiaria.append("Probabilidad de lluvia: ");
                //previsionDiaria.append("%\n"); // prob lluvia

                intento.putExtra(Intent.EXTRA_TEXT,previsionDiaria.toString());
                startActivity(Intent.createChooser(intento, "Compartir"));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int posicion) {
        Coordenadas coords = UltimaLocalizacion.ultimasCoordenadasBuscadas;
        if (coords == null){
            Location location = UltimaLocalizacion.ultimaLocalizacion;
            coords = new Coordenadas(location.getLongitude()+"",location.getLatitude()+"");
        }
        Intent intento = new Intent(MainActivity.this,HoraEspecificaActivity.class);
        intento.putExtra("lon",coords.getLongitud()+"");
        intento.putExtra("lat",coords.getLatitud()+"");
        intento.putExtra("pos",posicion);
        startActivity(intento);
    }
}