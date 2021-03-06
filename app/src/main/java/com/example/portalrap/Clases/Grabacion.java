package com.example.portalrap.Clases;


import java.util.HashMap;
import java.util.Map;

public class Grabacion {

    private String Nombre,Id,Url;
    private Boolean Favoritos;

    public Grabacion(String nombre, String id, Boolean fav, String url) {
        this.Nombre = nombre;
        this.Id = id;
        this.Favoritos = fav;
        this.Url = url;
    }

    public Grabacion() {
    }


    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("Nombre", this.Nombre);
        result.put("Favoritos", this.Favoritos);
        result.put("Url", this.Url);
        return result;
    }

    public String getNombre() { return this.Nombre; }
    public void setNombre(String nombre) { Nombre = nombre; }

    public String getId() { return this.Id; }
    public void setId(String id) { Id = id; }

    public Boolean getFavoritos() { return this.Favoritos; }
    public void setFavoritos(Boolean fav) { Favoritos = fav; }

    public String getUrl() { return this.Url; }
    public void setUrl(String url) { Url = url; }
}
