/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Models.Paciente;

/**
 *
 * @author Usuario
 */
public class PacienteDAO implements GenericDao<Paciente>{
    private Connection conexion;
    
    public PacienteDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Insertar paciente
    public void crear(Paciente p) throws SQLException {
        String sql = "INSERT INTO Paciente (nombre, apellido, dni, direccion, fecha_nacimiento) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApellido());
            ps.setLong(3, p.getDni());
            ps.setString(4, p.getDireccion());
            ps.setDate(5, p.getFechaNacimiento());

            ps.executeUpdate();

            // Obtener el ID generado
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                p.setIdPaciente(rs.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Paciente leer(long id) throws SQLException {
        String sql = "SELECT * FROM Paciente WHERE id_paciente = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }    return null; // no encontrado
    }
    
    @Override
    public List<Paciente> leerTodos() throws SQLException{
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM Paciente";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery(sql)) {

            while (rs.next()) {
                Paciente p = new Paciente(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getLong("dni"),
                        rs.getString("direccion"),
                        rs.getDate("fecha_nacimiento")
                );
                p.setIdPaciente(rs.getLong("id_paciente"));
                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    @Override
    public void actualizar(Paciente p) throws SQLException {
        String sql = "UPDATE Paciente " +
                "SET nombre = ?, apellido = ?, dni = ?, direccion = ?, fecha_nacimiento = ? " +
                "WHERE id_paciente = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApellido());
            ps.setLong(3, p.getDni());
            ps.setString(4, p.getDireccion());
            ps.setDate(5, p.getFechaNacimiento());
            ps.setLong(6, p.getIdPaciente());

            ps.executeUpdate();
        }
    }
    
    @Override
    public void eliminar(long id) throws SQLException {
        // Si querés soft delete, acá podrías hacer UPDATE en vez de DELETE.
        String sql = "DELETE FROM Paciente WHERE id_paciente = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
    
    private Paciente mapear(ResultSet rs) throws SQLException {
        Paciente p = new Paciente(
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getLong("dni"),
                rs.getString("direccion"),
                rs.getDate("fecha_nacimiento")
        );
        p.setIdPaciente(rs.getLong("id_paciente"));
        return p;
    }
}
