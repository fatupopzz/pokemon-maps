package com.pokemon.service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.pokemon.model.Pokemon;

/**
 * Implementación de MapaPokemons utilizando LinkedHashMap.
 * Autor: Fatima Navarro
 */
public class LinkedHashMapPokemon implements MapaPokemons {
    private Map<String, Pokemon> pokemonMap = new LinkedHashMap<>();
    
    @Override
    public void agregarPokemon(Pokemon pokemon) {
        pokemonMap.put(pokemon.getName(), pokemon);
    }
    
    @Override
    public Pokemon obtenerPokemon(String nombre) {
        return pokemonMap.get(nombre);
    }
    
    @Override
    public Map<String, Pokemon> obtenerTodos() {
        return pokemonMap;
    }
    
    @Override
    public boolean existePokemon(String nombre) {
        return pokemonMap.containsKey(nombre);
    }
    
    @Override
    public Map<String, Pokemon> buscarPorHabilidad(String habilidad) {
        // Modificado para usar getAbilities() y buscar coincidencias parciales
        return pokemonMap.entrySet().stream()
            .filter(entry -> entry.getValue().getAbilities().toLowerCase()
                          .contains(habilidad.toLowerCase()))
            .collect(Collectors.toMap(
                Map.Entry::getKey, 
                Map.Entry::getValue, 
                (v1, v2) -> v1, 
                LinkedHashMap::new
            ));
    }
    
    @Override
    public List<Pokemon> obtenerOrdenadosPorTipo() {
        return pokemonMap.values().stream()
            .sorted(Comparator.comparing(Pokemon::getType1)
                             .thenComparing(Pokemon::getName))
            .collect(Collectors.toList());
    }
    
    @Override
    public String getTipoMapa() {
        return "LinkedHashMap";
    }
}