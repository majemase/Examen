package com.example.examen;

public class Musica {
    private String titulo;
    private int imagen;
    private Integer cancion;

    public Musica(String titulo, int imagen, Integer cancion) {
        this.titulo = titulo;
        this.imagen = imagen;
        this.cancion = cancion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public Integer getCancion() {
        return cancion;
    }

    public void setCancion(Integer cancion) {
        this.cancion = cancion;
    }
}
