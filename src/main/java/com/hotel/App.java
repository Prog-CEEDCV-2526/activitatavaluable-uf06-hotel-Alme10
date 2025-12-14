package com.hotel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Gestió de reserves d'un hotel.
 */
public class App {

    // --------- CONSTANTS I VARIABLES GLOBALS ---------

    // Tipus d'habitació
    public static final String TIPUS_ESTANDARD = "Estàndard";
    public static final String TIPUS_SUITE = "Suite";
    public static final String TIPUS_DELUXE = "Deluxe";

    // Serveis addicionals
    public static final String SERVEI_ESMORZAR = "Esmorzar";
    public static final String SERVEI_GIMNAS = "Gimnàs";
    public static final String SERVEI_SPA = "Spa";
    public static final String SERVEI_PISCINA = "Piscina";

    // Capacitat inicial
    public static final int CAPACITAT_ESTANDARD = 30;
    public static final int CAPACITAT_SUITE = 20;
    public static final int CAPACITAT_DELUXE = 10;

    // IVA
    public static final float IVA = 0.21f;

    // Scanner únic
    public static Scanner sc = new Scanner(System.in);

    // HashMaps de consulta
    public static HashMap<String, Float> preusHabitacions = new HashMap<String, Float>();
    public static HashMap<String, Integer> capacitatInicial = new HashMap<String, Integer>();
    public static HashMap<String, Float> preusServeis = new HashMap<String, Float>();

    // HashMaps dinàmics
    public static HashMap<String, Integer> disponibilitatHabitacions = new HashMap<String, Integer>();
    public static HashMap<Integer, ArrayList<String>> reserves = new HashMap<Integer, ArrayList<String>>();

    // Generador de nombres aleatoris per als codis de reserva
    public static Random random = new Random();

    // --------- MÈTODE MAIN ---------

    /**
     * Mètode principal. Mostra el menú en un bucle i gestiona l'opció triada
     * fins que l'usuari decideix eixir.
     */
    public static void main(String[] args) {
        inicialitzarPreus();

        int opcio = 0;
        do {
            mostrarMenu();
            opcio = llegirEnter("Seleccione una opció: ");
            gestionarOpcio(opcio);
        } while (opcio != 6);

        System.out.println("Eixint del sistema... Gràcies per utilitzar el gestor de reserves!");
    }

    // --------- MÈTODES DEMANATS ---------

    /**
     * Configura els preus de les habitacions, serveis addicionals i
     * les capacitats inicials en els HashMaps corresponents.
     */
    public static void inicialitzarPreus() {
        // Preus habitacions
        preusHabitacions.put(TIPUS_ESTANDARD, 50f);
        preusHabitacions.put(TIPUS_SUITE, 100f);
        preusHabitacions.put(TIPUS_DELUXE, 150f);

        // Capacitats inicials
        capacitatInicial.put(TIPUS_ESTANDARD, CAPACITAT_ESTANDARD);
        capacitatInicial.put(TIPUS_SUITE, CAPACITAT_SUITE);
        capacitatInicial.put(TIPUS_DELUXE, CAPACITAT_DELUXE);

        // Disponibilitat inicial (comença igual que la capacitat)
        disponibilitatHabitacions.put(TIPUS_ESTANDARD, CAPACITAT_ESTANDARD);
        disponibilitatHabitacions.put(TIPUS_SUITE, CAPACITAT_SUITE);
        disponibilitatHabitacions.put(TIPUS_DELUXE, CAPACITAT_DELUXE);

        // Preus serveis
        preusServeis.put(SERVEI_ESMORZAR, 10f);
        preusServeis.put(SERVEI_GIMNAS, 15f);
        preusServeis.put(SERVEI_SPA, 20f);
        preusServeis.put(SERVEI_PISCINA, 25f);
    }

