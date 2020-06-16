package com.example.portalrap.FragmentsUsuario;

import android.app.Fragment;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.portalrap.Adaptadores.adaptadorGrabacionesUsuario;
import com.example.portalrap.Clases.Grabacion;
import com.example.portalrap.MainActivity;
import com.example.portalrap.R;

import java.util.ArrayList;

public class FragUsuario extends Fragment implements View.OnClickListener{

    ImageButton btneditar,btnfav;
    ListView lista;
    ArrayList<Grabacion> arrGrabaciones = new ArrayList<>();
    adaptadorGrabacionesUsuario adaptador;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_frag_usuario, container, false);

        btneditar =  v.findViewById(R.id.btneditar);
        btnfav = v.findViewById(R.id.btnfav);

        btneditar.setOnClickListener(this);
        btnfav.setOnClickListener(this);

        adaptador = new adaptadorGrabacionesUsuario(arrGrabaciones,getActivity());

        lista = v.findViewById(R.id.lista);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Log.d("Funciona","siiiiiiiiii");

                /*String titulo;
                titulo = arraydepeliculas.get(position)._titulo;

                MainActivity main=(MainActivity) getActivity();
                main.recibirTexto(titulo);

                main.pasarADetalleFragment();*/

            }
        });

        for(int i = 0; i<4;i++){
            Grabacion miGrabacion = new Grabacion();

            miGrabacion.set_Nombre("Nombre " + i);
            arrGrabaciones.add(miGrabacion);

        }



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
            main.PasaraFragEditarPerfil();
        }
        else if(idbotonapretado == R.id.btnfav)
        {
            main.PasaraFragFavoritos();
        }


    }
}