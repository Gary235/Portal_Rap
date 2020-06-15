package com.example.portalrap.Clases;

public class Model {

    private int Image;
    private String texto;

    public Model(int image, String texto) {
        this.Image = image;
        this.texto = texto;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
