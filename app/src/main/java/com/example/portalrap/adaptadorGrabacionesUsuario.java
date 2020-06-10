package com.example.portalrap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class adaptadorGrabacionesUsuario extends BaseAdapter {
    private ArrayList<Grabacion> arrGrabacion = new ArrayList<>();
    private Context miContexto;
    Grabacion miGrabacion = new Grabacion();
    ImageButton btnFav,btnPlay;
    TextView Nombre;
   // Boolean fav;

    public adaptadorGrabacionesUsuario(ArrayList<Grabacion> arrayGrabacion, Context contexto) {
        arrGrabacion = arrayGrabacion;
        miContexto = contexto;
    }

    @Override
    public int getCount() { return arrGrabacion.size(); }

    @Override
    public Grabacion getItem(int position) {
        miGrabacion = arrGrabacion.get(position);
        return miGrabacion;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista;

        LayoutInflater inflador;
        inflador = (LayoutInflater) miContexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        vista = inflador.inflate(R.layout.lista_grabaciones_usuario, parent, false);


        btnFav = vista.findViewById(R.id.btnFavlista);
        btnPlay = vista.findViewById(R.id.btnPlaylista);
        Nombre = vista.findViewById(R.id.textolista);


        miGrabacion= getItem(position);
        Nombre.setText(miGrabacion._Nombre);


        return vista;
    }
}
