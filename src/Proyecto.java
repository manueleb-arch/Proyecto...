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

            // ETAPA 2 y 3: pedir los datos del nuevo usuario y guardarlos
            ValidaciondeDatosparaelUsuario(sc);
        }

        sc.close(); // Cierre del programa
    }

    public static char AceptaciondePoliticas(Scanner politica) {

        char respuesta;

        System.out.println("--- Aceptacion de las Politicas ---");
        System.out.println("Aceptas nuestras politicas de seguridad?");
        System.out.print("Presione 's' para continuar / cualquier otra tecla para salir: ");

        // Lee el texto, toma la primera letra y la convierte a minuscula
        respuesta = politica.next().toLowerCase().charAt(0);

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
        int edad;
        String correo;

        System.out.print("Ingrese su nombre: ");
        nombre = dato.next();

        // Validacion de la edad: se repite hasta que sea un numero razonable
        do {
            System.out.print("Ingrese su edad: ");
            while (!dato.hasNextInt()) {
                System.out.print("Eso no es un numero. Ingrese su edad: ");
                dato.next(); // descarta lo que el usuario escribio mal
            }
            edad = dato.nextInt();

            if (edad < 15 || edad > 100) {
                System.out.println("La edad debe estar entre 15 y 100 anios.");
            }
        } while (edad < 15 || edad > 100);

        // Validacion del correo: debe contener el caracter '@'
        do {
            System.out.print("Ingrese su correo: ");
            correo = dato.next();

            if (correo.indexOf('@') == -1) {
                System.out.println("El correo no es valido, le falta el caracter '@'.");
            }
        } while (correo.indexOf('@') == -1);

        // ETAPA 3: ya que los datos son correctos, se guardan en el archivo
        guardarUsuario(nombre, edad, correo);
    }

    public static void guardarUsuario(String nombre, int edad, String correo) {

        try {

            FileWriter archivo = new FileWriter("usuarios.txt", true);
            BufferedWriter escritor = new BufferedWriter(archivo);

            escritor.write(nombre + ";" + edad + ";" + correo);
            escritor.newLine();

            escritor.close();

            System.out.println("Usuario guardado correctamente.");

        } catch (IOException e) {

            System.out.println("Ocurrio un error al guardar el usuario.");

        }
    }

    public static void mostrarUsuarios() {

        try {

            FileReader archivo = new FileReader("usuarios.txt");
            BufferedReader lector = new BufferedReader(archivo);

            String linea;

            while ((linea = lector.readLine()) != null) {

                System.out.println(linea);
                

            }

            lector.close();

        } catch (IOException e) {

            System.out.println("No hay usuarios registrados.");

        }
    }
    public static void eliminarUsuario(String correoABorrar) {

    ArrayList<String> lineas = new ArrayList<>();
    boolean encontrado = false;

    // PASO 1: leer todo el archivo y guardarlo en memoria
    try {
        FileReader archivo = new FileReader("usuarios.txt");
        BufferedReader lector = new BufferedReader(archivo);
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
        lector.close();

    } catch (IOException e) {
        System.out.println("No hay usuarios registrados.");
        return;
    }

    if (!encontrado) {
        System.out.println("No se encontro ningun usuario con ese correo.");
        return;
    }

    // PASO 3: reescribir el archivo con los que quedaron
    try {
        FileWriter archivo = new FileWriter("usuarios.txt", false);
        BufferedWriter escritor = new BufferedWriter(archivo);

        for (int i = 0; i < lineas.size(); i++) {
            escritor.write(lineas.get(i));
            escritor.newLine();
        }
        escritor.close();

        System.out.println("Usuario eliminado correctamente.");

    } catch (IOException e) {
        System.out.println("Ocurrio un error al eliminar el usuario.");
    }
}
}

   


