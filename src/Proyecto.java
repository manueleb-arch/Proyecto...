import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 *
 * @author manue
 */
public class Proyecto {

    /**
     * 
     * Un programa que ayude a las personas para que puedan realizar un plan de ahorro
     * para poder llegar a una meta de comprar un aparato electronico, un vehiculo, o
     * algun viaje a futuro.
     *
     * Etapa 1 - Bienvenida, politicas de privacidad, aceptacion.  (LISTO)
     * Etapa 2 - Crear usuario, pedir datos, validar datos.        (LISTO)
     * Etapa 3 - Guardar usuarios en usuarios.txt.                 (LISTO)
     * Etapa 4 - Leer usuarios al iniciar y mostrarlos.            (LISTO)
     * Etapa 5 - Crear el plan de ahorro.                          (pendiente)
     * Etapa 6 - Calcular el tiempo para alcanzar la meta.         (pendiente)
     * Etapa 7 - Guardar tambien el plan de ahorro.                (pendiente)
     * 
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Bienvenido al programa de plan de ahorro.");
        System.out.println("===========================================");
        System.out.println();
        System.out.println("A continuacion se te estara pidiendo unos datos");
        System.out.println("personales los cuales son confidenciales.");
        System.out.println();

        System.out.println("===========================================");
        System.out.println("II Aceptacion de Politicas de Privacidad II");
        System.out.println("===========================================");
        System.out.println("Al continuar, aceptas nuestras Politicas de Privacidad y Terminos de Uso.");
        System.out.println("Tus datos e informacion de metas de ahorro se utilizaran unicamente para");
        System.out.println("calcular tus tiempos de meta, proyecciones financieras y facilitarte el");
        System.out.println("seguimiento de tus objetivos (vehiculo, viajes o tecnologia).");
        System.out.println("No compartiremos tu informacion financiera con terceros.");
        System.out.println();

        char respuesta = AceptaciondePoliticas(sc);

        if (respuesta == 's') {
            System.out.println("BIENVENIDO AL PROGRAMA");

            // ETAPA 4: leer el archivo y mostrar los usuarios que ya existen
            System.out.println();
            System.out.println("--- Usuarios registrados hasta el momento ---");
            mostrarUsuarios();
            System.out.println();

            // Menu sencillo para poder usar tambien eliminarUsuario
            boolean salir = false;
            while (!salir) {
                System.out.println("1. Registrar usuario");
                System.out.println("2. Eliminar usuario");
                System.out.println("3. Ver usuarios");
                System.out.println("4. Salir");
                System.out.print("Elija una opcion: ");
                String opcion = sc.nextLine().trim();
                System.out.println();

                if (opcion.equals("1")) {
                    // ETAPA 2 y 3: pedir los datos del nuevo usuario y guardarlos
                    ValidaciondeDatosparaelUsuario(sc);
                } else if (opcion.equals("2")) {
                    System.out.print("Ingrese el correo del usuario a eliminar: ");
                    String correo = sc.nextLine().trim();
                    eliminarUsuario(correo);
                } else if (opcion.equals("3")) {
                    mostrarUsuarios();
                } else if (opcion.equals("4")) {
                    salir = true;
                } else {
                    System.out.println("Opcion no valida.");
                }
                System.out.println();
            }
        }

        sc.close(); // Cierre del programa
    }

    public static char AceptaciondePoliticas(Scanner politica) {

        System.out.println("--- Aceptacion de las Politicas ---");
        System.out.println("Aceptas nuestras politicas de seguridad?");
        System.out.print("Presione 's' para continuar / cualquier otra tecla para salir: ");

        // nextLine() lee la linea completa (incluye el Enter), asi no queda nada
        // pendiente en el buffer. Si el usuario solo presiona Enter, se toma como 'n'.
        String texto = politica.nextLine().trim().toLowerCase();
        char respuesta = texto.isEmpty() ? 'n' : texto.charAt(0);

        if (respuesta == 's') {
            System.out.println();
            System.out.println("Politicas aceptadas.");
            System.out.println("Continuando con el programa...");
        } else {
            System.out.println();
            System.out.println("Politicas no aceptadas.");
            System.out.println("Programa finalizado.");
        }

        return respuesta;
    }

    public static void ValidaciondeDatosparaelUsuario(Scanner dato) {

        String nombre;
        int edad = 0;
        String correo;

        // Nombre: se lee la linea completa para permitir nombres con espacios
        // ("Manuel Lopez"). No puede estar vacio ni tener ';' porque es el separador.
        do {
            System.out.print("Ingrese su nombre: ");
            nombre = dato.nextLine().trim();

            if (nombre.isEmpty() || nombre.contains(";")) {
                System.out.println("El nombre no puede estar vacio ni contener ';'.");
            }
        } while (nombre.isEmpty() || nombre.contains(";"));

        // Edad: se lee como texto y se convierte; si falla, se avisa y se repite
        boolean edadValida = false;
        while (!edadValida) {
            System.out.print("Ingrese su edad: ");
            String texto = dato.nextLine().trim();

            try {
                edad = Integer.parseInt(texto);

                if (edad < 15 || edad > 100) {
                    System.out.println("La edad debe estar entre 15 y 100 anios.");
                } else {
                    edadValida = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Eso no es un numero.");
            }
        }

        // Correo: formato basico valido y que no este repetido
        boolean correoValido = false;
        do {
            System.out.print("Ingrese su correo: ");
            correo = dato.nextLine().trim();

            if (!esCorreoValido(correo)) {
                System.out.println("El correo no es valido (ejemplo: usuario@dominio.com).");
            } else if (existeCorreo(correo)) {
                System.out.println("Ese correo ya esta registrado.");
            } else {
                correoValido = true;
            }
        } while (!correoValido);

        // ETAPA 3: ya que los datos son correctos, se guardan en el archivo
        guardarUsuario(nombre, edad, correo);
    }

    // Revisa que el correo tenga algo antes de '@', un '.' despues, y sin espacios ni ';'
    public static boolean esCorreoValido(String correo) {
        int arroba = correo.indexOf('@');

        return arroba > 0
                && correo.indexOf('.', arroba) > arroba + 1
                && !correo.endsWith(".")
                && !correo.contains(" ")
                && !correo.contains(";");
    }

    // Busca en usuarios.txt si ya existe ese correo
    public static boolean existeCorreo(String correo) {

        try (BufferedReader lector = new BufferedReader(new FileReader("usuarios.txt"))) {

            String linea;

            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(";");

                if (partes.length >= 3 && partes[2].equalsIgnoreCase(correo)) {
                    return true;
                }
            }

        } catch (IOException e) {
            // Si el archivo no existe todavia, no hay correos repetidos
        }

        return false;
    }

    public static void guardarUsuario(String nombre, int edad, String correo) {

        // try-with-resources: el archivo se cierra solo, incluso si ocurre un error
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter("usuarios.txt", true))) {

            escritor.write(nombre + ";" + edad + ";" + correo);
            escritor.newLine();

            System.out.println("Usuario guardado correctamente.");

        } catch (IOException e) {

            System.out.println("Ocurrio un error al guardar el usuario.");

        }
    }

    public static void mostrarUsuarios() {

        try (BufferedReader lector = new BufferedReader(new FileReader("usuarios.txt"))) {

            String linea;
            boolean hayUsuarios = false;

            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(";");

                if (partes.length >= 3) {
                    System.out.println("Nombre: " + partes[0]
                            + " | Edad: " + partes[1]
                            + " | Correo: " + partes[2]);
                    hayUsuarios = true;
                }
            }

            if (!hayUsuarios) {
                System.out.println("No hay usuarios registrados.");
            }

        } catch (IOException e) {

            System.out.println("No hay usuarios registrados.");

        }
    }

    public static void eliminarUsuario(String correoABorrar) {

        ArrayList<String> lineas = new ArrayList<>();
        boolean encontrado = false;

        // PASO 1: leer todo el archivo y guardarlo en memoria
        try (BufferedReader lector = new BufferedReader(new FileReader("usuarios.txt"))) {

            String linea;

            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(";");

                // PASO 2: si el correo coincide, NO lo agregamos a la lista
                if (partes.length >= 3 && partes[2].equalsIgnoreCase(correoABorrar)) {
                    encontrado = true;
                } else {
                    lineas.add(linea);
                }
            }

        } catch (IOException e) {
            System.out.println("No hay usuarios registrados.");
            return;
        }

        if (!encontrado) {
            System.out.println("No se encontro ningun usuario con ese correo.");
            return;
        }

        // PASO 3: reescribir el archivo con los que quedaron
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter("usuarios.txt", false))) {

            for (int i = 0; i < lineas.size(); i++) {
                escritor.write(lineas.get(i));
                escritor.newLine();
            }

            System.out.println("Usuario eliminado correctamente.");

        } catch (IOException e) {
            System.out.println("Ocurrio un error al eliminar el usuario.");
        }
    }
}