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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.portalrap.MainActivity;
import com.example.portalrap.R;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class FragEditarPerfil extends Fragment {

    ImageButton btnVolver, btnVernoVerContra,btnCambiar;
    TextView cambiarFoto;
    EditText edtNombre,edtContra;
    Boolean Ver = false;
    ImageView fotoperfil;

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
                mensaje.setPositiveButton("Aceptar",escuchador2);
                mensaje.setNegativeButton("Cancelar", escuchador2);
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
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(intent, "Seleccione una imagen"), 1);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Uri selectedImageUri = null;
        Uri selectedImage;

        String filePath = null;
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    String selectedPath=selectedImage.getPath();
                    if (selectedPath != null) {
                        InputStream imageStream = null;
                        try {
                            imageStream = getActivity().getContentResolver().openInputStream(
                                    selectedImage);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        // Transformamos la URI de la imagen a inputStream y este a un Bitmap
                        Bitmap bmp = BitmapFactory.decodeStream(imageStream);
                        // Ponemos nuestro bitmap en un ImageView que tengamos en la vista
                        fotoperfil.setImageBitmap(bmp);
                        fotoperfil.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    }
                }
                break;
        }
    }



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