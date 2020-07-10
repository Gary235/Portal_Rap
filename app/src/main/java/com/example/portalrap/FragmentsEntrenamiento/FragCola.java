package com.example.portalrap.FragmentsEntrenamiento;

import android.app.Fragment;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.cardview.widget.CardView;

import com.example.portalrap.Adaptadores.adaptadorBases;
import com.example.portalrap.Adaptadores.adaptadorDeColaSiguiente;
import com.example.portalrap.Clases.Base;
import com.example.portalrap.FragBases;
import com.example.portalrap.MainActivity;
import com.example.portalrap.R;

import java.util.ArrayList;


public class FragCola extends Fragment{

    ImageButton btnVolver;
    ListView listaSiguiente;
    ArrayList<Base> arrBasesSiguiente = new ArrayList<>();
    adaptadorDeColaSiguiente adaptador;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_cola, container, false);

        btnVolver = v.findViewById(R.id.btnVOlverdecola);
        listaSiguiente = v.findViewById(R.id.listaSiguiente);

        adaptador = new adaptadorDeColaSiguiente(arrBasesSiguiente,getActivity());
        CargarArray();
        listaSiguiente.setAdapter(adaptador);


        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragEntrenar.holderparacola.setVisibility(View.GONE);
            }
        });

        return v;
    }


    public void CargarArray()
    {
        for(int i =1; i < FragBases.UserSelection.size();i++)
        {
            arrBasesSiguiente.add(FragBases.UserSelection.get(i));
        }

    }

}