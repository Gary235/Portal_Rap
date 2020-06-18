package com.example.portalrap.FragmentsEntrenamiento;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.portalrap.MainActivity;
import com.example.portalrap.R;


public class FragTodoListo extends Fragment {

    Button btnAvanzar,btnANterior;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String desdedur = getArguments().getString("desdedur");

        if(desdedur == "si")
        {        View v = inflater.inflate(R.layout.fragment_frag_todo_listo, container, false);

            btnANterior = v.findViewById(R.id.btnAanteriordetodolisto);
            btnAvanzar = v.findViewById(R.id.btnAvanzardetodolisto);

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


            return v;
        }
        else {
            MainActivity main=(MainActivity) getActivity();
            main.PasaraFragEntrenar();
            return null;
        }

    }
}