package com.example.portalrap;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


public class FragEditarPerfil extends Fragment {

    ImageButton btnVolver, btnVernoVerContra,btnCambiar;
    TextView cambiarFoto;
    EditText edtNombre,edtContra;
    Boolean Ver = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_editar_perfil, container, false);

        Setear(v);
        ListenersAdicionales();



        return v;
    }

    public void Setear(View v) {
        btnVolver = v.findViewById(R.id.flechitabajodeeditarperfil);
        btnVernoVerContra = v.findViewById(R.id.ojo);
        btnCambiar = v.findViewById(R.id.btnCambiar);
        cambiarFoto = v.findViewById(R.id.txtCambiarFoto);
        edtNombre = v.findViewById(R.id.edtNombreUsuariodeEditar);
        edtContra = v.findViewById(R.id.edtContradeEditar);

        btnCambiar.setElevation(10000);
        btnCambiar.setTranslationZ(100);
    }

    public void ListenersAdicionales(){
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alertDialog
                AlertDialog.Builder mensaje;
                mensaje = new AlertDialog.Builder(getActivity());
                mensaje.setTitle("Volver");
                mensaje.setMessage("Todos lo cambios que hayas hecho no se confirmaran");
                mensaje.setPositiveButton("Aceptar",escuchador);
                mensaje.setNegativeButton("Cancelar", escuchador);
                mensaje.create();
                mensaje.show();
            }
        });


        btnVernoVerContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cambiar el input Type y el src del boton
                if(!Ver){
                    edtContra.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    btnVernoVerContra.setImageResource(R.drawable.ic_ojo_notachado);
                    Ver = true;
                }
                else if(Ver){
                    edtContra.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    btnVernoVerContra.setImageResource(R.drawable.ic_ojo_tachado);
                    Ver = false;
                }
                Log.d("Verrrrr",""+Ver);
            }
        });

        btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alertDialog
                AlertDialog.Builder mensaje;
                mensaje = new AlertDialog.Builder(getActivity());
                mensaje.setTitle("Confirmar Cambio");
                mensaje.setMessage("Todos lo cambios que hayas hecho se confirmaran");
                mensaje.setPositiveButton("Aceptar",escuchador2);
                mensaje.setNegativeButton("Cancelar", escuchador2);
                mensaje.create();
                mensaje.show();
            }
        });

        cambiarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cambiarFoto
            }
        });

    }
    DialogInterface.OnClickListener escuchador = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            if(which == -1)
            {
                MainActivity main=(MainActivity) getActivity();
                main.PasaraFragUsuario();
            }
            else if(which == -2)
            {
                dialog.cancel();
            }

        }
    };
    DialogInterface.OnClickListener escuchador2 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            if(which == -1)
            {
                MainActivity main=(MainActivity) getActivity();
                main.PasaraFragUsuario();
            }
            else if(which == -2)
            {
                dialog.cancel();
            }

        }
    };
}