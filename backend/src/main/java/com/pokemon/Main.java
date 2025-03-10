package com.pokemon;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.pokemon.model.Pokemon;
import com.pokemon.service.ColeccionUsuario;
import com.pokemon.service.MapaPokemons;
import com.pokemon.service.PokemonMapFactory;
import com.pokemon.util.LectorCSV;

/**
 * Clase principal del programa para exploración de Pokémon.
 * Autor: Fatima Navarro - 24044
 */
public class Main {
    //--------
    // Variables globales para mantener el estado del programa
    // y permitir la interacción con el usuario
    //--------
    private static MapaPokemons mapaPokemons;
    private static ColeccionUsuario coleccionUsuario;
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        //--------
        // Punto de entrada principal de la aplicación
        // Configura el entorno y muestra el menú principal
        //--------
        mostrarEncabezado();
        
        try {
            // Selección del tipo de mapa a utilizar
            int tipoMapa = seleccionarTipoMapa();
            
            // Crear el mapa usando el Factory
            mapaPokemons = PokemonMapFactory.crearMapa(tipoMapa);
            
            // Inicializar la colección del usuario
            coleccionUsuario = new ColeccionUsuario(mapaPokemons);
            
            // Cargar datos desde el archivo CSV
            cargarDatosPokemon();
            
            // Mostrar menú principal en bucle hasta que el usuario decida salir
            menuPrincipal();
            
        } catch (Exception e) {
            System.err.println("Error fatal: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            if (scanner != null) {
                scanner.close();
            }
        }
    }
    
    /**
     * Muestra el encabezado del programa.
     */
    private static void mostrarEncabezado() {
        //--------
        // Encabezado visual para mejorar la experiencia de usuario
        //--------
        System.out.println("═════════════════════════════════════════");
        System.out.println("       EXPLORADOR DE POKÉMON MAPS        ");
        System.out.println("═════════════════════════════════════════");
        System.out.println("       Autor: Fatima Navarro - 24044     ");
        System.out.println("═════════════════════════════════════════");
        System.out.println();
    }
    
    /**
     * Muestra las opciones de tipos de mapa y captura la selección.
     */
    private static int seleccionarTipoMapa() {
        //--------
        // Muestra las opciones de implementación y solicita
        // la selección al usuario, validando la entrada
        //--------
        System.out.println("SELECCIÓN DE IMPLEMENTACIÓN DE MAP");
        System.out.println("----------------------------------");
        System.out.println("1) HashMap     - Acceso rápido, sin orden");
        System.out.println("2) TreeMap     - Ordenado por nombre");
        System.out.println("3) LinkedHashMap - Mantiene orden de inserción");
        System.out.println("----------------------------------");
        
        return obtenerEntero("Seleccione una opción (1-3): ", 1, 3);
    }
    
    /**
     * Carga los datos del archivo CSV de Pokémon.
     */
    private static void cargarDatosPokemon() {
        //--------
        // Intenta cargar los datos del archivo CSV utilizando
        // la ruta adecuada y maneja posibles errores
        //--------
        System.out.println("\nCargando datos de Pokémon...");
        
        try {
            // Ruta al archivo CSV (relativa al directorio de ejecución)
            String rutaArchivo = "data/pokemon_data_pokeapi.csv";
            
            // Leer el archivo y cargar los Pokémon
            mapaPokemons = LectorCSV.leerArchivoPokemon(rutaArchivo, mapaPokemons);
            
            int totalPokemon = mapaPokemons.obtenerTodos().size();
            System.out.println("¡Datos cargados con éxito! " + totalPokemon + " Pokémon disponibles.");
            System.out.println("Usando implementación: " + mapaPokemons.getTipoMapa());
            
        } catch (IOException e) {
            System.err.println("Error al cargar el archivo de datos: " + e.getMessage());
            System.exit(1); // Salir si no se pueden cargar los datos
        }
    }
    
    /**
     * Muestra el menú principal y gestiona las acciones del usuario.
     */
    private static void menuPrincipal() {
        //--------
        // Bucle principal del programa que muestra las opciones
        // disponibles y procesa la selección del usuario
        //--------
        boolean salir = false;
        
        while (!salir) {
            System.out.println("\n╔═══════════════════════════╗");
            System.out.println("║       MENÚ PRINCIPAL      ║");
            System.out.println("╠═══════════════════════════╣");
            System.out.println("║ 1. Listar todos           ║");
            System.out.println("║ 2. Buscar por nombre      ║");
            System.out.println("║ 3. Listar por tipo        ║");
            System.out.println("║ 4. Buscar por habilidad   ║");
            System.out.println("║ 5. Agregar a mi colección ║");
            System.out.println("║ 6. Ver mi colección       ║");
            System.out.println("║ 7. Comparar rendimiento   ║");
            System.out.println("║ 8. Salir                  ║");
            System.out.println("╚═══════════════════════════╝");
            
            int opcion = obtenerEntero("Seleccione una opción: ", 1, 8);
            
            switch (opcion) {
                case 1: listarTodosLosPokemon(); break;
                case 2: buscarPokemonPorNombre(); break;
                case 3: listarPokemonPorTipo(); break;
                case 4: buscarPorHabilidad(); break;
                case 5: agregarAColeccion(); break;
                case 6: verColeccion(); break;
                case 7: compararRendimiento(); break;
                case 8: 
                    salir = true;
                    System.out.println("\n¡Gracias por usar el Explorador de Pokémon Maps!");
                    break;
            }
        }
    }
    
    /**
     * Lista todos los Pokémon paginados.
     */
    private static void listarTodosLosPokemon() {
        //--------
        // Muestra todos los Pokémon con paginación para
        // una mejor experiencia de usuario
        //--------
        System.out.println("\n=== LISTADO DE TODOS LOS POKÉMON ===");
        
        Map<String, Pokemon> todos = mapaPokemons.obtenerTodos();
        int total = todos.size();
        
        // Parámetros de paginación
        int pokemonPorPagina = 10;
        int totalPaginas = (int) Math.ceil((double) total / pokemonPorPagina);
        int paginaActual = 1;
        
        while (paginaActual <= totalPaginas) {
            System.out.println("\nPágina " + paginaActual + " de " + totalPaginas);
            System.out.println("------------------------------------");
            
            // Calcular rangos para esta página
            int inicio = (paginaActual - 1) * pokemonPorPagina;
            int fin = Math.min(inicio + pokemonPorPagina, total);
            
            // Mostrar Pokémon para esta página
            mostrarPokemonsPaginados(todos, inicio, fin);
            
            // Opciones de navegación
            if (paginaActual < totalPaginas) {
                System.out.println("\n1. Siguiente página");
                System.out.println("2. Volver al menú principal");
                
                int seleccion = obtenerEntero("Seleccione una opción: ", 1, 2);
                if (seleccion == 1) {
                    paginaActual++;
                } else {
                    break;
                }
            } else {
                System.out.println("\nFin del listado.");
                System.out.println("Presione Enter para continuar...");
                scanner.nextLine();
                break;
            }
        }
    }
    
    /**
     * Muestra un subconjunto de Pokémon para paginación.
     */
    private static void mostrarPokemonsPaginados(Map<String, Pokemon> pokemonMap, int inicio, int fin) {
        //--------
        // Método auxiliar para mostrar una página de Pokémon
        // con formato consistente
        //--------
        int contador = 0;
        for (Pokemon pokemon : pokemonMap.values()) {
            contador++;
            
            if (contador > inicio && contador <= fin) {
                System.out.println(pokemon.toShortString());
            }
        }
    }
    
    /**
     * Busca un Pokémon por su nombre.
     */
    private static void buscarPokemonPorNombre() {
        //--------
        // Solicita un nombre al usuario y muestra los detalles
        // del Pokémon si se encuentra
        //--------
        System.out.println("\n=== BUSCAR POKÉMON POR NOMBRE ===");
        System.out.print("Ingrese el nombre del Pokémon: ");
        String nombre = scanner.nextLine().trim();
        
        Pokemon pokemon = mapaPokemons.obtenerPokemon(nombre);
        if (pokemon != null) {
            System.out.println("\nPokémon encontrado:");
            System.out.println(pokemon);
        } else {
            System.out.println("No se encontró ningún Pokémon con el nombre '" + nombre + "'");
        }
        
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
    
    /**
     * Lista los Pokémon agrupados por tipo.
     */
    private static void listarPokemonPorTipo() {
        //--------
        // Muestra los Pokémon ordenados por su tipo primario
        // agrupándolos visualmente para mejor organización
        //--------
        System.out.println("\n=== POKÉMON ORDENADOS POR TIPO ===");
        
        List<Pokemon> pokemonOrdenados = mapaPokemons.obtenerOrdenadosPorTipo();
        
        // Agrupar por tipo para mostrarlos
        String tipoActual = "";
        for (Pokemon pokemon : pokemonOrdenados) {
            if (!pokemon.getType1().equals(tipoActual)) {
                tipoActual = pokemon.getType1();
                System.out.println("\n== TIPO: " + tipoActual.toUpperCase() + " ==");
                System.out.println("----------------------------------");
            }
            
            System.out.printf("%-15s | %s%n", 
                            pokemon.getName(),
                            (pokemon.getType2() != null && !pokemon.getType2().isEmpty() ? 
                            "Tipo2: " + pokemon.getType2() : ""));
        }
        
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
    
    /**
     * Busca Pokémon por habilidad.
     */
    private static void buscarPorHabilidad() {
        //--------
        // Busca Pokémon que tengan una habilidad específica
        // mostrando resultados paginados si son muchos
        //--------
        System.out.println("\n=== BUSCAR POKÉMON POR HABILIDAD ===");
        System.out.print("Ingrese la habilidad a buscar: ");
        String habilidad = scanner.nextLine().trim();
        
        Map<String, Pokemon> resultados = mapaPokemons.buscarPorHabilidad(habilidad);
        
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron Pokémon con la habilidad '" + habilidad + "'");
        } else {
            System.out.println("\nSe encontraron " + resultados.size() + " Pokémon con la habilidad '" + habilidad + "':");
            
            for (Pokemon pokemon : resultados.values()) {
                System.out.printf("%-15s | Tipo: %-10s | Habilidades: %s%n", 
                                 pokemon.getName(),
                                 pokemon.getType1() + (pokemon.getType2() != null && !pokemon.getType2().isEmpty() ? 
                                                   "/" + pokemon.getType2() : ""),
                                 pokemon.getAbilities());
            }
        }
        
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
    
    /**
     * Agrega un Pokémon a la colección personal del usuario.
     */
    private static void agregarAColeccion() {
        //--------
        // Permite al usuario agregar Pokémon a su colección personal
        // validando que el Pokémon exista en la base de datos
        //--------
        System.out.println("\n=== AGREGAR POKÉMON A MI COLECCIÓN ===");
        System.out.print("Ingrese el nombre del Pokémon a agregar: ");
        String nombre = scanner.nextLine().trim();
        
        // Verificar que el Pokémon existe
        if (!mapaPokemons.existePokemon(nombre)) {
            System.out.println("No existe ningún Pokémon con el nombre '" + nombre + "'");
            System.out.println("\nPresione Enter para continuar...");
            scanner.nextLine();
            return;
        }
        
        // Intentar agregar a la colección
        boolean resultado = coleccionUsuario.agregarPokemon(nombre);
        if (resultado) {
            System.out.println("¡" + nombre + " ha sido agregado a tu colección!");
        } else {
            System.out.println(nombre + " ya está en tu colección.");
        }
        
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
    
    /**
     * Muestra la colección de Pokémon del usuario.
     */
    private static void verColeccion() {
        //--------
        // Muestra los Pokémon que el usuario ha agregado
        // a su colección personal con estadísticas
        //--------
        System.out.println("\n=== MI COLECCIÓN DE POKÉMON ===");
        
        int cantidad = coleccionUsuario.cantidadPokemon();
        if (cantidad == 0) {
            System.out.println("Tu colección está vacía. Agrega Pokémon usando la opción 5.");
            System.out.println("\nPresione Enter para continuar...");
            scanner.nextLine();
            return;
        }
        
        System.out.println("Tienes " + cantidad + " Pokémon en tu colección:");
        
        List<Pokemon> coleccion = coleccionUsuario.obtenerPokemons();
        for (Pokemon pokemon : coleccion) {
            System.out.println(pokemon.toShortString());
        }
        
        // Mostrar estadísticas de la colección
        System.out.println("\n" + coleccionUsuario.obtenerEstadisticas());
        
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
    
    /**
     * Compara el rendimiento de las diferentes implementaciones.
     */
    private static void compararRendimiento() {
        //--------
        // Realiza pruebas de rendimiento en vivo para comparar
        // las diferentes implementaciones de Map
        //--------
        System.out.println("\n=== ANÁLISIS DE RENDIMIENTO ===");
        System.out.println("Implementación actual: " + mapaPokemons.getTipoMapa());
        
        // Mostrar información general sobre implementaciones
        System.out.println("\n" + PokemonMapFactory.obtenerInfoImplementaciones());
        
        // Realizar algunas pruebas de rendimiento
        System.out.println("\n=== PRUEBAS DE RENDIMIENTO EN VIVO ===");
        
        // Prueba 1: Búsqueda por nombre (20 repeticiones)
        System.out.println("\n1. Prueba de búsqueda por nombre (20 repeticiones)");
        long inicio = System.nanoTime();
        for (int i = 0; i < 20; i++) {
            mapaPokemons.obtenerPokemon("Pikachu");
        }
        long fin = System.nanoTime();
        System.out.println("   Tiempo promedio: " + (fin - inicio) / 20 + " ns");
        
        // Prueba 2: Obtener todos ordenados por tipo
        System.out.println("\n2. Prueba de ordenamiento por tipo");
        inicio = System.nanoTime();
        List<Pokemon> ordenados = mapaPokemons.obtenerOrdenadosPorTipo();
        fin = System.nanoTime();
        System.out.println("   Tiempo total: " + (fin - inicio) + " ns");
        System.out.println("   Cantidad de elementos ordenados: " + ordenados.size());
        
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
    
    /**
     * Obtiene un entero del usuario con validación.
     */
    private static int obtenerEntero(String mensaje, int min, int max) {
        //--------
        // Método de utilidad para obtener un entero válido del usuario
        // en un rango específico, con manejo de errores
        //--------
        int numero = 0;
        boolean valido = false;
        
        while (!valido) {
            System.out.print(mensaje);
            try {
                numero = Integer.parseInt(scanner.nextLine().trim());
                if (numero >= min && numero <= max) {
                    valido = true;
                } else {
                    System.out.println("Por favor, ingrese un número entre " + min + " y " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido");
            }
        }
        
        return numero;
    }
}