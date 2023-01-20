package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListaDiasAdaptador  extends RecyclerView.Adapter<ListaHorasAdaptador.ViewHolder> {

    private Context contexto;
    private ArrayList<lista_diasModelo> listaModelo;

    public ListaDiasAdaptador(Context contexto, ArrayList<lista_diasModelo> listaModelo) {
        //super(contexto,R.layout.activity_lista_dias, listaModelo);
    }

/*

    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup) {
        lista_diasModelo modelo = getItem(posicion);

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.activity_lista_dias,viewGroup, false);

        }
        ImageView imagen = view.findViewById(R.id.fotoDias);
        TextView dia = view.findViewById(R.id.dias_listaDias);
        TextView max = view.findViewById(R.id.max_listaDias;
        TextView min = view.findViewById(R.id.min_listaDias);

        Picasso.get().load("https:".concat(modelo.getIcono())).into();

        return super.getView(posicion,view,viewGroup);
    }
*/
    @NonNull
    @Override
    public ListaHorasAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ListaHorasAdaptador.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
