package com.example.portalrap.Adaptadores;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.example.portalrap.Clases.Grabacion;
import com.example.portalrap.MainActivity;
import com.example.portalrap.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class adaptadorGrabacionesUsuario extends BaseAdapter {
    private ArrayList<Grabacion> arrGrabacion;
    private Context miContexto;
    private FirebaseFirestore db;
    Integer posicioneliminar;
    FirebaseUser user;
    MainActivity main;



    public adaptadorGrabacionesUsuario(ArrayList<Grabacion> arrayGrabacion, Context contexto) {
        arrGrabacion = arrayGrabacion;
        miContexto = contexto;

    }


    @Override
    public int getCount() { return arrGrabacion.size(); }

    @Override
    public Grabacion getItem(int position) {
        Grabacion grab;
        grab = arrGrabacion.get(position);
        return grab;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Context getContext(){
        return miContexto;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) miContexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lista_grabaciones_usuario, parent, false);

            holder.btnFav = convertView.findViewById(R.id.btnFavlista);
            holder.Nombre = convertView.findViewById(R.id.textolista);
            holder.btnEliminar = convertView.findViewById(R.id.btnEliminar);
            holder.btnCompartir = convertView.findViewById(R.id.btnCompartir);
            holder.btnFav.setFocusable(false);
            holder.Nombre.setFocusable(false);
            holder.btnEliminar.setFocusable(false);
            holder.btnCompartir.setFocusable(false);

            db = FirebaseFirestore.getInstance();
            main = (MainActivity) getContext();
            user = main.obtenerUsuario();

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }
        holder.Nombre.setText(arrGrabacion.get(position).getNombre());

        holder.btnFav.setTag(R.integer.btnplusview, convertView);
        holder.btnFav.setTag(position);

        holder.btnEliminar.setTag(R.integer.btnplusview, convertView);
        holder.btnEliminar.setTag(position);

        holder.btnCompartir.setTag(R.integer.btnplusview, convertView);
        holder.btnCompartir.setTag(position);

        if(arrGrabacion.get(position).getFavoritos() != null) {
            if(arrGrabacion.get(position).getFavoritos()) {
                holder.btnFav.setImageResource(R.drawable.ic_icono_fav_rojo);
            }
            else if(!arrGrabacion.get(position).getFavoritos()) {
                holder.btnFav.setImageResource(R.drawable.ic_icono_nofav);
            }
        }

        holder.btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View tempview = (View) holder.btnFav.getTag(R.integer.btnplusview);
                //TextView tv = (TextView) tempview.findViewById(R.id.animal);
                Integer pos = (Integer)  holder.btnFav.getTag();

                if(arrGrabacion.get(pos).getFavoritos()!= null)
                {
                    if(arrGrabacion.get(pos).getFavoritos()){
                        arrGrabacion.get(pos).setFavoritos(false);
                        holder.btnFav.setImageResource(R.drawable.ic_icono_nofav);

                    }else{
                        arrGrabacion.get(pos).setFavoritos(true);
                        holder.btnFav.setImageResource(R.drawable.ic_icono_fav_rojo);

                    }
                }

                actualizarFav(arrGrabacion.get(pos).getNombre(),arrGrabacion.get(pos).getId(),arrGrabacion.get(pos).getUrl(),arrGrabacion.get(pos).getFavoritos());
                Log.d("corazon","Bool: " + arrGrabacion.get(pos).getFavoritos() + ", Pos" + pos);
                Log.d("CambiarFav","Id: " + arrGrabacion.get(pos).getId() );
                Log.d("CambiarFav","UId: " + user.getUid() );

            }
        });


        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posicioneliminar = (Integer)  holder.btnEliminar.getTag();

                AlertDialog.Builder mensaje;
                mensaje = new AlertDialog.Builder(getContext());
                mensaje.setTitle("Eliminar Grabacion");
                mensaje.setMessage("\n No se puede recuperar una grabacion eliminada \n \n");
                mensaje.setPositiveButton("Eliminar", escuchador);
                mensaje.setNegativeButton("Cancelar", escuchador);
                mensaje.create();
                mensaje.show();

            }
        });


        holder.btnCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*int poscompartir = (Integer)  holder.btnCompartir.getTag();


                Uri uri = convertirAudioAURI(poscompartir);
                Intent compartir = new Intent(android.content.Intent.ACTION_SEND);
                compartir.putExtra(Intent.EXTRA_STREAM, uri);
                compartir.setType("audio/*");
                miContexto.startActivity(Intent.createChooser(compartir, "Compartir Grabacion"));*/
    }
});

        return convertView;
    }
    private class ViewHolder {
        protected ImageButton btnFav,btnEliminar,btnCompartir;
        protected TextView Nombre;

    }

    private void actualizarFav(String nombre, String id, String url, Boolean fav) {
        Map<String, Object> grabacion = (new Grabacion(nombre, id, fav, url)).toMap();

        db.collection("Usuarios").document(user.getUid()).collection("Grabaciones")
                .document(id)
                .update(grabacion)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("CambiarFav", "Bien Ahi");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("CambiarFav", "Error al actualizar: " + e);

                    }
                });
    }


    private Uri convertirAudioAURI(int pos){
        String path_file = Environment.getExternalStorageDirectory() + "/Portal Rap/";

        File file = new File(path_file, arrGrabacion.get(pos).getUrl());

        return FileProvider.getUriForFile(getContext(), "com.example.portalrap.fileprovider", file);
    }



    DialogInterface.OnClickListener escuchador = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            if(which == -1)
            {

                MainActivity main = (MainActivity) getContext();
                // borrar de storage
                main.eliminarGrabDeStorage(arrGrabacion.get(posicioneliminar).getUrl());
                // borrar de firestore
                main.eliminarGrabDeFirestore(arrGrabacion.get(posicioneliminar).getId());
                // borrar del almacenamiento interno
                main.eliminarGrabDeAlmacenamiento(arrGrabacion.get(posicioneliminar).getUrl());

            }
            else if(which == -2)
            {
                dialog.cancel();
            }

        }
    };


}
