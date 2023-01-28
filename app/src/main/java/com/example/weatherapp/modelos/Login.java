package com.example.weatherapp.modelos;

import com.example.weatherapp.util.Constantes;

public class Login {

    private String nombreUsuario;
    private String contrasena;

    public Login(String nombreUsuario, String contrasena) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }

    public boolean login(){
        return Constantes.USUARIO_POR_DEFECTO.equals(nombreUsuario) &&
                Constantes.CONTRASENA_POR_DEFECTO.equals(contrasena);
    }
}
