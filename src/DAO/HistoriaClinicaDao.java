/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.HistoriaClinica;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Usuario
 */
public class HistoriaClinicaDao implements GenericDao<HistoriaClinica>{
        private Connection conexion;

    public HistoriaClinicaDao(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void crear(HistoriaClinica h) throws SQLException {
    String sql = "INSERT INTO HistoriaClinica (id, diagnostico, fechaCreacion, idPaciente) VALUES (?, ?, ?, ?)";

    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setLong(1, h.getId());            // ← ID ingresado por el usuario
        ps.setString(2, h.getDiagnostico());
        ps.setDate(3, h.getFechaCreacion());
        ps.setLong(4, h.getIdPaciente());
        ps.executeUpdate();
    }
}

    @Override
    public HistoriaClinica leer(long id) throws SQLException {
        String sql = "SELECT * FROM HistoriaClinica WHERE id = ? AND eliminado = 0";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    @Override
    public List<HistoriaClinica> leerTodos() throws SQLException {
        List<HistoriaClinica> lista = new ArrayList<>();
        String sql = "SELECT * FROM HistoriaClinica WHERE eliminado = 0";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    @Override
public void actualizar(HistoriaClinica h) throws SQLException {
    String sql = "UPDATE HistoriaClinica SET diagnostico = ?, fechaCreacion = ?, idPaciente = ? WHERE id = ?";
    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setString(1, h.getDiagnostico());
        ps.setDate(2, h.getFechaCreacion());
        ps.setLong(3, h.getIdPaciente());
        ps.setLong(4, h.getId());
        ps.executeUpdate();
    }
}

    @Override
    public void eliminar(long id) throws SQLException {
        String sql = "UPDATE HistoriaClinica SET eliminado=1 WHERE id=?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    private HistoriaClinica mapear(ResultSet rs) throws SQLException {
    HistoriaClinica h = new HistoriaClinica();
    h.setId(rs.getLong("id"));
    h.setDiagnostico(rs.getString("diagnostico"));
    h.setFechaCreacion(rs.getDate("fechaCreacion"));
    h.setIdPaciente(rs.getLong("idPaciente"));
    return h;
}

}
