package com.pokemon.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.pokemon.model.Pokemon;

/**
 * Implementación de MapaPokemons utilizando TreeMap.
 * Autor: Fatima Navarro - 24044
 */
public class TreeMapPokemon implements MapaPokemons {
    //--------
    // TreeMap para almacenar los Pokémon, garantizando que estén
    // ordenados alfabéticamente por su nombre (clave)
    // Ideal para operación #4 que requiere mostrar ordenados
    //--------
    private TreeMap<String, Pokemon> pokemonMap;
    private long tiempoUltimaOperacion;

    /**
     * Constructor que inicializa el TreeMap
     */
    public TreeMapPokemon() {
        this.pokemonMap = new TreeMap<>();
        this.tiempoUltimaOperacion = 0;
    }
    
    @Override
    public void agregarPokemon(Pokemon pokemon) {
        //--------
        // En TreeMap, la inserción es O(log n) debido al 
        // balanceo del árbol rojo-negro interno
        //--------
        long inicio = System.nanoTime();
        
        pokemonMap.put(pokemon.getName(), pokemon);
        
        tiempoUltimaOperacion = System.nanoTime() - inicio;
    }
    
    @Override
    public Pokemon obtenerPokemon(String nombre) {
        //--------
        // Obtención con complejidad O(log n)
        // TreeMap tiene acceso más lento que HashMap
        //--------
        long inicio = System.nanoTime();
        
        Pokemon resultado = pokemonMap.get(nombre);
        
        tiempoUltimaOperacion = System.nanoTime() - inicio;
        return resultado;
    }
    
    @Override
    public Map<String, Pokemon> obtenerTodos() {
        //--------
        // Ventaja: ya están ordenados alfabéticamente por nombre
        // No necesitamos hacer ordenamiento adicional para mostrarlos
        // ordenados por nombre
        //--------
        return pokemonMap;
    }
    
    @Override
    public boolean existePokemon(String nombre) {
        //--------
        // Complejidad O(log n) para verificar existencia
        //--------
        return pokemonMap.containsKey(nombre);
    }
    
    @Override
    public Map<String, Pokemon> buscarPorHabilidad(String habilidad) {
        //--------
        // La búsqueda por habilidad requiere revisar todos los elementos
        // O(n) independientemente de la implementación, pero mantenemos
        // el resultado ordenado por nombre usando TreeMap
        //--------
        long inicio = System.nanoTime();
        
        TreeMap<String, Pokemon> resultado = pokemonMap.entrySet().stream()
                .filter(entrada -> entrada.getValue().getAbilities().toLowerCase()
                                .contains(habilidad.toLowerCase()))
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (v1, v2) -> v1,
                    TreeMap::new
                ));
        
        tiempoUltimaOperacion = System.nanoTime() - inicio;
        return resultado;
    }
    
    @Override
    public List<Pokemon> obtenerOrdenadosPorTipo() {
        //--------
        // Ordenamiento por tipo requiere transformación porque
        // TreeMap ya está ordenado por nombre
        // Creamos un nuevo ordenamiento por tipo
        //--------
        long inicio = System.nanoTime();
        
        List<Pokemon> resultado = new ArrayList<>(pokemonMap.values());
        // Ya están ordenados por nombre porque vienen de TreeMap
        // Ahora ordenamos por tipo primario, manteniendo orden por nombre
        resultado.sort(Comparator.comparing(Pokemon::getType1)
                       .thenComparing(Pokemon::getName));
        
        tiempoUltimaOperacion = System.nanoTime() - inicio;
        return resultado;
    }
    
    @Override
    public String getTipoMapa() {
        return "TreeMap";
    }
    
    /**
     * Obtiene la lista de Pokémon en un rango alfabético
     */
    public List<Pokemon> obtenerPokemonPorRangoAlfabetico(String inicio, String fin) {
        //--------
        // Método único para esta implementación: aprovecha la capacidad de
        // TreeMap de obtener submapas por rango de claves
        // Útil para paginar resultados alfabéticamente
        //--------
        return new ArrayList<>(pokemonMap.subMap(inicio, true, fin, true).values());
    }
    
    /**
     * Obtiene estadísticas específicas de TreeMap
     */
    public String getEstadisticasRendimiento() {
        //--------
        // Información de rendimiento específica para TreeMap
        //--------
        return "Estadísticas TreeMap:\n" +
               "- Tiempo última operación: " + tiempoUltimaOperacion + " ns\n" +
               "- Cantidad de elementos: " + pokemonMap.size() + "\n" +
               "- Altura estimada del árbol: " + 
                  (Math.floor(Math.log(pokemonMap.size()) / Math.log(2))) + "\n" +
               "- Complejidad teórica de búsqueda: O(log n)";
    }
}