package com.example.portalrap;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class FragEstimulo extends Fragment implements View.OnClickListener {

    ImageButton btnSiguiente,btnAnterior,btnInfo;
    Button btnsec1,btnsec2,btnsec3,btnsec4,btnsec5;
    TextView txtfrecelegida;
    public static int Frecuencia = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_frag_estimulo, container, false);

        txtfrecelegida = v.findViewById(R.id.estimuloelegido);
        btnAnterior = v.findViewById(R.id.botonanteriordeestimulo);
        btnSiguiente = v.findViewById(R.id.botonsiguientedeestimulo);
        btnInfo = v.findViewById(R.id.botoninfodeestimulo);
        btnsec1 = v.findViewById(R.id.btn2seg);
        btnsec2 = v.findViewById(R.id.btn5seg);
        btnsec3 = v.findViewById(R.id.btn10seg);
        btnsec4 = v.findViewById(R.id.btn20seg);
        btnsec5 = v.findViewById(R.id.btn30seg);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mensaje;
                mensaje = new AlertDialog.Builder(getActivity());
                mensaje.setTitle("Elegir Frecuencia");
                mensaje.setMessage("Elige una frecuencia para controlar, cada\n" +
                        "cuanto tiempo aparecen las palabras, \n" +
                        "imagenes u objetos por la pantalla.");
                mensaje.setPositiveButton("Aceptar",null);
                mensaje.create();
                mensaje.show();
            }
        });
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity main=(MainActivity) getActivity();
                main.PasaraFragmentDuracion();
            }
        });
        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity main=(MainActivity) getActivity();
                main.PasaraFragmentModo();
            }
        });
        btnsec1.setOnClickListener(this);
        btnsec2.setOnClickListener(this);
        btnsec3.setOnClickListener(this);
        btnsec4.setOnClickListener(this);
        btnsec5.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View v) {

        Button botonapretado2 = (Button)v;
        int idboton2 = botonapretado2.getId();


        if(idboton2 == R.id.btn2seg)
        {
            txtfrecelegida.setText(btnsec1.getText().toString());
            Frecuencia = 0;
        }
        if(idboton2 == R.id.btn5seg)
        {
            txtfrecelegida.setText(btnsec2.getText().toString());
            Frecuencia = 1;
        }
        if(idboton2 == R.id.btn10seg)
        {
            txtfrecelegida.setText(btnsec3.getText().toString());
            Frecuencia = 2;
        }
        if(idboton2 == R.id.btn20seg)
        {
            txtfrecelegida.setText(btnsec4.getText().toString());
            Frecuencia = 3;
        }
        if(idboton2 == R.id.btn30seg)
        {
            txtfrecelegida.setText(btnsec5.getText().toString());
            Frecuencia = 4;
        }
    }



}