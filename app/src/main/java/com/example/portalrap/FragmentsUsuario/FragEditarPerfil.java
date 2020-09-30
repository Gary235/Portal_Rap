package com.example.portalrap.FragmentsUsuario;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;


import android.os.Environment;
import android.os.ParcelFileDescriptor;
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
import androidx.core.content.res.ResourcesCompat;

import com.example.portalrap.MainActivity;
import com.example.portalrap.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class FragEditarPerfil extends Fragment {

    ImageButton btnVolver, btnEliminarFoto;
    Button btnConfirmarCambios,btnCambiarContra,btnCambiarEmail;
    TextView cambiarFoto;
    ImageView fotoperfil;
    FirebaseUser user;
    Bitmap bmp;
    public static String contravieja = null, contra = null, email = null;
    private FirebaseAuth mAuth;
    Uri selectedImage;


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
        btnEliminarFoto = v.findViewById(R.id.btnEliminardeEditarPerfil);


        MainActivity main = (MainActivity) getActivity();
        user = main.obtenerUsuario();

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Portal Rap/Fotos");
        String fname = "FotoDePerfil: " + user.getUid() + ".jpg";
        File file = new File (myDir, fname);

        if(file.exists())
            descargarFotoPerfil();


    }

    public void ListenersAdicionales(){

        btnEliminarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fotoperfil.setImageResource(R.drawable.ic_usuario_2);
                fotoperfil.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }
        });
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
                //openGallery();
                //openCamera();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult
                        (Intent.createChooser(intent, "Seleccione una imagen"), 1);
                Log.d("FotoPerfil", "manda el intent");

            }

    });
}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Log.d("FotoPerfil", "entra al result");

        if (resultCode == Activity.RESULT_OK) {
            Log.d("FotoPerfil", "ok");
            switch (requestCode) {
                case 1:
                        Log.d("FotoPerfil", "entro case 1");
                        selectedImage = imageReturnedIntent.getData();
                        assert selectedImage != null;
                        String selectedPath=selectedImage.getPath();
                        if (selectedPath != null) {
                            InputStream imageStream = null;
                            try {
                                imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                                Log.d("FotoPerfil", "error");

                            }
                            // Transformamos la URI de la imagen a inputStream y este a un Bitmap
                            bmp = BitmapFactory.decodeStream(imageStream);
                            // Ponemos nuestro bitmap en un ImageView que tengamos en la vista
                            fotoperfil.setImageBitmap(bmp);
                            fotoperfil.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            Log.d("FotoPerfil", "Foto Perfil" + fotoperfil);
                        }
                    Log.d("FotoPerfil", "llega al final del case" );
                break;
            }
            Log.d("FotoPerfil", "termina el switch" );
        }
        Log.d("FotoPerfil", "termina el activityresult" );
        //getFragmentManager().popBackStack();
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

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setPhotoUri(selectedImage)
                            .build();

                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //Log.d(TAG, "User profile updated.");
                                        Toast.makeText(getActivity(), "Cambio exitoso " , Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                    guardarImagen();


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

    private void guardarImagen() {
        Drawable drw = ResourcesCompat.getDrawable(getActivity().getResources(), R.drawable.ic_usuario_2, null);

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Portal Rap/Fotos");

        if (!myDir.exists())
            myDir.mkdir();

        String fname = "FotoDePerfil: " + user.getUid() + ".jpg";
        File file = new File (myDir, fname);


        FileOutputStream fos ;
        try {
            fos = new FileOutputStream(file);

            if(bmp != null)
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            else
                convertToBitmap(drw, drw.getIntrinsicWidth(), drw.getIntrinsicHeight()).compress(Bitmap.CompressFormat.PNG, 100, fos);

            fos.flush();
            fos.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }



    }

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


    private void descargarFotoPerfil(){



        Log.d("FotoPerfil", "url: " + user.getPhotoUrl());

        buscarfotoPerfil buscarfotoPerfil = new buscarfotoPerfil();
        buscarfotoPerfil.execute();



    }

    private class buscarfotoPerfil extends AsyncTask<Void, Void, Bitmap> {
        protected Bitmap doInBackground(Void... voids) {
            Bitmap bitmap = null;
            try {

                String root = Environment.getExternalStorageDirectory().toString();
                File myDir = new File(root + "/Portal Rap/Fotos");
                String fname = "FotoDePerfil: " + user.getUid() + ".jpg";
                File file = new File (myDir, fname);
                Uri uri = Uri.fromFile(file);

                bitmap = getBitmapFromUri(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            fotoperfil.setImageBitmap(result);
            fotoperfil.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }

    }


    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = getActivity().getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    private Bitmap convertToBitmap(Drawable drawable, int widthPixels, int heightPixels) {
        Bitmap bitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, widthPixels, heightPixels);
        drawable.draw(canvas);
        return bitmap;
    }


}