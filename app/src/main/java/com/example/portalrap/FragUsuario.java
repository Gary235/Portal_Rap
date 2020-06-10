package com.example.portalrap;

import android.app.Fragment;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class FragUsuario extends Fragment implements View.OnClickListener{

    ImageButton btneditar,btnfav;
    ListView lista;
    ArrayList<Grabacion> arrGrabaciones = new ArrayList<>();
    adaptadorGrabacionesUsuario adaptador;
    Grabacion miGrabacion = new Grabacion();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_frag_usuario, container, false);

        btneditar =  v.findViewById(R.id.btneditar);
        btnfav = v.findViewById(R.id.btnfav);
        lista = v.findViewById(R.id.lista);

        btneditar.setOnClickListener(this);
        btnfav.setOnClickListener(this);

        adaptador = new adaptadorGrabacionesUsuario(arrGrabaciones,getActivity());


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Log.d("Conexion","Pos:" + position);

                /*String titulo;
                titulo = arraydepeliculas.get(position)._titulo;

                MainActivity main=(MainActivity) getActivity();
                main.recibirTexto(titulo);

                main.pasarADetalleFragment();*/

            }
        });

        miGrabacion._Nombre = "Nombre";
        miGrabacion._btnFav= null;
        miGrabacion._btnPlay = null;
        arrGrabaciones.add(miGrabacion);


        lista.setAdapter(adaptador);

        return v;
    }

    @Override
    public void onClick(View v) {

        ImageButton botonapretado;
        botonapretado = (ImageButton)v;
        int idbotonapretado = botonapretado.getId();
        MainActivity main=(MainActivity) getActivity();

        if(idbotonapretado == R.id.btneditar)
        {


        }
        else if(idbotonapretado == R.id.btnfav)
        {


        }


    }
}