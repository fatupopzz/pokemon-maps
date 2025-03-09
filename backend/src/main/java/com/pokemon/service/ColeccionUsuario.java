package com.pokemon.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.pokemon.model.Pokemon;

/**
 * Clase que gestiona la colección de Pokémon del usuario.
 * Autor: Fatima Navarro
 */
public class ColeccionUsuario {
    private Set<String> pokemonesUsuario = new HashSet<>();
    private MapaPokemons mapaPokemons;
    
    /**
     * Constructor que recibe el mapa de todos los Pokémon disponibles.
     */
    public ColeccionUsuario(MapaPokemons mapaPokemons) {
        this.mapaPokemons = mapaPokemons;
    }
    
    /**
     * Agrega un Pokémon a la colección del usuario por nombre.
     */
    public boolean agregarPokemon(String nombre) {
        // Verificar si el pokemon existe en los datos
        if (!mapaPokemons.existePokemon(nombre)) {
            return false; // No existe en los datos
        }
        
        // Verificar si ya está en la colección
        if (pokemonesUsuario.contains(nombre)) {
            return false; // Ya está en la colección
        }
        
        // Agregar a la colección
        return pokemonesUsuario.add(nombre);
    }
    
    /**
     * Obtiene la lista de nombres de Pokémon que el usuario tiene en su colección.
     */
    public Set<String> obtenerNombresPokemon() {
        return pokemonesUsuario;
    }
    
    /**
     * Obtiene la cantidad de Pokémon en la colección.
     */
    public int cantidadPokemon() {
        return pokemonesUsuario.size();
    }
    
    /**
     * Obtiene una lista de los objetos Pokémon que el usuario tiene en su colección.
     */
    public List<Pokemon> obtenerPokemons() {
        List<Pokemon> resultado = new ArrayList<>();
        for (String nombre : pokemonesUsuario) {
            Pokemon pokemon = mapaPokemons.obtenerPokemon(nombre);
            if (pokemon != null) {
                resultado.add(pokemon);
            }
        }
        return resultado;
    }
    
    /**
     * Obtiene la lista de Pokémon del usuario ordenados por tipo primario.
     */
    public List<Pokemon> obtenerPokemonsPorTipo() {
        List<Pokemon> pokemons = obtenerPokemons();
        return pokemons.stream()
                      .sorted(Comparator.comparing(Pokemon::getType1)
                                       .thenComparing(Pokemon::getName))
                      .collect(Collectors.toList());
    }
    
    /**
     * Convierte la colección del usuario a formato JSON
     */
    public String toJson() {
        List<Pokemon> pokemons = obtenerPokemons();
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < pokemons.size(); i++) {
            sb.append(pokemons.get(i).toJson());
            if (i < pokemons.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}