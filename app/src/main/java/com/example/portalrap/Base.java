package com.example.portalrap;

import android.widget.CheckBox;
import android.widget.ImageButton;

public class Base {
    String _Nombre,_Artista;
    ImageButton _btnFav;
    CheckBox checkBox;
    //Boolean _Fav;

    public Base( String nom, ImageButton btnfav, String artista, CheckBox che /*Boolean fav*/){

        _Nombre = nom;
        _Artista = artista;
        _btnFav = btnfav;
        checkBox = che;
        //_Fav = fav;
    }
    public Base( ){


    }
}
