package com.example.portalrap.Clases;


public class Grabacion {

    private String Nombre,Id;
    private Boolean Fav,Play;

    public Grabacion(String nombre, String id, Boolean fav, Boolean play) {
        Nombre = nombre;
        Id = id;
        Fav = fav;
        Play = play;
    }

    public Grabacion() {
    }


    public String getNombre() { return Nombre; }
    public void setNombre(String nombre) { Nombre = nombre; }

    public String getId() { return Id; }
    public void setId(String id) { Id = id; }

    public Boolean getFav() { return Fav; }
    public void setFav(Boolean fav) { Fav = fav; }

    public Boolean getPlay() { return Play; }
    public void setPlay(Boolean play) { Play = play; }
}
