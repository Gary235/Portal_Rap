package com.example.portalrap.Clases;

import android.widget.ImageButton;

public class Base {


    private String _Nombre,_Artista;
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
    public Boolean getCheckBox() {
        return checkBox;
    }
    public void setCheckBox(Boolean checkBox) {
        this.checkBox = checkBox;
    }

    public String get_Nombre() {
        return _Nombre;
    }

    public void set_Nombre(String _Nombre) {
        this._Nombre = _Nombre;
    }

    public String get_Artista() {
        return _Artista;
    }

    public void set_Artista(String _Artista) {
        this._Artista = _Artista;
    }
}