    /**
     * Mostra el menú principal amb les opcions disponibles per a l'usuari.
     */
    public static void mostrarMenu() {
        System.out.println("\n===== MENÚ PRINCIPAL =====");
        System.out.println("1. Reservar una habitació");
        System.out.println("2. Alliberar una habitació");
        System.out.println("3. Consultar disponibilitat");
        System.out.println("4. Llistar reserves per tipus");
        System.out.println("5. Obtindre una reserva");
        System.out.println("6. Ixir");
    }

    /**
     * Processa l'opció seleccionada per l'usuari i crida el mètode corresponent.
     */
    public static void gestionarOpcio(int opcio) {
       //TODO:
        //Processa la opció i entra en el mètode
        switch (opcio) {
            case 1:
                reservarHabitacio();             
                break;
            case 2: 
                alliberarHabitacio();
                break;
            case 3:
                consultarDisponibilitat();
                break;
            case 4:
                obtindreReservaPerTipus();
                break;
            case 5:
                obtindreReserva();
                break;
            default:
                System.out.println("Opció no valida, escollir la opció correcta (1-6)");
        }
    }

    /**
     * Gestiona tot el procés de reserva: selecció del tipus d'habitació,
     * serveis addicionals, càlcul del preu total i generació del codi de reserva.
     */
    public static void reservarHabitacio() {
        System.out.println("\n===== RESERVAR HABITACIÓ =====");
        //TODO:
        //Seleccionar tipus d'habitació disponible
        String tipusHabitacio = seleccionarTipusHabitacioDisponible();
        
        if (tipusHabitacio == null) {
            System.out.println("No hi ha habitacions disponibles per al tipus seleccionat.");
            return;
        }

        //Seleccionar serveis addicionals
        ArrayList<String> serveisSeleccionats = seleccionarServeis();

        //Calcular preu total
        float preuTotal = calcularPreuTotal(tipusHabitacio, serveisSeleccionats);

        //Generar codi de reserva únic
        int codiReserva = generarCodiReserva();

        //Crear la llista de dades de la reserva
        ArrayList<String> dadesReserva = new ArrayList<>();
        //Tipus d'habitació
            dadesReserva.add(tipusHabitacio);
        //Preu total
            dadesReserva.add(String.valueOf(preuTotal)); 
        //Serveis addicionals
            dadesReserva.addAll(serveisSeleccionats);

        //Registrar la reserva
        reserves.put(codiReserva, dadesReserva);
        
        //Actualitzar la disponibilitat
        disponibilitatHabitacions.put(tipusHabitacio, disponibilitatHabitacions.get(tipusHabitacio) - 1);

        //Confirmació
        System.out.println("\nReserva realitzada amb èxit!");
        System.out.println("Codi de Reserva: " + codiReserva);
        System.out.println("Preu Final (IVA inclòs):" + preuTotal + "€");
    }

    /**
     * Pregunta a l'usuari un tipus d'habitació en format numèric i
     * retorna el nom del tipus.
     */
    public static String seleccionarTipusHabitacio() {
        //TODO:
        //Imprimir el tipus d'habitació
        mostrarInfoTipus(TIPUS_ESTANDARD);
        mostrarInfoTipus(TIPUS_SUITE);
        mostrarInfoTipus(TIPUS_DELUXE);

        String tipus = null;
        boolean correcte = false;

        //Seleccionar el tipus d'habitació
        while (!correcte) {
            int opcio = llegirEnter("Seleccione un tipus d'habitació:");
            switch (opcio) {
                case 1:
                    tipus = TIPUS_ESTANDARD;
                    correcte = true;
                    break;
                case 2:
                    tipus = TIPUS_SUITE;
                    correcte = true;                    
                    break;
                case 3:
                    tipus = TIPUS_DELUXE;
                    correcte = true;
                    break;            
                default:
                    System.out.println("Opció no valida, escollir la opció correcta.");
            }
        }
        return tipus;
    }

