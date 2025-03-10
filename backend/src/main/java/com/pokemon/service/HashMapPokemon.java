package com.pokemon.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.pokemon.model.Pokemon;

/**
 * Implementación de MapaPokemons utilizando HashMap.
 * Autor: Fatima Navarro - 24044
 */
public class HashMapPokemon implements MapaPokemons {
    //--------
    // HashMap interno para almacenar los Pokémon
    // La clave es el nombre del Pokémon y el valor es el objeto Pokémon
    // Se utiliza HashMap para un acceso O(1) a los elementos
    //--------
    private Map<String, Pokemon> pokemonMap;
    private long tiempoUltimaOperacion;
    
    /**
     * Constructor que inicializa el mapa
     */
    public HashMapPokemon() {
        // Inicializamos con capacidad inicial para mejorar rendimiento
        this.pokemonMap = new HashMap<>(500);
        this.tiempoUltimaOperacion = 0;
    }
    
    @Override
    public void agregarPokemon(Pokemon pokemon) {
        //--------
        // Medimos el tiempo de ejecución para análisis de rendimiento
        // Esto nos permite comparar empíricamente el rendimiento de
        // las diferentes implementaciones
        //--------
        long inicio = System.nanoTime();
        
        // Agregamos el Pokémon al mapa usando su nombre como clave
        pokemonMap.put(pokemon.getName(), pokemon);
        
        // Registramos el tiempo que tardó
        tiempoUltimaOperacion = System.nanoTime() - inicio;
    }
    
    @Override
    public Pokemon obtenerPokemon(String nombre) {
        //--------
        // Acceso directo al mapa con complejidad O(1)
        // Esta es la principal ventaja de usar HashMap
        //--------
        long inicio = System.nanoTime();
        
        Pokemon resultado = pokemonMap.get(nombre);
        
        tiempoUltimaOperacion = System.nanoTime() - inicio;
        return resultado;
    }
    
    @Override
    public Map<String, Pokemon> obtenerTodos() {
        //--------
        // Devolvemos una referencia al mapa interno
        // No hay garantía de orden en un HashMap
        //--------
        return pokemonMap;
    }
    
    @Override
    public boolean existePokemon(String nombre) {
        //--------
        // Verificación de existencia en O(1)
        //--------
        return pokemonMap.containsKey(nombre);
    }
    
    @Override
    public Map<String, Pokemon> buscarPorHabilidad(String habilidad) {
        //--------
        // Usamos Stream API para filtrar Pokémon por habilidad
        // Complejidad O(n) ya que debemos revisar todos los elementos
        //--------
        long inicio = System.nanoTime();
        
        Map<String, Pokemon> resultado = pokemonMap.entrySet().stream()
                .filter(entrada -> entrada.getValue().getAbilities().toLowerCase()
                                .contains(habilidad.toLowerCase()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        
        tiempoUltimaOperacion = System.nanoTime() - inicio;
        return resultado;
    }
    
    @Override
    public List<Pokemon> obtenerOrdenadosPorTipo() {
        //--------
        // En HashMap no hay orden natural, así que debemos ordenar explícitamente
        // Complejidad O(n log n) debido al algoritmo de ordenamiento
        //--------
        long inicio = System.nanoTime();
        
        // Primero por tipo primario, luego por nombre para un ordenamiento consistente
        List<Pokemon> resultado = new ArrayList<>(pokemonMap.values());
        resultado.sort(Comparator.comparing(Pokemon::getType1)
                              .thenComparing(Pokemon::getName));
        
        tiempoUltimaOperacion = System.nanoTime() - inicio;
        return resultado;
    }
    
    @Override
    public String getTipoMapa() {
        return "HashMap";
    }
    
    /**
     * Método adicional para obtener información de rendimiento
     */
    public String getEstadisticasRendimiento() {
        //--------
        // Información detallada sobre el rendimiento del HashMap
        // Incluye tiempo de la última operación y características específicas
        //--------
        return "Estadísticas HashMap:\n" +
               "- Tiempo última operación: " + tiempoUltimaOperacion + " ns\n" +
               "- Cantidad de elementos: " + pokemonMap.size() + "\n" +
               "- Complejidad teórica de búsqueda: O(1)\n" +
               "- Factor de carga actual: " + pokemonMap.size() / 500.0f;
    }
}