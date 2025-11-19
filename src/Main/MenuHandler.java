
package Main;

import java.util.List;
import java.util.Scanner;
import Models.Paciente;
import Models.HistoriaClinica;
import Service.PacienteService;
import Service.HistoriaClinicaService;

/**
 * Controlador del menú para la gestión de Pacientes y sus Historias Clínicas.
 */
public class MenuHandler {
    private final Scanner scanner;
    private final PacienteService pacienteService;
    private final HistoriaClinicaService historiaClinicaService;

    public MenuHandler(Scanner scanner, PacienteService pacienteService, HistoriaClinicaService historiaClinicaService) {
        if (scanner == null) throw new IllegalArgumentException("Scanner no puede ser null");
        if (pacienteService == null) throw new IllegalArgumentException("PacienteService no puede ser null");
        if (historiaClinicaService == null) throw new IllegalArgumentException("HistoriaClinicaService no puede ser null");
        this.scanner = scanner;
        this.pacienteService = pacienteService;
        this.historiaClinicaService = historiaClinicaService;
    }

    /** Opción 1: Crear nuevo paciente */
    public void crearPaciente() {
        try {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine().trim();

            System.out.print("Apellido: ");
            String apellido = scanner.nextLine().trim();

            System.out.print("DNI: ");
            Long dni = Long.parseLong(scanner.nextLine().trim());
            
            System.out.print("Dirección: ");
            String direccion = scanner.nextLine().trim();

            System.out.print("Fecha nacimiento (yyyy-mm-dd): ");
            String fechaStr = scanner.nextLine();
            java.sql.Date fechaNacimiento = java.sql.Date.valueOf(fechaStr);

            HistoriaClinica historia = null;
            System.out.print("¿Desea crear una historia clínica para el paciente? (s/n): ");
            if (scanner.nextLine().equalsIgnoreCase("s")) {
                historia = crearHistoriaClinica();
            }

            Paciente paciente = new Paciente(nombre, apellido, dni, direccion, fechaNacimiento);
            pacienteService.insertar(paciente);
            System.out.println("Paciente creado exitosamente con ID: " + paciente.getIdPaciente());
        } catch (Exception e) {
            System.err.println("Error al crear paciente: " + e.getMessage());
        }
    }

    /** Opción 2: Listar pacientes */
    public void listarPacientes() {
        try {
            List<Paciente> pacientes = pacienteService.getAll();
            if (pacientes.isEmpty()) {
                System.out.println("No se encontraron pacientes.");
                return;
            }

            for (Paciente p : pacientes) {
                System.out.println("ID: " + p.getIdPaciente() + ", " + p.getNombre() + " " + p.getApellido() + " (DNI: " + p.getDni() + ")");
                p.toString();
            }
        } catch (Exception e) {
            System.err.println("Error al listar pacientes: " + e.getMessage());
        }
    }

    /** Opción 3: Actualizar paciente */
    public void actualizarPaciente() {
        try {
            System.out.print("ID del paciente a actualizar: ");
            Long id = Long.parseLong(scanner.nextLine());
            Paciente p = pacienteService.getById(id);

            if (p == null) {
                System.out.println("Paciente no encontrado.");
                return;
            }

            System.out.print("Nuevo nombre (" + p.getNombre() + "): ");
            String nombre = scanner.nextLine().trim();
            if (!nombre.isEmpty()) p.setNombre(nombre);

            System.out.print("Nuevo apellido (" + p.getApellido() + "): ");
            String apellido = scanner.nextLine().trim();
            if (!apellido.isEmpty()) p.setApellido(apellido);

            System.out.print("Nuevo DNI (" + p.getDni() + "): ");
            Long dni = Long.parseLong(scanner.nextLine().trim());
            p.setDni(dni);
                        
            pacienteService.actualizar(p);
            System.out.println("Paciente actualizado correctamente.");
        } catch (Exception e) {
            System.err.println("Error al actualizar paciente: " + e.getMessage());
        }
    }

    /** Opción 4: Eliminar paciente */
    public void eliminarPaciente() {
        try {
            System.out.print("ID del paciente a eliminar: ");
            Long id = Long.parseLong(scanner.nextLine());
            pacienteService.eliminar(id);
            System.out.println("Paciente eliminado exitosamente.");
        } catch (Exception e) {
            System.err.println("Error al eliminar paciente: " + e.getMessage());
        }
    }

    /** Opción 5: Crear historia clínica */
    public HistoriaClinica crearHistoriaClinica() {
        try {
            System.out.println("=== Crear nueva historia clínica ===");
            System.out.print("Diagnóstico: ");
            String diagnostico = scanner.nextLine().trim();

            System.out.print("Tratamiento: ");
            String tratamiento = scanner.nextLine().trim();
            
            java.sql.Date fechaCreacion = java.sql.Date.valueOf(java.time.LocalDate.now());
            
            System.out.print("Ingrese el ID del paciente: ");
            Long idPaciente = Long.parseLong(scanner.nextLine().trim());


            if (diagnostico.isEmpty() || tratamiento.isEmpty()) {
                System.out.println("Error: diagnóstico y tratamiento son obligatorios.");
                return null;
            }

            HistoriaClinica historia = new HistoriaClinica(null, diagnostico, fechaCreacion, idPaciente);
            historiaClinicaService.insertar(historia);
            System.out.println("Historia clínica creada exitosamente con ID: " + historia.getId());
            return historia;
        } catch (Exception e) {
            System.err.println("Error al crear historia clínica: " + e.getMessage());
            return null;
        }
    }

    /** Opción 6: Listar historias clínicas */
    public void listarHistoriaClinica() {
        try {
            List<HistoriaClinica> historias = historiaClinicaService.getAll();

            if (historias.isEmpty()) {
                System.out.println("No se encontraron historias clínicas.");
                return;
            }

            for (HistoriaClinica h : historias) {
                h.toString();
            }
        } catch (Exception e) {
            System.err.println("Error al listar historias clínicas: " + e.getMessage());
        }
    }

    /** Opción 7: Actualizar historia clínica por ID */
    public void actualizarHistoriaClinicaPorId() {
        try {
            System.out.print("ID de la historia clínica a actualizar: ");
            Long id = Long.parseLong(scanner.nextLine());

            HistoriaClinica h = historiaClinicaService.getById(id);
            if (h == null) {
                System.out.println("Historia clínica no encontrada.");
                return;
            }

            System.out.print("Nuevo diagnóstico (actual: " + h.getDiagnostico() + "): ");
            String diagnostico = scanner.nextLine().trim();
            if (!diagnostico.isEmpty()) h.setDiagnostico(diagnostico);

            
            historiaClinicaService.actualizar(h);
            System.out.println("Historia clínica actualizada exitosamente.");
        } catch (Exception e) {
            System.err.println("Error al actualizar historia clínica: " + e.getMessage());
        }
    }

    /** Opción 8: Eliminar historia clínica por ID */
    public void eliminarHistoriaClinicaPorId() {
        try {
            System.out.print("ID de la historia clínica a eliminar: ");
            Long id = Long.parseLong(scanner.nextLine());
            historiaClinicaService.eliminar(id);
            System.out.println("Historia clínica eliminada exitosamente.");
        } catch (Exception e) {
            System.err.println("Error al eliminar historia clínica: " + e.getMessage());
        }
    }

    
}