    /**
     * Mostra la disponibilitat i el preu de cada tipus d'habitació,
     * demana a l'usuari un tipus i només el retorna si encara hi ha
     * habitacions disponibles. En cas contrari, retorna null.
     */
    public static String seleccionarTipusHabitacioDisponible() {
        System.out.println("\nTipus d'habitació disponibles:");
        //TODO:
        //Mostrar el Tipus d'habitació i la disponibilitat de cada una
        String tipusSeleccinat = seleccionarTipusHabitacio();
        //Comprobar disponibilidad
        if (disponibilitatHabitacions.get(tipusSeleccinat) > 0 ) {
            return tipusSeleccinat;
        } else 
        return null;
        
    }

    /**
     * Permet triar serveis addicionals (entre 0 i 4, sense repetir) i
     * els retorna en un ArrayList de String.
     */
    public static ArrayList<String> seleccionarServeis() {
        //TODO:
        //S'imprimeix els tipus de servicis adicionals que hi ha
        ArrayList<String> serveisSeleccionats = new ArrayList<>();
        List<String> serveisDisponibles = Arrays.asList(SERVEI_ESMORZAR, SERVEI_GIMNAS, SERVEI_SPA, SERVEI_PISCINA);
        int opcio = -1;
        
        System.out.println("\n===== SERVEIS ADDICIONALS =====");
        System.out.println("Seleccione serveis (0 per finalitzar, màxim " + serveisDisponibles.size() + "):");
        
        while (opcio != 0 && serveisSeleccionats.size() < serveisDisponibles.size()) {
            
            // Mostrar serveis disponibles
            System.out.println("---");
            for (int i = 0; i < serveisDisponibles.size(); i++) {
                String servei = serveisDisponibles.get(i);
                float preu = preusServeis.get(servei);
                // Només mostrem si no s'ha seleccionat ja
                if (!serveisSeleccionats.contains(servei)) {
                    System.out.println((i + 1) + ". " + servei + " (" + preu + "€)");
                }
            }
            System.out.println("0. Finalitzar selecció");
            
            opcio = llegirEnter("Seleccione servei: ");
            
            /*Revisa que l'opció seleccionada siga major o igual que 1 i que siga menor o igual als servicis que hi ha.
            T'avisa quan has seleccionat un servici i et diu qual has afegit.
            També revisa si ja esta afegit i et dona un missatge que este servici ja esta afegit.
            I si no selecciones una opció correcta et dona un avís. 
            */
            if (opcio >= 1 && opcio <= serveisDisponibles.size()) {
                String serveiTriat = serveisDisponibles.get(opcio - 1);
                
                if (!serveisSeleccionats.contains(serveiTriat)) {
                    serveisSeleccionats.add(serveiTriat);
                    System.out.println("-" + serveiTriat + " afegit.");
                } else {
                    System.out.println("Aquest servei ja ha estat afegit.");
                }
            } else if (opcio != 0) {
                System.out.println("Opció no vàlida.");
            }
        }
        
        if (serveisSeleccionats.isEmpty()) {
            System.out.println("Cap servei addicional seleccionat.");
        }

        return serveisSeleccionats;
    }

    /**
     * Calcula i retorna el cost total de la reserva, incloent l'habitació,
     * els serveis seleccionats i l'IVA.
     */
    public static float calcularPreuTotal(String tipusHabitacio, ArrayList<String> serveisSeleccionats) {
        //TODO:
        //Es calcula el preu segons el tipus d'habitació i després es mostra tot el resum dels preus 
        float preuBase = preusHabitacions.get(tipusHabitacio);
        float costServeis = 0.0f;
        
        for (String servei : serveisSeleccionats) {
            costServeis += preusServeis.get(servei);
        }

        float subtotal = preuBase + costServeis;
        float impostos = subtotal * IVA;
        float preuTotal = subtotal + impostos;
        
        System.out.println("\n- Resum de Preus\n");
        System.out.println("Preu Habitació" + "("+ tipusHabitacio + ")"+ ":" + preuBase + "€");
        System.out.println("Cost Serveis:" + costServeis + "€");
        System.out.println("Subtotal:" + subtotal +"€");
        System.out.println("IVA" + "(" + IVA * 100 + ")" + ":" + impostos +"€");
        System.out.println("PREU TOTAL FINAL" + ":" + preuTotal + "€");
        return preuTotal;
    }

