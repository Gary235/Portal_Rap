package com.example.portalrap.FragmentsUsuario;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.portalrap.MainActivity;
import com.example.portalrap.R;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class FragEditarEmail extends Fragment {


    ImageButton btnCancelar, btnAceptar;
    EditText edtEmail, edtEmailnuevo;
    FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cambiar_email, container, false);

        btnAceptar = v.findViewById(R.id.btnAceptarEmail);
        btnCancelar = v.findViewById(R.id.btnCancelarEmail);
        edtEmail = v.findViewById(R.id.edtCambiarEmaildisable);
        edtEmailnuevo = v.findViewById(R.id.edtCambiarEmail);

        MainActivity main=(MainActivity) getActivity();
        user = main.obtenerUsuario();

        edtEmail.setText(user.getEmail());
        edtEmail.setEnabled(false);


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mensaje;
                mensaje = new AlertDialog.Builder(getActivity());
                mensaje.setTitle("Cancelar");
                mensaje.setMessage("Todos lo cambios que hayas hecho no se guardaran");
                mensaje.setPositiveButton("Aceptar",escuchadordecancelar);
                mensaje.setNegativeButton("Cancelar", escuchadordecancelar);
                mensaje.create();
                mensaje.show();

            }
        });
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtEmailnuevo.getText().toString().trim().length() != 0){
                    if(validarEmail(edtEmailnuevo.getText().toString().trim())) {
                        AlertDialog.Builder mensaje;
                        mensaje = new AlertDialog.Builder(getActivity());
                        mensaje.setTitle("Aceptar");
                        mensaje.setMessage("Los cambios se guardaran parcialmente");
                        mensaje.setPositiveButton("Aceptar",escuchadordeaceptar);
                        mensaje.setNegativeButton("Cancelar", escuchadordeaceptar);
                        mensaje.create();
                        mensaje.show();
                    }
                    else {
                        Toast.makeText(getActivity(), "Email invalido", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getActivity(), "Email invalido", Toast.LENGTH_SHORT).show();

                }
            }
        });

        return v;
    }

    public Boolean validarEmail(String mail){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(mail).matches();
    }


    DialogInterface.OnClickListener escuchadordecancelar = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(which == -1)
            {
                MainActivity main=(MainActivity) getActivity();
                main.PasaraFragEditarPerfil();
            }
            else if(which == -2)
            {
                dialog.cancel();
            }

        }
    };


    DialogInterface.OnClickListener escuchadordeaceptar = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            if(which == -1) {
                FragEditarPerfil.email = edtEmailnuevo.getText().toString().trim();
                MainActivity main = (MainActivity) getActivity();
                main.PasaraFragEditarPerfil();
            }
            else if(which == -2) {
                dialog.cancel();
            }

        }
    };

}
