package Entidades;

import java.util.Objects;

public class Comida {
    private int idComida;
    private int cantCalorias;
    private String nombre; 
    private String detalle;
    private boolean estado;

    public Comida() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Comida other = (Comida) obj;
        return Objects.equals(this.nombre, other.nombre);
    }

    public Comida(int idComida) {
        this.idComida = idComida;
    }

    public Comida(int idComida, int cantCalorias, String nombre, String detalle, boolean estado) {
        this.idComida = idComida;
        this.cantCalorias = cantCalorias;
        this.nombre = nombre;
        this.detalle = detalle;
        this.estado = estado;
    }

    public Comida(int cantCalorias, String nombre, String detalle, boolean estado) {
        this.cantCalorias = cantCalorias;
        this.nombre = nombre;
        this.detalle = detalle;
        this.estado = estado;
    }

    public int getIdComida() {
        return idComida;
    }

    public void setIdComida(int idComida) {
        this.idComida = idComida;
    }

    public int getCantCalorias() {
        return cantCalorias;
    }

    public void setCantCalorias(int cantCalorias) {
        this.cantCalorias = cantCalorias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return nombre;
    }

 
}
