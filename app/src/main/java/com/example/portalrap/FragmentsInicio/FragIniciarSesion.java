package com.example.portalrap.FragmentsInicio;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.portalrap.MainActivity;
import com.example.portalrap.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class FragIniciarSesion extends Fragment implements View.OnClickListener{


    EditText editContrasenia,editUsuario;
    Button btnIniciarSesion,btnRegistro;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    ProgressDialog dialogo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View vista;
        vista = inflater.inflate(R.layout.iniciarsesion,container,false);

        mAuth = FirebaseAuth.getInstance();
        editContrasenia = vista.findViewById(R.id.edtContrasenia);
        editUsuario = vista.findViewById(R.id.edtUsuario);
        btnIniciarSesion = vista.findViewById(R.id.btnIniciarSesion);
        btnRegistro = vista.findViewById(R.id.btnRegistrarse);
        progressBar = vista.findViewById(R.id.progress);
        dialogo = new ProgressDialog(getActivity());

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
            //progressBar.setVisibility(View.VISIBLE);
            dialogo.show();
            validar();
            if (validar())
                loguearUsuario(editUsuario.getText().toString().trim(), editContrasenia.getText().toString().trim());
            dialogo.dismiss();
            //progressBar.setVisibility(View.GONE);
        }
        else if (botonapretado.getId() == R.id.btnRegistrarse)
        {
            main.PasaraFragmentRegistro();
        }

    }


    public Boolean validar(){
        String mail = editUsuario.getText().toString().trim();
        String contra = editContrasenia.getText().toString().trim();
        dialogo.setMessage("Validando...");

        if(mail.length() == 0 || contra.length() == 0)
            Toast.makeText(getActivity(), "Ingreso Invalido", Toast.LENGTH_SHORT).show();
        else {
            Boolean mailValido = validarEmail(mail);
            if (!mailValido) {
                Toast.makeText(getActivity(), "Email Invalido", Toast.LENGTH_SHORT).show();
                return false;
            } else
                return true;
        }
        return false;
    }
    public Boolean validarEmail(String mail){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(mail).matches();
    }
    private void loguearUsuario(String email, String password) {
        dialogo.setMessage("Iniciando Sesion de " + email);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            dialogo.setMessage("Iniciado Exitoso");

                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            MainActivity main = (MainActivity) getActivity();
                            main.actualizarUsuario(user);
                            main.PasaraFragmentHome();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                                    Toast.makeText(getActivity(), "Contraseña o Email inválidos", Toast.LENGTH_SHORT).show();
                            else
                                    Toast.makeText(getActivity(), "Fallo en el Ingreso", Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });

    }

}
