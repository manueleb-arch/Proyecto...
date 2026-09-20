import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 *
 * @author Joseph Castro
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
     * Etapa 5 - Crear el plan de ahorro.                          (LISTO)
     * Etapa 6 - Calcular el tiempo para alcanzar la meta.         (LISTO)
     * Etapa 7 - Guardar tambien el plan de ahorro.                (LISTO)
     * 
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // ETAPA 5, 6 y 7: aqui se va armando el plan.
        // Al crearlo (Etapa 5) trae 6 datos:
        //    correo;tipoMeta;costoTotal;ahorroInicial;ingresoMensual;tipoCalculo
        // Al calcularlo (Etapa 6) se le agregan 2 datos mas:
        //    ...;meses;cuotaMensual
        String planAhorro = "";

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
                System.out.println("4. Crear plan de ahorro");
                System.out.println("5. Calcular el plan de ahorro");
                System.out.println("6. Guardar el plan de ahorro");
                System.out.println("7. Ver los planes de un usuario");
                System.out.println("8. Salir");
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
                    // ETAPA 5: se crea el plan y se guarda temporalmente en memoria
                    planAhorro = CrearPlanAhorro(sc);
                } else if (opcion.equals("5")) {
                    // ETAPA 6: se hacen los calculos sobre el plan creado
                    planAhorro = CalcularPlanAhorro(sc, planAhorro);
                } else if (opcion.equals("6")) {
                    // ETAPA 7: se manda el plan ya calculado al archivo planes.txt
                    GuardarPlan(planAhorro);
                } else if (opcion.equals("7")) {
                    // ETAPA 7: se muestran los planes vinculados a un correo
                    MostrarPlanesPorCorreo(sc);
                } else if (opcion.equals("8")) {
                    salir = true;
                } else {
                    System.out.println("Opcion no valida.");
                }
                System.out.println();
            }
        }

        sc.close(); // Cierre del programa
    }//Fin de main

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
    }//Fin de Funcion 

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
    }//Fin de Funcion 

    // Revisa que el correo tenga algo antes de '@', un '.' despues, y sin espacios ni ';'
    public static boolean esCorreoValido(String correo) {
        int arroba = correo.indexOf('@');

        return arroba > 0
                && correo.indexOf('.', arroba) > arroba + 1
                && !correo.endsWith(".")
                && !correo.contains(" ")
                && !correo.contains(";");
    }//Fin de funcion 

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
    }//Fin de Funcion 

    public static void guardarUsuario(String nombre, int edad, String correo) {

        // try-with-resources: el archivo se cierra solo, incluso si ocurre un error
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter("usuarios.txt", true))) {

            escritor.write(nombre + ";" + edad + ";" + correo);
            escritor.newLine();

            System.out.println("Usuario guardado correctamente.");

        } catch (IOException e) {

            System.out.println("Ocurrio un error al guardar el usuario.");

        }
    }//Fin de Funcion 

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
    }//Fin de Funcion 

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
    }//Fin de funcion 

    /*
     * ===========================================================
     *                        ETAPA 5
     *              CREACION DEL PLAN DE AHORRO
     * ===========================================================
     */

    /**
     * Funcion principal de la Etapa 5. Captura y valida todos los datos de la
     * meta de ahorro y los devuelve en una sola cadena separada por ';', con el
     * mismo formato que se usa en usuarios.txt.
     *
     * Formato: correo;tipoMeta;costoTotal;ahorroInicial;ingresoMensual;tipoCalculo
     *
     * @param sc Instancia de la libreria Scanner que viene desde el main
     * @return String Devuelve el plan armado, o una cadena vacia si se cancela
     * @see SeleccionarTipoMeta
     * @see ValidarMontoPositivo
     */
    public static String CrearPlanAhorro(Scanner sc) {

        String correo = "";
        String tipoMeta = "";
        double costoTotal = 0;
        double ahorroInicial = 0;
        double ingresoMensual = 0;
        int tipoCalculo = 0;
        String plan = "";

        System.out.println("===========================================");
        System.out.println("        CREACION DEL PLAN DE AHORRO        ");
        System.out.println("===========================================");

        // 1. A quien pertenece el plan (se usa la funcion existeCorreo de la Etapa 3)
        correo = SolicitarCorreoRegistrado(sc);

        if (correo.isEmpty()) {
            System.out.println("Creacion del plan cancelada.");
            return "";
        }

        // 2. Tipo de meta: electronico, vehiculo o viaje
        tipoMeta = SeleccionarTipoMeta(sc);

        // 3. Montos de la meta
        costoTotal = ValidarMontoPositivo(sc, "Ingrese el costo total de la meta (Lps.): ");
        ahorroInicial = ValidarAhorroInicial(sc, costoTotal);
        ingresoMensual = ValidarMontoPositivo(sc, "Ingrese el monto mensual que puede ahorrar (Lps.): ");

        // 4. Que quiere calcular despues (esto se usa en la Etapa 6)
        tipoCalculo = SeleccionarTipoCalculo(sc);

        // 5. Se arma la linea del plan con el separador ';'
        plan = correo + ";"
                + tipoMeta + ";"
                + costoTotal + ";"
                + ahorroInicial + ";"
                + ingresoMensual + ";"
                + tipoCalculo;

        MostrarResumenPlan(plan);

        return plan;
    }//Fin de Funcion

    /**
     * Pide el correo del dueno del plan y verifica que ya este registrado.
     *
     * @param sc Instancia de la libreria Scanner
     * @return String El correo validado, o cadena vacia si el usuario cancela
     */
    public static String SolicitarCorreoRegistrado(Scanner sc) {

        String correo = "";
        boolean correoValido = false;

        do {
            System.out.print("Ingrese el correo del usuario dueno del plan (0 para cancelar): ");
            correo = sc.nextLine().trim();

            if (correo.equals("0")) {
                return "";
            }

            if (!existeCorreo(correo)) {
                System.out.println("Ese correo no esta registrado.");
                System.out.println("Registre primero al usuario en la opcion 1 del menu.");
            } else {
                correoValido = true;
            }
        } while (!correoValido);

        return correo;
    }//Fin de Funcion

    /**
     * Muestra el menu de tipos de meta y devuelve la opcion elegida ya escrita
     * en texto, para que se guarde bonito en el archivo.
     *
     * @param sc Instancia de la libreria Scanner
     * @return String El tipo de meta seleccionado
     */
    public static String SeleccionarTipoMeta(Scanner sc) {

        String tipoMeta = "";
        String opcion = "";

        do {
            System.out.println("-------------------------------------------");
            System.out.println("         Que tipo de meta desea?           ");
            System.out.println("-------------------------------------------");
            System.out.println("1. Aparato electronico");
            System.out.println("2. Vehiculo");
            System.out.println("3. Viaje");
            System.out.print("Elija el tipo de meta: ");
            opcion = sc.nextLine().trim();

            switch (opcion) {
                case "1":
                    tipoMeta = "Aparato electronico";
                    break;
                case "2":
                    tipoMeta = "Vehiculo";
                    break;
                case "3":
                    tipoMeta = "Viaje";
                    break;
                default:
                    System.out.println("Opcion no valida, intente de nuevo.");
            }//Fin de Switch

        } while (tipoMeta.isEmpty());

        return tipoMeta;
    }//Fin de Funcion

    /**
     * Captura un monto en Lempiras y valida que sea un numero mayor a 0. Como
     * recibe el mensaje por parametro, sirve para el costo total y tambien para
     * el ingreso mensual.
     *
     * @param sc Instancia de la libreria Scanner
     * @param mensaje El texto que se le muestra al usuario antes de capturar
     * @return double El monto ya validado
     */
    public static double ValidarMontoPositivo(Scanner sc, String mensaje) {

        double monto = 0;
        String texto = "";
        boolean montoValido = false;

        do {
            System.out.print(mensaje);
            texto = sc.nextLine().trim();

            try {
                monto = Double.parseDouble(texto);

                if (monto <= 0) {
                    System.out.println("El monto debe ser mayor a 0.");
                } else {
                    montoValido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Eso no es un numero valido.");
            }

        } while (!montoValido);

        return monto;
    }//Fin de Funcion

    /**
     * Captura el ahorro inicial. Aqui si se permite el 0 (alguien puede empezar
     * de cero), pero no puede ser negativo ni mayor o igual al costo de la meta
     * porque entonces ya no habria nada que ahorrar.
     *
     * @param sc Instancia de la libreria Scanner
     * @param costoTotal El costo de la meta, para comparar contra el ahorro
     * @return double El ahorro inicial ya validado
     */
    public static double ValidarAhorroInicial(Scanner sc, double costoTotal) {

        double ahorro = 0;
        String texto = "";
        boolean ahorroValido = false;

        do {
            System.out.print("Ingrese el ahorro inicial que ya tiene (0 si no tiene): Lps. ");
            texto = sc.nextLine().trim();

            try {
                ahorro = Double.parseDouble(texto);

                if (ahorro < 0) {
                    System.out.println("El ahorro inicial no puede ser negativo.");
                } else if (ahorro >= costoTotal) {
                    System.out.printf("Su ahorro ya cubre el costo de %.2f Lps.\n", costoTotal);
                    System.out.println("Ingrese un monto menor al costo de la meta.");
                } else {
                    ahorroValido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Eso no es un numero valido.");
            }

        } while (!ahorroValido);

        return ahorro;
    }//Fin de Funcion

    /**
     * Pregunta que calculo quiere realizar el usuario. Este dato se utiliza en
     * la Etapa 6 para decidir que formula se aplica.
     *
     * @param sc Instancia de la libreria Scanner
     * @return int 1 = calcular el tiempo / 2 = calcular la cuota mensual
     */
    public static int SeleccionarTipoCalculo(Scanner sc) {

        int tipoCalculo = 0;
        String opcion = "";
        boolean opcionValida = false;

        do {
            System.out.println("-------------------------------------------");
            System.out.println("          Que desea calcular?              ");
            System.out.println("-------------------------------------------");
            System.out.println("1. El tiempo necesario para alcanzar la meta");
            System.out.println("2. La cuota mensual necesaria");
            System.out.print("Elija una opcion: ");
            opcion = sc.nextLine().trim();

            if (opcion.equals("1") || opcion.equals("2")) {
                tipoCalculo = Integer.parseInt(opcion);
                opcionValida = true;
            } else {
                System.out.println("Opcion no valida, intente de nuevo.");
            }

        } while (!opcionValida);

        return tipoCalculo;
    }//Fin de Funcion

    /**
     * Imprime en pantalla el resumen del plan recien creado. Recibe la cadena
     * completa y la separa con split(';'), igual que se hace con los usuarios.
     *
     * @param plan La cadena del plan con sus 6 datos separados por ';'
     */
    public static void MostrarResumenPlan(String plan) {

        String[] partes = plan.split(";");
        String tipoMeta = "";
        double costoTotal = 0;
        double ahorroInicial = 0;
        double ingresoMensual = 0;
        double faltante = 0;

        if (partes.length < 6) {
            System.out.println("No hay ningun plan para mostrar.");
            return;
        }

        tipoMeta = partes[1];
        costoTotal = Double.parseDouble(partes[2]);
        ahorroInicial = Double.parseDouble(partes[3]);
        ingresoMensual = Double.parseDouble(partes[4]);
        faltante = MontoRestante(costoTotal, ahorroInicial);

        System.out.println();
        System.out.println("===========================================");
        System.out.println("           RESUMEN DE SU PLAN              ");
        System.out.println("===========================================");
        System.out.printf("Usuario          : %s\n", partes[0]);
        System.out.printf("Tipo de meta     : %s\n", tipoMeta);
        System.out.printf("Costo total      : Lps. %.2f\n", costoTotal);
        System.out.printf("Ahorro inicial   : Lps. %.2f\n", ahorroInicial);
        System.out.printf("Ahorro mensual   : Lps. %.2f\n", ingresoMensual);
        System.out.printf("Falta por ahorrar: Lps. %.2f\n", faltante);

        if (partes[5].equals("1")) {
            System.out.println("Calculo elegido  : Tiempo necesario");
        } else {
            System.out.println("Calculo elegido  : Cuota mensual necesaria");
        }

        System.out.println("===========================================");
        System.out.println("Plan creado correctamente.");
        System.out.println("Use la opcion 5 del menu para calcularlo.");
    }//Fin de Funcion

    /*
     * ===========================================================
     *                        ETAPA 6
     *          CALCULO DEL TIEMPO O DE LA CUOTA MENSUAL
     * ===========================================================
     */

    /**
     * Funcion principal de la Etapa 6. Toma el plan creado en la Etapa 5, le
     * aplica las formulas y le agrega al final los dos resultados: los meses y
     * la cuota mensual.
     *
     * Formato final:
     * correo;tipoMeta;costoTotal;ahorroInicial;ingresoMensual;tipoCalculo;meses;cuota
     *
     * @param sc Instancia de la libreria Scanner que viene desde el main
     * @param plan El plan creado en la Etapa 5
     * @return String El plan ya con los resultados agregados
     * @see MesesNecesarios
     * @see CuotaMensual
     */
    public static String CalcularPlanAhorro(Scanner sc, String plan) {

        String[] partes = plan.split(";");
        double costoTotal = 0;
        double ahorroInicial = 0;
        double ingresoMensual = 0;
        double montoRestante = 0;
        double cuotaMensual = 0;
        int tipoCalculo = 0;
        int meses = 0;
        String planCalculado = "";

        // Validacion: primero tiene que existir un plan creado
        if (plan.isEmpty() || partes.length < 6) {
            System.out.println("Todavia no ha creado ningun plan.");
            System.out.println("Use la opcion 4 del menu para crearlo primero.");
            return plan;
        }

        costoTotal = Double.parseDouble(partes[2]);
        ahorroInicial = Double.parseDouble(partes[3]);
        ingresoMensual = Double.parseDouble(partes[4]);
        tipoCalculo = Integer.parseInt(partes[5]);

        System.out.println("===========================================");
        System.out.println("         CALCULO DEL PLAN DE AHORRO        ");
        System.out.println("===========================================");

        // Este calculo se ocupa en los dos casos
        montoRestante = MontoRestante(costoTotal, ahorroInicial);

        if (tipoCalculo == 1) {

            // CASO 1: se sabe cuanto puede ahorrar al mes y se busca el tiempo
            meses = MesesNecesarios(montoRestante, ingresoMensual);
            cuotaMensual = ingresoMensual;

        } else {

            // CASO 2: la persona pone el plazo y se busca la cuota mensual
            meses = ValidarPlazoMeses(sc);
            cuotaMensual = CuotaMensual(montoRestante, meses);

        }

        // Si alguna formula devolvio -1 fue porque hubo un error de division
        if (meses <= 0 || cuotaMensual <= 0) {
            System.out.println("No se pudo realizar el calculo.");
            System.out.println("Revise que el monto mensual y el plazo sean mayores a 0.");
            return plan;
        }

        // Se le pegan los dos resultados al final del plan
        planCalculado = plan + ";" + meses + ";" + cuotaMensual;

        MostrarResultadoCalculo(planCalculado);

        return planCalculado;
    }//Fin de Funcion

    /**
     * Calcula cuanto dinero falta por ahorrar para llegar a la meta.
     *
     * @param costoTotal El precio total de la meta
     * @param ahorroInicial El dinero que la persona ya tiene guardado
     * @return double El monto que todavia falta por reunir
     */
    public static double MontoRestante(double costoTotal, double ahorroInicial) {

        double restante = 0;

        restante = costoTotal - ahorroInicial;

        // Si por alguna razon el ahorro fuera mayor a la meta, se devuelve 0
        if (restante < 0) {
            restante = 0;
        }

        return restante;
    }//Fin de Funcion

    /**
     * Calcula cuantos meses se necesitan para reunir el monto restante
     * ahorrando una cuota fija cada mes.
     *
     * Se usa Math.ceil() porque los meses no se parten a la mitad: si el
     * resultado da 4.2 meses, en realidad se necesitan 5 meses.
     *
     * @param montoRestante El dinero que falta por ahorrar
     * @param cuotaMensual Lo que la persona puede ahorrar cada mes
     * @return int Los meses necesarios, o -1 si ocurrio un error de calculo
     */
    public static int MesesNecesarios(double montoRestante, double cuotaMensual) {

        int meses = 0;

        try {

            // Si la cuota fuera 0 se estaria dividiendo entre cero, por eso se
            // lanza la excepcion a proposito y se atrapa aqui mismo abajo.
            if (cuotaMensual <= 0) {
                throw new ArithmeticException("La cuota mensual no puede ser 0");
            }

            meses = (int) Math.ceil(montoRestante / cuotaMensual);

        } catch (ArithmeticException e) {

            System.out.println("Error de calculo: no se puede dividir entre cero.");
            meses = -1;

        }

        return meses;
    }//Fin de Funcion

    /**
     * Calcula cuanto tendria que ahorrar la persona cada mes para alcanzar la
     * meta dentro de un plazo definido.
     *
     * @param montoRestante El dinero que falta por ahorrar
     * @param meses El plazo en meses que la persona se puso de limite
     * @return double La cuota mensual necesaria, o -1 si ocurrio un error
     */
    public static double CuotaMensual(double montoRestante, int meses) {

        double cuota = 0;

        try {

            // Misma proteccion: un plazo de 0 meses seria division entre cero
            if (meses <= 0) {
                throw new ArithmeticException("El plazo no puede ser 0 meses");
            }

            cuota = montoRestante / meses;

        } catch (ArithmeticException e) {

            System.out.println("Error de calculo: no se puede dividir entre cero.");
            cuota = -1;

        }

        return cuota;
    }//Fin de Funcion

    /**
     * Calcula el porcentaje de la meta que la persona ya tiene ahorrado.
     *
     * @param costoTotal El precio total de la meta
     * @param ahorroInicial El dinero que ya tiene guardado
     * @return double El avance en porcentaje, o 0 si ocurrio un error
     */
    public static double PorcentajeAvance(double costoTotal, double ahorroInicial) {

        double porcentaje = 0;

        try {

            if (costoTotal <= 0) {
                throw new ArithmeticException("El costo total no puede ser 0");
            }

            porcentaje = (ahorroInicial / costoTotal) * 100;

        } catch (ArithmeticException e) {

            porcentaje = 0;

        }

        return porcentaje;
    }//Fin de Funcion

    /**
     * Captura el plazo en meses cuando la persona quiere saber cuanto tiene que
     * ahorrar cada mes. Se valida que sea entero y mayor a 0.
     *
     * @param sc Instancia de la libreria Scanner
     * @return int El plazo en meses ya validado
     */
    public static int ValidarPlazoMeses(Scanner sc) {

        int meses = 0;
        String texto = "";
        boolean plazoValido = false;

        do {
            System.out.print("En cuantos meses desea alcanzar la meta?: ");
            texto = sc.nextLine().trim();

            try {
                meses = Integer.parseInt(texto);

                if (meses <= 0) {
                    System.out.println("El plazo debe ser mayor a 0 meses.");
                } else if (meses > 600) {
                    System.out.println("El plazo no puede ser mayor a 600 meses (50 anios).");
                } else {
                    plazoValido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Eso no es un numero entero valido.");
            }

        } while (!plazoValido);

        return meses;
    }//Fin de Funcion

    /**
     * Convierte una cantidad de meses en un texto mas facil de leer para la
     * persona, por ejemplo: 27 meses se convierte en "2 anio(s) y 3 mes(es)".
     *
     * @param meses La cantidad total de meses
     * @return String El tiempo escrito en anios y meses
     */
    public static String ConvertirMesesATexto(int meses) {

        int anios = 0;
        int mesesRestantes = 0;
        String texto = "";

        anios = meses / 12;
        mesesRestantes = meses % 12;

        if (anios == 0) {
            texto = mesesRestantes + " mes(es)";
        } else if (mesesRestantes == 0) {
            texto = anios + " anio(s)";
        } else {
            texto = anios + " anio(s) y " + mesesRestantes + " mes(es)";
        }

        return texto;
    }//Fin de Funcion

    /**
     * Imprime el resultado final del calculo en pantalla.
     *
     * @param plan El plan completo con sus 8 datos separados por ';'
     */
    public static void MostrarResultadoCalculo(String plan) {

        String[] partes = plan.split(";");
        double costoTotal = 0;
        double ahorroInicial = 0;
        double montoRestante = 0;
        double cuotaMensual = 0;
        double porcentaje = 0;
        int meses = 0;

        if (partes.length < 8) {
            System.out.println("Este plan todavia no ha sido calculado.");
            return;
        }

        costoTotal = Double.parseDouble(partes[2]);
        ahorroInicial = Double.parseDouble(partes[3]);
        meses = Integer.parseInt(partes[6]);
        cuotaMensual = Double.parseDouble(partes[7]);

        montoRestante = MontoRestante(costoTotal, ahorroInicial);
        porcentaje = PorcentajeAvance(costoTotal, ahorroInicial);

        System.out.println();
        System.out.println("===========================================");
        System.out.println("          RESULTADO DE SU PLAN             ");
        System.out.println("===========================================");
        System.out.printf("Usuario            : %s\n", partes[0]);
        System.out.printf("Meta               : %s\n", partes[1]);
        System.out.printf("Costo total        : Lps. %.2f\n", costoTotal);
        System.out.printf("Ahorro inicial     : Lps. %.2f (%.1f%% de la meta)\n", ahorroInicial, porcentaje);
        System.out.printf("Falta por ahorrar  : Lps. %.2f\n", montoRestante);
        System.out.println("-------------------------------------------");

        if (partes[5].equals("1")) {
            System.out.printf("Ahorrando Lps. %.2f cada mes,\n", cuotaMensual);
            System.out.printf("alcanzara su meta en %d meses.\n", meses);
        } else {
            System.out.printf("Para lograr su meta en %d meses,\n", meses);
            System.out.printf("debe ahorrar Lps. %.2f cada mes.\n", cuotaMensual);
        }

        System.out.printf("Eso equivale a %s.\n", ConvertirMesesATexto(meses));
        System.out.println("===========================================");
    }//Fin de Funcion

    /*
     * ===========================================================
     *                        ETAPA 7
     *        GUARDAR Y CONSULTAR LOS PLANES EN planes.txt
     * ===========================================================
     */

    /**
     * Funcion principal de la Etapa 7. Guarda el plan ya calculado en el
     * archivo planes.txt. Se usa el modo append (true) para que los planes
     * anteriores no se borren, igual que en guardarUsuario.
     *
     * @param plan El plan completo con sus 8 datos separados por ';'
     */
    public static void GuardarPlan(String plan) {

        String[] partes = plan.split(";");

        // Validacion: solo se guardan planes que ya pasaron por la Etapa 6
        if (plan.isEmpty() || partes.length < 8) {
            System.out.println("No hay un plan calculado para guardar.");
            System.out.println("Cree el plan (opcion 4) y calculelo (opcion 5) primero.");
            return;
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter("planes.txt", true))) {

            escritor.write(plan);
            escritor.newLine();

            System.out.println("Plan de ahorro guardado correctamente.");
            System.out.printf("Quedo vinculado al correo: %s\n", partes[0]);

        } catch (IOException e) {

            System.out.println("Ocurrio un error al guardar el plan de ahorro.");

        }
    }//Fin de Funcion

    /**
     * Pide un correo y muestra los resultados finales de todos los planes de
     * ahorro que esten vinculados a ese correo dentro de planes.txt.
     *
     * @param sc Instancia de la libreria Scanner
     */
    public static void MostrarPlanesPorCorreo(Scanner sc) {

        String correo = "";
        String linea = "";
        int contador = 0;

        System.out.print("Ingrese el correo del usuario a consultar: ");
        correo = sc.nextLine().trim();

        if (correo.isEmpty()) {
            System.out.println("Debe ingresar un correo.");
            return;
        }

        try (BufferedReader lector = new BufferedReader(new FileReader("planes.txt"))) {

            while ((linea = lector.readLine()) != null) {

                String[] partes = linea.split(";");

                // Solo se muestran las lineas completas del correo buscado
                if (partes.length >= 8 && partes[0].equalsIgnoreCase(correo)) {

                    contador++;
                    System.out.printf("\n>>> PLAN #%d <<<\n", contador);
                    MostrarResultadoCalculo(linea);

                }

            }//Fin de While

        } catch (IOException e) {

            System.out.println("Todavia no hay ningun plan guardado.");
            return;

        }

        if (contador == 0) {
            System.out.println("Ese correo no tiene ningun plan de ahorro guardado.");
        } else {
            System.out.printf("\nTotal de planes encontrados: %d\n", contador);
        }
    }//Fin de Funcion

}//Fin de Class
