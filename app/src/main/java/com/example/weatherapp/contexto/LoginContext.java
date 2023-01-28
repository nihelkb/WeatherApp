package com.example.weatherapp.contexto;

import android.app.Application;

import com.example.weatherapp.modelos.Login;

public class LoginContext extends Application {
    private Login credenciales;

    public Login getLogin(){ return credenciales; }

    public void setLogin(Login credenciales) {this.credenciales = credenciales; }

    public boolean credencialesGuardadas(){
        return credenciales != null;
    }
}
