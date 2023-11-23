package com.example.xyz_final;

public class MainModel {
    String Nombre, Descripcion, Existencias, Precio, imgURL;

    public MainModel(){

    }
    public MainModel (String nombre, String descripcion, String existencias, String precio,String imgURL){
        Nombre = nombre;
        Descripcion = descripcion;
        Existencias = existencias;
        Precio = precio;
        this.imgURL = imgURL;
    }
    public String getNombre(){
        return Nombre;
    }
    public void setNombre(String nombre){
        Nombre = nombre;
    }
    public String getDescripcion(){
        return Descripcion;
    }
    public void  setDescripcion(String descripcion){
        Descripcion = descripcion;
    }
    public String getExistencias(){
        return Existencias;
    }
    public  void setExistencias(String existencias){
        this.Existencias = existencias;
    }
    public String getPrecio(){
        return Precio;
    }
    public void setPrecio(String precio){
        Precio = precio;
    }
    public String getImgURL(){
        return imgURL;
    }
    public void setImgURL(String imgURL){
        this.imgURL = imgURL;
    }
}



