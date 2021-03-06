package com.example.portalrap.FragmentsEntrenamiento;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.viewpager.widget.ViewPager;

import com.example.portalrap.Adaptadores.Adapter1;
import com.example.portalrap.MainActivity;
import com.example.portalrap.Clases.Model;
import com.example.portalrap.R;

import java.util.ArrayList;
import java.util.List;


public class FragModo extends Fragment implements View.OnClickListener {

    ViewPager viewPager;
    Adapter1 adapter1;
    List<Model> lista =  new ArrayList<>();
    ImageButton btnSiguiente;
    Button btnInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_modo,container,false);

        MainActivity.PosModo = -1;

        Setear(v);
        ListenersAdicionales();
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Verificacion", "si pasa");
                AlertDialog.Builder mensaje;
                mensaje = new AlertDialog.Builder(getActivity());
                mensaje.setTitle("Elegir Modo");
                mensaje.setMessage("· Elige un modo para poder entrenar luego\n\n· Desliza hacia la derecha o hacia la izquierda para cambiar el modo");
                mensaje.setPositiveButton("Aceptar",null);
                mensaje.create();
                mensaje.show();

            }
        });

        return v;
    }

    public void Setear(View v)
    {
        viewPager = v.findViewById(R.id.viewpager2);
        btnInfo = v.findViewById(R.id.botoninfodemodo);
        btnSiguiente = v.findViewById(R.id.botonsiguientedemodo);
        btnSiguiente.setOnClickListener(this);

        lista.add(new Model(R.drawable.ic_aleatorio,"Aleatorio"));
        lista.add(new Model(R.drawable.ic_objetos,"Objetos"));
        lista.add(new Model(R.drawable.ic_palabras,"Palabras"));

        adapter1 = new Adapter1(lista,getActivity());
        viewPager.setAdapter(adapter1);
        viewPager.setPadding(130,0,130,0);



    }

    public void ListenersAdicionales() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                MainActivity.PosModo = position;
            }
            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });



    }


    @Override
    public void onClick(View v) {

        ImageButton botonapretado = (ImageButton)v;
        int idboton = botonapretado.getId();

        if(idboton == R.id.botonsiguientedemodo) {
            MainActivity main=(MainActivity) getActivity();
            main.PasaraFragmentEstimulo();
        }

    }
}