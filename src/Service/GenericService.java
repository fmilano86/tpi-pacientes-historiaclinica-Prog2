/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Service;

import java.util.List;

/**
 *
 * @author Usuario
 */
public interface GenericService<T> {
        // Inserta una nueva entidad
    void insertar(T entidad);

    // Actualiza una entidad existente
    void actualizar(T entidad);

    // Elimina una entidad por ID
    void eliminar(Long id);

    // Obtiene una entidad por su ID
    T getById(Long id);

    // Obtiene todas las entidades activas
    List<T> getAll();
}
