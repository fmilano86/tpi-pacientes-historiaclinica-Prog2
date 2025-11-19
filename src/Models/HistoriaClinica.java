/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author Usuario
 */
public class HistoriaClinica {
     // Atributos
    private Long id;
    private String diagnostico;
    private java.sql.Date fechaCreacion;
    private Long idPaciente;

    // Constructores

    // Constructor vacío (requerido por frameworks y DAO)
    public HistoriaClinica() {
    }

    // Constructor completo
    public HistoriaClinica(Long id, String diagnostico, java.sql.Date fechaCreacion, Long idPaciente) {
        this.id = id;
        this.diagnostico = diagnostico;
        this.fechaCreacion = fechaCreacion;
        this.idPaciente = idPaciente;
       
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    @Override
    public String toString() {
        return "HistoriaClinica{" + "id=" + id + ", diagnostico=" + diagnostico + ", fechaCreacion=" + fechaCreacion + ", idPaciente=" + idPaciente + '}';
    }



    // equals() y hashCode() (opcional pero recomendable)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoriaClinica)) return false;
        HistoriaClinica that = (HistoriaClinica) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
