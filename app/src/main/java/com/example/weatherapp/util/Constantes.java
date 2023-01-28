package com.example.weatherapp.util;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Constantes {

    public static final String USUARIO = "usuario";
    public static final String CONTRASENA = "contrasena";
    public static final String LOGIN = "login";

    public static final String USUARIO_POR_DEFECTO = "usuario1";
    public static final String CONTRASENA_POR_DEFECTO = "123";

    public static String[] emojis = {"🌧️","🌥️","☀️","☃️","☁", "🌙"};
    private static String[] nieve = {"Nieve moderada a intervalos en las aproximaciones", "Aguanieve moderada a intervalos en las aproximaciones", "Chubascos de nieve", "Aguanieve fuerte o moderada", "Nevadas ligeras a intervalos", "Nevadas ligeras", "Nieve moderada a intervalos", "Nieve moderada", "Nevadas intensas", "Fuertes nevadas", "Granizo", "Ligeros chubascos de aguanieve", "Chubascos de aguanieve fuertes o moderados", "Ligeras precipitaciones de nieve", "Chubascos de nieve fuertes o moderados", "Ligeros chubascos acompañados de granizo", "Chubascos fuertes o moderados acompañados de granizo", "Nieve moderada con tormenta en la región", "Nieve moderada o fuertes nevadas con tormenta en la región"};
    public static Set<String> nieveSet = new HashSet<>(Arrays.asList(nieve));
    private static String[] lluvias = {"Llovizna helada a intervalos en las aproximaciones", "Llovizna a intervalos", "Llovizna", "Llovizna helada","Lluvias ligeras a intervalos","Ligeras lluvias", "Periodos de lluvia moderada", "Lluvia moderada","Periodos de fuertes lluvias", "Fuertes lluvias", "Ligeras lluvias heladas", "Lluvias heladas fuertes o moderadas", "Ligeras precipitaciones de aguanieve", "Ligeras precipitaciones", "Lluvias fuertes o moderadas","Lluvias torrenciales"};
    public static Set<String> lluviaSet = new HashSet<>(Arrays.asList(lluvias));
    private static String[] nublado = {"Parcialmente nublado", "Nublado"};
    public static Set<String> nubladoSet = new HashSet<>(Arrays.asList(nublado));

    public static Map<String, String> icons = new HashMap<String, String>();

    public static final long SERVICIO_NOTIFICACIONES_MS = 1*30*1000; // 1*60*60*1000 -> 1 hora   1*60*1000 -> 1 minuto



}
