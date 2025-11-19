/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

/**
 * Punto de entrada de la aplicación Java.
 * Crea AppMenu y ejecuta el menú principal.
 *
 * Flujo:
 * 1. Crea instancia de AppMenu (inicializa toda la aplicación)
 * 2. Llama a app.run() que ejecuta el loop del menú
 * 3. Cuando el usuario sale (opción 0), run() termina y la aplicación finaliza
 */
public class MainTPint {

    public static void main(String[] args) {
        AppMenu app = new AppMenu();
        app.run();
    }
}
