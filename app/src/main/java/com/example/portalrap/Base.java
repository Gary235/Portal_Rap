package com.example.portalrap;

import android.widget.ImageButton;

public class Base {
    String _Nombre,_Artista;
    ImageButton _btnFav;
    Boolean checkBox;
    //Boolean _Fav;

    public Base( String nom, ImageButton btnfav, String artista, Boolean che /*Boolean fav*/){

        _Nombre = nom;
        _Artista = artista;
        _btnFav = btnfav;
        checkBox = che;
        //_Fav = fav;
    }
    public Base( ){


    }
}
