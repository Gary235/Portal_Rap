package com.example.portalrap;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class FragRegistro extends Fragment implements View.OnClickListener{


    EditText edtusu,edtcontra,edtconf;
    Button btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.registro,container,false);

        edtusu = v.findViewById(R.id.editusuario);
        edtcontra = v.findViewById(R.id.editcontra);
        edtconf = v.findViewById(R.id.editconf);
        btn = v.findViewById(R.id.btnRegistrarse);
        btn.setOnClickListener(this);


        return v;
    }


    @Override
    public void onClick(View v) {
        MainActivity main=(MainActivity) getActivity();
        main.PasaraFragmentSlider();

    }
}
