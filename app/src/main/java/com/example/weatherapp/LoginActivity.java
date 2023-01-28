package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherapp.contexto.LoginContext;
import com.example.weatherapp.modelos.Login;
import com.example.weatherapp.util.Utils;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Sig-in button
        Button btn_sigIn = findViewById(R.id.login);
        btn_sigIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Credenciales
                String nombreUsuario = ((EditText) findViewById(R.id.username)).getText().toString();
                String contrasena = ((EditText) findViewById(R.id.password)).getText().toString();
                //Recuerdame
                boolean remindMe = ((CheckBox) findViewById(R.id.check_remindMe)).isChecked();
                //Login
                Login credenciales = new Login(nombreUsuario, contrasena);

                if(credenciales.login()){
                    ((LoginContext) getApplicationContext()).setLogin(credenciales);
                    //if the check box is marked, save in preferences the username and pass
                    if(remindMe){
                        Utils.saveUserInPreferences(LoginActivity.this,nombreUsuario, contrasena);
                    }
                    //creates the intent and move to the main MainActivity
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(LoginActivity.this, "Credenciales incorrectas. Intentalo de nuevo.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
