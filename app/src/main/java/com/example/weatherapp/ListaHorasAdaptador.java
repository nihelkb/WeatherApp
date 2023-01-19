package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListaHorasAdaptador extends RecyclerView.Adapter<ListaHorasAdaptador.ViewHolder> {
    private Context contexto;
    private List<ListaHorasModelo> listaModelo;

    public ListaHorasAdaptador(Context contexto, List<ListaHorasModelo> listaModelo) {
        this.contexto = contexto;
        this.listaModelo = listaModelo;
    }

    @NonNull
    @Override
    public ListaHorasAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(contexto).inflate(R.layout.lista_horas,parent, false);
        return new ViewHolder(vista);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListaHorasAdaptador.ViewHolder holder, int position) {
        ListaHorasModelo modelo = listaModelo.get(position);
        holder.temperatura.setText(modelo.getTemperatura()+"°C");
        SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-DD hh:mm");
        SimpleDateFormat fechaModificada = new SimpleDateFormat("hh:mm aa");
        try {
            Date t = fecha.parse(modelo.getHora());
            holder.hora.setText(fechaModificada.format(t));

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        holder.condicion.setText(modelo.getCondicion()+"Km/h");
       Picasso.get().load("https:".concat(modelo.getIcono())).into(holder.icono);
        //https://cdn-icons-png.flaticon.com/512/1146/1146856.png
        //Picasso.get().load("https://cdn-icons-png.flaticon.com/512/1146/1146856.png").into(holder.icono);

    }

    @Override
    public int getItemCount() {
        return listaModelo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView hora;
        private TextView temperatura;
        private TextView condicion;
        private ImageView icono;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hora= itemView.findViewById(R.id.Hora);
            temperatura = itemView.findViewById(R.id.temperatura);
            condicion = itemView.findViewById(R.id.condicion);
            //icono = itemView.findViewById(R.id.fotoTiempo); NO ES
            icono = itemView.findViewById(R.id.fotolista);
        }
    }
}
