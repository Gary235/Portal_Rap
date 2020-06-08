package com.example.portalrap;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class FragIniciarSesion extends Fragment implements View.OnClickListener{


    EditText editContrasenia,editUsuario;
    Button btnIniciarSesion,btnRegistro;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View vista;
        vista = inflater.inflate(R.layout.iniciarsesion,container,false);



        editContrasenia = vista.findViewById(R.id.edtContrasenia);
        editUsuario = vista.findViewById(R.id.edtUsuario);
        btnIniciarSesion = vista.findViewById(R.id.btnIniciarSesion);
        btnRegistro = vista.findViewById(R.id.btnRegistrarse);

        btnRegistro.setOnClickListener(this);
        btnIniciarSesion.setOnClickListener(this);

        return vista;
    }

    @Override
    public void onClick(View v) {
        Button botonapretado;
        botonapretado = (Button)v;
        int idbotonapretado = botonapretado.getId();
        MainActivity main=(MainActivity) getActivity();

        if(botonapretado.getId() == R.id.btnIniciarSesion)
        {

        }
        else if (botonapretado.getId() == R.id.btnRegistrarse)
        {
            main.PasaraFragmentRegistro();
        }

    }
}
