/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

/**
 *
 * @author AMD-PC
 */
import java.util.Scanner;
import DAO.HistoriaClinicaDao;
import DAO.PacienteDAO;
import Service.HistoriaClinicaService;
import Service.PacienteService;
import Config.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Orquestador principal del menú de la aplicación.
 * Gestiona el ciclo de vida del menú y coordina todas las dependencias.
 *
 * Responsabilidades:
 * - Crear y gestionar el Scanner único (evita múltiples instancias de System.in)
 * - Inicializar toda la cadena de dependencias (DAOs → Services → Handler)
 * - Ejecutar el loop principal del menú
 * - Manejar la selección de opciones y delegarlas a MenuHandler
 * - Cerrar recursos al salir (Scanner)
 *
 * Patrón: Application Controller + Dependency Injection manual
 * Arquitectura: Punto de entrada que ensambla las 4 capas (Main → Service → DAO → Models)
 *
 * IMPORTANTE: Esta clase NO tiene lógica de negocio ni de UI.
 * Solo coordina y delega.
 */
public class AppMenu {
    /**
     * Scanner único compartido por toda la aplicación.
     * IMPORTANTE: Solo debe haber UNA instancia de Scanner(System.in).
     * Múltiples instancias causan problemas de buffering de entrada.
     */
    private final Scanner scanner;

    /**
     * Handler que ejecuta las operaciones del menú.
     * Contiene toda la lógica de interacción con el usuario.
     */
    private final MenuHandler menuHandler;

    /**
     * Flag que controla el loop principal del menú.
     * Se setea a false cuando el usuario selecciona "0 - Salir".
     */
    private boolean running;

    /**
     * Constructor que inicializa la aplicación.
     *
     * Flujo de inicialización:
     * 1. Crea Scanner único para toda la aplicación
     * 2. Crea cadena de dependencias (DAOs → Services) mediante createPersonaService()
     * 3. Crea MenuHandler con Scanner y PersonaService
     * 4. Setea running=true para iniciar el loop
     *
     * Patrón de inyección de dependencias (DI) manual:
     * - DomicilioDAO (sin dependencias)
     * - PersonaDAO (depende de DomicilioDAO)
     * - DomicilioServiceImpl (depende de DomicilioDAO)
     * - PersonaServiceImpl (depende de PersonaDAO y DomicilioServiceImpl)
     * - MenuHandler (depende de Scanner y PersonaServiceImpl)
     *
     * Esta inicialización garantiza que todas las dependencias estén correctamente conectadas.
     */
    public AppMenu() {
    this.scanner = new Scanner(System.in);

    try {
        // 1) Obtener conexión a la base de datos
        Connection conn = DatabaseConnection.getConnection();

        // 2) Crear DAOs
        PacienteDAO pacienteDAO = new PacienteDAO(conn);
        HistoriaClinicaDao historiaClinicaDao = new HistoriaClinicaDao(conn);

        // 3) Crear Services
        PacienteService pacienteService = new PacienteService(conn, pacienteDAO);
        HistoriaClinicaService historiaClinicaService = new HistoriaClinicaService(conn, historiaClinicaDao);

        // 4) Crear MenuHandler con todas las dependencias
        this.menuHandler = new MenuHandler(scanner, pacienteService, historiaClinicaService);

        // 5) Iniciar el loop
        this.running = true;

    } catch (SQLException e) {
        // Si falla la conexión, no tiene sentido seguir
        throw new RuntimeException("Error al inicializar la aplicación: " + e.getMessage(), e);
    }
}

    /**
     * Punto de entrada de la aplicación Java.
     * Crea instancia de AppMenu y ejecuta el menú principal.
     *
     * @param args Argumentos de línea de comandos (no usados)
     */
    public static void main(String[] args) {
        AppMenu app = new AppMenu();
        app.run();
    }

    /**
     * Loop principal del menú.
     *
     * Flujo:
     * 1. Mientras running==true:
     *    a. Muestra menú con MenuDisplay.mostrarMenuPrincipal()
     *    b. Lee opción del usuario (scanner.nextLine())
     *    c. Convierte a int (puede lanzar NumberFormatException)
     *    d. Procesa opción con processOption()
     * 2. Si el usuario ingresa texto no numérico: Muestra mensaje de error y continúa
     * 3. Cuando running==false (opción 0): Sale del loop y cierra Scanner
     *
     * Manejo de errores:
     * - NumberFormatException: Captura entrada no numérica (ej: "abc")
     * - Muestra mensaje amigable y NO termina la aplicación
     * - El usuario puede volver a intentar
     *
     * IMPORTANTE: El Scanner se cierra al salir del loop.
     * Cerrar Scanner(System.in) cierra System.in para toda la aplicación.
     */
    public void run() {
        while (running) {
            try {
                MenuDisplay.mostrarMenuPrincipal();
                int opcion = Integer.parseInt(scanner.nextLine());
                processOption(opcion);
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Por favor, ingrese un numero.");
            }
        }
        scanner.close();
    }

    /**
     * Procesa la opción seleccionada por el usuario y delega a MenuHandler.
     *
     * Switch expression (Java 14+) con operador arrow (->):
     * - Más conciso que switch tradicional
     * - No requiere break (cada caso es independiente)
     * - Permite bloques con {} para múltiples statements
     *
     * Mapeo de opciones (corresponde a MenuDisplay):
     * 1  → crearPaciente(): Registrar nuevo paciente (con historia clínica opcional)
     * 2  → listarPacientes(): Listar pacientes (todos o filtrados por nombre/apellido)
     * 3  → actualizarPaciente(): Actualizar datos de un paciente existente
     * 4  → eliminarPaciente(): Eliminar paciente (soft delete)
     * 5  → crearHistoriaClinicaIndependiente(): Crear historia clínica independiente (sin paciente asociado)
     * 6  → listarHistoriasClinicas(): Listar todas las historias clínicas registradas
     * 7  → actualizarHistoriaClinicaPorId(): Actualizar historia clínica por ID (afecta al paciente asociado)
     * 8  → eliminarHistoriaClinicaPorId(): Eliminar historia clínica por ID (PELIGRO: puede dejar pacientes sin historia clínica asociada)
     * 9  → actualizarHistoriaClinicaPorPaciente(): Actualizar historia clínica de un paciente (seguro: afecta solo a ese paciente)
     * 10 → eliminarHistoriaClinicaPorPaciente(): Eliminar historia clínica de un paciente (seguro: desasocia primero, luego elimina)
     * 0  → Salir: Finaliza la ejecución del menú (running = false)
     *
     * Opción inválida: Muestra mensaje y continúa el loop.
     *
     * IMPORTANTE: Todas las excepciones de MenuHandler se capturan dentro de los métodos.
     * processOption() NO propaga excepciones al caller (run()).
     *
     * @param opcion Número de opción ingresado por el usuario
     */
    private void processOption(int opcion) {
        switch (opcion) {
            case 1 -> menuHandler.crearPaciente();
            case 2 -> menuHandler.listarPacientes();
            case 3 -> menuHandler.actualizarPaciente();
            case 4 -> menuHandler.eliminarPaciente();
            case 5 -> menuHandler.crearHistoriaClinica();
            case 6 -> menuHandler.listarHistoriaClinica();
            case 7 -> menuHandler.actualizarHistoriaClinicaPorId();
            case 8 -> menuHandler.eliminarHistoriaClinicaPorId();
            case 0 -> {
                System.out.println("Saliendo...");
                running = false;
            }
            default -> System.out.println("Opcion no valida.");
        }
    }

    
}