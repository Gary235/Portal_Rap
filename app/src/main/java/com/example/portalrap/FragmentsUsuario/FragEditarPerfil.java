package com.example.portalrap.FragmentsUsuario;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.example.portalrap.MainActivity;
import com.example.portalrap.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;


public class FragEditarPerfil extends Fragment {

    ImageButton btnVolver;
    Button btnConfirmarCambios,btnCambiarContra,btnCambiarEmail;
    TextView cambiarFoto;
    ImageView fotoperfil;
    FirebaseUser user;
    Bitmap bmp;
    public static String contravieja = null, contra = null, email = null;
    private FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.editar_perfil_dos, container, false);
        mAuth = FirebaseAuth.getInstance();

        Setear(v);
        ListenersAdicionales();

        return v;
    }

    public void Setear(View v) {
        btnVolver = v.findViewById(R.id.flechitabajodeeditarperfil);
        btnConfirmarCambios = v.findViewById(R.id.btnCambiar);
        cambiarFoto = v.findViewById(R.id.txtCambiarFoto);
        fotoperfil = v.findViewById(R.id.foto);
        btnCambiarContra = v.findViewById(R.id.btnCambiarContraseña);
        btnCambiarEmail = v.findViewById(R.id.btnCambiarEmail);

        MainActivity main = (MainActivity) getActivity();
        user = main.obtenerUsuario();

    }

    public void ListenersAdicionales(){


        btnCambiarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity main=(MainActivity) getActivity();
                main.PasaraFragEditarEmail();

            }
        });
        btnCambiarContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity main=(MainActivity) getActivity();
                main.PasaraFragEditarContra();

            }
        });
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alertDialog
                AlertDialog.Builder mensaje;
                mensaje = new AlertDialog.Builder(getActivity());
                mensaje.setTitle("Volver");
                mensaje.setMessage("Todos lo cambios que hayas hecho no se confirmaran");
                mensaje.setPositiveButton("Aceptar",escuchadordevolver);
                mensaje.setNegativeButton("Cancelar", escuchadordevolver);
                mensaje.create();
                mensaje.show();
            }
        });
        btnConfirmarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alertDialog

                       AlertDialog.Builder mensaje;
                            mensaje = new AlertDialog.Builder(getActivity());
                            mensaje.setTitle("Confirmar Cambios");
                            mensaje.setMessage("Todos lo cambios que hayas hecho se confirmaran");
                            mensaje.setPositiveButton("Aceptar",escuchadordecambiar);
                            mensaje.setNegativeButton("Cancelar", escuchadordecambiar);
                            mensaje.create();
                            mensaje.show();


            }
        });

        cambiarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cambiarFoto
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                getActivity().startActivityForResult(Intent.createChooser(intent, "Seleccione una imagen"), 1);

        }

    });
}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImage;

        bmp = null;
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImage = data.getData();
                    String selectedPath = selectedImage.getPath();

                    if (selectedPath != null) {
                        InputStream imageStream = null;
                        try {
                            imageStream = getActivity().getContentResolver().openInputStream(selectedImage);

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        // Transformamos la URI de la imagen a inputStream y este a un Bitmap
                        bmp = BitmapFactory.decodeStream(imageStream);
                        Log.d("Servicio", "Imagen: " + bmp);
                        // Ponemos nuestro bitmap en un ImageView que tengamos en la vista
                        fotoperfil.setImageBitmap(bmp);
                    }
                }
                break;
            case 2:
                if (resultCode == Activity.RESULT_OK) {
                    bmp = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                    fotoperfil.setImageBitmap(bmp);
                    Log.d("Servicio", "Imagen: " + bmp);
                }
                break;
        }


    }



    DialogInterface.OnClickListener escuchadordecambiar = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

          if(which == -1)
            {
                if (contra != null) {
                    user.updatePassword(contra)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("editarperfil", "User password updated.");
                                        Toast.makeText(getActivity(), "Cambio exitoso " , Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("editarperfil", "User password not updated.");
                            Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })

                    ;
                }
                if(email != null){
                     user.updateEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("editarperfil", "User email address updated.");
                                        Toast.makeText(getActivity(), "Cambio exitoso " , Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                             .addOnFailureListener(new OnFailureListener() {
                                 @Override
                                 public void onFailure(@NonNull Exception e) {
                                     Log.d("editarperfil", "User email address not updated: " + e.getMessage());
                                     Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                                 }
                             });
                }
                Log.d("editarperfil", "email" + email);
                Log.d("editarperfil", "password" + contra);

                MainActivity main=(MainActivity) getActivity();
                main.PasaraFragUsuario();
            }
            else if(which == -2)
            {
                dialog.cancel();
            }

        }
    };
    DialogInterface.OnClickListener escuchadordevolver = new DialogInterface.OnClickListener() {
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