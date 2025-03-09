package com.pokemon;

import java.io.IOException;
import java.util.Scanner;

import com.pokemon.service.MapaPokemons;
import com.pokemon.service.PokemonMapFactory;
import com.pokemon.util.LectorCSV;

/**
 * Clase principal que ejecuta la aplicación en modo consola.
 * Autor: Fatima Navarro
 */
public class Main {
    private static MapaPokemons mapaPokemons;
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        try {
            System.out.println("Bienvenido, escoge tu tipo de mapa de Pokémon");
            
            // Selección del tipo de Map
            System.out.println("----------------------------------------------");
            System.out.println("Seleccione la implementación de Map a utilizar:");
            System.out.println("1) HashMap - Acceso rápido, sin orden específico");
            System.out.println("2) TreeMap - Elementos ordenados por nombre");
            System.out.println("3) LinkedHashMap - Mantiene el orden de inserción");
            System.out.println("----------------------------------------------");
            
            int opcionMap = obtenerEntero("Ingrese su opción (1-3): ", 1, 3);
            
            // Crear el mapa usando Factory
            mapaPokemons = PokemonMapFactory.crearMapa(opcionMap);
            
            // Ruta del archivo CSV
            String rutaArchivo = "data/pokemon_data_pokeapi.csv";
            if (args.length > 0) {
                rutaArchivo = args[0];
            }
            
            // Leer el archivo de Pokémon
            System.out.println("\nLeyendo datos de Pokémon desde " + rutaArchivo + "...");
            try {
                mapaPokemons = LectorCSV.leerArchivoPokemon(rutaArchivo, mapaPokemons);
                
                // Mostrar información básica
                System.out.println("Se cargaron " + mapaPokemons.obtenerTodos().size() + " Pokémon.");
                System.out.println("Usando " + mapaPokemons.getTipoMapa() + " como implementación.");
                
                // Mostrar menú principal
                mostrarMenu();
                
            } catch (IOException e) {
                System.err.println("Error al leer el archivo: " + e.getMessage());
                System.exit(1);
            }
            
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
    
    /**
     * Muestra el menú principal y procesa las opciones del usuario.
     */
    private static void mostrarMenu() {
        boolean salir = false;
        
        while (!salir) {
            System.out.println("\n======== MENÚ PRINCIPAL =========");
            System.out.println("1. Mostrar todos los Pokémon");
            System.out.println("2. Buscar Pokémon por nombre");
            System.out.println("3. Mostrar Pokémon ordenados por tipo");
            System.out.println("4. Buscar Pokémon por habilidad");
            System.out.println("5. Exportar datos a JSON");
            System.out.println("6. Salir");
            System.out.println("=================================");
            
            int opcion = obtenerEntero("Seleccione una opción (1-6): ", 1, 6);
            
            switch (opcion) {
                case 1:
                    mostrarTodosLosPokemon();
                    break;
                case 2:
                    buscarPokemonPorNombre();
                    break;
                case 3:
                    mostrarPokemonPorTipo();
                    break;
                case 4:
                    buscarPokemonPorHabilidad();
                    break;
                case 5:
                    exportarDatosJSON();
                    break;
                case 6:
                    salir = true;
                    System.out.println("¡Gracias por usar la aplicación! ¡Hasta pronto!");
                    break;
            }
        }
    }
    
    /**
     * Muestra una lista de todos los Pokémon disponibles.
     */
    private static void mostrarTodosLosPokemon() {
        System.out.println("\n== TODOS LOS POKÉMON ==");
        
        // Limitar a 10 Pokémon por página para no saturar la consola
        int total = mapaPokemons.obtenerTodos().size();
        int porPagina = 10;
        int totalPaginas = (int) Math.ceil((double) total / porPagina);
        int paginaActual = 1;
        
        boolean continuar = true;
        while (continuar && paginaActual <= totalPaginas) {
            System.out.println("\nPágina " + paginaActual + " de " + totalPaginas);
            
            // Calcular índices de inicio y fin para esta página
            int inicio = (paginaActual - 1) * porPagina;
            int fin = Math.min(inicio + porPagina, total);
            
            // Mostrar los Pokémon de esta página
            int contador = 0;
            for (var entry : mapaPokemons.obtenerTodos().entrySet()) {
                contador++;
                
                if (contador > inicio && contador <= fin) {
                    var pokemon = entry.getValue();
                    System.out.println("- " + pokemon.getName() + 
                                      " (Tipo: " + pokemon.getType1() + 
                                      (pokemon.getType2() != null && !pokemon.getType2().isEmpty() ? 
                                      "/" + pokemon.getType2() : "") + 
                                      ")");
                }
            }
            
            // Opciones de navegación
            if (paginaActual < totalPaginas) {
                System.out.println("\n1. Siguiente página");
                System.out.println("2. Volver al menú principal");
                
                int opcion = obtenerEntero("Seleccione una opción (1-2): ", 1, 2);
                if (opcion == 1) {
                    paginaActual++;
                } else {
                    continuar = false;
                }
            } else {
                System.out.println("\nFin de la lista. Presione Enter para volver al menú principal...");
                scanner.nextLine();
                continuar = false;
            }
        }
    }
    
    /**
     * Busca un Pokémon por su nombre y muestra sus detalles.
     */
    private static void buscarPokemonPorNombre() {
        System.out.println("\n== BUSCAR POKÉMON POR NOMBRE ==");
        System.out.print("Ingrese el nombre del Pokémon: ");
        String nombre = scanner.nextLine().trim();
        
        var pokemon = mapaPokemons.obtenerPokemon(nombre);
        if (pokemon != null) {
            System.out.println("\nDetalles del Pokémon:");
            System.out.println(pokemon);
        } else {
            System.out.println("No se encontró ningún Pokémon con el nombre '" + nombre + "'");
        }
        
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
    
    /**
     * Muestra todos los Pokémon ordenados por su tipo primario.
     */
    private static void mostrarPokemonPorTipo() {
        System.out.println("\n== POKÉMON ORDENADOS POR TIPO ==");
        
        var pokemonesPorTipo = mapaPokemons.obtenerOrdenadosPorTipo();
        
        // Agrupar por tipo para mostrarlos organizados
        String tipoActual = "";
        for (var pokemon : pokemonesPorTipo) {
            if (!pokemon.getType1().equals(tipoActual)) {
                tipoActual = pokemon.getType1();
                System.out.println("\nTipo: " + tipoActual);
            }
            
            System.out.println("- " + pokemon.getName() + 
                             (pokemon.getType2() != null && !pokemon.getType2().isEmpty() ? 
                             " (Tipo secundario: " + pokemon.getType2() + ")" : ""));
        }
        
        System.out.println("\nPresione Enter para volver al menú principal...");
        scanner.nextLine();
    }
    
    /**
     * Busca Pokémon que tengan una habilidad específica.
     */
    private static void buscarPokemonPorHabilidad() {
        System.out.println("\n== BUSCAR POKÉMON POR HABILIDAD ==");
        System.out.print("Ingrese la habilidad a buscar: ");
        String habilidad = scanner.nextLine().trim();
        
        var encontrados = mapaPokemons.buscarPorHabilidad(habilidad);
        
        if (encontrados.isEmpty()) {
            System.out.println("No se encontraron Pokémon con la habilidad '" + habilidad + "'");
        } else {
            System.out.println("\nPokémon encontrados (" + encontrados.size() + "):");
            for (var entry : encontrados.entrySet()) {
                var pokemon = entry.getValue();
                System.out.println("- " + pokemon.getName() + 
                                  " (Tipo: " + pokemon.getType1() + 
                                  (pokemon.getType2() != null && !pokemon.getType2().isEmpty() ? 
                                  "/" + pokemon.getType2() : "") + 
                                  ")");
            }
        }
        
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
    
    /**
     * Exporta todos los datos de Pokémon a formato JSON.
     */
    private static void exportarDatosJSON() {
        System.out.println("\n== EXPORTAR DATOS A JSON ==");
        System.out.println("Exportando datos...");
        
        StringBuilder json = new StringBuilder("[\n");
        var pokemons = mapaPokemons.obtenerTodos();
        int contador = 0;
        
        for (var entry : pokemons.entrySet()) {
            json.append("  ").append(entry.getValue().toJson());
            
            if (contador < pokemons.size() - 1) {
                json.append(",");
            }
            json.append("\n");
            contador++;
        }
        
        json.append("]");
        
        // En una aplicación real, esto se guardaría en un archivo
        System.out.println("Datos exportados correctamente. Primeras 200 caracteres del JSON:");
        System.out.println(json.substring(0, Math.min(200, json.length())) + "...");
        
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
    
    /**
     * Obtiene un número entero del usuario con validación.
     */
    private static int obtenerEntero(String mensaje, int min, int max) {
        int numero = 0;
        boolean valido = false;
        
        while (!valido) {
            System.out.print(mensaje);
            try {
                numero = Integer.parseInt(scanner.nextLine().trim());
                if (numero >= min && numero <= max) {
                    valido = true;
                } else {
                    System.out.println("El número debe estar entre " + min + " y " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido");
            }
        }
        
        return numero;
    }
}