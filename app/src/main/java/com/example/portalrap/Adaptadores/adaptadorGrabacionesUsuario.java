package com.example.portalrap.Adaptadores;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.portalrap.Clases.Base;
import com.example.portalrap.Clases.Grabacion;
import com.example.portalrap.FragmentsUsuario.FragFavoritos;
import com.example.portalrap.MainActivity;
import com.example.portalrap.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class adaptadorGrabacionesUsuario extends BaseAdapter {
    private ArrayList<Grabacion> arrGrabacion;
    private Context miContexto;
    private FirebaseFirestore db;

    adaptadorGrabacionesUsuario adaptadorGrabacionesUsuario = null;

    public adaptadorGrabacionesUsuario(ArrayList<Grabacion> arrayGrabacion, Context contexto) {
        arrGrabacion = arrayGrabacion;
        miContexto = contexto;
        this.adaptadorGrabacionesUsuario = this;
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) miContexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lista_grabaciones_usuario, parent, false);

            holder.btnFav = convertView.findViewById(R.id.btnFavlista);
            holder.Nombre = convertView.findViewById(R.id.textolista);
            holder.btnFav.setFocusable(false);
            holder.Nombre.setFocusable(false);
            db = FirebaseFirestore.getInstance();

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }
        holder.Nombre.setText(arrGrabacion.get(position).getNombre());

        holder.btnFav.setTag(R.integer.btnplusview, convertView);
        holder.btnFav.setTag(position);

        Log.d("corazon","Bool: " + arrGrabacion.get(position).getFavoritos() + ", Pos" + position);

        if(arrGrabacion.get(position).getFavoritos()) {
            //desfavear
            holder.btnFav.setImageResource(R.drawable.ic_icono_fav_rojo);
        }
        else if(!arrGrabacion.get(position).getFavoritos()) {
            //fav
            holder.btnFav.setImageResource(R.drawable.ic_icono_nofav);
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
            }
        });


        return convertView;
    }
    private class ViewHolder {
        protected ImageButton btnFav;
        protected TextView Nombre;

    }
    private void actualizarFav(String nombre, String id, String url, Boolean fav) {
        Map<String, Object> grabacion = (new Grabacion(nombre, id, fav, url)).toMap();

        db.collection("Grabaciones")
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

}
