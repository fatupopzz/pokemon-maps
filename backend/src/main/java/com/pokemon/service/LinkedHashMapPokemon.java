package com.pokemon.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.pokemon.model.Pokemon;

/**
 * Implementación de MapaPokemons utilizando LinkedHashMap.
 * Autor: Fatima Navarro - 24044
 */
public class LinkedHashMapPokemon implements MapaPokemons {
    //--------
    // LinkedHashMap para almacenar los Pokémon
    // Mantiene el orden de inserción mientras ofrece acceso rápido O(1)
    // Es como un HashMap con memoria de orden de inserción
    //--------
    private LinkedHashMap<String, Pokemon> pokemonMap;
    private long tiempoUltimaOperacion;
    
    /**
     * Constructor que inicializa el LinkedHashMap
     */
    public LinkedHashMapPokemon() {
        this.pokemonMap = new LinkedHashMap<>(500, 0.75f, false);
        // El último parámetro (false) indica que mantiene orden de inserción
        // Si fuera true, mantendría orden de acceso (LRU)
        this.tiempoUltimaOperacion = 0;
    }
    
    @Override
    public void agregarPokemon(Pokemon pokemon) {
        //--------
        // Agregar elementos mantiene el orden de inserción
        // Mismo rendimiento que HashMap O(1) con sobrecarga mínima
        //--------
        long inicio = System.nanoTime();
        
        pokemonMap.put(pokemon.getName(), pokemon);
        
        tiempoUltimaOperacion = System.nanoTime() - inicio;
    }
    
    @Override
    public Pokemon obtenerPokemon(String nombre) {
        //--------
        // Acceso directo con complejidad O(1) como HashMap
        // Pero sin modificar el orden de los elementos
        //--------
        long inicio = System.nanoTime();
        
        Pokemon resultado = pokemonMap.get(nombre);
        
        tiempoUltimaOperacion = System.nanoTime() - inicio;
        return resultado;
    }
    
    @Override
    public Map<String, Pokemon> obtenerTodos() {
        //--------
        // Al ser LinkedHashMap, se garantiza que al iterar los elementos
        // aparecerán en el mismo orden en que fueron insertados
        // Esto puede ser ideal para mantener el orden del archivo CSV
        //--------
        return pokemonMap;
    }
    
    @Override
    public boolean existePokemon(String nombre) {
        //--------
        // Verificación rápida O(1) igual que en HashMap
        //--------
        return pokemonMap.containsKey(nombre);
    }
    
    @Override
    public Map<String, Pokemon> buscarPorHabilidad(String habilidad) {
        //--------
        // Preservamos el orden original en el resultado de la búsqueda
        // usando otro LinkedHashMap como colector
        //--------
        long inicio = System.nanoTime();
        
        LinkedHashMap<String, Pokemon> resultado = pokemonMap.entrySet().stream()
                .filter(entrada -> entrada.getValue().getAbilities().toLowerCase()
                                .contains(habilidad.toLowerCase()))
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (v1, v2) -> v1,  // En caso de colisión (no debería ocurrir)
                    LinkedHashMap::new
                ));
        
        tiempoUltimaOperacion = System.nanoTime() - inicio;
        return resultado;
    }
    
    @Override
    public List<Pokemon> obtenerOrdenadosPorTipo() {
        //--------
        // Para ordenar por tipo, necesitamos crear un nuevo ordenamiento
        // ya que por defecto está ordenado por orden de inserción
        //--------
        long inicio = System.nanoTime();
        
        List<Pokemon> resultado = new ArrayList<>(pokemonMap.values());
        // Ordenamos explícitamente por tipo y luego por nombre
        resultado.sort(Comparator.comparing(Pokemon::getType1)
                      .thenComparing(Pokemon::getName));
        
        tiempoUltimaOperacion = System.nanoTime() - inicio;
        return resultado;
    }
    
    @Override
    public String getTipoMapa() {
        return "LinkedHashMap";
    }
    
    /**
     * Reordena los Pokémon por su número de Pokédex
     */
    public void reordenarPorPokedex() {
        //--------
        // Método exclusivo de esta implementación
        // Elimina y reinserta los elementos en orden de Pokédex
        // Aprovecha que LinkedHashMap mantiene el orden de inserción
        //--------
        List<Pokemon> pokemones = new ArrayList<>(pokemonMap.values());
        pokemones.sort(Comparator.comparing(Pokemon::getPokedexNumber));
        
        pokemonMap.clear(); // Vaciamos el mapa
        for (Pokemon p : pokemones) {
            pokemonMap.put(p.getName(), p); // Reinsertamos en nuevo orden
        }
    }
    
    /**
     * Obtiene las estadísticas de rendimiento
     */
    public String getEstadisticasRendimiento() {
        //--------
        // Información específica del rendimiento de LinkedHashMap
        //--------
        return "Estadísticas LinkedHashMap:\n" +
               "- Tiempo última operación: " + tiempoUltimaOperacion + " ns\n" +
               "- Cantidad de elementos: " + pokemonMap.size() + "\n" +
               "- Complejidad teórica de búsqueda: O(1)\n" +
               "- Uso de memoria: Mayor que HashMap debido a referencias de orden\n" +
               "- Factor de carga actual: " + pokemonMap.size() / 500.0f;
    }
}