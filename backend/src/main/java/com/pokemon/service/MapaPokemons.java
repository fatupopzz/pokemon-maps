package com.pokemon.service;

import java.util.List;
import java.util.Map;

import com.pokemon.model.Pokemon;

/**
 * Interfaz que define las operaciones básicas para un mapa de Pokémon.
 * Autor: Fatima Navarro - 24044
 */
public interface MapaPokemons {
    
    //--------
    // Define el método para agregar un Pokémon al mapa
    // Recibe un objeto Pokémon y lo incorpora a la colección
    //--------
    void agregarPokemon(Pokemon pokemon);
    
    //--------
    // Permite obtener un Pokémon específico del mapa
    // utilizando su nombre como clave de búsqueda
    //--------
    Pokemon obtenerPokemon(String nombre);
    
    //--------
    // Devuelve el mapa completo con todos los Pokémon
    // La implementación determinará si está ordenado o no
    //--------
    Map<String, Pokemon> obtenerTodos();
    
    //--------
    // Verifica si un Pokémon existe en el mapa por su nombre
    // Útil para validaciones antes de agregar nuevos Pokémon
    //--------
    boolean existePokemon(String nombre);
    
    //--------
    // Busca Pokémon que tengan ciertas habilidades
    // Recibe parte del texto de una habilidad y busca coincidencias
    //--------
    Map<String, Pokemon> buscarPorHabilidad(String habilidad);
    
    //--------
    // Obtiene todos los Pokémon ordenados por tipo primario
    // independientemente de cómo estén almacenados en el mapa
    //--------
    List<Pokemon> obtenerOrdenadosPorTipo();
    
    //--------
    // Devuelve el nombre del tipo de mapa que se está utilizando
    // Útil para mostrar información al usuario
    //--------
    String getTipoMapa();
}