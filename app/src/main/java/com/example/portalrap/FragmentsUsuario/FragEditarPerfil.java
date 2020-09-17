package com.example.portalrap.FragmentsUsuario;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.portalrap.MainActivity;
import com.example.portalrap.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;


public class FragEditarPerfil extends Fragment {

    ImageButton btnVolver, btnVernoVerContra;
    Button btnCambiar;
    TextView cambiarFoto;
    EditText edtNombre,edtContra;
    Boolean Ver = false;
    ImageView fotoperfil;
    FirebaseUser user;
    Bitmap bmp;
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
        fotoperfil = v.findViewById(R.id.foto);

        MainActivity main = (MainActivity) getActivity();
        user = main.obtenerUsuario();
        edtNombre.setText(user.getEmail());

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
                mensaje.setPositiveButton("Aceptar",escuchadordevolver);
                mensaje.setNegativeButton("Cancelar", escuchadordevolver);
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

                if(edtContra.getText().toString().length() == 0 || edtNombre.getText().toString().equals(user.getEmail())) {
                    Toast.makeText(getActivity(), "No hubo cambios detectados", Toast.LENGTH_SHORT).show();
                }   else {
                        if(edtContra.getText().toString().length() <6){
                            Toast.makeText(getActivity(), "La contraseña debe contener, al menos, 6 caracteres", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            AlertDialog.Builder mensaje;
                            mensaje = new AlertDialog.Builder(getActivity());
                            mensaje.setTitle("Confirmar Cambio");
                            mensaje.setMessage("Todos lo cambios que hayas hecho se confirmaran");
                            mensaje.setPositiveButton("Aceptar",escuchadordecambiar);
                            mensaje.setNegativeButton("Cancelar", escuchadordecambiar);
                            mensaje.create();
                            mensaje.show();

                    }
                }
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
                user.updateEmail(edtNombre.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    //Log.d(TAG, "User email address updated.");
                                }
                            }
                        });

                user.updatePassword(edtContra.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                   //Log.d(TAG, "User password updated.");
                                }
                            }
                        });

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