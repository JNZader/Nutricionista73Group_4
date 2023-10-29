package Entidades;

public class DietaComida {

    private int id;
    private Comida comida;
    private Dieta dieta;
    private int porcion;
    private Horario horario;
    private boolean estado;

    public DietaComida() {
    }

    public DietaComida(Comida comida, Dieta dieta, int porcion, Horario horario, boolean estado) {

        this.comida = comida;
        this.dieta = dieta;
        this.porcion = porcion;
        this.horario = horario;
        this.estado = estado;
    }

    public DietaComida(int id, Comida comida, Dieta dieta, int porcion, Horario horario, boolean estado) {

        this.id = id;
        this.comida = comida;
        this.dieta = dieta;
        this.porcion = porcion;
        this.horario = horario;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Comida getComida() {
        return comida;
    }

    public void setComida(Comida comida) {
        this.comida = comida;
    }

    public Dieta getDieta() {
        return dieta;
    }

    public void setDieta(Dieta dieta) {
        this.dieta = dieta;
    }

    public int getPorcion() {
        return porcion;
    }

    public void setPorcion(int porcion) {
        this.porcion = porcion;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "DietaComida{" + "id=" + id + ", comida=" + comida + ", dieta=" + dieta + ", porcion=" + porcion + ", horario=" + horario + ", estado=" + estado + '}';
    }
}
