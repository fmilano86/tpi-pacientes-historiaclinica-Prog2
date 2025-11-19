/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Service;

import DAO.HistoriaClinicaDao;
import Models.HistoriaClinica;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author Usuario
 */
public class HistoriaClinicaService implements GenericService<HistoriaClinica>{
    private Connection conn;
    private HistoriaClinicaDao historiaClinicaDao;

    public HistoriaClinicaService(Connection conn, HistoriaClinicaDao historiaClinicaDao) {
        this.conn = conn;
        this.historiaClinicaDao = historiaClinicaDao;
    }

    @Override
    public void insertar(HistoriaClinica entidad) {
        if (entidad == null)
            throw new IllegalArgumentException("La historia clínica no puede ser nula.");

        try {
            conn.setAutoCommit(false);

            historiaClinicaDao.crear(entidad);

            conn.commit();
            System.out.println(" Historia clínica insertada correctamente.");

        } catch (SQLException e) {
            System.err.println(" Error al insertar historia clínica: " + e.getMessage());
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
    public void actualizar(HistoriaClinica entidad) {
        if (entidad == null)
            throw new IllegalArgumentException("La historia clínica no puede ser nula.");

        try {
            conn.setAutoCommit(false);

            historiaClinicaDao.actualizar(entidad);

            conn.commit();
            System.out.println(" Historia clínica actualizada correctamente.");

        } catch (SQLException e) {
            System.err.println(" Error al actualizar historia clínica: " + e.getMessage());
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

            historiaClinicaDao.eliminar(id);

            conn.commit();
            System.out.println(" Historia clínica eliminada correctamente.");

        } catch (SQLException e) {
            System.err.println(" Error al eliminar historia clínica: " + e.getMessage());
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
    public HistoriaClinica getById(Long id) {
        try {
            return historiaClinicaDao.leer(id);
        } catch (SQLException e) {
            System.err.println(" Error al leer historia clínica: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<HistoriaClinica> getAll() {
        try {
            return historiaClinicaDao.leerTodos();
        } catch (SQLException e) {
            System.err.println(" Error al listar historias clínicas: " + e.getMessage());
            return null;
        }
    }

}
