package com.example.portalrap.Adaptadores;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.portalrap.Clases.Base;
import com.example.portalrap.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class adaptadorDeColaSiguiente extends BaseAdapter {
    private ArrayList<Base> arrBasesSiguiente = new ArrayList<>();
    private Context miContexto;
    private FirebaseFirestore db;

    public adaptadorDeColaSiguiente(ArrayList<Base> arrBasesSiguiente, Context miContexto) {
        this.arrBasesSiguiente = arrBasesSiguiente;
        this.miContexto = miContexto;
    }

    @Override
    public int getCount() { return arrBasesSiguiente.size(); }

    @Override
    public Base getItem(int position) {
        Base bas;
        bas = arrBasesSiguiente.get(position);
        return bas;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder {
        protected TextView Nombre,Artista;
        protected ImageButton btnFav;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) miContexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_cola_siguiente, parent, false);

            holder.btnFav = convertView.findViewById(R.id.btnFavoritoCola2);
            holder.Nombre = convertView.findViewById(R.id.nombreBeatCola2);
            holder.Artista = convertView.findViewById(R.id.nombreArtistaCola2);
            holder.btnFav.setFocusable(false);
            holder.Nombre.setFocusable(false);
            holder.Artista.setFocusable(false);
            db = FirebaseFirestore.getInstance();


            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.Nombre.setText(arrBasesSiguiente.get(position).getNombre());
        holder.Artista.setText(arrBasesSiguiente.get(position).getArtista());

        holder.btnFav.setTag(R.integer.btnplusview, convertView);
        holder.btnFav.setTag(position);


        if(arrBasesSiguiente.get(position).getFavoritos())
        {
            //desfavear
            holder.btnFav.setImageResource(R.drawable.ic_icono_fav_blanco);
        }
        else if(!arrBasesSiguiente.get(position).getFavoritos()) {
            //fav
            holder.btnFav.setImageResource(R.drawable.ic_icono_nofav_blanco);

        }


        holder.btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View tempview = (View) holder.btnFav.getTag(R.integer.btnplusview);
                //TextView tv = (TextView) tempview.findViewById(R.id.animal);
                Integer pos = (Integer)  holder.btnFav.getTag();

                if(arrBasesSiguiente.get(pos).getFavoritos()!= null)
                {
                    if(arrBasesSiguiente.get(pos).getFavoritos()){
                        arrBasesSiguiente.get(pos).setFavoritos(false);
                        holder.btnFav.setImageResource(R.drawable.ic_icono_nofav_blanco);

                    }else{
                        arrBasesSiguiente.get(pos).setFavoritos(true);
                        holder.btnFav.setImageResource(R.drawable.ic_icono_fav_blanco);

                    }
                }
                actualizarFav(arrBasesSiguiente.get(pos).getNombre(),arrBasesSiguiente.get(pos).getArtista(),false,arrBasesSiguiente.get(pos).getId(),arrBasesSiguiente.get(pos).getUrl(),arrBasesSiguiente.get(pos).getFavoritos(), arrBasesSiguiente.get(pos).getDuracion());
            }
        });


        return convertView;
    }

    private void actualizarFav(String nombre,String artista,Boolean destacado, String id, String url, Boolean fav, String dur) {
        Map<String, Object> beat = (new Base(artista,nombre, url,dur,destacado, fav,id)).toMap();

        db.collection("Beats")
                .document(id)
                .update(beat)
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
