package com.example.portalrap;

import android.app.Fragment;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;


public class FragBases extends Fragment implements View.OnClickListener {

    ImageButton btnOrdenar, btnFiltrar,btnBuscar;
    EditText edtBuscar;
    ListView listabases;
    ArrayList<Base> arrBases = new ArrayList<>();
    adaptadorBases adaptador;
    Base unaBase = new Base();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_frag_bases, container, false);

        btnOrdenar = v.findViewById(R.id.ordenar);
        btnFiltrar = v.findViewById(R.id.filtrar);
        btnBuscar = v.findViewById(R.id.btnbuscar);
        edtBuscar = v.findViewById(R.id.edtBuscar);
        listabases = v.findViewById(R.id.listabases);

        btnBuscar.setOnClickListener(this);
        btnFiltrar.setOnClickListener(this);
        btnOrdenar.setOnClickListener(this);

        adaptador = new adaptadorBases(arrBases,getActivity());

        listabases.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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


        unaBase._Artista = "Artista";
        unaBase._Nombre = "Nombre";
        unaBase._btnFav = null;
        unaBase._btnPlay = null;

        arrBases.add(unaBase);

        listabases.setAdapter(adaptador);
        return v;
    }

    @Override
    public void onClick(View v) {
        ImageButton botonapretado;
        botonapretado = (ImageButton)v;
        int idbotonapretado = botonapretado.getId();
        MainActivity main=(MainActivity) getActivity();

        if(idbotonapretado == R.id.btnbuscar)
        {


        }
        else if(idbotonapretado == R.id.ordenar)
        {


        }
        else if(idbotonapretado == R.id.filtrar)
        {


        }
    }
}