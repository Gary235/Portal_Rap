package com.example.portalrap.Clases;


import java.util.HashMap;
import java.util.Map;

public class Base {

    private String Artista,Nombre,Url,Duracion;
    private Boolean Destacado,Favoritos;
    private String Id;

    //Constructores

    public Base(String artista, String nombre, String url, String duracion, Boolean destacado, Boolean favoritos, String id) {
        Artista = artista;
        Nombre = nombre;
        Url = url;
        Duracion = duracion;
        Destacado = destacado;
        Favoritos = favoritos;
        Id = id;
    }

    public Base( ){


    }

    //map
    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("Nombre", this.Nombre);
        result.put("Artista", this.Artista);
        result.put("Url", this.Url);
        result.put("Destacado", this.Destacado);
        result.put("Favoritos", this.Favoritos);
        result.put("Duracion", this.Duracion);
        return result;
    }


    //Getter y Setter
    public String getArtista() {
        return Artista;
    }
    public void setArtista(String artista) {
        Artista = artista;
    }

    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getUrl() {
        return Url;
    }
    public void setUrl(String url) {
        Url = url;
    }

    public Boolean getDestacado() {
        return Destacado;
    }
    public void setDestacado(Boolean destacado) {
        Destacado = destacado;
    }

    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
    }

    public Boolean getFavoritos() { return Favoritos; }
    public void setFavoritos(Boolean favoritos) { Favoritos = favoritos; }

    public String getDuracion() { return Duracion; }
    public void setDuracion(String duracion) { Duracion = duracion; }
}
