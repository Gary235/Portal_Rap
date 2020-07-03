package com.example.portalrap.FragmentsEntrenamiento;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.portalrap.MainActivity;
import com.example.portalrap.R;


public class FragCola extends Fragment {

    ImageButton btnVolver;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_cola, container, false);

        btnVolver = v.findViewById(R.id.btnVOlverdecola);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragEntrenar.holderparacola.setVisibility(View.GONE);
            }
        });
        return v;
    }
}