package Entidades;

import java.time.LocalDate;

public class Consulta {

    private int idConsulta;
    private Paciente paciente;
    private LocalDate fecha;
    private double pesoActual;

    public Consulta() {
    }

    public Consulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Consulta(Paciente paciente, LocalDate fecha, double pesoActual) {
        this.paciente = paciente;
        this.fecha = fecha;
        this.pesoActual = pesoActual;
    }

    public Consulta(int idConsulta, Paciente paciente, LocalDate fecha, double pesoActual) {
        this.idConsulta = idConsulta;
        this.paciente = paciente;
        this.fecha = fecha;
        this.pesoActual = pesoActual;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getPesoActual() {
        return pesoActual;
    }

    public void setPesoActual(double pesoActual) {
        this.pesoActual = pesoActual;
    }

    @Override
    public String toString() {
        return "Consulta{" + "idConsulta=" + idConsulta + ", paciente=" + paciente + ", fecha=" + fecha + ", pesoActual=" + pesoActual + '}';
    }

}
