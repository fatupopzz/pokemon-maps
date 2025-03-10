package com.pokemon.service;

/**
 * Implementación del patrón Factory para crear diferentes tipos de MapaPokemons.
 * Autor: Fatima Navarro - 24044
 */
public class PokemonMapFactory {
    //--------
    // Este Factory implementa el patrón de diseño Factory Method
    // Su propósito es ocultar la lógica de creación de objetos
    // y centralizar la instanciación en un solo lugar
    //--------
    
    /**
     * Crea una instancia de MapaPokemons según el tipo especificado.
     * 
     * @param tipo 1 para HashMap, 2 para TreeMap, 3 para LinkedHashMap
     * @return Una instancia de MapaPokemons con la implementación solicitada
     * @throws IllegalArgumentException si el tipo no es válido
     */
    public static MapaPokemons crearMapa(int tipo) {
        //--------
        // Función principal del Factory: crea el tipo correcto de
        // implementación según el parámetro recibido, ocultando los
        // detalles de creación al resto del programa
        //--------
        switch(tipo) {
            case 1:
                System.out.println("INFO: Creando implementación HashMap...");
                return new HashMapPokemon();
            case 2:
                System.out.println("INFO: Creando implementación TreeMap...");
                return new TreeMapPokemon();
            case 3:
                System.out.println("INFO: Creando implementación LinkedHashMap...");
                return new LinkedHashMapPokemon();
            default:
                throw new IllegalArgumentException("Tipo de mapa inválido: " + tipo + 
                                                  ". Use 1=HashMap, 2=TreeMap, 3=LinkedHashMap");
        }
    }
    
    /**
     * Crea una implementación de MapaPokemons según el nombre del tipo.
     * Sobrecarga para permitir crear por nombre en lugar de número.
     */
    public static MapaPokemons crearMapaPorNombre(String tipoNombre) {
        //--------
        // Alternativa que acepta nombres en lugar de números
        // Más descriptivo pero requiere procesamiento de texto
        //--------
        switch(tipoNombre.toLowerCase()) {
            case "hashmap":
                return crearMapa(1);
            case "treemap":
                return crearMapa(2);
            case "linkedhashmap":
                return crearMapa(3);
            default:
                throw new IllegalArgumentException("Tipo de mapa inválido: " + tipoNombre + 
                                                  ". Opciones válidas: HashMap, TreeMap, LinkedHashMap");
        }
    }
    
    /**
     * Devuelve una descripción textual de las diferencias entre 
     * las implementaciones disponibles.
     */
    public static String obtenerInfoImplementaciones() {
        //--------
        // Método informativo que explica las características
        // de cada implementación de MapaPokemons
        //--------
        StringBuilder info = new StringBuilder();
        
        info.append("=== COMPARATIVA DE IMPLEMENTACIONES ===\n\n");
        
        // Información sobre HashMap
        info.append("1. HashMap:\n");
        info.append("   - Búsqueda: O(1) - Acceso muy rápido\n");
        info.append("   - Orden: No mantiene ningún orden específico\n");
        info.append("   - Memoria: Eficiente en espacio\n");
        info.append("   - Mejor para: Operaciones de búsqueda frecuentes\n\n");
        
        // Información sobre TreeMap
        info.append("2. TreeMap:\n");
        info.append("   - Búsqueda: O(log n) - Menor rendimiento\n");
        info.append("   - Orden: Mantiene elementos ordenados por clave (nombre)\n");
        info.append("   - Memoria: Mayor consumo por estructura de árbol\n");
        info.append("   - Mejor para: Datos que necesitan estar ordenados\n\n");
        
        // Información sobre LinkedHashMap
        info.append("3. LinkedHashMap:\n");
        info.append("   - Búsqueda: O(1) - Rápido como HashMap\n");
        info.append("   - Orden: Preserva orden de inserción\n");
        info.append("   - Memoria: Mayor consumo que HashMap\n");
        info.append("   - Mejor para: Mantener orden mientras se necesita acceso rápido\n");
        
        return info.toString();
    }
}