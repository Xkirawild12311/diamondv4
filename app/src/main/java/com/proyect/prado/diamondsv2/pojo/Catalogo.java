package com.proyect.prado.diamondsv2.pojo;

public class Catalogo {

    private String titulo;
    private String detalle;
    private String precio;
    private String imagen;

    public Catalogo() {
    }

    public Catalogo(String titulo, String detalle, String imagen,String precio) {
        this.titulo = titulo;
        this.detalle = detalle;
        this.imagen = imagen;
        this.precio = precio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
