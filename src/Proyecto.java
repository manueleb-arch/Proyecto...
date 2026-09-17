
import java.util.Scanner;

/**
 *
 * @author manue
 */
public class Proyecto {

    /**
     * Un programa que ayude a las personas para que puedan realizar un plan de ahorro
       para poder llegar a una meta de comprar un aparato electrónico, un vehículo, o
        algún viaje a futuro. Este programa facilita al usuario al no estar sacando calculo
        extenso para poder saber cuanto tiempo y esfuerzo necesita para cumplir su
        objetivo.
        * 
        * Etapa 1 — Lo que ya tienes
            Bienvenida.
            Políticas de privacidad.
            Aceptación.

        Etapa 2
            Crear usuario.
            Pedir datos.
            Validar datos.

        Etapa 3
            Guardar usuarios en usuarios.txt.

        Etapa 4
            Leer usuarios al iniciar el programa.
            Mostrar usuarios existentes.
            Permitir seleccionar uno.

        Etapa 5
            Crear el plan de ahorro.
            Precio de la meta.
            Ahorro actual.
            Ahorro mensual.

        Etapa 6
            Calcular cuánto tiempo necesita para alcanzar la meta.

        Etapa 7
            Guardar también el plan de ahorro.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Bienvenido al prorama de plan de ahorro de datos.");
        System.out.print("===========================================");
        System.out.println();
        System.out.println();
        
        System.out.println("A continuacion se te estara pidiendo unos datos");
        System.out.println("personales los cuales son confidenciles.");
        System.out.println();
        
        System.out.print("===========================================");       
        System.out.println(); System.out.println("II Aceptación de Políticas de Privacidad II");
        System.out.print("===========================================");
        System.out.println();
        System.out.println("Al continuar, aceptas nuestras Políticas de Privacidad y Términos de Uso.");
        System.out.println();
        System.out.println("Tus datos e información de metas de ahorro se utilizarán únicamente para");
        System.out.println();
        System.out.println("calcular tus tiempos de meta, proyecciones financieras y facilitarte el");
        System.out.println();
        System.out.println("seguimiento de tus objetivos (vehículo, viajes o tecnología).");
        System.out.println();
        System.out.println("No compartiremos tu información financiera con terceros.");
        System.out.println();
            
       char respuesta = AceptaciondePoliticas(sc);
       
       if(respuesta == 's'){
           // Aqui empieza el inicio de seccion al aceptar las politicas
           
           System.out.println("BIENVENIDO AL PROGRAMA");
       }
       sc.close();//Sierre del programa
    }
   public static char AceptaciondePoliticas (Scanner politica){
       
       char respuesta;

            System.out.println("\n--- Aceptacion de las Politicas ---");
            
            // Aquí va la aceptacion de las politicas

            System.out.print("¿Aceptas nuestras politicas de seguridad?" );
            System.out.print("");
            System.out.print("Presione 's' para continuar / cualquier otra tecla para salir): ");
            /*A qui se le pide al usuario que va a usar esta app si quiere realizar su metod de ahorro
              tendra que aceptar las politicas privadas que se le hace saber que sus datos no van hacer 
              manipulados por terceros, solo se utilizarian para el respectivo proceso de su metodo de ahorro 
              el cual va a rrealizar en esta app.
            */
            
            // Lee el texto, toma la primera letra y la convierte a minúscula
            respuesta = politica.next().toLowerCase().charAt(0);

        if (respuesta == 's'){
            System.out.println("\n Politicas aceptadas.");
            System.out.println("Continuando con el programa...");
        }//fin if
        else{
        System.out.println("\n Politicas no aceptadas");
        System.out.println("Programa finalizado.");
        
   }//fin else
     return respuesta;
   }
}
  /* public static String validaciondeDatos(Scanner dato){
      String Nom;
      String Apell;
      int NumCell = 0;
      
       System.out.println("Ingrese sus nombres: ");
       Nom = dato.nextLine();
       System.out.print("");
       
       System.out.println("Ingrese sus Apellidos: ");
       Apell = dato.nextLine();
       System.out.print("");
       
       System.out.println("Ingrese su numero de telefono: ");
       NumCell = dato.nextInt();
       System.out.print("");
       
       System.out.println("");
       System.out.print("");
      
   }
}*/

