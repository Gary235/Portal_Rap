package com.example.portalrap.Adaptadores;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.portalrap.Clases.Base;
import com.example.portalrap.FragBases;
import com.example.portalrap.FragmentsUsuario.FragFavoritos;
import com.example.portalrap.MainActivity;
import com.example.portalrap.R;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class adaptadorBases extends BaseAdapter {
    private ArrayList<Base> arrBases;
    private Context miContexto;
    public static int aja = 0;
    private FirebaseFirestore db;
    MainActivity main;

    public adaptadorBases(ArrayList<Base> arrayBases,Context contexto)
    {
        arrBases = arrayBases;
        miContexto = contexto;
    }
    @Override
    public int getViewTypeCount() {
        if(getCount() <= 1)
            return 1;
        else
            return arrBases.size();
    }
    @Override
    public int getItemViewType(int position) { return position; }

    @Override
    public int getCount() { return arrBases.size(); }

    @Override
    public Base getItem(int position) {
        Base bas;
        bas = arrBases.get(position);
        return bas;
    }

    @Override
    public long getItemId(int position) { return position; }

    public Context getContext(){ return miContexto; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) miContexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lista_bases, parent, false);

            holder.btnFav = convertView.findViewById(R.id.btnFavlistabases);
            holder.Nombre = convertView.findViewById(R.id.nombreBase);
            holder.Artista = convertView.findViewById(R.id.nombreArtista);
            holder.check = convertView.findViewById(R.id.checkbox);
            holder.Duracion = convertView.findViewById(R.id.duracionbase);
            holder.btnFav.setFocusable(false);
            holder.check.setFocusable(false);
            holder.Nombre.setFocusable(false);
            holder.Artista.setFocusable(false);
            holder.Duracion.setFocusable(false);
            db = FirebaseFirestore.getInstance();
            main = (MainActivity) getContext();

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.Duracion.setText(arrBases.get(position).getDuracion());
        holder.Nombre.setText(arrBases.get(position).getNombre());
        holder.Artista.setText(arrBases.get(position).getArtista());

        if(FragBases.isActionMode || FragFavoritos.isActionMode) {
            holder.check.setVisibility(View.VISIBLE);
        }
        else {
            holder.check.setVisibility(View.INVISIBLE);
        }
        if(arrBases.get(position).getDestacado() != null) {
            holder.check.setChecked(arrBases.get(position).getDestacado());
        }

        holder.check.setTag(R.integer.btnplusview, convertView);
        holder.check.setTag(position);
        holder.btnFav.setTag(R.integer.btnplusview, convertView);
        holder.btnFav.setTag(position);

        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int position = (int) buttonView.getTag();
                Integer pos = (Integer)  holder.check.getTag();

                if(arrBases.get(pos).getDestacado()!= null) {
                    if(arrBases.get(pos).getDestacado() ){
                        arrBases.get(pos).setDestacado(false);
                    }else{
                        arrBases.get(pos).setDestacado(true);
                    }
                }

                if(FragBases.UserSelection.contains(arrBases.get(position))) {
                    FragBases.UserSelection.remove(arrBases.get(position));
                    aja = aja - 1;
                } else {
                    FragBases.UserSelection.add(arrBases.get(position));
                    aja = aja + 1;
                }

                if(FragFavoritos.UserSelection.contains(arrBases.get(position))) {
                    FragFavoritos.UserSelection.remove(arrBases.get(position));
                    aja = aja - 1;
                } else {
                    FragFavoritos.UserSelection.add(arrBases.get(position));
                    aja = aja + 1;
                }

                if(FragFavoritos.isActionMode) {
                    if(FragFavoritos.UserSelection != null) {
                        if(FragFavoritos.UserSelection.size() == 0)
                            FragFavoritos.actionMode.setTitle(" 0 Bases seleccionadas");
                        else
                            FragFavoritos.actionMode.setTitle(FragFavoritos.UserSelection.size() + " Bases seleccionadas");
                    }
                } else if(FragBases.isActionMode){
                    if(FragBases.UserSelection != null) {
                        if(FragBases.UserSelection.size() == 0 )
                            FragBases.actionMode.setTitle(" 0 Bases seleccionadas");

                        FragBases.actionMode.setTitle(FragBases.UserSelection.size()+ " Bases seleccionadas");
                    }
                }


            }
        });

            if(arrBases.get(position).getFavoritos()) {
                holder.btnFav.setImageResource(R.drawable.ic_icono_fav_rojo);
            } else if(!arrBases.get(position).getFavoritos()) {
                holder.btnFav.setImageResource(R.drawable.ic_icono_nofav);
            }


        holder.btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer pos = (Integer)  holder.btnFav.getTag();

                if(arrBases.get(pos).getFavoritos()!= null) {
                    if(arrBases.get(pos).getFavoritos()){
                        arrBases.get(pos).setFavoritos(false);
                        holder.btnFav.setImageResource(R.drawable.ic_icono_nofav);
                        main.eliminarFav(arrBases.get(pos).getId());
                    }else{
                        arrBases.get(pos).setFavoritos(true);
                        holder.btnFav.setImageResource(R.drawable.ic_icono_fav_rojo);
                        main.agregarFav(arrBases.get(pos).getNombre(),arrBases.get(pos).getArtista(),false,arrBases.get(pos).getId(),arrBases.get(pos).getUrl(),arrBases.get(pos).getFavoritos(), arrBases.get(pos).getDuracion());
                    }
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        protected TextView Nombre,Artista, Duracion;
        protected ImageButton btnFav;
        protected CheckBox check;

    }
}
