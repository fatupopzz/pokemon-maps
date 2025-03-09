package com.pokemon.service;

/**
 * Implementación del patrón Factory para crear diferentes tipos de MapaPokemons.
 */
public class PokemonMapFactory {
    
    /**
     * Crea una instancia de MapaPokemons según el tipo especificado.
     * 
     * @param tipo 1 para HashMap, 2 para TreeMap, 3 para LinkedHashMap
     * @return Una instancia de MapaPokemons con la implementación solicitada
     * @throws IllegalArgumentException si el tipo no es válido
     */
    public static MapaPokemons crearMapa(int tipo) {
        switch(tipo) {
            case 1:
                System.out.println("Utilizando implementación HashMap");
                return new HashMapPokemon();
            case 2:
                System.out.println("Utilizando implementación TreeMap");
                return new TreeMapPokemon();
            case 3:
                System.out.println("Utilizando implementación LinkedHashMap");
                return new LinkedHashMapPokemon();
            default:
                throw new IllegalArgumentException("Tipo de mapa no válido: " + tipo + 
                                                  ". Use 1 para HashMap, 2 para TreeMap o 3 para LinkedHashMap");
        }
    }
    
    /**
     * Crea una instancia de MapaPokemons según el nombre del tipo.
     * 
     * @param tipoNombre "HashMap", "TreeMap" o "LinkedHashMap"
     * @return Una instancia de MapaPokemons con la implementación solicitada
     * @throws IllegalArgumentException si el tipo no es válido
     */
    public static MapaPokemons crearMapaPorNombre(String tipoNombre) {
        switch(tipoNombre.toLowerCase()) {
            case "hashmap":
                System.out.println("Utilizando implementación HashMap");
                return new HashMapPokemon();
            case "treemap":
                System.out.println("Utilizando implementación TreeMap");
                return new TreeMapPokemon();
            case "linkedhashmap":
                System.out.println("Utilizando implementación LinkedHashMap");
                return new LinkedHashMapPokemon();
            default:
                throw new IllegalArgumentException("Tipo de mapa no válido: " + tipoNombre + 
                                                  ". Use HashMap, TreeMap o LinkedHashMap");
        }
    }
}