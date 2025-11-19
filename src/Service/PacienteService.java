/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.PacienteDAO;
import Models.Paciente;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class PacienteService implements GenericService<Paciente>{
    private Connection conn;
    private PacienteDAO pacienteDao;

    public PacienteService(Connection conn, PacienteDAO pacienteDao) {
        this.conn = conn;
        this.pacienteDao = pacienteDao;
    }

    @Override
    public void insertar(Paciente entidad) {
        if (entidad == null)
            throw new IllegalArgumentException("El paciente no puede ser nulo.");

        try {
            conn.setAutoCommit(false);

            pacienteDao.crear(entidad);

            conn.commit();
            System.out.println(" Paciente insertado correctamente.");

        } catch (SQLException e) {
            System.err.println(" Error al insertar paciente: " + e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println(" Error al hacer rollback: " + ex.getMessage());
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println(" Error al restablecer autoCommit: " + e.getMessage());
            }
        }
    }

    @Override
    public void actualizar(Paciente entidad) {
        if (entidad == null)
            throw new IllegalArgumentException("El paciente no puede ser nulo.");

        try {
            conn.setAutoCommit(false);

            pacienteDao.actualizar(entidad);

            conn.commit();
            System.out.println(" Paciente actualizado correctamente.");

        } catch (SQLException e) {
            System.err.println(" Error al actualizar paciente: " + e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println(" Error al hacer rollback: " + ex.getMessage());
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println(" Error al restablecer autoCommit: " + e.getMessage());
            }
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            conn.setAutoCommit(false);

            pacienteDao.eliminar(id);

            conn.commit();
            System.out.println(" Paciente eliminado correctamente.");

        } catch (SQLException e) {
            System.err.println(" Error al eliminar paciente: " + e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println(" Error al hacer rollback: " + ex.getMessage());
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println(" Error al restablecer autoCommit: " + e.getMessage());
            }
        }
    }

    @Override
    public Paciente getById(Long id) {
        try {
            return pacienteDao.leer(id);
        } catch (SQLException e) {
            System.err.println(" Error al leer paciente: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Paciente> getAll() {
        try {
            return pacienteDao.leerTodos();
        } catch (SQLException e) {
            System.err.println(" Error al listar pacientes: " + e.getMessage());
            return null;
        }
    }
}
