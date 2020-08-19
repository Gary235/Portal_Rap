package com.example.portalrap.FragmentsInicio;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.portalrap.MainActivity;
import com.example.portalrap.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;
import java.util.regex.Pattern;

public class FragRegistro extends Fragment{


    EditText edtusu,edtcontra,edtconf;
    Button btn;
    private FirebaseAuth mAuth;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.registro,container,false);

        mAuth = FirebaseAuth.getInstance();
        edtusu = v.findViewById(R.id.editusuario);
        edtcontra = v.findViewById(R.id.editcontra);
        edtconf = v.findViewById(R.id.editconf);
        btn = v.findViewById(R.id.btnRegistrarse);
        ListenerBoton();

        return v;
    }


    public void ListenerBoton(){

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtusu.getText().toString().trim();
                String contra = edtcontra.getText().toString().trim();
                String confirmacion = edtconf.getText().toString().trim();


                if (email.length() == 0 || contra.length() == 0 || confirmacion.length() == 0)
                        Toast.makeText(getActivity(), "Ingreso Invalido", Toast.LENGTH_SHORT).show();
                else {
                   Boolean valido = validarEmail(email);
                   if(!valido)
                       Toast.makeText(getActivity(), "Email Invalido", Toast.LENGTH_SHORT).show();

                   if(!contra.equals(confirmacion))
                       Toast.makeText(getActivity(), "La contraseña y la confirmacion no coinciden", Toast.LENGTH_SHORT).show();

                   if (valido && contra.equals(confirmacion)){
                       //registrar usuario
                       registrarUsuario(email,contra);
                   }

                }



            }
        });


    }

    public Boolean validarEmail(String mail){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(mail).matches();
    }

    private void registrarUsuario(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener( getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            Toast.makeText(getActivity(), "Registro Existoso", Toast.LENGTH_SHORT).show();


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Fallo en el registro", Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }





}
