package com.example.weatherapp.modelos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.ListaAuxiliarInterfaz;
import com.example.weatherapp.R;
import com.example.weatherapp.util.Utils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ListaHorasAdaptador extends RecyclerView.Adapter<ListaHorasAdaptador.ViewHolder> {
    private Context contexto;

    private List<DatosTiempoModelo> listaModelo;
    private final ListaAuxiliarInterfaz interfaz;

    public ListaHorasAdaptador(Context contexto, List<DatosTiempoModelo> listaModelo, ListaAuxiliarInterfaz interfaz) {
        this.contexto = contexto;
        this.listaModelo = listaModelo;
        this.interfaz=interfaz;
    }

    public void setListaModelo(List<DatosTiempoModelo> listaModelo) {
        this.listaModelo = listaModelo;
    }

    @NonNull
    @Override
    public ListaHorasAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(contexto).inflate(R.layout.lista_horas,parent, false);
        return new ViewHolder(vista,interfaz);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListaHorasAdaptador.ViewHolder holder, int position) {
        DatosTiempoModelo modelo = listaModelo.get(position);
        holder.temperatura.setText(modelo.getTemperatura()+"°C");
        /*SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-DD hh:mm");
        SimpleDateFormat fechaModificada = new SimpleDateFormat("hh:mm aa");
        try {
            Date t = fecha.parse(modelo.getHora());
            holder.hora.setText(fechaModificada.format(t));

        }
        catch (ParseException e) {
            e.printStackTrace();
        }*/
        String[] fecha = modelo.getHora().split(" ");
        holder.hora.setText(fecha[1]);
        //holder.condicion.setText(modelo.getCondicion()+"km/h");
        holder.condicion.setText(modelo.getCondicion()+"% 🌧");
        //Picasso.get().load("https:".concat(modelo.getImagen())).into(holder.icono);
        Utils.setImageToImageView(holder.icono,modelo.getImagen());
        //https://cdn-icons-png.flaticon.com/512/1146/1146856.png
        //Picasso.get().load("https://cdn-icons-png.flaticon.com/512/1146/1146856.png").into(holder.icono);

    }

    @Override
    public int getItemCount() {
        return listaModelo.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView hora;
        private TextView temperatura;
        private TextView condicion;
        private ImageView icono;
        public ViewHolder(@NonNull View itemView, ListaAuxiliarInterfaz interfaz) {
            super(itemView);
            hora= itemView.findViewById(R.id.Hora);
            temperatura = itemView.findViewById(R.id.temperatura);
            condicion = itemView.findViewById(R.id.condicion);
            //icono = itemView.findViewById(R.id.fotoTiempo); NO ES
            icono = itemView.findViewById(R.id.fotolista);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(interfaz!=null){
                        int horaAc = 0;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            Instant instante = Instant.now();
                            LocalDateTime ldt = LocalDateTime.ofInstant(instante, ZoneId.systemDefault());
                            horaAc = ldt.getHour();
                        }
                        int posicion = getAdapterPosition();
                        if(posicion!=RecyclerView.NO_POSITION){
                            interfaz.onItemClick(horaAc+posicion);
                        }

                    }
                }
            });
        }
    }


}
