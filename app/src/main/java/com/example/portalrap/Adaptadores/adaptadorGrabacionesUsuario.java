package com.example.portalrap.Adaptadores;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.portalrap.Clases.Grabacion;
import com.example.portalrap.R;

import java.util.ArrayList;

public class adaptadorGrabacionesUsuario extends BaseAdapter {
    private ArrayList<Grabacion> arrGrabacion;
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
    public int getCount() {

        Log.d("Tamnio","Size: " + arrGrabacion.size());


        return arrGrabacion.size(); }

    @Override
    public Grabacion getItem(int position) {

        Grabacion grab;
        grab = arrGrabacion.get(position);
        return grab;
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

        btnPlay.setFocusable(false);
        btnFav.setFocusable(false);
        Nombre.setFocusable(false);

        miGrabacion = getItem(position);
        Nombre.setText(miGrabacion.get_Nombre());


        return vista;
    }
}