    /**
     * Genera i retorna un codi de reserva únic de tres xifres
     * (entre 100 i 999) que no estiga repetit.
     */
    public static int generarCodiReserva() {
        //TODO:
        //Generem un codi per a la reserva.
        int codi;
        do {
            codi = random.nextInt(900) + 100;
        } while(reserves.containsKey(codi));
        
        return codi;
    }

    /**
     * Permet alliberar una habitació utilitzant el codi de reserva
     * i actualitza la disponibilitat.
     */
    public static void alliberarHabitacio() {
        System.out.println("\n===== ALLIBERAR HABITACIÓ =====");
         // TODO: Demanar codi, tornar habitació i eliminar reserva
        //Es demana el codi de la reserva i procedim a eliminar la reserva i actualitzar la disponibilitat del tipus d'estada
        int codi = llegirEnter("Introdueix el codi de resrva: ");

        if (reserves.containsKey(codi)) {
            ArrayList<String> dadesReserva = reserves.get(codi);
            String tipusHabitacio = dadesReserva.get(0);

            reserves.remove(codi);

            int disponiblesActual = disponibilitatHabitacions.get (tipusHabitacio);
            disponibilitatHabitacions.put (tipusHabitacio, disponiblesActual +1);
            System.out.println ("-Habitació alliberada correctament");
            System.out.println ("-Disponibilitat actualizada");
        } else {
            System.out.println ("El codi de reserva " + codi +  " no existeix.");
        }
    }

    /**
     * Mostra la disponibilitat actual de les habitacions (lliures i ocupades).
     */
    public static void consultarDisponibilitat() {
        // TODO: Mostrar lliures i ocupades
        //Es mostra en una taula les habitacions lliures i ocupades.
        System.out.println("TIPUS\t\tLLIURES\tOCUPADES");
        System.out.println("----------------------------------");
        mostrarDisponibilitatTipus(TIPUS_ESTANDARD);
        mostrarDisponibilitatTipus(TIPUS_SUITE);
        mostrarDisponibilitatTipus(TIPUS_DELUXE);
        System.out.println("----------------------------------");
        
        //Càlcul de total d'habitacions reservades
        int totalReservades = reserves.size();
        System.out.println("Total de reserves actives: " + totalReservades);
    }

    /**
     * Funció recursiva. Mostra les dades de totes les reserves
     * associades a un tipus d'habitació.
     */
    public static void llistarReservesPerTipus(int[] codis, String tipus) {
         // TODO: Implementar recursivitat
        //Mostra les dades de totes les reserves que estan asociadas al tipus d'habitació
        if (codis == null || codis.length == 0) {
            return;
        }
        int codiActual = codis[0];
        mostrarDadesReserva(codiActual);
        
        if (codis.length > 1) {
            int[] codisRestants = Arrays.copyOfRange(codis, 1, codis.length);
            llistarReservesPerTipus(codisRestants, tipus);
        }
    }

    /**
     * Permet consultar els detalls d'una reserva introduint el codi.
     */
    public static void obtindreReserva() {
        System.out.println("\n===== CONSULTAR RESERVA =====");
        // TODO: Mostrar dades d'una reserva concreta
        //S'introduïx el codi i ens mostra totes les dades de la reserva associat al codi, i si no existeix el codi et dona un avís
        int codi = llegirEnter("Introdueix el codi de reserva: ");
        if (reserves.containsKey(codi)) {
            mostrarDadesReserva(codi);
        } else {
            System.out.println ("El codi de reserva " + codi + " no existeix");
        }
    }

