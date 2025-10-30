package co.edu.poli.actividad.modelo;

import java.io.Serializable;

/**
 * Clase base que representa una actividad turística general.
 */
public abstract class Escalada implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idActividad;
    private String nombre;
    private int precio;
    private int duracion;
    private String tipo;
    private String lugar;

    public Escalada(String idActividad, String nombre, int precio, int duracion, String tipo, String lugar) {
        this.idActividad = idActividad;
        this.nombre = nombre;
        this.precio = precio;
        this.duracion = duracion;
        this.tipo = tipo;
        this.lugar = lugar;
    }

    public String getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(String idActividad) {
        this.idActividad = idActividad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public abstract double calcularCosto();

    @Override
    public String toString() {
        return "Actividad_turistica {" +
                "id='" + idActividad + '\'' +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", duracion=" + duracion +
                ", tipo='" + tipo + '\'' +
                ", lugar='" + lugar + '\'' +
                '}';
    }
}
