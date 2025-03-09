package com.pokemon.service;

import java.util.List;
import java.util.Map;

import com.pokemon.model.Pokemon;

/**
 * Interfaz que define las operaciones básicas para un mapa de Pokémon.
 * Autor: Fatima Navarro
 */
public interface MapaPokemons {
    
    /**
     * Agrega un Pokémon al mapa utilizando su nombre como clave.
     */
    void agregarPokemon(Pokemon pokemon);
    
    /**
     * Obtiene un Pokémon del mapa por su nombre.
     */
    Pokemon obtenerPokemon(String nombre);
    
    /**
     * Devuelve el mapa completo de Pokémon.
     */
    Map<String, Pokemon> obtenerTodos();
    
    /**
     * Verifica si un Pokémon existe en el mapa por su nombre.
     */
    boolean existePokemon(String nombre);
    
    /**
     * Busca Pokémon que tengan ciertas habilidades.
     * @param habilidad Parte del texto de la habilidad a buscar
     */
    Map<String, Pokemon> buscarPorHabilidad(String habilidad);
    
    /**
     * Obtiene todos los Pokémon ordenados por tipo primario.
     */
    List<Pokemon> obtenerOrdenadosPorTipo();
    
    /**
     * Devuelve el nombre del tipo de mapa.
     */
    String getTipoMapa();
}