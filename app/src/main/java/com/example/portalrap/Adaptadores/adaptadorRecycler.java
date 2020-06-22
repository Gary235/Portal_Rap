package com.example.portalrap.Adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.portalrap.Clases.Base;
import com.example.portalrap.FragBases;
import com.example.portalrap.FragmentsUsuario.FragFavoritos;
import com.example.portalrap.R;

import java.util.ArrayList;

public class adaptadorRecycler extends RecyclerView.Adapter {

    private ArrayList<Base> arrBases;
    private Context miContexto;
    public static int aja = 0;
    Base miBase = new Base();
    @SuppressLint("StaticFieldLeak")
    static TextView Nombre,Artista;
    @SuppressLint("StaticFieldLeak")
    static ImageButton btnFav;
    @SuppressLint("StaticFieldLeak")
    static CheckBox check;

    public adaptadorRecycler(ArrayList<Base> arrBases, Context miContexto) {
        this.arrBases = arrBases;
        this.miContexto = miContexto;
    }

    public static class Holder extends RecyclerView.ViewHolder{

        public Holder(@NonNull View vista) {
            super(vista);
            btnFav = vista.findViewById(R.id.btnFavlista);
            Nombre = vista.findViewById(R.id.nombreBase);
            Artista = vista.findViewById(R.id.nombreArtista);
            check = vista.findViewById(R.id.checkbox);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v ;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_bases,parent,false);
        return new Holder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        check.setTag(position);
        btnFav.setFocusable(false);
        check.setFocusable(false);
        Nombre.setFocusable(false);
        Artista.setFocusable(false);

        miBase = arrBases.get(position);
        Nombre.setText(miBase.getNombre());
        Artista.setText(miBase.getArtista());

        if(FragBases.isActionMode || FragFavoritos.isActionMode)
        {
            check.setVisibility(View.VISIBLE);
        }
        else
        {
            check.setVisibility(View.GONE);
        }

        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                int position = (int) buttonView.getTag();
                ArrayList<Integer> checkedlist = new ArrayList<>();

                Log.d("pos","" + position);
                Log.d("pos","" + isChecked);

                if (isChecked)
                {
                    arrBases.get(position).setDestacado(true);
                }
                else
                {
                    arrBases.get(position).setDestacado(false);
                }

                if(FragBases.UserSelection.contains(arrBases.get(position))) {
                    FragBases.UserSelection.remove(arrBases.get(position));
                    aja = aja - 1;
                }
                else {
                    FragBases.UserSelection.add(arrBases.get(position));
                    aja = aja + 1;
                }

                if(FragFavoritos.UserSelection.contains(arrBases.get(position))) {
                    FragFavoritos.UserSelection.remove(arrBases.get(position));
                    aja = aja - 1;
                }
                else {
                    FragFavoritos.UserSelection.add(arrBases.get(position));
                    aja = aja + 1;
                }

                if(FragFavoritos.isActionMode) {
                    FragFavoritos.actionMode.setTitle(FragFavoritos.UserSelection.size() + " Bases seleccionadas");
                }
                else {
                    FragBases.actionMode.setTitle(FragBases.UserSelection.size()+ " Bases seleccionadas");
                }
            }
        });
    }

    @Override
    public int getItemCount() { return arrBases.size(); }
}
