package com.example.portalrap.FragmentsEntrenamiento;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.portalrap.MainActivity;
import com.example.portalrap.R;


public class FragTodoListo extends Fragment {

    Button btnAvanzar,btnANterior;
    TextView lbl,lbl2,lbl3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String desdedur = getArguments().getString("desdedur");

        if(desdedur == "si")
        {        View v = inflater.inflate(R.layout.fragment_frag_todo_listo, container, false);

            btnANterior = v.findViewById(R.id.btnAanteriordetodolisto);
            btnAvanzar = v.findViewById(R.id.btnAvanzardetodolisto);
            lbl = v.findViewById(R.id.textolabel1deTodolisto);
            lbl2 = v.findViewById(R.id.textolabel2deTodolisto);
            lbl3 = v.findViewById(R.id.textolabel3deTodolisto);

            btnAvanzar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity main=(MainActivity) getActivity();
                    main.PasaraFragEntrenar();
                }
            });
            btnANterior.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity main=(MainActivity) getActivity();
                    main.PasaraFragBases("si");
                }
            });


            if(MainActivity.PosModo == 0)
            { lbl.setText("Aleatorio"); }
            else if(MainActivity.PosModo == 1)
            { lbl.setText("Objetos");}
            else
            { lbl.setText("Palabras");}

            if(MainActivity.Frecuencia == 0)
            { lbl2.setText("2s"); }
            else if(MainActivity.Frecuencia == 1)
            { lbl2.setText("5s");}
            else if(MainActivity.Frecuencia == 2)
            { lbl2.setText("10s");}
            else if(MainActivity.Frecuencia == 3)
            { lbl2.setText("20s");}
            else if(MainActivity.Frecuencia == 4)
            { lbl2.setText("30s");}
            lbl3.setText(MainActivity.Minutos + "m / " + MainActivity.Segundos + "s");

            return v;
        }
        else {
            MainActivity main=(MainActivity) getActivity();
            main.PasaraFragEntrenar();
            return null;
        }

    }
}