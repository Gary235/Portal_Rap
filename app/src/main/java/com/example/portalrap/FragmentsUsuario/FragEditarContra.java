package com.example.portalrap.FragmentsUsuario;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.portalrap.MainActivity;
import com.example.portalrap.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseUser;

public class FragEditarContra extends Fragment {


    ImageButton btnCancelar, btnAceptar;
    EditText edtContra, edtConf;
    FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cambiar_contra, container, false);

        btnAceptar = v.findViewById(R.id.btnAceptarContra);
        btnCancelar = v.findViewById(R.id.btnCancelarContra);
        edtConf = v.findViewById(R.id.edtCambiarContrados);
        edtContra = v.findViewById(R.id.edtCambiarContrauno);
        MainActivity main=(MainActivity) getActivity();
        user = main.obtenerUsuario();


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
                if(edtConf.getText().toString().trim().length() >= 6 && edtContra.getText().toString().trim().length() >= 6){
                    AuthCredential credential = EmailAuthProvider
                            .getCredential(user.getEmail(),edtContra.getText().toString().trim());
                    // Prompt the user to re-provide their sign-in credentials
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //Log.d(TAG, "User re-authenticated.");
                                    if(task.isSuccessful()){
                                        AlertDialog.Builder mensaje;
                                        mensaje = new AlertDialog.Builder(getActivity());
                                        mensaje.setTitle("Aceptar");
                                        mensaje.setMessage("Los cambios se guardaran parcialmente");
                                        mensaje.setPositiveButton("Aceptar",escuchadordeaceptar);
                                        mensaje.setNegativeButton("Cancelar", escuchadordeaceptar);
                                        mensaje.create();
                                        mensaje.show();

                                    } else {
                                        // Password is incorrect
                                        Toast.makeText(getActivity(), "Contraseña actual incorrecta", Toast.LENGTH_SHORT).show();

                                    }}
                            });
                }
                else {
                    Toast.makeText(getActivity(), "La contraseña debe contener 6 caracteres como minimo", Toast.LENGTH_SHORT).show();
                }

            }
        });




        return v;
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
                FragEditarPerfil.contravieja = edtContra.getText().toString().trim();
                FragEditarPerfil.contra = edtConf.getText().toString().trim();
                MainActivity main=(MainActivity) getActivity();
                main.PasaraFragEditarPerfil();
            }
            else if(which == -2) {
                dialog.cancel();
            }

        }
    };

}
