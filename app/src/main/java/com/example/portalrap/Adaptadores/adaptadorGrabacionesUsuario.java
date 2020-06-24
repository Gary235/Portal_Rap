package com.example.portalrap.Adaptadores;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.portalrap.Clases.Grabacion;
import com.example.portalrap.R;

import java.util.ArrayList;

public class adaptadorGrabacionesUsuario extends BaseAdapter {
    private ArrayList<Grabacion> arrGrabacion;
    private Context miContexto;
    Grabacion miGrabacion = new Grabacion();
     ImageButton btnFav,btnPlay;
     TextView Nombre;
    adaptadorGrabacionesUsuario adaptadorGrabacionesUsuario = null;

    public adaptadorGrabacionesUsuario(ArrayList<Grabacion> arrayGrabacion, Context contexto) {
        arrGrabacion = arrayGrabacion;
        miContexto = contexto;
        this.adaptadorGrabacionesUsuario = this;
    }

    @Override
    public int getCount() { return arrGrabacion.size(); }

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View vista;

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
        Nombre.setText(miGrabacion.getNombre());
        btnFav.setTag(position);

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(miContexto,"presionó elegir",Toast.LENGTH_LONG);
                Integer pos = (Integer)  btnFav.getTag();
                Log.d("Favoritos","" + arrGrabacion.get(pos).getFavorito());


                if(arrGrabacion.get(pos).getFavorito()!= null)
                {
                    if(arrGrabacion.get(pos).getFavorito() ){
                        arrGrabacion.get(pos).setFavorito(false);
                        btnFav.setImageResource(R.drawable.ic_icono_corazon);
                    }else{
                        arrGrabacion.get(pos).setFavorito(true);
                        btnFav.setImageResource(R.drawable.ic_icono_nofav);

                    }
                }

            }
        });


        return vista;
    }


}