    /**
     * Mostra totes les reserves existents per a un tipus d'habitació
     * específic.
     */
    public static void obtindreReservaPerTipus() {
        System.out.println("\n===== CONSULTAR RESERVES PER TIPUS =====");
        // TODO: Llistar reserves per tipus
        //Seleccionem el tipus d'habitació i després ens ensenya les dades de totes les reserves que haja d'eixe tipus d'habitació
        String tipus = seleccionarTipusHabitacio();
        ArrayList<Integer> codisCoincidents = new ArrayList<>();
        for (Integer codi: reserves.keySet()) {
            if (reserves.get(codi).get(0).equals(tipus)) {
                codisCoincidents.add(codi);
            }
        }
        if (codisCoincidents.isEmpty()) {
            System.out.println("No hi ha reserves actives per al tipus " + tipus + ".");
        }
        System.out.println("Reserves de tipus " + tipus );
        int [] codisArray = new int [codisCoincidents.size()];
        for (int i = 0; i < codisCoincidents.size(); i++) {
            codisArray[i] = codisCoincidents.get(i);
        }
        llistarReservesPerTipus(codisArray, tipus);

    }

    /**
     * Consulta i mostra en detall la informació d'una reserva.
     */
    public static void mostrarDadesReserva(int codi) {
       // TODO: Imprimir tota la informació d'una reserva
        //Es demana el codi de la reserva i es mostra els detalls de la reserva.
        if (!reserves.containsKey(codi)) {
            System.out.println("Reserva amb codi " + codi + " no trobada.");
        }
        
        ArrayList<String> dadesReserva = reserves.get(codi);
        String tipusHabitacio = dadesReserva.get(0);
        float preuTotal = Float.parseFloat(dadesReserva.get(1));
        
        System.out.println("---------------------------------");
        System.out.println("DETALLS DE LA RESERVA");
        System.out.println("Codi:" + codi);
        System.out.println("Tipus d'Habitació: " + tipusHabitacio);
        System.out.println("Preu Total (Final): " + preuTotal + "€");
        
        //Mostra les serveis
        if (dadesReserva.size() > 2) {
            System.out.println("Serveis Addicionals:");
            for (int i = 2; i < dadesReserva.size(); i++) {
                System.out.println(" - " + dadesReserva.get(i));
            }
            } else {
            System.out.println("Serveis Addicionals: Cap.");
        }
        System.out.println("---------------------------------");
    }

    // --------- MÈTODES AUXILIARS (PER MILLORAR LEGIBILITAT) ---------

    /**
     * Llig un enter per teclat mostrant un missatge i gestiona possibles
     * errors d'entrada.
     */
    static int llegirEnter(String missatge) {
        int valor = 0;
        boolean correcte = false;
        while (!correcte) {
                System.out.print(missatge);
                valor = sc.nextInt();
                correcte = true;
        }
        return valor;
    }

    /**
     * Mostra per pantalla informació d'un tipus d'habitació: preu i
     * habitacions disponibles.
     */
    static void mostrarInfoTipus(String tipus) {
        int disponibles = disponibilitatHabitacions.get(tipus);
        int capacitat = capacitatInicial.get(tipus);
        float preu = preusHabitacions.get(tipus);
        System.out.println("- " + tipus + " (" + disponibles + " disponibles de " + capacitat + ") - " + preu + "€");
    }

    /**
     * Mostra la disponibilitat (lliures i ocupades) d'un tipus d'habitació.
     */
    static void mostrarDisponibilitatTipus(String tipus) {
        int lliures = disponibilitatHabitacions.get(tipus);
        int capacitat = capacitatInicial.get(tipus);
        int ocupades = capacitat - lliures;

        String etiqueta = tipus;
        if (etiqueta.length() < 8) {
            etiqueta = etiqueta + "\t"; // per a quadrar la taula
        }

        System.out.println(etiqueta + "\t" + lliures + "\t" + ocupades);
    }
}
