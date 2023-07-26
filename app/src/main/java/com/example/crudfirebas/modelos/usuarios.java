package com.example.crudfirebas.modelos;

public class usuarios {
    private String ID;
    private String Foro;
    private String Apellido;
    private String Nombre;
    private String genero;
    private String fecha;

    public usuarios() {

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getForo() {
        return Foro;
    }

    public void setForo(String foro) {
        Foro = foro;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return Foro;
    }
}
