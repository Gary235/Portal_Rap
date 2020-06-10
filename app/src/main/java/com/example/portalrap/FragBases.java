package com.example.portalrap;

import android.app.Fragment;
import android.os.Bundle;

import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class FragBases extends Fragment implements View.OnClickListener {

    ImageButton btnOrdenar, btnFiltrar,btnBuscar,btnUsar;
    EditText edtBuscar;
    ListView listabases;
    ArrayList<Integer> arraydeposiciones = new ArrayList<>();
    ArrayList<Base> arrBases = new ArrayList<>();
    adaptadorBases adaptador;
    Base unaBase = new Base();
    int contadordeitems = 0;
    Toast toast1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_bases, container, false);

        btnUsar = v.findViewById(R.id.btnusar);
        btnOrdenar = v.findViewById(R.id.ordenar);
        btnFiltrar = v.findViewById(R.id.filtrar);
        btnBuscar = v.findViewById(R.id.btnbuscar);
        edtBuscar = v.findViewById(R.id.edtBuscar);

        btnBuscar.setOnClickListener(this);
        btnFiltrar.setOnClickListener(this);
        btnOrdenar.setOnClickListener(this);
        btnUsar.setOnClickListener(this);

        adaptador = new adaptadorBases(arrBases,getActivity());


        listabases = v.findViewById(R.id.listabases);
        for(int i= 0;i<3;i++){

            unaBase._Nombre="sss";
            unaBase._Artista="aaa";
            arrBases.add(unaBase);

        }
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
        else if(idbotonapretado == R.id.btnusar)
        {


        }
    }
}