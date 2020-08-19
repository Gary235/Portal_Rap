package com.example.portalrap;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class FragHome extends Fragment implements View.OnClickListener {

    ImageButton btnEntrenar;
    ImageView fondo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_2, container, false);


        fondo = v.findViewById(R.id.fondo);
        btnEntrenar = v.findViewById(R.id.btnentrenar);
        btnEntrenar.setOnClickListener(this);

        //if (!MainActivity.TodosPermisos) btnEntrenar.setEnabled(false);

        int aleatorio = (int) (Math.random() * 3) ;

        switch (aleatorio){
            case 0:
                fondo.setImageResource(R.drawable.ic_fondo_home_1);
                break;
            case 1:
                fondo.setImageResource(R.drawable.ic_fondo_home_2);
                break;
            case 2:
                fondo.setImageResource(R.drawable.ic_fondo_home_3);
                break;

        }
        return v;
    }

    @Override
    public void onClick(View v) {

        MainActivity main = (MainActivity) getActivity();
        main.PasaraFragmentModo();
    }



}